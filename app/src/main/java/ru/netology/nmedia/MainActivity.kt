package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.activity.PostContentActivity
import ru.netology.nmedia.classes.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        val activityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent: String? ->
            postContent?.let(viewModel::onSaveButtonClicked)
        }

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
                activityLauncher.launch(post.content)
            }

            override fun onVideoListener(post: Post) {
                viewModel.onClickVideo(post)
            }

        })

        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.videoEvent.observe(this) { post ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
            //val shareIntent = Intent.createChooser(intent, "Воспроизвести с помощью...")
            startActivity(intent)
        }

        binding.fab.setOnClickListener {
            activityLauncher.launch(null)
        }
    }
}