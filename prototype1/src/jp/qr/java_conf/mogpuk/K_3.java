package jp.qr.java_conf.mogpuk;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class K_3 extends Activity {
	
    int count=0;
    Dialog dialog;
    BindData data;
    String[] chara_name;
    String[] chara_syousai;
    Bitmap[] chara_img;
    int now_col[];
	Bitmap misyutoku;
	RelativeLayout rl;
	
	//txt�����p
	Resources res;
	String file_result;
	String result;
	BufferedReader br = null;
	InputStream is;
	StringBuilder sb = new StringBuilder();
    
    
    public class CustomData {
	    private Bitmap image_stage;
	    private String textData_stagemei;
	    private String textData_stageinfo;
	    
	 
	    public void setImagaData(Bitmap image) {
	        image_stage = image;
	    }
	 
	    public Bitmap getImageData() {
	        return image_stage;
	    }
	 
	    public void setTextData(String text) {
	        textData_stagemei = text;
	    }
	    public void setTextData2(String text2) {
	        textData_stageinfo = text2;
	    }
	 
	    public String getTextData() {
	        return textData_stagemei;
	    }
	    public String getTextData2() {
	        return textData_stageinfo;
	    }
	    
	}
	
	public class CustomAdapter extends ArrayAdapter<CustomData> {
		 private LayoutInflater layoutInflater_;
		 
		 public CustomAdapter(Context context, int textViewResourceId, List<CustomData> objects) {
		 super(context, textViewResourceId, objects);
		 layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 }
		 
		 @Override
		 public View getView(int position, View convertView, ViewGroup parent) {
		 // ����̍s(position)�̃f�[�^�𓾂�
		 CustomData item = (CustomData)getItem(position);
		 
		 // convertView�͎g���񂵂���Ă���\��������̂�null�̎������V�������
		 if (null == convertView) {
		 convertView = layoutInflater_.inflate(R.layout.list_layout_shop, null);
		 }
		 
		 // CustomData�̃f�[�^��View�̊eWidget�ɃZ�b�g����
		 ImageView imageView;
		 imageView = (ImageView)convertView.findViewById(R.id.imageview);
		 imageView.setImageBitmap(item.getImageData());
		 
		 TextView textView;
		 textView = (TextView)convertView.findViewById(R.id.textview2);
		 textView.setText(item.getTextData());
		 
		 return convertView;
		 }
		 @Override
		 public boolean isEnabled(int position) { 
      	   	 //�摜�̊l����Ԃ̓ǂݍ���
    		 for(int i = 0; i<now_col.length;i++){
 		        SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
 		         now_col[i] = sp_now_zukan.getInt("now_cha_col"+i, 0);
 		         }
    		 if(now_col[position]==1){
    			 return true;
         		}
             	else{
             		return false;
             	}
             }
		}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.collection);    
	  //�\���p�e�L�X�g�̕���
    	
    
    	
    	res = this.getResources();
    	is = res.openRawResource(R.raw.cha_syoukai);
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
		chara_syousai=result.split("/", 0);
	    
	    rl=(RelativeLayout)findViewById(R.id.layout_collection_bg);
	    rl.setBackgroundResource(R.drawable.bg_repeat_aka);
	    
	  //�㕔�\���L�����̕ύX
	    TextView txt_cha_vo=(TextView)findViewById(R.id.txt_col_fuki);
	    txt_cha_vo.setText("���܂Ŋl������\n"+"�L�����摜��\n"+"�m�F�ł����[�B");
	    txt_cha_vo.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    ImageView img_cha=(ImageView)findViewById(R.id.imag_kupon_bg2);
	    img_cha.setImageResource(R.drawable.col_cha8);
	    
	    now_col=new int[11];
	    chara_name= new String[]{"�����X�L�[","����畃����","�����ꂳ��","�}���K������","�����Ƃ���","�Ԃ΂΂�","�Z���q��","���I","�^�L","�}���K�������̑�","�t�^�J���l"};
	    chara_img= new Bitmap[now_col.length];
	    
	    
		
		//���擾�摜�u�H�v��bitmap�ϊ�
	    int crd = getResources().getIdentifier("col_sil", "drawable", "jp.qr.java_conf.mogpuk");
		misyutoku = BitmapFactory.decodeResource(getResources(), crd);
		
		//�摜�̊l����Ԃ̓ǂݍ���
		 for(int i = 0; i<now_col.length;i++){
		        SharedPreferences sp_now_zukan = getSharedPreferences("collection", MODE_PRIVATE);
		         now_col[i] = sp_now_zukan.getInt("now_cha_col"+i, 0);
		  }
		 
		 //�܂��S�Ăɖ��擾��ԉ摜��\��
		 for(int i=0;i<now_col.length;i++){
             		chara_img[i] = misyutoku;
         }
	    
	    List<CustomData> objects = new ArrayList<CustomData>();
 
        CustomAdapter customAdapater = new CustomAdapter(this, 0, objects);
        
        for(int i = 0; i < now_col.length; i++){
	    	//�摜���l�����Ă���Ȃ�Ή����Ă�����̂�\��
        	if(now_col[i]==1){
        		int cr = getResources().getIdentifier("col_cha" + (i), "drawable", "jp.qr.java_conf.mogpuk");
        		chara_img[i]=BitmapFactory.decodeResource(getResources(), cr);
        	}
        	
            //���X�g�Ɋi�[����A�C�e�����P���쐬
	    	CustomData item = new CustomData();
	    	item.setImagaData(chara_img[i]);
	    	item.setTextData(chara_name[i]);
	    	objects.add(item);
	    }
    	
 
        ListView listView = (ListView)findViewById(R.id.list_zukan_ibe);
        listView.setAdapter(customAdapater);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //���X�g���ڃN���b�N���̏���
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        	   
	        	 //�C�x���g�̏ڍׂ�\������_�C�A���O�̍쐬
	       		dialog = new Dialog(K_3.this);
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
	       		d_result_title.setText("�L�����}��");
	       		TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
	       		dialog_titile.setText("�u"+chara_name[position]+"�v");
	       		ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
	       		image.setImageBitmap(chara_img[position]);
	       		TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
	       		dialog_setumei.setText(chara_syousai[position]);
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
           });
	}

	protected void onPause(){
		super.onPause();
		
	}
}
