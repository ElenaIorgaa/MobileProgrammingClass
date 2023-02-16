package com.example.android.codelabs.navigation

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper  // creating a constructor for our database handler.
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT,"
                + AUTHOR + " TEXT)")
        db.execSQL(query)
    }

    fun addNewBook(
        title: String?,
        author: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(TITLE, title)
        values.put(AUTHOR, author)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    @SuppressLint("Range")
    fun getAllBooks():ArrayList<Book> {
        val db = this.writableDatabase
        val elements = ArrayList<Book>()
        val selectAll = "select * from $TABLE_NAME"
        val cursor = db.rawQuery(selectAll,null)

        if(cursor.moveToFirst())
        {
            do{
                elements.add(Book(cursor.getInt(cursor.getColumnIndex(ID_COL)),cursor.getString(cursor.getColumnIndex(TITLE)),
                    cursor.getString(cursor.getColumnIndex(AUTHOR))))

            }while(cursor.moveToNext())
        }
        cursor.close()
        return elements
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "books"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "books"
        private const val ID_COL = "id"
        private const val TITLE = "title"
        private const val AUTHOR = "author"
    }
}