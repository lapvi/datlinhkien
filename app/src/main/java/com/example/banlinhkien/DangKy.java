package com.example.banlinhkien;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangKy extends AppCompatActivity {
    ImageView imgback;
    EditText edttendn;
    EditText edtmatkhau1;
    EditText edtmatkhau2;
    Button btndangky;
    TextView txtdangnhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        imgback = findViewById( R.id.imgback);
        txtdangnhap =findViewById(R.id.txtdangnhap);
        edttendn = findViewById(R.id.edttendn);
        edtmatkhau1 = findViewById(R.id.edtmakhau1);
        edtmatkhau2 = findViewById(R.id.edtmatkhau2);
        btndangky = findViewById(R.id.btndangky);
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemTaiKhoan();
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txtdangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public  void ThemTaiKhoan(){
        if(!Checkconnect.haveNetworkConnection(DangKy.this)){
            Checkconnect.ShowToast(DangKy.this,"Bạn hãy kiểm tra lại kết nối!");
        }else{
            String tendn = edttendn.getText().toString().trim();
            String matkhau1 = edtmatkhau1.getText().toString().trim();
            String matkhau2 = edtmatkhau2.getText().toString().trim();
            RequestQueue requestQueue = Volley.newRequestQueue(DangKy.this);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.duongdangettaikhoan,
                    new Response.Listener<JSONArray>() {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onResponse(JSONArray response) {
                            if(response!=null){
                                Boolean check= true;
                                String tendangnhap="";
                                Log.d("TAG", "onResponse: "+response.toString());
                                for(int i=0;i<response.length();i++){
                                    try {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        tendangnhap = jsonObject.getString("tendangnhap");
                                        if(tendn.equals(tendangnhap)){
                                            check=false;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!check){
                                    Toast.makeText(DangKy.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_LONG).show();
                                }else if( tendn.length ()>0 && matkhau1.length()>0&&matkhau2.length()>0 && matkhau1.equals(matkhau2)) {
                                    RequestQueue requestQueue = Volley.newRequestQueue(DangKy.this);
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, sever.duongdanthemtaikhoan, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Log.d("scmnd", response);
                                            if ("1".equals(response)) {
                                                Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            HashMap<String, String> hashMap = new HashMap<String, String>();
                                            hashMap.put("tendangnhap", tendn);
                                            hashMap.put("matkhau", matkhau1);
                                            return hashMap;
                                        }
                                    };
                                    requestQueue.add(stringRequest);
                                }else {
                                    Toast.makeText(DangKy.this, "Vui lòng kiểm tra lại thông tin đăng ký", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Checkconnect.ShowToast(DangKy.this,error.toString());
                }
            });
            requestQueue.add(jsonArrayRequest);
        }
    }
}