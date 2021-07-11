package br.com.imiranda.contatoskt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.imiranda.contatoskt.R
import br.com.imiranda.contatoskt.model.Contato

class ContatosAdapter(private val contatosList: MutableList<Contato>,
                      private val onContatoClickListener: OnContatoClickListener
                      ):RecyclerView.Adapter<ContatosAdapter.ContatoViewHolder>() {
                          inner class ContatoViewHolder(viewContato: View): RecyclerView.ViewHolder(viewContato){
                              val nomeTv: TextView = viewContato.findViewById(R.id.nomeContatoTv)
                              val emailTv: TextView = viewContato.findViewById(R.id.emailContatoTv)
                          }
    //chamada pelo layout manager para criar uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatoViewHolder {
        //val viewContatoBinding: ViewContatoBinding = ViewContatoBinding.inflate(LayoutInflater.from(parent.context))
        val viewContato: View = LayoutInflater.from(parent.context).inflate(R.layout.view_contato,parent,false)
        return ContatoViewHolder(viewContato)
        //return ContatoViewHolder(viewContatoBinding.root)
    }
    //chamada atualizar os valores de uma célula ou view
    override fun onBindViewHolder(holder: ContatoViewHolder, position: Int) {
        val contato: Contato = contatosList[position]
        holder.nomeTv.text = contato.nome
        holder.emailTv.text = contato.email
        holder.itemView.setOnClickListener{
            onContatoClickListener.onContatoClick(position)
        }
    }

//    override fun getItemCount(): Int {
//        return contatosList.size
//    }
    //single expression function
    override fun getItemCount(): Int = contatosList.size



}