package de.bluecolored.bluemap.api.events;

import org.jetbrains.annotations.Nullable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to register and dispatch BlueMapAPI-Events.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public final class Events {

    private static final Collection<ListenerRegistration<?>> registrations = new ArrayList<>();
    private static final Map<Class<?>, EventDispatcher<?>> dispatchers = new HashMap<>();

    private Events() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Returns the event-dispatcher for a specific event-class.
     * @param eventClass The event-class of the dispatcher
     * @return The event-dispatcher
     * @param <T> The type of the event
     */
    @SuppressWarnings("unchecked")
    public static synchronized <T> EventDispatcher<T> getDispatcher(Class<T> eventClass) {
        return (EventDispatcher<T>) dispatchers.computeIfAbsent(eventClass, c -> {
            EventDispatcher<T> dispatcher = new EventDispatcher<>();

            for (ListenerRegistration<?> reg : registrations) {
                if (!reg.getEventClass().isAssignableFrom(eventClass)) continue;
                ListenerRegistration<? super T> registration = (ListenerRegistration<? super T>) reg;

                EventConsumer<? super T> listener = registration.getListener();
                if (listener != null) dispatcher.addListener(listener);
            }

            return dispatcher;
        });
    }

    /**
     * Registers an {@link EventConsumer} that will be invoked for every event of the eventClass-type AND any subclass.
     * @param eventClass The (super-)class of the event that should be listened for
     * @param addon The addon/plugin/mod instance (can be used in {@link #unregisterListeners(Object)}
     *              to unregister all listeners of this addon)
     * @param listener The {@link EventConsumer} that will be invoked
     * @param <T> The event-type
     */
    @SuppressWarnings("unchecked")
    public static synchronized <T> void registerListener(Class<T> eventClass, Object addon, EventConsumer<? super T> listener) {
        ListenerRegistration<T> registration = new ListenerRegistration<>(eventClass, addon, listener);

        registrations.add(registration);

        dispatchers.forEach((dispatcherEventClass, dispatcher) -> {
            if (eventClass.isAssignableFrom(dispatcherEventClass))
                ((EventDispatcher<? extends T>) dispatcher).addListener(listener);
        });
    }

    /**
     * <p>Registers all methods of the provided holder-instance that are annotated with {@link Listener}
     * as an {@link EventConsumer} (see: {@link #registerListener(Class, Object, EventConsumer)}</p>
     *
     * <p>Annotated methods need to have exactly one parameter which represents the event that should be listened for.</p>
     *
     * <p>An example listener method could look like this:
     * <blockquote><pre>
     *      {@code @Events.Listener}
     *      public void on(LifecycleEvent.Load.Post evt) {
     *          // do something
     *      }
     * </pre></blockquote></b>
     *
     * @param addon The addon/plugin/mod instance (can be used in {@link #unregisterListeners(Object)}
     *              to unregister all listeners of this addon)
     * @param holder The instance that will be scanned for {@link Listener}-methods
     * @throws ListenerRegistrationException If an annotated method does not match the requirements to be used as an event-listener.
     */
    public static synchronized void registerListeners(Object addon, Object holder) throws ListenerRegistrationException {
        for (Method method : holder.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Listener.class)) continue;

            // sanity checks
            if (method.getParameterTypes().length != 1) {
                throw new ListenerRegistrationException("Failed to register listener-method '" + method +
                        "': Method must have exactly one parameter!");
            }

            if (!method.trySetAccessible()) {
                throw new ListenerRegistrationException("Failed to register listener-method '" + method +
                        "': Method is not accessible!");
            }

            Class<?> eventClass = method.getParameterTypes()[0];
            registerListener(eventClass, addon, event -> method.invoke(holder, event));
        }
    }

    /**
     * Unregisters an {@link EventConsumer} that has been previously registered with {@link #registerListener(Class, Object, EventConsumer)}
     * @param listener The listener that should be unregistered from all events
     */
    public static synchronized boolean unregisterListener(EventConsumer<?> listener) {
        boolean removed = false;
        for (EventDispatcher<?> dispatcher : dispatchers.values()) {
            removed |= dispatcher.removeListener(listener);
        }
        return removed;
    }

    /**
     * Unregisters all {@link EventConsumer}s that have been previously registered with the given addon-instance.
     * @param addon The addon instance whose listeners should be unregistered
     */
    public static synchronized void unregisterListeners(Object addon) {
        for (ListenerRegistration<?> registration : registrations) {
            Object registrationAddon = registration.getAddon();
            if (registrationAddon != null && !registrationAddon.equals(addon)) continue;

            EventConsumer<?> listener = registration.getListener();
            if (listener == null) continue;

            unregisterListener(listener);
        }

        // tidy up registrations list
        registrations.removeIf(registration -> registration.getListener() == null);
    }

    private static class ListenerRegistration<T> {
        private final Class<T> eventClass;
        private final WeakReference<Object> addon;
        private final WeakReference<EventConsumer<? super T>> listenerRef;

        public ListenerRegistration(Class<T> eventClass, Object addon, EventConsumer<? super T> listener) {
            this.eventClass = eventClass;
            this.addon = new WeakReference<>(addon);
            this.listenerRef = new WeakReference<>(listener);
        }

        public Class<T> getEventClass() {
            return eventClass;
        }

        public @Nullable Object getAddon() {
            return addon.get();
        }

        public @Nullable EventConsumer<? super T> getListener() {
            return listenerRef.get();
        }

    }

    /**
     * This Annotation represents a method that can be registered with {@link #registerListeners(Object, Object)}.
     * @see #registerListeners(Object, Object)
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Listener {}

}
