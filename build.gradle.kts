import java.io.BufferedReader
import java.io.InputStreamReader

fun startProcess(name: String, directory: File, command: List<String>) {
    try {
        println("▶ Starting $name: ${command.joinToString(" ")}")

        val process = ProcessBuilder(command)
            .directory(directory)
            .redirectErrorStream(true)
            .start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            println("[$name] $line")
        }

        process.waitFor()
    } catch (e: Exception) {
        println("⚠️ Fehler beim Start von $name: ${e.message}")
    }
}


fun killPort(port: String) {
    try {
        val command = listOf("bash", "-c", "fuser -k ${port}/tcp")
        val process = ProcessBuilder(command)
            .redirectErrorStream(true)
            .start()

        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            println("[killPort:$port] $line")
        }
        process.waitFor()
        println("🔌 Port $port ist geschlossen.")
    } catch (e: Exception) {
        println("Fehler: Port konnte nicht geschlossen werden $port: ${e.message}")
    }
}

fun isPortInUse(port: Int): Boolean {
    return try {
        val socket = java.net.Socket("localhost", port)
        socket.close()
        true
    } catch (e: Exception) {
        false
    }
}

tasks.register("runBoth") {
    doLast {
        if (isPortInUse(5173)) {
            println("⚠️ Frontend port 5173 already in use. Killing process...")
            killPort("5173")
            Thread.sleep(2000)
        }
        startProcess(
            name = "Backend",
            directory = file("."),
            command = listOf("./gradlew", ":api:runBackend")
        )
        if (isPortInUse(5173)) {
            println("⚠️ Backend port 8080 already in use. Killing process...")
            killPort("8080")
            Thread.sleep(2000)
        }
        startProcess(
            name = "Frontend",
            directory = file("client"),
            command = listOf("npm", "run", "dev")
        )

        println("✅ Starting Frontend + Backend erfolgreich.")
        println("⏳ Up and Running... (To stop: Ctrl+C)")

        while (true) {
            Thread.sleep(10_000)
        }
    }
}

tasks.register("restartFrontend") {
    doLast {
        killPort("5173")
        startProcess(
            name = "Frontend",
            directory = file("client"),
            command = listOf("npm", "run", "dev")
        )

        println("Frontend restarted.")
    }
}

tasks.register("restartBackend") {
    doLast {
        killPort("8080")
        startProcess(
            name = "Backend",
            directory = file("."),
            command = listOf("./gradlew", ":api:runBackend")
        )

        println("Backend restarted.")
    }
}

tasks.register("restartBoth") {
    doLast {
        killPort("5173")
        killPort("8080")
        val backendThread = Thread {
            startProcess("Backend", file("."), listOf("./gradlew", ":api:runBackend"))
        }

        val frontendThread = Thread {
            startProcess("Frontend", file("client"), listOf("npm", "run", "dev"))
        }

        backendThread.start()
        frontendThread.start()

        println("🚀 Restarting frontend + backend...")

        backendThread.join()
        frontendThread.join()
    }
}




