package com.example.roomwordsample

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()
    @SuppressLint("SimpleDateFormat")
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word:Word)
    {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm:ss")
        word.time = formatter.format(time);
        wordDao.insert(word)
    }
}