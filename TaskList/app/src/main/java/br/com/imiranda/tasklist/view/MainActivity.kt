package br.com.imiranda.tasklist.view

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.AsyncTask
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.imiranda.tasklist.AutenticacaoFirebase
import br.com.imiranda.tasklist.R
import br.com.imiranda.tasklist.adpter.OnTaskClickListener
import br.com.imiranda.tasklist.adpter.TaskAdapter
import br.com.imiranda.tasklist.controller.TaskController
import br.com.imiranda.tasklist.databinding.ActivityMainBinding
import br.com.imiranda.tasklist.model.Task


class MainActivity : AppCompatActivity(),OnTaskClickListener{
    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var taskList: MutableList<Task>
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskLayoutManager: LinearLayoutManager
    private lateinit var taskController: TaskController
    private lateinit var novaTaskLauncher: ActivityResultLauncher<Intent>
    private lateinit var editTaskLauncher: ActivityResultLauncher<Intent>
    private lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        taskController = TaskController(this)
        taskList = mutableListOf()

        taskList = taskController.buscaTasks()

        taskLayoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList,this,menuInflater)
        activityMainBinding.tasksRv.adapter= taskAdapter
        activityMainBinding.tasksRv.layoutManager = taskLayoutManager

        //registrando um activity call back para resiltado de uma activity
        //activityresultContract
        novaTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activitResult ->
            if( activitResult.resultCode == RESULT_OK){
                val task :Task? = activitResult.data?.getParcelableExtra<Task>(Intent.EXTRA_USER)
                if (task != null){
                    atualizaTaskList(task)

                    //inserindo task no banco
                   taskController.insereTask(task)
                }
            }
        }

        editTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activitResult ->
            if( activitResult.resultCode == RESULT_OK){
                val task :Task? = activitResult.data?.getParcelableExtra<Task>(Intent.EXTRA_USER)
                if (task != null){
                    taskAdapter.getPosicao()
                    taskList.set(taskAdapter.getPosicao(), task)
                    taskAdapter.notifyDataSetChanged()
                    taskController.atualizaTask(task)

                }
            }
        }
    }

    fun atualizaTaskList(task: Task) {
        if(task.titulo.isNotEmpty()){
            taskList.add(task)
            taskAdapter.notifyDataSetChanged()
        }
    }


    fun atualizaAdapter() = taskAdapter.notifyDataSetChanged()


    override fun onResume() {
        super.onResume()
        taskList = taskController.buscaTasks()
        taskAdapter.notifyDataSetChanged()
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//    }



    override fun onStart() {
        super.onStart()
        if(AutenticacaoFirebase.firebaseAuth.currentUser == null){
            finish()
        }
    }

    override fun onTaskClick(posicao: Int) {
        val task: Task = taskList[posicao]
        Toast.makeText(this,task.toString(), Toast.LENGTH_SHORT ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.novaTaskMi -> {
            val novaTaskIntent: Intent = Intent(this, TaskActivity::class.java)
            novaTaskLauncher.launch(novaTaskIntent)
            true
        }
        R.id.sairMi -> {
            AutenticacaoFirebase.firebaseAuth.signOut()
            AutenticacaoFirebase.googleSignInClient?.signOut()
            finish()
            true
        }
        else -> {
            false
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        task = taskList.get(taskAdapter.getPosicao())
        val posicao: Int = taskAdapter.getPosicao()

        when(item.itemId){
            R.id.concluirMi -> {
                if(task.usuarioExecutor.equals("")){
                    task.usuarioExecutor = AutenticacaoFirebase.firebaseAuth.currentUser?.email.toString()
                    taskAdapter.notifyDataSetChanged()
                    taskController.atualizaTask(task)
                    Toast.makeText(this, "TASK CONCLUIDA " +task.titulo, Toast.LENGTH_SHORT).show()
                    return true
                }else{
                    Toast.makeText(this, "A ${task.titulo} já se encontra concluída", Toast.LENGTH_SHORT).show()
                    return false
                }

            }

            R.id.removerMi -> {
                if(task.usuarioExecutor.equals("")){
                    Toast.makeText(this, "TASK REMOVIDA", Toast.LENGTH_SHORT).show()
                    taskList.remove(task)
                    taskAdapter.notifyDataSetChanged()
                    taskController.removeTask(task.titulo)
                    return true
                }else{
                    Toast.makeText(this, "Task concluída não pode ser removida" +task.titulo, Toast.LENGTH_SHORT).show()
                    return true
                }

            }

            R.id.editarMi -> {
                if(task.usuarioExecutor.equals("")) {
                    val editTaskIntent = Intent(this, EditTaskActivity::class.java)
                    editTaskIntent.putExtra(Intent.EXTRA_USER, task)
                    editTaskLauncher.launch(editTaskIntent)
                }else{
                    Toast.makeText(this, "Task concluída não pode ser editada" +task.titulo, Toast.LENGTH_SHORT).show()
                }
                return true
            }

        }
        return false
    }

}