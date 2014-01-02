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
	
	//���y
	SoundPool sp;
	int oto1;
	int oto2;
	int oto3;
	int oto4;
	int oto5;
	
	//�ݒ�ǂݍ��ݗp
	int sta;
	
	//����N�����̃_�C�A���O�\���p
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
	
	//PHP�A���@����
	//�T�[�o�̃f�[�^�ɑ���f�[�^
	String sv_send_data;
	String sv_send_file_n;
	//�T�[�o�����������f�[�^��int�^�ɂ���
	int sv_get_data;
	
	
	//SD�J�[�h���݊m�F
	public static boolean sdcardWriteReady(){
	 //SD�J�[�h���������Ă邩�m�F
	String state = Environment.getExternalStorageState();
	 return (Environment.MEDIA_MOUNTED.equals(state));
	 }
	//�摜�ۑ�����
	//��������Context��
	//��2�����ɂ͕ۑ�������View��
	//��3�����ɂ͉掿��100�܂ł̐��l��
	//��4������true�Ȃ�JPEG,false�Ȃ�PNG��
	public static String saveBitmapPng(Context context,View view,int quality,boolean formattype) throws IOException {


	 //�f�B���N�g�����Ȃ�������쐬 
	final String SAVE_DIR = "/�����`�ۂ�/";
	File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
	 try{
	 if(!file.exists()){
	 file.mkdir();
	 }
	 }catch(SecurityException e){
	 e.printStackTrace();
	 throw e;
	 }


	 //�����Ă���View��Bitmap��
	 Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(),Bitmap.Config.ARGB_8888);
	 Canvas canvas = new Canvas(bitmap);
	 view.draw(canvas);

	 //�t�@�C�������쐬
	 Date mDate = new Date();
	 SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
	 String fileName;
	 //true�Ȃ�jpg,false�Ȃ�png
	 if(formattype){
	 fileName = fileNameDate.format(mDate) + ".jpeg";
	 }else{
	 fileName = fileNameDate.format(mDate) + ".png";
	 }
	 String AttachName = file.getAbsolutePath() + "/" + fileName;

	 //�ۑ����
	 try {
	 FileOutputStream out = new FileOutputStream(AttachName);
	 //true�Ȃ�JPEG,false�Ȃ�PNG
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

	 //�摜��o�^
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
	    
	  //����N���p�_�C�A���O�̕\�����f
		SharedPreferences po_syokai = getSharedPreferences("syokai", MODE_PRIVATE);
    	syokai_hanbetu = po_syokai.getInt("syokai_kupon", 0);

		    
		    if(syokai_hanbetu==0){
		    	
		    	koumoku_syokai=5;
		    	count_syokai=0;
		    	
		    	//����p�摜���\�[�X�ǂݍ��ݏ���
		 		syokai_res = new int[koumoku_syokai];
		 		syokai_res[0] =getResources().getIdentifier("setumei_bt_koukan","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[1] =getResources().getIdentifier("col_cha9","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[2] =getResources().getIdentifier("col_cha9","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[3] =getResources().getIdentifier("nayami","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[4] =getResources().getIdentifier("nayami","drawable", "jp.qr.java_conf.mogpuk");
		    	
		    	//�\���p�e�L�X�g�̕���
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
					// TODO �����������ꂽ catch �u���b�N
					e.printStackTrace();
				}
				result=String.valueOf(sb);
				str_syouokai=result.split("/", 0);
				
		    	// �_�C�A���O�̍쐬�ƕ\��
		    	
		        //����N�����̂ݕ\������
		    	dialog4 = new Dialog(kupon.this);
				//�^�C�g���̔�\��
				dialog4.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				//�t���X�N���[��
				dialog4.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
				//dialog_custom.xml����ɂ��ă_�C�A���O���쐬
				dialog4.setContentView(R.layout.dialog_custom_syokai);
				//�w�i�𓧖��ɂ���
				dialog4.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				//�_�C�A���O�\������back�{�^���𖳌���
				dialog4.setCancelable(false);
				dialog4.show();
				
				//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
				TextView d_title=(TextView)dialog4.findViewById(R.id.d_title);
				d_title.setText("�������ɂ���");
				d_message=(TextView)dialog4.findViewById(R.id.d_message);
				d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
				d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
				d_message2.setText(str_syouokai[0]);
				d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
				d_syoukai_img.setImageResource(syokai_res[0]);
				d_syoukai_btn=(Button)dialog4.findViewById(R.id.d_button);

				//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
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
							 	d_syoukai_btn.setText("����");
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
		
	    //�ڍאݒ�̓ǂݍ���
	    SharedPreferences po22 = getSharedPreferences("tgl", MODE_PRIVATE);
        sta = po22.getInt("status3", 0);
	    
	    act=kupon.this;
	    
	    //ID�̓o�^
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
	    
	    //Shop.java�̉��Ԗڂ̃��X�g�����������̏�����ꍞ��
	    Bundle extras = getIntent().getExtras();
        if (extras!=null) {
        	 a = extras.getInt("a");
        	 }
        
        //�����{�^�������������w�i���݂̃|�C���g�j�ɕۑ������l���Ăяo��
        //Result.java�ŕۑ��������݂̃|�C���g�̌Ăяo���ɂ��g�p����
        SharedPreferences po = getSharedPreferences("n", MODE_PRIVATE);
        w = po.getInt("w", 0);
        
        
        //w�i���݂̃|�C���g�j�̒l��ۑ�����B�����Result.java�ƘA������
        SharedPreferences po1 = getSharedPreferences("n", MODE_PRIVATE);
        Editor e1 = po1.edit();
        e1.putInt("w", w);
        e1.commit();
        
        //�{�^�����������񐔂��Ăяo��
        SharedPreferences po3 = getSharedPreferences("name", MODE_PRIVATE);
        kaisuu = po3.getInt("kaisuu", 0);
        
        //�g�p�|�C���g���Ăяo��
        SharedPreferences po6 = getSharedPreferences("name", MODE_PRIVATE);
        siyou_point = po6.getInt("siyou_point", 500);
        
        //�ݒ肪����Ă���Ȃ�siyou=point�𔼕���
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
				               
								//���s�������u9999�v�̎��u����Ȃ��v�ɂ���
								str2 = Result2;
								String c = String.valueOf(str2.trim());
								int jogen = Integer.parseInt(c);
								
								if(jogen==9999){
									txt_jogen.setText("����Ȃ�");
								}
								
								else{
									txt_jogen.setText(str2+"��");
								}
					            //�L�������̌v�Z����
								//0=�����Ȃ��Cx=x�����C10x=x��
					            
								 //�T�[�o�̒l��int�ɕϊ�
				                String b = String.valueOf(info[a*6+3].trim());
								int kigen = Integer.parseInt(b);
								
								if(kigen==0){
									 txt_kigen.setText("�����Ȃ�");
								}
					             
								else if(kigen<50&&kigen!=0){
									Calendar now = Calendar.getInstance();
					                
					                int now_y = now.get(Calendar.YEAR);
					                int now_m = now.get(Calendar.MONTH) + 1;
					                int now_d = now.get(Calendar.DATE);
	
					                //�L�[�{�[�h������͂��ꂽ�lnu�����݂̓��t�ɑ���
					                now.add(Calendar.MONTH,kigen);
		
					                int y = now.get(Calendar.YEAR);
					                int m = now.get(Calendar.MONTH) + 1;
					                int d = now.get(Calendar.DATE);
					                txt_kigen.setText("���p�����F "+(now_y+"�N"+now_m+"��"+now_d+"��")+"�`"+(y+"�N"+m+"��"+d+"��"));
								}
								else if(kigen>100){
									
									kigen=kigen-100;
									
									Calendar now = Calendar.getInstance();
					                
					                int now_y = now.get(Calendar.YEAR);
					                int now_m = now.get(Calendar.MONTH) + 1;
					                int now_d = now.get(Calendar.DATE);
	
					                //�L�[�{�[�h������͂��ꂽ�lnu�����݂̓��t�ɑ���
					                now.add(Calendar.DATE,kigen);
		
					                int y = now.get(Calendar.YEAR);
					                int m = now.get(Calendar.MONTH) + 1;
					                int d = now.get(Calendar.DATE);
					                txt_kigen.setText((now_y+"�N"+now_m+"��"+now_d+"��")+"�`"+(y+"�N"+m+"��"+d+"��"));
								}
								
				                switch(a){
				                //�T�[�o��1�s�ڂ�\��������
				                case 0:
				                	imag_kupon.setImageBitmap(mThumbIds[0]);
				                    txt_service.setText(info[0]);
				        	    	txt_naiyou.setText(info[2]);				        	    	
				        	    	txt_siyou.setText(info[4]);
				        	    	
				        	    	p.setText(String.valueOf(w+" MOG"));
				                	break;
				                //�T�[�o��2�s�ڈȍ~��\��������
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
				// TODO �����������ꂽ���\�b�h�E�X�^�u
				//�_�C�A���O�̍쐬
				dialog = new Dialog(kupon.this);
				//�^�C�g���̔�\��
				dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
				//�t���X�N���[��
				dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
				//dialog_custom.xml����ɂ��ă_�C�A���O���쐬
				dialog.setContentView(R.layout.dialog_custom);
				//�w�i�𓧖��ɂ���
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog.show();
				
				//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
				TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
				d_title.setText("�m�F");
				d_title.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
				TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
				d_message.setText("�X�܏ڍ׃y�[�W�ֈړ����܂����H");
				d_message.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));

				//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
					 @Override
			            public void onClick(View v) {
						 Uri uri = Uri.parse(info[5]);
							Intent hp = new Intent(Intent.ACTION_VIEW,uri);
							 startActivity(hp);
							 dialog.dismiss();
					}
				});
				 
				//�_�C�A���O�́u�~�v�������ꂽ���̏���
				dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
			}
		});

		
		//�����{�^�������������̏���
		sukusyo_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(w >= siyou_point){
				//���݂̃|�C���g���g�p�|�C���g��葽���Ƃ��Ɏ��s
					
					//�_�C�A���O�̍쐬
					dialog = new Dialog(kupon.this);
					//�^�C�g���̔�\��
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					//�t���X�N���[��
					dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
					//dialog_custom.xml����ɂ��ă_�C�A���O���쐬
					dialog.setContentView(R.layout.dialog_custom);
					//�w�i�𓧖��ɂ���
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
					sp.play( oto1 , 4.0F , 4.0F , 0 , 0 , 1.0F );
					
					//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
					TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
					d_title.setText("�m�F");
					TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
					d_message.setText(siyou_point+"MOG�g�p����\n"+"�N�[�|�����ƌ������郉���`�H");

					//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
					dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
						 @Override
				            public void onClick(View v) {
							 
							 //�����O�ɂ�����x�N�[�|�����̓ǂݍ���
							 FileLoader2 task2 = new FileLoader2(act);
							    task2.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/kupo_lim/kupon_lim"+a+".txt");
								task2.setOnCallBack(new FileLoader2.CallBackFile() {
									@Override
									public void CallBack(String Result2) {
						                str2 = Result2;
						              //���s�������u9999�v�̎��u����Ȃ��v�ɂ���
										str2 = Result2;
										String c = String.valueOf(str2.trim());
										int jogen = Integer.parseInt(c);
										
										if(jogen==9999){
											txt_jogen.setText("����Ȃ�");
										}
										
										else{
											txt_jogen.setText(str2+"��");
										}
							 
							//�N�[�|���̎c�薇�����m�F���邽�߂�Int�^�ɒu������
							 String b = String.valueOf(str2.trim());
							 sv_get_data = Integer.parseInt(b);
							 
							
							 
							 //�����ݒ肪����Ă��Ȃ��i��9999�j�Ȃ�C���ʒʂ�_�C�A���O�\��
							 if(sv_get_data==9999){
								//�{�^���������C�����X�L�[�ƌ��t��\��
								 	TelephonyManager manager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
								    sukusyo2.setText("ID:" + manager.getDeviceId());
									sukusyo_button.setVisibility(View.INVISIBLE);
									sukusyo_button.setEnabled(false);
									sukusyo2.setVisibility(View.VISIBLE);
									sukusyo_kiyora.setVisibility(View.VISIBLE);
									
									//�{�^�����������񐔂ɂ��kaisu����
										kaisuu = kaisuu +1;

										
									//�񐔂�ۑ�����
									SharedPreferences po = getSharedPreferences("name", MODE_PRIVATE);
								    Editor e = po.edit();
								    e.putInt("kaisuu", kaisuu);
								    e.commit();
								    
					                //w�i���݂̃|�C���g�j����siyou_point�ɓ������l�������āC�e�L�X�g�ɕ\��
									w = w - siyou_point;
									p.setText(String.valueOf(w+" MOG"));
									
									//�g�p�|�C���g�̐ݒ�
									siyou_point = 2000;
									
									//�g�p�|�C���g��ۑ�����
								    SharedPreferences po2 = getSharedPreferences("name", MODE_PRIVATE);
								    Editor e2 = po2.edit();
								    e2.putInt("siyou_point", siyou_point);
								    e2.commit();

				                    //w�i���݂̃|�C���g�j�̒l��ۑ�����B�����Result.java�ƘA������
									SharedPreferences po1 = getSharedPreferences("n", MODE_PRIVATE);
								    Editor e1 = po1.edit();
								    e1.putInt("w", w);
								    e1.commit();
								    
								    
								 
								dialog.dismiss();
								//�X�N���[���V���b�g���B�邩�ǂ����̃_�C�A���O
								dialog2 = new Dialog(kupon.this);
								dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
								dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
								dialog2.setContentView(R.layout.dialog_custom);
								dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								dialog2.setCancelable(false);
								dialog2.show();
								sp.play( oto1 , 4.0F , 4.0F , 0 , 0 , 1.0F );
								
								//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
								TextView d_title=(TextView)dialog2.findViewById(R.id.d_title);
								d_title.setText("�m�F");
								TextView d_message=(TextView)dialog2.findViewById(R.id.d_message);
								d_message.setText("�N�[�|��GET����!!"+"\n"+"�X�N���[���V���b�g��"+"\n"+"�B�e���郉���[�H");
								
								//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
								dialog2.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
									 @Override
							            public void onClick(View v) {
										 
										 try {
												//�S�̂̉摜�ۑ��J�n
												//��������Context��
												//��2�����ɂ͕ۑ�������View��id��������B��ʑS�̂��B�肽�����android.R.id.content���w�肵�Ă�����
												//��3�����ɂ͉掿��
												//��4������true�Ȃ�JPEG,false�Ȃ�PNG��
												filepass = kupon.saveBitmapPng(getApplicationContext(), findViewById(android.R.id.content), 100,false);
											    //���Ԃ��Ă���filepass���g���ĕ\��������Y�t���đ��M��������̏���
												Toast.makeText(getApplicationContext(),"�ۑ����܂���", Toast.LENGTH_SHORT).show();

												} catch (IOException q) {
												 Toast.makeText(getApplicationContext(),"�ۑ��Ɏ��s���܂���", Toast.LENGTH_SHORT).show();
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
									d_title.setText("�������s");
									TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
									d_message.setText("���ɃN�[�|���̔��s����ɂȂ��Ă��܂��Ă��郉���E�E�E");
									
									
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
					 
					//�_�C�A���O�́u�~�v�������ꂽ���̏���
					dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							sp.play( oto3 , 4.0F , 4.0F , 0 , 0 , 1.0F );
							dialog.dismiss();
						}
					});
					//dialog.dismiss();

				 }
				
				//���݂̃|�C���g���g�p�|�C���g�����Ȃ烁�b�Z�[�W��\�����������Ȃ�
				else{
					dialog = new Dialog(kupon.this);
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
					dialog.setContentView(R.layout.dialog_custom);
					dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
					dialog.show();
					sp.play( oto4 , 4.0F , 4.0F , 0 , 0 , 1.0F );
					
					TextView d_title=(TextView)dialog.findViewById(R.id.d_title);
					d_title.setText("�m�F");
					TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
					d_message.setText((siyou_point-w)+" MOG����܂���");
					
					
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
	
    //�����̏I������
	protected void onPouse(){
		super.onPause();
		sp.release();
	}
	// POST�ʐM�����s�iAsyncTask�ɂ��񓯊��������g���o�[�W�����j
	  private void exec_post() {

	    // �񓯊��^�X�N���`
	    HttpPostTask task = new HttpPostTask(
	      this,
	      "http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/kupo_lim/kupon_gensan.php",

	      // �^�X�N�������ɌĂ΂��UI�̃n���h��
	      new HttpPostHandler(){

	        @Override
	        public void onPostCompleted(String response) {
	          // ��M���ʂ�UI�ɕ\��
	        	//�{�^���������C�����X�L�[�ƌ��t��\��
			 	TelephonyManager manager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			    sukusyo2.setText("ID:" + manager.getDeviceId());
				sukusyo_button.setVisibility(View.INVISIBLE);
				sukusyo_button.setEnabled(false);
				sukusyo2.setVisibility(View.VISIBLE);
				sukusyo_kiyora.setVisibility(View.VISIBLE);
				
				//�{�^�����������񐔂ɂ��kaisu����
					kaisuu = kaisuu +1;

					
				//�񐔂�ۑ�����
				SharedPreferences po = getSharedPreferences("name", MODE_PRIVATE);
			    Editor e = po.edit();
			    e.putInt("kaisuu", kaisuu);
			    e.commit();
			    
                //w�i���݂̃|�C���g�j����siyou_point�ɓ������l�������āC�e�L�X�g�ɕ\��
				w = w - siyou_point;
				p.setText(String.valueOf(w+" MOG"));
				
				//�g�p�|�C���g�̐ݒ�
				siyou_point = 2000;
				
				//�g�p�|�C���g��ۑ�����
			    SharedPreferences po2 = getSharedPreferences("name", MODE_PRIVATE);
			    Editor e2 = po2.edit();
			    e2.putInt("siyou_point", siyou_point);
			    e2.commit();

                //w�i���݂̃|�C���g�j�̒l��ۑ�����B�����Result.java�ƘA������
				SharedPreferences po1 = getSharedPreferences("n", MODE_PRIVATE);
			    Editor e1 = po1.edit();
			    e1.putInt("w", w);
			    e1.commit();
			    
			    
			 
			dialog.dismiss();
			//�X�N���[���V���b�g���B�邩�ǂ����̃_�C�A���O
			dialog2 = new Dialog(kupon.this);
			dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
			dialog2.setContentView(R.layout.dialog_custom);
			dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog2.setCancelable(false);
			dialog2.show();
			sp.play( oto1 , 4.0F , 4.0F , 0 , 0 , 1.0F );
			
			//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
			TextView d_title=(TextView)dialog2.findViewById(R.id.d_title);
			d_title.setText("�m�F");
			TextView d_message=(TextView)dialog2.findViewById(R.id.d_message);
			d_message.setText(response+"\n"+"�X�N���[���V���b�g��"+"\n"+"�B�e���郉���[�H");
			
			//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
			dialog2.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
				 @Override
		            public void onClick(View v) {
					 
					 try {
							//�S�̂̉摜�ۑ��J�n
							//��������Context��
							//��2�����ɂ͕ۑ�������View��id��������B��ʑS�̂��B�肽�����android.R.id.content���w�肵�Ă�����
							//��3�����ɂ͉掿��
							//��4������true�Ȃ�JPEG,false�Ȃ�PNG��
							filepass = kupon.saveBitmapPng(getApplicationContext(), findViewById(android.R.id.content), 100,false);
						    //���Ԃ��Ă���filepass���g���ĕ\��������Y�t���đ��M��������̏���
							Toast.makeText(getApplicationContext(),"�ۑ����܂���", Toast.LENGTH_SHORT).show();

							} catch (IOException q) {
							 Toast.makeText(getApplicationContext(),"�ۑ��Ɏ��s���܂���", Toast.LENGTH_SHORT).show();
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
				d_title.setText("�������s");
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

	    // �^�X�N���J�n
	    task.execute();

	  }
}