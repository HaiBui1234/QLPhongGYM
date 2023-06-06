package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.ManHinhDk;
import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;

public class QuanLyHocVienFragment extends Fragment implements InteLoadData {
    ListView lv;
    FloatingActionButton fab;
    ImageView imageView;
    HV hv;
    ArrayList<HV> list = new ArrayList<>();
    Button btnAddHV, btnHuyAddHV, btnEditHV, btnHuyEditHV;
    TextInputEditText edUser_HV, edName_HV, edPass_HV, edRePass_HV, edEmail_HV, edPin_HV;
    AdapterListView_HV adapter;
    TextView tv_info_name, tv_info_email, tv_info_phone;
    TextInputEditText edUser_HV_edit, edName_HV_edit, edPass_HV_edit;


    public QuanLyHocVienFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static QuanLyHocVienFragment newInstance() {
        QuanLyHocVienFragment fragment = new QuanLyHocVienFragment();
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
        return inflater.inflate(R.layout.fragment_quan_ly_hoc_vien, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedInstanceState = getActivity().getIntent().getExtras();
        String permission = savedInstanceState.getString("value");
        lv = view.findViewById(R.id.lis_HV);
        fab = view.findViewById(R.id.float_hv);
        imageView=new ImageView(getActivity());
        imageView.setImageResource(R.drawable.img_avata);
        loadData();
        if (!permission.equals("ADMIN")){
            fab.setVisibility(View.INVISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_hv_add);
                btnAddHV = dialog.findViewById(R.id.btnAddHV);
                btnHuyAddHV = dialog.findViewById(R.id.btnHuyAddHV);
                edUser_HV = dialog.findViewById(R.id.edUserName_HV);
                edName_HV = dialog.findViewById(R.id.edName_HV);
                edEmail_HV = dialog.findViewById(R.id.edEmailHV);
                edPass_HV = dialog.findViewById(R.id.edPass_HV);
                edRePass_HV = dialog.findViewById(R.id.edRePass_HV);
                edPin_HV = dialog.findViewById(R.id.edPin_HV);
                btnAddHV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate()){
                            String user_HV = edUser_HV.getText().toString();
                            String name_HV = edName_HV.getText().toString();
                            String email_HV = edEmail_HV.getText().toString();
                            String pass_HV = edPass_HV.getText().toString();
                            String rePass_HV = edRePass_HV.getText().toString();
                            int pin_HV = Integer.parseInt(edPin_HV.getText().toString());
                            //set thuộc tính HV
                            hv = new HV(user_HV,name_HV,Image_To_byte(imageView),pass_HV,email_HV,pin_HV);
                            //Add hv vào database
                            DatabaseGym.getInstance(getActivity()).dao_hv().insertHV(hv);
                            //View list hv lên màn hình
                            loadData();
                            Log.d("zzz", "onViewCreated: " + list.size());
                            dialog.dismiss();
                        }
                    }
                });
                btnHuyAddHV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hv = list.get(position);
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_show_info_pt);
                ImageView imageView=dialog.findViewById(R.id.img_hvshow);
                tv_info_name = dialog.findViewById(R.id.tv_info_name);
                tv_info_email = dialog.findViewById(R.id.tv_info_email);
                tv_info_phone = dialog.findViewById(R.id.tv_info_phone);
                Bitmap bitmap= BitmapFactory.decodeByteArray(hv.getAvatar_HV(),0,hv.getAvatar_HV().length);
                imageView.setImageBitmap(bitmap);
                tv_info_name.setText(hv.getName_HV());
                tv_info_email.setText(hv.getEmail_HV());
                tv_info_phone.setText(hv.getPhone_HV());
                dialog.show();
            }
        });
    }

    public void loadData() {
        list = (ArrayList<HV>) DatabaseGym.getInstance(getActivity()).dao_hv().getAllHV();
        adapter = new AdapterListView_HV(getActivity(), this::loadData);
        adapter.setdata(list);
        lv.setAdapter(adapter);
    }
    public byte[] Image_To_byte(ImageView imageView){
        BitmapDrawable drawable= (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap= drawable.getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }

    public boolean validate(){
        //validate k bỏ trống nhập
        if(edUser_HV.getText().length() == 0 || edName_HV.getText().length() == 0 ||
                edEmail_HV.getText().length() == 0 || edPass_HV.getText().length() == 0 ||
                edRePass_HV.getText().length() == 0 || edPin_HV.getText().length() == 0){
            Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // validate check user
        String user = edUser_HV.getText().toString();
        List<HV> list = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoUser(user);
        if(list.size() > 0){
            Toast.makeText(getActivity(), "User name đã tồn tại!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // validate check pass và repass
        if(!(edPass_HV.getText().toString().equals(edRePass_HV.getText().toString()))){
            Toast.makeText(getActivity(), "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //validate Email
        String email = edEmail_HV.getText().toString().trim();
        String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!email.matches(emailPattern)){
            Toast.makeText(getActivity(), "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //validate check độ dài pin
        if(!(edPin_HV.getText().length() == 4)){
            Toast.makeText(getActivity(), "Pin gồm 4 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // validate kiểu pin
        try {
            Integer.parseInt(edPin_HV.getText().toString());
        }
        catch (Exception e){
//            e.printStackTrace();
            Toast.makeText(getActivity(), "Pin gồm 4 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
