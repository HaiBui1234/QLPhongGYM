package Nhom_8_Duan1.fpoly.myapplication.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import java.text.SimpleDateFormat;

import Nhom_8_Duan1.fpoly.myapplication.R;

public class MyService extends Service {
    public static final String CHANNEL_ID = "exampleServiceChannel";
    Notification notification = null;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "Example Service Channel",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(serviceChannel);
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String [] a=sdf.format(new java.util.Date()).split(":");
        if (Integer.parseInt(a[0])<12){
           thongbao("Chào buổi sáng");
        }else if (Integer.parseInt(a[0])<18&&Integer.parseInt(a[0])>=12){
            thongbao("Chào buổi chiều");
        }else if (Integer.parseInt(a[0])<24&&Integer.parseInt(a[0])>=18){
            thongbao("Chào buổi buổi");
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    void thongbao(String a){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification =  new Notification.Builder(this,CHANNEL_ID)
                    .setContentTitle("Sống Khỏe Mỗi Ngày")
                    .setContentText(a)
                    .setSmallIcon(R.drawable.img_avata)
                    .build();
        }
    }
}