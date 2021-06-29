package br.com.imiranda.dados
import  android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Configuracao(val numeroDado:Int=1, val numeroFaces:Int=6):Parcelable
