package com.example.roomwordsample

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


@Database(entities = arrayOf(Word::class),
    version = 2,
exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object{
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
        fun getDatabase(context: Context,scope:CoroutineScope): WordRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        @SuppressLint("SimpleDateFormat")
        suspend fun populateDatabase(wordDao: WordDao) {
            // Delete all content here.
            wordDao.deleteAll()

            val time = Calendar.getInstance().time
            val formatter = SimpleDateFormat("HH:mm:ss")
            // Add sample words.
            var word = Word(word = "Hello", time = formatter.format(time))
            wordDao.insert(word)
            word = Word(word = "World!", time = formatter.format(time))
            wordDao.insert(word)

            // TODO: Add your own words!
        }
    }
}