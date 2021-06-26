package br.com.imiranda.calculadora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import br.com.imiranda.calculadora.databinding.ActivityBasicCalculatorBinding;

public class BasicCalculatorActivity extends AppCompatActivity {
    private ActivityBasicCalculatorBinding activityBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = activityBinding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());

    }
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btZero:
                buildExpression("0", true);
                break;
            case R.id.btOne:
                buildExpression("1", true);
                break;
            case R.id.btTwo:
                buildExpression("2", true);
                break;
            case R.id.btThree:
                buildExpression("3", true);
                break;
            case R.id.btFour:
                buildExpression("4", true);
                break;
            case R.id.btFive:
                buildExpression("5", true);
                break;
            case R.id.btSix:
                buildExpression("6", true);
                break;
            case R.id.btSeven:
                buildExpression("7", true);
                break;
            case R.id.btEight:
                buildExpression("8", true);
                break;
            case R.id.btNine:
                buildExpression("9", true);
                break;
            case R.id.btComma:
                buildExpression(".", true);
                break;
            case R.id.btMult:
                buildExpression("*", false);
                break;
            case R.id.btDiv:
                buildExpression("/", false);
                break;
            case R.id.btSum:
                buildExpression("+", false);
                break;
            case R.id.btSub:
                buildExpression("-", false);
                break;
            case R.id.btClear:
                activityBinding.txtExpression.setText("");
                activityBinding.txtResult.setText("");
                break;
            case R.id.btBackspace:
                String string = activityBinding.txtExpression.getText().toString();
                if(!string.isEmpty()){
                    byte v0 = 0;
                    int v1 = string.length()-1;
                    String expression = string.substring(v0,v1);
                    activityBinding.txtExpression.setText(expression);
                }
                activityBinding.txtResult.setText(" ");
                break;
            case R.id.btEqual:
                long lResult;
                try{

                    Expression expression = new ExpressionBuilder(activityBinding.txtExpression.getText().toString()).build();
                    double result = expression.evaluate();
                    lResult = (long) result;

                    activityBinding.txtExpression.setText(toString());
                    if (result == (double) lResult) {
                        activityBinding.txtResult.setText((CharSequence) String.valueOf(lResult));
                        activityBinding.txtExpression.setText("");

                    }else{
                        activityBinding.txtResult.setText((CharSequence) String.valueOf(result));
                        activityBinding.txtExpression.setText("");

                    }
                }catch (Exception e){

                }

                break;

        }
    }



    public void buildExpression(String string, boolean cleardata){
        if(activityBinding.txtResult.getText().equals("")){
            activityBinding.txtExpression.setText(" ");
        }
        if(cleardata){
            activityBinding.txtResult.setText(" ");
            activityBinding.txtExpression.append(string);
        }else{
            activityBinding.txtExpression.append(activityBinding.txtResult.getText());
            activityBinding.txtExpression.append(string);
            activityBinding.txtResult.setText(" ");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.basicCalculatorMi:
                Toast.makeText(this, "you're in basic calculator", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.advancedCalculatorMi:
                Intent advancedCalculatorIntent = new Intent(this, AdvancedCalculatorActivity.class);
                startActivity(advancedCalculatorIntent);
                return true;
            default:
                return false;
        }
    }
}