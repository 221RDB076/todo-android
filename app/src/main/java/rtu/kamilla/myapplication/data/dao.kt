package rtu.kamilla.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDAO(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todos.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_TODO = "todo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TASK = "task"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_TODO ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TASK TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TODO")
        onCreate(db)
    }

    fun createTodo(task: String): Todo {
        val values = ContentValues().apply {
            put(COLUMN_TASK, task)
        }
        val id = writableDatabase.insert(TABLE_TODO, null, values)
        return Todo(id, task)
    }

    fun getAllTodos(): List<Todo> {
        val todos = mutableListOf<Todo>()
        val cursor = readableDatabase.query(TABLE_TODO, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val task = getString(getColumnIndexOrThrow(COLUMN_TASK))
                todos.add(Todo(id, task))
            }
        }
        cursor.close()
        return todos
    }

    fun updateTodo(todo: Todo) {
        val values = ContentValues().apply {
            put(COLUMN_TASK, todo.task)
        }
        writableDatabase.update(TABLE_TODO, values, "$COLUMN_ID = ?", arrayOf(todo.id.toString()))
    }

    fun deleteTodo(todo: Todo) {
        writableDatabase.delete(TABLE_TODO, "$COLUMN_ID = ?", arrayOf(todo.id.toString()))
    }
}
