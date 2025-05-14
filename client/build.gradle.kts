plugins {
    id("com.github.node-gradle.node") version "3.5.1"
}

node {
    version.set("18.17.1")
    npmVersion.set("9.6.7")
    download.set(true)
}


tasks.register<com.github.gradle.node.npm.task.NpmTask>("runFrontend") {
    /*dependsOn("npmInstall")*/
    args.set(listOf("run", "dev"))
}

