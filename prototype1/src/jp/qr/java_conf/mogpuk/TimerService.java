package jp.qr.java_conf.mogpuk;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.widget.Toast;

public class TimerService extends Service {
	
	NotificationManager nm;
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void onCreate(){
		nm = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		
	}
	@SuppressWarnings("deprecation")
	public void onStart(Intent it,int id){
		
		SharedPreferences po = getSharedPreferences("btn", MODE_PRIVATE);
        Editor e = po.edit();
        e.putBoolean("btn_enable", true);
        e.commit();
        
        
		Intent it_kekka = new Intent(this,MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, it_kekka, 0);
		Notification nf = new Notification();
		nf.icon=R.drawable.icon;
		nf.setLatestEventInfo(getApplicationContext(), "�����`�ۂ�", "���ʂ����郉���`�H", pi);
		nf.flags = Notification.FLAG_AUTO_CANCEL;
		nm.notify(0, nf);
		Toast.makeText(this, "����`�����I���������!!", Toast.LENGTH_LONG).show();

		
		/*
		.setContentTitle("�����`�ۂ�")
		.setContentText("TOP�N��")
		.setContentIntent(pi)
		.setSmallIcon(R.drawable.min_egaokiyora)
		.setWhen(System.currentTimeMillis())
		.build();
		nf.flags = Notification.FLAG_AUTO_CANCEL;
		nm.notify(0, nf);
		*/
		
		//
		
		
		stopSelf();
	}
	
	public void onDestroy(){
	}
}
