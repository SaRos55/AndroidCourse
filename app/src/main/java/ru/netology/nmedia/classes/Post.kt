package ru.netology.nmedia.classes

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean = false,
    var likedCount: Int = 9999,
    var sharedCount: Int = 0
)