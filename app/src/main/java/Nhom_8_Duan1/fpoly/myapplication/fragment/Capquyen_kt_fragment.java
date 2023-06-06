package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_KT;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_KT_capQuyen;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;


public class Capquyen_kt_fragment extends Fragment {

    ListView lv;
    ArrayList<KT> list = new ArrayList<>();
    AdapterListView_KT_capQuyen adapterListView_kt_capQuyen;
    TextView tv_info_name, tv_info_Date, tv_info_gia, tv_info_trangThai;
    KT kt;
    ArrayAdapter<KT> adapter;
    Spinner spinner;


    public Capquyen_kt_fragment() {
        // Required empty public constructor
    }


    public static Capquyen_kt_fragment newInstance() {
        Capquyen_kt_fragment fragment = new Capquyen_kt_fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_capquyen_kt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spin_capquyen);
        lv = view.findViewById(R.id.lvCapQuyen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.Cap_quyen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        loadDataChuaCapQuyen();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String values_capquyen = spinner.getSelectedItem().toString();
                if(values_capquyen.equalsIgnoreCase("Chưa cấp quyền")){
                    loadDataChuaCapQuyen();
                }
                else if(values_capquyen.equalsIgnoreCase("Đã cấp quyền")){
                    loadDataDaCapQuyen();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kt = list.get(position);
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_show_info_kt);
                tv_info_name = dialog.findViewById(R.id.tv_info_nameKT);
                tv_info_Date = dialog.findViewById(R.id.tv_info_dateKT);
                tv_info_gia = dialog.findViewById(R.id.tv_info_giaKT);
                tv_info_trangThai = dialog.findViewById(R.id.tv_info_trangThai_KT);
                tv_info_name.setText(kt.getName_KhoaTap());
                tv_info_Date.setText(String.valueOf(kt.getSoNgayTap()));
                tv_info_gia.setText(String.valueOf(kt.getGiaKhoaTap()));
                int check = kt.getCapquyen();
                if(check == 0){
                    tv_info_trangThai.setText("Chưa cấp quyền");
                }
                else if(check == 1){
                    tv_info_trangThai.setText("Đã cấp quyền");
                }
                dialog.show();
            }
        });
    }
    private void loadDataChuaCapQuyen() {
        list = (ArrayList<KT>) DatabaseGym.getInstance(getActivity()).dao_kt().getKTChuaCapQuyen();
        adapterListView_kt_capQuyen = new AdapterListView_KT_capQuyen(getActivity(), this::loadDataChuaCapQuyen);
        adapterListView_kt_capQuyen.setdata(list);
        lv.setAdapter(adapterListView_kt_capQuyen);
    }
    private void loadDataDaCapQuyen() {
        list = (ArrayList<KT>) DatabaseGym.getInstance(getActivity()).dao_kt().getKTDaCapQuyen();
        adapterListView_kt_capQuyen = new AdapterListView_KT_capQuyen(getActivity(), this::loadDataDaCapQuyen);
        adapterListView_kt_capQuyen.setdata(list);
        lv.setAdapter(adapterListView_kt_capQuyen);
    }
}