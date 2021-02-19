package com.example.exerciseform;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class InscriptionFragment extends Fragment {
    private EditText txtFirsName,txtLastName;
    private CheckBox cbOLevel,cbBachelor,cbMaster;
    private Button btnSave;
    private String firstName,lastName,degrees;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_inscription, container, false);
        txtFirsName = view.findViewById(R.id.txtFirstName);
        txtLastName = view.findViewById(R.id.txtLastName);
        cbOLevel = view.findViewById(R.id.cbOLevel);
        cbBachelor = view.findViewById(R.id.cbBachelor);
        cbMaster = view.findViewById(R.id.cbMaster);
        btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = txtFirsName.getText().toString().trim();
                lastName  = txtLastName.getText().toString().trim();
                degrees="";
                if(cbOLevel.isChecked()){
                        degrees+=cbOLevel.getText().toString()+" ";
                }
                if(cbBachelor.isChecked()){
                    degrees+=cbBachelor.getText().toString()+" ";
                }
                if(cbMaster.isChecked()){
                    degrees+=cbMaster.getText().toString()+" ";
                }
                if(firstName.isEmpty() || lastName.isEmpty() || degrees.isEmpty()){
                    Toast.makeText(getActivity(),getString(R.string.error_fields),Toast.LENGTH_SHORT).show();
                }
                else{
                    //String resume = firstName+"\n\n"+lastName+"\n\n"+degrees;
                    //Toast.makeText(getActivity(),resume,Toast.LENGTH_SHORT).show();
                    inscriptionServer();
                }
            }
        });
        return view;
    }

    public void inscriptionServer(){
        String url = "http://192.168.43.123:8081/diti5/inscription.php";
        //String url = "http://3iweb.org/diti5/inscription.php";
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("first_name",firstName)
                .add("last_name",lastName)
                .add("degrees",degrees)
                .add("formation",FormationFragment.formation)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        try {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String message = getString(R.string.error_connection);
                            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String result = response.body().string();
                        JSONObject jo = new JSONObject(result);
                        String status = jo.getString("status");
                        if(status.equalsIgnoreCase("OK")){
                            Toast.makeText(getActivity(),getString(R.string.success_inscription),Toast.LENGTH_SHORT).show();
                            /**Redirection
                            Intent intent = new Intent(getActivity(),HomeActivity.class);
                            startActivity(intent);**/
                        }
                        else{
                            Toast.makeText(getActivity(),getString(R.string.error_inscription),Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}