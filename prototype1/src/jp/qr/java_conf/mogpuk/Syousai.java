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
    Boolean[] bool_tgl;//トグルボタンのenable
    Boolean[] bool_tgl2;//トグルボタンのcheck
    int[] time_game;
    int[] tgl_status;
    Bitmap[] img_check;
    Button btn_now_result;
    int st;
    RelativeLayout rl;
    int kouken,kaisuu;
    int[] kiyora_kouken;
    String[] kiyora_syurui= {"フタカワきよらスキー","押し戸きよらスキー","浴衣きよらスキー","成金きよらスキー"};
    String[] kiyora_sp_setu={"もっとお手伝いするラヨ・・・","もっと訓練が必要ラヨ!!","お手伝いにも慣れてきたラヨ!!","たくさん頑張ったラヨ!!","図鑑コンプリートおめでとうラヨ!!"};
    String[] kouka_syoukai= {"ゲーム開始時の体力が＋15されるラヨ!!","ゲーム開始時の魅力が＋15されるラヨ!!","ゲーム開始時の運が＋15されるラヨ!!",
    		"ゲーム時の獲得MOGがUPするラヨ!!","お手伝い終了時間が早くなるラヨ!!",
    		"ゲーム開始時の体力が＋30されるラヨ!!","ゲーム開始時の魅力が＋30されるラヨ!!","ゲーム開始時の運が＋30されるラヨ!!",
    		"クーポン交換時の使用MOGが1/2になるラヨ!!"};
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
		 // 特定の行(position)のデータを得る
		 CustomData item = (CustomData)getItem(position);
		 
		 // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
		 if (null == convertView) {
		 convertView = layoutInflater_.inflate(R.layout.list_layout_bonus, null);
		 }
		 
		 // CustomDataのデータをViewの各Widgetにセットする
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
	    
	    
	  //図鑑コンプリート読み込み
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
	   

	    rl=(RelativeLayout)findViewById(R.id.RelativeLayout1);
	    rl.setBackgroundResource(R.drawable.bg_repeat_man);
	    
	  //上部表示キャラの変更
	    TextView txt_cha_vo=(TextView)findViewById(R.id.Text_syousai_kiyo);
	    txt_cha_vo.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    
	    //ボタンのID認識
	    btn_now_result=(Button)findViewById(R.id.btn_syousai_result);
	    btn_now_result.setOnClickListener(this);
	    
	    //リスト用の配列
	    chara_name= new String[]{"1回","5回","10回","15回","20回","40回","60回","80回","100回"};
	    chara_syousai= new String[]{"おしとい氏の鬼のお手玉","セン子氏の礼儀作法","タキの気遣い","赤ばばぁの知恵","孫のお手伝い","きよら父さんの力","メオの写真","マンガンじいのお守り","きよら母さんの買い物術"};
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
	    
	    //ゲーム回数，交換回数を読み込み
	    SharedPreferences po0 = getSharedPreferences("time", MODE_PRIVATE);
        game = po0.getInt("game", 0);
	    SharedPreferences po1 = getSharedPreferences("name", MODE_PRIVATE);
        koukan = po1.getInt("count", 0);
        SharedPreferences po2 = getSharedPreferences("name", MODE_PRIVATE);
        kaisuu = po2.getInt("kaisuu", 0);
        
        //ダイアログ表示用の貢献度の計算
        kouken= game*2+kaisuu*3;
        if(kouken>500){
        	kouken=500;
        }
        
        //貢献度に応じた画像の変更処理
        kiyora_kouken=new int[4];
        for(int i = 0; i<kiyora_kouken.length;i++){
        	 kiyora_kouken[i] = getResources().getIdentifier("sp"+i, "drawable", "jp.qr.java_conf.mogpuk");

	        }
        
	    
        //tglbtnの設定の読み込み
        //tglbtnの状態を反映（0=false,1=true）
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
	    	//画像リソースを順に配列に格納
        	if(game<time_game[i]){
        		bool_tgl[i]=false;
        		chara_syousai[i]="？？？？";
        	}
        	else if(game>=time_game[i]){
        		bool_tgl[i]=true;
        	}
            //リストに格納するアイテムを１つずつ作成
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

            //リスト項目クリック時の処理
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
    		        	
    		        	//イベントの詳細を表示するダイアログの作成
    		       		dialog = new Dialog(Syousai.this);
    		       		//タイトルの非表示
    		       		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    		       		//フルスクリーン
    		       		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
    		       		//dialog_result.xmlを基にしてダイアログを作成
    		       		dialog.setContentView(R.layout.dialog_result);
    		       		//背景を透明にする
    		       		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    		       		dialog.show();
    		       		
    		       		//ダイアログに表示するテキストのID登録と表示命令
    		       		TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
    		       		d_result_title.setText("「ON」にしたときの効果");
    		       		TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
    		       		dialog_titile.setText("「"+chara_syousai[position]+"」");
    		       		ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
    		       		image.setImageResource(chara_img[position]);
    		       		TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
    		       		dialog_setumei.setText(kouka_syoukai[position]);
    		       		TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
    		       		dialog_close.setText("×");
    		       		
    		       		//ダイアログの「×」が押された時の処理
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
    	    	    	//画像リソースを順に配列に格納
    	            	if(game<time_game[i]){
    	            		bool_tgl[i]=false;
    	            		chara_syousai[i]="？？？？";
    	            	}
    	            	else if(game>=time_game[i]){
    	            		bool_tgl[i]=true;
    	            	}
    	                //リストに格納するアイテムを１つずつ作成
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
		// TODO 自動生成されたメソッド・スタブ
		dialog = new Dialog(Syousai.this);
   		//タイトルの非表示
   		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
   		//フルスクリーン
   		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
   		//dialog_result.xmlを基にしてダイアログを作成
   		dialog.setContentView(R.layout.dialog_result_goukei);
   		//背景を透明にする
   		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
   		dialog.show();
   		
   		//ダイアログに表示するテキストのID登録と表示命令
   		TextView d_result_title=(TextView)dialog.findViewById(R.id.d_result_title);
   		d_result_title.setText("きよらスキーの評価");
   		TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
   		TextView dialog_sp=(TextView)dialog.findViewById(R.id.text_sp);
   		TextView dialog_kaisu=(TextView)dialog.findViewById(R.id.txt_result_dialog_kaisu_p);
   		dialog_kaisu.setText(""+game+"回");
   		TextView dialog_koukan=(TextView)dialog.findViewById(R.id.txt_result_dialog_koukan_p);
   		dialog_koukan.setText(""+kaisuu+"回");
   		ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
   		for(int i = 0; i<kiyora_kouken.length;i++){
       	 if(kouken>=kouken_lim[i]){
       		 dialog_sp.setText(kiyora_sp_setu[i]);
       		 image.setImageResource(kiyora_kouken[i]);
       		 dialog_titile.setText(""+kiyora_syurui[i]);
       	 	}
         //図鑑をコンプリートしてるかどうか
         if(goukei>=33){
        	 dialog_sp.setText(kiyora_sp_setu[4]);
        	 image.setImageResource(R.drawable.sp4);
       		 dialog_titile.setText("筋肉きよらスキー");
         }
	    }
   		TextView dialog_close=(TextView)dialog.findViewById(R.id.d_result_close_button);
   		dialog_close.setText("×");
   		
   		TextView dialog_game=(TextView)dialog.findViewById(R.id.txt_result_dialog_game_p);
   		dialog_game.setText(""+kouken+"pt");
   		
   		
   		
   		//ダイアログの「×」が押された時の処理
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
