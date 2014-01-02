package jp.qr.java_conf.mogpuk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Help extends Activity implements OnClickListener {
	
	Button btn0,btn1,btn2,btn3,btn4;
	RelativeLayout rl;
	
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
		int[] syokai_res;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.halp);
	    
	    rl=(RelativeLayout)findViewById(R.id.layout_halp_bg);
	    rl.setBackgroundResource(R.drawable.bg_repeat_kuro);
	    
	    btn0=(Button)findViewById(R.id.btn_help_mog);
	    btn0.setOnClickListener(this);
	    btn1=(Button)findViewById(R.id.btn_help_game);
	    btn1.setOnClickListener(this);
	    btn2=(Button)findViewById(R.id.btn_help_kupo);
	    btn2.setOnClickListener(this);
	    btn3=(Button)findViewById(R.id.btn_help_zukan);
	    btn3.setOnClickListener(this);
	    btn4=(Button)findViewById(R.id.btn_help_settei);
	    btn4.setOnClickListener(this);
	    
	    res = this.getResources();
	    
	  //�㕔�\���L�����̕ύX
	    TextView txt_cha_vo=(TextView)findViewById(R.id.textView1);
	    txt_cha_vo.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	
	    // TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if(v==btn0){
			
			syokai1_res = new int[6];
	 		syokai1_res[0] =getResources().getIdentifier("kiyora_syoukai","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai1_res[1] =getResources().getIdentifier("keieikekka_ura","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai1_res[2] =getResources().getIdentifier("kiyora_enjoy","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai1_res[3] =getResources().getIdentifier("icon_zukan","drawable", "jp.qr.java_conf.mogpuk");
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
	    	dialog4 = new Dialog(Help.this);
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
							    	dialog4 = new Dialog(Help.this);
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
							    }
					 	}
				 	}
			    });
		}
		else if(v==btn1){
			
			 count_syokai=0;
			 koumoku_syokai=4;
			//����p�摜���\�[�X�ǂݍ��ݏ���
		 		syokai_res = new int[koumoku_syokai];
		 		syokai_res[0] =getResources().getIdentifier("col_cha8","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[1] =getResources().getIdentifier("crd2","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[2] =getResources().getIdentifier("keieikekka1","drawable", "jp.qr.java_conf.mogpuk");
		 		syokai_res[3] =getResources().getIdentifier("crd9","drawable", "jp.qr.java_conf.mogpuk");
			//�\���p�e�L�X�g�̕���
		    	String file_result;
		    	String result;
		    	BufferedReader br = null;
		    	InputStream is;
		    	StringBuilder sb = new StringBuilder();
		    	
		    	
		    	is = res.openRawResource(R.raw.txt_game1);
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
			 
		    	dialog4 = new Dialog(Help.this);
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
				d_title.setText("�V�ѕ�");
				d_message=(TextView)dialog4.findViewById(R.id.d_message);
				d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
				d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
				d_message2.setText(str_syouokai[0]);
				d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
				d_syoukai_img.setImageResource(R.anim.anim_crd_hazure);
				AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
				ani.start();
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
						 	if(count_syokai==1){
						 		d_syoukai_img.setImageResource(R.anim.anim_crd_cha);
								AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
								ani.start();
						 	}
						 	if(count_syokai==2){
						 		d_syoukai_img.setImageResource(R.anim.anim_crd_setumei);
								AnimationDrawable ani = (AnimationDrawable)d_syoukai_img.getDrawable();
								ani.start();
						 	}
						 	if(count_syokai==koumoku_syokai){
						 		dialog4.dismiss();
						 	}
					 	}
				    });
		}
		else if (v==btn2) {
			
			koumoku_syokai=4;
	    	count_syokai=0;
			//����p�摜���\�[�X�ǂݍ��ݏ���
	 		syokai_res = new int[koumoku_syokai];
	 		syokai_res[0] =getResources().getIdentifier("setumei_bt_koukan","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai_res[1] =getResources().getIdentifier("col_cha9","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai_res[2] =getResources().getIdentifier("nayami","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai_res[3] =getResources().getIdentifier("nayami","drawable", "jp.qr.java_conf.mogpuk");
	    	
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
	    	dialog4 = new Dialog(Help.this);
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
			
		}
		else if(v==btn3){
			koumoku_syokai=2;
	    	count_syokai=0;
	    	
	    	
	    	//�\���p�e�L�X�g�̕���
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
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
			result=String.valueOf(sb);
			str_syouokai=result.split("/", 0);
			
	    	// �_�C�A���O�̍쐬�ƕ\��
	    	
	        //����N�����̂ݕ\������
	    	dialog4 = new Dialog(Help.this);
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
			d_title.setText("�}�ӂɂ���");
			d_message=(TextView)dialog4.findViewById(R.id.d_message);
			d_message.setText(""+(count_syokai+1)+"/"+koumoku_syokai);
			d_message2=(TextView)dialog4.findViewById(R.id.d_message2);
			d_message2.setText(str_syouokai[0]);
			d_syoukai_img=(ImageView)dialog4.findViewById(R.id.imag_syokai);
			d_syoukai_img.setImageResource(R.drawable.col_cha9);
			d_syoukai_btn=(Button)dialog4.findViewById(R.id.d_button);

			//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
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
						 	d_syoukai_btn.setText("����");
					 	}
					 	if(count_syokai==koumoku_syokai){
					 		dialog4.dismiss();
					 	}
				 	}
			    });
		}
		else if(v==btn4) {
			koumoku_syokai=3;
	    	count_syokai=0;
	    	
	    	syokai_res = new int[koumoku_syokai];
	 		syokai_res[0] =getResources().getIdentifier("setumei_bt_asobi","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai_res[1] =getResources().getIdentifier("setumei_bt_keiei","drawable", "jp.qr.java_conf.mogpuk");
	 		syokai_res[2] =getResources().getIdentifier("bt","drawable", "jp.qr.java_conf.mogpuk");
	    	
	    	//�\���p�e�L�X�g�̕���
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
				// TODO �����������ꂽ catch �u���b�N
				e.printStackTrace();
			}
			result=String.valueOf(sb);
			str_syouokai=result.split("/", 0);
			
	    	// �_�C�A���O�̍쐬�ƕ\��
	    	
	        //����N�����̂ݕ\������
	    	dialog4 = new Dialog(Help.this);
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
			d_title.setText("�ݒ�ɂ���");
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
					 	if(count_syokai==2){
					 		d_syoukai_img.setImageResource(R.anim.anim_settei_setumei);
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
		}
	}

}
