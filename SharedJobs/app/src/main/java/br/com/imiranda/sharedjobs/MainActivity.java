package br.com.imiranda.sharedjobs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import br.com.imiranda.sharedjobs.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private String education;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        //binding views and objects
        activityMainBinding.conclusionYearEt.setVisibility(View.GONE);
        //Listener select view
        activityMainBinding.educationSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                clear();
                if((((TextView) view).getText().equals("Elementary School"))||(((TextView) view).getText().equals("High School"))){
                    activityMainBinding.conclusionYearEt.setVisibility(View.VISIBLE);
                    activityMainBinding.institutionEt.setVisibility(View.GONE);
                    activityMainBinding.monographyTitleEt.setVisibility(View.GONE);
                    activityMainBinding.advisorEt.setVisibility(View.GONE);
                }
                else if((((TextView) view).getText().equals("Graduation"))||(((TextView) view).getText().equals("Specialisation"))){
                    activityMainBinding.institutionEt.setVisibility(View.VISIBLE);
                    activityMainBinding.monographyTitleEt.setVisibility(View.GONE);


                }
                else if((((TextView) view).getText().equals("Masters"))||(((TextView) view).getText().equals("PhD"))){
                    activityMainBinding.institutionEt.setVisibility(View.VISIBLE);
                    activityMainBinding.monographyTitleEt.setVisibility(View.VISIBLE);
                    activityMainBinding.advisorEt.setVisibility(View.VISIBLE);
                }
                education = ((TextView) view).getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        activityMainBinding.educationSp.setVisibility(View.GONE);
        activityMainBinding.educationSp.setVisibility(View.VISIBLE);
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
        activityMainBinding.institutionEt.setText("");
        activityMainBinding.monographyTitleEt.setText("");
        activityMainBinding.advisorEt.setText("");
        activityMainBinding.fullNameEt.setText("");
        activityMainBinding.emailEt.setText("");
        activityMainBinding.notificationsCb.setChecked(false);
        activityMainBinding.phoneEt.setText("");
        activityMainBinding.homeRb.setChecked(false);
        activityMainBinding.comercialRb.setChecked(false);
        activityMainBinding.maleRb.setChecked(false);
        activityMainBinding.femaleRb.setChecked(false);
        activityMainBinding.birthDateEt.setText("");
        activityMainBinding.educationSp.setSelection(0);
        activityMainBinding.conclusionYearEt.setText("");
        activityMainBinding.institutionEt.setText("");
        activityMainBinding.monographyTitleEt.setText("");
        activityMainBinding.advisorEt.setText("");
        activityMainBinding.interestJobsEt.setText("");
    }
    private void saveForm(){
        StringBuffer sumary = new StringBuffer();
        String typeFone = "";
        sumary.append("FullName: ").append(activityMainBinding.fullNameEt.getText().toString()).append("\n");
        sumary.append("Email: ").append(activityMainBinding.emailEt.getText().toString()).append("\n");
        sumary.append("Email Notifications: ").append(activityMainBinding.notificationsCb.isChecked()).append("\n");


        switch (activityMainBinding.typePhoneRg.getCheckedRadioButtonId()){
            case R.id.homeRb:
                typeFone = "Home";
                break;
            case R.id.comercialRb:
                typeFone = "Comercial";
                break;
            default:
                break;
        }

        sumary.append("Phone: ").append(activityMainBinding.phoneEt.getText().toString()+"|"+typeFone).append("\n");
        sumary.append("Birth Date: ").append(activityMainBinding.birthDateEt.getText().toString()).append("\n");
        sumary.append("Education: ").append(education).append("\n");
        sumary.append("Genre: ");
        switch (activityMainBinding.genreRg.getCheckedRadioButtonId()){
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

        sumary.append("Conclusion Year: ").append(activityMainBinding.conclusionYearEt.getText().toString()).append("\n");
        sumary.append("Instituion: ").append(activityMainBinding.institutionEt.getText().toString()).append("\n");
        sumary.append("Monography Title: ").append(activityMainBinding.monographyTitleEt.getText().toString()).append("\n");
        sumary.append("Advisor: ").append(activityMainBinding.advisorEt.getText().toString()).append("\n");
        sumary.append("Interest Jobs: ").append(activityMainBinding.interestJobsEt.getText().toString()).append("\n");

        Toast.makeText(this,sumary.toString(),Toast.LENGTH_SHORT).show();
    }

    private void clear(){
        activityMainBinding.institutionEt.setText("");
        activityMainBinding.monographyTitleEt.setText("");
        activityMainBinding.advisorEt.setText("");
    }

//    private void bindViews(){
//        fullNameEt = findViewById(R.id.fullNameEt);
//        emailEt = findViewById(R.id.emailEt);
//        notificationsCb = findViewById(R.id.notificationsCb);
//        phoneEt = findViewById(R.id.phoneEt );
//        typePhoneRg = findViewById(R.id.typePhoneRg);
//        homeRb = findViewById(R.id.homeRb );
//        comercialRb = findViewById(R.id.comercialRb);
//        genreRg = findViewById(R.id.genreRg);
//        maleRb = findViewById(R.id.maleRb);
//        femaleRb = findViewById(R.id.femaleRb);
//        birthDateEt = findViewById(R.id.birthDateEt);
//        educationSp = findViewById(R.id.educationSp);
//        conclusionYearEt = findViewById(R.id.conclusionYearEt);
//        institutionEt = findViewById(R.id.institutionEt);
//        monographyTitleEt = findViewById(R.id.monographyTitleEt);
//        advisorEt = findViewById(R.id.advisorEt);
//        interestJobsEt = findViewById(R.id.interestJobsEt);
//
//    }
}