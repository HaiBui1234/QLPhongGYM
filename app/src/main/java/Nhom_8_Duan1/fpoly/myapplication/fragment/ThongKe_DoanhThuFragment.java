package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.DK;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class ThongKe_DoanhThuFragment extends Fragment {

    Button btnDoanhThu, btnTongDoanhThu;
    EditText edTuNgay, edDenNgay;
    TextView tvDoanhThu;
    long tongthu = 0;
    long tongThuDate = 0;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    int mYear, mMonth, mDay;
    List<DK> list = new ArrayList<>();
    List<DK> listDoanhThu = new ArrayList<>();


    public ThongKe_DoanhThuFragment() {
        // Required empty public constructor
    }

    public static ThongKe_DoanhThuFragment newInstance() {
        ThongKe_DoanhThuFragment fragment = new ThongKe_DoanhThuFragment();
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
        return inflater.inflate(R.layout.fragment_thong_ke__doanh_thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edTuNgay = view.findViewById(R.id.edt_ngayBatDau);
        edDenNgay = view.findViewById(R.id.edt_ngayKetThuc);
        tvDoanhThu = view.findViewById(R.id.tv_doanhthu);
        btnDoanhThu = view.findViewById(R.id.btnDoanhThu);
        btnTongDoanhThu = view.findViewById(R.id.btnTongDoanhThu);

        edTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        edDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        //tổng doanh thu
        btnTongDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list = DatabaseGym.getInstance(getActivity()).dao_dk().getAllDK();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getThanhToan() == 1){
                        tongthu = tongthu + list.get(i).getGia();
                    }
                }
                if (String.valueOf(tongthu).length() > 9){
                    Toast.makeText(getActivity(), String.valueOf(tongthu).length()+"" , Toast.LENGTH_SHORT).show();
                    tongthu = (long) (Math.floor((tongthu / 1000000000)*100)/100);
                    tvDoanhThu.setText(tongthu+" Tỷ");
                }else if (String.valueOf(tongthu).length() > 6){
                    tongthu = (long) (Math.floor((tongthu / 1000000)*100)/100);
                    tvDoanhThu.setText(tongthu+" Tr");
                }else{
                        tvDoanhThu.setText(tongthu+"");
                    }
//                tvDoanhThu.setText(String.valueOf(tongthu));
            }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    //tính tiền theo ngày
                    listDoanhThu = DatabaseGym.getInstance(getActivity()).dao_dk().getDoanhThu(edTuNgay.getText().toString(), edDenNgay.getText().toString());
                    if(listDoanhThu.size() == 0){
                        tongThuDate = 0;
                        tvDoanhThu.setText(String.valueOf(tongThuDate));
                    }
                    else {
                        for (int i = 0; i < listDoanhThu.size(); i++) {
                            if (listDoanhThu.get(i).getThanhToan() == 1){
                                tongThuDate = tongThuDate + listDoanhThu.get(i).getGia();
                            }
                        }
                        if (String.valueOf(tongThuDate).length() > 9){
                            Toast.makeText(getActivity(), String.valueOf(tongThuDate).length()+"" , Toast.LENGTH_SHORT).show();
                            tongThuDate = (long) (Math.floor((tongThuDate / 1000000000)*100)/100);
                            tvDoanhThu.setText(tongThuDate+" Tỷ");
                        }else if (String.valueOf(tongThuDate).length() > 6){
                            tongThuDate = (long) (Math.floor((tongThuDate / 1000000)*100)/100);
                            tvDoanhThu.setText(tongThuDate+" Tr");
                        }else{
                            tvDoanhThu.setText(tongThuDate+"");
                        }
                    }
                }

//                    Log.d("zzz", "onClick: " + tongThuDate);
            }
        });
    }

    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edTuNgay.setText(sdf.format(c.getTime()));
        }
    };

    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar(mYear, mMonth, mDay);
            edDenNgay.setText(sdf.format(c.getTime()));
        }
    };

    //

    public boolean validate(){
        if (edTuNgay.toString().compareTo(edDenNgay.toString()) >= 0){
            Toast.makeText(getActivity(), "không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}