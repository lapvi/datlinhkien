package com.example.banlinhkien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import Model.TaiKhoan;

public class DoiMatKhau extends AppCompatActivity {
    ImageView imgback;
    Button btndoimatkhau;
    EditText edtmatkhaucu;
    EditText edtmatkhaumoi1;
    EditText edtmatkhaumoi2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        imgback = findViewById(R.id.imgback);
        btndoimatkhau = findViewById(R.id.btndoimatkhau);
        edtmatkhaucu = findViewById(R.id.edtmakhaucu);
        edtmatkhaumoi1 = findViewById(R.id.edtmakhaumoi1);
        edtmatkhaumoi2 = findViewById(R.id.edtmatkhaumoi2);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btndoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update();
            }
        });
    }
    public void Update(){
        final String ma = String.valueOf(TaiKhoan.mataikhoan);
        final String matkhaucu = TaiKhoan.matkhau;
        final String matkhaumoi1 = edtmatkhaumoi1.getText().toString().trim();
        final String matkhaumoi2 = edtmatkhaumoi1.getText().toString();
        if(matkhaucu.equals(edtmatkhaucu.getText().toString())){
            if(matkhaumoi1.equals(matkhaumoi2)&&matkhaumoi1.length()>4){
                RequestQueue requestQueue = Volley.newRequestQueue (DoiMatKhau.this );
                StringRequest stringRequest = new StringRequest(Request.Method.POST, sever.duongdandoimatkhau, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("scmnd", response);
                        if ("1".equals(response)){
                            Toast.makeText(DoiMatKhau.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String> ();
                        hashMap.put("mataikhoan",ma);
                        hashMap.put ( "matkhau",matkhaumoi1 );
                        return hashMap;
                    }
                };
                requestQueue.add ( stringRequest );
            }else {
                Toast.makeText(DoiMatKhau.this, "Mật khẩu phải mới giống nhau và lớn hơn 4 ký tự", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(DoiMatKhau.this, "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
        }
    }
}