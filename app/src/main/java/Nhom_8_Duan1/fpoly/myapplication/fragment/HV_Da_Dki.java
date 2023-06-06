package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_HV_DA_DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class HV_Da_Dki extends Fragment {
    ListView lv;
    ArrayList<DK> list;
    AdapterListView_HV_DA_DK adapterListView_hv_da_dki;
    PT pt;
    DK dk;

    public HV_Da_Dki() {
        // Required empty public constructor
    }


    public static HV_Da_Dki newInstance() {
        HV_Da_Dki fragment = new HV_Da_Dki();
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
        return inflater.inflate(R.layout.fragment_h_v__da__dki, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lv_HV_da_dki);
        Bundle bundle = getActivity().getIntent().getExtras();
        String userPT = bundle.getString("userPT");
        pt = DatabaseGym.getInstance(getContext()).dao_pt().getPTtheoUser(userPT).get(0);
        int id_pt = pt.getId_PT();
        loadData(id_pt);
    }
    private void loadData(int id) {
        list = (ArrayList<DK>) DatabaseGym.getInstance(getActivity()).dao_dk().getHVDki(id);
        adapterListView_hv_da_dki = new AdapterListView_HV_DA_DK(getActivity());
        adapterListView_hv_da_dki.setdata(list);
        lv.setAdapter(adapterListView_hv_da_dki);
    }
}