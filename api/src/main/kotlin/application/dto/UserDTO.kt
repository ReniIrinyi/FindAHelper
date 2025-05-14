package application.dto

import ch.abb.findahelper.model.Roles
import ch.abb.findahelper.model.UserModel
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserDTO(
    val id: Int? = null,
    val idToken: String? = null,
    val email: String,
    val password: String? = null,
    val name: String? = null,
    val authProvider: String? = null,
    val imgUrl: String? = null,
    val imgBase64: String? = null,
) : DTO<UserModel> {
    override fun toModel(): UserModel {
        return UserModel(
            id = id,
            email = email,
            name = name ?: "",
            authProvider = authProvider ?: "",
            imgUrl = imgUrl,
            img = imgBase64?.let { Base64.getDecoder().decode(it) },
            password = "",
            role = Roles.USER
        )
    }

    companion object {
        fun fromModel(user: UserModel): UserDTO {
            return UserDTO(
                id = user.id,
                email = user.email,
                name = user.name,
                authProvider = user.authProvider,
                imgUrl = user.imgUrl,
                imgBase64 = user.img?.takeIf { it.isNotEmpty() }
                    ?.let { Base64.getEncoder().encodeToString(it) },
            )
        }
    }
}
