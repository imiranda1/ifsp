package br.com.imiranda.contatoskt.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.imiranda.contatoskt.AutenticacaoFirebase
import br.com.imiranda.contatoskt.R
import br.com.imiranda.contatoskt.adapter.ContatosAdapter
import br.com.imiranda.contatoskt.adapter.OnContatoClickListener
import br.com.imiranda.contatoskt.controller.ContatoController
import br.com.imiranda.contatoskt.databinding.ActivityMainBinding
import br.com.imiranda.contatoskt.model.Contato

class MainActivity : AppCompatActivity(),OnContatoClickListener{
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var contatosList: MutableList<Contato>
    private lateinit var contatosAdapter: ContatosAdapter
    private lateinit var contatosLayoutManager: LinearLayoutManager
    private lateinit var contatoController: ContatoController
    private val NOVO_CONTATO_REQUEST_CODE: Int = 0
    private lateinit var novoContatoLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        contatoController = ContatoController(this)

        contatosList = contatoController.buscaContatos()
//        for (i in 1..50){
//            contatosList.add(
//                Contato(
//                "Nome $i",
//                "Email $i",
//                "Telefone $i",
//                if(i%2 ==0)false else true,
//                "Celular $i",
//                "Site $i"
//
//            ))
//        }
        contatosLayoutManager = LinearLayoutManager(this)
        contatosAdapter = ContatosAdapter(contatosList,this)
        activityMainBinding.contatosRv.adapter= contatosAdapter
        activityMainBinding.contatosRv.layoutManager = contatosLayoutManager

        //registrando um activity call back para resiltado de uma activity
        //activityresultContract
        novoContatoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activitResult ->
            if( activitResult.resultCode == RESULT_OK){
              val contato:Contato? = activitResult.data?.getParcelableExtra<Contato>(Intent.EXTRA_USER)
                if (contato != null){
                    contatosList.add(contato)
                    contatosAdapter.notifyDataSetChanged()

                    //inserindo contato no banco
                    contatoController.insereContato(contato)
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
    override fun onContatoClick(posicao: Int) {
        val contato:Contato =contatosList[posicao]
        Toast.makeText(this,contato.toString(), Toast.LENGTH_SHORT ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.novoContatoMi -> {
            val novoContatoIntent: Intent = Intent(this, ContatoActivity::class.java)
            novoContatoLauncher.launch(novoContatoIntent)
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