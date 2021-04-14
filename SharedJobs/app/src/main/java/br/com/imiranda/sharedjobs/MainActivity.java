package br.com.imiranda.sharedjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

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
    private EditText advisorEt;
    private EditText interestJobsEt;
    private String education = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding views and objects
        bindViews();
        conclusionYearEt.setVisibility(View.GONE);
        //Listener select view
        educationSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clear();
                if((((TextView) view).getText().equals("Elementary School"))||(((TextView) view).getText().equals("High School"))){
                    conclusionYearEt.setVisibility(View.VISIBLE);
                    institutionEt.setVisibility(View.GONE);
                    monographyTitleEt.setVisibility(View.GONE);
                    advisorEt.setVisibility(View.GONE);
                }
                else if((((TextView) view).getText().equals("Graduation"))||(((TextView) view).getText().equals("Specialisation"))){
                    institutionEt.setVisibility(View.VISIBLE);
                    monographyTitleEt.setVisibility(View.GONE);


                }
                else if((((TextView) view).getText().equals("Masters"))||(((TextView) view).getText().equals("PhD"))){
                    institutionEt.setVisibility(View.VISIBLE);
                    monographyTitleEt.setVisibility(View.VISIBLE);
                    advisorEt.setVisibility(View.VISIBLE);
                }
                education = ((TextView) view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        educationSp.setVisibility(View.GONE);
        educationSp.setVisibility(View.VISIBLE);
    }

    public void onClickButton(View view){
        switch (view.getId()){
            case R.id.saveBt:
                saveForm();
                break;
            case R.id.clearBt:
                cleanForm();
                break;
            default:
                break;
        }
    }
    private void cleanForm(){
        institutionEt.setText("");
        monographyTitleEt.setText("");
        advisorEt.setText("");
        fullNameEt.setText("");
        emailEt.setText("");
        notificationsCb.setChecked(false);
        phoneEt.setText("");
        homeRb.setChecked(false);
        comercialRb.setChecked(false);
        maleRb.setChecked(false);
        femaleRb.setChecked(false);
        birthDateEt.setText("");
        educationSp.setSelection(0);
        conclusionYearEt.setText("");
        institutionEt.setText("");
        monographyTitleEt.setText("");
        advisorEt.setText("");
        interestJobsEt.setText("");
    }
    private void saveForm(){
        StringBuffer sumary = new StringBuffer();
        String typeFone = "";
        sumary.append("FullName: ").append(fullNameEt.getText().toString()).append("\n");
        sumary.append("Email: ").append(emailEt.getText().toString()).append("\n");
        sumary.append("Email Notifications: ").append(notificationsCb.isChecked()).append("\n");


        switch (typePhoneRg.getCheckedRadioButtonId()){
            case R.id.homeRb:
                typeFone = "Home";
                break;
            case R.id.comercialRb:
                typeFone = "Comercial";
                break;
            default:
                break;
        }
        sumary.append("Phone: ").append(phoneEt.getText().toString()+"|"+typeFone).append("\n");
        sumary.append("Birth Date: ").append(birthDateEt.getText().toString()).append("\n");
        sumary.append("Education: ").append(education).append("\n");
        sumary.append("Genre: ");
        switch (genreRg.getCheckedRadioButtonId()){
            case R.id.maleRb:
                sumary.append("Male");
                break;
            case R.id.femaleRb:
                sumary.append("Female");
                break;
            default:
                break;
        }
        sumary.append("\n");

        sumary.append("Conclusion Year: ").append(conclusionYearEt.getText().toString()).append("\n");
        sumary.append("Instituion: ").append(institutionEt.getText().toString()).append("\n");
        sumary.append("Monography Title: ").append(monographyTitleEt.getText().toString()).append("\n");
        sumary.append("Advisor: ").append(advisorEt.getText().toString()).append("\n");
        sumary.append("Interest Jobs: ").append(interestJobsEt.getText().toString()).append("\n");

        Toast.makeText(this,sumary.toString(),Toast.LENGTH_SHORT).show();
    }

    private void clear(){
        institutionEt.setText("");
        monographyTitleEt.setText("");
        advisorEt.setText("");
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
        advisorEt = findViewById(R.id.advisorEt);
        interestJobsEt = findViewById(R.id.interestJobsEt);

    }
}