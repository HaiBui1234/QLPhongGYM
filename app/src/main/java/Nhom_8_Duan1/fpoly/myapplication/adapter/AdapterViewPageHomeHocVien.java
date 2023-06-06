package Nhom_8_Duan1.fpoly.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import Nhom_8_Duan1.fpoly.myapplication.fragment.NguoiDung_DangKyKhoaHocFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.NguoiDung_KhoaTapDaDKFragment;

public class AdapterViewPageHomeHocVien extends FragmentStateAdapter {

    public AdapterViewPageHomeHocVien(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = NguoiDung_DangKyKhoaHocFragment.newInstance();
                break;
            case 1:
                fragment = NguoiDung_KhoaTapDaDKFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
