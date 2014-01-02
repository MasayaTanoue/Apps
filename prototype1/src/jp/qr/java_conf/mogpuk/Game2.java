package jp.qr.java_conf.mogpuk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Game2 extends Activity implements OnClickListener  {
	
	//背景を動的に変えるためのレイアウトの宣言
	RelativeLayout rl; 
	
	//カード裏面の絵柄を格納するArrayList
	ArrayList<Integer> pct = new ArrayList<Integer>();	
	ArrayList<Integer> check = new ArrayList<Integer>();
	
	//カードの配列
	private ImageView[] crd = new ImageView [20];	
	
	//Test用

	//現在めくられている枚数を表す変数
	int count ;
	
	//選んだカードが同じか判別する変数
	int a;
	int b;
	
	//選んだカードを格納する配列
	int[] first = new int[2];
	
	//カードの現在の状態を格納する配列　0＝裏，1＝選択状態，2＝揃っている
	int[] stat = new int[20];
	
	int kouka_hanbetu;
	
	//TextView 宣言
	TextView tairyoku;
	TextView gizyutu;
	TextView un;
	TextView tairyoku_rank;
	TextView gizyutu_rank;
	TextView un_rank;
	TextView fukidashi;
	TextView tai;
	TextView gizyu;
	TextView u;
	
	ImageView kiyora;
	
	//ステータス用　ランダム
	Random rnd = new Random();
	
	int[] kekka = new int[10];
	
	//ステータス表示用
	int tairyoku_now;
	int gizyutu_now;
	int un_now;
	int end_count;
	
	//はずれ判別用
	int hazure1;
	int hazure2;
	int hazure_hanbetu;
	int hazure_count1;
	int hazure_count2;
	
	//音楽
	private MediaPlayer bgm_game;
	SoundPool sp;
	int sp_donn;
	int sp_atari;
	int sp_hazure;
	int sp_bubu;
	
	//rank判別用
	int rank_hanbetu;
	int stat_hanbetu;
	String rank;
	int rank_color;
	
	Dialog dialog;
	
	//詳細設定読み込み用
	int[] sta;
	
	//初回起動時のダイアログ表示用
	int count_syokai;
	ImageView d_syoukai_img;
	Button d_syoukai_btn;
	int koumoku_syokai;
	TextView d_message;
	TextView d_message2;
	String[] str_syouokai;
	Resources res;
	TextView d_title;
	String[] d_title_syousai;
	int[] syokai_res;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.game);
	    
	    res = this.getResources();

        //最初に表示するダイアログの作成
		dialog = new Dialog(Game2.this);
		//タイトルの非表示
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//フルスクリーン
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		//dialog_custom.xmlを基にしてダイアログを作成
		dialog.setContentView(R.layout.dialog_custom_game_start);
		//背景を透明にする
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.show();
		
		//ダイアログに表示するテキストのID登録と表示命令
		d_title=(TextView)dialog.findViewById(R.id.d_title);
		d_title.setText("Game Start");
		d_message=(TextView)dialog.findViewById(R.id.d_message);
		d_message.setText("一緒にお手伝いする\n"+"仲間を探すラヨ!!");
		//ダイアログの「OK」ボタンが押された時の処理
		dialog.findViewById(R.id.d_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				dialog.dismiss();
			}
		});
		dialog.findViewById(R.id.d_button2).setOnClickListener(new View.OnClickListener() {
			 @Override
	            public void onClick(View v) {
				 dialog.dismiss();
				 count_syokai=0;
				 koumoku_syokai=4;
				//初回用画像リソース読み込み処理
			 		syokai_res = new int[koumoku_syokai];
			 		syokai_res[0] =getResources().getIdentifier("col_cha9","drawable", "jp.qr.java_conf.mogpuk");
			 		syokai_res[1] =getResources().getIdentifier("crd2","drawable", "jp.qr.java_conf.mogpuk");
			 		syokai_res[2] =getResources().getIdentifier("crd9","drawable", "jp.qr.java_conf.mogpuk");
			 		syokai_res[3] =getResources().getIdentifier("col_cha9","drawable", "jp.qr.java_conf.mogpuk");
				//表示用テキストの分割
			    	String file_result;
			    	String result;
			    	BufferedReader br = null;
			    	InputStream is;
			    	StringBuilder sb = new StringBuilder();
			    	
			    	
			    	is = res.openRawResource(R.raw.txt_game2);
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
				 
			    	dialog = new Dialog(Game2.this);
					//タイトルの非表示
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					//フルスクリーン
					dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
					//dialog_custom.xmlを基にしてダイアログを作成
					dialog.setContentView(R.layout.dialog_custom_syokai);
					//背景を透明にする
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					//ダイアログ表示時のbackボタンを無効に
					dialog.setCancelable(false);
					dialog.show();
					
					//ダイアログに表示するテキストのID登録と表示命令
					d_title=(TextView)dialog.findViewById(R.id.d_title);
					d_title.setText("遊び方");
					d_message=(TextView)dialog.findViewById(R.id.d_message);
					d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
					d_message2=(TextView)dialog.findViewById(R.id.d_message2);
					d_message2.setText(str_syouokai[0]);
					d_syoukai_img=(ImageView)dialog.findViewById(R.id.imag_syokai);
					d_syoukai_img.setImageResource(R.anim.anim_crd_cha);
					AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
					ani.start();
					d_syoukai_btn=(Button)dialog.findViewById(R.id.d_button);
			
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
							 	if(count_syokai==1){
							 		d_syoukai_img.setImageResource(R.anim.anim_crd_setumei);
									AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
									ani.start();
							 	}
							 	if(count_syokai==2){
							 		d_syoukai_img.setImageResource(R.anim.anim_crd_hazure);
									AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
									ani.start();
							 	}
							 	if (count_syokai==koumoku_syokai-1) {
								 	d_syoukai_btn.setText("ゲームスタート");
							 	}
							 	if(count_syokai==koumoku_syokai){
							 		dialog.dismiss();
							 	}
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
		});
	    
	    rl=(RelativeLayout)findViewById(R.id.layout_game_main);
	    rl.setBackgroundResource(R.drawable.bg_repeat_man);
	
	    tairyoku=(TextView)findViewById(R.id.txt_tairyoku_val);
	    gizyutu=(TextView)findViewById(R.id.txt_gizyutu_val);
	    un=(TextView)findViewById(R.id.txt_un_val);
	    
	    tairyoku_rank=(TextView)findViewById(R.id.txt_tairyoku_rank);
	    gizyutu_rank=(TextView)findViewById(R.id.txt_gizyutu_rank);
	    un_rank=(TextView)findViewById(R.id.txt_un_rank);
	    
	    fukidashi=(TextView)findViewById(R.id .txt_game_fuki);
	    fukidashi.setText("カードを選ぶラヨー！！");
	    fukidashi.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    
	    kiyora = (ImageView)findViewById(R.id.imag_game_kiyo);
	    
	    //font指定
	    tai=(TextView)findViewById(R.id.txt_tairyoku);
	    gizyu=(TextView)findViewById(R.id.txt_gizyutu);
	    u=(TextView)findViewById(R.id.txt_un);
	    
	    //カード裏面の画像をリソースから設定
	    for(int i = 0; i < 10 ; i++){
	    	int crd = getResources().getIdentifier("crd" + (i), "drawable", "jp.qr.java_conf.mogpuk");
	        pct.add(crd);
	        pct.add(crd);
	    }
	    // ArrayList　pctの中身をシャッフルし画像がランダムで表示されるようにする
		Collections.shuffle(pct); 
		
		hazure1 = getResources().getIdentifier("crd" + (8), "drawable", "jp.qr.java_conf.mogpuk");
		hazure2 = getResources().getIdentifier("crd" + (9), "drawable", "jp.qr.java_conf.mogpuk");
		
		//ImageView配列crdのそれぞれをXML内のIDと結びつける
		 for(int j = 0; j < 20 ; j++){
		    	int img_view = getResources().getIdentifier("imag_ura" + (j+1), "id", "jp.qr.java_conf.mogpuk");

		    	crd[j]=(ImageView)findViewById(img_view); 
		    	}
	    //ImageView配列crdのそれぞれにリスナを登録
	    for(int c=0;c<crd.length;c++){
	    	crd[c].setOnClickListener(this);
	    }
	    
	    //テスト用
	    
	    //使用する変数の初期化
	    count = 0;
	    hazure_count1=0;
	    hazure_count2=0;
	
	    a=0;
	    b=0;
	    for(int i=0;i<stat.length;i++){
	    stat[i]=0;
	    }
	    
	    tairyoku_now=0;
	    gizyutu_now=0;
	    un_now=0;
	    
sta = new int[9];
	    
	    for(int i=0;i<9;i++){
	    	SharedPreferences po2 = getSharedPreferences("tgl", MODE_PRIVATE);
            sta[i] = po2.getInt("status"+i, 0);
	    }
	    if(sta[0]==1){
	    	tairyoku_now = tairyoku_now + 15;
	    	tairyoku.setText(""+tairyoku_now);
	    	rank(tairyoku_now,0);
	    }
	    if(sta[1]==1){
	    	gizyutu_now = gizyutu_now + 15;
	    	gizyutu.setText(""+gizyutu_now);
	    	rank(gizyutu_now,1);
	    }
	    if(sta[2]==1){
	    	un_now =un_now + 15;
	    	rank(un_now,2);
	    	un.setText(""+un_now);
	    }
	    if(sta[5]==1){
	    	tairyoku_now = tairyoku_now + 30;
	    	rank(tairyoku_now,0);
	    	tairyoku.setText(""+tairyoku_now);
	    }
	    if(sta[6]==1){
	    	gizyutu_now = gizyutu_now + 30;
	    	rank(gizyutu_now,1);
	    	gizyutu.setText(""+gizyutu_now);
	    }
	    if(sta[7]==1){
	    	un_now =un_now + 30;
	    	rank(un_now,2);
	    	un.setText(""+un_now);
	    }
	}
	
	public void onResume(){
		super.onResume();
		 //音楽の読み込み
  		bgm_game = MediaPlayer.create(getBaseContext(), R.raw.bgm_game2);
  		
  		sp = new SoundPool( 4 ,AudioManager.STREAM_MUSIC,0);
  		sp_donn = sp.load( getBaseContext() , R.raw.se_donn03, 1 );
  		sp_atari = sp.load( getBaseContext() , R.raw.se_atari, 1 );
  		sp_hazure = sp.load( getBaseContext() , R.raw.se_hazure, 1 );
  		sp_bubu = sp.load( getBaseContext() , R.raw.se_bubu, 1 );

  		//BGMの再生
  		bgm_game.setLooping(true);
  		bgm_game.start();
	}


	@Override
	public void onClick(View v) {
		
		//クリックされたImageViewを判別
		for(int j = 0 ; j<crd.length;j++){
				if(v==crd[j]){	
					//同じカード（カードの状態が１）なら反応しないようにする
						if(stat[j]==0){
							stat[j]=1;
							crd[j].setImageResource(pct.get(j));
								
							//ハズレの処理
							hazure_hanbetu = pct.get(j);
							if(hazure_hanbetu==hazure1){
								kiyora.setImageResource(R.drawable.nayami);
								sp.play( sp_hazure , 4.0F , 4.0F , 0 , 0 , 1.0F );
								kouka(hazure_hanbetu);
								count++;
							}
							else if(hazure_hanbetu==hazure2){
								kiyora.setImageResource(R.drawable.nayami);
								sp.play( sp_hazure , 4.0F , 4.0F , 0 , 0 , 1.0F );
								kouka(hazure_hanbetu);
								count++;
							}
							else{
								kiyora.setImageResource(R.drawable.kiyora_enjoy);
								count++;		//現在選択されているカードの枚数を数える
								a=pct.get(j);	//変数aにfirst[]に記録されている数字を添字としたpct内の画像を取得させる
								sp.play( sp_atari , 1.0F , 1.0F , 0 , 0 , 1.0F );
								kouka(a);
							}
							Handler hdl = new Handler();
							hdl.postDelayed(new splashHandler(), 200);
							for(int c=0;c<crd.length;c++){
								crd[c].setEnabled(false);
								
							}
							fukidashi.setText("あと"+(5-count)+"枚選べるラヨー");	
							if(count==5){
								 dialog = new Dialog(Game2.this);
								//タイトルの非表示
									dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
									//フルスクリーン
									dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
									//dialog_custom.xmlを基にしてダイアログを作成
									dialog.setContentView(R.layout.dialog_custom_game);
									//背景を透明にする
									dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
									//ダイアログ表示時のbackボタンを無効に
									dialog.setCancelable(false);
									
									dialog.show();
									
									//ダイアログに表示するテキストのID登録と表示命令
									TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
									d_title.setText("出発確認");
									TextView vo=(TextView)dialog.findViewById(R.id.txt_custom_vo);
									TextView tai=(TextView)dialog.findViewById(R.id.txt_custom_tai);
									TextView tai_val=(TextView)dialog.findViewById(R.id.txt_custom_tai_val);
									TextView miryo=(TextView)dialog.findViewById(R.id.txt_custom_miryo);
									TextView miryo_val=(TextView)dialog.findViewById(R.id.txt_custom_miryo_val);
									TextView un=(TextView)dialog.findViewById(R.id.txt_custom_un);
									TextView un_val=(TextView)dialog.findViewById(R.id.txt_custom_un_val);
									
									
									vo.setText("この結果でお手伝いを始めます。");
									tai.setText("体力");
									tai_val.setText(""+tairyoku_rank.getText());
									tai_val.setTextColor(tairyoku_rank.getTextColors());
									miryo.setText("魅力");
									miryo_val.setText(""+gizyutu_rank.getText());
									miryo_val.setTextColor(gizyutu_rank.getTextColors());
									un.setText("運");
									un_val.setText(""+un_rank.getText());
									un_val.setTextColor(un_rank.getTextColors());


									
									//ダイアログの「OK」ボタンが押された時の処理
									dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
										 @Override
								            public void onClick(View v) {
											 if (bgm_game.isPlaying()) {
													bgm_game.stop();
													}

												//放置型への遷移

												//AlarmManagerの設定								
												AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
												Context ct = getApplicationContext();
												//サービス用のインテントの発行
												Intent timer = new Intent(ct,TimerService.class);
												PendingIntent pi = PendingIntent.getService(ct, 0, timer, 0);
												
												
												//現在時刻の取得
												long time = Calendar.getInstance().getTimeInMillis();
												//経営終了時刻の計算
												long end_time=time+300*1000;
												//設定がされているなら放置時間をを半分に
											    SharedPreferences po2 = getSharedPreferences("tgl", MODE_PRIVATE);
										        int sta = po2.getInt("status"+8, 0);
										        if(sta==1){
										        	end_time=time+150*1000;
										        }
												//セットする。
												am.set (AlarmManager.RTC, end_time,pi);
												
												
												//同時にTOP画面へ遷移するためのインテントの発行
												Intent it = new Intent(Game2.this, Go.class);
												
												//経営結果を保存
												SharedPreferences sp_result = getSharedPreferences("result", MODE_PRIVATE);
										        Editor e_result = sp_result.edit();
										        e_result.putInt("raikyakusuu", tairyoku_now);
										        e_result.putInt("manzoku", gizyutu_now);
										        e_result.putInt("bonus", un_now);
										        e_result.putInt("stage", 3);
										        e_result.commit();
												
												//TOPのゲームボタンをロック情報を保存
												SharedPreferences po = getSharedPreferences("btn", MODE_PRIVATE);
										        Editor e = po.edit();
										        e.putBoolean("btn_game_enable", false);
										        e.commit();
										        
										      //終了時間の保存（TOP画面に表示させる）
										        SharedPreferences po1 = getSharedPreferences("time", MODE_PRIVATE);
										        Editor e1 = po1.edit();
										        e1.putLong("end_time",end_time);
										        e1.commit();
												
												startActivity(it);
												 dialog.dismiss();
										}
									});
									 
									//ダイアログの「×」が押された時の処理
									dialog.findViewById(R.id.close_button).setOnClickListener(null); 
							}
					}
			}
		}
	}
	class splashHandler implements Runnable {
 		public void run() {
 			for(int c=0;c<crd.length;c++){
				crd[c].setEnabled(true);
			}
 			kiyora.setImageResource(R.drawable.kiyora_stand);
		}
	}
	
	public void kouka(int kouka_hanbetu) {
		
		//体力
		kekka[0]=rnd.nextInt(10)+15;
		kekka[1]=rnd.nextInt(10)+30;
		kekka[2]=rnd.nextInt(10)+50;
		//魅力
		kekka[3]=rnd.nextInt(10)+15;
		kekka[4]=rnd.nextInt(10)+30;
		kekka[5]=rnd.nextInt(10)+50;
		//運
		kekka[6]=rnd.nextInt(10)+30;
		kekka[7]=rnd.nextInt(10)+50;
		
		/*
		for(int i= 0;i<2;i++){
			for(int j=15;j<45;j=j+15){
				kekka[i] = rnd.nextInt(j)+(j-15);
			}
		}
		for(int i= 3;i<5;i++){
			for(int j=15;j<45;j=j+15){
				kekka[i] = rnd.nextInt(j)+(j-15);
			}
		}
		for(int i=6 ;i<8;i++){
			for(int j=15;j<30;j=j+15){
				kekka[i] = rnd.nextInt(j)+(j-15);
			}
		}*/
		
		
		 for(int i = 0; i < 10 ; i++){
			 //引いたカードが何かを確認する配列
			 	int check_url = getResources().getIdentifier("crd" + (i), "drawable", "jp.qr.java_conf.mogpuk");
			 	check.add(check_url);
		    }
		
		 for(int i = 0; i < 10 ; i++){
			 int kakunin = check.get(i);
			 if(kouka_hanbetu==kakunin){
				 if(0<=i && i<3){
					 rank_hanbetu = tairyoku_now + kekka[i];
					 tairyoku_now = tairyoku_now + kekka[i];
					 tairyoku.setText(""+tairyoku_now);
					 stat_hanbetu = 0;
					 rank(rank_hanbetu,stat_hanbetu);
					 }
				 if(3<=i && i<6){
					 rank_hanbetu = gizyutu_now + kekka[i];
					 gizyutu_now = gizyutu_now + kekka[i];
					 gizyutu.setText(""+gizyutu_now);
					 stat_hanbetu = 1;
					 rank(rank_hanbetu,stat_hanbetu);
				 }
				 if(6<=i&&i<8){
					 rank_hanbetu = un_now + kekka[i];
					 un_now =un_now + kekka[i];
					 un.setText(""+un_now);
					 stat_hanbetu = 2;
					 rank(rank_hanbetu,stat_hanbetu);
				 }
				 if(8<=i&&i<10){
					 //全ステータスリセット
					 int batu=rnd.nextInt(2);
					 if(batu==0){
						 rank_hanbetu = 0;
						 tairyoku_now = 0;
						 tairyoku.setText(""+tairyoku_now);
						 stat_hanbetu = 0;
						 rank(rank_hanbetu,stat_hanbetu);
					 }
					 else if(batu==1){
						 rank_hanbetu = 0;
						 gizyutu_now = 0;
						 gizyutu.setText(""+gizyutu_now);
						 stat_hanbetu = 1;
						 rank(rank_hanbetu,stat_hanbetu);
					 }
					 else if(batu==2){
						 rank_hanbetu = 0;
						 un_now = 0;
						 un.setText(""+un_now);
						 stat_hanbetu = 2;
						 rank(rank_hanbetu,stat_hanbetu);
					 }
				 }
			 }
		 }
	}
	public void rank(int rank_hanbetu,int stat_hanbetu){
		
	
		if(0<rank_hanbetu&&rank_hanbetu<=30){
			rank = "D";
			rank_color=Color.GREEN;
		}		
		else if(30<rank_hanbetu&&rank_hanbetu<=50){
			rank="C";
			rank_color =Color.CYAN;
		}
		else if(50<rank_hanbetu&&rank_hanbetu<=70){
			rank="B";
			rank_color =Color.BLUE;
		}
		else if(70<rank_hanbetu&&rank_hanbetu<=90){
			rank="A";
			rank_color =Color.RED;
		}
		else if(90<rank_hanbetu){
			rank="S";
			rank_color =Color.MAGENTA;
		}
		else {
			rank="E";
			rank_color =Color.BLACK;
		}

		
		switch (stat_hanbetu) {
		case 0: 
			tairyoku_rank.setText(rank);
			tairyoku_rank.setTextColor(rank_color);
			break;
		case 1: 
			gizyutu_rank.setText(rank);
			gizyutu_rank.setTextColor(rank_color);
			break;
		case 2: 
			un_rank.setText(rank);
			un_rank.setTextColor(rank_color);
			break;
		}
	}
	
	protected  void onPause(){
		super.onPause();
		if (bgm_game.isPlaying()) {
			bgm_game.stop();
			}	
		bgm_game.release();
		sp.release();
	}
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        switch (event.getKeyCode()) {
	        case KeyEvent.KEYCODE_BACK:
	            // ダイアログ表示など特定の処理を行いたい場合はここに記述
	            // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返す
	            return true;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
}