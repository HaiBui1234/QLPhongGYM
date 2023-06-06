package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterViewPageHomeAdmin;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterViewPageHomeHocVien;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterViewPageHomePT;

public class HomeFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    AdapterViewPageHomeAdmin adapterViewPageHomeAdmin;
    AdapterViewPageHomePT adapterViewPageHomePT;
    AdapterViewPageHomeHocVien adapterViewPageHomeHocVien;
    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ánh xạ biến
        tabLayout = view.findViewById(R.id.TabLayout_HomeAdmin);
        viewPager2 = view.findViewById(R.id.viewPage_HomeAdmin);
        savedInstanceState = getActivity().getIntent().getExtras();
        String permission = savedInstanceState.getString("value");
        //liên kết tablauout vs viewpager2
        if(permission.equalsIgnoreCase("ADMIN")){
            adapterViewPageHomeAdmin = new AdapterViewPageHomeAdmin(getActivity());
            viewPager2.setAdapter(adapterViewPageHomeAdmin);
            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    switch (position) {
                        case 0:
                            tab.setText("PT");
                            break;
                        case 1:
                            tab.setText("Học Viên");
                            break;
                        case 2:
                            tab.setText("Xếp Hạng");
                            break;
                        case  3:
                            tab.setText("Phiếu nạp");
                            break;
                        case 4:
                            tab.setText("Khóa tập");
                            break;
                    }
                }
            });
            tabLayoutMediator.attach();
        }
        if(permission.equalsIgnoreCase("PT")){
            adapterViewPageHomePT = new AdapterViewPageHomePT(getActivity());
            viewPager2.setAdapter(adapterViewPageHomePT);
            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    switch (position) {
                        case 0:
                            tab.setText("Khóa tập");
                            break;
                        case 1:
                            tab.setText("Thực đơn");
                            break;
                        case 2:
                            tab.setText("Bài tập");
                            break;
                        case 3:
                            tab.setText("Học Viên");
                    }
                }
            });
            tabLayoutMediator.attach();
        }
        if(permission.equalsIgnoreCase("Học viên")){
            adapterViewPageHomeHocVien = new AdapterViewPageHomeHocVien(getActivity());
            viewPager2.setAdapter(adapterViewPageHomeHocVien);
            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                    switch (position) {
                        case 0:
                            tab.setText("Đăng ký khóa tập");
                            break;
                        case 1:
                            tab.setText("Thông tin khóa tập");
                            break;
                    }
                }
            });
            tabLayoutMediator.attach();
        }
    }
}