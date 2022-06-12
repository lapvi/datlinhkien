package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import Model.DanhMuc;
import com.example.banlinhkien.R;

import java.util.ArrayList;

import Fragment.TrangChuFragment;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.DanhMucViewHoder>{
    Context context;
    ArrayList<DanhMuc> arraydanhmuc;
    OnItemlistener onItemlistener;

    public DanhMucAdapter(Context context, ArrayList<DanhMuc> arraydanhmuc, TrangChuFragment onItemlistener) {
        this.context = context;
        this.arraydanhmuc = arraydanhmuc;
        this.onItemlistener = onItemlistener;
    }

    @NonNull
    @Override
    public DanhMucViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_danh_muc,parent,false);
        DanhMucViewHoder danhMucViewHoder = new DanhMucViewHoder(v,onItemlistener);
        return danhMucViewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHoder holder, int position) {
        DanhMuc danhMuc = arraydanhmuc.get(position);
        Glide.with(holder.imganh.getContext()).load(arraydanhmuc.get(position).getAnh()).into(holder.imganh);
        holder.txttendanhmuc.setText(""+arraydanhmuc.get(position).getTen());
    }

    @Override
    public int getItemCount() {
        return arraydanhmuc.size();
    }

    public interface OnItemlistener{
        void onItemClick(int postion, String dayText);
    }
    public class  DanhMucViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imganh;
        TextView txttendanhmuc;
        OnItemlistener onItemlistener;

        public DanhMucViewHoder(@NonNull View itemView,OnItemlistener onItemlistener) {
            super(itemView);
            imganh = (ImageView) itemView.findViewById(R.id.imganh);
            txttendanhmuc = (TextView) itemView.findViewById(R.id.txttendanhmuc);
            this.onItemlistener = onItemlistener;
            itemView.setOnClickListener(this);
        }

        @Override

        public void onClick(View v) {
            onItemlistener.onItemClick(getAdapterPosition(),(String) txttendanhmuc.getText().toString());
        }
    }
}
