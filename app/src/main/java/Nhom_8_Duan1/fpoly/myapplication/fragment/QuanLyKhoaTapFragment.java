package Nhom_8_Duan1.fpoly.myapplication.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.IntelLoadData2;


public class QuanLyKhoaTapFragment extends Fragment implements InteLoadData, IntelLoadData2 {
    ListView lv;
    FloatingActionButton fab;
    KT kt;
    ArrayList<KT> list_KT;
    Button btnAddKT, btnHuyAddKT, btnEditKT, btnHuyEditKT;
    TextInputEditText edName_KT, edDate_KT, edGia_KT;
    AdapterListView_KT adapterListView_kt;
    TextView tv_info_name, tv_info_Date, tv_info_gia, tv_info_trangThai;
    ImageView img_avarta,imgCamera,img_Fodel,img_default;
    PT pt;


    public QuanLyKhoaTapFragment() {
        // Required empty public constructor
    }

    public static QuanLyKhoaTapFragment newInstance() {
        QuanLyKhoaTapFragment fragment = new QuanLyKhoaTapFragment();
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
        return inflater.inflate(R.layout.fragment_quan_ly_khoa_tap, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lis_KT);
        fab = view.findViewById(R.id.float_KT);
        Bundle bundle = getActivity().getIntent().getExtras();
        String user_PT = bundle.getString("userPT");
        pt = DatabaseGym.getInstance(getContext()).dao_pt().getPTtheoUser(user_PT).get(0);
        int id_pt = pt.getId_PT();
        loadData2(id_pt);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_kt_add);
                btnAddKT = dialog.findViewById(R.id.btnAddKT);
                btnHuyAddKT = dialog.findViewById(R.id.btnHuyAddKT);
                edName_KT = dialog.findViewById(R.id.edName_KT);
                edDate_KT = dialog.findViewById(R.id.edDate_KT);
                edGia_KT = dialog.findViewById(R.id.edGia_KT);
                img_avarta=dialog.findViewById(R.id.img_avata_KT);
                imgCamera=dialog.findViewById(R.id.img_cameraKT);
                img_Fodel=dialog.findViewById(R.id.img_fodelKT);
                imgCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,123);
                    }
                });
                img_Fodel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,456);
                    }
                });
                btnAddKT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate()){
                            String name_KT = edName_KT.getText().toString();
                            int date_KT = Integer.parseInt(edDate_KT.getText().toString());
                            int gia_KT = Integer.parseInt(edGia_KT.getText().toString());
                            pt = DatabaseGym.getInstance(getActivity()).dao_pt().getPTtheoUser(user_PT).get(0);
                            kt = new KT(pt.getId_PT(),name_KT, Image_To_Byte(img_avarta), date_KT, gia_KT, 0);
                            DatabaseGym.getInstance(getActivity()).dao_kt().insertKT(kt);
                            Toast.makeText(getActivity(), "Thành công!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            loadData2(id_pt);
                        }
                    }
                });
                btnHuyAddKT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                kt = list_KT.get(position);
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_show_info_kt);
                tv_info_name = dialog.findViewById(R.id.tv_info_nameKT);
                tv_info_Date = dialog.findViewById(R.id.tv_info_dateKT);
                tv_info_gia = dialog.findViewById(R.id.tv_info_giaKT);
                tv_info_trangThai = dialog.findViewById(R.id.tv_info_trangThai_KT);
                tv_info_name.setText(kt.getName_KhoaTap());
                tv_info_Date.setText(String.valueOf(kt.getSoNgayTap()));
                tv_info_gia.setText(String.valueOf(kt.getGiaKhoaTap()));
                int check = kt.getCapquyen();
                if(check == 0){
                    tv_info_trangThai.setText("Chưa cấp quyền");
                }
                else if(check == 1){
                    tv_info_trangThai.setText("Đã cấp quyền");
                }
                dialog.show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_avarta.setImageBitmap(bitmap);

        }
        if (requestCode == 456 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream stream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                img_avarta.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public byte[] Image_To_Byte(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }
    public void loadData2(int id) {
        list_KT = (ArrayList<KT>) DatabaseGym.getInstance(getActivity()).dao_kt().getAllKT(id);
        adapterListView_kt = new AdapterListView_KT(getActivity(), this::loadData2);
        adapterListView_kt.setdata(list_KT);
        lv.setAdapter(adapterListView_kt);
    }
    public boolean validate(){
        if (edName_KT.getText().toString().isEmpty()||edDate_KT.getText().toString().isEmpty()||
                edGia_KT.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        // validate check nhập số
        try {
            Integer.parseInt(edDate_KT.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(getActivity(), "thơif gian tập không đưọc nhập chữ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void loadData() {
        list_KT = (ArrayList<KT>) DatabaseGym.getInstance(getActivity()).dao_kt().getAllKT1();
        adapterListView_kt = new AdapterListView_KT(getActivity(), this::loadData);
        adapterListView_kt.setdata(list_KT);
        lv.setAdapter(adapterListView_kt);
    }

}