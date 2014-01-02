package jp.qr.java_conf.mogpuk;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class stage_select extends Activity implements OnClickListener {
	
	Dialog dialog;
	Bitmap[] stage_image =new Bitmap[3];
	String[] stage_mei = {"����G���A","���莛�G���A","�Ԕn��G���A"};
	ImageButton bt_st0,bt_st1,bt_st2;
	int kaisuu;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.stage_game);
	    
	    //�{�^����ID�o�^
	    bt_st0=(ImageButton)findViewById(R.id.bt_st0);
	    bt_st1=(ImageButton)findViewById(R.id.bt_st1);
	    bt_st2=(ImageButton)findViewById(R.id.bt_st2);
	    bt_st0.setOnClickListener(this);
	    bt_st1.setOnClickListener(this);
	    bt_st2.setOnClickListener(this);
	    TextView txt=(TextView)findViewById(R.id.textView2);
	    txt.setTypeface(Typeface.createFromAsset(getAssets(), "AZUKI.TTF"));
	    
	    
	    //�݌v���������̓ǂݍ���
	    SharedPreferences po3 = getSharedPreferences("name", MODE_PRIVATE);
        kaisuu = po3.getInt("kaisuu", 0);

        
	    }
	protected  void onPause(){
		super.onPause();
	}
	@Override
	public void onClick(View v) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		if(v==bt_st0){
			dialog = new Dialog(stage_select.this);
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
			d_message.setText("�����ꂳ���\n"+"���ق�����`�����郉���`�H");

			//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
			dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
				 @Override
		            public void onClick(View v) {
					 Intent intent = new Intent(stage_select.this, Game.class);
					 startActivity(intent);
				 }
				});
			dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
				 @Override
		            public void onClick(View v) {
					 dialog.dismiss();
				 }
				});
    	   }
    	   else if(v==bt_st2){
    		   if(kaisuu<1){
        		   dialog = new Dialog(stage_select.this);
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
					d_result_title.setText("�m�F");
					TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
					dialog_titile.setText("�}���K�������̉���");
					ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
					image.setImageResource(R.drawable.col_cha3);
					TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
					dialog_setumei.setText("�N�[�|����\n"+"����"+(1-kaisuu)+"���������� \n"+"�V�ׂ�悤�ɂȂ郉���[");
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
        		   else if(kaisuu>=1){
        			   dialog = new Dialog(stage_select.this);
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
        				d_message.setText("�}���K�������̉����\n"+"����`�����郉���`�H");

        				//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
        				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
        					 @Override
        			            public void onClick(View v) {
        						 Intent intent = new Intent(stage_select.this, Game2.class);
        							startActivity(intent);
        					 }
        					});
        				dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
        					 @Override
        			            public void onClick(View v) {
        						 dialog.dismiss();
        					 }
        					});
						
					}
    	   }
    	   else if(v==bt_st1){
    		   if(kaisuu<3){
        		   dialog = new Dialog(stage_select.this);
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
					d_result_title.setText("�m�F");
					TextView dialog_titile=(TextView)dialog.findViewById(R.id.txt_result_dialog_titile);
					dialog_titile.setText("�Ԃ΂΂��̂�����������");
					ImageView image = (ImageView)dialog.findViewById(R.id.imag_getcol);
					image.setImageResource(R.drawable.col_cha5);
					TextView dialog_setumei=(TextView)dialog.findViewById(R.id.txt_result_dialog_setumei);
					dialog_setumei.setText("�N�[�|����\n"+"����"+(3-kaisuu)+"���������� \n"+"�V�ׂ�悤�ɂȂ郉���[");

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
        		   else if(kaisuu>=3){
        			   dialog = new Dialog(stage_select.this);
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
        				d_message.setText("�Ԃ΂΂��̂������������\n"+"����`�����郉���`�H");

        				//�_�C�A���O�́uOK�v�{�^���������ꂽ���̏���
        				dialog.findViewById(R.id.d_button).setOnClickListener(new View.OnClickListener() {
        					 @Override
        			            public void onClick(View v) {
        						 Intent intent = new Intent(stage_select.this, Game3.class);
        							startActivity(intent);
        					 }
        					});
        				dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
        					 @Override
        			            public void onClick(View v) {
        						 dialog.dismiss();
        					 }
        					});
						
					}
    	   }
	}
}
