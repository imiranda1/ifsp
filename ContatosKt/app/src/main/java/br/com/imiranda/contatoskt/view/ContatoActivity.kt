package br.com.imiranda.contatoskt.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.imiranda.contatoskt.AutenticacaoFirebase
import br.com.imiranda.contatoskt.databinding.ActivityContatoBinding
import br.com.imiranda.contatoskt.databinding.ViewContatoBinding
import br.com.imiranda.contatoskt.model.Contato

class ContatoActivity : AppCompatActivity() {
   private lateinit var activityContatoBinding: ActivityContatoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityContatoBinding = ActivityContatoBinding.inflate(layoutInflater)
        setContentView(activityContatoBinding.root)
    }
    fun onClick(view: View){
        val contato: Contato
       with(activityContatoBinding){
           contato = Contato(
               nomeEt.text.toString(),
               emailEt.text.toString(),
               telefoneEt.text.toString(),
               telefoneComercialSw.isChecked,
               telefoneCelularEt.text.toString(),
               siteEt.text.toString()
           )
       }
        if(view == activityContatoBinding.salvarBt){
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER, contato)
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