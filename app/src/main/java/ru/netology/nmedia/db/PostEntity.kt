package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class PostEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    val author: String,
    val content: String,
    val published: String,

    @ColumnInfo(name = "likedByMe")
    val likedByMe: Boolean = false,
    val likedCount: Int = 9999,
    val sharedCount: Int = 0
)