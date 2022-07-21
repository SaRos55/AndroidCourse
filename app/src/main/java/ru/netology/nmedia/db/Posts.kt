package ru.netology.nmedia.db

import ru.netology.nmedia.classes.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    likedCount = likedCount,
    likedByMe = likedByMe,
    sharedCount = sharedCount
    )

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likedCount = likedCount,
    likedByMe = likedByMe,
    sharedCount = sharedCount
)