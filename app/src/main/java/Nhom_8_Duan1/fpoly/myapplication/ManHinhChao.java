package Nhom_8_Duan1.fpoly.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Nhom_8_Duan1.fpoly.myapplication.adapter.slideAdapter;
import Nhom_8_Duan1.fpoly.myapplication.model.model_slideImage;
import me.relex.circleindicator.CircleIndicator;

public class ManHinhChao extends AppCompatActivity implements View.OnClickListener {
    Button butchuyen;
    TextView tv_dn;
     ViewPager mViewPager;
     CircleIndicator mCircleIndicator;
     ArrayList<model_slideImage> model_slideImages;
     slideAdapter mAdapter;
     Timer mTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        //ánh xạ biến
        butchuyen = findViewById(R.id.btn_mhc_start);
        tv_dn = findViewById(R.id.tv_mhc_dangnhap);
        mViewPager=findViewById(R.id.ViewPagerSlide);
        mCircleIndicator=findViewById(R.id.cirIn);
        //bắt sựu kiện
        butchuyen.setOnClickListener(this);
        tv_dn.setOnClickListener(this);

        //tạo slide hình ảnh cho màn hình chào
        model_slideImages=new ArrayList<>();
        dataSlide();
        mAdapter=new slideAdapter(this,model_slideImages);
        mViewPager.setAdapter(mAdapter);
        mCircleIndicator.setViewPager(mViewPager);
        mAdapter.registerDataSetObserver(mCircleIndicator.getDataSetObserver());
        if (mTimer==null){
            mTimer=new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int current = mViewPager.getCurrentItem();
                        int index = model_slideImages.size()-1;
                        if (current<index){
                            current++;
                            mViewPager.setCurrentItem(current);
                        }else {
                            mViewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },1000,3000);
    }

    private void dataSlide() {
        model_slideImages.add(new model_slideImage(R.drawable.img_banner1,"Bạn Muốn làm Gì","Biến tập gym thành một cách sống","Không chỉ là một buổi luyện tập mà còn là trải nghiệm"));
        model_slideImages.add(new model_slideImage(R.drawable.img_banner2,"Poly Gym App","Rèn Luyện Bản Thân","Đừng để ngoại hình định nghĩa con người bạn. Bạn không béo. Bạn có mỡ thôi. MỠ không định nghĩa bạn là ai cả"));
        model_slideImages.add(new model_slideImage(R.drawable.img_banner3,"Poly Gym","Quản Lý Cho Poly Gym","Đừng ngại thất bại, đó là con đường dẫn đến thành công"));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_mhc_start|| id == R.id.tv_mhc_dangnhap){
            Intent intent = new Intent(ManHinhChao.this,ManHinhLogin.class);
            startActivity(intent);
            finish();
        }
    }
}