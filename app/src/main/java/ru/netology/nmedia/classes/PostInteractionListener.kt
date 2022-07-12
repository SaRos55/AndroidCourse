package ru.netology.nmedia.classes

interface PostInteractionListener {
    fun onLikeListener(post: Post)
    fun onShareListener(post: Post)
    fun onDeleteListener(post: Post)
    fun onEditListener(post: Post)
    fun onVideoListener(post: Post)
    fun onBodyListener(post: Post)
}