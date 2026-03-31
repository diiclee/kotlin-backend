package com.example.kotlin_backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var email: String
) {
    constructor() : this(
        id = null,
        name = "",
        email = ""
    )
}