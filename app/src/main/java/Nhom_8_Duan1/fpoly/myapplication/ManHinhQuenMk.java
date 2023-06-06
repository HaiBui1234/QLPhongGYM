package Nhom_8_Duan1.fpoly.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class ManHinhQuenMk extends AppCompatActivity {
    private ImageView back;
    private Button SetMK, btnSwapPass_forget, btnEndSwapPass_forget;
    private TextView tv_dk;
    private TextInputEditText pin_forget, user_foget, newPass_forget, reNewPass_forget;
    List<HV> list = new ArrayList<>();
    HV hv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_quen_mk);
        //ánh xạ biến
        back = findViewById(R.id.img_mhQuenMk_back);
        SetMK = findViewById(R.id.btn_mhQuenMk_SetNewMk);
        tv_dk = findViewById(R.id.tv_mhQuenMK_textDK);
        user_foget = findViewById(R.id.txt_edt_mhQuenMk_User);
        pin_forget = findViewById(R.id.txt_edt_mhQuenMk_PIN);
        //tạo intent chuyển màn hình
        Intent intent = new Intent(ManHinhQuenMk.this,ManHinhLogin.class);
        Intent intent2 = new Intent(ManHinhQuenMk.this,ManHinhDk.class);

        //bắt sự kiện
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SetMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String userForget = user_foget.getText().toString();
                    int pinForget = Integer.parseInt(pin_forget.getText().toString());
                    list = DatabaseGym.getInstance(ManHinhQuenMk.this).dao_hv().checkForgetPassHV(userForget, pinForget);
                    if(list.size() == 1){
                        hv = list.get(0);
                        Dialog dialog = new Dialog(ManHinhQuenMk.this);
                        dialog.setContentView(R.layout.dialog_forgot_pass);
                        newPass_forget = dialog.findViewById(R.id.edPass_forget);
                        reNewPass_forget = dialog.findViewById(R.id.edRePass_forget);
                        btnSwapPass_forget = dialog.findViewById(R.id.btnSwapPass_forget);
                        btnEndSwapPass_forget = dialog.findViewById(R.id.btnEndSwapPass_forget);
                        btnSwapPass_forget.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hv.setPass_HV(newPass_forget.getText().toString());
                                DatabaseGym.getInstance(ManHinhQuenMk.this).dao_hv().updataHV(hv);
                                Toast.makeText(ManHinhQuenMk.this, "Đã đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                            }
                        });
                        btnEndSwapPass_forget.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                    else {
                        Toast.makeText(ManHinhQuenMk.this, "Username và mã PIN không trùng khớp.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tv_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
                finish();
            }
        });
    }
    public boolean validate(){
        if(user_foget.getText().toString().length() == 0 || pin_forget.getText().toString().length() == 0 )
        {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!(pin_forget.getText().length() == 4)){
            Toast.makeText(this, "Pin gồm 4 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // validate kiểu pin
        try {
            Integer.parseInt(pin_forget.getText().toString());
        }
        catch (Exception e){
//            e.printStackTrace();
            Toast.makeText(this, "Pin gồm 4 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}