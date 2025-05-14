package ch.abb.findahelper.application.interactor

import adapter.persistence.database.repository.UserRepository

class UserInteractor(
    private val userRepository: UserRepository,
) {
}
