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
import android.widget.TextView;

public class SplashActivity3 extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	 // タイトルを非表示にします。
	 		requestWindowFeature(Window.FEATURE_NO_TITLE);
	 		// splash.xmlをViewに指定します。
	 		setContentView(R.layout.splash3);
	 		TextView txt =(TextView)findViewById(R.id.textView1);
	 		TextView txt2 =(TextView)findViewById(R.id.textView2);
	 		ImageView img = (ImageView)findViewById(R.id.imag_splash);
	 		Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
	 		txt.startAnimation(anim);
	 		txt2.startAnimation(anim);
	 	    img.startAnimation(anim);
	 		Handler hdl = new Handler();
	 		// 500ms遅延させてsplashHandlerを実行します。
	 		hdl.postDelayed(new splashHandler(), 2000);
	 	}
	
	public boolean dispatchKeyEvent(KeyEvent event) {
	    if (event.getAction()==KeyEvent.ACTION_DOWN) {
	        switch (event.getKeyCode()) {
	        case KeyEvent.KEYCODE_BACK:
	            // ダイアログ表示など特定の処理を行いたい場合はここに記述
	            // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返す
	            return true;
	        }
	    }
	    return super.dispatchKeyEvent(event);
	}
	 	class splashHandler implements Runnable {
	 		public void run() {
	 			// スプラッシュ完了後に実行するActivityを指定します。
	 			Intent intent = new Intent(getApplication(), SplashActivity4.class);
	 			startActivity(intent);
	 			// SplashActivityを終了させます。
	 			SplashActivity3.this.finish();
	 		}
	 	}

}
