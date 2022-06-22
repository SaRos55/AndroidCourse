package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.classes.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.utils.hideKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val adapter = PostsAdapter(object : PostInteractionListener {
            override fun onLikeListener(post: Post) {
                viewModel.likeByID(post.id)
            }

            override fun onShareListener(post: Post) {
                viewModel.shareByID(post.id)
            }

            override fun onDeleteListener(post: Post) {
                viewModel.deleteByID(post.id)
            }

            override fun onEditListener(post: Post) {
                viewModel.onEditButtonClicked(post)
                binding.postHeader.text = post.author
                binding.cancelGroup.visibility = View.VISIBLE
            }
        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.currentPost.observe(this) { currentPost ->
            binding.content.setText(currentPost?.content)
        }

        fun closeKeyboard() {
            binding.cancelGroup.visibility = View.GONE
            with(binding.content) {
                clearFocus()
                hideKeyboard()
            }
        }

        binding.save.setOnClickListener {
                val content = binding.content.text.toString()
                viewModel.onSaveButtonClicked(content)
                closeKeyboard()

        }

        binding.cancel.setOnClickListener {
            viewModel.onCancelButtonClicked()
            closeKeyboard()
        }
    }
}