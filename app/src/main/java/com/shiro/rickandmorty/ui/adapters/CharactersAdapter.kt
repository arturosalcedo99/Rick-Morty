package com.shiro.rickandmorty.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shiro.rickandmorty.databinding.ItemCharacterBinding
import com.shiro.rickandmorty.domain.models.Character

class CharactersAdapter(
    private val context: Context,
    private val clickListener: () -> Unit
): ListAdapter<Character,
        CharactersAdapter.CharacterViewHolder>(CharactersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
       holder.bind(context, getItem(position), clickListener)
    }

    class CharacterViewHolder(
        private val binding: ItemCharacterBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            context: Context,
            item: Character,
            clickListener: () -> Unit
        ) {
            binding.character = item
            binding.root.setOnClickListener { clickListener() }
            Glide.with(context)
                .load(item.image)
                .into(binding.imageUser)
        }

        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCharacterBinding.inflate(layoutInflater, parent, false)
                return CharacterViewHolder(binding)
            }
        }
    }

    class CharactersDiffCallback(): DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}