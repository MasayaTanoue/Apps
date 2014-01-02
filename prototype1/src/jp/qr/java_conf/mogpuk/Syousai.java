package jp.qr.java_conf.mogpuk;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Syousai extends Activity implements OnClickListener{
	
    int count,game,koukan;
    Dialog dialog;
    BindData data;
    String[] chara_name;
    String[] chara_syousai;
    Boolean[] bool_tgl;//�g�O���{�^����enable
    Boolean[] bool_tgl2;//�g�O���{�^����check
    int[] time_game;
    int[] tgl_status;
    Bitmap[] img_check;
    Button btn_now_result;
    int st;
    RelativeLayout rl;
    int kouken,kaisuu;
    int[] kiyora_kouken;
    String[] kiyora_syurui= {"�t�^�J�������X�L�[","�����˂����X�L�[","���߂����X�L�[","���������X�L�["};
    String[] kiyora_sp_setu={"�����Ƃ���`�����郉���E�E�E","�����ƌP�����K�v����!!","����`���ɂ�����Ă�������!!","��������撣��������!!","�}�ӃR���v���[�g���߂łƂ�����!!"};
    String[] kouka_syoukai= {"�Q�[���J�n���̗̑͂��{15����郉��!!","�Q�[���J�n���̖��͂��{15����郉��!!","�Q�[���J�n���̉^���{15����郉��!!",
    		"�Q�[�����̊l��MOG��UP���郉��!!","����`���I�����Ԃ������Ȃ郉��!!",
    		"�Q�[���J�n���̗̑͂��{30����郉��!!","�Q�[���J�n���̖��͂��{30����郉��!!","�Q�[���J�n���̉^���{30����郉��!!",
    		"�N�[�|���������̎g�pMOG��1/2�ɂȂ郉��!!"};
    int[] kouken_lim={0,50,100,175,250};
    int kan,ibe,cha,goukei;
    CustomAdapter customAdapater;
    ToggleButton tgll ;
    int[] chara_img;
    
    
    public class CustomData {
    	private boolean btn_Data_tgl;
    	private boolean btn_Data_tgl2;
	    private String textData_stagemei;
	    private String textData_stageinfo;

	 
	    public void setTextData(String text) {
	        textData_stagemei = text;
	    }
	    public void setTextData2(String text2) {
	        textData_stageinfo = text2;
	    }
	    public void setTgl_Data(Boolean tgl) {
	        btn_Data_tgl = tgl;
	    }
	    public void setTgl_Data2(Boolean tgl2) {
	        btn_Data_tgl2 = tgl2;
	    }
	    
	    public String getTextData() {
	        return textData_stagemei;
	    }
	    public String getTextData2() {
	        return textData_stageinfo;
	    }
	    public Boolean getTgl() {
	        return btn_Data_tgl;
	    }
	    public Boolean getTgl2() {
	        return btn_Data_tgl2;
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
		 convertView = layoutInflater_.inflate(R.layout.list_layout_bonus, null);
		 }
		 
		 // CustomData�̃f�[�^��View�̊eWidget�ɃZ�b�g����
		 ToggleButton tgl;
		 tgl=(ToggleButton)convertView.findViewById(R.id.toggle1);
		 tgl.setEnabled(item.getTgl());
		 tgl.setChecked(item.getTgl2());
		 
		 TextView textView;
		 textView = (TextView)convertView.findViewById(R.id.te1);
		 textView.setText(item.getTextData());
		 
		 TextView textView1;
		 textView1 = (TextView)convertView.findViewById(R.id.textview);
		 textView1.setText(item.getTextData2());
		 
		 return convertView;
		 }
		 @Override
		 public boolean isEnabled(int position) {
			 
			 bool_tgl=new Boolean[9];
			 time_game = new int[]{1,5,10,15,20,40,60,80,100};
      	   	 SharedPreferences po0 = getSharedPreferences("time", MODE_PRIVATE);
             game = po0.getInt("game", 0);
             
      		   if(game<time_game[position]){
      			 return false;
             	}
             	else{
             		return true;
             	}
             }
		}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.syousai);  
	    
	    
	  //�}�ӃR���v���[�g�ǂݍ���
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
	   

	    rl=(RelativeLayout)findViewById(R.id.RelativeLayout1);
	    rl.setBackgroundResource(R.drawable.bg_repeat_man);
	    
	  //�㕔�\���L�����̕ύX
	    TextView txt_cha_vo=(TextView)findViewById(R.id.Text_syousai_kiyo);
	    txt_cha_vo.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    
	    //�{�^����ID�F��
	    btn_now_result=(Button)findViewById(R.id.btn_syousai_result);
	    btn_now_result.setOnClickListener(this);
	    
	    //���X�g�p�̔z��
	    chara_name= new String[]{"1��","5��","10��","15��","20��","40��","60��","80��","100��"};
	    chara_syousai= new String[]{"�����Ƃ����̋S�̂����","�Z���q���̗�V��@","�^�L�̋C����","�Ԃ΂΂��̒m�b","���̂���`��","����畃����̗�","���I�̎ʐ^","�}���K�������̂����","�����ꂳ��̔������p"};
	    bool_tgl=new Boolean[9];
	    bool_tgl2=new Boolean[9];
	    img_check=new Bitmap[9]; 
	    chara_img= new int[chara_name.length];
	    chara_img[0]=getResources().getIdentifier("col_cha4","drawable", "jp.qr.java_conf.mogpuk");
	    chara_img[1]=getResources().getIdentifier("col_cha6","drawable", "jp.qr.java_conf.mogpuk");
	    chara_img[2]=getResources().getIdentifier("col_cha8","drawable", "jp.qr.java_conf.mogpuk");
	    chara_img[3]=getResources().getIdentifier("col_cha5","drawable", "jp.qr.java_conf.mogpuk");
	    chara_img[4]=getResources().getIdentifier("col_cha9","drawable", "jp.qr.java_conf.mogpuk");
	    chara_img[5]=getResources().getIdentifier("col_cha1","drawable", "jp.qr.java_conf.mogpuk");
	    chara_img[6]=getResources().getIdentifier("col_cha7","drawable", "jp.qr.java_conf.mogpuk");
	    chara_img[7]=getResources().getIdentifier("col_cha3","drawable", "jp.qr.java_conf.mogpuk");
	    chara_img[8]=getResources().getIdentifier("col_cha2","drawable", "jp.qr.java_conf.mogpuk");
	    
	    time_game = new int[]{1,5,10,15,20,40,60,80,100};
	    tgl_status =new int[9];
	    
	    //�Q�[���񐔁C�����񐔂�ǂݍ���
	    SharedPreferences po0 = getSharedPreferences("time", MODE_PRIVATE);
        game = po0.getInt("game", 0);
	    SharedPreferences po1 = getSharedPreferences("name", MODE_PRIVATE);
        koukan = po1.getInt("count", 0);
        SharedPreferences po2 = getSharedPreferences("name", MODE_PRIVATE);
        kaisuu = po2.getInt("kaisuu", 0);
        
        //�_�C�A���O�\���p�̍v���x�̌v�Z
        kouken= game*2+kaisuu*3;
        if(kouken>500){
        	kouken=500;
        }
        
        //�v���x�ɉ������摜�̕ύX����
        kiyora_kouken=new int[4];
        for(int i = 0; i<kiyora_kouken.length;i++){
        	 kiyora_kouken[i] = getResources().getIdentifier("sp"+i, "drawable", "jp.qr.java_conf.mogpuk");

	        }
        
	    
        //tglbtn�̐ݒ�̓ǂݍ���
        //tglbtn�̏�Ԃ𔽉f�i0=false,1=true�j
        for(int i = 0;i<9;i++){
        	SharedPreferences po5 = getSharedPreferences("tgl", MODE_PRIVATE);
            tgl_status[i] = po5.getInt("status"+i, 0);
            if(tgl_status[i]==0){
            	bool_tgl2[i]=false;
            }
            else if(tgl_status[i]==1){
            	bool_tgl2[i]=true;
            }
        }
        
        
	    
	    List<CustomData> objects = new ArrayList<CustomData>();
        customAdapater = new CustomAdapter(this, 0, objects);
        
        
        
        for(int i = 0; i < chara_name.length ; i++){
	    	//�摜���\�[�X�����ɔz��Ɋi�[
        	if(game<time_game[i]){
        		bool_tgl[i]=false;
        		chara_syousai[i]="�H�H�H�H";
        	}
        	else if(game>=time_game[i]){
        		bool_tgl[i]=true;
        	}
            //���X�g�Ɋi�[����A�C�e�����P���쐬
	    	CustomData item = new CustomData();
	    	item.setTextData(chara_name[i]);
	    	item.setTextData2(chara_syousai[i]);
	    	item.setTgl_Data(bool_tgl[i]);
	    	item.setTgl_Data2(bool_tgl2[i]);
	    	objects.add(item);
	    }
    	
 
        ListView listView = (ListView)findViewById(R.id.list_settei);
        listView.setAdapter(customAdapater);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //���X�g���ڃN���b�N���̏���
           @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
        		   
        	   		//ToggleButton tgll = (ToggleButton) view.findViewById(R.id.toggle1);
    	        	SharedPreferences po2 = getSharedPreferences("tgl", MODE_PRIVATE);
    	            st = po2.getInt("status"+position, 0);
    	            if(st==0){
    	            	Editor ed = po2.edit();
    		        	ed.putInt("status"+position, 1);
    		        	ed.commit();
    		        	bool_tgl2[position]=true;
    		        	
    		        	//�C�x���g�̏ڍׂ�\������_�C�A���O�̍쐬
    		       		dialog = new Dialog(Syousai.this);
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
    		       		d_result_title.setText("�uON�v�ɂ����Ƃ��̌���");
    		       		TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
    		       		dialog_titile.setText("�u"+chara_syousai[position]+"�v");
    		       		ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
    		       		image.setImageResource(chara_img[position]);
    		       		TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
    		       		dialog_setumei.setText(kouka_syoukai[position]);
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
    	            else if(st==1){
    	            	Editor ed = po2.edit();
    		        	ed.putInt("status"+position, 0);
    		        	ed.commit();
    		        	bool_tgl2[position]=false;
    	            }
    	           
    	            
    	            List<CustomData> objects = new ArrayList<CustomData>();
    	            customAdapater = new CustomAdapter(Syousai.this, 0, objects);
    	            
    	            
    	            
    	            for(int i = 0; i < chara_name.length ; i++){
    	    	    	//�摜���\�[�X�����ɔz��Ɋi�[
    	            	if(game<time_game[i]){
    	            		bool_tgl[i]=false;
    	            		chara_syousai[i]="�H�H�H�H";
    	            	}
    	            	else if(game>=time_game[i]){
    	            		bool_tgl[i]=true;
    	            	}
    	                //���X�g�Ɋi�[����A�C�e�����P���쐬
    	    	    	CustomData item = new CustomData();
    	    	    	item.setTextData(chara_name[i]);
    	    	    	item.setTextData2(chara_syousai[i]);
    	    	    	item.setTgl_Data(bool_tgl[i]);
    	    	    	item.setTgl_Data2(bool_tgl2[i]);
    	    	    	objects.add(item);
    	    	    }
    	        	
    	     
    	            ListView listView = (ListView)findViewById(R.id.list_settei);
    	            listView.setAdapter(customAdapater);
	           }
           });
	}
	


	

	@Override
	public void onClick(View v) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		dialog = new Dialog(Syousai.this);
   		//�^�C�g���̔�\��
   		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
   		//�t���X�N���[��
   		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
   		//dialog_result.xml����ɂ��ă_�C�A���O���쐬
   		dialog.setContentView(R.layout.dialog_result_goukei);
   		//�w�i�𓧖��ɂ���
   		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
   		dialog.show();
   		
   		//�_�C�A���O�ɕ\������e�L�X�g��ID�o�^�ƕ\������
   		TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
   		d_result_title.setText("�����X�L�[�̕]��");
   		TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
   		TextView dialog_sp=(TextView)dialog.findViewById(R.id.text_sp);
   		TextView dialog_kaisu=(TextView)dialog.findViewById(R.id.txt_result_dialog_kaisu_p);
   		dialog_kaisu.setText(""+game+"��");
   		TextView dialog_koukan=(TextView)dialog.findViewById(R.id.txt_result_dialog_koukan_p);
   		dialog_koukan.setText(""+kaisuu+"��");
   		ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
   		for(int i = 0; i<kiyora_kouken.length;i++){
       	 if(kouken>=kouken_lim[i]){
       		 dialog_sp.setText(kiyora_sp_setu[i]);
       		 image.setImageResource(kiyora_kouken[i]);
       		 dialog_titile.setText(""+kiyora_syurui[i]);
       	 	}
         //�}�ӂ��R���v���[�g���Ă邩�ǂ���
         if(goukei>=33){
        	 dialog_sp.setText(kiyora_sp_setu[4]);
        	 image.setImageResource(R.drawable.sp4);
       		 dialog_titile.setText("�ؓ������X�L�[");
         }
	    }
   		TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
   		dialog_close.setText("�~");
   		
   		TextView dialog_game=(TextView)dialog.findViewById(R.id.txt_result_dialog_game_p);
   		dialog_game.setText(""+kouken+"pt");
   		
   		
   		
   		//�_�C�A���O�́u�~�v�������ꂽ���̏���
   		dialog.findViewById(R.id.d_result_close_button).setOnClickListener(new View.OnClickListener() {
   			@Override
   			public void onClick(View v) {
   				dialog.dismiss();
   				}
   			});
		}
	
	protected void onPause(){
		super.onPause();
		
	}
}
