package br.com.imiranda.sharedjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private EditText fullNameEt;
    private EditText emailEt;
    private CheckBox notificationsCb;
    private EditText phoneEt;
    private RadioGroup typePhoneRg;
    private RadioButton homeRb;
    private RadioButton comercialRb;
    private RadioGroup genreRg;
    private RadioButton maleRb;
    private RadioButton femaleRb;
    private EditText birthDateEt;
    private Spinner educationSp;
    private EditText conclusionYearEt;
    private EditText institutionEt;
    private EditText monographyTitleEt;
    private EditText interestJobsEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private void bindViews(){
        fullNameEt = findViewById(R.id.fullNameEt);
        emailEt = findViewById(R.id.emailEt);
        notificationsCb = findViewById(R.id.notificationsCb);
        phoneEt = findViewById(R.id.phoneEt );
        typePhoneRg = findViewById(R.id.typePhoneRg);
        homeRb = findViewById(R.id.homeRb );
        comercialRb = findViewById(R.id.comercialRb);
        genreRg = findViewById(R.id.genreRg);
        maleRb = findViewById(R.id.maleRb);
        femaleRb = findViewById(R.id.femaleRb);
        birthDateEt = findViewById(R.id.birthDateEt);
        educationSp = findViewById(R.id.educationSp);
        conclusionYearEt = findViewById(R.id.conclusionYearEt);
        institutionEt = findViewById(R.id.institutionEt);
        monographyTitleEt = findViewById(R.id.monographyTitleEt);
        interestJobsEt = findViewById(R.id.interestJobsEt);

    }
}