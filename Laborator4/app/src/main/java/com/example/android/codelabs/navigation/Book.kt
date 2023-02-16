package com.example.android.codelabs.navigation

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
class Book(val id: Int, val title: String, val author: String) : Parcelable {
}