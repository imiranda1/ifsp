package br.com.imiranda.dados

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import br.com.imiranda.dados.SettingsActivity.Constantes.CONFIGURACOES_ARQUIVO
import br.com.imiranda.dados.SettingsActivity.Constantes.NUMERO_DADOS_ATRIBUTO
import br.com.imiranda.dados.SettingsActivity.Constantes.NUMERO_FACES_ATRIBUTO
import br.com.imiranda.dados.SettingsActivity.Constantes.VALOR_NAO_ENCONTRATO
import br.com.imiranda.dados.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var activitySettingsBinding: ActivitySettingsBinding

     object Constantes{
        val CONFIGURACOES_ARQUIVO = "configuracoes"
        val NUMERO_DADOS_ATRIBUTO = "numeroDados"
        val NUMERO_FACES_ATRIBUTO = "numeroFaces"
        val VALOR_NAO_ENCONTRATO = -1
    }
    private lateinit var configuracoesSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySettingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(activitySettingsBinding.root)

        // iniciando ou abrindo arquivo de configurações
        configuracoesSharedPreferences = getSharedPreferences(CONFIGURACOES_ARQUIVO, MODE_PRIVATE)
        carregaConfiguracoes()

        activitySettingsBinding.salvarBt.setOnClickListener{
            val numeroDados: Int = (activitySettingsBinding.numeroDadosSp.selectedView as TextView).text.toString().toInt()
            val numeroFaces: Int = activitySettingsBinding.numeroFacesEt.text.toString().toInt()
            val configuracao= Configuracao(numeroDados,numeroFaces)
            val retornoIntent = Intent()
            retornoIntent.putExtra(Intent.EXTRA_USER,configuracao)
            setResult(RESULT_OK, retornoIntent)

            //salvando as configurações

            salvaConfiguracoes(numeroDados, numeroFaces)
            finish()
        }
    }

    private fun salvaConfiguracoes(numeroDados: Int, numeroFaces: Int) {
        val editorSharedPreferences = configuracoesSharedPreferences.edit()
        editorSharedPreferences.putInt(NUMERO_DADOS_ATRIBUTO, numeroDados)
        editorSharedPreferences.putInt(NUMERO_FACES_ATRIBUTO, numeroFaces)
        editorSharedPreferences.commit()
    }

    private fun carregaConfiguracoes(){
        val numeroDados: Int = configuracoesSharedPreferences.getInt(NUMERO_DADOS_ATRIBUTO, VALOR_NAO_ENCONTRATO)
        val numeroFaces: Int = configuracoesSharedPreferences.getInt(NUMERO_FACES_ATRIBUTO, VALOR_NAO_ENCONTRATO)
        if (numeroDados != VALOR_NAO_ENCONTRATO){
            activitySettingsBinding.numeroDadosSp.setSelection(numeroDados - 1)
        }
        if(numeroFaces != -1){
            activitySettingsBinding.numeroFacesEt.setText(numeroFaces.toString())
        }
    }
}