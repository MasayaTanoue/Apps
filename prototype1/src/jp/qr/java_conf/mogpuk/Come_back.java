package jp.qr.java_conf.mogpuk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;

public class Come_back extends Activity {
	
	Handler hdl;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	 // �^�C�g�����\���ɂ��܂��B
	 		requestWindowFeature(Window.FEATURE_NO_TITLE);
	 		// splash.xml��View�Ɏw�肵�܂��B
	 		setContentView(R.layout.come_back);
	 		hdl = new Handler();
	 		// 500ms�x��������splashHandler�����s���܂��B
	 		hdl.postDelayed(new splashHandler(), 800);
	 	}
	@Override
	protected  void onResume(){
		super.onResume();
		
	}
	protected  void onPause(){
		super.onPause();
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
	 	class splashHandler implements Runnable {
	 		public void run() {
	 			// �X�v���b�V��������Ɏ��s����Activity���w�肵�܂��B
	 			Intent intent = new Intent(getApplication(), Result.class);
	 			startActivity(intent);
	 			// SplashActivity���I�������܂��B
	 			Come_back.this.finish();
	 		}
	 	}

}
