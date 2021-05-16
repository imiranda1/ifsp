package br.com.imiranda.contato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import br.com.imiranda.contato.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private Contato contato;
    private final int PERMISSAO_LIGACAO_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    public void onClick(View view){
        contato = new Contato(
                activityMainBinding.nomeEt.getText().toString(),
                activityMainBinding.emailEt.getText().toString(),
                activityMainBinding.telefoneEt.getText().toString(),
                activityMainBinding.telefoneComercialSw.isChecked(),
                activityMainBinding.telefoneCelularEt.getText().toString(),
                activityMainBinding.siteEt.getText().toString()
        );

        switch (view.getId()){
            case R.id.salvarBt:
                break;
            case R.id.enviarEmailBt:
                Intent enviarEmailIntent = new Intent (Intent.ACTION_SENDTO);
                enviarEmailIntent.setData(Uri.parse("mailto"));
                enviarEmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{contato.getEmail()});
                enviarEmailIntent.putExtra(Intent.EXTRA_SUBJECT,"Contato");
                enviarEmailIntent.putExtra(Intent.EXTRA_TEXT, contato.toString());
                startActivity(enviarEmailIntent);
                break;
            case R.id.chamarTelefoneBt:
                verifyCallPhonePermissionAndCall();
                break;
            case R.id.acessarSiteBt:
                Intent acessarSiteIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(contato.getSite()));
                startActivity(acessarSiteIntent);
                break;
            case R.id.gerarPdfBt:
                break;
            default:
                break;
        }
    }
    private void verifyCallPhonePermissionAndCall(){
        Intent ligarIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + contato.getTelefone()));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},PERMISSAO_LIGACAO_REQUEST_CODE);
            } else {
                startActivity(ligarIntent);
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
        if(requestCode==PERMISSAO_LIGACAO_REQUEST_CODE){
            verifyCallPhonePermissionAndCall();
        }
    }

}