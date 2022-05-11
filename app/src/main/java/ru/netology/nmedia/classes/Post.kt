package ru.netology.nmedia.classes

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean = false,
    val likedCount: Int = 9999,
    val sharedCount: Int = 0
)