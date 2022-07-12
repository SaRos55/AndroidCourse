package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.classes.LongArg
import ru.netology.nmedia.classes.StringArg
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.viewModel.PostViewModel

class NewPostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        arguments?.textArg.let(binding.edit::setText)

        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            val postContent = binding.edit.text?.toString()
            if (!postContent.isNullOrBlank()) {
                postContent.let(viewModel::onSaveButtonClicked)
            }
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
        var Bundle.longArg: Long? by LongArg
    }
}