package com.example.sqldatabase


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (" +
                    "$COL_1 INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COL_2 TEXT, " +
                    "$COL_3 TEXT, " +
                    "$COL_4 INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(name: String?, surname: String?, marks: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_2, name)
            put(COL_3, surname)
            put(COL_4, marks)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun getData(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun updateData(id: String, name: String?, surname: String?, marks: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_2, name)
            put(COL_3, surname)
            put(COL_4, marks)
        }
        val result = db.update(TABLE_NAME, contentValues, "$COL_1 = ?", arrayOf(id))
        return result > 0
    }

    fun delData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_1 = ?", arrayOf(id))
    }

    companion object {
        const val DATABASE_NAME = "students.db"
        const val TABLE_NAME = "s1_table"
        const val COL_1 = "ID"
        const val COL_2 = "NAME"
        const val COL_3 = "SURNAME"
        const val COL_4 = "MARKS"
    }
}
