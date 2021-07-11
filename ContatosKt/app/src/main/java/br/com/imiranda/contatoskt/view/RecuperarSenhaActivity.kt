package br.com.imiranda.contatoskt.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.imiranda.contatoskt.AutenticacaoFirebase
import br.com.imiranda.contatoskt.databinding.ActivityAutenticacaoBinding
import br.com.imiranda.contatoskt.databinding.ActivityRecuperarSenhaBinding

class RecuperarSenhaActivity : AppCompatActivity() {
    private lateinit var activityRecuperarSenhaBinding: ActivityRecuperarSenhaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityRecuperarSenhaBinding = ActivityRecuperarSenhaBinding.inflate(layoutInflater)
        setContentView(activityRecuperarSenhaBinding.root)
    }
    fun onClick(view: View) {
        if (view == activityRecuperarSenhaBinding.enviarEmailBt){
            val email = activityRecuperarSenhaBinding.emailRecuperacaoSenhaEt.text.toString()
           AutenticacaoFirebase.firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener {
               Toast.makeText(this, "Email de recuperação de senha enviado para $email", Toast.LENGTH_SHORT).show()
                finish()
           }.addOnFailureListener{
               Toast.makeText(this, "Falha no envio de email de recuperação", Toast.LENGTH_SHORT).show()
           }
        }

    }
}