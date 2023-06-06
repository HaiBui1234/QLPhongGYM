package Nhom_8_Duan1.fpoly.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ManHinhDk_ThanhCong extends AppCompatActivity {
    private Button Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dk_thanh_cong);
        //ánh xạ biến
        Start = findViewById(R.id.btn_NextToMhMain);
        //tạo intent chuyển màn hình
        Intent intent = new Intent(ManHinhDk_ThanhCong.this,ManHinhLogin.class);
        //bắt sự kiện
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });

    }
}