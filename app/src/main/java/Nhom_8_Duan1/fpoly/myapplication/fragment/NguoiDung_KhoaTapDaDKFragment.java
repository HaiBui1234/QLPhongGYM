package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.MainActivity_admin;
import Nhom_8_Duan1.fpoly.myapplication.ManHinhLogin;
import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_DK;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_KT_Da_DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;


public class NguoiDung_KhoaTapDaDKFragment extends Fragment {
    ListView lv;
    ArrayList<DK> list = new ArrayList<>();
    AdapterListView_KT_Da_DK adapterListView_kt_da_dk;
    DK dk;
    HV hv;
    Button btnThanhToan, btnHuyNap;
    TextView tv_nav_name, tv_nav_email, tv_name_main, tv_money_main, tv_hello_time, tv_hello_permission;
    TextView btnNap, tvhd_nap;
    TextInputEditText ed_so_tien;
    CheckBox ck_nap;
    ImageView img_avatar_main, img_avatar_nav;
    TT tt;

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public NguoiDung_KhoaTapDaDKFragment() {
        // Required empty public constructor
    }

    public static NguoiDung_KhoaTapDaDKFragment newInstance() {
        NguoiDung_KhoaTapDaDKFragment fragment = new NguoiDung_KhoaTapDaDKFragment();
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
        return inflater.inflate(R.layout.fragment_khoa_tap_da_d_k, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lv_da_dki);
        String userHV = getBundle();
        onResume();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dk = list.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông báo!");
                builder.setMessage("Bạn muôn làm gì ?");
                builder.setPositiveButton("Thanh toán khóa tập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int money_hv = hv.getMonney();
                        int money_kt = dk.getGia();
                        if (money_hv < money_kt) {
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setTitle("Thông báo!");
                            builder1.setMessage("Tài khoản của bạn không dủ, vui lòng nạp thêm!");
                            builder1.setPositiveButton("Nạp tiền", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Dialog dialogNap = new Dialog(getActivity());
                                    dialogNap.setContentView(R.layout.dialog_nap);
                                    tvhd_nap = dialogNap.findViewById(R.id.tv_hd_nap);
                                    ck_nap = dialogNap.findViewById(R.id.ck_conform_nap);
                                    btnThanhToan = dialogNap.findViewById(R.id.btnConform_nap);
                                    btnHuyNap = dialogNap.findViewById(R.id.btnHuy_nap);
                                    ed_so_tien = dialogNap.findViewById(R.id.edMoney_nap);
                                    ck_nap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            if (isChecked) {
                                                if (ed_so_tien.getText().toString().length() == 0) {
                                                    btnThanhToan.setEnabled(false);
                                                }
                                                btnThanhToan.setEnabled(true);
                                            } else btnThanhToan.setEnabled(false);
                                        }
                                    });
                                    btnThanhToan.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            hv = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoUser(userHV).get(0);
                                            tt = new TT();
                                            tt.setTien_TT(Integer.parseInt(ed_so_tien.getText().toString()));
                                            tt.setTrangThai_TT(0);
                                            tt.setId_HV(hv.getId_HV());
                                            long millis = System.currentTimeMillis();
                                            Date date = new Date(millis);
                                            tt.setTime(getDateString(date));
                                            DatabaseGym.getInstance(getActivity()).dao_tt().insertTT(tt);
                                            Toast.makeText(getActivity(), "Vui lòng đợi hệ thống xử lí...", Toast.LENGTH_SHORT).show();
//                        int money_now = hv.getMonney();
//                        hv.setMonney(money_now + Integer.parseInt(ed_so_tien.getText().toString()));
//                        DatabaseGym.getInstance(MainActivity_admin.this).dao_hv().updataHV(hv);
//                        Toast.makeText(MainActivity_admin.this, "Đã thanh toán thành công", Toast.LENGTH_SHORT).show();
//                        tv_money_main.setText(String.valueOf(hv.getMonney())+"đ");
                                            dialogNap.dismiss();
                                        }
                                    });
                                    btnHuyNap.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialogNap.dismiss();
                                        }
                                    });
                                    tvhd_nap.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Dialog dialogHD = new Dialog(dialogNap.getContext());
                                            dialogHD.setContentView(R.layout.dialog_hd_nap);
                                            dialogHD.findViewById(R.id.btnDaHieu).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialogHD.dismiss();
                                                }
                                            });
                                            dialogHD.show();
                                        }
                                    });
                                    dialogNap.show();
                                }
                            });
                            builder1.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            AlertDialog alertDialog1 = builder1.create();
                            alertDialog1.show();
                        } else {
                            dk.setThanhToan(1);
                            DatabaseGym.getInstance(getActivity()).dao_dk().updateDK(dk);
                            hv.setMonney(money_hv - money_kt);
                            DatabaseGym.getInstance(getActivity()).dao_hv().updataHV(hv);
                            tv_money_main = getActivity().findViewById(R.id.tv_money_main);
                            tv_money_main.setText(hv.getMonney() + "Đ");
                            loadData();
                        }
                    }
                });
                builder.setNegativeButton("Xóa khóa tập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseGym.getInstance(getActivity()).dao_dk().deleteDK(dk);
//                        dk.setThanhToan(0);
//                        DatabaseGym.getInstance(getActivity()).dao_dk().updateDK(dk);
                        Toast.makeText(getActivity(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        loadData();
                    }
                });
                if (dk.getThanhToan() == 1) {
                    builder.setPositiveButton("", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                }
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void loadData() {
        String userHV = getBundle();
        hv = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoUser(userHV).get(0);
        int id = hv.getId_HV();
        list = (ArrayList<DK>) DatabaseGym.getInstance(getActivity()).dao_dk().getAllDKWhereID(id);
        adapterListView_kt_da_dk = new AdapterListView_KT_Da_DK(getActivity());
        adapterListView_kt_da_dk.setdata(list);
        lv.setAdapter(adapterListView_kt_da_dk);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private String getBundle(){
        Bundle bundle = getActivity().getIntent().getExtras();
        String userHV = bundle.getString("userHV");
        return userHV;
    }

    public static String getDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }
}