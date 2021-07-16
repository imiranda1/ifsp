package br.com.imiranda.tasklist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val titulo: String = "",
    var descricao: String = "",
    val dataCriacao: String = "",
    var dataPrevistaExecucao: String = "",
    val usuarioCriador: String = "",
    var usuarioExecutor: String = ""

): Parcelable

