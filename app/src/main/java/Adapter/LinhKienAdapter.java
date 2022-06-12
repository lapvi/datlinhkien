package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import Model.LinhKien;
import com.example.banlinhkien.R;
import com.example.banlinhkien.TimKiem;

import java.util.ArrayList;

import Fragment.TrangChuFragment;

public class LinhKienAdapter extends BaseAdapter {
    TrangChuFragment context;
    TimKiem contextt;
    ArrayList<LinhKien> manglinhkien;

    public LinhKienAdapter(TrangChuFragment context, ArrayList<LinhKien> manglinhkien) {
        this.context = context;
        this.manglinhkien = manglinhkien;
    }
    public LinhKienAdapter(TimKiem context, ArrayList<LinhKien> manglinhkien) {
        this.contextt = context;
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
        LayoutInflater layoutInflater;
        try {
            layoutInflater = LayoutInflater.from(context.getActivity());
        }catch (Exception e){
            layoutInflater = LayoutInflater.from(contextt);
        }
        convertView= layoutInflater.inflate(R.layout.dong_linh_kien,parent,false);
        TextView ten = convertView.findViewById(R.id.txtten);
        TextView gia = convertView.findViewById(R.id.txtgia);
        ImageView anh = convertView.findViewById(R.id.imganh);
        Glide.with(convertView.getContext()).load(""+manglinhkien.get(position).getAnh()).into(anh);
        ten.setText("Tên: "+manglinhkien.get(position).getTen());
        gia.setText("Giá: "+manglinhkien.get(position).getGia());
        return convertView;
    }
}
