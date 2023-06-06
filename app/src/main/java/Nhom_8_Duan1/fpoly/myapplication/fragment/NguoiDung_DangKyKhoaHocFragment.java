package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_DK;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class NguoiDung_DangKyKhoaHocFragment extends Fragment {
    ListView lv;
    ArrayList<KT> list;
    KT kt;
    DK dk;
    HV hv;
    PT pt;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear, mMonth, mDay;
    AdapterListView_DK adapterListView_dk;
    public NguoiDung_DangKyKhoaHocFragment() {
        // Required empty public constructor
    }
    public static NguoiDung_DangKyKhoaHocFragment newInstance() {
        NguoiDung_DangKyKhoaHocFragment fragment = new NguoiDung_DangKyKhoaHocFragment();
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
        return inflater.inflate(R.layout.fragment_nguoi_dung__dang_ky_khoa_hoc, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        int idHV = bundle.getInt("idHV");
        lv = view.findViewById(R.id.lv_dki_kt);
        loadData();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kt = list.get(position);
                hv = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoId(idHV).get(0);
                dk = new DK();
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("Đăng ký");
                builder.setMessage("Bạn muốn đăng ký khóa tập này ?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int a) {
                        if(checkAddDK(kt.getId_KhoaTap(), idHV)){
                            dk.setId_KhoaTap(kt.getId_KhoaTap());
                            dk.setId_PT(kt.getId_PT());
                            dk.setId_HV(hv.getId_HV());
                            dk.setGia(kt.getGiaKhoaTap());
                            dk.setThanhToan(0);
                            dk.setDate(sdf.format(c.getTime()));
                            DatabaseGym.getInstance(getContext()).dao_dk().insertDK(dk);
                            Log.d("zzz", "onClick: " + dk.getDate());
                            list.clear();
                            loadData();
                            Toast.makeText(getActivity(), "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("NO",null);
                builder.show();
            }
        });
    }
    private void loadData() {
        list = (ArrayList<KT>) DatabaseGym.getInstance(getActivity()).dao_kt().getKTDaCapQuyen();
        adapterListView_dk = new AdapterListView_DK(getActivity());
        adapterListView_dk.setdata(list);
        lv.setAdapter(adapterListView_dk);
    }
    public boolean checkAddDK(int id_KT, int id_hv){
        if(DatabaseGym.getInstance(getContext()).dao_dk().checkKTDK(id_KT, id_hv).size() >= 1){
            Toast.makeText(getContext(), "Khóa tập này đã được đăng kí!!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

//    @Override
//    public void onResume(int id) {
//        super.onResume();
//        loadData(id);
//    }
}