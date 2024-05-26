package rtu.kamilla.data

data class Todo(
    var id: Long = 0,
    var task: String,
    var completed: Boolean = false
)
