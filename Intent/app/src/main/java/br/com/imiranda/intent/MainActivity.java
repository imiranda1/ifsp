package br.com.imiranda.intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import br.com.imiranda.intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    //constantes para passagem de parametro e retorno

    public static final String PARAMETRO = "PARAMETRO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        getSupportActionBar().setTitle("Tratando Intents");
        getSupportActionBar().setSubtitle("SubTítulo");
        setContentView(activityMainBinding.getRoot());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.outraActivityMi:
                //abrir activity
                Intent outraActivityIntent = new Intent(this, OutraActivity.class);

                //#1 de passagem de parâmetros
                Bundle parametros = new Bundle();
                parametros.putString(PARAMETRO,activityMainBinding.parameterEt.getText().toString());
                outraActivityIntent.putExtras(parametros);

                startActivity(outraActivityIntent);

                return true;
            case R.id.viewMi:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}