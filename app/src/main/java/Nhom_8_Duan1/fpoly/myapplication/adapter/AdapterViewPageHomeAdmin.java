package Nhom_8_Duan1.fpoly.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Nhom_8_Duan1.fpoly.myapplication.fragment.Capquyen_kt_fragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.CheckNapFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.QuanLyHocVienFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.QuanLyLoadDataFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.ThongKe_TopPTFragment;

public class AdapterViewPageHomeAdmin extends FragmentStateAdapter {

    public AdapterViewPageHomeAdmin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = QuanLyLoadDataFragment.newInstance();
                break;
            case 1:
                fragment = QuanLyHocVienFragment.newInstance();
                break;
            case 2:
                fragment = ThongKe_TopPTFragment.newInstance();
                break;
            case 3:
                fragment = CheckNapFragment.newInstance();
                break;
            case 4:
                fragment = Capquyen_kt_fragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
