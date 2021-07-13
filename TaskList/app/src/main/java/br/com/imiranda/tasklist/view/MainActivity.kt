package br.com.imiranda.tasklist.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private lateinit var taskList: MutableList<Task>
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var taskLayoutManager: LinearLayoutManager
    private lateinit var taskController: TaskController
    private lateinit var novaTaskLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        taskController = TaskController(this)
        taskList = mutableListOf()
        taskList = taskController.buscaTasks()
//      for (i in 1..5){
//            taskList.add(
//                Task(
//                "Nome $i",
//                "Email $i",
//                "Telefone $i",
//                "Celular $i",
//                "Site $i",
//                    "b"
//            ))
//       }


        taskLayoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(taskList,this)
        activityMainBinding.tasksRv.adapter= taskAdapter
        activityMainBinding.tasksRv.layoutManager = taskLayoutManager

        //registrando um activity call back para resiltado de uma activity
        //activityresultContract
        novaTaskLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activitResult ->
            if( activitResult.resultCode == RESULT_OK){
                val task :Task? = activitResult.data?.getParcelableExtra<Task>(Intent.EXTRA_USER)
                if (task != null){
                    taskList.add(task)
                    taskAdapter.notifyDataSetChanged()

                    //inserindo task no banco
                   taskController.insereTask(task)
                }
            }
        }
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

}