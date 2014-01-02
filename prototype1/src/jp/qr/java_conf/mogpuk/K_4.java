package jp.qr.java_conf.mogpuk;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class K_4 extends Activity {
	int count,game,koukan;
    Dialog dialog;
    BindData data;
    String[] chara_name;
    String[] chara_syousai;
    String[] tassei;
    int[] count_gazou;
    int kan,ibe,cha,goukei;
    Bitmap[] img_check;
    Button btn_now_result;
    RelativeLayout rl;
    
    
    
    public class CustomData {
    	private Bitmap image_stage;
	    private String textData_stagemei;
	    private String textData_stageinfo;
	    private String textData_tassei;
	    
	 
	    public void setImagaData(Bitmap image) {
	        image_stage = image;
	    }
	 
	    public Bitmap getImageData() {
	        return image_stage;
	    }
	    public void setTextData3(String text3) {
	        textData_tassei = text3;
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
	    public String getTextData3() {
	        return textData_tassei;
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
		 convertView = layoutInflater_.inflate(R.layout.list_layout_settei, null);
		 }
		 
		 // CustomData�̃f�[�^��View�̊eWidget�ɃZ�b�g����
		 
		 ImageView imageView;
		 imageView = (ImageView)convertView.findViewById(R.id.check1);
		 imageView.setImageBitmap(item.getImageData());
		 
		 TextView textView;
		 textView = (TextView)convertView.findViewById(R.id.te1);
		 textView.setText(item.getTextData());

		 
		 TextView textView1;
		 textView1 = (TextView)convertView.findViewById(R.id.textview);
		 textView1.setText(item.getTextData2());
		 
		 TextView textView3;
		 textView3 = (TextView)convertView.findViewById(R.id.textView2);
		 textView3.setText(item.getTextData3());
		 
		 
		 if(item.getTextData3()=="�B��"){
			 textView3.setTextColor(Color.RED);
		 }
		 else{
			 textView3.setTextColor(Color.BLUE);
		 }
		 
		 return convertView;
		 }
		}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.collection);    
	    
	    rl=(RelativeLayout)findViewById(R.id.layout_collection_bg);
	    rl.setBackgroundResource(R.drawable.bg_repeat_mog);
	    
	  //�㕔�\���L�����̕ύX
	    TextView txt_cha_vo=(TextView)findViewById(R.id.txt_col_fuki);
	    txt_cha_vo.setText("�}�ӂ����܂��\n"+"MOG��w�i�摜��\n"+"�l���ł���ł���B");
	    txt_cha_vo.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    ImageView img_cha=(ImageView)findViewById(R.id.imag_kupon_bg2);
	    img_cha.setImageResource(R.drawable.col_cha4);
	    
	    //���X�g�p�̔z��
	    chara_name= new String[]{"�}�Ӂ~1","�}�Ӂ~3","�}�Ӂ~5","�}�Ӂ~10","�}�Ӂ~15","�ό��n�}��\n"+"�R���v���[�g","�C�x���g�}��\n"+"�R���v���[�g","�L�����}��\n"+"�R���v���[�g","�S�}��\n"+"�R���v���[�g"};
	    chara_syousai= new String[]{"+100MOG","+500MOG","+750MOG","+1000MOG","+1500MOG","�w�i�@��","�w�i�@�[","�w�i�@��","�����X�L�[�����"};
	    tassei=new String[9];
	    img_check=new Bitmap[9]; 
	    count_gazou=new int[]{1,3,5,10,15};
	    
	    
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
	    
	    
	    List<CustomData> objects = new ArrayList<CustomData>();
        CustomAdapter customAdapater = new CustomAdapter(this, 0, objects);
	    
        //�l���󋵂̉摜��\��
        for(int i=0;i<5;i++){
	        if(goukei<count_gazou[i]){
	    		int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
	    		img_check[i]=BitmapFactory.decodeResource(getResources(), crd);
	    		tassei[i]="���B��";
	    		chara_syousai[i]="�H�H�H�H";
	    	}
	    	else if(goukei>=count_gazou[i]){
	    		int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
	    		img_check[i]=BitmapFactory.decodeResource(getResources(), crd);
	    		tassei[i]="�B��";
	    	}
        }
        //�}�ӂ��R���v���[�g���Ă邩�ǂ���
        if(kan<10){
        	int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[5]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[5]="���B��";
    		chara_syousai[5]="�H�H�H�H";
        }
        else if(kan>=10){
        	int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[5]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[5]="�B��";
        }
        if(ibe<12){
        	int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[6]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[6]="���B��";
    		chara_syousai[6]="�H�H�H�H";
        }
        else if(ibe>=12){
        	int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[6]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[6]="�B��";
        }
        if(cha<11){
        	int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[7]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[7]="���B��";
    		chara_syousai[7]="�H�H�H�H";
        }
        else if(cha>=11){
        	int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[7]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[7]="�B��";
        }
        if(goukei<33){
        	int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[8]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[8]="���B��";
    		chara_syousai[8]="�H�H�H�H";
        }
        else if(goukei>=33){
        	int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[8]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[8]="�B��";
        }
        
        
        for(int i = 0; i < chara_name.length ; i++){
            //���X�g�Ɋi�[����A�C�e�����P���쐬
	    	CustomData item = new CustomData();
	    	item.setImagaData(img_check[i]);
	    	item.setTextData(chara_name[i]);
	    	item.setTextData2(chara_syousai[i]);
	    	item.setTextData3(tassei[i]);
	    	objects.add(item);
	    }
    	
 
        ListView listView = (ListView)findViewById(R.id.list_zukan_ibe);
        listView.setAdapter(customAdapater);
        
        listView.setOnItemClickListener(null);
	        	 
	}
	protected void onPause(){
		super.onPause();
		
	}
}