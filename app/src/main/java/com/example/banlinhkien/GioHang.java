package com.example.banlinhkien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Adapter.LinhKienGioAdapter;
import Model.LinhKienGio;

public class GioHang extends AppCompatActivity {
    ImageView imgback;
    ImageView imgtrangchu;
    ListView lvlinhkiengio;
    Button btnDathang;
    TextView txttongtien;
    ArrayList<LinhKienGio> manglinhkien;
    LinhKienGioAdapter linhKienGioAdapter;
    public int tongtien=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        lvlinhkiengio = findViewById(R.id.lvlinhkiengio);
        imgback = findViewById(R.id.imgback);
        imgtrangchu = findViewById(R.id.imgtrangchu);
        btnDathang =findViewById(R.id.btnDathang);
        txttongtien = findViewById(R.id.txttongtien);
        manglinhkien = new ArrayList<LinhKienGio>();
        linhKienGioAdapter = new LinhKienGioAdapter(GioHang.this,manglinhkien);
        lvlinhkiengio.setAdapter(linhKienGioAdapter);
        getDuLieuLinhKienGio();

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgtrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHang.this, Home.class);
                startActivity(intent);
            }
        });
        lvlinhkiengio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GioHang.this, ThongTinLinhKien.class);
                intent.putExtra("ma",manglinhkien.get(position).getMa());
                startActivity(intent);
            }
        });
        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(GioHang.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.nhap_thong_tin, null);
//                mBtnHuy = view.findViewById(R.id.btnHuy);
//                mBtnThem = view.findViewById(R.id.btnThem);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    public void getDuLieuLinhKienGio(){
        RequestQueue requestQueue = Volley.newRequestQueue(GioHang.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.duongdangetlinhkiengio,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            int id;
                            int ma;
                            String ten;
                            int gia;
                            String anh;
                            int soluong;
                            Log.d("TAG", "onResponse: "+response.toString());
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    ma = jsonObject.getInt("ma");
                                    ten = jsonObject.getString("ten");
                                    gia = jsonObject.getInt("gia");
                                    anh = jsonObject.getString("anh");
                                    soluong = jsonObject.getInt("soluong");
                                    tongtien+=soluong*gia;
                                    txttongtien.setText(""+tongtien);
                                    manglinhkien.add(new LinhKienGio(id,ma,ten,anh,gia,soluong));
                                    linhKienGioAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconnect.ShowToast(GioHang.this,error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void xoadanhsach() {
        for(int i=0 ;i<manglinhkien.size() ;i++){
            manglinhkien.remove(i);
            i--;
        }
        linhKienGioAdapter.notifyDataSetChanged();
    }
    public void Xoa(View view, int idi){
        android.app.AlertDialog.Builder alBuilder = new android.app.AlertDialog.Builder(view.getContext() );
        alBuilder.setMessage("Bạn có chắc chắn muốn xoá?");
        alBuilder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String id = String.valueOf(idi);

                if(id.length() > 0){
                    RequestQueue requestQueue = Volley.newRequestQueue (view.getContext() );
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, sever.duongdanxoalinhkiengio, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("scmnd", response);
                            if ("1".equals(response)){
                                Toast.makeText(view.getContext(), "Xoá thành công", Toast.LENGTH_SHORT).show();
                                xoadanhsach();
                                getDuLieuLinhKienGio();
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
                            hashMap.put("id",id);
                            return hashMap;
                        }
                    };
                    requestQueue.add ( stringRequest );
                }else{
                    Checkconnect.ShowToast(view.getContext(), "Hãy kiểm tra lại dữ liệu" );
                }
            }
        });
        alBuilder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alBuilder.show();
    }
    public void loadTongTien(){
        txttongtien.setText(""+tongtien);
    }

}