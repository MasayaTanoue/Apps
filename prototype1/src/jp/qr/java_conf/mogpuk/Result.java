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
	
	//�̗͂̌���
	int res1;
	//���͂̌���
	int res2;
	//�^�̌���
	int res3;
	//�Q�[�������X�e�[�W�ɑΉ��������ʂ��擾�i����=0,���莛=3,�Ԕn��=6�j
	int res4;
	//�]���Ɏg���ires1+res2�ō\���j
	int res5;
	int point_kei,w,count,game;
	
	//�l�������摜�̃��\�[�X�ɑΉ������z��ԍ����i�[
	int get_col;
	
	
	Activity act;
	String[] info;
	String[] mTitles;
	String str;
	
	//�ݒ�ǂݍ��ݗp
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
	    
	    //�ڍאݒ�̓ǂݍ���
	    SharedPreferences po22 = getSharedPreferences("tgl", MODE_PRIVATE);
        sta = po22.getInt("status3", 0);
	    
	    act=Result.this; 
	    
	    //�v���t�@�����X����f�[�^���󂯎��
	    SharedPreferences sp_result = getSharedPreferences("result", MODE_PRIVATE);
        res1 = sp_result.getInt("raikyakusuu", 0);
        res2 = sp_result.getInt("manzoku", 0);
        res3 = sp_result.getInt("bonus", 0);
	    res4 = sp_result.getInt("stage", 0);
	    
	    
	    //���ʂ�0�������ꍇ�Ƀ����_����1�`20���w�肷��
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
	    
	    
	    
	    //ID�o�^
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
	    
	    //�������\�[�X�ǂݍ���
	    oto1 = MediaPlayer.create(getBaseContext(), R.raw.result_hakken);
	    bgm = MediaPlayer.create(getBaseContext(), R.raw.bgm_result);
	    oto2 = MediaPlayer.create(getBaseContext(), R.raw.dialog_cancel);
	    bgm.setLooping(true);
	    bgm.start();
	    
	    //�t�H���g�̕ύX
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
	    
	    //���ʂ̌v�Z
	    int rand_kesu= rnd.nextInt(20)+10;

	    
	    raikyaku.setText(""+((res1*10)/5)+"�l");

	    manzoku.setText(""+((res2*rand_kesu)/15)+"%");
	    
	    //�]������
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
	    
	    
	  //�ݒ肪����Ă���Ȃ�l���|�C���g��{��
	    if(sta==1){
	    	res5 = res5+50;
	    }
	  //����G���A�̎�
        if(res4==0){
        }
        else if (res4==3){
        	res5=res5+50;
        }
        else if(res4==6){
        	res5=res5+100;
        }
        
	    

	    
	    mog.setText(""+res5+" MOG");
	    

        
        //w�i���݂̃|�C���g�j�ɓ����Ă���l���Ăяo���B���̏�����kupon.java�ƘA�����Ă���
        SharedPreferences po2 = getSharedPreferences("n", MODE_PRIVATE);
        w = po2.getInt("w", 0);
        
        //count�ɓ����Ă���l���Ăяo��
        SharedPreferences po3 = getSharedPreferences("name", MODE_PRIVATE);
        count = po3.getInt("count", 0);
        
      //game�ɓ����Ă���l���Ăяo���āC���Q�[���񐔂𑝂₷
        SharedPreferences po0 = getSharedPreferences("time", MODE_PRIVATE);
        game = po0.getInt("game", 0);
        game++;
        Editor e0 = po0.edit();
        e0.putInt("game", game);
        e0.commit();
  
     
        	if(w == 0){
        		//w�i���݂̃|�C���g�j��0�ł���ꍇ�̏���
        		w = point_kei + res5;	
        	}else{
        		//�Q�[�����i��w�i���݂̃|�C���g�j��0�łȂ��Ȃ����ꍇ�͂��̏������s��
        	    w = w + res5;
        	}  
        
        //w�ɓ����Ă��錻�݂̃|�C���g��"w"�ɓ��ꍞ�ݕۑ�����B���̏�����kupon.java�ƘA������
	    SharedPreferences po = getSharedPreferences("n", MODE_PRIVATE);
        Editor e = po.edit();
        e.putInt("w", w);
        e.commit();
        
        //���݂̊l��MOG��\������
        txt_goukei_mog.setText(""+w+" MOG");
        
        //�}�ӗp�R���N�V�����v�Z
        //����G���A�̎�
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
	        //�l�������摜���̕ۑ�
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
      //���莛�G���A�̎�
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
	        //�l�������摜���̕ۑ�
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
      //�Ԕn��G���A�̎�
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
	        //�l�������摜���̕ۑ�
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
        
      //TOP�̃{�^����enable�����ꂼ��ύX
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
						//�T�[�o����f�[�^���擾
						FileLoader task = new FileLoader(act);
						//�X�e�[�W�������URL��ύX
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
								
								
								//�l�������R���N�V������\������_�C�A���O�̍쐬
								dialog = new Dialog(Result.this);
								//�^�C�g���̔�\��
								dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
								//�t���X�N���[��
								dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
								//dialog_result.xml����ɂ��ă_�C�A���O���쐬
								dialog.setContentView(R.layout.dialog_result);
								//�w�i�𓧖��ɂ���
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								//�_�C�A���O�\������back�{�^���𖳌���
								dialog.setCancelable(false);
								
								dialog.show();
								oto1.start();
								
								//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
								TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
								d_result_title.setText("�}�ӏ��");
								TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
								dialog_titile.setText("�R���N�V����GET!!");
								ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
								image.setImageBitmap(bmp_col[0]);
								TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
								dialog_setumei.setText("No."+(get_col+1)+" �u"+info[get_col*3]+"�v\n"+"��������������`");
								TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
								dialog_close.setText("�~");
								
								//�_�C�A���O�́u�~�v�������ꂽ���̏���
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
						
						String[] chara_name= new String[]{"�����X�L�[","����畃����","�����ꂳ��","�}���K������","�����Ƃ���","�Ԃ΂΂�","�Z���q��","���I","�^�L","�}���K�������̑�","�t�^�J���l"};
						Bitmap bp;
						int crd = getResources().getIdentifier("col_cha" + (get_col), "drawable", "jp.qr.java_conf.mogpuk");
				    	bp = BitmapFactory.decodeResource(getResources(), crd);
								
								//�l�������R���N�V������\������_�C�A���O�̍쐬
								dialog = new Dialog(Result.this);
								//�^�C�g���̔�\��
								dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
								//�t���X�N���[��
								dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
								//dialog_result.xml����ɂ��ă_�C�A���O���쐬
								dialog.setContentView(R.layout.dialog_result);
								//�w�i�𓧖��ɂ���
								dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
								//�_�C�A���O�\������back�{�^���𖳌���
								dialog.setCancelable(false);
								
								dialog.show();
								oto1.start();
								
								//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
								TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
								d_result_title.setText("�}�ӏ��");
								TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
								dialog_titile.setText("�R���N�V����GET");
								ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
								image.setImageBitmap(bp);
								TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
								dialog_setumei.setText("No."+(get_col+1)+" �u"+chara_name[get_col]+"�v \n"+"�������������!!");
								TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
								dialog_close.setText("�~");
								
								//�_�C�A���O�́u�~�v�������ꂽ���̏���
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
	    //�݌v�l�������̌v�Z
	    goukei=kan+ibe+cha;

        //�l���󋵂̉摜��\��
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
        //�}�ӂ��R���v���[�g���Ă邩�ǂ���
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
      //�Q�[���񐔁C�����񐔂�ǂݍ���
	    SharedPreferences po10= getSharedPreferences("time", MODE_PRIVATE);
        game = po10.getInt("game", 0);
        
        time_game = new int[]{1,5,10,15,20,40,60,80,100};
        
      //�l���󋵂̉摜��\��
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
	            // �_�C�A���O�\���ȂǓ���̏������s�������ꍇ�͂����ɋL�q
	            // �e�N���X��dispatchKeyEvent()���Ăяo������true��Ԃ�
	            return true;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
}

