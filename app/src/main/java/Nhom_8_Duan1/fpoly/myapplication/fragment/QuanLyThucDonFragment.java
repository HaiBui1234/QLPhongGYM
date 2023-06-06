package Nhom_8_Duan1.fpoly.myapplication.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_TD;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.BT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.KT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TD;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.IntelLoadData2;

public class QuanLyThucDonFragment extends Fragment implements IntelLoadData2 {
    ListView lv_TD;
    FloatingActionButton FlAdd_TD;
    ArrayList<TD> list_TD;
    Spinner spLocTD;
    EditText edName_TD,edGhiChu;
    AdapterListView_TD adapterListView_td;
    TextView tv_info_name, tv_info_gc;
    KT kt;
    ArrayList<KT> list1;
    ImageView img_avarta,imgCamera,img_Fodel,img_default;
    public QuanLyThucDonFragment() {
        // Required empty public constructor
    }


    public static QuanLyThucDonFragment newInstance() {
        QuanLyThucDonFragment fragment = new QuanLyThucDonFragment();

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
        return inflater.inflate(R.layout.fragment_quan_ly_thuc_don, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv_TD = view.findViewById(R.id.lis_TD);
        FlAdd_TD = view.findViewById(R.id.float_TD);
        spLocTD=view.findViewById(R.id.spLocTD);
        list1= (ArrayList<KT>) DatabaseGym.getInstance(getActivity()).dao_kt().getAllKT1();
        ArrayAdapter<KT> adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,list1);
        spLocTD.setAdapter(adapter);
        spLocTD.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kt = list1.get(i);
                int id = kt.getId_KhoaTap();
                list_TD = (ArrayList<TD>) DatabaseGym.getInstance(getActivity()).dao_td().getid(id);
                loadData2(id);
//                Toast.makeText(getActivity(), ""+list1.get(i).getName_KhoaTap(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        FlAdd_TD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_td_add);
                Button btnAddTD = dialog.findViewById(R.id.btnAddtd);
                Button btnHuyAddTD = dialog.findViewById(R.id.btnHuyAddtd);
                edName_TD = dialog.findViewById(R.id.edName_td);
                edGhiChu = dialog.findViewById(R.id.edGhiChutd);
                Spinner spKT=dialog.findViewById(R.id.spKT);
                ArrayList<KT> list;
                list= (ArrayList<KT>) DatabaseGym.getInstance(getActivity()).dao_kt().getAllKT1();
                ArrayAdapter<KT> adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,list);
                spKT.setAdapter(adapter);
                img_avarta=dialog.findViewById(R.id.img_avata_TD);
                imgCamera=dialog.findViewById(R.id.img_cameraTD);
                img_Fodel=dialog.findViewById(R.id.img_fodelTD);
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
                btnAddTD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (validate()){
                            KT kt= (KT) spKT.getSelectedItem();
                            TD td = new TD(edName_TD.getText().toString(),Image_To_Byte(img_avarta), edGhiChu.getText().toString(),kt.getId_KhoaTap());
                            DatabaseGym.getInstance(getActivity()).dao_td().insertTD(td);
                            list_TD = (ArrayList<TD>) DatabaseGym.getInstance(getActivity()).dao_td().getid(kt.getId_KhoaTap());
                            loadData2(kt.getId_KhoaTap());
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnHuyAddTD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    public void loadData2(int id) {
        list_TD = (ArrayList<TD>) DatabaseGym.getInstance(getActivity()).dao_td().getid(kt.getId_KhoaTap());
        adapterListView_td = new AdapterListView_TD(getActivity(), this::loadData2);
        adapterListView_td.setdata(list_TD);
        lv_TD.setAdapter(adapterListView_td);
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

    public boolean validate(){
        if (edName_TD.getText().toString().isEmpty()||edGhiChu.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        list1= (ArrayList<KT>) DatabaseGym.getInstance(getActivity()).dao_kt().getAllKT1();
        ArrayAdapter<KT> adapter=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item,list1);
        spLocTD.setAdapter(adapter);

    }
}