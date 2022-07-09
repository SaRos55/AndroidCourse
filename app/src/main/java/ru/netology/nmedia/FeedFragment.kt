package ru.netology.nmedia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.activity.NewPostFragment.Companion.longArg
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.classes.Post
import ru.netology.nmedia.classes.PostInteractionListener
import ru.netology.nmedia.classes.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.viewModel.PostViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

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
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply { textArg = post.content }
                )
            }

            override fun onVideoListener(post: Post) {
                viewModel.onClickVideo(post)
            }

            override fun onBodyListener(post: Post) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_postFragment,
                    Bundle().apply { longArg = post.id }
                )
            }
        })

        binding.list.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        viewModel.videoEvent.observe(viewLifecycleOwner) { post ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
            startActivity(intent)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }

        return binding.root
    }
}