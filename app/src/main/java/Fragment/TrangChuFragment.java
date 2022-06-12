package Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.banlinhkien.Checkconnect;
import Model.DanhMuc;
import com.example.banlinhkien.GioHang;
import Model.LinhKien;
import com.example.banlinhkien.R;
import com.example.banlinhkien.ThongTinLinhKien;
import com.example.banlinhkien.TimKiem;
import com.example.banlinhkien.sever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapter.DanhMucAdapter;
import Adapter.LinhKienAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrangChuFragment extends Fragment implements Adapter.DanhMucAdapter.OnItemlistener{
    ListView lvlinhkien;
    ImageView imgsearch;
    ImageView imggiohang;
    RecyclerView lvdanhmuc;
    ArrayList<LinhKien> manglinhkien;
    ArrayList<DanhMuc> mangdanhmuc;
    DanhMucAdapter danhMucAdapter;
    LinhKienAdapter linhKienAdapter;
    int mataikhoan;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TrangChuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrangChuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrangChuFragment newInstance(String param1, String param2) {
        TrangChuFragment fragment = new TrangChuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);

        lvlinhkien = (ListView) view.findViewById(R.id.lvlinhkien);
        lvdanhmuc = (RecyclerView) view.findViewById(R.id.lvdanhmuc);
        imgsearch = (ImageView) view.findViewById((R.id.imgsearch));
        imggiohang = (ImageView) view.findViewById(R.id.imggiohang);
        manglinhkien = new ArrayList<>();
        mangdanhmuc = new ArrayList<>();
        danhMucAdapter = new DanhMucAdapter(getContext(), mangdanhmuc,this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        lvdanhmuc.setLayoutManager(layoutManager);
        lvdanhmuc.setAdapter(danhMucAdapter);
        linhKienAdapter = new LinhKienAdapter(TrangChuFragment.this,manglinhkien);
        lvlinhkien.setAdapter(linhKienAdapter);

        main();
        BatSuKienNutBam();
        BatSuKienListView();
        return view;

    }
    void BatSuKienNutBam(){
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TimKiem.class);
                startActivity(intent);
            }
        });
        imggiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GioHang.class);
                startActivity(intent);
            }
        });
    }
    void BatSuKienListView(){
        lvlinhkien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int ma = manglinhkien.get(position).getId();
                String ten = manglinhkien.get(position).getTen();
                int gia = manglinhkien.get(position).getGia();
                int soluong = manglinhkien.get(position).getSoluong();
                String anh = manglinhkien.get(position).getAnh();
                String loai = manglinhkien.get(position).getLoai();
                String mota = manglinhkien.get(position).getMota();
                LinhKien lk = new LinhKien(ma,ten,gia,anh,loai,soluong,mota);
                Intent intent = new Intent(getContext(), ThongTinLinhKien.class);
                intent.putExtra("lk",lk);
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    public void onItemClick(int postion, String dayText) {
        if (dayText.equals("") == false) {
            String ma = mangdanhmuc.get(postion).getMa();
            Intent intent = new Intent(getActivity(), TimKiem.class);
            intent.putExtra("ma",ma);
            startActivity(intent);
        }
    }

    private void main() {
        if(!Checkconnect.haveNetworkConnection(getContext())){
            Checkconnect.ShowToast(getContext(),"Bạn hãy kiểm tra lại kết nối!");
        }else{
            getDuLieuDanhMuc();
            getDuLieuLinhKien();

        }
    }
    public void getDuLieuDanhMuc(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(sever.duongdangetdanhmuc,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            String ma="";
                            String ten="";
                            String anh;
                            Log.d("TAG", "onResponse: "+response.toString());
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    ma = jsonObject.getString("ma");
                                    ten = jsonObject.getString("ten");
                                    anh = jsonObject.getString("anh");
                                    mangdanhmuc.add(new DanhMuc(ma,ten,anh));
                                    danhMucAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconnect.ShowToast(getContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void getDuLieuLinhKien(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                                    manglinhkien.add(new LinhKien(id,ten,gia,anh,loai,soluong,mota));
                                    linhKienAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Checkconnect.ShowToast(getContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

}