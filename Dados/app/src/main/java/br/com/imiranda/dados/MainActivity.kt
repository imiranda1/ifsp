package br.com.imiranda.dados

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.com.imiranda.dados.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var geradorRandomico: Random

    private lateinit var settingsActivityLauncher: ActivityResultLauncher<Intent>
    private var numberOfDices: Int = 1
    private var numberOfFaces: Int = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        geradorRandomico = Random(System.currentTimeMillis())

        activityMainBinding.jogarDadoBt.setOnClickListener {

            val firstResult: Int = geradorRandomico.nextInt(1..numberOfFaces)
            val msg = "A(s) face(s) sorteada(s) foi(ram) $firstResult".also {
                activityMainBinding.resultadoTv.text = it
            }
            val nomePrimeiraImagem = "dice_$firstResult"

            activityMainBinding.resultadoIv.setImageResource(
                resources.getIdentifier(nomePrimeiraImagem, "mipmap", packageName)
            )

            if (numberOfDices == 2) {
                val secondResult: Int = geradorRandomico.nextInt(1..numberOfFaces)
                "$msg e $secondResult".also { activityMainBinding.resultadoTv.text = it }
                val nomeSegundaImagem = "dice_$secondResult"

                activityMainBinding.resultado2Iv.setImageResource(
                    resources.getIdentifier(nomeSegundaImagem, "mipmap", packageName)
                )
                if (numberOfFaces <= 6) {
                    activityMainBinding.resultado2Iv.visibility = View.VISIBLE
                } else {
                    activityMainBinding.resultadoIv.visibility = View.GONE
                    activityMainBinding.resultado2Iv.visibility = View.GONE
                }
            } else if (numberOfFaces > 6) {
                activityMainBinding.resultadoIv.visibility = View.GONE
                activityMainBinding.resultado2Iv.visibility = View.GONE
            } else {
                activityMainBinding.resultado2Iv.visibility = View.GONE
            }
        }
        settingsActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    val configuracao: Configuracao? = result.data?.getParcelableExtra<Configuracao>(Intent.EXTRA_USER)
                    numberOfDices = configuracao!!.numeroDado
                    numberOfFaces = configuracao!!.numeroFaces
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settingsMi) {
            val settingsIntent = Intent(this, SettingsActivity::class.java)
            settingsActivityLauncher.launch(settingsIntent)
            return true
        }
        return false
    }
}