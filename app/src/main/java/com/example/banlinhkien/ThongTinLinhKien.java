package com.example.banlinhkien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.Map;

import Model.LinhKien;
import Model.TaiKhoan;

public class ThongTinLinhKien extends AppCompatActivity {
    Button btnthemgiohang;
    ImageView imgback;
    ImageView imggiohang;
    ImageView imganh;
    TextView txttenlk;
    TextView txtsoluong;
    TextView txtgia;
    TextView txtmota;
    ImageView imgthem,imgtru;
    TextView txtsl;
    int soluong=1;
    LinhKien lk=new LinhKien();
    MainActivity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_linh_kien);

        imggiohang = findViewById(R.id.imggiohang);
        imgback = findViewById(R.id.imgback);
        imganh = findViewById(R.id.imglinkien);
        txttenlk = findViewById(R.id.txttenlk);
        txtsoluong = findViewById(R.id.txtsoluong);
        txtgia = findViewById(R.id.txtgia);
        txtmota = findViewById(R.id.txtmota);
        txtsl = findViewById(R.id.txtsl);
        imgthem = findViewById(R.id.imgthem);
        imgtru = findViewById(R.id.imgtru);
        btnthemgiohang = findViewById(R.id.btnThemGioHang);

        lk = (LinhKien) getIntent().getExtras().get("lk");
        Glide.with(ThongTinLinhKien.this).load(""+lk.getAnh()).into(imganh);
        txttenlk.setText("Tên: "+lk.getTen());
        txtgia.setText("Giá: "+lk.getGia());
        txtsoluong.setText(""+lk.getSoluong());
        txtmota.setText(""+lk.getMota());
        imgtru.setEnabled(false);
        SuKienNutBam();



    }
    public void SuKienNutBam(){
        btnthemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemGioHang();
            }
        });
        imgtru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluong--;
                txtsl.setText(""+soluong);
                if (soluong<=1){
                    imgtru.setEnabled(false);
                }
            }
        });
        imgthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluong++;
                txtsl.setText(""+soluong);
                if (soluong>1){
                    imgtru.setEnabled(true);
                }
            }
        });
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThongTinLinhKien.this,GioHang.class);
                startActivity(intent);
            }
        });
    }
    public void ThemGioHang(){
        final String ma = String.valueOf(lk.getId());
        final String ten = lk.getTen();
        final String gia = String.valueOf(lk.getGia());
        final String anh = lk.getAnh();
        final String soluong = String.valueOf(txtsl.getText());
        final String taikhoan = String.valueOf(TaiKhoan.mataikhoan);
        if( ten.length ()>0 ) {
            RequestQueue requestQueue = Volley.newRequestQueue(ThongTinLinhKien.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, sever.duongdanthemgiohang, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("scmnd", response);
                    if ("1".equals(response)) {
                        Toast.makeText(ThongTinLinhKien.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
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
                    hashMap.put("ma", ma);
                    hashMap.put("ten", ten);
                    hashMap.put("gia", gia);
                    hashMap.put("anh", anh);
                    hashMap.put("soluong", soluong);
                    hashMap.put("taikhoan", taikhoan);
                    return hashMap;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}