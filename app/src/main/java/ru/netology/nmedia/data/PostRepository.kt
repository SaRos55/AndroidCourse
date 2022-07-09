package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.classes.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeByID(id: Long)
    fun shareByID(id: Long)
    fun deleteByID(id: Long)
    fun save(post: Post)
    fun getPost(id: Long):Post?

    companion object {
        const val NEW_POST_ID = 0L
    }
}