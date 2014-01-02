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
	 // タイトルを非表示にします。
	 		requestWindowFeature(Window.FEATURE_NO_TITLE);
	 		// splash.xmlをViewに指定します。
	 		setContentView(R.layout.come_back);
	 		hdl = new Handler();
	 		// 500ms遅延させてsplashHandlerを実行します。
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
	 			Intent intent = new Intent(getApplication(), Result.class);
	 			startActivity(intent);
	 			// SplashActivityを終了させます。
	 			Come_back.this.finish();
	 		}
	 	}

}
