pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "find-a-helper"

include("api", "client")
include("client")
