package com.example.a328project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class weather extends AppCompatActivity {

    public static int imgResource;
    private String city;
    public  ImageView img;
    TextView temp;
    TextView humidity;
    EditText inputCity;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        temp = (TextView) findViewById(R.id.temp);
        humidity = (TextView) findViewById(R.id.humidity);
        inputCity=(EditText)findViewById(R.id.cityName);
        img = (ImageView) findViewById(R.id.img);
        ok =(Button)findViewById(R.id.getWeather);
        Button back = (Button) findViewById(R.id.goAct1);

        //humidity.setVisibility(View.INVISIBLE);
        //temp.setVisibility(View.INVISIBLE);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                humidity.setVisibility(View.VISIBLE);
                temp.setVisibility(View.VISIBLE);

                String city=inputCity.getText().toString();
                String parsed="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=7e564ffe27af0b36949b50a283d3fdd0&units=metric";
                weather(parsed);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(weather.this, MainActivity.class));
            }
        });
    }

    public void weather(String url) {

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // result of the request
                        try {
                            JSONObject jsonMain = response.getJSONObject("main");

                            double temperature = jsonMain.getDouble("temp");
                            temp.setText("Temprature: " + String.valueOf(temperature));

                            double humid = jsonMain.getDouble("humidity");
                            humidity.setText("Humidity: "+ String.valueOf(humid));

                            whetherPic(response.getJSONArray("weather"));
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error:", error.toString());
                    }

                });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObj);
    }


    public void whetherPic(JSONArray jArray) {

        for (int i = 0; i < jArray.length(); i++) {
            try {
                JSONObject oneObject = jArray.getJSONObject(i);
                String weatherCondition = oneObject.getString("main");

                if(weatherCondition.equals("Clouds")){

                    img.setImageResource(R.drawable.cloudy);
                }

                else if(weatherCondition.equals("Clear")){
                    img.setImageResource(R.drawable.sunny);
                }
                else if(weatherCondition.equals("Rain")){
                    img.setImageResource(R.drawable.rainy);
                }
                else if(weatherCondition.equals("Snow")){
                    img.setImageResource(R.drawable.snowy);
                }
            }
            catch (Exception e) {
                Toast.makeText(weather.this, "an error occured", Toast.LENGTH_SHORT).show();
            }
        }
    }

}