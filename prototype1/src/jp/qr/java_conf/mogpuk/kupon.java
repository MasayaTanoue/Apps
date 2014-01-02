package jp.qr.java_conf.mogpuk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class kupon extends Activity{
	Bitmap[] bmp_image_dai = new Bitmap[3];
	TextView txt_service;
	TextView txt_name;
	TextView txt_naiyou;
	TextView txt_kigen;
	TextView txt_siyou;
	TextView txt_jogen;
	ImageView imag_kupon,sukusyo_kiyora;
	TextView sukusyo2;
	TextView p;
	TextView r;
	Button sukusyo_button;
	int a = 0;
	int point,w,c;
	int kaisuu,siyou_point;
	Dialog dialog;
	Dialog dialog2;
	String filepass = null;
	
	String[] info;
	Bitmap[] mThumbIds;
	String str,str2;
	BindData data;
	String[] url;
	Activity act;
	
	//音楽
	SoundPool sp;
	int oto1;
	int oto2;
	int oto3;
	int oto4;
	int oto5;
	
	//設定読み込み用
	int sta;
	
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
	
	//PHP連動　処理
	//サーバのデータに送るデータ
	String sv_send_data;
	String sv_send_file_n;
	//サーバからもらったデータをint型にする
	int sv_get_data;
	
	
	//SDカード存在確認
	public static boolean sdcardWriteReady(){
	 //SDカードがささってるか確認
	String state = Environment.getExternalStorageState();
	 return (Environment.MEDIA_MOUNTED.equals(state));
	 }
	//画像保存処理
	//第一引数はContextを
	//第2引数には保存したいViewを
	//第3引数には画質を100までの数値で
	//第4引数はtrueならJPEG,falseならPNGで
	public static String saveBitmapPng(Context context,View view,int quality,boolean formattype) throws IOException {


	 //ディレクトリがなかったら作成 
	final String SAVE_DIR = "/もぐ～ぽん/";
	File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
	 try{
	 if(!file.exists()){
	 file.mkdir();
	 }
	 }catch(SecurityException e){
	 e.printStackTrace();
	 throw e;
	 }


	 //もってきたViewをBitmapに
	 Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
	 Canvas canvas = new Canvas(bitmap);
	 view.draw(canvas);

	 //ファイル名を作成
	 Date mDate = new Date();
	 SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
	 String fileName;
	 //trueならjpg,falseならpng
	 if(formattype){
	 fileName = fileNameDate.format(mDate) + ".jpeg";
	 }else{
	 fileName = fileNameDate.format(mDate) + ".png";
	 }
	 String AttachName = file.getAbsolutePath() + "/" + fileName;

	 //保存作業
	 try {
	 FileOutputStream out = new FileOutputStream(AttachName);
	 //trueならJPEG,falseならPNG
	 if(formattype){
	 bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
	 }else{
	 bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
	 }
	 out.flush();
	 out.close();
	 } catch(IOException e) {
	 e.printStackTrace();
	 throw e;
	 }

	 //画像を登録
	 ContentValues values = new ContentValues();
	 ContentResolver contentResolver = context.getContentResolver();
	 if(formattype){
	 values.put(Images.Media.MIME_TYPE, "image/jpeg");
	 }else{
	 values.put(Images.Media.MIME_TYPE, "image/png");
	 }
	 values.put(Images.Media.TITLE, fileName);
	 values.put("_data", AttachName);
	 contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

	 return AttachName;
	 }


	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.kupon);
	    
	  //初回起動用ダイアログの表示判断
		SharedPreferences po_syokai = getSharedPreferences("syokai", MODE_PRIVATE);
    	syokai_hanbetu = po_syokai.getInt("syokai_kupon", 0);

		    
		    if(syokai_hanbetu==0){
		    	
		    	koumoku_syokai=5;
		    	count_syokai=0;
		    	
		    	//初回用画像リソース読み込み処理
		 		syokai_res = new int[koumoku_syokai];
		 		syokai_res[0] =getResources().getIdentifier("setumei_bt_koukan","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[1] =getResources().getIdentifier("col_cha9","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[2] =getResources().getIdentifier("col_cha9","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[3] =getResources().getIdentifier("nayami","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[4] =getResources().getIdentifier("nayami","drawable", "jp.qr.java_conf.mogpuk");
		    	
		    	//表示用テキストの分割
		    	Resources res;
		    	String file_result;
		    	String result;
		    	BufferedReader br = null;
		    	InputStream is;
		    	StringBuilder sb = new StringBuilder();
		    
		    	
		    	res = this.getResources();
		    	is = res.openRawResource(R.raw.kupon);
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
		    	dialog4 = new Dialog(kupon.this);
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
				d_title.setText("交換所について");
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
                e7.putInt("syokai_kupon", 1);
                e7.commit();
		    }
	}
	
	protected  void onResume(){
		super.onResume();
		
	    //詳細設定の読み込み
	    SharedPreferences po22 = getSharedPreferences("tgl", MODE_PRIVATE);
        sta = po22.getInt("status3", 0);
	    
	    act=kupon.this;
	    
	    //IDの登録
	    txt_service=(TextView)findViewById(R.id.txt_service);
	    txt_naiyou=(TextView)findViewById(R.id.txt_naiyou_data);
	    txt_kigen=(TextView)findViewById(R.id.txt_kigen_data);
	    txt_siyou=(TextView)findViewById(R.id.txt_siyou_data);
	    txt_jogen=(TextView)findViewById(R.id.txt_jogen_data);
	    imag_kupon=(ImageView)findViewById(R.id.imag_kupon_dai);
	    sukusyo_button = (Button)findViewById(R.id.btn_kupon_koukan);
	    sukusyo_kiyora=(ImageView)findViewById(R.id.imag_kupn_sukukiyo);
	    sukusyo2=(TextView)findViewById(R.id.sukusyo2);
	    p=(TextView)findViewById(R.id.txt_point_now);
	    r=(TextView)findViewById(R.id.txt_point);
	    sp = new SoundPool( 5 ,AudioManager.STREAM_MUSIC,0);
  		oto1 = sp.load( getBaseContext() , R.raw.dialog_hyouji, 1 );
  		oto2 = sp.load( getBaseContext() , R.raw.ketteion, 1 );
  		oto3 = sp.load( getBaseContext() , R.raw.dialog_cancel, 1 );
  		oto4 = sp.load( getBaseContext() , R.raw.dialog_point_fusoku, 1 );
  		oto5 = sp.load( getBaseContext() , R.raw.kupon_get, 1 );
	    
	    //Shop.javaの何番目のリストを押したかの情報を入れ込む
	    Bundle extras = getIntent().getExtras();
        if (extras!=null) {
        	 a = extras.getInt("a");
        	 }
        
        //交換ボタンを押した後にw（現在のポイント）に保存した値を呼び出す
        //Result.javaで保存した現在のポイントの呼び出しにも使用する
        SharedPreferences po = getSharedPreferences("n", MODE_PRIVATE);
        w = po.getInt("w", 0);
        
        
        //w（現在のポイント）の値を保存する。これはResult.javaと連動する
        SharedPreferences po1 = getSharedPreferences("n", MODE_PRIVATE);
        Editor e1 = po1.edit();
        e1.putInt("w", w);
        e1.commit();
        
        //ボタンを押した回数を呼び出す
        SharedPreferences po3 = getSharedPreferences("name", MODE_PRIVATE);
        kaisuu = po3.getInt("kaisuu", 0);
        
        //使用ポイントを呼び出す
        SharedPreferences po6 = getSharedPreferences("name", MODE_PRIVATE);
        siyou_point = po6.getInt("siyou_point", 500);
        
        //設定がされているならsiyou=pointを半分に
	    SharedPreferences po2 = getSharedPreferences("tgl", MODE_PRIVATE);
        int sta = po2.getInt("status"+8, 0);
        if(sta==1){
        	siyou_point=siyou_point/2;
        }
        
        
	    FileLoader task = new FileLoader(act);
	    task.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/sample.txt");
		task.setOnCallBack(new FileLoader.CallBackFile() {
			@Override
			public void CallBack(String Result) {
                str = Result;
                info = str.split(",", -1);
                url = new String[1];

                url[0]="http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/image/kupon/kupon"+a+".jpg";
                

                mThumbIds = new Bitmap[url.length];
                ImageLoader _il = new ImageLoader(act);
                _il.execute(url);
				_il.setOnCallBack(new ImageLoader.CallBackImage(){
				 @Override
				 public void CallBack(Bitmap[] _bitmap) {
						 mThumbIds[0]=_bitmap[0];
					 
					 FileLoader2 task2 = new FileLoader2(act);
					    task2.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/kupo_lim/kupon_lim"+a+".txt");
						task2.setOnCallBack(new FileLoader2.CallBackFile() {
							@Override
							public void CallBack(String Result2) {
				               
								//発行枚数が「9999」の時「上限なし」にする
								str2 = Result2;
								String c = String.valueOf(str2.trim());
								int jogen = Integer.parseInt(c);
								
								if(jogen==9999){
									txt_jogen.setText("上限なし");
								}
								
								else{
									txt_jogen.setText(str2+"枚");
								}
					            //有効期限の計算処理
								//0=期限なし，x=xヶ月，10x=x日
					            
								 //サーバの値をintに変換
				                String b = String.valueOf(info[a*6+3].trim());
								int kigen = Integer.parseInt(b);
								
								if(kigen==0){
									 txt_kigen.setText("期限なし");
								}
					             
								else if(kigen<50&&kigen!=0){
									Calendar now = Calendar.getInstance();
					                
					                int now_y = now.get(Calendar.YEAR);
					                int now_m = now.get(Calendar.MONTH) + 1;
					                int now_d = now.get(Calendar.DATE);
	
					                //キーボードから入力された値nuを現在の日付に足す
					                now.add(Calendar.MONTH,kigen);
		
					                int y = now.get(Calendar.YEAR);
					                int m = now.get(Calendar.MONTH) + 1;
					                int d = now.get(Calendar.DATE);
					                txt_kigen.setText("利用期限： "+(now_y+"年"+now_m+"月"+now_d+"日")+"～"+(y+"年"+m+"月"+d+"日"));
								}
								else if(kigen>100){
									
									kigen=kigen-100;
									
									Calendar now = Calendar.getInstance();
					                
					                int now_y = now.get(Calendar.YEAR);
					                int now_m = now.get(Calendar.MONTH) + 1;
					                int now_d = now.get(Calendar.DATE);
	
					                //キーボードから入力された値nuを現在の日付に足す
					                now.add(Calendar.DATE,kigen);
		
					                int y = now.get(Calendar.YEAR);
					                int m = now.get(Calendar.MONTH) + 1;
					                int d = now.get(Calendar.DATE);
					                txt_kigen.setText((now_y+"年"+now_m+"月"+now_d+"日")+"～"+(y+"年"+m+"月"+d+"日"));
								}
								
				                switch(a){
				                //サーバの1行目を表示させる
				                case 0:
				                	imag_kupon.setImageBitmap(mThumbIds[0]);
				                    txt_service.setText(info[0]);
				        	    	txt_naiyou.setText(info[2]);				        	    	
				        	    	txt_siyou.setText(info[4]);
				        	    	
				        	    	p.setText(String.valueOf(w+" MOG"));
				                	break;
				                //サーバの2行目以降を表示させる
				                default:
				                	imag_kupon.setImageBitmap(mThumbIds[0]);
				                    txt_service.setText(info[6*a]);
				        	    	txt_naiyou.setText(info[6*a+2]);
				        	    	txt_siyou.setText(info[6*a+4]);
				        	    	p.setText(String.valueOf(w+" MOG"));
				        	    	break;
				                }
			                }
						});
				 	}
				});
			}
		});
		
		imag_kupon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				//ダイアログの作成
				dialog = new Dialog(kupon.this);
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
				d_title.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
				TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
				d_message.setText("店舗詳細ページへ移動しますか？");
				d_message.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));

				//ダイアログの「OK」ボタンが押された時の処理
				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
					 @Override
			            public void onClick(View v) {
						 Uri uri = Uri.parse(info[5]);
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
		});

		
		//交換ボタンを押した時の処理
		sukusyo_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(w >= siyou_point){
				//現在のポイントが使用ポイントより多いときに実行
					
					//ダイアログの作成
					dialog = new Dialog(kupon.this);
					//タイトルの非表示
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					//フルスクリーン
					dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
					//dialog_custom.xmlを基にしてダイアログを作成
					dialog.setContentView(R.layout.dialog_custom);
					//背景を透明にする
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
					sp.play( oto1 , 4.0F , 4.0F , 0 , 0 , 1.0F );
					
					//ダイアログに表示するテキストのID登録と表示命令
					TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
					d_title.setText("確認");
					TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
					d_message.setText(siyou_point+"MOG使用して\n"+"クーポン券と交換するラヨ～？");

					//ダイアログの「OK」ボタンが押された時の処理
					dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
						 @Override
				            public void onClick(View v) {
							 
							 //交換前にもう一度クーポン情報の読み込み
							 FileLoader2 task2 = new FileLoader2(act);
							    task2.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/kupo_lim/kupon_lim"+a+".txt");
								task2.setOnCallBack(new FileLoader2.CallBackFile() {
									@Override
									public void CallBack(String Result2) {
						                str2 = Result2;
						              //発行枚数が「9999」の時「上限なし」にする
										str2 = Result2;
										String c = String.valueOf(str2.trim());
										int jogen = Integer.parseInt(c);
										
										if(jogen==9999){
											txt_jogen.setText("上限なし");
										}
										
										else{
											txt_jogen.setText(str2+"枚");
										}
							 
							//クーポンの残り枚数を確認するためにInt型に置き換え
							 String b = String.valueOf(str2.trim());
							 sv_get_data = Integer.parseInt(b);
							 
							
							 
							 //枚数設定がされていない（＝9999）なら，普通通りダイアログ表示
							 if(sv_get_data==9999){
								//ボタンを消し，きよらスキーと言葉を表示
								 	TelephonyManager manager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
								    sukusyo2.setText("ID:" + manager.getDeviceId());
									sukusyo_button.setVisibility(View.INVISIBLE);
									sukusyo_button.setEnabled(false);
									sukusyo2.setVisibility(View.VISIBLE);
									sukusyo_kiyora.setVisibility(View.VISIBLE);
									
									//ボタンを押した回数によりkaisu増加
										kaisuu = kaisuu +1;

										
									//回数を保存する
									SharedPreferences po = getSharedPreferences("name", MODE_PRIVATE);
								    Editor e = po.edit();
								    e.putInt("kaisuu", kaisuu);
								    e.commit();
								    
					                //w（現在のポイント）からsiyou_pointに入った値を引いて，テキストに表示
									w = w - siyou_point;
									p.setText(String.valueOf(w+" MOG"));
									
									//使用ポイントの設定
									siyou_point = 2000;
									
									//使用ポイントを保存する
								    SharedPreferences po2 = getSharedPreferences("name", MODE_PRIVATE);
								    Editor e2 = po2.edit();
								    e2.putInt("siyou_point", siyou_point);
								    e2.commit();

				                    //w（現在のポイント）の値を保存する。これはResult.javaと連動する
									SharedPreferences po1 = getSharedPreferences("n", MODE_PRIVATE);
								    Editor e1 = po1.edit();
								    e1.putInt("w", w);
								    e1.commit();
								    
								    
								 
								dialog.dismiss();
								//スクリーンショットを撮るかどうかのダイアログ
								dialog2 = new Dialog(kupon.this);
								dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
								dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
								dialog2.setContentView(R.layout.dialog_custom);
								dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog2.setCancelable(false);
								dialog2.show();
								sp.play( oto1 , 4.0F , 4.0F , 0 , 0 , 1.0F );
								
								//ダイアログに表示するテキストのID登録と表示命令
								TextView d_title=(TextView)dialog2.findViewById(R.id.d_title);
								d_title.setText("確認");
								TextView d_message=(TextView)dialog2.findViewById(R.id.d_message);
								d_message.setText("クーポンGETラヨ!!"+"\n"+"スクリーンショットを"+"\n"+"撮影するラヨー？");
								
								//ダイアログの「OK」ボタンが押された時の処理
								dialog2.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
									 @Override
							            public void onClick(View v) {
										 
										 try {
												//全体の画像保存開始
												//第一引数はContextを
												//第2引数には保存したいViewのidだったり。画面全体を撮りたければandroid.R.id.contentを指定してもいい
												//第3引数には画質を
												//第4引数はtrueならJPEG,falseならPNGで
												filepass = kupon.saveBitmapPng(getApplicationContext(), findViewById(android.R.id.content), 100,false);
											    //↓返ってきたfilepassを使って表示したり添付して送信させたりの処理
												Toast.makeText(getApplicationContext(),"保存しました", Toast.LENGTH_SHORT).show();

												} catch (IOException q) {
												 Toast.makeText(getApplicationContext(),"保存に失敗しました", Toast.LENGTH_SHORT).show();
												 q.printStackTrace();
												}
										 
										dialog2.dismiss();
										sp.play( oto5 , 4.0F , 4.0F , 0 , 0 , 1.0F );
									 }
								});
								dialog2.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										sp.play( oto3 , 4.0F , 4.0F , 0 , 0 , 1.0F );
										dialog2.dismiss();
									}
								});
						        //dialog2.dismiss();
						        }
							 else if((sv_get_data-1)<0){
								 dialog = new Dialog(kupon.this);
						        	dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
						        	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
									dialog.setContentView(R.layout.dialog_custom);
									dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
									dialog.show();
									sp.play( oto4 , 4.0F , 4.0F , 0 , 0 , 1.0F );
									
									TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
									d_title.setText("交換失敗");
									TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
									d_message.setText("既にクーポンの発行上限になってしまっているラヨ・・・");
									
									
									dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
										 @Override
								            public void onClick(View v) {
											 sp.play( oto2 , 4.0F , 4.0F , 0 , 0 , 1.0F );
											 dialog.dismiss();
										 }
									});
									}
							 else if ((sv_get_data-1)>=0&&sv_get_data!=9999) {
								 sv_get_data=sv_get_data-1;
								 sv_send_file_n= String.valueOf(a);
								 sv_send_data = String.valueOf(sv_get_data);
								 exec_post();
							}
							
						}
									
					});
						 }
					});
					 
					//ダイアログの「×」が押された時の処理
					dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							sp.play( oto3 , 4.0F , 4.0F , 0 , 0 , 1.0F );
							dialog.dismiss();
						}
					});
					//dialog.dismiss();

				 }
				
				//現在のポイントが使用ポイント未満ならメッセージを表示し何もしない
				else{
					dialog = new Dialog(kupon.this);
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
					dialog.setContentView(R.layout.dialog_custom);
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
					sp.play( oto4 , 4.0F , 4.0F , 0 , 0 , 1.0F );
					
					TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
					d_title.setText("確認");
					TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
					d_message.setText((siyou_point-w)+" MOG足りません");
					
					
					dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
						 @Override
				            public void onClick(View v) {
							 sp.play( oto2 , 4.0F , 4.0F , 0 , 0 , 1.0F );
							 dialog.dismiss();
						 }
					});
					dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							sp.play( oto3 , 4.0F , 4.0F , 0 , 0 , 1.0F );
							dialog.dismiss();
						}
					});
				}
			}
		});
	}
	
    //音声の終了処理
	protected void onPouse(){
		super.onPause();
		sp.release();
	}
	// POST通信を実行（AsyncTaskによる非同期処理を使うバージョン）
	  private void exec_post() {

	    // 非同期タスクを定義
	    HttpPostTask task = new HttpPostTask(
	      this,
	      "http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/kupo_lim/kupon_gensan.php",

	      // タスク完了時に呼ばれるUIのハンドラ
	      new HttpPostHandler(){

	        @Override
	        public void onPostCompleted(String response) {
	          // 受信結果をUIに表示
	        	//ボタンを消し，きよらスキーと言葉を表示
			 	TelephonyManager manager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			    sukusyo2.setText("ID:" + manager.getDeviceId());
				sukusyo_button.setVisibility(View.INVISIBLE);
				sukusyo_button.setEnabled(false);
				sukusyo2.setVisibility(View.VISIBLE);
				sukusyo_kiyora.setVisibility(View.VISIBLE);
				
				//ボタンを押した回数によりkaisu増加
					kaisuu = kaisuu +1;

					
				//回数を保存する
				SharedPreferences po = getSharedPreferences("name", MODE_PRIVATE);
			    Editor e = po.edit();
			    e.putInt("kaisuu", kaisuu);
			    e.commit();
			    
                //w（現在のポイント）からsiyou_pointに入った値を引いて，テキストに表示
				w = w - siyou_point;
				p.setText(String.valueOf(w+" MOG"));
				
				//使用ポイントの設定
				siyou_point = 2000;
				
				//使用ポイントを保存する
			    SharedPreferences po2 = getSharedPreferences("name", MODE_PRIVATE);
			    Editor e2 = po2.edit();
			    e2.putInt("siyou_point", siyou_point);
			    e2.commit();

                //w（現在のポイント）の値を保存する。これはResult.javaと連動する
				SharedPreferences po1 = getSharedPreferences("n", MODE_PRIVATE);
			    Editor e1 = po1.edit();
			    e1.putInt("w", w);
			    e1.commit();
			    
			    
			 
			dialog.dismiss();
			//スクリーンショットを撮るかどうかのダイアログ
			dialog2 = new Dialog(kupon.this);
			dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
			dialog2.setContentView(R.layout.dialog_custom);
			dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog2.setCancelable(false);
			dialog2.show();
			sp.play( oto1 , 4.0F , 4.0F , 0 , 0 , 1.0F );
			
			//ダイアログに表示するテキストのID登録と表示命令
			TextView d_title=(TextView)dialog2.findViewById(R.id.d_title);
			d_title.setText("確認");
			TextView d_message=(TextView)dialog2.findViewById(R.id.d_message);
			d_message.setText(response+"\n"+"スクリーンショットを"+"\n"+"撮影するラヨー？");
			
			//ダイアログの「OK」ボタンが押された時の処理
			dialog2.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
				 @Override
		            public void onClick(View v) {
					 
					 try {
							//全体の画像保存開始
							//第一引数はContextを
							//第2引数には保存したいViewのidだったり。画面全体を撮りたければandroid.R.id.contentを指定してもいい
							//第3引数には画質を
							//第4引数はtrueならJPEG,falseならPNGで
							filepass = kupon.saveBitmapPng(getApplicationContext(), findViewById(android.R.id.content), 100,false);
						    //↓返ってきたfilepassを使って表示したり添付して送信させたりの処理
							Toast.makeText(getApplicationContext(),"保存しました", Toast.LENGTH_SHORT).show();

							} catch (IOException q) {
							 Toast.makeText(getApplicationContext(),"保存に失敗しました", Toast.LENGTH_SHORT).show();
							 q.printStackTrace();
							}
					 
					dialog2.dismiss();
					sp.play( oto5 , 4.0F , 4.0F , 0 , 0 , 1.0F );
				 }
			});
			dialog2.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					sp.play( oto3 , 4.0F , 4.0F , 0 , 0 , 1.0F );
					dialog2.dismiss();
				}
			});
	        //dialog2.dismiss();
	        }

	        @Override
	        public void onPostFailed(String response) {
	        	
	        	dialog = new Dialog(kupon.this);
	        	dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	        	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
				dialog.setContentView(R.layout.dialog_custom);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
				sp.play( oto4 , 4.0F , 4.0F , 0 , 0 , 1.0F );
				
				TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
				d_title.setText("交換失敗");
				TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
				d_message.setText(response);
				
				
				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
					 @Override
			            public void onClick(View v) {
						 sp.play( oto2 , 4.0F , 4.0F , 0 , 0 , 1.0F );
						 dialog.dismiss();
					 }
				});
	        }
	      }
	    );
	    task.addPostParam( "post_1", sv_send_file_n );
	    task.addPostParam( "post_2", sv_send_data );

	    // タスクを開始
	    task.execute();

	  }
}