package br.com.imiranda.ciclopdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import br.com.imiranda.ciclopdm.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //constante filtro LogCat
    private final String CICLO_PDM_TAG = "CICLO_PDM_TAG";
    private final String TELEFONE = "TELEFONE";
    private ActivityMainBinding activityMainBinding;
    private TextView telefoneTv;
    private EditText telefoneEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());


        telefoneTv = new TextView(this);
        telefoneTv.setText("Telefone");
        telefoneEt = new EditText(this);
        telefoneEt.setInputType(InputType.TYPE_CLASS_PHONE);

        activityMainBinding.linearLayout.addView(telefoneTv);
        activityMainBinding.linearLayout.addView(telefoneEt);


//        if(savedInstanceState  != null){
//            telefoneEt.setText(savedInstanceState.getString(TELEFONE,""));
//        }

        Log.v(CICLO_PDM_TAG,"onCreate: starting FULL CICLE");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        telefoneEt.setText(savedInstanceState.getString(TELEFONE,""));

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(CICLO_PDM_TAG,"onStart: starting VISIBLE CICLE");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(CICLO_PDM_TAG,"onResume: starting FOREGROUND CICLE");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(CICLO_PDM_TAG,"onPause: ending FOREGROUND CICLE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(CICLO_PDM_TAG,"onStop: ending VISIBLE CICLE");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(CICLO_PDM_TAG,"onDestroy: ending FULL CICLE");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(CICLO_PDM_TAG,"onRestart: preparing to call onStart");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TELEFONE,telefoneEt.getText().toString());
    }
}