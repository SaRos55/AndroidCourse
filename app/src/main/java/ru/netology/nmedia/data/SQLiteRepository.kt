package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.classes.Post
import ru.netology.nmedia.db.PostDao

class SQLiteRepository(
    private val dao: PostDao
) : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    private val data = MutableLiveData(dao.getAll())

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeByID(id: Long) {
    dao.likeByID(id)
    data.value = posts.map {
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
        dao.shareByID(id)
        data.value = posts.map {
            if (it.id != id) it else {
                it.copy(sharedCount = it.sharedCount + 1)
            }
        }
    }

    override fun deleteByID(id: Long) {
        dao.removeByID(id)
        data.value = posts.filter { it.id != id }
    }

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        data.value = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
    }

    override fun getPost(id: Long): Post? {
        return posts.find { it.id == id }
    }

//    private companion object {
//        const val GENERATED_POST_AMOUNT = 10
//    }

}