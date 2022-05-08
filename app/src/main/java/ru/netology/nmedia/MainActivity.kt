package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.classes.*
import ru.netology.nmedia.databinding.PostBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Вася Пупкин",
            content = "Всем привет! Это мой первый пост! УРА!!!",
            published = "28 апреля в 13:45"
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                likes.setImageResource(R.drawable.ic_liked_24dp)
            }
            likesCount.text = convertCount(post.likedCount)
            likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) {
                    likes.setImageResource(R.drawable.ic_liked_24dp)
                    post.likedCount++
                } else {
                    likes.setImageResource(R.drawable.ic_likes_24dp)
                    post.likedCount--
                }
                likesCount.text = convertCount(post.likedCount)
            }

            shares.setOnClickListener {
                post.sharedCount++
                sharesCount.text = convertCount(post.sharedCount)
            }
        }
    }
}