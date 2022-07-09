package ru.netology.nmedia.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.classes.Post
import ru.netology.nmedia.data.FilePostRepository
import ru.netology.nmedia.data.PostRepository

class PostViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository: PostRepository =
        FilePostRepository(application)

    val data = repository.getAll()

    private val currentPost = MutableLiveData<Post?>(null)

    val videoEvent = SingleLiveEvent<Post>()

    fun likeByID(id: Long) = repository.likeByID(id)
    fun shareByID(id: Long) = repository.shareByID(id)
    fun deleteByID(id: Long) = repository.deleteByID(id)
    fun postByID(id: Long) = repository.getPost(id)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Ростислав",
            content = content,
            published = "21 июня 14:46",
            likedCount = 0,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onEditButtonClicked(post: Post) {
        currentPost.value = post
    }

    fun onClickVideo(post: Post) {
        videoEvent.value = post
    }
}