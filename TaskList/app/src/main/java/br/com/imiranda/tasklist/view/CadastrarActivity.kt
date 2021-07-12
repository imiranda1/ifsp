package br.com.imiranda.tasklist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.imiranda.tasklist.AutenticacaoFirebase
import br.com.imiranda.tasklist.databinding.ActivityCadastrarBinding

class CadastrarActivity : AppCompatActivity() {
    private lateinit var activityCadastrarBinding : ActivityCadastrarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCadastrarBinding = ActivityCadastrarBinding.inflate(layoutInflater)
        setContentView(activityCadastrarBinding.root)
    }
    

    fun onClick(view: View){
        if (view == activityCadastrarBinding.cadastrarBt){
            val email = activityCadastrarBinding.emailEt.text.toString()
            val senha = activityCadastrarBinding.senhaEt.text.toString()
            val repetirSenha = activityCadastrarBinding.repetirSenhaEt.text.toString()

            if (senha.equals(repetirSenha)) {
                AutenticacaoFirebase.firebaseAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener { cadastro ->
                    if (cadastro.isSuccessful) {
                        Toast.makeText(this, "$email cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this, "Falha ao Cadastrar $email", Toast.LENGTH_SHORT).show()

                    }

                }
            }
            else{
                Toast.makeText(this, "Senhas não são iguais", Toast.LENGTH_SHORT).show()
            }
        }
    }
}