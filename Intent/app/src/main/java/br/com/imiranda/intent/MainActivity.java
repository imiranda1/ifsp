package br.com.imiranda.intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.imiranda.intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;

    //constantes para passagem de parametro e retorno

    public static final String PARAMETRO = "PARAMETRO";

    //request code
    private   final int OUTRA_ACTIVITY_REQUEST_CODE = 0;
    private   final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;
    private   final int PICK_IMAGE_FILE_REQUEST_CODE = 2;
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
                //Intent outraActivityIntent = new Intent(this, OutraActivity.class);
                Intent outraActivityIntent = new Intent("RECEBER_E_RETORNAR_ACTION");
                //#1 de passagem de parâmetros
                //Bundle parametros = new Bundle();
                //parametros.putString(PARAMETRO,activityMainBinding.parameterEt.getText().toString());
                //outraActivityIntent.putExtras(parametros);

                //#2 de passagem de parâmetros
                outraActivityIntent.putExtra(PARAMETRO,activityMainBinding.parameterEt.getText().toString());
                startActivityForResult(outraActivityIntent, OUTRA_ACTIVITY_REQUEST_CODE);

                return true;
            case R.id.viewMi:

                //Abrindo navegador
                Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW);
               // abrirNavegadorIntent.setData(Uri.parse("http://scl.ifsp.edu.br"));
                abrirNavegadorIntent.setData(Uri.parse(activityMainBinding.parameterEt.getText().toString()));
                startActivity(abrirNavegadorIntent);
                return true;
            case R.id.callMi:
                verifyCallPhonePermission();
                return true;
            case R.id.dialMi:
                Intent discarIntent = new Intent(Intent.ACTION_DIAL);
                discarIntent.setData(Uri.parse("tel:" +activityMainBinding.parameterEt.getText().toString()));
                startActivity(discarIntent);
                return true;
            case R.id.pickMi:
                startActivityForResult(getPickImageIntent(), PICK_IMAGE_FILE_REQUEST_CODE);
                return true;
            case R.id.chooserMi:
                //forçar  o usuário escolha entre uma lista de aplicativos memos já que exista um
                //app padrao
                Intent escolherActivityIntent = new Intent(Intent.ACTION_CHOOSER);
                escolherActivityIntent.putExtra(Intent.EXTRA_INTENT, getPickImageIntent());
                escolherActivityIntent.putExtra(Intent.EXTRA_TITLE,"Escolha um APP");
                startActivityForResult(escolherActivityIntent, PICK_IMAGE_FILE_REQUEST_CODE);
                return true;



        }
        return super.onOptionsItemSelected(item);
    }

    private Intent getPickImageIntent(){
        Intent pegarImagemIntent = new Intent(Intent.ACTION_PICK);
        String diretorioImagens = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
        pegarImagemIntent.setDataAndType(Uri.parse(diretorioImagens),"image/*");
        return pegarImagemIntent;
    }

    private void verifyCallPhonePermission(){
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel:" + activityMainBinding.parameterEt.getText().toString()));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                startActivity(ligarIntent);
            } else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE_PERMISSION_REQUEST_CODE);



            }
        }
        else{
            //a permissão já foi solicitada, android < M
            startActivity(ligarIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CALL_PHONE_PERMISSION_REQUEST_CODE){
            if(permissions[0].equals(Manifest.permission.CALL_PHONE) && grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Não Permitido", Toast.LENGTH_SHORT).show();

            }else{

                verifyCallPhonePermission();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this,"Voltou", Toast.LENGTH_SHORT).show();
        //recebendo o retorno da OutraActivity
        if(requestCode == OUTRA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            String retorno = data.getStringExtra(OutraActivity.RETORNO);
            if(retorno != null){
                activityMainBinding.returnTv.setText(retorno);
            }
        }else{
            if(requestCode == PICK_IMAGE_FILE_REQUEST_CODE && resultCode == RESULT_OK){
                Uri imagemUri = data.getData();
                Toast.makeText(this, imagemUri.toString(), Toast.LENGTH_SHORT).show();
                Intent visualizarImagem = new Intent(Intent.ACTION_VIEW,imagemUri);
                startActivity(visualizarImagem);
            }
        }
    }
}