package ch.abb.findahelper.model

import kotlinx.serialization.Serializable

@Serializable
enum class Roles {
    USER,
    ADMIN
}

@Serializable
data class UserModel(
    val id: Int? = null,
    val email: String,
    val password: String,
    val authProvider: String,
    val role: Roles,
    val name:String,
    val imgUrl:String?="",
    val img: ByteArray?=null,
)