package ru.netology.nmedia.classes

import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding

class PostViewHolder(
    private val binding: PostBinding,
    private val interactionListener: PostInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.isChecked = post.likedByMe
            likes.text = convertCount(post.likedCount)
            shares.text = convertCount(post.sharedCount)
            likes.setOnClickListener {
                interactionListener.onLikeListener(post)
            }
            shares.setOnClickListener {
                interactionListener.onShareListener(post)
            }

            video.visibility = if (post.video == null) View.GONE else View.VISIBLE
            video.setOnClickListener {
                interactionListener.onVideoListener(post)
            }

            body.setOnClickListener {
                interactionListener.onBodyListener(post)
            }


            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                interactionListener.onDeleteListener (post)
                                true
                            }
                            R.id.edit -> {
                                interactionListener.onEditListener(post)
                                true
                            }
                            else -> false
                        }

                    }
                }.show()
            }
        }
    }
}