package br.com.imiranda.tasklist.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val titulo: String = "",
    var descricao: String = "",
    val dataCriacao: String = "",
    val dataPrevistaExecucao: String = "",
    val usuarioCriador: String = "",
    val usuarioExecutor: String = ""

): Parcelable