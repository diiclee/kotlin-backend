package com.example.kotlin_backend.service

import com.example.kotlin_backend.dto.request.CreateUserRequest
import com.example.kotlin_backend.dto.response.UserResponse
import com.example.kotlin_backend.mapper.UserMapper
import com.example.kotlin_backend.repository.UserRepository
import org.springframework.stereotype.Service
import com.example.kotlin_backend.util.findByIdOrThrow

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) {

    fun createUser(request: CreateUserRequest): UserResponse {
        val user = userMapper.toEntity(request)
        val savedUser = userRepository.save(user)
        return userMapper.toResponse(savedUser)
    }

    fun getUser(id: Long): UserResponse {
        val user = userRepository.findByIdOrThrow(id, "User")
        return userMapper.toResponse(user)
    }
}