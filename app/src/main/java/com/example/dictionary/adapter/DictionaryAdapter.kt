package com.example.dictionary.adapter

import android.annotation.SuppressLint
import android.database.Cursor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dictionary.R
import com.example.dictionary.databinding.ItemDictionaryBinding

class DictionaryAdapter : Adapter<DictionaryAdapter.VHolder>() {
    private var cursor: Cursor? = null

    private var listener: ((Int) -> Unit)? = null
    private var favListener : ((Int, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    inner class VHolder(private val itemDictionaryBinding: ItemDictionaryBinding) :
        ViewHolder(itemDictionaryBinding.root) {

        init {
            itemDictionaryBinding.root.setOnClickListener {
                cursor?.let {
                    it.moveToPosition(adapterPosition)
                    listener?.invoke(it.getInt(it.getColumnIndex("id") ?: 0))
                }
            }
            itemDictionaryBinding.buttonFavourite.setOnClickListener {
                cursor?.let {
                    it.moveToPosition(adapterPosition)
                    favListener?.invoke(
                        it.getInt(it.getColumnIndex("id") ?: 0),
                        it.getInt(it.getColumnIndex("is_favourite") ?: 0)
                    )
                }
            }
        }

        fun bind(position: Int) {
            cursor?.let {
                it.moveToPosition(position)
                itemDictionaryBinding.textUzbek.text = it.getString(it.getColumnIndex("uzbek") ?: 0)
                itemDictionaryBinding.textEnglish.text =
                    it.getString(it.getColumnIndex("english") ?: 0)
                val isFav = it.getInt(it.getColumnIndex("is_favourite") ?: 0)

                itemDictionaryBinding.buttonFavourite.setImageResource(if (isFav == 0) R.drawable.ic_unselected else R.drawable.ic_selected )
            }
        }
    }

    fun setOnClickListener(l: (Int) -> Unit) {
        listener = l
    }

    fun setOnFavClickListener(l: (Int, Int) -> Unit) {
        favListener = l
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(
            ItemDictionaryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = cursor?.count ?: 0

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(position)
    }
}