package jp.qr.java_conf.mogpuk;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImageLoader extends AsyncTask<String[], Integer, Bitmap[]> {
		 
	 public CallBackImage _callBackImage;
	 private Activity mActivity;
	 private Dialog dialog;
	 AnimationDrawable animation;
	 
	 public ImageLoader(Activity act) {
	     mActivity = act;
	  }
	 
	 public ImageLoader() {}
	 Bitmap[] bit;
	 
	 
	 
	 @Override  
	 protected void onPreExecute() {
		 	dialog = new Dialog(mActivity,R.style.MyProgressTheme);
			dialog.setTitle("Now Loading...");
			dialog.setContentView(R.layout.progress_dialog);
			dialog.setCancelable(false);
			dialog.show();
			
			//ダイアログ内に表示するアニメーション
			ImageView iv = (ImageView)dialog.findViewById(R.id.image);
			iv.setBackgroundResource(R.anim.dialog_anime);
			animation = (AnimationDrawable)iv.getBackground();
			animation.start();	
	 }

	 @Override
	 protected Bitmap[] doInBackground(String[]... params) {
	 return getData(params[0]);
	 }
	 
	 public void setOnCallBack(CallBackImage _cbj){
	 _callBackImage = _cbj;
	 }
	 
	 @Override  
	 protected void onPostExecute(Bitmap[] result) {
		 animation.stop();
		 dialog.dismiss();
		 dialog = null;
		 _callBackImage.CallBack(result);
		 _callBackImage = null;
	 }  
	 
	 public Bitmap[] getData(String[] params) {
	 try {  
		 bit= new Bitmap[params.length];
		 for(int i=0;i<params.length;i++){
			 URL url = new URL(params[i]);  
			 HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
			 connection.setDoInput(true);  
			 connection.connect();  
			 InputStream input = connection.getInputStream();  
			 Bitmap myBitmap = BitmapFactory.decodeStream(input);  
			 bit[i]= myBitmap;
		 }
	 return bit;
	 } catch (IOException e) {  
	 return null;  
	 }  
	 }
	 public static class CallBackImage {
 
		 public void CallBack(Bitmap[] result){
			 //ここにいろいろ関数をかけばいいと思う。
		 }
}
}