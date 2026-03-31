package com.example.kotlin_backend.mapper

import com.example.kotlin_backend.dto.request.CreateUserRequest
import com.example.kotlin_backend.dto.response.UserResponse
import com.example.kotlin_backend.entity.User
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toEntity(request: CreateUserRequest): User {
        return User(
            name = request.name,
            email = request.email
        )
    }

    fun toResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id ?: throw IllegalStateException("User id was null after save"),
            name = user.name,
            email = user.email
        )
    }
}