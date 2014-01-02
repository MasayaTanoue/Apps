package jp.qr.java_conf.mogpuk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class stage_select extends Activity implements OnClickListener {
	
	Dialog dialog;
	Bitmap[] stage_image =new Bitmap[3];
	String[] stage_mei = {"黒川エリア","満願寺エリア","赤馬場エリア"};
	ImageButton bt_st0,bt_st1,bt_st2;
	int kaisuu;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.stage_game);
	    
	    //ボタンのID登録
	    bt_st0=(ImageButton)findViewById(R.id.bt_st0);
	    bt_st1=(ImageButton)findViewById(R.id.bt_st1);
	    bt_st2=(ImageButton)findViewById(R.id.bt_st2);
	    bt_st0.setOnClickListener(this);
	    bt_st1.setOnClickListener(this);
	    bt_st2.setOnClickListener(this);
	    TextView txt=(TextView)findViewById(R.id.textView2);
	    txt.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    
	    
	    //累計交換枚数の読み込み
	    SharedPreferences po3 = getSharedPreferences("name", MODE_PRIVATE);
        kaisuu = po3.getInt("kaisuu", 0);

        
	    }
	protected  void onPause(){
		super.onPause();
	}
	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		if(v==bt_st0){
			dialog = new Dialog(stage_select.this);
			//タイトルの非表示
			dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			//フルスクリーン
			dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
			//dialog_custom.xmlを基にしてダイアログを作成
			dialog.setContentView(R.layout.dialog_custom);
			//背景を透明にする
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog.show();
			
			//ダイアログに表示するテキストのID登録と表示命令
			TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
			d_title.setText("確認");
			TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
			d_message.setText("きよら母さんの\n"+"旅館をお手伝いするラヨ～？");

			//ダイアログの「OK」ボタンが押された時の処理
			dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
				 @Override
		            public void onClick(View v) {
					 Intent intent = new Intent(stage_select.this, Game.class);
					 startActivity(intent);
				 }
				});
			dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
				 @Override
		            public void onClick(View v) {
					 dialog.dismiss();
				 }
				});
    	   }
    	   else if(v==bt_st2){
    		   if(kaisuu<1){
        		   dialog = new Dialog(stage_select.this);
					//タイトルの非表示
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					//フルスクリーン
					dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
					//dialog_result.xmlを基にしてダイアログを作成
					dialog.setContentView(R.layout.dialog_result);
					//背景を透明にする
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
					
					//ダイアログに表示するテキストのID登録と表示命令
					TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
					d_result_title.setText("確認");
					TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
					dialog_titile.setText("マンガンじいの温泉");
					ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
					image.setImageResource(R.drawable.col_cha3);
					TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
					dialog_setumei.setText("クーポンを\n"+"あと"+(1-kaisuu)+"回交換すると \n"+"遊べるようになるラヨー");
					TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
					dialog_close.setText("×");
					
					//ダイアログの「×」が押された時の処理
					dialog.findViewById(R.id.d_result_close_button).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
        		   }
        		   else if(kaisuu>=1){
        			   dialog = new Dialog(stage_select.this);
        			 //タイトルの非表示
        				dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        				//フルスクリーン
        				dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        				//dialog_custom.xmlを基にしてダイアログを作成
        				dialog.setContentView(R.layout.dialog_custom);
        				//背景を透明にする
        				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        				dialog.show();
        				
        				//ダイアログに表示するテキストのID登録と表示命令
        				TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
        				d_title.setText("確認");
        				TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
        				d_message.setText("マンガンじいの温泉を\n"+"お手伝いするラヨ～？");

        				//ダイアログの「OK」ボタンが押された時の処理
        				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
        					 @Override
        			            public void onClick(View v) {
        						 Intent intent = new Intent(stage_select.this, Game2.class);
        							startActivity(intent);
        					 }
        					});
        				dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
        					 @Override
        			            public void onClick(View v) {
        						 dialog.dismiss();
        					 }
        					});
						
					}
    	   }
    	   else if(v==bt_st1){
    		   if(kaisuu<3){
        		   dialog = new Dialog(stage_select.this);
					//タイトルの非表示
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					//フルスクリーン
					dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
					//dialog_result.xmlを基にしてダイアログを作成
					dialog.setContentView(R.layout.dialog_result);
					//背景を透明にする
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
					
					//ダイアログに表示するテキストのID登録と表示命令
					TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
					d_result_title.setText("確認");
					TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
					dialog_titile.setText("赤ばばぁのお蕎麦屋さん");
					ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
					image.setImageResource(R.drawable.col_cha5);
					TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
					dialog_setumei.setText("クーポンを\n"+"あと"+(3-kaisuu)+"回交換すると \n"+"遊べるようになるラヨー");

					TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
					dialog_close.setText("×");
					
					//ダイアログの「×」が押された時の処理
					dialog.findViewById(R.id.d_result_close_button).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							dialog.dismiss();
						}
					});
        		   }
        		   else if(kaisuu>=3){
        			   dialog = new Dialog(stage_select.this);
        			 //タイトルの非表示
        				dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        				//フルスクリーン
        				dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        				//dialog_custom.xmlを基にしてダイアログを作成
        				dialog.setContentView(R.layout.dialog_custom);
        				//背景を透明にする
        				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        				dialog.show();
        				
        				//ダイアログに表示するテキストのID登録と表示命令
        				TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
        				d_title.setText("確認");
        				TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
        				d_message.setText("赤ばばぁのお蕎麦屋さんを\n"+"お手伝いするラヨ～？");

        				//ダイアログの「OK」ボタンが押された時の処理
        				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
        					 @Override
        			            public void onClick(View v) {
        						 Intent intent = new Intent(stage_select.this, Game3.class);
        							startActivity(intent);
        					 }
        					});
        				dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
        					 @Override
        			            public void onClick(View v) {
        						 dialog.dismiss();
        					 }
        					});
						
					}
    	   }
	}
}
