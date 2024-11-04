package com.example.dictionary.source.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class DictionaryEntity (
    @PrimaryKey (autoGenerate = true) val id : Int,
    val uzbek : String?,
    val english : String?,
    val type : String?,
    @ColumnInfo ("is_favourite")
    var isFavourite :Int?,
    val countable : String?,
    val transcript : String?

)