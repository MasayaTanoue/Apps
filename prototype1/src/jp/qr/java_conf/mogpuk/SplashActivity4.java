package jp.qr.java_conf.mogpuk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity4 extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	 // �^�C�g�����\���ɂ��܂��B
	 		requestWindowFeature(Window.FEATURE_NO_TITLE);
	 		// splash.xml��View�Ɏw�肵�܂��B
	 		setContentView(R.layout.splash4);
	 		ImageView img = (ImageView)findViewById(R.id.imag_splash);
	 		Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
	 	    img.startAnimation(anim);
	 		Handler hdl = new Handler();
	 		// 500ms�x��������splashHandler�����s���܂��B
	 		hdl.postDelayed(new splashHandler(), 2000);
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
	 			Intent intent = new Intent(getApplication(), SplashActivity2.class);
	 			startActivity(intent);
	 			// SplashActivity���I�������܂��B
	 			SplashActivity4.this.finish();
	 		}
	 	}

}
