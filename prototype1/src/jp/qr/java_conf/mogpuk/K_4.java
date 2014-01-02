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
		 // 特定の行(position)のデータを得る
		 CustomData item = (CustomData)getItem(position);
		 
		 // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		 if (null == convertView) {
		 convertView = layoutInflater_.inflate(R.layout.list_layout_settei, null);
		 }
		 
		 // CustomDataのデータをViewの各Widgetにセットする
		 
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
		 
		 
		 if(item.getTextData3()=="達成"){
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
	    
	  //上部表示キャラの変更
	    TextView txt_cha_vo=(TextView)findViewById(R.id.txt_col_fuki);
	    txt_cha_vo.setText("図鑑が埋まると\n"+"MOGや背景画像が\n"+"獲得できるである。");
	    txt_cha_vo.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    ImageView img_cha=(ImageView)findViewById(R.id.imag_kupon_bg2);
	    img_cha.setImageResource(R.drawable.col_cha4);
	    
	    //リスト用の配列
	    chara_name= new String[]{"図鑑×1","図鑑×3","図鑑×5","図鑑×10","図鑑×15","観光地図鑑\n"+"コンプリート","イベント図鑑\n"+"コンプリート","キャラ図鑑\n"+"コンプリート","全図鑑\n"+"コンプリート"};
	    chara_syousai= new String[]{"+100MOG","+500MOG","+750MOG","+1000MOG","+1500MOG","背景　昼","背景　夕","背景　夜","きよらスキーが･･･"};
	    tassei=new String[9];
	    img_check=new Bitmap[9]; 
	    count_gazou=new int[]{1,3,5,10,15};
	    
	    
	    //各画像獲得情報を読み込み
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
	    //累計獲得枚数の計算
	    goukei=kan+ibe+cha;
	    
	    
	    List<CustomData> objects = new ArrayList<CustomData>();
        CustomAdapter customAdapater = new CustomAdapter(this, 0, objects);
	    
        //獲得状況の画像を表示
        for(int i=0;i<5;i++){
	        if(goukei<count_gazou[i]){
	    		int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
	    		img_check[i]=BitmapFactory.decodeResource(getResources(), crd);
	    		tassei[i]="未達成";
	    		chara_syousai[i]="？？？？";
	    	}
	    	else if(goukei>=count_gazou[i]){
	    		int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
	    		img_check[i]=BitmapFactory.decodeResource(getResources(), crd);
	    		tassei[i]="達成";
	    	}
        }
        //図鑑をコンプリートしてるかどうか
        if(kan<10){
        	int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[5]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[5]="未達成";
    		chara_syousai[5]="？？？？";
        }
        else if(kan>=10){
        	int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[5]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[5]="達成";
        }
        if(ibe<12){
        	int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[6]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[6]="未達成";
    		chara_syousai[6]="？？？？";
        }
        else if(ibe>=12){
        	int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[6]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[6]="達成";
        }
        if(cha<11){
        	int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[7]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[7]="未達成";
    		chara_syousai[7]="？？？？";
        }
        else if(cha>=11){
        	int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[7]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[7]="達成";
        }
        if(goukei<33){
        	int crd = getResources().getIdentifier("min_osuwari_naki", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[8]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[8]="未達成";
    		chara_syousai[8]="？？？？";
        }
        else if(goukei>=33){
        	int crd = getResources().getIdentifier("min_yorokobi", "drawable", "jp.qr.java_conf.mogpuk");
    		img_check[8]=BitmapFactory.decodeResource(getResources(), crd);
    		tassei[8]="達成";
        }
        
        
        for(int i = 0; i < chara_name.length ; i++){
            //リストに格納するアイテムを１つずつ作成
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