package br.com.imiranda.sompleviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //OBJECTS BINDS VIEWS
    private EditText nameEt;
    private EditText lastNameEt;
    private EditText emailEt;
    private Spinner maritalStatusSp;
    private LinearLayout spouseLl;
    private EditText spouseLastNameEt;
    private EditText spouseNameEt;
    private RadioGroup genreRg;
    private RadioButton maleRb;
    private RadioButton femaleRb;
    private Button saveBt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding
        bindViews();
        maritalStatusSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(((TextView)view).getText().equals("Married")){
                    maritalStatusSp.setVisibility(View.VISIBLE);
                }
                else{
                    maritalStatusSp.setVisibility(View.GONE);
                    spouseNameEt.setText("");
                    spouseLastNameEt.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
//
//        spouseLl.setVisibility(View.GONE);
//        spouseLl.setVisibility(View.VISIBLE);

    }
    private void bindViews(){
        nameEt = findViewById(R.id.nameEt);
        lastNameEt = findViewById(R.id.lastNameEt);
        emailEt = findViewById(R.id.emailEt);
        maritalStatusSp = findViewById(R.id.maritalStatusSp);
        spouseLl = findViewById(R.id.spouseLl);
        spouseLastNameEt = findViewById(R.id.spouseLastNameEt);
        spouseNameEt = findViewById(R.id.spouseNameEt);
        genreRg = findViewById(R.id.genreRg);
        maleRb = findViewById(R.id.maleRb);
        femaleRb =findViewById(R.id.femaleRb);


    }
}