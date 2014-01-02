package jp.qr.java_conf.mogpuk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Settei extends Activity {
	
	private Button btn_help;
	private Button btn_pure;
	//初回起動時のダイアログ表示用
		Dialog dialog4;
		int count_syokai;
		ImageView d_syoukai_img;
		Button d_syoukai_btn;
		int koumoku_syokai;
		TextView d_message;
		TextView d_message2;
		int syokai_hanbetu;
		String[] str_syouokai;
		int[] syokai_res;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settei);
	    
	  //初回起動用ダイアログの表示判断
	  	SharedPreferences po_syokai = getSharedPreferences("syokai", MODE_PRIVATE);
	    syokai_hanbetu = po_syokai.getInt("syokai_settei", 0);
	    
	    if(syokai_hanbetu==0){
	    	
	    	koumoku_syokai=3;
	    	count_syokai=0;
	    	
	    	syokai_res = new int[koumoku_syokai];
	 		syokai_res[0] =getResources().getIdentifier("setumei_bt_asobi","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai_res[1] =getResources().getIdentifier("setumei_bt_keiei","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai_res[2] =getResources().getIdentifier("bt","drawable", "jp.qr.java_conf.mogpuk");
	    	
	    	//表示用テキストの分割
	    	Resources res;
	    	String file_result;
	    	String result;
	    	BufferedReader br = null;
	    	InputStream is;
	    	StringBuilder sb = new StringBuilder();
	    
	    	
	    	res = this.getResources();
	    	is = res.openRawResource(R.raw.settei_help);
			br = new BufferedReader(new InputStreamReader(is));
			try {
				while((file_result = br.readLine()) != null){
					sb.append(file_result);
				}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			result=String.valueOf(sb);
			str_syouokai=result.split("/", 0);
			
	    	// ダイアログの作成と表示
	    	
	        //初回起動時のみ表示する
	    	dialog4 = new Dialog(Settei.this);
			//タイトルの非表示
			dialog4.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			//フルスクリーン
			dialog4.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
			//dialog_custom.xmlを基にしてダイアログを作成
			dialog4.setContentView(R.layout.dialog_custom_syokai);
			//背景を透明にする
			dialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			//ダイアログ表示時のbackボタンを無効に
			dialog4.setCancelable(false);
			dialog4.show();
			
			//ダイアログに表示するテキストのID登録と表示命令
			TextView d_title=(TextView)dialog4.findViewById(R.id.d_title);
			d_title.setText("設定について");
			d_message=(TextView)dialog4.findViewById(R.id.d_message);
			d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
			d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
			d_message2.setText(str_syouokai[0]);
			d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
			d_syoukai_img.setImageResource(syokai_res[0]);
			d_syoukai_btn=(Button)dialog4.findViewById(R.id.d_button);

			//ダイアログの「OK」ボタンが押された時の処理
			d_syoukai_btn.setOnClickListener(new View.OnClickListener() {
				 	@Override
		            public void onClick(View v) {
					 	count_syokai++;
					 	if(count_syokai<koumoku_syokai){
						 	d_syoukai_img.setImageResource(syokai_res[count_syokai]);
						 	d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
						 	d_message2.setText(str_syouokai[count_syokai]);
					 	}
					 	if(count_syokai==2){
					 		d_syoukai_img.setImageResource(R.anim.anim_settei_setumei);
							AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
							ani.start();
					 	}
					 	if (count_syokai==koumoku_syokai-1) {
						 	d_syoukai_btn.setText("閉じる");
					 	}
					 	if(count_syokai==koumoku_syokai){
					 		dialog4.dismiss();
					 	}
				 	}
			    });
			SharedPreferences po_syokai_m = getSharedPreferences("syokai", MODE_PRIVATE);
	        Editor e7 = po_syokai_m.edit();
            e7.putInt("syokai_settei", 1);
            e7.commit();
	    }

	
	    // TODO Auto-generated method stub
	    
	    btn_help=(Button)findViewById(R.id.btn_settei_help);
	    btn_help.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Settei.this, Help.class);
				startActivity(intent);
				
				
			}
		});
	    
	    btn_pure=(Button)findViewById(R.id.btn_settei_syousai);
	    btn_pure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Settei.this, Syousai.class);
				startActivity(intent);
			}
		});
	}

}
