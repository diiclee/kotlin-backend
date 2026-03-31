package com.example.kotlin_backend.repository

import com.example.kotlin_backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>