package br.com.imiranda.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button welcomeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeButton = findViewById(R.id.welcomeButton);
        welcomeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, getString(R.string.clicked), Toast.LENGTH_SHORT);

    }
}