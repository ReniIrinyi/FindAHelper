package common

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LoggerService {
    private val logFile = File("logs/debug.log")
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun debugLog(data: Any) {
        val timestamp = LocalDateTime.now().format(formatter)
        val traceElement = Thread.currentThread().stackTrace
            .firstOrNull { it.className.contains("usecase") || it.className.contains("service") || it.className.contains("controller") }

        val location = if (traceElement != null) {
            "${traceElement.fileName}:${traceElement.lineNumber} -> ${traceElement.methodName}()"
        } else {
            "unknown source"
        }

        val logEntry = buildString {
            append("[$timestamp] [$location]\n")
            appendLine(
                try {
                    Json.encodeToString(data)
                } catch (e: Exception) {
                    data.toString()
                }
            )
        }

        logFile.parentFile.mkdirs()
        logFile.appendText(logEntry)
    }
}
