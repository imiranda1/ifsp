package br.com.imiranda.tasklist.controller

import br.com.imiranda.tasklist.model.Task
import br.com.imiranda.tasklist.model.TaskDAO
import br.com.imiranda.tasklist.model.TaskFirebase
import br.com.imiranda.tasklist.view.MainActivity

class TaskController(mainActivity: MainActivity) {

    val taskDao: TaskDAO
    init{
        taskDao = TaskFirebase()
    }

    fun insereTask(task: Task) = taskDao.createTask(task)
    fun buscaTask(titulo: String) = taskDao.readTask(titulo)
    fun buscaTasks() = taskDao.readTasks()
    fun atualizaTask(task: Task) = taskDao.updateTask(task)
    fun removeTask(titulo: String) = taskDao.deleteTask(titulo)
}