package adapter.persistence.database.table

import org.jetbrains.exposed.sql.Table


enum class AuthProvider {
    ENUM_LOCAL,
    ENUM_GOOGLE
}

object User: Table("user") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 255)
    val password = varchar("password", 255).nullable()
    val authProvider = enumerationByName("authProvider", 255, AuthProvider::class)
    val name = varchar("name", 255).nullable()
    val imgUrl = varchar("imgUrl", 255).nullable()
    val img = blob("img").nullable()

    override val primaryKey = PrimaryKey(id)
}