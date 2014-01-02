package jp.qr.java_conf.mogpuk;

import java.util.Random;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Result extends Activity {
	
	TextView raikyaku,manzoku,mog,col,txt_goukei_mog,uti;
	TextView raikyaku2,manzoku2,mog2,col2,txt_goukei_mog2,res_ti;
	ImageView kiyo,shitu,hyouka;
	Button btn_top;
	String rank;
	int rank_color;
	Random rnd = new Random();
	private MediaPlayer oto1,bgm,oto2;
	Dialog dialog;
	String sv;
	
	//体力の結果
	int res1;
	//魅力の結果
	int res2;
	//運の結果
	int res3;
	//ゲームしたステージに対応した結果を取得（黒川=0,満願寺=3,赤馬場=6）
	int res4;
	//評価に使う（res1+res2で構成）
	int res5;
	int point_kei,w,count,game;
	
	//獲得した画像のリソースに対応した配列番号を格納
	int get_col;
	
	
	Activity act;
	String[] info;
	String[] mTitles;
	String str;
	
	//設定読み込み用
	int sta;
	int kan,ibe,cha,goukei;
	int[] count_gazou;
	int get_info[];
	int get_info2[];
	int[] time_game;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.result);
	    
	    //詳細設定の読み込み
	    SharedPreferences po22 = getSharedPreferences("tgl", MODE_PRIVATE);
        sta = po22.getInt("status3", 0);
	    
	    act=Result.this; 
	    
	    //プリファレンスからデータを受け取る
	    SharedPreferences sp_result = getSharedPreferences("result", MODE_PRIVATE);
        res1 = sp_result.getInt("raikyakusuu", 0);
        res2 = sp_result.getInt("manzoku", 0);
        res3 = sp_result.getInt("bonus", 0);
	    res4 = sp_result.getInt("stage", 0);
	    
	    
	    //結果が0だった場合にランダムで1～20を指定する
	    int saisyou_kekka1 = rnd.nextInt(20);
	    int saisyou_kekka2 = rnd.nextInt(20)+10;
	    		
	    if(res1 == 0){
	    	res1=saisyou_kekka1;
	    }
	    if(res2 == 0){
	    	res2=saisyou_kekka2;
	    }
	    res1=res1*15/10;
	    res5 = res1+res2;
	    
	    
	    
	    //ID登録
	    res_ti=(TextView)findViewById(R.id.txt_result_title);
	    raikyaku2=(TextView)findViewById(R.id.txt_result_raikyaku);
	    manzoku2=(TextView)findViewById(R.id.txt_result_manzoku);
	    mog2=(TextView)findViewById(R.id.txt_result_mog);
	    txt_goukei_mog2=(TextView)findViewById(R.id.textView1);
	    uti=(TextView)findViewById(R.id.textView2);
	    
	    raikyaku=(TextView)findViewById(R.id.txt_result_raikyaku_val);
	    manzoku=(TextView)findViewById(R.id.txt_result_manzoku_val);
	    hyouka=(ImageView)findViewById(R.id.imag_result_l);
	    mog=(TextView)findViewById(R.id.txt_result_mog_val);
	    txt_goukei_mog=(TextView)findViewById(R.id.txt_goukei_mog);
	    
	    btn_top=(Button)findViewById(R.id.btn_result_top);
	 
	    kiyo=(ImageView)findViewById(R.id.img_res_kiyo);
	    shitu=(ImageView)findViewById(R.id.img_res_shitu);
	    
	    //音声リソース読み込み
	    oto1 = MediaPlayer.create(getBaseContext(), R.raw.result_hakken);
	    bgm = MediaPlayer.create(getBaseContext(), R.raw.bgm_result);
	    oto2 = MediaPlayer.create(getBaseContext(), R.raw.dialog_cancel);
	    bgm.setLooping(true);
	    bgm.start();
	    
	    //フォントの変更
	    res_ti.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    raikyaku2.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    manzoku2.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    mog2.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    txt_goukei_mog2.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    
	    raikyaku.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    manzoku.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    mog.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    txt_goukei_mog.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    uti.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    
	    //結果の計算
	    int rand_kesu= rnd.nextInt(20)+10;

	    
	    raikyaku.setText(""+((res1*10)/5)+"人");

	    manzoku.setText(""+((res2*rand_kesu)/15)+"%");
	    
	    //評価判別
	    if(res5<50){
			rank = "D";
			rank_color=Color.GREEN;
			res5=res2;
			kiyo.setImageResource(R.drawable.dialog_anime3);
			hyouka.setImageResource(R.drawable.ld);
		}		
		else if(res5<100){
			rank="C";
			rank_color =Color.CYAN;
			res5=25+res2;
			kiyo.setImageResource(R.drawable.kiyora_stand);	
			hyouka.setImageResource(R.drawable.lc);
		}
		else if(res5<150){
			rank="B";
			rank_color =Color.BLUE;
			res5=50+res2;
			kiyo.setImageResource(R.drawable.kiyora_stand);
			hyouka.setImageResource(R.drawable.lb);
		}
		else if(res5<200){
			rank="A";
			rank_color =Color.RED;
			res5=75+res2;
			kiyo.setImageResource(R.drawable.kiyora_enjoy);
			hyouka.setImageResource(R.drawable.la);
		}
		else if(200<=res5){
			rank="S";
			rank_color =Color.MAGENTA;
			res5=120+res2;
			kiyo.setImageResource(R.drawable.kiyora_enjoy);
			hyouka.setImageResource(R.drawable.ls);
		}
	    
	    
	  //設定がされているなら獲得ポイントを倍に
	    if(sta==1){
	    	res5 = res5+50;
	    }
	  //黒川エリアの時
        if(res4==0){
        }
        else if (res4==3){
        	res5=res5+50;
        }
        else if(res4==6){
        	res5=res5+100;
        }
        
	    

	    
	    mog.setText(""+res5+" MOG");
	    

        
        //w（現在のポイント）に入っている値を呼び出す。この処理はkupon.javaと連動している
        SharedPreferences po2 = getSharedPreferences("n", MODE_PRIVATE);
        w = po2.getInt("w", 0);
        
        //countに入っている値を呼び出す
        SharedPreferences po3 = getSharedPreferences("name", MODE_PRIVATE);
        count = po3.getInt("count", 0);
        
      //gameに入っている値を呼び出して，総ゲーム回数を増やす
        SharedPreferences po0 = getSharedPreferences("time", MODE_PRIVATE);
        game = po0.getInt("game", 0);
        game++;
        Editor e0 = po0.edit();
        e0.putInt("game", game);
        e0.commit();
  
     
        	if(w == 0){
        		//w（現在のポイント）が0である場合の処理
        		w = point_kei + res5;	
        	}else{
        		//ゲームが進みw（現在のポイント）が0でなくなった場合はこの処理を行う
        	    w = w + res5;
        	}  
        
        //wに入っている現在のポイントを"w"に入れ込み保存する。この処理はkupon.javaと連動する
	    SharedPreferences po = getSharedPreferences("n", MODE_PRIVATE);
        Editor e = po.edit();
        e.putInt("w", w);
        e.commit();
        
        //現在の獲得MOGを表示する
        txt_goukei_mog.setText(""+w+" MOG");
        
        //図鑑用コレクション計算
        //黒川エリアの時
        if(res4==0){
        	shitu.setImageResource(R.drawable.col_cha2);
        	sv = "kan";
        	Random rn = new Random();
	        if(0<=res3&&res3<=30){
				get_col=rn.nextInt(4);
			}		
			else if(30<res3&&res3<=50){
				get_col=rn.nextInt(4)+3;
			}
			else if(50<res3&&res3<=80){
				get_col=rn.nextInt(4)+6;
			}
			else if(80<res3){
				get_col=rn.nextInt(2)+8;
			}
	        //獲得した画像情報の保存
	        for(int i = 0; i<10;i++){
		        if(get_col==i){
		        	SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
		        	int now_col = sp_now_zukan.getInt("now_kan_col"+i, 0);
		        	
		        	now_col = 1; 
		        	
		        	SharedPreferences sp_kousin_zukan = getSharedPreferences("collection", MODE_PRIVATE);
		        	Editor ed = sp_kousin_zukan.edit();
		        	ed.putInt("now_kan_col"+i, now_col);
		        	ed.commit();
		        }
	        }
        }
      //満願寺エリアの時
        else if(res4==3){
        	shitu.setImageResource(R.drawable.col_cha3);
        	sv = "ibe";
        	Random rn = new Random();
        	if(res3<30){
				get_col= rn.nextInt(3);
			}		
			else if(res3<60){
				get_col=rn.nextInt(6);
			}
			else if(res3<90){
				get_col=rn.nextInt(4)+5;
			}
			else if(res3>=90){
				get_col=rn.nextInt(4)+8;
			}
	        //獲得した画像情報の保存
	        for(int i = 0; i<12;i++){
		        if(get_col==i){
		        	SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
		        	int now_col = sp_now_zukan.getInt("now_ibe_col"+i, 0);
		        	
		        	now_col = 1; 
		        	
		        	SharedPreferences sp_kousin_zukan = getSharedPreferences("collection", MODE_PRIVATE);
		        	Editor ed = sp_kousin_zukan.edit();
		        	ed.putInt("now_ibe_col"+i, now_col);
		        	ed.commit();
		        }
	        }
        }
      //赤馬場エリアの時
        else if(res4==6){
        	shitu.setImageResource(R.drawable.col_cha5);
        	Random rn = new Random();
        	if(res3<30){
				get_col= rn.nextInt(4);
			}		
			else if(res3<60){
				get_col=rn.nextInt(3)+4;
			}
			else if(res3<90){
				get_col=rn.nextInt(5)+6;
			}
			else if(res3>=90){
				get_col=rn.nextInt(3)+8;
			}
	        //獲得した画像情報の保存
	        for(int i = 0; i<11;i++){
		        if(get_col==i){
		        	SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
		        	int now_col = sp_now_zukan.getInt("now_cha_col"+i, 0);
		        	
		        	now_col = 1; 
		        	
		        	SharedPreferences sp_kousin_zukan = getSharedPreferences("collection", MODE_PRIVATE);
		        	Editor ed = sp_kousin_zukan.edit();
		        	ed.putInt("now_cha_col"+i, now_col);
		        	ed.commit();
		        }
	        }
        }
        
      //TOPのボタンのenableをそれぞれ変更
        SharedPreferences sp3 = getSharedPreferences("btn", MODE_PRIVATE);
        Editor edt = sp3.edit();
        edt.putBoolean("btn_enable", false);
        edt.putBoolean("btn_game_enable", true);
        edt.commit();
	    
	    btn_top.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(v==btn_top){
					if(res4==0||res4==3){
						//サーバからデータを取得
						FileLoader task = new FileLoader(act);
						//ステージ名を基にURLを変更
						if (res4==0) {
							task.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/txt/kan.txt");
							
						}
						else if(res4==3){
							task.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/txt/ibe.txt");
							
						}
						
						task.setOnCallBack(new FileLoader.CallBackFile() {	
							
							@Override
							public void CallBack(String Result) {
								
				                str = Result;
				                info = str.split(",", -1);
						
						String[] url = new String[1];
		                url[0]="http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/image/"+sv+"/"+sv+get_col+".jpg";
		                
	
		                final Bitmap[] bmp_col = new Bitmap[url.length];
		                ImageLoader _il = new ImageLoader(act);
		                _il.execute(url);
						_il.setOnCallBack(new ImageLoader.CallBackImage(){
						 @Override
						 public void CallBack(Bitmap[] _bitmap) {
								bmp_col[0]=_bitmap[0];
								
								
								//獲得したコレクションを表示するダイアログの作成
								dialog = new Dialog(Result.this);
								//タイトルの非表示
								dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
								//フルスクリーン
								dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
								//dialog_result.xmlを基にしてダイアログを作成
								dialog.setContentView(R.layout.dialog_result);
								//背景を透明にする
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								//ダイアログ表示時のbackボタンを無効に
								dialog.setCancelable(false);
								
								dialog.show();
								oto1.start();
								
								//ダイアログに表示するテキストのID登録と表示命令
								TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
								d_result_title.setText("図鑑情報");
								TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
								dialog_titile.setText("コレクションGET!!");
								ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
								image.setImageBitmap(bmp_col[0]);
								TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
								dialog_setumei.setText("No."+(get_col+1)+" 「"+info[get_col*3]+"」\n"+"をもらったラヨ～");
								TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
								dialog_close.setText("×");
								
								//ダイアログの「×」が押された時の処理
								dialog.findViewById(R.id.d_result_close_button).setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										oto2.start();
										dialog.dismiss();
										Intent intent = new Intent(Result.this, MainActivity.class);
										if (bgm.isPlaying()) {
											bgm.stop();
											}	
										bgm.release();
										startActivity(intent);
									}
								});
						 	}
						});
							}
						});
					};
					if(res4==6){
						
						String[] chara_name= new String[]{"きよらスキー","きよら父さん","きよら母さん","マンガンじい","おしとい氏","赤ばばぁ","セン子氏","メオ","タキ","マンガンじいの孫","フタカワ人"};
						Bitmap bp;
						int crd = getResources().getIdentifier("col_cha" + (get_col), "drawable", "jp.qr.java_conf.mogpuk");
				    	bp = BitmapFactory.decodeResource(getResources(), crd);
								
								//獲得したコレクションを表示するダイアログの作成
								dialog = new Dialog(Result.this);
								//タイトルの非表示
								dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
								//フルスクリーン
								dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
								//dialog_result.xmlを基にしてダイアログを作成
								dialog.setContentView(R.layout.dialog_result);
								//背景を透明にする
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								//ダイアログ表示時のbackボタンを無効に
								dialog.setCancelable(false);
								
								dialog.show();
								oto1.start();
								
								//ダイアログに表示するテキストのID登録と表示命令
								TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
								d_result_title.setText("図鑑情報");
								TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
								dialog_titile.setText("コレクションGET");
								ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
								image.setImageBitmap(bp);
								TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
								dialog_setumei.setText("No."+(get_col+1)+" 「"+chara_name[get_col]+"」 \n"+"をもらったラヨ!!");
								TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
								dialog_close.setText("×");
								
								//ダイアログの「×」が押された時の処理
								dialog.findViewById(R.id.d_result_close_button).setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										oto2.start();
										dialog.dismiss();
										Intent intent = new Intent(Result.this, MainActivity.class);
										if (bgm.isPlaying()) {
											bgm.stop();
											}	
										bgm.release();
										startActivity(intent);
									}
						 	});
					};
				};
			}
	    });	
	   count_gazou=new int[]{1,3,5,10,15};
	   get_info=new int[9];
	   get_info2=new int[9];
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
	    //累計獲得枚数の計算
	    goukei=kan+ibe+cha;

        //獲得状況の画像を表示
        for(int i=0;i<5;i++){
        	if(goukei>=count_gazou[i]){
        		SharedPreferences pou = getSharedPreferences("collection", MODE_PRIVATE);
    	    	get_info[i] = pou.getInt("get_info"+i, 0);
        		if(get_info[i]==0){
	                Editor e3 = pou.edit();
	                e3.putInt("get_info"+i, 1);
	                e3.commit();
        		}
        	}
        }
        //図鑑をコンプリートしてるかどうか
        if(kan>=10){
        	SharedPreferences pou = getSharedPreferences("collection", MODE_PRIVATE);
	    	get_info[5] = pou.getInt("get_info"+5, 0);
    		if(get_info[5]==0){
                Editor e3 = pou.edit();
                e3.putInt("get_info"+5, 1);
                e3.commit();
    		}
        }
        if(ibe>=12){
        	SharedPreferences pou = getSharedPreferences("collection", MODE_PRIVATE);
	    	get_info[6] = pou.getInt("get_info"+6, 0);
    		if(get_info[6]==0){
                Editor e3 = pou.edit();
                e3.putInt("get_info"+6, 1);
                e3.commit();
    		}
        }
        if(cha>=11){
        	SharedPreferences pou = getSharedPreferences("collection", MODE_PRIVATE);
	    	get_info[7] = pou.getInt("get_info"+7, 0);
    		if(get_info[7]==0){
                Editor e3 = pou.edit();
                e3.putInt("get_info"+7, 1);
                e3.commit();
    		}
        }
        if(goukei>=33){
        	SharedPreferences pou = getSharedPreferences("collection", MODE_PRIVATE);
	    	get_info[8] = pou.getInt("get_info"+8, 0);
    		if(get_info[8]==0){
                Editor e3 = pou.edit();
                e3.putInt("get_info", 1);
                e3.commit();
    		}
        }
      //ゲーム回数，交換回数を読み込み
	    SharedPreferences po10= getSharedPreferences("time", MODE_PRIVATE);
        game = po10.getInt("game", 0);
        
        time_game = new int[]{1,5,10,15,20,40,60,80,100};
        
      //獲得状況の画像を表示
        for(int i=0;i<9;i++){
        	if(game>=time_game[i]){
        		SharedPreferences po11 = getSharedPreferences("collection", MODE_PRIVATE);
    	    	get_info2[i] = po11.getInt("get_info_settei"+i, 0);
        		if(get_info2[i]==0){
	                Editor e4 = po11.edit();
	                e4.putInt("get_info_settei"+i, 1);
	                e4.commit();
        		}
        	}
        }
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

