package br.com.imiranda.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import br.com.imiranda.intent.databinding.ActivityOutraBinding;

public class OutraActivity extends AppCompatActivity {
    //instancia da classe view bindind
    private ActivityOutraBinding activityOutraBinding;
    //constante retorno

    public static final String RETORNO = "RETORNO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());
        setContentView(activityOutraBinding.getRoot());

        //recebendo parametros pela forma #1
//        Bundle parametros = getIntent().getExtras();
//        if(parametros != null){
//            String parametro = parametros.getString(MainActivity.PARAMETRO,"");
//            activityOutraBinding.recebidoTv.setText(parametro);
//        }
        //recebendo parametros pela forma #2
        String parametro = getIntent().getStringExtra(MainActivity.PARAMETRO);
        if(parametro != null){
            activityOutraBinding.recebidoTv.setText(parametro);
        }

        Log.v(getString(R.string.app_name) + getLocalClassName(),"onCreate: Iniciando ciclo completo");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.v(getString(R.string.app_name) + getLocalClassName(),"onStart: Iniciando ciclo visível");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(getString(R.string.app_name) + getLocalClassName(),"onResume: Iniciando ciclo foreground");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(getString(R.string.app_name) + getLocalClassName(),"onPause: Finalizando ciclo foreground");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(getString(R.string.app_name) + getLocalClassName(),"onStop: Finalizando ciclo visível");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(getString(R.string.app_name) + getLocalClassName(),"onDestroy: Finalizando ciclo completo");

    }
    public void onClick(View view){
        Intent retorno = new Intent();
        retorno.putExtra(RETORNO, activityOutraBinding.retornoEt.getText().toString());
        setResult(RESULT_OK, retorno);
        finish(); //onPause, onStop,onDestroy
    }
}