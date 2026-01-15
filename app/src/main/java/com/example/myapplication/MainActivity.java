package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private TextView tempView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempView = findViewById(R.id.tempView);
        tempView.setText("Загрузка...");

        new Thread(() -> {
            while (true) {
                try {
                    URL url = new URL("http://10.0.2.2:8000/temp");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    Scanner scanner = new Scanner(conn.getInputStream());
                    String json = scanner.useDelimiter("\\A").next();
                    scanner.close();

                    JSONObject obj = new JSONObject(json);
                    double t = obj.getDouble("t");
                    String time = obj.getString("time");

                    runOnUiThread(() ->
                            tempView.setText(String.format("%.1f°C\n%s", t, time))
                    );

                    conn.disconnect();
                    Thread.sleep(5000);

                } catch (Exception e) {
                    runOnUiThread(() ->
                            tempView.setText("Ошибка: " + e.getMessage())
                    );
                    try { Thread.sleep(5000); } catch (Exception ex) {}
                }
            }
        }).start();
    }
}