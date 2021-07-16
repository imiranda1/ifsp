package br.com.imiranda.tasklist.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import br.com.imiranda.tasklist.AutenticacaoFirebase
import br.com.imiranda.tasklist.databinding.ActivityTaskBinding
import br.com.imiranda.tasklist.model.Task
import java.util.*


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
                tituloEt.text.toString().uppercase(),
                descricaoEt.text.toString(),
                getDataAtual(),
                dataConclusaoEt.text.toString(),
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

    fun getDataAtual():String {
        val currentTime = Calendar.getInstance().time.toString()
        return currentTime
    }

}