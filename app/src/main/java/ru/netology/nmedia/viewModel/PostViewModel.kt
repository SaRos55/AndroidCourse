package ru.netology.nmedia.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.classes.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()

    val currentPost = MutableLiveData<Post?>(null)

    fun likeByID(id: Long) = repository.likeByID(id)
    fun shareByID(id: Long) = repository.shareByID(id)
    fun deleteByID(id: Long) = repository.deleteByID(id)

    fun onSaveButtonClicked(content: String) {
        if (content.isBlank()) return

        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Ростислав",
            content = content,
            published = "21 июня 14:46",
            likedCount = 0
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onEditButtonClicked(post: Post) {
        currentPost.value = post
    }

    fun onCancelButtonClicked() {
        currentPost.value = null
    }

}