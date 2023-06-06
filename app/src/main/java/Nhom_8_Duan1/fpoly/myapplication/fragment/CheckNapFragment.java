package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_TT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class CheckNapFragment extends Fragment {

    ListView lv_TT;
    ArrayList<TT> list = new ArrayList<>();
    AdapterListView_TT adapter;
    TT tt;
    HV hv;


    public CheckNapFragment() {
        // Required empty public constructor
    }

    public static CheckNapFragment newInstance() {
        CheckNapFragment fragment = new CheckNapFragment();
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
        return inflater.inflate(R.layout.fragment_check_nap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_TT = view.findViewById(R.id.lvCheck_TT);
        loadData();
        lv_TT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tt = list.get(position);
                int money = tt.getTien_TT();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận nạp tiền");
//                builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
                builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id_hv_TT = tt.getId_HV();
                        hv = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoId(id_hv_TT).get(0);
                        int money_now = hv.getMonney();
                        hv.setMonney(money_now + money);
                        DatabaseGym.getInstance(getActivity()).dao_hv().updataHV(hv);
                        tt.setTrangThai_TT(1);
                        DatabaseGym.getInstance(getActivity()).dao_tt().updateTT(tt);
                        Toast.makeText(getActivity(), "Xác nhận thành công.", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                });
                builder.setNegativeButton("Xóa phiếu nạp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseGym.getInstance(getActivity()).dao_tt().deleteTT(tt);
                        Toast.makeText(getActivity(), "Đã xóa phiếu nạp.", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    public void loadData() {
        list = (ArrayList<TT>) DatabaseGym.getInstance(getActivity()).dao_tt().getAllTTChuaTT();
        adapter = new AdapterListView_TT(getActivity());
        adapter.setdata(list);
        lv_TT.setAdapter(adapter);
    }
}