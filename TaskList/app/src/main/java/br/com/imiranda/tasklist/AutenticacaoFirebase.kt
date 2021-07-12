package br.com.imiranda.tasklist

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

object AutenticacaoFirebase {
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    //Opções
    var googleSignInOptions: GoogleSignInOptions? = null
    var googleSignInClient: GoogleSignInClient? = null
    var googleSignInAccount: GoogleSignInAccount? = null
}