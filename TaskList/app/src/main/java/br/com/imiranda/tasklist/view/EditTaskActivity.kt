package br.com.imiranda.tasklist.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.imiranda.tasklist.databinding.ActivityTaskEditBinding
import br.com.imiranda.tasklist.model.Task


class EditTaskActivity : AppCompatActivity() {
    private lateinit var activityTaskEditBinding: ActivityTaskEditBinding
    private var task: Task? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTaskEditBinding = ActivityTaskEditBinding.inflate(layoutInflater)
        setContentView(activityTaskEditBinding.root)
        //Verificar se algum contato foi receido
        task = intent.getParcelableExtra(Intent.EXTRA_USER) as Task?

        setTitle("Editar Task")

        activityTaskEditBinding.tituloEt.setText(task?.titulo)
        activityTaskEditBinding.descricaoEt.setText(task?.descricao)
        activityTaskEditBinding.dataCriacaoEt.setText(task?.dataCriacao)
        activityTaskEditBinding.usuarioExecutorEt.setText(task?.usuarioExecutor)
        activityTaskEditBinding.previsaoConclusaoEt.setText(task?.dataPrevistaExecucao)
        activityTaskEditBinding.usuarioCriadorEt.setText(task?.usuarioCriador)

    }



    fun onClick(view: View) {
        if(activityTaskEditBinding.descricaoEt.text.toString() != ""){
            task?.descricao = activityTaskEditBinding.descricaoEt.text.toString()
        }
        if(activityTaskEditBinding.previsaoConclusaoEt.text.toString() != ""){
            task?.dataPrevistaExecucao = activityTaskEditBinding.previsaoConclusaoEt.text.toString()
        }

        val retornoIntent = Intent()
        retornoIntent.putExtra(Intent.EXTRA_USER, task)
        setResult(RESULT_OK,retornoIntent)
        finish()

    }

}