package br.com.imiranda.contato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.imiranda.contato.databinding.ActivityContatoBinding;

public class ContatoActivity extends AppCompatActivity {
    private static final int PERMISSAO_ESCRITA_ARMAZENAMENTO_EXTERNO = 0;
    private ActivityContatoBinding activityContatoBinding;
    private Contato contato;
    private int posicao = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityContatoBinding = activityContatoBinding.inflate(getLayoutInflater());
        setContentView(activityContatoBinding.getRoot());
        //Verificar se algum contato foi receido
        contato = (Contato) getIntent().getSerializableExtra(Intent.EXTRA_USER);
        if (contato != null){
            posicao = getIntent().getIntExtra(Intent.EXTRA_INDEX,-1);
            boolean ativo = (posicao != -1)?true:false;
            alterarAtivacaoViews(ativo);

            if (ativo){
                getSupportActionBar().setTitle("Edição Contato");

            }else{
                getSupportActionBar().setTitle("Detalhes Contato");
            }
            //usando dados do contato para preencher a view
            activityContatoBinding.nomeEt.setText(contato.getNome());
            activityContatoBinding.emailEt.setText(contato.getEmail());
            activityContatoBinding.telefoneEt.setText(contato.getTelefone());
            activityContatoBinding.telefoneComercialSw.setChecked(contato.isTelefoneComercial());
            activityContatoBinding.telefoneCelularEt.setText(contato.getTelefoneCelular());
            activityContatoBinding.siteEt.setText(contato.getEmail());
        }else{
            getSupportActionBar().setTitle("Novo Contato");
        }
    }
    private void alterarAtivacaoViews(boolean ativo){
        activityContatoBinding.nomeEt.setEnabled(ativo);
        activityContatoBinding.emailEt.setEnabled(ativo);
        activityContatoBinding.telefoneEt.setEnabled(ativo);
        activityContatoBinding.telefoneComercialSw.setEnabled(ativo);
        activityContatoBinding.telefoneCelularEt.setEnabled(ativo);
        activityContatoBinding.siteEt.setEnabled(ativo);
    }

    public void onClick(View view){
        contato = new Contato(
                activityContatoBinding.nomeEt.getText().toString(),
                activityContatoBinding.emailEt.getText().toString(),
                activityContatoBinding.telefoneEt.getText().toString(),
                activityContatoBinding.telefoneComercialSw.isChecked(),
                activityContatoBinding.telefoneCelularEt.getText().toString(),
                activityContatoBinding.siteEt.getText().toString()
        );

        switch (view.getId()){
            case R.id.salvarBt:
                Intent retornoIntent = new Intent();
                retornoIntent.putExtra(Intent.EXTRA_USER, contato);
                retornoIntent.putExtra(Intent.EXTRA_INDEX, posicao);
                setResult(RESULT_OK, retornoIntent);
                finish();
                break;
            case R.id.gerarPdfBt:
                gerarDocumentPdf();
                break;
            default:
                break;
        }
    }

    private void verifyPermissaoEscritaAmarzanamentoExterno(){
        Intent ligarIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contato.getTelefone()));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSAO_ESCRITA_ARMAZENAMENTO_EXTERNO);
            } else {
               verifyPermissaoEscritaAmarzanamentoExterno();
            }
        }
        else{
            gerarDocumentPdf();
        }
    }

    private void gerarDocumentPdf() {
        //pegar a altura e largura da view raiz para gerar iamgem do pdf
        View conteudo = activityContatoBinding.getRoot();
        int largura = conteudo.getWidth();
        int altura = conteudo.getHeight();
        PdfDocument document = new PdfDocument();

        //criar configura de uma pagina e iniciando uma pagina a partir da configuracao
        PdfDocument.PageInfo configuracaoPagina = new PdfDocument.PageInfo.Builder(largura, altura,1).create();
        PdfDocument.Page pagina = document.startPage(configuracaoPagina);
        //criando snapshot da view no pagina pdf
        conteudo.draw(pagina.getCanvas());
        document.finishPage(pagina);

        File diretorioDocumentos = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());

        try {
            File documento = new File (diretorioDocumentos, contato.getNome().replace(" ","_")+ ".pdf");
            documento.createNewFile();
            document.writeTo(new FileOutputStream(documento));
            document.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSAO_ESCRITA_ARMAZENAMENTO_EXTERNO){
            verifyPermissaoEscritaAmarzanamentoExterno();
        }
    }
}