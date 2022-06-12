package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.banlinhkien.LichSu;
import com.example.banlinhkien.R;

import java.util.ArrayList;

import Fragment.LichSuFragment;

public class LichSuAdapter extends BaseAdapter {
    LichSuFragment context;
    ArrayList<LichSu> mlichsu;

    public LichSuAdapter(LichSuFragment context, ArrayList<LichSu> mlichsu) {
        this.context = context;
        this.mlichsu = mlichsu;
    }

    @Override
    public int getCount() {
        return mlichsu.size();
    }

    @Override
    public Object getItem(int position) {
        return mlichsu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context.getActivity());
        convertView = layoutInflater.inflate(R.layout.dong_lich_su,parent,false);
        TextView ten = convertView.findViewById(R.id.txtten);
        TextView gia = convertView.findViewById(R.id.txtgia);
        TextView ngay = convertView.findViewById(R.id.txtngay);
        TextView sl = convertView.findViewById(R.id.txtsoluong);
        ten.setText("Tên sản phẩm: "+mlichsu.get(position).getTen());
        gia.setText("Giá: "+mlichsu.get(position).getGia());
        sl.setText("Số lượng: "+mlichsu.get(position).getSoluong());
        ngay.setText("Ngày: "+mlichsu.get(position).getNgay());

        return convertView;
    }
}
