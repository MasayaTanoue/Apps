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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class zukan extends Activity implements OnClickListener {
	
	Button btn_zukan_ibe;
	Button btn_zukan_kankou;
	Button btn_zukan_chara;
	Button btn_zukan_bonus;
	
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.zukan_list);
	    
	    btn_zukan_ibe=(Button)findViewById(R.id.btn_zukan_ibe);
	    btn_zukan_ibe.setOnClickListener(this);
	    
	    btn_zukan_kankou=(Button)findViewById(R.id.btn_zukan_kankou);
	    btn_zukan_kankou.setOnClickListener(this);
	    
	    btn_zukan_chara=(Button)findViewById(R.id.btn_zukan_chara);
	    btn_zukan_chara.setOnClickListener(this);
	    
	    btn_zukan_bonus=(Button)findViewById(R.id.btn_zukan_bonus);
	    btn_zukan_bonus.setOnClickListener(this);
	    
	    
	  //初回起動用ダイアログの表示判断
		SharedPreferences po_syokai = getSharedPreferences("syokai", MODE_PRIVATE);
    	syokai_hanbetu = po_syokai.getInt("syokai_zukan", 0);
	    
	    if(syokai_hanbetu==0){
	    	
	    	koumoku_syokai=2;
	    	count_syokai=0;
	    	
	    	
	    	//表示用テキストの分割
	    	Resources res;
	    	String file_result;
	    	String result;
	    	BufferedReader br = null;
	    	InputStream is;
	    	StringBuilder sb = new StringBuilder();
	    
	    	
	    	res = this.getResources();
	    	is = res.openRawResource(R.raw.zukan_help);
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
	    	dialog4 = new Dialog(zukan.this);
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
			d_title.setText("図鑑について");
			d_message=(TextView)dialog4.findViewById(R.id.d_message);
			d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
			d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
			d_message2.setText(str_syouokai[0]);
			d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
			d_syoukai_img.setImageResource(R.drawable.col_cha9);
			d_syoukai_btn=(Button)dialog4.findViewById(R.id.d_button);

			//ダイアログの「OK」ボタンが押された時の処理
			d_syoukai_btn.setOnClickListener(new View.OnClickListener() {
				 	@Override
		            public void onClick(View v) {
					 	count_syokai++;
					 	if(count_syokai<koumoku_syokai){
						 	d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
						 	d_message2.setText(str_syouokai[count_syokai]);
						 	d_syoukai_img.setImageResource(R.drawable.setumei_bt_zukan);
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
            e7.putInt("syokai_zukan", 1);
            e7.commit();
	    }

	    
	}
	
		protected void onPouse() {
			super.onPause();
		}

		@Override
		public void onClick(View v) {
			if(v==btn_zukan_ibe){
				Intent intent = new Intent(zukan.this, K_2.class);
				startActivity(intent);
			}
			else if(v==btn_zukan_kankou){
				Intent intent = new Intent(zukan.this, K.class);
				startActivity(intent);
			}
			else if(v==btn_zukan_chara){
				Intent intent = new Intent(zukan.this, K_3.class);
				startActivity(intent);
			}
			else if(v==btn_zukan_bonus){
				Intent intent = new Intent(zukan.this, K_4.class);
				startActivity(intent);
			}
			
		}
	}