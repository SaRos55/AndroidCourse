package ru.netology.nmedia.classes

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likedCount: Int = 9999,
    val sharedCount: Int = 0,
    val video: String? = null
)