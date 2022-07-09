package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.FeedFragment
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.NewPostFragment.Companion.longArg
import ru.netology.nmedia.activity.NewPostFragment.Companion.textArg
import ru.netology.nmedia.classes.Post
import ru.netology.nmedia.classes.convertCount
import ru.netology.nmedia.databinding.FragmentPostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


        val post = arguments?.longArg?.let { viewModel.postByID(it) }

        fun onLikeListener(post: Post) {
            viewModel.likeByID(post.id)
        }
        fun onShareListener(post: Post) {
            viewModel.shareByID(post.id)
        }
        fun onDeleteListener(post: Post) {
            viewModel.deleteByID(post.id)
            findNavController().navigateUp()
        }
        fun onEditListener(post: Post) {
            viewModel.onEditButtonClicked(post)
            findNavController().navigateUp()
            findNavController().navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply { textArg = post.content }
            )
        }
        fun onVideoListener(post: Post) {
            viewModel.onClickVideo(post)
            findNavController().navigateUp()
        }

        binding.apply {
            if (post != null) {
                likes.setOnClickListener {
                    onLikeListener(post)
                }
                shares.setOnClickListener {
                    onShareListener(post)
                }
                video.setOnClickListener {
                    onVideoListener(post)
                }
            }
            menu.setOnClickListener {
                if (post != null)
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onDeleteListener(post)
                                true
                            }
                            R.id.edit -> {
                                onEditListener(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
        }

        viewModel.data.observe(viewLifecycleOwner) {
            val postNew = arguments?.longArg?.let { viewModel.postByID(it) }
            if (postNew != null)
                binding.apply {
                    author.text = postNew.author
                    published.text = postNew.published
                    content.text = postNew.content
                    likes.isChecked = postNew.likedByMe
                    likes.text = convertCount(postNew.likedCount)
                    shares.text = convertCount(postNew.sharedCount)
                    video.visibility = if (post?.video == null) View.GONE else View.VISIBLE
                }
        }
        return binding.root
    }

}