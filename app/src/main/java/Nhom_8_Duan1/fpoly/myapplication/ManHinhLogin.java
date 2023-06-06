package Nhom_8_Duan1.fpoly.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class ManHinhLogin extends AppCompatActivity {
    private Button btn_dangNhap;
    private TextView tv_DangKy,tv_QuenMk;
    CheckBox ckluuMK;
    private TextInputEditText edUser_login,edPass_login;
    private Spinner spinner;
    ArrayList<PT> listCheckPT;
    ArrayList<HV> listCheckHV;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_login);
        //ánh xạ biến
        spinner = findViewById(R.id.spin_mhlogin_vaitro);
        btn_dangNhap = findViewById(R.id.btn_mhlogin_dangnhap);
        tv_DangKy = findViewById(R.id.tv_mhlogin_textDangKy);
        tv_QuenMk = findViewById(R.id.tv_quenMK);
        edUser_login = findViewById(R.id.txt_edt_mhlogin_username);
        edPass_login = findViewById(R.id.txt_edt_mhlogin_pass);
        ckluuMK=findViewById(R.id.chk_luumk);
        // Lấy mật khẩu
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        edUser_login.setText(sharedPreferences.getString("user_name",""));
        edPass_login.setText(sharedPreferences.getString("pass",""));
        ckluuMK.setChecked(sharedPreferences.getBoolean("ck",false));
        //set item cho spiner vai trò
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Vai_tro, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int pos = adapter.getPosition(sharedPreferences.getString("tk",""));
        spinner.setSelection(pos);
        //bắt sự kiện
        //bắt sự kiện ấn nút dăng nhập
        btn_dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    String permission = spinner.getSelectedItem().toString();
                    Intent intent = new Intent(ManHinhLogin.this, MainActivity_admin.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("value", permission);
                    intent.putExtras(bundle);
                    if(permission.equalsIgnoreCase("ADMIN")){
                        if(edUser_login.getText().toString().trim().equalsIgnoreCase("admin") && edPass_login.getText().toString().trim().equalsIgnoreCase("admin")){
                            if (ckluuMK.isChecked()){
                                editor.putString("user_name",edUser_login.getText().toString());
                                editor.putString("pass",edPass_login.getText().toString());
                                editor.putString("tk",permission);
                                editor.putBoolean("ck",true);
                                editor.commit();
                            }else {
                                editor.remove("user_name");
                                editor.remove("pass");
                                editor.remove("tk");
                                editor.remove("ck");
                                editor.commit();
                            }
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ManHinhLogin.this, "login thất bại!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(permission.equals("PT")){
                        String user = edUser_login.getText().toString();
                        String pass = edPass_login.getText().toString();
                        listCheckPT = new ArrayList<>();
                        listCheckPT = (ArrayList<PT>) DatabaseGym.getInstance(ManHinhLogin.this).dao_pt().CheckLogin(user, pass);
                        if(listCheckPT.size() == 1){
                            String tenPT = listCheckPT.get(0).getName_PT();
                            String emailPT = listCheckPT.get(0).getEmail_PT();
                            int idPT = listCheckPT.get(0).getId_PT();
                            byte[] avatarPT =(listCheckPT.get(0).getAvatar_PT());
                            String userPT = listCheckPT.get(0).getUsername_PT();
                            bundle.putInt("idPT",idPT);
                            bundle.putString("tenPT", tenPT);
                            bundle.putString("emailPT", emailPT);
                            bundle.putByteArray("avatarPT",avatarPT);
                            bundle.putString("userPT", userPT);
                            intent.putExtras(bundle);
                            Toast.makeText(ManHinhLogin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            if (ckluuMK.isChecked()){
                                editor.putString("user_name",edUser_login.getText().toString());
                                editor.putString("pass",edPass_login.getText().toString());
                                editor.putString("tk",permission);
                                editor.putBoolean("ck",true);
                                editor.commit();
                            }else {
                                editor.remove("user_name");
                                editor.remove("pass");
                                editor.remove("tk");
                                editor.remove("ck");
                                editor.commit();
                            }
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ManHinhLogin.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(permission.equalsIgnoreCase("Học viên")){
                        String user = edUser_login.getText().toString();
                        String pass = edPass_login.getText().toString();
                        listCheckHV = new ArrayList<>();
                        listCheckHV = (ArrayList<HV>) DatabaseGym.getInstance(ManHinhLogin.this).dao_hv().CheckLogin(user, pass);
                        if(listCheckHV.size() == 1){
                            String tenHV = listCheckHV.get(0).getName_HV();
                            String emailHV = listCheckHV.get(0).getEmail_HV();
                            byte[] avatarHV = listCheckHV.get(0).getAvatar_HV();
                            int moneyHV = listCheckHV.get(0).getMonney();
                            int idHV = listCheckHV.get(0).getId_HV();
                            String userHV = listCheckHV.get(0).getUsername_HV();
                            bundle.putString("tenHV", tenHV);
                            bundle.putInt("idHV", idHV);
                            bundle.putString("emailHV", emailHV);
                            bundle.putByteArray("avatarHV", avatarHV);
                            bundle.putInt("moneyHV", moneyHV);
                            bundle.putString("userHV", userHV);
                            intent.putExtras(bundle);
                            Toast.makeText(ManHinhLogin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            if (ckluuMK.isChecked()){
                                editor.putString("user_name",edUser_login.getText().toString());
                                editor.putString("pass",edPass_login.getText().toString());
                                editor.putString("tk",permission);
                                editor.putBoolean("ck",true);
                                editor.commit();
                            }else {
                                editor.remove("user_name");
                                editor.remove("pass");
                                editor.remove("tk");
                                editor.remove("ck");
                                editor.commit();
                            }
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ManHinhLogin.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Log.d("zzz", "onClick: " + permission);
                }
            }
        });

        tv_DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //đi đến màn hình đăng ký tài khoản
                Toast.makeText(ManHinhLogin.this, "đăng ký chỉ dành cho học viên", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManHinhLogin.this,ManHinhDk.class);
                startActivity(intent);
            }
        });

        tv_QuenMk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinhLogin.this,ManHinhQuenMk.class);
                startActivity(intent);
            }
        });
    }
    public boolean validate(){
        if(edUser_login.getText().length() == 0 || edPass_login.getText().length() == 0){
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}