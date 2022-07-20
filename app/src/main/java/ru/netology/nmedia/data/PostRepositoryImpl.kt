package ru.netology.nmedia.data

import androidx.lifecycle.map
import ru.netology.nmedia.classes.Post
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    private val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getAll() = data

    override fun likeByID(id: Long) {
        dao.likeById(id)
    }

    override fun shareByID(id: Long) {
        dao.shareByID(id)

    }

    override fun deleteByID(id: Long) {
        dao.removeById(id)
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity())
        else dao.updateContentById(post.id, post.content)
    }

    override fun getPost(id: Long): Post? {
        return data.value?.find { it.id == id }
    }

}