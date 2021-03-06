package com.tec.utb.pokemon01;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class campo_batalla extends AppCompatActivity {
    TextView pokemon1_nombre;
    TextView pokemon2_nombre;
    ImageView pokemon1_imagen;
    ImageView pokemon2_imagen;
    TextView vida1;
    TextView vida2;
    Button ataque;
    int jugador1_vida=100;
    int maquina_vida=100;
    String nombre1;
    String nombre2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campo_batalla);
        pokemon1_nombre= (TextView) findViewById(R.id.pokemon_1_nombre);
        pokemon2_nombre= (TextView) findViewById(R.id.pokemon_2_nombre);
        pokemon1_imagen= (ImageView) findViewById(R.id.pokemon_1_imagen);
        pokemon2_imagen= (ImageView) findViewById(R.id.pokemon_2_imagen);
        ataque= (Button) findViewById(R.id.button2);
        vida1= (TextView) findViewById(R.id.pokemon_1_vida);
        vida2= (TextView) findViewById(R.id.pokemon_2_vida);

        vida1.setText("100");
        vida2.setText("100");

        Random random=new Random();
        int id1= (int) (Math.random()*500);
        int id2=(int) (Math.random()*500);
        traernombre(id1,pokemon1_nombre);
        traernombre(id2,pokemon2_nombre);
        cargarimagen(id2,pokemon1_imagen);
        cargarimagen2(id1,pokemon2_imagen);
        ataque.setEnabled(false);
        ataque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ataque();
            }
        });

    }

    public boolean verificar(int vida){
        if (vida<=0){
            return true;
        }
        else return false;
    }
    public void ataque(){
        ataque.setEnabled(false);
        int ataque_jugador= (int) (Math.random()*50);
        final int ataque_maquina= (int) (Math.random()*50);
        //
        maquina_vida=(maquina_vida-ataque_jugador);
        if (verificar(maquina_vida)==true){
            vida2.setText("0");
            ataque.setEnabled(false);
            Toast.makeText(campo_batalla.this, "GANASTES", Toast.LENGTH_SHORT).show();

        }else {
            vida2.setText(""+maquina_vida);
                   jugador1_vida=(jugador1_vida-ataque_maquina);
                    if (verificar(jugador1_vida)==true){
                        vida1.setText("0");
                        ataque.setEnabled(false);
                        Toast.makeText(campo_batalla.this, "PERDISTES", Toast.LENGTH_SHORT).show();

                    }else {
                        vida1.setText(""+jugador1_vida);
                        ataque.setEnabled(true);
                    }




        }



    }

    public void descargarimagen(ImageView imageView,String url){
        ImageLoader mImageLoader;
// The URL for the image that is being loaded.
// Get the ImageLoader through your singleton class.
        mImageLoader = MySingleton.getInstance(this).getImageLoader();
        mImageLoader.get(url, ImageLoader.getImageListener(imageView,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher));
        ataque.setEnabled(true);

    }

    public void cargarimagen(int id, final ImageView imageView){
        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        String url ="http://pokeapi.co/api/v2/pokemon-form/"+id+"/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("response",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject jsonObject1=new JSONObject(jsonObject.getString("sprites"));
                            String url=jsonObject1.getString("front_default").toString();
                            descargarimagen(imageView,url);
                            Log.i("imagen: ", jsonObject1.getString("front_default").toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("","That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    public void cargarimagen2(int id, final ImageView imageView){
        MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        String url ="http://pokeapi.co/api/v2/pokemon-form/"+id+"/";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("response",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            JSONObject jsonObject1=new JSONObject(jsonObject.getString("sprites"));
                            String url=jsonObject1.getString("back_default").toString();
                            descargarimagen(imageView,url);
                            Log.i("imagen: ", jsonObject1.getString("back_default").toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("","That didn't work!");
            }
        });
// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void traernombre(int id, final TextView t){

        MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

// ...
        String url ="http://pokeapi.co/api/v2/pokemon/"+id+"/";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("response",response);
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            Log.i("nombre",jsonObject.getString("name"));
                            t.setText( jsonObject.getString("name"));
                            JSONArray jsonArray=jsonObject.getJSONArray("abilities");
                            for (int i=0;i<jsonArray.length();i++) {

                                Log.i("abilities: ", jsonArray.getJSONObject(i).toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("","That didn't work!");
            }
        });
// Add the request to the RequestQueue.

// Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }
}
