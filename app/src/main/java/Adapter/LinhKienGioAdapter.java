package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.banlinhkien.Checkconnect;
import com.example.banlinhkien.GioHang;
import Model.LinhKienGio;
import com.example.banlinhkien.R;
import com.example.banlinhkien.sever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LinhKienGioAdapter extends BaseAdapter {
    GioHang contextt;
    ArrayList<LinhKienGio> manglinhkien;


    public LinhKienGioAdapter(GioHang contextt, ArrayList<LinhKienGio> manglinhkien) {
        this.contextt = contextt;
        this.manglinhkien = manglinhkien;
    }

    @Override
    public int getCount() {
        return manglinhkien.size();
    }

    @Override
    public Object getItem(int position) {
        return manglinhkien.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(contextt);
        convertView= layoutInflater.inflate(R.layout.linh_kien_trong_gio,parent,false);
        TextView ten = convertView.findViewById(R.id.txtten);
        TextView gia = convertView.findViewById(R.id.txtgia);
        ImageView anh = convertView.findViewById(R.id.imganh);
        TextView sl = convertView.findViewById(R.id.txtsoluong);
        ImageView imgcong = convertView.findViewById(R.id.imgcong);
        ImageView imgtru = convertView.findViewById(R.id.imgtru);
        ImageView imgxoa = convertView.findViewById(R.id.imgxoa);

        Glide.with(convertView.getContext()).load(""+manglinhkien.get(position).getAnh()).into(anh);
        ten.setText(manglinhkien.get(position).getTen());
        gia.setText("Giá: "+manglinhkien.get(position).getGia());
        sl.setText(""+manglinhkien.get(position).getSoluong());
        imgcong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sli=manglinhkien.get(position).getSoluong();
                sli+=1;
                contextt.tongtien+=manglinhkien.get(position).getGia();
                contextt.loadTongTien();
                int idi = manglinhkien.get(position).getId();
                manglinhkien.get(position).setSoluong(sli);
                sl.setText(""+sli);
                Update(view,idi,sli);

            }
        });
        imgtru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sli=manglinhkien.get(position).getSoluong();
                sli-=1;
                contextt.tongtien-=manglinhkien.get(position).getGia();
                contextt.loadTongTien();
                int idi = manglinhkien.get(position).getId();
                manglinhkien.get(position).setSoluong(sli);
                sl.setText(""+sli);
                if (sli<=1){
                    imgtru.setEnabled(false);
                }else {
                    imgtru.setEnabled(true);
                }
                Update(view,idi,sli);
            }
        });
        imgxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = manglinhkien.get(position).getId();
                contextt.Xoa(view,id);

            }
        });
        return convertView;
    }
    public void Update(View view,int idi,int sli){
        final String id = String.valueOf(idi);
        final String soluong = String.valueOf(sli);
        RequestQueue requestQueue = Volley.newRequestQueue (view.getContext() );
        StringRequest stringRequest = new StringRequest(Request.Method.POST, sever.duongdanupdategiohang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("scmnd", response);
                if ("1".equals(response)){
                    Toast.makeText(view.getContext(), "Cập thật thành công", Toast.LENGTH_SHORT).show();
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
                hashMap.put ( "soluong",soluong );
                return hashMap;
            }
        };
        requestQueue.add ( stringRequest );
    }

}
