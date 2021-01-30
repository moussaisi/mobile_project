package com.example.exerciseform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText txtLogin,txtPassword;
    private Button btnConnect,btnSignUp;
    private String login,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        btnConnect = findViewById(R.id.btnConnect);
        btnSignUp = findViewById(R.id.btnSignUp);


        String login = txtLogin.getText().toString();
        String password = txtPassword.getText().toString();

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        // btnConnect.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View evt) {
//                if (login.equalsIgnoreCase("moussa") && password.equalsIgnoreCase("sarr")) {
//                    Log.i("messageSuccess","Connection ok");
//                }else{
//                    Log.i("messageError","Connection failed");
//                }
            //}
        //});
    }
}