package Fragment;

import static com.example.banlinhkien.R.layout.support_simple_spinner_dropdown_item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import Adapter.LichSuAdapter;

import androidx.fragment.app.Fragment;

import com.example.banlinhkien.LichSu;
import com.example.banlinhkien.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichSuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LichSuFragment extends Fragment {
    ListView lvlichsu;
    ArrayList<LichSu> mangls;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LichSuFragment() {
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
    public static LichSuFragment newInstance(String param1, String param2) {
        LichSuFragment fragment = new LichSuFragment();
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
        View view = inflater.inflate(R.layout.fragment_lich_su, container, false);
        // Inflate the layout for this fragment
        lvlichsu = (ListView) view.findViewById(R.id.lvlichsu);
        mangls = new ArrayList<LichSu>();
        LichSuAdapter lichSuAdapter = new LichSuAdapter(LichSuFragment.this,mangls);
        mangls.add(new LichSu("Bàn phím cơ blustack",1000,2,"20/2/2021"));
        mangls.add(new LichSu("Bàn phím cơ blustack",1000,1,"20/2/2021"));
        mangls.add(new LichSu("Bàn phím cơ blustack",1000,3,"20/2/2021"));
        mangls.add(new LichSu("Bàn phím cơ blustack",1000,1,"20/2/2021"));
        lvlichsu.setAdapter(lichSuAdapter);
        lichSuAdapter.notifyDataSetChanged();
        return view;
    }
}