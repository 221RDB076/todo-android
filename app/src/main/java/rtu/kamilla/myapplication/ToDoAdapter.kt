package rtu.kamilla.myapplication

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import rtu.kamilla.data.Todo
import rtu.kamilla.data.TodoDAO

class TodoAdapter(private val context: Context, private val todos: MutableList<Todo>, private val todoDAO: TodoDAO) : BaseAdapter() {

    override fun getCount(): Int = todos.size

    override fun getItem(position: Int): Todo = todos[position]

    override fun getItemId(position: Int): Long = todos[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_todo, parent, false)
        val todo = getItem(position)

        val textViewTask: TextView = view.findViewById(R.id.textViewTask)
        val buttonEdit: Button = view.findViewById(R.id.buttonEdit)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)

        textViewTask.text = todo.task

        buttonEdit.setOnClickListener {
            showEditDialog(todo)
        }

        buttonDelete.setOnClickListener {
            todoDAO.deleteTodo(todo)
            todos.remove(todo)
            notifyDataSetChanged()
        }

        return view
    }

    private fun showEditDialog(todo: Todo) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Edit Task")
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_edit_delete, null)
        val editTextTask = view.findViewById<EditText>(R.id.editTextTask)
        editTextTask.setText(todo.task)
        builder.setView(view)
        builder.setPositiveButton("Update") { dialog, which ->
            val task = editTextTask.text.toString()
            if (task.isNotEmpty()) {
                todo.task = task
                todoDAO.updateTodo(todo)
                notifyDataSetChanged()
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
    fun add(todo: Todo) {
        todos.add(todo)
        notifyDataSetChanged()
    }
}
