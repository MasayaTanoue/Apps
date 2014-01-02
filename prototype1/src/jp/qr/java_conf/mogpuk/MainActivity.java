package jp.qr.java_conf.mogpuk;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;

@SuppressLint("SimpleDateFormat")
public class MainActivity extends Activity implements OnClickListener{
	
	//ボタンの宣言
	private Button btn_game;
	private Button btn_shop;
	private Button btn_zukan;
	private Button btn_settei;
	private Button btn_rink;
	private Button btn_result;
	private ImageButton btn_change;
	
	//アニメーションのImageView
	private TextView TOP;
	String[] word = {"今日も頑張るラヨ!!","お手伝いに出発ラヨ!!","たまには一休みするラヨー"};
	private Random random;
	private RelativeLayout layout;
	AnimationDrawable anima;
	
	//音楽
	private MediaPlayer oto;
	private MediaPlayer bgm_top;
	SoundPool sp;
	int sp_donn;
	int sp_atari;
	
	Boolean enable_btn;
	Boolean enable_btn_game;
	
	private ScheduledExecutorService service;
	private Handler handler = new Handler();
	
	Dialog dialog;
	Dialog dialog2;
	
	//背景画像変更用
	int kan,ibe,cha;
	int now_back1;
	int now_back2,now_back0;
	
	
	
	//図鑑が揃ったのを知らせる処理用
	int get_info[];
	int get_info_settei[];
	
	//現在の獲得MOGを格納する変数
	int w;
	
	//初回起動時のダイアログ表示用
	Dialog dialog4;
	int count_syokai;
	ImageView d_syoukai_img;
	Button d_syoukai_btn;
	int koumoku_syokai;
	TextView d_message;
	TextView d_message2;
	int syokai_hanbetu;
	private String[] str_syouokai;
	private String[] str_syouokai2;
	Resources res;
	TextView d_title;
	String[] d_title_syousai={"「start」ボタン","「経営結果」ボタン","「交換所」ボタン","「図鑑」ボタン","「設定」ボタン","「HPリンク」ボタン"};
	int[] syokai2_res;
	int[] syokai1_res;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		random = new Random();
				
		//ボタンのID登録	
		btn_game =(Button)findViewById(R.id.btn_game);
		btn_game.setOnClickListener(this);
		//現在放置中なら(enable=false)になる
		SharedPreferences po_btn_game = getSharedPreferences("btn", MODE_PRIVATE);
	    enable_btn_game = po_btn_game.getBoolean("btn_game_enable",true);
		btn_game.setEnabled(enable_btn_game);
		
		btn_shop =(Button)findViewById(R.id.btn_shop);
		btn_shop.setOnClickListener(this);
		
		btn_zukan =(Button)findViewById(R.id.btn_zukan);
		btn_zukan.setOnClickListener(this);
		
		btn_settei =(Button)findViewById(R.id.btn_settei);
		btn_settei.setOnClickListener(this);
		
		btn_rink =(Button)findViewById(R.id.btn_rink);
		btn_rink.setOnClickListener(this);
		
		btn_result =(Button)findViewById(R.id.btn_result);
		btn_result.setOnClickListener(this);
		
		btn_change =(ImageButton)findViewById(R.id.btn_change);
		btn_change.setOnClickListener(this);
		
		layout=(RelativeLayout)findViewById(R.id.Layout_top);
		
		TOP = (TextView)findViewById(R.id.TOP);
		
		
		//各画像獲得情報を読み込み
	    for(int i = 0;i<10;i++){
	    	SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
	    	kan = kan+sp_now_zukan.getInt("now_kan_col"+i, 0);
	    }
	    for(int i = 0;i<12;i++){
	    	SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
	    	ibe = ibe+sp_now_zukan.getInt("now_ibe_col"+i, 0);
	    }
	    for(int i = 0;i<11;i++){
	    	SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
	    	cha = cha+sp_now_zukan.getInt("now_cha_col"+i, 0);
	    }
	    
	    //現在時刻で背景を変える処理
	    final Calendar calendar = Calendar.getInstance();
	    final int hour = calendar.get(Calendar.HOUR_OF_DAY);
	    if(6<=hour&&hour<=15){
			if(kan>=10){
				now_back1 = getResources().getIdentifier("top_bg_a1", "drawable", "jp.qr.java_conf.mogpuk");
				now_back2 = getResources().getIdentifier("top_bg_a2", "drawable", "jp.qr.java_conf.mogpuk");
				now_back0 = getResources().getIdentifier("top_bg_a0", "drawable", "jp.qr.java_conf.mogpuk");
	        }
			else{
				now_back1 = getResources().getIdentifier("top_bg_m1", "drawable", "jp.qr.java_conf.mogpuk");
				now_back2 = getResources().getIdentifier("top_bg_m2", "drawable", "jp.qr.java_conf.mogpuk");
				now_back0 = getResources().getIdentifier("top_background", "drawable", "jp.qr.java_conf.mogpuk");
			}
	    }
	    else if(15<hour&&hour<=19){
			if(ibe>=12){
				now_back1 = getResources().getIdentifier("top_bg_h1", "drawable", "jp.qr.java_conf.mogpuk");
				now_back2 = getResources().getIdentifier("top_bg_h2", "drawable", "jp.qr.java_conf.mogpuk");
				now_back0 = getResources().getIdentifier("top_bg_h0", "drawable", "jp.qr.java_conf.mogpuk");
	        }
			else{
				now_back1 = getResources().getIdentifier("top_bg_m1", "drawable", "jp.qr.java_conf.mogpuk");
				now_back2 = getResources().getIdentifier("top_bg_m2", "drawable", "jp.qr.java_conf.mogpuk");
				now_back0 = getResources().getIdentifier("top_background", "drawable", "jp.qr.java_conf.mogpuk");
			}
	    }
	    else if (hour<6||19<hour&&hour<=24) {
	    	if(cha>=11){
	    		now_back1 = getResources().getIdentifier("top_bg_y1", "drawable", "jp.qr.java_conf.mogpuk");
				now_back2 = getResources().getIdentifier("top_bg_y1", "drawable", "jp.qr.java_conf.mogpuk");
				now_back0 = getResources().getIdentifier("top_bg_y0", "drawable", "jp.qr.java_conf.mogpuk");
				word[0] = "Zzz･･･";
				word[1] = "Zzz･･･";
				word[2] = "Zzz･･･";
	    		
	        }
			else {
				now_back1 = getResources().getIdentifier("top_bg_m1", "drawable", "jp.qr.java_conf.mogpuk");
				now_back2 = getResources().getIdentifier("top_bg_m2", "drawable", "jp.qr.java_conf.mogpuk");
				now_back0 = getResources().getIdentifier("top_background", "drawable", "jp.qr.java_conf.mogpuk");
			}
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	protected  void onResume(){
		super.onResume();
		res = this.getResources();
		
		
		
		//TOP画像の変更（放置中→看板を「お仕事中」になど）
				if (enable_btn_game==true){
					btn_game.setBackgroundResource(R.anim.start_anime);
					anima = (AnimationDrawable)btn_game.getBackground();
					anima.start();
					layout.setBackgroundResource(now_back1);
					btn_change.setEnabled(true);
				}
				else if(enable_btn_game==false){
					btn_game.setBackgroundResource(R.drawable.oshigoto);
					layout.setBackgroundResource(now_back0);
					//きよらスキーがいないので，btn_change.enale=falseに
					btn_change.setEnabled(false);
					
					//終了時刻の表示
					SharedPreferences po1 = getSharedPreferences("time", MODE_PRIVATE);
					//現在時刻の取得
					long now_time = Calendar.getInstance().getTimeInMillis();
			        long time = po1.getLong("end_time",0);
			        long lim_time =time-now_time;
			        SimpleDateFormat sdf = new SimpleDateFormat("m分");
			        TOP.setText("お手伝い終了時間まで\n"+"残り"+sdf.format(lim_time)+"ラヨ!!");
			        if(lim_time<60000){
			        	SimpleDateFormat sdff = new SimpleDateFormat("s秒");
			        	TOP.setText("お手伝い終了時間まで\n"+"残り"+sdff.format(lim_time)+"ラヨ!!");
			        }
			        if(lim_time<0){
			        	SharedPreferences po = getSharedPreferences("btn", MODE_PRIVATE);
			            Editor e = po.edit();
			            e.putBoolean("btn_enable", true);
			            e.commit();
			        }
			        
			        TOP.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
			        TOP.setVisibility(View.VISIBLE);	
				}
		
		
		//初回起動用ダイアログの表示判断
		SharedPreferences po_syokai = getSharedPreferences("syokai", MODE_PRIVATE);
    	syokai_hanbetu = po_syokai.getInt("syokai_main", 0);

		    
		    if(syokai_hanbetu==0){
		    	
		    	syokai1_res = new int[6];
		 		syokai1_res[0] =getResources().getIdentifier("kiyora_syoukai","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai1_res[1] =getResources().getIdentifier("keieikekka_ura","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai1_res[2] =getResources().getIdentifier("kiyora_enjoy","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai1_res[3] =getResources().getIdentifier("coupon_bg","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai1_res[4] =getResources().getIdentifier("icon_settei","drawable", "jp.qr.java_conf.mogpuk");
		    	koumoku_syokai=5;
		    	count_syokai=0;
		    	
		    	//表示用テキストの分割
		    	
		    	String file_result;
		    	String result;
		    	BufferedReader br = null;
		    	InputStream is;
		    	StringBuilder sb = new StringBuilder();
		    

		    	is = res.openRawResource(R.raw.app_help);
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
		    	dialog4 = new Dialog(MainActivity.this);
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
				d_title=(TextView)dialog4.findViewById(R.id.d_title);
				d_title.setText("もぐ～ぽんとは？？");
				d_message=(TextView)dialog4.findViewById(R.id.d_message);
				d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
				d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
				d_message2.setText(str_syouokai[0]);
				d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
				d_syoukai_img.setImageResource(syokai1_res[0]);
				d_syoukai_btn=(Button)dialog4.findViewById(R.id.d_button);

				//ダイアログの「OK」ボタンが押された時の処理
				d_syoukai_btn.setOnClickListener(new View.OnClickListener() {
					 	@Override
			            public void onClick(View v) {
						 	count_syokai++;
						 	if(count_syokai<koumoku_syokai){
							 	d_syoukai_img.setImageResource(syokai1_res[count_syokai]);
							 	d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
							 	d_message2.setText(str_syouokai[count_syokai]);
						 	}
						 	if(count_syokai==1){
						 		d_syoukai_img.setImageResource(R.anim.anim_game_setumei);
								AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
								ani.start();
						 	}
						 	if(count_syokai==3){
						 		d_syoukai_img.setImageResource(R.anim.anim_result_setumei);
								AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
								ani.start();
						 	}
						 	if(count_syokai==4){
						 		d_syoukai_img.setImageResource(R.anim.anim_kupon_setumei);
								AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
								ani.start();
						 	}
						 	if (count_syokai==koumoku_syokai-1) {
							 	d_syoukai_btn.setText("閉じる");
						 	}
						 	if(count_syokai==koumoku_syokai){
						 		dialog4.dismiss();
						 		
						 		//初回用画像リソース読み込み処理
						 		syokai2_res = new int[6];
						 		syokai2_res[0] =getResources().getIdentifier("start","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[1] =getResources().getIdentifier("keieikekka_ura","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[2] =getResources().getIdentifier("icon_koukanjo","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[3] =getResources().getIdentifier("icon_zukan","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[4] =getResources().getIdentifier("icon_settei","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[5] =getResources().getIdentifier("icon_hplink","drawable", "jp.qr.java_conf.mogpuk");
						 		//初回起動用ダイアログの表示判断
								SharedPreferences po_syokai = getSharedPreferences("syokai", MODE_PRIVATE);
						    	syokai_hanbetu = po_syokai.getInt("syokai_main_gamen", 0);

								    
								    if(syokai_hanbetu==0){
								    	
								    	koumoku_syokai=6;
								    	count_syokai=0;

								    	//表示用テキストの分割
								    	String file_result;
								    	String result;
								    	BufferedReader br = null;
								    	InputStream is;
								    	StringBuilder sb = new StringBuilder();
								    
								    	is = res.openRawResource(R.raw.main_help);
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
										str_syouokai2=result.split(",", 0);
								    	// ダイアログの作成と表示
								    	
								        //初回起動時のみ表示する
								    	dialog4 = new Dialog(MainActivity.this);
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
										d_title=(TextView)dialog4.findViewById(R.id.d_title);
										d_title.setText(d_title_syousai[0]);
										d_message=(TextView)dialog4.findViewById(R.id.d_message);
										d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
										d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
										d_message2.setText(str_syouokai2[0]);
										d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
										d_syoukai_img.setImageResource(syokai2_res[0]);
										d_syoukai_btn=(Button)dialog4.findViewById(R.id.d_button);

										//ダイアログの「OK」ボタンが押された時の処理
										d_syoukai_btn.setOnClickListener(new View.OnClickListener() {
											 	@Override
									            public void onClick(View v) {
												 	count_syokai++;
												 	if(count_syokai<koumoku_syokai){
													 	d_syoukai_img.setImageResource(syokai2_res[count_syokai]);
													 	d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
													 	d_message2.setText(str_syouokai2[count_syokai]);
													 	d_title.setText(d_title_syousai[count_syokai]);
												 	}
												 	if(count_syokai==1){
												 		d_syoukai_img.setImageResource(R.anim.kanban_anime2);
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
						                e7.putInt("syokai_main_gamen", 1);
						                e7.commit();
								    }
						 	}
					 	}
				    });
				SharedPreferences po_syokai_m = getSharedPreferences("syokai", MODE_PRIVATE);
		        Editor e7 = po_syokai_m.edit();
                e7.putInt("syokai_main", 1);
                e7.commit();
		    }
		

		
		
		//音の再生
		oto = MediaPlayer.create(getBaseContext(), R.raw.ketteion);
		sp = new SoundPool( 4 ,AudioManager.STREAM_MUSIC,0);
  		sp_atari = sp.load( getBaseContext() , R.raw.se_atari, 1 );
		//BGMの再生
		bgm_top = MediaPlayer.create(getBaseContext(), R.raw.bgm_top);
		bgm_top.setLooping(true);
		bgm_top.start();
		
		//放置の監視
		service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				handler.post(new Runnable() {
					@Override
					public void run() {
						 SharedPreferences po = getSharedPreferences("btn", MODE_PRIVATE);
					     enable_btn = po.getBoolean("btn_enable",false );
					     
					     
						 btn_result.setEnabled(enable_btn);
						 if(enable_btn==true){
							 btn_result.setBackgroundResource(R.anim.kanban_anime);
							 AnimationDrawable animation = (AnimationDrawable)btn_result.getBackground();
							 animation.start();
							 TOP.setVisibility(View.INVISIBLE);
						 }
					}
			});
		}
		}, 0, 500, TimeUnit.MILLISECONDS);
		
		//アンケートダイアログの表示
		
		//ゲーム回数，交換回数を読み込み
	    SharedPreferences po0 = getSharedPreferences("time", MODE_PRIVATE);
        int game = po0.getInt("game", 0);
        SharedPreferences po2 = getSharedPreferences("name", MODE_PRIVATE);
        int kaisuu = po2.getInt("kaisuu", 0);
        
        SharedPreferences po5 = getSharedPreferences("syokai", MODE_PRIVATE);
        int syokai_anke = po5.getInt("syokai_anke", 0);
        //総ゲーム回数3回以上，クーポン交換1回以上で出る
        if(syokai_anke==0){
	        if(game>=3&&kaisuu>0){
	        	
	        	dialog = new Dialog(MainActivity.this);
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
				d_title.setText("お願い");
				TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
				d_message.setText("もぐ～ぽんをご利用頂きありがとうございます。\n"+"MOGPUKの更なる発展のために，アンケートへのご協力をお願いいたします。\n"+
				"※お答えいただいた方にはクーポン1枚分相当のMOG（500MOG）プレゼント");

				//ダイアログの「OK」ボタンが押された時の処理
				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
					 @Override
			            public void onClick(View v) {
						 
						//w（現在のポイント）に入っている値を呼び出す。この処理はkupon.java,Result.javaと連動している
			    	        SharedPreferences po10 = getSharedPreferences("n", MODE_PRIVATE);
			    	        w = po10.getInt("w", 0);
			    	        
			    	        //wに図鑑ボーナスより獲得したポイントを足しこむ
			    	        w = w + 500;
			    			
			    	        //wに入っている現在のポイントを"w"に入れ込み保存する。この処理はkupon.javaResult.javaと連動する
			    		    SharedPreferences po = getSharedPreferences("n", MODE_PRIVATE);
			    	        Editor e = po.edit();
			    	        e.putInt("w", w);
			    	        e.commit();
						 
						 Uri uri = Uri.parse("https://docs.google.com/forms/d/1q2mqZ-WQPB9DBt6cJ0N7nyhU2phkBs49nxljoAVUjfQ/viewform");
							Intent hp = new Intent(Intent.ACTION_VIEW,uri);
							 startActivity(hp);
							 dialog.dismiss();
					}
				});
				 
				//ダイアログの「×」が押された時の処理
				dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	        	
	        	SharedPreferences po_syokai_a = getSharedPreferences("syokai", MODE_PRIVATE);
		        Editor e7 = po_syokai_a.edit();
	            e7.putInt("syokai_anke", 1);
	            e7.commit();
	        }
        }
		
		//ボーナス獲得通知
		get_info=new int[9];
		for(int i=0;i<9;i++){
			SharedPreferences pou = getSharedPreferences("collection", MODE_PRIVATE);
	    	get_info[i] = pou.getInt("get_info"+i, 0);
	    	if(get_info[i]==1){

	    		//ポイント増える処理
	    		if(i<5){
	    			int[] point={100,500,750,1000,1500};
	    			//w（現在のポイント）に入っている値を呼び出す。この処理はkupon.java,Result.javaと連動している
	    	        SharedPreferences po10 = getSharedPreferences("n", MODE_PRIVATE);
	    	        w = po10.getInt("w", 0);
	    	        
	    	        //wに図鑑ボーナスより獲得したポイントを足しこむ
	    	        w = w + point[i];
	    			
	    	        //wに入っている現在のポイントを"w"に入れ込み保存する。この処理はkupon.javaResult.javaと連動する
	    		    SharedPreferences po = getSharedPreferences("n", MODE_PRIVATE);
	    	        Editor e = po.edit();
	    	        e.putInt("w", w);
	    	        e.commit();
	    		}
	    		String[] chara_name= {"図鑑×1","図鑑×3","図鑑×5","図鑑×10","図鑑×15","観光地図鑑コンプリート","イベント図鑑コンプリート","キャラ図鑑コンプリート","全図鑑コンプリート"};
	    		String[] chara_syousai= {"100MOG","500MOG","750MOG","1000MOG","1500MOG","背景　昼","背景　夕","背景　夜","きよらスキーが･･･"};
	    		
	    		sp.play( sp_atari , 1.0F , 1.0F , 0 , 0 , 1.0F );
	    		
	    		dialog = new Dialog(MainActivity.this);
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
	       		d_result_title.setText("図鑑ボーナス");
	       		TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
	       		dialog_titile.setText("「"+chara_name[i]+"」\n"+"達成!!");
	       		ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
	       		image.setImageResource(R.drawable.min_egaokiyora);
	       		TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
	       		dialog_setumei.setText("「"+chara_syousai[i]+"」 を\n"+"獲得したラヨ!!");
	       		TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
	       		dialog_close.setText("×");
	       		
	       		//ダイアログの「×」が押された時の処理
	       		dialog.findViewById(R.id.d_result_close_button).setOnClickListener(new View.OnClickListener() {
	       			@Override
	       			public void onClick(View v) {
	       				dialog.dismiss();
	       			}
	       		});
	    		get_info[i]++;
	    		Editor e3 = pou.edit();
                e3.putInt("get_info"+i, get_info[i]);
                e3.commit();
	    	}
		}
		
		get_info_settei=new int[9];
			for(int i=0;i<9;i++){
				SharedPreferences pou1 = getSharedPreferences("collection", MODE_PRIVATE);
		    	get_info_settei[i] = pou1.getInt("get_info_settei"+i, 0);
		    	if(get_info_settei[i]==1){
		    		
		    		
		    		String[]chara_name={"1回","5回","10回","15回","20回","40回","60回","80回","100回"};
		    		String[]chara_syousai={"おしとい氏の鬼のお手玉","セン子氏の礼儀作法","タキの気遣い","赤ばばぁの知恵","孫のお手伝い","きよら父さんの力","メオの写真","マンガンじいのお守り","きよら母さんの買い物術"};
		    		sp.play( sp_atari , 1.0F , 1.0F , 0 , 0 , 1.0F );
		    		dialog2 = new Dialog(MainActivity.this);
		       		//タイトルの非表示
		       		dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		       		//フルスクリーン
		       		dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		       		//dialog_result.xmlを基にしてダイアログを作成
		       		dialog2.setContentView(R.layout.dialog_result2);
		       		//背景を透明にする
		       		dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		       		dialog2.show();
		       		
		       		//ダイアログに表示するテキストのID登録と表示命令
		       		TextView d_result_title=(TextView)dialog2.findViewById(R.id.d_result_title2);
		       		d_result_title.setText("お手伝い回数ボーナス");
		       		TextView dialog_titile=(TextView)dialog2.findViewById(R.id.txt_result_dialog_titile2);
		       		dialog_titile.setText("お手伝い回数が\n"+"「"+chara_name[i]+"」に到達!!");
		       		ImageView image = (ImageView)dialog2.findViewById(R.id.imag_getcol2);
		       		image.setImageResource(R.drawable.col_cha9);
		       		TextView dialog_setumei=(TextView)dialog2.findViewById(R.id.txt_result_dialog_setumei2);
		       		dialog_setumei.setText("「"+chara_syousai[i]+"」が\n"+"設定できるようになりました!!");
		       		
		       		TextView dialog_close=(TextView)dialog2.findViewById(R.id.d_result_close_button2);
		       		dialog_close.setText("×");
		       		
		       		//ダイアログの「×」が押された時の処理
		       		dialog2.findViewById(R.id.d_result_close_button2).setOnClickListener(new View.OnClickListener() {
		       			@Override
		       			public void onClick(View v) {
		       				dialog2.dismiss();
		       			}
		       		});
		    		get_info_settei[i]++;
		    		Editor e4 = pou1.edit();
	                e4.putInt("get_info_settei"+i, get_info_settei[i]);
	                e4.commit();
		    	}
			}
	}
	
	@Override
	public void onClick(View v) {
		
		oto.start();
		if(v==btn_game){
			Intent intent = new Intent(MainActivity.this, stage_select.class);
			startActivity(intent);
		}
		else if(v==btn_shop){
			SharedPreferences po_shop = getSharedPreferences("shop", MODE_PRIVATE);
	        Editor es = po_shop.edit();
            es.putInt("line", 0);
            es.commit();
			Intent intent = new Intent(MainActivity.this, Shop.class);
			startActivity(intent);
		}
		else if(v==btn_zukan){
			Intent intent = new Intent(MainActivity.this, zukan.class);
			startActivity(intent);
		}
		else if(v==btn_settei){
			Intent intent = new Intent(MainActivity.this, Settei.class);
			startActivity(intent);
		}
		else if(v==btn_rink){
			//ダイアログの作成
			dialog = new Dialog(MainActivity.this);
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
			d_message.setText("HPへ移動するラヨ～？");

			//ダイアログの「OK」ボタンが押された時の処理
			dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
				 @Override
		            public void onClick(View v) {
					 Uri uri = Uri.parse("http://www.pu-kumamoto.ac.jp/~iimulab/mogpuk_mobile/");
						Intent hp = new Intent(Intent.ACTION_VIEW,uri);
						 startActivity(hp);
						 dialog.dismiss();
				}
			});
			 
			//ダイアログの「×」が押された時の処理
			dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		 }	
		
		else if(v==btn_result){
			//データ受け渡し
			Intent it_res_gamenhe = new Intent(MainActivity.this, Come_back.class);
			startActivity(it_res_gamenhe);
		}
		else if(v==btn_change){
			//アニメーションをタッチしても動かなくする（ロックする）
			btn_change.setEnabled(false);
			layout.setBackgroundResource(now_back2);
			Handler hdl = new Handler();
	 		hdl.postDelayed(new splashHandler(), 1000);
			//ランダムでテキストビューを表示
			TOP.setText(word[random.nextInt(word.length)]);
			TOP.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
			TOP.setVisibility(View.VISIBLE);
		}
	} 

			
	class splashHandler implements Runnable {
		 	public void run() {
		 			TOP.setVisibility(View.INVISIBLE);
		 			layout.setBackgroundResource(now_back1);
		 			//ロックを解除
		 			btn_change.setEnabled(true);
		 		}
		 	}
		
	
	
	protected  void onPause(){
		super.onPause();
		service.shutdown();
		if (bgm_top.isPlaying()) {
			bgm_top.stop();
			bgm_top.release();
			}	
	}
	
	protected void onDestroy() {
		}

	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        switch (event.getKeyCode()) {
	        case KeyEvent.KEYCODE_BACK:
	            // ダイアログ表示など特定の処理を行いたい場合はここに記述
	            // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返す
	        	
	        	//ダイアログの作成
				dialog = new Dialog(MainActivity.this);
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
				d_message.setText("終了しますか？");

				//ダイアログの「OK」ボタンが押された時の処理
				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
					 @Override
			            public void onClick(View v) {
						 dialog.dismiss();
						 moveTaskToBack (true);
					}
				});
				 
				//ダイアログの「×」が押された時の処理
				dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
	            return true;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}

	
}
