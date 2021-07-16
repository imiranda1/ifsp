package br.com.imiranda.tasklist.adpter

import android.graphics.Color
import android.view.*
import android.widget.TextView
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import br.com.imiranda.tasklist.R
import br.com.imiranda.tasklist.model.Task

class TaskAdapter (private val tasksList: MutableList<Task>,
                   private val onTaskClickListener: OnTaskClickListener,
                   private val menuInflater: MenuInflater
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(viewTask: View): RecyclerView.ViewHolder(viewTask), View.OnCreateContextMenuListener{
        val tituloTv: TextView = viewTask.findViewById(R.id.tituloTaskTv)
        val descricaoTv: TextView = viewTask.findViewById(R.id.descricaoTaskTv)
        val conclusaoTv: TextView = viewTask.findViewById(R.id.conclusaoTaskTv)
        init {
            viewTask.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(menu: ContextMenu?,v: View?,menuInfo: ContextMenu.ContextMenuInfo?){
            menuInflater.inflate(R.menu.context_menu, menu)
        }

    }

    private var posicao = 0
    //chamada pelo layout manager para criar uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val viewTask: View = LayoutInflater.from(parent.context).inflate(R.layout.view_task,parent,false)
        return TaskViewHolder(viewTask)
    }
    //chamada atualizar os valores de uma célula ou view
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = tasksList[position]
        holder.tituloTv.text = task.titulo.uppercase()
        holder.descricaoTv.text = task.descricao
        holder.conclusaoTv.text = task.dataPrevistaExecucao


        if(!task.usuarioExecutor.equals("")){
            holder.itemView.setBackgroundColor(Color.parseColor("#2ECC71"))

        }else{
            holder.itemView.setBackgroundColor(Color.parseColor("#FF5733"))
        }
        holder.itemView.setOnClickListener{
            onTaskClickListener.onTaskClick(position)
        }
        //on long click listener
        holder.itemView.setOnLongClickListener { v: View? ->
            posicao = position
            false
        }

    }

    fun getPosicao(): Int {
        return posicao
    }

    override fun getItemCount(): Int = tasksList.size
}