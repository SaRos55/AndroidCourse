package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.classes.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = List(GENERATED_POST_AMOUNT) { index ->
        Post(
            id = index + 1L,
            author = "Василий Пупкин",
            content = "Привет, это мой пост №$index. \nЗдесь должен быть какой-то текст",
            published = "8 мая 15:44"
        )
    }

    private var nextID = GENERATED_POST_AMOUNT.toLong()

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

    override fun deleteByID(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextID)
        ) + posts
        data.value = posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
        data.value = posts
    }

    private companion object {
        const val GENERATED_POST_AMOUNT = 10
    }
}