package br.com.imiranda.tasklist.model

import br.com.imiranda.tasklist.model.TaskFirebase.Constantes.LISTA_TAREFAS_DATABASE
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class TaskFirebase:TaskDAO {
    object Constantes {
        val LISTA_TAREFAS_DATABASE = "listaTarefas"
    }

    private val tarefasListDB = Firebase.database.getReference(LISTA_TAREFAS_DATABASE)

    private val taskList: MutableList<Task> = mutableListOf()

    init{
        tarefasListDB.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val newTask: Task = snapshot.getValue<Task>()?:Task()
                if (taskList.indexOfFirst { it.titulo.equals(newTask.titulo) } == -1){
                    taskList.add(newTask)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val taskEditada: Task = snapshot.getValue<Task>()?:Task()
                val indice = taskList.indexOfFirst { it.titulo.equals(taskEditada.titulo) }
                taskList[indice] = taskEditada
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val taskRemovida: Task = snapshot.getValue<Task>()?:Task()
                taskList.remove(taskRemovida)

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                //não se aplica
            }

            override fun onCancelled(error: DatabaseError) {
                //não se aplica
            }

        })
        tarefasListDB.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var contato:Task =  snapshot.getValue<Task>()?:Task()
                taskList.add(contato)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun createTask(task: Task) {
        criaOuAtualizaTask(task)
    }

    override fun readTask(titulo: String): Task {
        return taskList[taskList.indexOfFirst { it.titulo.equals(titulo)  }]
    }

    override fun readTasks(): MutableList<Task> = taskList

    override fun updateTask(task: Task)  = criaOuAtualizaTask(task)

    override fun deleteTask(titulo: String) {
        tarefasListDB.child(titulo).removeValue()
    }

    private fun criaOuAtualizaTask(task: Task) {
        tarefasListDB.child(task.titulo).setValue(task)
    }

}