package de.bluecolored.bluemap.api.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A dispatcher able to dispatch events of a specific type to registered {@link EventConsumer}s.
 * @param <T> The type of events this dispatcher dispatches
 */
@SuppressWarnings("unused")
public class EventDispatcher<T> {

    private final Collection<EventConsumer<? super T>> listeners = new ArrayList<>();

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * Instances of this class should only be acquired using {@link Events#getDispatcher(Class)}
     */
    EventDispatcher() {}

    /**
     * Dispatches the given event to all {@link EventConsumer}s registered to this dispatcher.
     * @param event The event that should be dispatched
     * @throws Exception If one or more of the {@link EventConsumer}s threw an exception
     */
    public void dispatch(T event) throws Exception {
        List<Exception> thrownExceptions = null;

        lock.readLock().lock();
        try {
            for (EventConsumer<? super T> listener : listeners) {
                try {
                    listener.accept(event);
                } catch (Exception e) {
                    if (thrownExceptions == null) thrownExceptions = new ArrayList<>(1);
                    thrownExceptions.add(e);
                }
            }
        } finally {
            lock.readLock().unlock();
        }

        if (thrownExceptions != null && !thrownExceptions.isEmpty()) {
            Exception ex = thrownExceptions.get(0);
            for (int i = 1; i < thrownExceptions.size(); i++) {
                ex.addSuppressed(thrownExceptions.get(i));
            }
            throw ex;
        }
    }

    /**
     * Adds an {@link EventConsumer} to this dispatcher.
     * @param listener The {@link EventConsumer} to be added
     */
    public void addListener(EventConsumer<? super T> listener) {
        lock.writeLock().lock();
        try {
            listeners.add(listener);
        } finally {
            lock.writeLock().unlock();
        }
    }

    /**
     * Removes an {@link EventConsumer} from this dispatcher.
     * @param listener The {@link EventConsumer} to be removed
     */
    public boolean removeListener(EventConsumer<?> listener) {
        lock.writeLock().lock();
        try {
            return listeners.remove(listener);
        } finally {
            lock.writeLock().unlock();
        }
    }

}
