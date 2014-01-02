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
	
	//�{�^���̐錾
	private Button btn_game;
	private Button btn_shop;
	private Button btn_zukan;
	private Button btn_settei;
	private Button btn_rink;
	private Button btn_result;
	private ImageButton btn_change;
	
	//�A�j���[�V������ImageView
	private TextView TOP;
	String[] word = {"�������撣�郉��!!","����`���ɏo������!!","���܂ɂ͈�x�݂��郉���["};
	private Random random;
	private RelativeLayout layout;
	AnimationDrawable anima;
	
	//���y
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
	
	//�w�i�摜�ύX�p
	int kan,ibe,cha;
	int now_back1;
	int now_back2,now_back0;
	
	
	
	//�}�ӂ��������̂�m�点�鏈���p
	int get_info[];
	int get_info_settei[];
	
	//���݂̊l��MOG���i�[����ϐ�
	int w;
	
	//����N�����̃_�C�A���O�\���p
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
	String[] d_title_syousai={"�ustart�v�{�^��","�u�o�c���ʁv�{�^��","�u�������v�{�^��","�u�}�Ӂv�{�^��","�u�ݒ�v�{�^��","�uHP�����N�v�{�^��"};
	int[] syokai2_res;
	int[] syokai1_res;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		random = new Random();
				
		//�{�^����ID�o�^	
		btn_game =(Button)findViewById(R.id.btn_game);
		btn_game.setOnClickListener(this);
		//���ݕ��u���Ȃ�(enable=false)�ɂȂ�
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
		
		
		//�e�摜�l������ǂݍ���
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
	    
	    //���ݎ����Ŕw�i��ς��鏈��
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
				word[0] = "Zzz���";
				word[1] = "Zzz���";
				word[2] = "Zzz���";
	    		
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
		
		
		
		//TOP�摜�̕ύX�i���u�����Ŕ��u���d�����v�ɂȂǁj
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
					//�����X�L�[�����Ȃ��̂ŁCbtn_change.enale=false��
					btn_change.setEnabled(false);
					
					//�I�������̕\��
					SharedPreferences po1 = getSharedPreferences("time", MODE_PRIVATE);
					//���ݎ����̎擾
					long now_time = Calendar.getInstance().getTimeInMillis();
			        long time = po1.getLong("end_time",0);
			        long lim_time =time-now_time;
			        SimpleDateFormat sdf = new SimpleDateFormat("m��");
			        TOP.setText("����`���I�����Ԃ܂�\n"+"�c��"+sdf.format(lim_time)+"����!!");
			        if(lim_time<60000){
			        	SimpleDateFormat sdff = new SimpleDateFormat("s�b");
			        	TOP.setText("����`���I�����Ԃ܂�\n"+"�c��"+sdff.format(lim_time)+"����!!");
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
		
		
		//����N���p�_�C�A���O�̕\�����f
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
		    	
		    	//�\���p�e�L�X�g�̕���
		    	
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
					// TODO �����������ꂽ catch �u���b�N
					e.printStackTrace();
				}
				result=String.valueOf(sb);
				str_syouokai=result.split("/", 0);
		    	
		    	// �_�C�A���O�̍쐬�ƕ\��
		    	
		        //����N�����̂ݕ\������
		    	dialog4 = new Dialog(MainActivity.this);
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
				d_title=(TextView)dialog4.findViewById(R.id.d_title);
				d_title.setText("�����`�ۂ�Ƃ́H�H");
				d_message=(TextView)dialog4.findViewById(R.id.d_message);
				d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
				d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
				d_message2.setText(str_syouokai[0]);
				d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
				d_syoukai_img.setImageResource(syokai1_res[0]);
				d_syoukai_btn=(Button)dialog4.findViewById(R.id.d_button);

				//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
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
							 	d_syoukai_btn.setText("����");
						 	}
						 	if(count_syokai==koumoku_syokai){
						 		dialog4.dismiss();
						 		
						 		//����p�摜���\�[�X�ǂݍ��ݏ���
						 		syokai2_res = new int[6];
						 		syokai2_res[0] =getResources().getIdentifier("start","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[1] =getResources().getIdentifier("keieikekka_ura","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[2] =getResources().getIdentifier("icon_koukanjo","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[3] =getResources().getIdentifier("icon_zukan","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[4] =getResources().getIdentifier("icon_settei","drawable", "jp.qr.java_conf.mogpuk");
						 		syokai2_res[5] =getResources().getIdentifier("icon_hplink","drawable", "jp.qr.java_conf.mogpuk");
						 		//����N���p�_�C�A���O�̕\�����f
								SharedPreferences po_syokai = getSharedPreferences("syokai", MODE_PRIVATE);
						    	syokai_hanbetu = po_syokai.getInt("syokai_main_gamen", 0);

								    
								    if(syokai_hanbetu==0){
								    	
								    	koumoku_syokai=6;
								    	count_syokai=0;

								    	//�\���p�e�L�X�g�̕���
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
											// TODO �����������ꂽ catch �u���b�N
											e.printStackTrace();
										}
										result=String.valueOf(sb);
										str_syouokai2=result.split(",", 0);
								    	// �_�C�A���O�̍쐬�ƕ\��
								    	
								        //����N�����̂ݕ\������
								    	dialog4 = new Dialog(MainActivity.this);
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
										d_title=(TextView)dialog4.findViewById(R.id.d_title);
										d_title.setText(d_title_syousai[0]);
										d_message=(TextView)dialog4.findViewById(R.id.d_message);
										d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
										d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
										d_message2.setText(str_syouokai2[0]);
										d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
										d_syoukai_img.setImageResource(syokai2_res[0]);
										d_syoukai_btn=(Button)dialog4.findViewById(R.id.d_button);

										//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
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
													 	d_syoukai_btn.setText("����");
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
		

		
		
		//���̍Đ�
		oto = MediaPlayer.create(getBaseContext(), R.raw.ketteion);
		sp = new SoundPool( 4 ,AudioManager.STREAM_MUSIC,0);
  		sp_atari = sp.load( getBaseContext() , R.raw.se_atari, 1 );
		//BGM�̍Đ�
		bgm_top = MediaPlayer.create(getBaseContext(), R.raw.bgm_top);
		bgm_top.setLooping(true);
		bgm_top.start();
		
		//���u�̊Ď�
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
		
		//�A���P�[�g�_�C�A���O�̕\��
		
		//�Q�[���񐔁C�����񐔂�ǂݍ���
	    SharedPreferences po0 = getSharedPreferences("time", MODE_PRIVATE);
        int game = po0.getInt("game", 0);
        SharedPreferences po2 = getSharedPreferences("name", MODE_PRIVATE);
        int kaisuu = po2.getInt("kaisuu", 0);
        
        SharedPreferences po5 = getSharedPreferences("syokai", MODE_PRIVATE);
        int syokai_anke = po5.getInt("syokai_anke", 0);
        //���Q�[����3��ȏ�C�N�[�|������1��ȏ�ŏo��
        if(syokai_anke==0){
	        if(game>=3&&kaisuu>0){
	        	
	        	dialog = new Dialog(MainActivity.this);
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
				d_title.setText("���肢");
				TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
				d_message.setText("�����`�ۂ�������p�������肪�Ƃ��������܂��B\n"+"MOGPUK�̍X�Ȃ锭�W�̂��߂ɁC�A���P�[�g�ւ̂����͂����肢�������܂��B\n"+
				"���������������������ɂ̓N�[�|��1����������MOG�i500MOG�j�v���[���g");

				//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
					 @Override
			            public void onClick(View v) {
						 
						//w�i���݂̃|�C���g�j�ɓ����Ă���l���Ăяo���B���̏�����kupon.java,Result.java�ƘA�����Ă���
			    	        SharedPreferences po10 = getSharedPreferences("n", MODE_PRIVATE);
			    	        w = po10.getInt("w", 0);
			    	        
			    	        //w�ɐ}�Ӄ{�[�i�X���l�������|�C���g�𑫂�����
			    	        w = w + 500;
			    			
			    	        //w�ɓ����Ă��錻�݂̃|�C���g��"w"�ɓ��ꍞ�ݕۑ�����B���̏�����kupon.javaResult.java�ƘA������
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
				 
				//�_�C�A���O�́u�~�v�������ꂽ���̏���
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
		
		//�{�[�i�X�l���ʒm
		get_info=new int[9];
		for(int i=0;i<9;i++){
			SharedPreferences pou = getSharedPreferences("collection", MODE_PRIVATE);
	    	get_info[i] = pou.getInt("get_info"+i, 0);
	    	if(get_info[i]==1){

	    		//�|�C���g�����鏈��
	    		if(i<5){
	    			int[] point={100,500,750,1000,1500};
	    			//w�i���݂̃|�C���g�j�ɓ����Ă���l���Ăяo���B���̏�����kupon.java,Result.java�ƘA�����Ă���
	    	        SharedPreferences po10 = getSharedPreferences("n", MODE_PRIVATE);
	    	        w = po10.getInt("w", 0);
	    	        
	    	        //w�ɐ}�Ӄ{�[�i�X���l�������|�C���g�𑫂�����
	    	        w = w + point[i];
	    			
	    	        //w�ɓ����Ă��錻�݂̃|�C���g��"w"�ɓ��ꍞ�ݕۑ�����B���̏�����kupon.javaResult.java�ƘA������
	    		    SharedPreferences po = getSharedPreferences("n", MODE_PRIVATE);
	    	        Editor e = po.edit();
	    	        e.putInt("w", w);
	    	        e.commit();
	    		}
	    		String[] chara_name= {"�}�Ӂ~1","�}�Ӂ~3","�}�Ӂ~5","�}�Ӂ~10","�}�Ӂ~15","�ό��n�}�ӃR���v���[�g","�C�x���g�}�ӃR���v���[�g","�L�����}�ӃR���v���[�g","�S�}�ӃR���v���[�g"};
	    		String[] chara_syousai= {"100MOG","500MOG","750MOG","1000MOG","1500MOG","�w�i�@��","�w�i�@�[","�w�i�@��","�����X�L�[�����"};
	    		
	    		sp.play( sp_atari , 1.0F , 1.0F , 0 , 0 , 1.0F );
	    		
	    		dialog = new Dialog(MainActivity.this);
	       		//�^�C�g���̔�\��
	       		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	       		//�t���X�N���[��
	       		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
	       		//dialog_result.xml����ɂ��ă_�C�A���O���쐬
	       		dialog.setContentView(R.layout.dialog_result);
	       		//�w�i�𓧖��ɂ���
	       		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
	       		dialog.show();
	       		
	       		//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
	       		TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
	       		d_result_title.setText("�}�Ӄ{�[�i�X");
	       		TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
	       		dialog_titile.setText("�u"+chara_name[i]+"�v\n"+"�B��!!");
	       		ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
	       		image.setImageResource(R.drawable.min_egaokiyora);
	       		TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
	       		dialog_setumei.setText("�u"+chara_syousai[i]+"�v ��\n"+"�l����������!!");
	       		TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
	       		dialog_close.setText("�~");
	       		
	       		//�_�C�A���O�́u�~�v�������ꂽ���̏���
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
		    		
		    		
		    		String[]chara_name={"1��","5��","10��","15��","20��","40��","60��","80��","100��"};
		    		String[]chara_syousai={"�����Ƃ����̋S�̂����","�Z���q���̗�V��@","�^�L�̋C����","�Ԃ΂΂��̒m�b","���̂���`��","����畃����̗�","���I�̎ʐ^","�}���K�������̂����","�����ꂳ��̔������p"};
		    		sp.play( sp_atari , 1.0F , 1.0F , 0 , 0 , 1.0F );
		    		dialog2 = new Dialog(MainActivity.this);
		       		//�^�C�g���̔�\��
		       		dialog2.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		       		//�t���X�N���[��
		       		dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		       		//dialog_result.xml����ɂ��ă_�C�A���O���쐬
		       		dialog2.setContentView(R.layout.dialog_result2);
		       		//�w�i�𓧖��ɂ���
		       		dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		       		dialog2.show();
		       		
		       		//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
		       		TextView d_result_title=(TextView)dialog2.findViewById(R.id.d_result_title2);
		       		d_result_title.setText("����`���񐔃{�[�i�X");
		       		TextView dialog_titile=(TextView)dialog2.findViewById(R.id.txt_result_dialog_titile2);
		       		dialog_titile.setText("����`���񐔂�\n"+"�u"+chara_name[i]+"�v�ɓ��B!!");
		       		ImageView image = (ImageView)dialog2.findViewById(R.id.imag_getcol2);
		       		image.setImageResource(R.drawable.col_cha9);
		       		TextView dialog_setumei=(TextView)dialog2.findViewById(R.id.txt_result_dialog_setumei2);
		       		dialog_setumei.setText("�u"+chara_syousai[i]+"�v��\n"+"�ݒ�ł���悤�ɂȂ�܂���!!");
		       		
		       		TextView dialog_close=(TextView)dialog2.findViewById(R.id.d_result_close_button2);
		       		dialog_close.setText("�~");
		       		
		       		//�_�C�A���O�́u�~�v�������ꂽ���̏���
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
			//�_�C�A���O�̍쐬
			dialog = new Dialog(MainActivity.this);
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
			TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
			d_message.setText("HP�ֈړ����郉���`�H");

			//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
			dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
				 @Override
		            public void onClick(View v) {
					 Uri uri = Uri.parse("http://www.pu-kumamoto.ac.jp/~iimulab/mogpuk_mobile/");
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
		
		else if(v==btn_result){
			//�f�[�^�󂯓n��
			Intent it_res_gamenhe = new Intent(MainActivity.this, Come_back.class);
			startActivity(it_res_gamenhe);
		}
		else if(v==btn_change){
			//�A�j���[�V�������^�b�`���Ă������Ȃ�����i���b�N����j
			btn_change.setEnabled(false);
			layout.setBackgroundResource(now_back2);
			Handler hdl = new Handler();
	 		hdl.postDelayed(new splashHandler(), 1000);
			//�����_���Ńe�L�X�g�r���[��\��
			TOP.setText(word[random.nextInt(word.length)]);
			TOP.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
			TOP.setVisibility(View.VISIBLE);
		}
	} 

			
	class splashHandler implements Runnable {
		 	public void run() {
		 			TOP.setVisibility(View.INVISIBLE);
		 			layout.setBackgroundResource(now_back1);
		 			//���b�N������
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
	            // �_�C�A���O�\���ȂǓ���̏������s�������ꍇ�͂����ɋL�q
	            // �e�N���X��dispatchKeyEvent()���Ăяo������true��Ԃ�
	        	
	        	//�_�C�A���O�̍쐬
				dialog = new Dialog(MainActivity.this);
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
				TextView d_message=(TextView)dialog.findViewById(R.id.d_message);
				d_message.setText("�I�����܂����H");

				//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
					 @Override
			            public void onClick(View v) {
						 dialog.dismiss();
						 moveTaskToBack (true);
					}
				});
				 
				//�_�C�A���O�́u�~�v�������ꂽ���̏���
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
