package jp.qr.java_conf.mogpuk;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class Shop extends Activity implements OnItemClickListener,OnClickListener{

	Thread thread;
	Dialog dialog;
	AnimationDrawable animation;
	String[] info;
	String[] mTitles;
	String[] mTitles2;
	Bitmap[] mThumbIds;
	ArrayList<Bitmap> bmp_List;
	String str;
	List<BindData2> objects;
	BindData2 data;
	String[] url;
	Activity act;
	
	//���̍��ڂ�\������{�^��
	Button btn_left;
	Button btn_right;
	
	//���݉��Ԗڂ̍��ڂ��ۑ�����ϐ�
	int now_count;
	int zyougen;
	
	RelativeLayout rl;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop);
		
		rl=(RelativeLayout)findViewById(R.id.layout_shop_bg);
	    rl.setBackgroundResource(R.drawable.bg_repeat_man);
	    
	    TextView txt_cha_vo=(TextView)findViewById(R.id.imag_kupon_fukidashi);
	    txt_cha_vo.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
		
		btn_left=(Button)findViewById(R.id.img_btn_left);
		btn_left.setOnClickListener(this);
		btn_right=(Button)findViewById(R.id.img_btn_right);
		btn_right.setOnClickListener(this);
	}
		
	protected  void onResume(){
		super.onResume();
		act=Shop.this;
		
		SharedPreferences sp = getSharedPreferences("shop", MODE_PRIVATE);
        now_count = sp.getInt("line", 0);
		
		if(now_count==0){
			btn_left.setEnabled(false);
			btn_left.setText("-----");
			btn_right.setEnabled(true);
			btn_right.setText("���̂T��");
		}
		if(now_count!=0){
			btn_left.setEnabled(true);
			btn_left.setText("�O�̂T��");
		}			

		//�T�[�o����f�[�^���擾
		FileLoader task = new FileLoader(act);
		task.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/sample.txt");
		task.setOnCallBack(new FileLoader.CallBackFile() {	
			
			@Override
			public void CallBack(String Result) {
				
                str = Result;
                info = str.split(",", -1);
                
                
                objects = new ArrayList<BindData2>();
                zyougen=5;
                //�f�[�^���Ō��5�̏ꍇ�C�\������5�ł͂Ȃ��[���ɍ��킹��
                
                if((now_count+1)*5>info.length/6){
                	zyougen = (info.length/6)-(now_count*5); 
                	btn_right.setEnabled(false);
                	btn_right.setText("-----");
                }
                url = new String[zyougen];
                for(int i=0;i<zyougen;i++){
                	url[i]="http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/image/kupon/kupon"+(i+(5*now_count))+".jpg";
                }

                mThumbIds = new Bitmap[url.length];
                ImageLoader _il = new ImageLoader(act);
                _il.execute(url);
				_il.setOnCallBack(new ImageLoader.CallBackImage(){
				 @Override
				 public void CallBack(Bitmap[] _bitmap) {
					 for(int i=0;i<zyougen;i++){
						 mThumbIds[i]=_bitmap[i];
					 }
                
                //�����f�[�^��mTitles�z��Ɋi�[����
            	mTitles = new String[url.length];
            	mTitles2 = new String[url.length];
            	int j = 6;

                for(int i = 0;i<zyougen; i++) {
                	mTitles[i] = info[j*(5*now_count+i)];
                	mTitles2[i]= info[j*(5*now_count+i)+1];
                }
            	
                //mTitles(�����f�[�^), mThumbIds�i�摜�f�[�^�j�����ꂼ�ꃊ�X�g�r���[�ɒǉ�����
            	for(int i = 0; i < mTitles.length; i++) {
        			data = new BindData2(mTitles[i],mTitles2[i], mThumbIds[i]);
        			objects.add(data);
        		}
            	
        		ListView list = (ListView)findViewById(R.id.list);
        		list.setAdapter(new ShopAdapter2(Shop.this, objects));
        		list.setOnItemClickListener(Shop.this);
				 }
				});
			};		
		});
		
	}

	public void onItemClick(AdapterView<?>parent,View view,final int position,long id){   
		Intent intent = new Intent();
		intent.setClass(this, kupon.class);
		SharedPreferences po_shop = getSharedPreferences("shop", MODE_PRIVATE);
        Editor es = po_shop.edit();
        es.putInt("line", now_count);
        es.commit();
		if(now_count>=1){
			int koumoku =position+(5*now_count);
			//"a"�ɉ��Ԗڂ̃��X�g�������ꂽ���̃f�[�^��^����
			intent.putExtra("a", koumoku);
			startActivityForResult(intent, koumoku);
		}
		else if(now_count==0){
			//"a"�ɉ��Ԗڂ̃��X�g�������ꂽ���̃f�[�^��^����
			intent.putExtra("a", position);
			startActivityForResult(intent, position);
		}
	}

	@Override
	public void onClick(View v) {
		
		if(v==btn_left){
			now_count = now_count-1;			
			act=Shop.this;
			btn_right.setEnabled(true);
			btn_right.setText("���̂T��");
			//�P���ڂ̎��u�O�̂T���v�v��enable��L����
			if(now_count==0){
				btn_left.setEnabled(false);
				btn_left.setText("-----");
				
			}
			
			//�T�[�o����f�[�^���擾
			FileLoader task = new FileLoader(act);
			task.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/sample.txt");
			task.setOnCallBack(new FileLoader.CallBackFile() {	
				
				@Override
				public void CallBack(String Result) {
					
	                str = Result;
	                info = str.split(",", -1);
	                
	                
	                objects = new ArrayList<BindData2>();
	                zyougen=5;
	                
	                url = new String[zyougen];
	                for(int i=0;i<zyougen;i++){
	                	url[i]="http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/image/kupon/kupon"+(i+(5*now_count))+".jpg";
	                }

	                mThumbIds = new Bitmap[url.length];
	                ImageLoader _il = new ImageLoader(act);
	                _il.execute(url);
					_il.setOnCallBack(new ImageLoader.CallBackImage(){
					 @Override
					 public void CallBack(Bitmap[] _bitmap) {
						 for(int i=0;i<zyougen;i++){
							 mThumbIds[i]=_bitmap[i];
						 }
	                
	                //�����f�[�^��mTitles�z��Ɋi�[����
	            	mTitles = new String[url.length];
	            	mTitles2 = new String[url.length];
	            	int j = 6;

	                for(int i = 0;i<zyougen; i++) {
	                	mTitles[i] = info[j*(5*now_count+i)];
	                	mTitles2[i]= info[j*(5*now_count+i)+1];
	                }
	            	
	                //mTitles(�����f�[�^), mThumbIds�i�摜�f�[�^�j�����ꂼ�ꃊ�X�g�r���[�ɒǉ�����
	            	for(int i = 0; i < mTitles.length; i++) {
	        			data = new BindData2(mTitles[i],mTitles2[i], mThumbIds[i]);
	        			objects.add(data);
	        		}
	            	
	        		ListView list = (ListView)findViewById(R.id.list);
	        		list.setAdapter(new ShopAdapter2(Shop.this, objects));
	        		list.setOnItemClickListener(Shop.this);
					 }
					});
				};		
			});
		}
		else if(v==btn_right){
			
			
			
			now_count=now_count+1;			
			act=Shop.this;
			
			//5���ڈȍ~�̎��u�O�̂T���v�v��enable��L����
			if(now_count!=0){
				btn_left.setEnabled(true);
				btn_left.setText("�O�̂T��");
			}
			
			//�T�[�o����f�[�^���擾
			FileLoader task = new FileLoader(act);
			task.execute("http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/sample.txt");
			task.setOnCallBack(new FileLoader.CallBackFile() {	
				
				@Override
				public void CallBack(String Result) {
					
	                str = Result;
	                info = str.split(",", -1);
	                
	                
	                objects = new ArrayList<BindData2>();
	                zyougen=5;
	                //�f�[�^���Ō��5�̏ꍇ�C�\������5�ł͂Ȃ��[���ɍ��킹��
	                
	                if((now_count+1)*5>info.length/6){
	                	zyougen = (info.length/6)-(now_count*5); 
	                	btn_right.setEnabled(false);
	                	btn_right.setText("-----");
	                }
	                url = new String[zyougen];
	                for(int i=0;i<zyougen;i++){
	                	url[i]="http://www.pu-kumamoto.ac.jp/~iimulab/akiyora/image/kupon/kupon"+(i+(5*now_count))+".jpg";
	                }

	                mThumbIds = new Bitmap[url.length];
	                ImageLoader _il = new ImageLoader(act);
	                _il.execute(url);
					_il.setOnCallBack(new ImageLoader.CallBackImage(){
					 @Override
					 public void CallBack(Bitmap[] _bitmap) {
						 for(int i=0;i<zyougen;i++){
							 mThumbIds[i]=_bitmap[i];
						 }
	                
	                //�����f�[�^��mTitles�z��Ɋi�[����
	            	mTitles = new String[url.length];
	            	mTitles2 = new String[url.length];
	            	int j = 6;

	                for(int i = 0;i<zyougen; i++) {
	                	mTitles[i] = info[j*(5*now_count+i)];
	                	mTitles2[i]= info[j*(5*now_count+i)+1];
	                }
	            	
	                //mTitles(�����f�[�^), mThumbIds�i�摜�f�[�^�j�����ꂼ�ꃊ�X�g�r���[�ɒǉ�����
	            	for(int i = 0; i < mTitles.length; i++) {
	        			data = new BindData2(mTitles[i],mTitles2[i], mThumbIds[i]);
	        			objects.add(data);
	        		}
	            	
	        		ListView list = (ListView)findViewById(R.id.list);
	        		list.setAdapter(new ShopAdapter2(Shop.this, objects));
	        		list.setOnItemClickListener(Shop.this);
					 }
					});
				};		
			});
		}
		
	}
}