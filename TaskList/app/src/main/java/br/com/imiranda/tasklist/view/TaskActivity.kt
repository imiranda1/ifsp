package br.com.imiranda.tasklist.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import br.com.imiranda.tasklist.AutenticacaoFirebase
import br.com.imiranda.tasklist.databinding.ActivityTaskBinding
import br.com.imiranda.tasklist.model.Task
import java.time.LocalDateTime


class TaskActivity : AppCompatActivity() {
    private lateinit var activityTaskBinding: ActivityTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTaskBinding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(activityTaskBinding.root)
    }

    fun onClick(view: View){
        val task: Task
        with(activityTaskBinding){
            task = Task(
                tituloEt.text.toString(),
                descricaoEt.text.toString(),
                "",
                "12-07-2021",
                AutenticacaoFirebase.firebaseAuth.currentUser?.email.toString(),
                ""
            )
        }




        if(view == activityTaskBinding.salvarBt){
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, task)
            setResult(RESULT_OK,retornoIntent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        if(AutenticacaoFirebase.firebaseAuth.currentUser == null){
            finish()
        }
    }

}