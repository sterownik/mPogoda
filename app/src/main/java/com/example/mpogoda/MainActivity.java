package com.example.mpogoda;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.textclassifier.TextLinks;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    Button click;
    EditText miastowpisz;
    public static TextView data,datka,godzina,miasto,weather;
    ImageView ikona;
    String txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        miastowpisz = (EditText) findViewById(R.id.wpisz);
        datka = (TextView)findViewById(R.id.datka);
        godzina = (TextView)findViewById(R.id.godzina);
        miasto = (TextView)findViewById(R.id.miasto);
        weather = (TextView)findViewById(R.id.weather);
        ikona = (ImageView)findViewById(R.id.ikona);
        getSupportActionBar().hide();
        click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.data);
        Calendar c = Calendar.getInstance(TimeZone.getDefault());
        int month = c.get(Calendar.MONTH);
        month = month+1;
        datka.setText(c.get(Calendar.DAY_OF_MONTH)+"."+month+"."+c.get(Calendar.YEAR));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        godzina.setText(sdf.format(c.getTime()));


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  fetchData process = new fetchData();
                //    process.execute();
                findWeather();


            }
        });
    }

    public void findWeather() {

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + miastowpisz.getText() + "&appid=3e95f6a44299dfb2ce72a25301239f49&units=Imperial";
        //String url = "https://api.openweathermap.org/data/2.5/weather?q=Mielec&appid=3e95f6a44299dfb2ce72a25301239f49&units=Imperial";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                   // Toast.makeText(getApplicationContext(), "powiodło sie", Toast.LENGTH_LONG).show();
                    JSONObject main_obcject = response.getJSONObject("main");

                    JSONArray main_obcjecttwo = response.getJSONArray("weather");

                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);


                    String temp = String.valueOf(main_obcject.getDouble("temp"));
                    String desc = object.getString("description");

                    String city = response.getString("name");
                    Double db = Double.parseDouble(temp);
                    db = (db - 32) / 1.8;
                    miastowpisz.setText(" ");

                    //data.setText(new DecimalFormat("##.#").format(db) + " " + city + "\n" + desc);
                    miasto.setText(city+"");
                    data.setText(new DecimalFormat("##.#").format(db)+ " \u2103");
                    if(desc.equals("clear sky"))
                    {
                        ikona.setImageResource(R.drawable.sun);
                        weather.setText("Czyste niebo");
                    }
                    else if(desc.equals("few clouds"))
                    {
                        ikona.setImageResource(R.drawable.few);
                        weather.setText("Kilka chmur");
                    }
                    else if(desc.equals("scattered clouds"))
                    {
                        ikona.setImageResource(R.drawable.scattered);
                        weather.setText("Spore zachmurzenie");
                    }
                    else if(desc.equals("broken clouds"))
                    {
                        ikona.setImageResource(R.drawable.broken);
                        weather.setText("Lekkie zachmurzenie");

                    }
                    else if(desc.equals("shower rain"))
                    {
                        ikona.setImageResource(R.drawable.showerrain);
                        weather.setText("Lekki deszcz");
                    }
                    else if(desc.equals("rain"))
                    {
                        ikona.setImageResource(R.drawable.rain);
                        weather.setText("Rzęsisty deszcz");
                    }
                    else if(desc.equals("thunderstorm"))
                    {
                        ikona.setImageResource(R.drawable.thunderstrom);
                        weather.setText("Burza z piorunami");
                    }
                    else if(desc.equals("snow"))
                    {
                        ikona.setImageResource(R.drawable.snow);
                        weather.setText("Snieg");
                    }
                    else if(desc.equals("mist"))
                    {
                        ikona.setImageResource(R.drawable.mist);
                        weather.setText("Mgła");
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(jsonObjectRequest);
    }
}
