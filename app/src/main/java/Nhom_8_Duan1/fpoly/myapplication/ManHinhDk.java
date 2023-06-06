package Nhom_8_Duan1.fpoly.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.List;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;

public class ManHinhDk extends AppCompatActivity {
    ImageView imageView;
    private ImageView back;
    private Button DangKy;
    private TextInputEditText txt_User ,txtName ,txt_Email, txt_Pass, txt_RePass, txt_Pin;
    private TextView tv_dn, tv_ChinhSachBaoMat;
    HV hv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dk);
        imageView=new ImageView(this);
        imageView.setImageResource(R.drawable.img_avata);
        //ánh xạ biến
        back = findViewById(R.id.img_mhdk_back);
        DangKy = findViewById(R.id.btn_mhDk_dangky);
        tv_dn = findViewById(R.id.tv_mhdk_textDangNhap);
        tv_ChinhSachBaoMat = findViewById(R.id.tv_chinh_sach);
        txt_User = findViewById(R.id.txt_edt_mhDk_User);
        txtName = findViewById(R.id.txt_edt_mhDk_name);
        txt_Email = findViewById(R.id.txt_edt_mhDk_Email);
        txt_Pass = findViewById(R.id.txt_edt_mhDk_pass);
        txt_RePass = findViewById(R.id.txt_edt_mhDk_repass);
        txt_Pin = findViewById(R.id.txt_edt_mhDk_Pin);

        //tạo intent chuyển màn hình
        Intent intent = new Intent(ManHinhDk.this,ManHinhLogin.class);
        Intent intent2 = new Intent(ManHinhDk.this,ManHinhDk_ThanhCong.class);
        //bắt sự kiện
        //sự kiện quay lại màn hình đăng nhập(finish() màn hình đăng ký)
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });

        //sự kiện click đăng ký
        DangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    String user = txt_User.getText().toString();
                    String name = txtName.getText().toString();
                    String email = txt_Email.getText().toString();
                    String pass = txt_Pass.getText().toString();
                    String rePass = txt_RePass.getText().toString();
                    int pin = Integer.parseInt(txt_Pin.getText().toString());
                    hv = new HV(user, name,Image_to_bye(imageView), pass, email, pin);
                    DatabaseGym.getInstance(ManHinhDk.this).dao_hv().insertHV(hv);
                    Toast.makeText(ManHinhDk.this, "Đăng ký thành công!!!", Toast.LENGTH_SHORT).show();
                    startActivity(intent2);
                    finish();
                }
            }
        });

        //bắt sự kiện click text chính sách
        tv_ChinhSachBaoMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hiển thị chính sách bảo mâtj của app
            }
        });

        //bắt sự kiện click text đăng nhập
        tv_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();//thoát màn hình đăng ký
            }
        });

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
//       validate k bỏ trống nhập
        if(txt_User.getText().length() == 0 || txtName.getText().length() == 0 ||
                txt_Email.getText().length() == 0 || txt_Pass.getText().length() == 0 ||
                    txt_RePass.getText().length() == 0 || txt_Pin.getText().length() == 0){
            Toast.makeText(this, "Vui lòng nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // validate check user
        String user = txt_User.getText().toString();
        List<HV> list = DatabaseGym.getInstance(ManHinhDk.this).dao_hv().getHVtheoUser(user);
        if(list.size() > 0){
            Toast.makeText(this, "User name đã tồn tại!", Toast.LENGTH_SHORT).show();
            return false;
        }
        // validate check pass và repass
        if(!(txt_Pass.getText().toString().equals(txt_RePass.getText().toString()))){
            Toast.makeText(this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //validate Email
        String email = txt_Email.getText().toString().trim();
        String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(!email.matches(emailPattern)){
            Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            return false;
        }
        //validate check độ dài pin
        if(!(txt_Pin.getText().length() == 4)){
            Toast.makeText(this, "Pin gồm 4 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }

        // validate kiểu pin
        try {
            Integer.parseInt(txt_Pin.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(this, "Pin gồm 4 chữ số!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}