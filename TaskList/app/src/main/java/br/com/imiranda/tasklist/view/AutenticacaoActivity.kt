package br.com.imiranda.tasklist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.com.imiranda.tasklist.AutenticacaoFirebase
import br.com.imiranda.tasklist.R
import br.com.imiranda.tasklist.databinding.ActivityAutenticacaoBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class AutenticacaoActivity : AppCompatActivity() {
    private lateinit var activityAutenticacaoBinding: ActivityAutenticacaoBinding

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAutenticacaoBinding = ActivityAutenticacaoBinding.inflate(layoutInflater)
        setContentView(activityAutenticacaoBinding.root)

        //Instanciando objetos de signin

        AutenticacaoFirebase.googleSignInOptions = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        //Cliente a partir das options

        AutenticacaoFirebase.googleSignInClient = GoogleSignIn.getClient(this, AutenticacaoFirebase.googleSignInOptions!!)
        //Buscar a ultima conta google autenticada
        AutenticacaoFirebase.googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this)

        //se ja autenticaram com a conta google e a conta permanece
        if(AutenticacaoFirebase.googleSignInAccount != null){
            Toast.makeText(this, "Usuário autenticado com sucesso", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, TaskActivity::class.java))
            finish()
        }

        activityAutenticacaoBinding.entrarGoogleBr.setOnClickListener{
            //Abrir activity de sign in do google
            val googleSignInIntent = AutenticacaoFirebase.googleSignInClient?.signInIntent
            googleSignInLauncher.launch(googleSignInIntent)
        }

        googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            AutenticacaoFirebase.googleSignInAccount = task.getResult(ApiException::class.java)

            //extraindo credencial da conta google para autenticar no firebase
            val credencial: AuthCredential =
                GoogleAuthProvider.getCredential(AutenticacaoFirebase.googleSignInAccount?.idToken, null)

            // Usar credencial para autenticar no firebase
            AutenticacaoFirebase.firebaseAuth.signInWithCredential(credencial)
                .addOnSuccessListener {
                    Toast.makeText(this, "Usuário ${AutenticacaoFirebase.googleSignInAccount?.email}autenticado com sucesso", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Erro na Autenticação", Toast.LENGTH_SHORT).show()
                }
        }
    }
    fun onClick(view: View){
        when(view){
            activityAutenticacaoBinding.cadastrarBt -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            activityAutenticacaoBinding.entrarBt -> {
                val email: String
                val senha: String
                with(activityAutenticacaoBinding){
                    email = emailEt.text.toString()
                    senha = senhaEt.text.toString()
                }
                AutenticacaoFirebase.firebaseAuth.signInWithEmailAndPassword(email,senha)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Usuário autenticado com sucesso", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }.addOnFailureListener{
                        Toast.makeText(this, "Usuário/senha inválidos", Toast.LENGTH_SHORT).show()

                    }

            }
            activityAutenticacaoBinding.recuperarSenhaBt -> {
                startActivity(Intent(this, RecuperarSenhaActivity::class.java))
            }
        }
    }
    override fun onStart() {
        super.onStart()
        if(AutenticacaoFirebase.firebaseAuth.currentUser != null){
            Toast.makeText(this, "Usuário já autenticado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))

        }
    }
}