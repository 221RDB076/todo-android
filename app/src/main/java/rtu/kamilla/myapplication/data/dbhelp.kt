package rtu.kamilla.data


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class dbhelp(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todolist.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_TODOS = "todos"
        const val COLUMN_ID = "_id"
        const val COLUMN_TASK = "task"
        const val COLUMN_COMPLETED = "completed"

        private const val TABLE_CREATE =
            "CREATE TABLE $TABLE_TODOS (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TASK TEXT, " +
                    "$COLUMN_COMPLETED INTEGER DEFAULT 0)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TODOS")
        onCreate(db)
    }
}
