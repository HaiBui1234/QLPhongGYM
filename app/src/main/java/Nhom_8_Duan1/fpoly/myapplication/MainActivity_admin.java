package Nhom_8_Duan1.fpoly.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.HV;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.PT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.DTO.TT;
import Nhom_8_Duan1.fpoly.myapplication.csdl.Data_base.DatabaseGym;
import Nhom_8_Duan1.fpoly.myapplication.fragment.CaiDatFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.HomeFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.NguoiDung_ThongTinFragment;
import Nhom_8_Duan1.fpoly.myapplication.fragment.ThongKe_DoanhThuFragment;
import Nhom_8_Duan1.fpoly.myapplication.service.MyService;

public class MainActivity_admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView img_avatar_main, img_avatar_nav,img_naptien,img_camNaptien,img_fodelNaptien;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    TextView tv_nav_name, tv_nav_email, tv_name_main, tv_money_main, tv_hello_time, tv_hello_permission;
    TextView btnNap, tvhd_nap;
    CheckBox ck_nap;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    public static final String CHANNEL_ID = "exampleServiceChannel";
    Notification notification = null;
    Button btnThanhToan, btnHuyNap;
    TextInputEditText ed_so_tien;
    HV hv;
    PT pt;
    TT tt;
     int CAMERA=123;
     int FOLDER=456;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        //ánh xạ biến
        drawerLayout = findViewById(R.id.drr);
        toolbar = findViewById(R.id.toolBar_mhAdmin);
        img_avatar_main=findViewById(R.id.avatar_main);
        bottomNavigationView = findViewById(R.id.buttonNav_menu_Admin);
        btnNap = findViewById(R.id.btnNap_money_main);
        tv_hello_permission = findViewById(R.id.tv_hello_permission);
        tv_hello_time = findViewById(R.id.tv_hello_time);
        Log.d("TAG", "onCreate: "+sdf.format(new java.util.Date()));
        String [] a=sdf.format(new java.util.Date()).split(":");
        startService(new Intent(this, MyService.class));
        if (Integer.parseInt(a[0])<12){
//            Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.img_avata);
           tv_hello_time.setText("Chào buổi sáng");
//           Notification notification=new Notification.Builder(this).setContentTitle("Sức Khỏe mỗi ngày").setContentText("Chào buổi sáng").setSmallIcon(R.drawable.img_avata).setLargeIcon(bitmap).build();
//           NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//           if (manager!=null){
//               manager.notify(1,notification);
//           }
        }else if (Integer.parseInt(a[0])<18&&Integer.parseInt(a[0])>=12){
            tv_hello_time.setText("Chào buổi chiều");
        }else if (Integer.parseInt(a[0])<24&&Integer.parseInt(a[0])>=18){
            tv_hello_time.setText("Chào buổi tối");
        }
        Bundle bundle = getIntent().getExtras();
        String permission = bundle.getString("value");
        String tenPT = bundle.getString("tenPT");
        //
        String userPT = bundle.getString("userPT");
        String emailPT = bundle.getString("emailPT");
         byte[] avatarPT=bundle.getByteArray("avatarPT");
        String tenHV = bundle.getString("tenHV");
        String emailHV = bundle.getString("emailHV");
        int moneyHV = bundle.getInt("moneyHV");
        String userHV = bundle.getString("userHV");
        byte[] avatarHV=bundle.getByteArray("avatarHV");
        //Nap tiền
        btnNap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogNap = new Dialog(MainActivity_admin.this);
                dialogNap.setContentView(R.layout.dialog_nap);
                tvhd_nap = dialogNap.findViewById(R.id.tv_hd_nap);
                ck_nap = dialogNap.findViewById(R.id.ck_conform_nap);
                btnThanhToan = dialogNap.findViewById(R.id.btnConform_nap);
                btnHuyNap = dialogNap.findViewById(R.id.btnHuy_nap);
                img_naptien=dialogNap.findViewById(R.id.img_naptien);
                img_camNaptien=dialogNap.findViewById(R.id.img_camnaptien);
                img_fodelNaptien=dialogNap.findViewById(R.id.img_fodelNaptien);
                ed_so_tien = dialogNap.findViewById(R.id.edMoney_nap);
                img_camNaptien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,CAMERA);
                    }
                });
                img_fodelNaptien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,FOLDER);
                    }
                });
                ck_nap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            if(ed_so_tien.getText().toString().length() == 0){
                                btnThanhToan.setEnabled(false);
                            }
                            btnThanhToan.setEnabled(true);
                        }
                        else btnThanhToan.setEnabled(false);
                    }
                });
                btnThanhToan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            hv = DatabaseGym.getInstance(MainActivity_admin.this).dao_hv().getHVtheoUser(userHV).get(0);
                            tt = new TT();
                            tt.setTien_TT(Integer.parseInt(ed_so_tien.getText().toString()));
                            tt.setTrangThai_TT(0);
                            tt.setId_HV(hv.getId_HV());
                            tt.setImg_Naptien(Image_To_Byte(img_naptien));
                            long millis=System.currentTimeMillis();
                            Date date = new Date(millis);
                            tt.setTime(getDateString(date));
                            DatabaseGym.getInstance(MainActivity_admin.this).dao_tt().insertTT(tt);
                            Toast.makeText(MainActivity_admin.this, "Vui lòng đợi hệ thống xử lí...", Toast.LENGTH_SHORT).show();
                            dialogNap.dismiss();
                        }catch (Exception e){
                            Toast.makeText(MainActivity_admin.this, "Vui lòng nhập số!!!", Toast.LENGTH_SHORT).show();
                        }
//                        int money_now = hv.getMonney();
//                        hv.setMonney(money_now + Integer.parseInt(ed_so_tien.getText().toString()));
//                        DatabaseGym.getInstance(MainActivity_admin.this).dao_hv().updataHV(hv);
//                        Toast.makeText(MainActivity_admin.this, "Đã thanh toán thành công", Toast.LENGTH_SHORT).show();
//                        tv_money_main.setText(String.valueOf(hv.getMonney())+"đ");
                    }
                });
                btnHuyNap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogNap.dismiss();
                    }
                });
                tvhd_nap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog dialogHD = new Dialog(dialogNap.getContext());
                        dialogHD.setContentView(R.layout.dialog_hd_nap);
                        dialogHD.findViewById(R.id.btnDaHieu).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialogHD.dismiss();
                            }
                        });
                        dialogHD.show();
                    }
                });
                dialogNap.show();
            }
        });
        //==================navi....
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_dr, R.string.close_dr);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_l);
        tv_nav_name = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_name);
        tv_nav_email = navigationView.getHeaderView(0).findViewById(R.id.tv_nav_email);
        img_avatar_nav = navigationView.getHeaderView(0).findViewById(+R.id.img_nav_avata);
        tv_name_main = findViewById(R.id.tv_name_main);
        tv_money_main = findViewById(R.id.tv_money_main);
        navigationView.setNavigationItemSelectedListener(this);
        reFragment(HomeFragment.newInstance());

        //=================bottomnavi......

        if(permission.equalsIgnoreCase("ADMIN")){
            tv_nav_name.setText("Admin");
            tv_nav_email.setText("Admin123@admin.com");
            tv_name_main.setText("Admin");
            tv_money_main.setText("");
            btnNap.setEnabled(false);
            btnNap.setText("");
            btnNap.setBackground(null);
            tv_hello_permission.setText("Quản trị viên");
            bottomNavigationView.getMenu().findItem(R.id.nguoidung).setVisible(false);
            bottomNavigationView.getMenu().findItem(R.id.caidat).setVisible(false);
//            Toast.makeText(this, "Đây là activity Admin!!", Toast.LENGTH_SHORT).show();
        }
        else if(permission.equalsIgnoreCase("PT")){
            pt = DatabaseGym.getInstance(this).dao_pt().getPTtheoUser(userPT).get(0);
            Log.d("TAG1", "onCreate: "+pt.getUsername_PT());
            if (pt.getAvatar_PT()!=null){
                Bitmap bitmap=BitmapFactory.decodeByteArray(pt.getAvatar_PT(),0,pt.getAvatar_PT().length);
                img_avatar_main.setImageBitmap(bitmap);
                img_avatar_nav.setImageBitmap(bitmap);
            }
            tv_nav_name.setText(tenPT);
            tv_nav_email.setText(emailPT);
            tv_name_main.setText(tenPT);
            tv_money_main.setText("");
            btnNap.setEnabled(false);
            btnNap.setText("");
            btnNap.setBackground(null);
            tv_hello_permission.setText("Huấn luyện viên vô địch");
            bottomNavigationView.getMenu().findItem(R.id.thongke).setVisible(false);
//            Toast.makeText(this, "Đây là activity PT!!", Toast.LENGTH_SHORT).show();
        }
        else if(permission.equalsIgnoreCase("Học viên")){
            hv=DatabaseGym.getInstance(this).dao_hv().getHVtheoUser(userHV).get(0);
            if (hv.getAvatar_HV()!=null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(hv.getAvatar_HV(), 0, hv.getAvatar_HV().length);
                img_avatar_main.setImageBitmap(bitmap);
            }
            tv_nav_name.setText(tenHV);
            tv_nav_email.setText(emailHV);
            tv_name_main.setText(tenHV);
            tv_money_main.setText(String.valueOf(moneyHV) + "Đ");
            tv_hello_permission.setText("Học viên chăm chỉ");
            bottomNavigationView.getMenu().findItem(R.id.thongke).setVisible(false);
//            Toast.makeText(this, "Đây là activity Học viên!!", Toast.LENGTH_SHORT).show();
        }
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        reFragment(HomeFragment.newInstance());
                        break;
                    case R.id.thongke:
                        reFragment(ThongKe_DoanhThuFragment.newInstance());
                        break;
                    case R.id.nguoidung:
                        reFragment(NguoiDung_ThongTinFragment.newInstance());
                        break;
                    case R.id.caidat:
                        reFragment(CaiDatFragment.newInstance());
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.dangxuat:
                showDialogLogout();
                break;
        }
        drawerLayout.closeDrawer(navigationView);
        return true;
    }

    public void reFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_ly_mhAdmin, fragment);
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView);
            } else finish();
        }
        return true;
    }
    private void showDialogLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity_admin.this);
        builder.setTitle("Thông báo!");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(MainActivity_admin.this, ManHinhLogin.class));
                finish();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public static String getDateString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAMERA&&resultCode==RESULT_OK&&data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            img_naptien.setImageBitmap(bitmap);
        }
        if (requestCode==FOLDER&&resultCode==RESULT_OK&&data!=null){
            Uri uri = data.getData();
            try {
                InputStream stream =getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                img_naptien.setImageBitmap(bitmap);
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
    void thongbao(String a){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Example Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification =  new Notification.Builder(this,CHANNEL_ID)
                    .setContentTitle("Sống Khỏe Mỗi Ngày")
                    .setContentText(a)
                    .setSmallIcon(R.drawable.img_avata)
                    .build();
        }
    }
}

