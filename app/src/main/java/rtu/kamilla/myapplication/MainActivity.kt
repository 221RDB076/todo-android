package rtu.kamilla.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import rtu.kamilla.data.Todo
import rtu.kamilla.data.TodoDAO

class MainActivity : AppCompatActivity() {

    private lateinit var todoDAO: TodoDAO
    private lateinit var adapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoDAO = TodoDAO(this)

        val listView: ListView = findViewById(R.id.listViewTasks)
        val editTextTask: EditText = findViewById(R.id.editTextTask)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)

        val todos = todoDAO.getAllTodos().toMutableList()
        adapter = TodoAdapter(this, todos, todoDAO)
        listView.adapter = adapter

        buttonAdd.setOnClickListener {
            val task = editTextTask.text.toString()
            if (task.isNotEmpty()) {
                val todo = todoDAO.createTodo(task)
                adapter.add(todo)
                editTextTask.text.clear()
            }
        }
    }
}
