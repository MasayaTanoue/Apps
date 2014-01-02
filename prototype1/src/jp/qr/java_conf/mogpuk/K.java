package jp.qr.java_conf.mogpuk;


import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("HandlerLeak")
public class K extends Activity implements OnItemClickListener {
	Thread thread;
	Dialog dialog;
	AnimationDrawable animation;
	String[] info;
	String[] mTitles;
	Bitmap[] mThumbIds;
	String str;
	List<BindData> objects;
	BindData data;
	String[] url;
	Activity act;
	int now_col[];
	Bitmap misyutoku;
	RelativeLayout rl;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collection);
		
		//�w�i�ݒ�
		rl=(RelativeLayout)findViewById(R.id.layout_collection_bg);
	    rl.setBackgroundResource(R.drawable.bg_repeat_kuro);
	    
	    //�㕔�\���L�����̕ύX
	    TextView txt_cha_vo=(TextView)findViewById(R.id.txt_col_fuki);
	    txt_cha_vo.setText("���܂Ŋl������\n"+"�ό��n�摜��\n"+"�m�F�ł��܂��B");
	    txt_cha_vo.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    ImageView img_cha=(ImageView)findViewById(R.id.imag_kupon_bg2);
	    img_cha.setImageResource(R.drawable.col_cha6);
		
		act=K.this;
		now_col=new int[10];
		mThumbIds = new Bitmap[now_col.length];
		
		//���擾�摜�u�H�v��bitmap�ϊ�
	    int crd = getResources().getIdentifier("col_sil", "drawable", "jp.qr.java_conf.mogpuk");
		misyutoku = BitmapFactory.decodeResource(getResources(), crd);
		
		//�摜�̊l����Ԃ̓ǂݍ���
		 for(int i = 0; i<now_col.length;i++){
		        SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
		         now_col[i] = sp_now_zukan.getInt("now_kan_col"+i, 0);
		        }
		//�܂��S�Ăɖ��擾��ԉ摜��\��
		 for(int i=0;i<now_col.length;i++){
             		mThumbIds[i] = misyutoku;
             }
		
		//�T�[�o����f�[�^���擾
		FileLoader task = new FileLoader(act);
		task.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/txt/kan.txt");
		task.setOnCallBack(new FileLoader.CallBackFile() {	
			
			@Override
			public void CallBack(String Result) {
				
                str = Result;
                info = str.split(",", -1);
                
                objects = new ArrayList<BindData>();
                url = new String[info.length/3];
                for(int i=0;i<url.length;i++){
                	url[i]="http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/image/kan/kan"+i+".jpg";
                }

                
                ImageLoader _il = new ImageLoader(act);
                _il.execute(url);
				_il.setOnCallBack(new ImageLoader.CallBackImage(){
				 @Override
				 public void CallBack(Bitmap[] _bitmap) {
					 for(int i=0;i<url.length;i++){
						//�摜���l�����Ă���Ȃ�Ή����Ă�����̂�\��
						 if(now_col[i]==1){
							 mThumbIds[i]=_bitmap[i];
						 }
						 else{
							 switch(i){
				                //�T�[�o��1�s�ڂ�\��������
				                case 0:
				                	info[i+1]="�H�H�H";
				                	info[i+2]="�H�H�H";
				                	break;
				                //�T�[�o��2�s�ڈȍ~��\��������
				                default:
				                	info[i*3+1]="�H�H�H";
				                	info[i*3+2]="�H�H�H";
				        	    	break;
				             	}
						 }
					 }
		        	 
                
                //�����f�[�^��mTitles�z��Ɋi�[����
            	mTitles = new String[url.length];
            	int j = 3;

                for(int i = 0; i < mTitles.length; i++) {
                	switch(i){
                	case 0:
                		mTitles[i] = info[i];
                		break;
                	default:
                		mTitles[i] = info[i*j];
                		break;
                	}
                }
                
                
                
            	
                //mTitles(�����f�[�^), mThumbIds�i�摜�f�[�^�j�����ꂼ�ꃊ�X�g�r���[�ɒǉ�����
            	for(int i = 0; i < mTitles.length; i++) {
        			data = new BindData(mTitles[i], mThumbIds[i]);
        			objects.add(data);
        		}
            	
        		ListView list = (ListView)findViewById(R.id.list_zukan_ibe);
        		list.setAdapter(new ShopAdapter(K.this, objects));
        		list.setOnItemClickListener(K.this);
				 }
				});
			};		
		});
		
	};

	public void onItemClick(AdapterView<?>parent,View view,final int position,long id){   
		//�C�x���g�̏ڍׂ�\������_�C�A���O�̍쐬
		dialog = new Dialog(K.this);
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
		d_result_title.setText(info[0+(position*3)]);
		TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
		dialog_titile.setText(info[1+(position*3)]);
		ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
		image.setImageBitmap(mThumbIds[position]);
		TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
		dialog_setumei.setText(info[2+(position*3)]);
		TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
		dialog_close.setText("�~");
		
		//�_�C�A���O�́u�~�v�������ꂽ���̏���
		dialog.findViewById(R.id.d_result_close_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
}
