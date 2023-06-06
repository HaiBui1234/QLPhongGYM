package Nhom_8_Duan1.fpoly.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Nhom_8_Duan1.fpoly.myapplication.fragment.HV_Da_Dki;
import Nhom_8_Duan1.fpoly.myapplication.fragment.QuanLyBaiTapFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.QuanLyKhoaTapFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.QuanLyThucDonFragment;

public class AdapterViewPageHomePT extends FragmentStateAdapter {

    public AdapterViewPageHomePT(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = QuanLyKhoaTapFragment.newInstance();
                break;
            case 1:
                fragment = QuanLyThucDonFragment.newInstance();
                break;
            case 2:
                fragment = QuanLyBaiTapFragment.newInstance();
                break;
            case 3:
                fragment= HV_Da_Dki.newInstance();
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
