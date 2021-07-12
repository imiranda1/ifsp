package br.com.imiranda.tasklist.model

interface TaskDAO {
    fun createTask(task: Task)
    fun readTask(titulo: String):Task
    fun readTasks(): MutableList<Task>
    fun updateTask(task: Task)
    fun deleteTask(titulo: String)
}
