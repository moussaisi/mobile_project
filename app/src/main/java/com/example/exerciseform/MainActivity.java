package com.example.exerciseform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = txtLogin.getText().toString().trim();
                password = txtPassword.getText().toString().trim();
                if(login.isEmpty() || password.isEmpty())
                {
                    String message = getString(R.string.error_fields);
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Appel du service pour vérifier le login et le password
                    loginServer();
                }
            }
        });
    }

    //Communication entre la page login et le service connexion
    public void loginServer(){
        //String url = "http://192.168.43.123:8081/diti5/connexion.php?login="+login+"&password="+password;
        String url = "http://3iweb.org/diti5/connexion.php?login="+login+"&password="+password;
        OkHttpClient client = new OkHttpClient();//Création du client
        Request request = new Request.Builder()//Creation de la requete
            .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //Quand il y'a erreur
                final String message = getString(R.string.error_connection);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    String status = jo.getString("status");
                    if(status.equalsIgnoreCase("KO")){
                        final String message = getString(R.string.error_parametters);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }







}