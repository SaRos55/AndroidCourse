package ru.netology.nmedia.data

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.classes.Post
import kotlin.properties.Delegates

class FilePostRepository(
    private val application: Application
) : PostRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    private var posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            application.openFileOutput(FILE_NAME, Context.MODE_PRIVATE
            ).bufferedWriter().use {
                it.write(gson.toJson(value))
            }
            data.value = value
        }

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )

    private var nextID: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue)    }

    }

    private val data: MutableLiveData<List<Post>>

    init {
        val postsFile = application.filesDir.resolve(FILE_NAME)
        val posts: List<Post> = if (postsFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use {
                gson.fromJson(it, type)
            }
        } else emptyList()
        data = MutableLiveData(posts)
    }

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
    }

    override fun shareByID(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                it.copy(sharedCount = it.sharedCount + 1)
            }
        }
    }

    override fun deleteByID(id: Long) {
        posts = posts.filter { it.id != id }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextID)
        ) + posts
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private companion object {
        const val NEXT_ID_PREFS_KEY = "next ID"
        const val FILE_NAME = "posts.json"
    }
}