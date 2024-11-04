package com.example.dictionary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.R
import com.example.dictionary.databinding.ItemDictionaryBinding
import com.example.dictionary.source.entity.DictionaryEntity

class DictionaryAdapter() :
    ListAdapter<DictionaryEntity, DictionaryAdapter.VHolder>(DictionaryDiffUtil) {
    private var changeFavouriteListener : ((Int, DictionaryEntity) -> Unit)?= null
    private var listenWordListener : ((DictionaryEntity) -> Unit)? = null

    object DictionaryDiffUtil : DiffUtil.ItemCallback<DictionaryEntity>() {
        override fun areItemsTheSame(
            oldItem: DictionaryEntity,
            newItem: DictionaryEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: DictionaryEntity,
            newItem: DictionaryEntity
        ): Boolean {
            return oldItem == newItem
        }

    }

    inner class VHolder(val binding: ItemDictionaryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.buttonFavourite.setOnClickListener{
                getItem(adapterPosition).isFavourite= getItem(adapterPosition).isFavourite?.xor(1)
                changeFavouriteListener?.invoke(adapterPosition, getItem(adapterPosition))
                notifyItemChanged(adapterPosition)
            }

            binding.buttonListen.setOnClickListener {
                listenWordListener?.invoke(getItem(adapterPosition))
            }
        }

        fun bind(position: Int) {
            binding.textEnglish.text = getItem(position).english
            binding.textUzbek.text = getItem(position).uzbek
//            if (getItem(position).isFavourite == 0) {
//                binding.buttonFavourite.setImageResource(R.drawable.ic_unselected)
//            } else binding.buttonFavourite.setImageResource(R.drawable.ic_selected)
            binding.buttonFavourite.setImageResource(if (getItem(position).isFavourite == 0) R.drawable.ic_unselected else R.drawable.ic_selected)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(
            binding = ItemDictionaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(position)
    }
    fun setChangeFavouriteListener(block: (Int, DictionaryEntity) -> Unit) {
        changeFavouriteListener = block
    }
    fun setListenWordListener (block : (DictionaryEntity) -> Unit) {
        listenWordListener = block
    }
}