package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.classes.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = List(10) { index ->
        Post(
            id = index + 1L,
            author = "Василий Пупкин",
            content = "Привет, это мой пост №$index",
            published = "8 мая 15:44"
        )
    }

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeByID(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                var likedCount = it.likedCount
                if (it.likedByMe) {
                    likedCount--
                } else {
                    likedCount++
                }
                it.copy(likedByMe = !it.likedByMe, likedCount = likedCount)
            }
        }
        data.value = posts
    }

    override fun shareByID(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                it.copy(sharedCount = it.sharedCount + 1)
            }
        }
        data.value = posts
    }
}