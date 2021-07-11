package br.com.imiranda.contatoskt.controller

import br.com.imiranda.contatoskt.model.Contato
import br.com.imiranda.contatoskt.model.ContatoDao
import br.com.imiranda.contatoskt.model.ContatoFirebase
import br.com.imiranda.contatoskt.model.ContatoSqLite
import br.com.imiranda.contatoskt.view.MainActivity

class ContatoController(mainActivity: MainActivity){
    val contatoDao: ContatoDao
    init{
        //contatoDao = ContatoSqLite(mainActivity)
        contatoDao = ContatoFirebase()
    }

    fun insereContato(contato: Contato) = contatoDao.createContato(contato)
    fun buscaContato(nome: String) = contatoDao.readContato(nome)
    fun buscaContatos() = contatoDao.readContatos()
    fun atualizaContato(contato: Contato) = contatoDao.updateContato(contato)
    fun removeContato(nome: String) = contatoDao.deleteContato(nome)


}