package com.example.roomwordsample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.RowId
import java.sql.Time
import java.sql.Types.ROWID

@Entity(tableName = "word_table")
class Word(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    @ColumnInfo(name="word") val word: String,
    @ColumnInfo(name="time") var time : String
    )