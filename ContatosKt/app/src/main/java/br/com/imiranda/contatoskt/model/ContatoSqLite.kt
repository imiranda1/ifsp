package br.com.imiranda.contatoskt.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.com.imiranda.contatoskt.R
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.CONTATO_TABLE
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.CREATE_CONTATO_TABLE_STATEMENT
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.EMAIL_COLUMN
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.FALSO_INTEIRO
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.LISTA_CONTATOS_DATABASE
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.NOME_COLUMN
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.SITE_COLUMN
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.TELEFONE_CELULAR_COLUMN
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.TELEFONE_COLUMN
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.TELEFONE_COMERCIAL_COLUMN
import br.com.imiranda.contatoskt.model.ContatoSqLite.Constantes.VERDADEIRO_INTEIRO
import java.sql.SQLException

class ContatoSqLite(contexto: Context): ContatoDao {
    object Constantes {
        val LISTA_CONTATOS_DATABASE = "listaContatos"
        val CONTATO_TABLE = "contato"
        val NOME_COLUMN = "nome"
        val EMAIL_COLUMN = "email"
        val TELEFONE_COLUMN = "telefone"
        val TELEFONE_COMERCIAL_COLUMN = "telefoneComercial"
        val TELEFONE_CELULAR_COLUMN = "telefoneCelular"
        val SITE_COLUMN = "SITE"

        val CREATE_CONTATO_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS ${CONTATO_TABLE} ("+
        "${NOME_COLUMN} TEXT NOT NULL PRIMARY KEY," +
        "${EMAIL_COLUMN} TEXT NOT NULL," +
        "${TELEFONE_COLUMN} TEXT NOT NULL," +
        "${TELEFONE_COMERCIAL_COLUMN} INT NOT NULL," +
        "${TELEFONE_CELULAR_COLUMN} TEXT NOT NULL," +
        "${SITE_COLUMN} TEXT NOT NULL);"

        //MAPEAMENTO INTEIRO BOOLEANO

        val FALSO_INTEIRO = 0
        val VERDADEIRO_INTEIRO = 1

    }

    //REFERENCIA BANCO DE DADOS

    val listaContatosDb: SQLiteDatabase
    init{
        listaContatosDb = contexto.openOrCreateDatabase(
            LISTA_CONTATOS_DATABASE,
            MODE_PRIVATE,
            null
        )
        try{
            listaContatosDb.execSQL(CREATE_CONTATO_TABLE_STATEMENT)
        }
        catch (se: SQLException){
            Log.e(contexto.getString(R.string.app_name), se.toString())
        }
    }

    private fun contentValuesFromContato(contato: Contato, includePrimaryKey: Boolean): ContentValues{
        val valores = ContentValues()
        with(contato){
            if (includePrimaryKey){
                valores.put(NOME_COLUMN, nome)
            }

            valores.put(EMAIL_COLUMN, email)
            valores.put(TELEFONE_COLUMN,telefone)
            valores.put(TELEFONE_COMERCIAL_COLUMN,if(telefoneComercial) VERDADEIRO_INTEIRO else FALSO_INTEIRO)
            valores.put(TELEFONE_CELULAR_COLUMN,telefoneCelular)
            valores.put(SITE_COLUMN,site)
        }
        return valores
    }

    override fun createContato(contato: Contato) {

        listaContatosDb.insert(CONTATO_TABLE,null, contentValuesFromContato(contato, true))
    }

    override fun readContato(nome: String): Contato {
        //usando função query
        val contatoCursor = listaContatosDb.query(
            true,
            CONTATO_TABLE,
            null,
            "${NOME_COLUMN} = ?",
            arrayOf(nome),
            null,
            null,
            null,
            null,
        )
        return if(contatoCursor.moveToFirst()){
            contatoFromCursor(contatoCursor)
        }else{
            Contato()
        }
    }

    private fun contatoFromCursor (contatoCursor: Cursor) :Contato =  with(contatoCursor) {
        Contato(
            getString(contatoCursor.getColumnIndex(NOME_COLUMN)),
            getString(contatoCursor.getColumnIndex(EMAIL_COLUMN)),
            getString(contatoCursor.getColumnIndex(TELEFONE_COLUMN)),
            getInt(contatoCursor.getColumnIndex(TELEFONE_COMERCIAL_COLUMN)) != FALSO_INTEIRO,
            getString(contatoCursor.getColumnIndex(TELEFONE_CELULAR_COLUMN)),
            getString(contatoCursor.getColumnIndex(SITE_COLUMN))

        )
    }

    override fun readContatos(): MutableList<Contato> {
        val contatosList: MutableList<Contato> = mutableListOf()
        val consultaQuery = "SELECT * FROM ${CONTATO_TABLE}"
        val contatoCursor = listaContatosDb.rawQuery(consultaQuery,null)
        while(contatoCursor.moveToNext()){
            contatosList.add(contatoFromCursor(contatoCursor))
        }
        return contatosList
    }

    override fun updateContato(contato: Contato) {
        listaContatosDb.update(
            CONTATO_TABLE,
            contentValuesFromContato(contato, false),
            "${NOME_COLUMN} = ?",
            arrayOf(contato.nome)
        )
    }

    override fun deleteContato(nome: String) {
        listaContatosDb.delete(
            CONTATO_TABLE,
            "${NOME_COLUMN} = ?",
            arrayOf(nome)
        )
    }
}