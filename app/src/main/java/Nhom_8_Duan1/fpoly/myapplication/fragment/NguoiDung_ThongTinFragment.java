package Nhom_8_Duan1.fpoly.myapplication.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.MainActivity_admin;
import Nhom_8_Duan1.fpoly.myapplication.R;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class NguoiDung_ThongTinFragment extends Fragment {

    ArrayList<PT> listPT = new ArrayList<>();
    ArrayList<HV> listHV = new ArrayList<>();
    ImageView imageCamere, imgFodel, imgAvarta, img_avatar_main, img_avatar_nav, edit_name_TT, edit_gmail_TT, edit_phone_TT;
    NavigationView navigationView;
    Activity activity = new MainActivity_admin();
    TextView name, email, phone, name_main, name_nav, email_nav;
    Button btnsave;
    int REQUET_CODE_CAMERA = 123;
    PT pt;
    HV hv;
    int REQUET_CODE_FODEL = 456;
    public NguoiDung_ThongTinFragment() {
        // Required empty public constructor
    }

    public static NguoiDung_ThongTinFragment newInstance() {
        NguoiDung_ThongTinFragment fragment = new NguoiDung_ThongTinFragment();
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
        return inflater.inflate(R.layout.fragment_nguoi_dung__thong_tin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        savedInstanceState = getActivity().getIntent().getExtras();
        String userPT = savedInstanceState.getString("userPT");
        //ánh xa biến
        imageCamere = view.findViewById(R.id.img_edt_avata_camere);
        imgFodel = view.findViewById(R.id.img_edt_avata_fodel);
        imgAvarta = view.findViewById(R.id.img_avata_md);
        edit_name_TT = view.findViewById(R.id.btn_edit_name_TT);
        edit_gmail_TT = view.findViewById(R.id.btn_edit_gmail_TT);
        edit_phone_TT = view.findViewById(R.id.btn_edit_Phone_TT);
        btnsave=view.findViewById(R.id.btnsave);


        Bundle bundle = (getActivity()).getIntent().getExtras();
        String permission = bundle.getString("value");
        String tenPT = bundle.getString("tenPT");
        String usernamePT=bundle.getString("userPT");
        String userHV = bundle.getString("userHV");
        listPT = (ArrayList<PT>) DatabaseGym.getInstance(getActivity()).dao_pt().getAllPT();
        listHV = (ArrayList<HV>) DatabaseGym.getInstance(getActivity()).dao_hv().getAllHV();
        //ánh xạ biến
        name = view.findViewById(R.id.edName_TT);
        email = view.findViewById(R.id.edGmail_TT);
        phone = view.findViewById(R.id.edPhone_TT);
        img_avatar_main = getActivity().findViewById(R.id.avatar_main);
        navigationView = getActivity().findViewById(R.id.nav_l);
        img_avatar_nav = getActivity().findViewById(R.id.img_nav_avata);
        name_main = getActivity().findViewById(R.id.tv_name_main);
        name_nav = getActivity().findViewById(R.id.tv_nav_name);
        email_nav = getActivity().findViewById(R.id.tv_nav_email);
        btnsave.setVisibility(View.INVISIBLE);


        if (permission.equalsIgnoreCase("pt")) {
            pt = DatabaseGym.getInstance(getActivity()).dao_pt().getPTtheoUser(usernamePT).get(0);
            name.setText(pt.getName_PT());
            email.setText(pt.getEmail_PT());
            phone.setText(pt.getPhone_PT());
            Bitmap bitmap=BitmapFactory.decodeByteArray(pt.getAvatar_PT(),0,pt.getAvatar_PT().length);
            imgAvarta.setImageBitmap(bitmap);

//            Toast.makeText(getActivity(), idPT + " là id của ", Toast.LENGTH_SHORT).show();
        } else if (permission.equalsIgnoreCase("học viên")) {
            hv=DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoUser(userHV).get(0);
            name.setText(hv.getName_HV());
            email.setText(hv.getEmail_HV());
            phone.setText(hv.getPhone_HV());
            Bitmap bitmap=BitmapFactory.decodeByteArray(hv.getAvatar_HV(),0,hv.getAvatar_HV().length);
            imgAvarta.setImageBitmap(bitmap);

        }

        //avarta
        imageCamere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUET_CODE_CAMERA);
                btnsave.setVisibility(View.VISIBLE);
            }

        });
        imgFodel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUET_CODE_FODEL);
                btnsave.setVisibility(View.VISIBLE);
            }
        });
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()){
                    if (permission.equals("PT")) {
                        pt = DatabaseGym.getInstance(getActivity()).dao_pt().getPTtheoUser(usernamePT).get(0);
                        if (pt != null) {
                            Log.d("TAG", "onClick: " + pt.getUsername_PT());
                            pt.setAvatar_PT(Image_to_bye(imgAvarta));
                            pt.setName_PT(name.getText().toString());
                            pt.setEmail_PT(email.getText().toString());
                            pt.setPhone_PT(phone.getText().toString());
                            DatabaseGym.getInstance(getActivity()).dao_pt().updataPT(pt);
                            Bitmap bitmap= BitmapFactory.decodeByteArray(pt.getAvatar_PT(),0,pt.getAvatar_PT().length);
                            img_avatar_main.setImageBitmap(bitmap);
                            img_avatar_nav.setImageBitmap(bitmap);
                            name.setEnabled(false);
                            email.setEnabled(false);
                            phone.setEnabled(false);
                            btnsave.setVisibility(View.INVISIBLE);
                            name_main.setText(pt.getName_PT());
                            name_nav.setText(pt.getName_PT());
                            email_nav.setText(pt.getEmail_PT());
                        } else {
                            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                        }
                    }else if (permission.equals("Học Viên")){
                        hv = DatabaseGym.getInstance(getActivity()).dao_hv().getHVtheoUser(userHV).get(0);
                        if (hv != null) {
                            Log.d("TAG", "onClick: " + hv.getUsername_HV());
                            hv.setAvatar_HV(Image_to_bye(imgAvarta));
                            hv.setName_HV(name.getText().toString());
                            hv.setEmail_HV(email.getText().toString());
                            hv.setPhone_HV(phone.getText().toString());
                            DatabaseGym.getInstance(getActivity()).dao_hv().updataHV(hv);
                            Bitmap bitmap =BitmapFactory.decodeByteArray(hv.getAvatar_HV(),0,hv.getAvatar_HV().length);
                            img_avatar_main.setImageBitmap(bitmap);
                            img_avatar_nav.setImageBitmap(bitmap);
                            name.setEnabled(false);
                            email.setEnabled(false);
                            phone.setEnabled(false);
                            btnsave.setVisibility(View.INVISIBLE);
                            name_main.setText(hv.getName_HV());
                            name_nav.setText(hv.getName_HV());
                            email_nav.setText(hv.getEmail_HV());
                        } else {
                            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        edit_name_TT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setEnabled(true);
                btnsave.setVisibility(View.VISIBLE);
            }
        });
        edit_gmail_TT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setEnabled(true);
                btnsave.setVisibility(View.VISIBLE);

            }
        });
        edit_phone_TT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setEnabled(true);
                btnsave.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUET_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream stream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                imgAvarta.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUET_CODE_FODEL && resultCode == RESULT_OK && data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvarta.setImageBitmap(bitmap);
        }
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
        if (name.getText().toString().isEmpty()||email.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        //validate Email
        String emailck = email.getText().toString().trim();
        String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!emailck.matches(emailPattern)){
            Toast.makeText(getActivity(), "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // validate check nhập số
        try {
            Integer.parseInt(phone.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(getActivity(), "số điện thoại không được nhập chữ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}