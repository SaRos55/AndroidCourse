package ru.netology.nmedia.classes

import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding

class PostViewHolder(
    private val binding: PostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
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
            likes.setOnClickListener {
                onLikeListener(post)
            }
            shares.setOnClickListener {
                onShareListener(post)
            }
        }
    }
}


