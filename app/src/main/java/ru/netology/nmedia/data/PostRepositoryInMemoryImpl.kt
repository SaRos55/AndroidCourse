package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.classes.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Василий Пупкин",
        content = "Привет, это мой новый пост!",
        published = "8 мая 15:44"
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun like() {
        var likedCount = post.likedCount
        if (post.likedByMe) {
            likedCount--
        } else {
            likedCount++
        }
        post = post.copy(likedByMe = !post.likedByMe, likedCount = likedCount)
        data.value = post
    }

    override fun share() {
        post = post.copy(sharedCount = post.sharedCount + 1)
        data.value = post
    }
}