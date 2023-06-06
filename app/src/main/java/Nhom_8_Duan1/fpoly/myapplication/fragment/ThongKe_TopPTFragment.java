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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_DK_Top;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class ThongKe_TopPTFragment extends Fragment {
    ListView lv;
    AdapterListView_DK_Top adapterListView_dk_top;
    ArrayList<PT> list;
    PT pt;
    TextView tv_info_name, tv_info_email, tv_info_phone;

    public ThongKe_TopPTFragment() {
        // Required empty public constructor
    }


    public static ThongKe_TopPTFragment newInstance() {
        ThongKe_TopPTFragment fragment = new ThongKe_TopPTFragment();
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
        return inflater.inflate(R.layout.fragment_thong_ke__top_p_t, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.list_xh);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pt = list.get(position);
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_show_info_pt);
                tv_info_name = dialog.findViewById(R.id.tv_info_name);
                tv_info_email = dialog.findViewById(R.id.tv_info_email);
                tv_info_phone = dialog.findViewById(R.id.tv_info_phone);
                tv_info_name.setText(pt.getName_PT());
                tv_info_email.setText(pt.getEmail_PT());
                tv_info_phone.setText(pt.getPhone_PT());
                dialog.show();
            }
        });
        loadData();
    }
    public void loadData() {
        list = (ArrayList<PT>) DatabaseGym.getInstance(getActivity()).dao_pt().getPTTop();
        adapterListView_dk_top = new AdapterListView_DK_Top(getActivity(),this::loadData);
        adapterListView_dk_top.setdata(list);
        lv.setAdapter(adapterListView_dk_top);
    }
}