package com.example.test_notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmRecevier extends BroadcastReceiver {
    public final int NOTIFICATION_ID = 101;

    public AlarmRecevier(){ }

    NotificationManager manager;
    NotificationCompat.Builder builder;

    //오레오 이상은 반드시 채널을 설정해줘야 Notification이 작동함
    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";


    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        builder = null;
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            // Notification 고정하려면 setOngoing(true) 해줘야됨
            builder = new NotificationCompat.Builder(context, CHANNEL_ID).setOngoing(true);
        } else {
            builder = new NotificationCompat.Builder(context).setOngoing(true);
        }

        //알림창 클릭 시 activity 화면 부름
        // MainActivity
        Intent intent_main = new Intent(context, MainActivity.class);
        intent_main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent_main = PendingIntent.getActivity(context,101, intent_main, PendingIntent.FLAG_IMMUTABLE);

        //OtherActivity1
        Intent intent_other1 = new Intent(context, OtherActivity1.class);
        intent_other1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent_other1 = PendingIntent.getActivity(context,101, intent_other1, PendingIntent.FLAG_IMMUTABLE);

        //OtherActivity2
        Intent intent_other2 = new Intent(context, OtherActivity2.class);
        intent_other2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent_other2 = PendingIntent.getActivity(context,101, intent_other2, PendingIntent.FLAG_IMMUTABLE);

        // Notification 커스텀
        //RemoteViews normal_layout = new RemoteViews(context.getPackageName(), R.layout.notification_normal);
        //RemoteViews expand_layout = new RemoteViews(context.getPackageName(), R.layout.notification_expand);
        //builder.setCustomContentView(normal_layout);
        //builder.setCustomBigContentView(expand_layout);


        //알림창 아이콘
        builder.setSmallIcon(R.drawable.ic_sms);
        builder.setContentTitle("알림 Title");
        builder.setContentText("알림 Text");
        builder.setPriority(NotificationManagerCompat.IMPORTANCE_DEFAULT);

        //MainActivity 실행 (기본 알림 클릭 시)
        //builder.setContentIntent(pendingIntent_main);
        // 다른 옵션 선택 시 지정한 액티비티 실행
        builder.addAction(R.drawable.ic_sms, "other1", pendingIntent_other1);
        builder.addAction(R.drawable.ic_sms, "other2", pendingIntent_other2);

        Notification notification = builder.build();
        manager.notify(1,notification);

        /*NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());*/
    }
}