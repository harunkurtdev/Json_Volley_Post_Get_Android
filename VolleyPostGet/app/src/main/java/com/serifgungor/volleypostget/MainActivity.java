package com.serifgungor.volleypostget;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    StringRequest request;

    EditText etAdSoyad, etSifre, etTelefonNo, etEmail;
    Button btn;


    EditText etGirisEmail,etGirisSifre;
    Button btnGiris;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();


        //1. aşama kuyruğu tanımla
        queue = Volley.newRequestQueue(getApplicationContext());

        etGirisEmail = findViewById(R.id.etGirisEmail);
        etGirisSifre = findViewById(R.id.etGirisSifre);
        btnGiris = findViewById(R.id.btnGiris);


        etAdSoyad = findViewById(R.id.etAdSoyad);
        etSifre = findViewById(R.id.etSifre);
        etEmail = findViewById(R.id.etEmail);
        etTelefonNo = findViewById(R.id.etTelefonNo);
        btn = findViewById(R.id.btnKayit);



        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request = new StringRequest(
                        Request.Method.POST,
                        "http://10.1.9.14:7331/android_post_get/android_giris.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d("WEB_RESPONSE",response);

                                if("101".equals(response)){
                                    Toast.makeText(getApplicationContext(),"Kullanıcı adı yada şifre hatalı",Toast.LENGTH_LONG).show();
                                }else if("200".equals(response)){
                                    Toast.makeText(getApplicationContext(),"Bağlantı protokolü hatası",Toast.LENGTH_LONG).show();
                                }else{
                                    //JSON PARÇALA

                                    try {
                                        //isimsiz JSON okuma
                                        JSONArray jsonArray = new JSONArray(response);

                                        JSONObject uye = jsonArray.getJSONObject(0);
                                        int id = Integer.parseInt(uye.getString("id"));
                                        String ad_soyad = uye.getString("ad_soyad");
                                        String telefon_no = uye.getString("telefon_no");
                                        String email = uye.getString("email");

                                        editor.putInt("userid",id);
                                        editor.putString("adsoyad",ad_soyad);
                                        editor.putString("telefonno",telefon_no);
                                        editor.putString("email",email);
                                        editor.commit();

                                        startActivity(new Intent(MainActivity.this,AnaActivity.class));



                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        map.put("email",etGirisEmail.getText().toString());
                        map.put("sifre",etGirisSifre.getText().toString());
                        return map;
                    }
                };
                queue.add(request);


            }
        });



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                request = new StringRequest(
                        Request.Method.POST,
                        "http://10.1.9.14:7331/android_post_get/android_kayit.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                Log.d("WEB_RESPONSE"," "+response);


                                if("100".equals(response)){
                                    Toast.makeText(getApplicationContext(),"Kayıt başarılı",Toast.LENGTH_LONG).show();
                                }else if("101".equals(response)){
                                    Toast.makeText(getApplicationContext(),"Kayıt başarısız",Toast.LENGTH_LONG).show();
                                }else if("200".equals(response)){
                                    Toast.makeText(getApplicationContext(),"Veri tabanı bağlantı hatası oluştu !",Toast.LENGTH_LONG).show();

                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("WEB_RESPONSE"," "+error.getMessage());
                            }
                        }
                ) {

                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params = new HashMap<>();
                        params.put("adsoyad", ""+etAdSoyad.getText().toString());
                        params.put("telefonno", ""+etTelefonNo.getText().toString());
                        params.put("email",""+etEmail.getText().toString());
                        params.put("sifre", ""+etSifre.getText().toString());

                        return params;
                    }

                };

                //3. aşama
                queue.add(request);



            }
        });


    }
}
