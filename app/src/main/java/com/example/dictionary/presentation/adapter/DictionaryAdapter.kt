package com.example.dictionary.presentation.adapter

import android.database.Cursor
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dictionary.R
import com.example.dictionary.databinding.ItemDictionaryBinding
import com.example.dictionary.source.entity.DictionaryEntity

class DictionaryAdapter : Adapter<DictionaryAdapter.VHolder>() {
    private var cursor: Cursor? = null
    private var query: String = "" // Current search query
    private var listener: ((DictionaryEntity) -> Unit)? = null
    private var favListener: ((DictionaryEntity) -> Unit)? = null

    fun setCursor(cursor: Cursor) {
        this.cursor = cursor
        notifyDataSetChanged()
    }

    fun updateQuery(newQuery: String) {
        query = newQuery
        notifyDataSetChanged() // Refresh the list to apply highlighting
    }

    inner class VHolder(private val binding: ItemDictionaryBinding) :
        ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    listener?.invoke(getData(adapterPosition))
                }
                buttonFavourite.setOnClickListener {
                    buttonFavourite.setImageResource(
                        if (getData(adapterPosition).isFavourite == 0) R.drawable.ic_unselected else R.drawable.ic_selected
                    )
                    favListener?.invoke(getData(adapterPosition))
                }
            }
        }

        fun bind(position: Int) {
            val data = getData(position)

            binding.apply {
                textUzbek.text = SpannedString(textUzbek.text.toString())
                textEnglish.text = data.english?.let { highlightQueryText(it, query) }
                buttonFavourite.setImageResource(
                    if (data.isFavourite == 0) R.drawable.ic_unselected else R.drawable.ic_selected
                )
            }
        }
    }

    fun setOnClickListener(l: (DictionaryEntity) -> Unit) {
        listener = l
    }

    fun setOnFavClickListener(block: (DictionaryEntity) -> Unit) {
        favListener = block
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

    fun getData(index: Int): DictionaryEntity {
        return cursor?.run {
            moveToPosition(index)
            getColumnIndex(DictionaryEntity::id.name) ?: 0
            DictionaryEntity(
                id = getInt(0),
                uzbek = getString(getColumnIndex(DictionaryEntity::uzbek.name) ?: 0),
                english = getString(getColumnIndex(DictionaryEntity::english.name) ?: 0),
                type = getString(getColumnIndex(DictionaryEntity::type.name) ?: 0),
                isFavourite = getInt(getColumnIndex("is_favourite") ?: 0),
                countable = getString(getColumnIndex(DictionaryEntity::countable.name) ?: 0),
                transcript = getString(getColumnIndex(DictionaryEntity::transcript.name) ?: 0)
            )
        } ?: DictionaryEntity(-1, "", "", "", 0, "", "")
    }

    private fun highlightQueryText(fullText: String, query: String): SpannedString {
        val spannableString = SpannableString(fullText)
        val startIndex = fullText.indexOf(query, ignoreCase = true)
        if (startIndex >= 0) {
            spannableString.setSpan(
                ForegroundColorSpan(Color.RED),
                startIndex,
                startIndex + query.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return SpannedString(spannableString)
    }
}
