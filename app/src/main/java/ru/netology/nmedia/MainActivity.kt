package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.classes.*
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = PostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = PostViewModel()

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                if (post.likedByMe) {
                    likes.setImageResource(R.drawable.ic_liked_24dp)
                } else {
                    likes.setImageResource(R.drawable.ic_likes_24dp)
                }
                likesCount.text = convertCount(post.likedCount)
                sharesCount.text = convertCount(post.sharedCount)
            }
        }

        binding.likes.setOnClickListener {
            viewModel.like()
        }

        binding.shares.setOnClickListener {
            viewModel.share()
        }
    }
}