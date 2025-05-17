package config

// DatabaseConfig.kt
import com.typesafe.config.ConfigFactory
import adapter.persistence.database.table.User
import org.jetbrains.exposed.sql.Database
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File


object DatabaseConfig {
    fun init(app: Application) {
        val fileConfig = ConfigFactory.load()
        val dbUrl = fileConfig.getString("database.url")
        val user =fileConfig.getString("database.user")
        val password =  fileConfig.getString("database.password")
        val driver =  fileConfig.getString("database.driver")

        Database.connect(
            url = dbUrl,
            driver = driver,
            user = user,
            password = password
        )

        transaction {
            SchemaUtils.create(User)
        }
    }
}
