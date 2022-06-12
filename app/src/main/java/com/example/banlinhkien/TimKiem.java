package com.example.banlinhkien;

import static com.example.banlinhkien.R.layout.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.LinhKienAdapter;
import Model.LinhKien;

public class TimKiem extends AppCompatActivity {

    ImageView imgback;
    ListView lvtimkiem;
    ArrayList<LinhKien> mtimkiem;
    LinhKienAdapter linhKienAdapter;
    String ma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_tim_kiem);

        imgback = findViewById(R.id.imgback);
        lvtimkiem = findViewById(R.id.lvtimkiem);
        mtimkiem = new ArrayList<LinhKien>();

        ma = getIntent().getExtras().getString("ma");

        linhKienAdapter = new LinhKienAdapter(this,mtimkiem);
        lvtimkiem.setAdapter(linhKienAdapter);
        getDuLieuLinhKien();

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvtimkiem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ma = mtimkiem.get(position).getId();
                String ten = mtimkiem.get(position).getTen();
                int gia = mtimkiem.get(position).getGia();
                int soluong = mtimkiem.get(position).getSoluong();
                String anh = mtimkiem.get(position).getAnh();
                String loai = mtimkiem.get(position).getLoai();
                String mota = mtimkiem.get(position).getMota();
                LinhKien lk = new LinhKien(ma,ten,gia,anh,loai,soluong,mota);
                Intent intent = new Intent(TimKiem.this, ThongTinLinhKien.class);
                intent.putExtra("lk",lk);
                startActivityForResult(intent,100);
            }
        });
    }
    public void getDuLieuLinhKien(){
        RequestQueue requestQueue = Volley.newRequestQueue(TimKiem.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.duongdangetlinhkien,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            int id;
                            String ten;
                            int gia;
                            String anh;
                            String loai;
                            int soluong;
                            String mota;
                            Log.d("TAG", "onResponse: "+response.toString());
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    id = jsonObject.getInt("id");
                                    ten = jsonObject.getString("ten");
                                    gia = jsonObject.getInt("gia");
                                    anh = jsonObject.getString("anh");
                                    loai = jsonObject.getString("loai");
                                    soluong = jsonObject.getInt("soluong");
                                    mota = jsonObject.getString("chitiet");
                                        if(loai.equals(ma)){
                                            mtimkiem.add(new LinhKien(id,ten,gia,anh,loai,soluong,mota));
                                            linhKienAdapter.notifyDataSetChanged();
                                        }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconnect.ShowToast(TimKiem.this,error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}