package com.example.banlinhkien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.DanhMuc;
import Model.TaiKhoan;

public class MainActivity extends AppCompatActivity {
    EditText edtname;
    EditText edtpassword;
    TextView txtdangky;
    Button btdangnhap;
    CheckBox cbremember;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtname = findViewById(R.id.edttendangnhap);
        edtpassword = findViewById(R.id.edtmatkhau);
        btdangnhap = findViewById(R.id.btdangnhap);
        txtdangky = findViewById(R.id.txtdangky);
        cbremember = findViewById(R.id.cbremember);

        sharedPreferences = getSharedPreferences("dataLogin", MODE_PRIVATE);
        edtname.setText(sharedPreferences.getString("taikhoan",""));
        edtpassword.setText(sharedPreferences.getString("matkhau",""));
        cbremember.setChecked(sharedPreferences.getBoolean("checker", false));
        btdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Checkconnect.haveNetworkConnection(MainActivity.this)){
                    Checkconnect.ShowToast(MainActivity.this,"Bạn hãy kiểm tra lại kết nối!");
                }else{
                    String usename = edtname.getText().toString().trim();
                    String password = edtpassword.getText().toString().trim();
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.duongdangettaikhoan,
                            new Response.Listener<JSONArray>() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void onResponse(JSONArray response) {
                                    if(response!=null){
                                        Boolean check= false;
                                        String tendangnhap="";
                                        String matkhau="";
                                        Log.d("TAG", "onResponse: "+response.toString());
                                        for(int i=0;i<response.length();i++){
                                            try {
                                                JSONObject jsonObject = response.getJSONObject(i);
                                                tendangnhap = jsonObject.getString("tendangnhap");
                                                matkhau = jsonObject.getString("matkhau");
                                                if(usename.equals(tendangnhap)&&password.equals(matkhau)){
                                                    TaiKhoan.mataikhoan = jsonObject.getInt("mataikhoan");
                                                    TaiKhoan.tendangnhap = tendangnhap;
                                                    TaiKhoan.matkhau = matkhau;
                                                    check=true;
                                                    Intent intent = new Intent(MainActivity.this, Home.class);
                                                    if (cbremember.isChecked()){
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("taikhoan",usename);
                                                        editor.putString("matkhau",password);
                                                        editor.putBoolean("checker", true);
                                                        editor.commit();
                                                    }else {
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.remove("taikhoan");
                                                        editor.remove("matkhau");
                                                        editor.remove("checker");
                                                        editor.commit();
                                                    }
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        if (!check){
                                            Toast.makeText(MainActivity.this, "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Checkconnect.ShowToast(MainActivity.this,error.toString());
                        }
                    });
                    requestQueue.add(jsonArrayRequest);
                }

            }
        });
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DangKy.class);
                startActivity(intent);
            }
        });

    }
}