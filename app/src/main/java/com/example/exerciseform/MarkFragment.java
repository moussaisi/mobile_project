package com.example.exerciseform;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
public class MarkFragment extends Fragment {
    private TextView txtMarks;//Ce champ affichera les notes
    private Button btnMarks;
    private String marks;
    private Handler handler = new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_mark, container, false);
        txtMarks = view.findViewById(R.id.txtMarks);
        btnMarks = view.findViewById(R.id.btnMarks);
        btnMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marks="";
                marksServer();
            }
        });
        return view;
    }
    public void marksServer(){
        String url = "http://192.168.43.123:8081/diti5/mark.php?login="+MainActivity.login;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getActivity(),getString(R.string.error_connection),Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String result = response.body().string();
                    JSONObject jo = new JSONObject(result);
                    JSONArray ja = jo.getJSONArray("marks");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject element = ja.getJSONObject(i);
                        String course = element.getString("course");
                        String grade = element.getString("grade");
                        marks+=course+": "+grade+ "\n\n";
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtMarks.setText(marks);
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}