package Nhom_8_Duan1.fpoly.myapplication.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.adapter.AdapterListView_PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.interfaces.InteLoadData;


public class QuanLyLoadDataFragment extends Fragment implements InteLoadData {
    ListView lv;
    ImageView imageView;
    FloatingActionButton fab;
    AdapterListView_PT adapterListView_pt;
    PT pt;
    ArrayList<PT> list = new ArrayList<>();
    Button btnAddPT, btnHuyAddPT, btnEditPT, btnHuyEditPT;
    TextInputEditText edUser_PT, edName_PT, edPass_PT, edRePass_PT, edEmail_PT, edPin_PT;
    TextView tv_info_name, tv_info_email, tv_info_phone;
    TextInputEditText edUser_PT_edit, edName_PT_edit, edPass_PT_edit;
    public QuanLyLoadDataFragment() {
        // Required empty public constructor
    }

    public static QuanLyLoadDataFragment newInstance() {
        QuanLyLoadDataFragment fragment = new QuanLyLoadDataFragment();
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
        return inflater.inflate(R.layout.fragment_quan_ly_pt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = view.findViewById(R.id.lis_PT);
        fab = view.findViewById(R.id.float_pt);
        imageView=new ImageView(getActivity());
        imageView.setImageResource(R.drawable.img_avata);
        loadData();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_pt_add);
                btnAddPT = dialog.findViewById(R.id.btnAddPT);
                btnHuyAddPT = dialog.findViewById(R.id.btnHuyAddPT);
                edUser_PT = dialog.findViewById(R.id.edUserName_PT);
                edName_PT = dialog.findViewById(R.id.edName_PT);
                edEmail_PT = dialog.findViewById(R.id.edEmailPT);
                edPass_PT = dialog.findViewById(R.id.edPass_PT);
                edRePass_PT = dialog.findViewById(R.id.edRePass_PT);
                edPin_PT = dialog.findViewById(R.id.edPin_PT);
                btnAddPT.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (validate()){
                           String user_PT = edUser_PT.getText().toString();
                           String name_PT = edName_PT.getText().toString();
                           String email_PT = edEmail_PT.getText().toString();
                           String pass_PT = edPass_PT.getText().toString();
                           String rePass_PT = edRePass_PT.getText().toString();
                           int pin_PT = Integer.parseInt(edPin_PT.getText().toString());
                           //set thuộc tính PT
                           pt = new PT(user_PT, name_PT,Image_to_bye(imageView), pass_PT, email_PT, pin_PT);
                           //Add pt vào database
                           DatabaseGym.getInstance(getActivity()).dao_pt().insertPT(pt);
                           loadData();
                           Log.d("zzz", "onViewCreated: "+ list.size());
                           dialog.dismiss();
                       }
                    }
                });
                btnHuyAddPT.setOnClickListener(new View.OnClickListener() {
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
                pt = list.get(position);
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_show_info_pt);
                ImageView imageView1=dialog.findViewById(R.id.img_hvshow);
                tv_info_name = dialog.findViewById(R.id.tv_info_name);
                tv_info_email = dialog.findViewById(R.id.tv_info_email);
                tv_info_phone = dialog.findViewById(R.id.tv_info_phone);
                Bitmap bitmap= BitmapFactory.decodeByteArray(pt.getAvatar_PT(),0,pt.getAvatar_PT().length);
                imageView1.setImageBitmap(bitmap);
                tv_info_name.setText(pt.getName_PT());
                tv_info_email.setText(pt.getEmail_PT());
                tv_info_phone.setText(pt.getPhone_PT());
                dialog.show();
            }
        });
    }

    @Override
    public void loadData() {
        list = (ArrayList<PT>) DatabaseGym.getInstance(getActivity()).dao_pt().getAllPT();
        adapterListView_pt = new AdapterListView_PT(getActivity(),this::loadData);
        adapterListView_pt.setdata(list);
        lv.setAdapter(adapterListView_pt);
    }


    public byte[] Image_to_bye(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public boolean validate(){
        //validate k bỏ trống nhập
        if(edUser_PT.getText().length() == 0 || edName_PT.getText().length() == 0 ||
                edEmail_PT.getText().length() == 0 || edPass_PT.getText().length() == 0 ||
                edRePass_PT.getText().length() == 0 || edPin_PT.getText().length() == 0){
            Toast.makeText(getActivity(), "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // validate check user
        String user = edUser_PT.getText().toString();
        List<PT> list = DatabaseGym.getInstance(getActivity()).dao_pt().getPTtheoUser(user);
        if(list.size() > 0){
            Toast.makeText(getActivity(), "User name đã tồn tại!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // validate check pass và repass
        if(!(edPass_PT.getText().toString().equals(edRePass_PT.getText().toString()))){
            Toast.makeText(getActivity(), "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //validate Email
        String email = edEmail_PT.getText().toString().trim();
        String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!email.matches(emailPattern)){
            Toast.makeText(getActivity(), "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //validate check độ dài pin
        if(!(edPin_PT.getText().length() == 4)){
            Toast.makeText(getActivity(), "Pin gồm 4 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // validate kiểu pin
        try {
            Integer.parseInt(edPin_PT.getText().toString());
        }
        catch (Exception e){
//            e.printStackTrace();
            Toast.makeText(getActivity(), "Pin gồm 4 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}