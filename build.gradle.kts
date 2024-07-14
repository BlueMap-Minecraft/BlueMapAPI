plugins {
    bluemap.base
}

dependencies {
    api ( libs.flow.math )
    api ( libs.gson )

    compileOnly ( libs.jetbrains.annotations )
    compileOnly ( libs.lombok )

    annotationProcessor ( libs.lombok )
}
