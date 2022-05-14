package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import ru.netology.nmedia.classes.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeByID(id: Long)
    fun shareByID(id: Long)
}