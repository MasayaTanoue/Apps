package jp.qr.java_conf.mogpuk;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class FileLoader extends AsyncTask<String, Integer, String> {

    private CallBackFile callbackjson;
    private String mode = "none";
    private List<NameValuePair> _nameValuePair;
    private Activity mActivity;
	 private Dialog dialog;
	 AnimationDrawable animation;

    // コンストラクタ
    public FileLoader() {
        mode = "none";
    }
    
    public FileLoader(Activity act) {
    	
   	     mActivity = act;

    }

    public FileLoader(Activity act,List<NameValuePair> nameValuePair) {
        mode = "post";
        _nameValuePair = nameValuePair;
   	     mActivity = act;

    }

    @Override
    protected void onPreExecute() {
    	dialog = new Dialog(mActivity,R.style.MyProgressTheme);
		dialog.setTitle("Now Loading...");
		dialog.setContentView(R.layout.progress_dialog);
		dialog.setCancelable(false);
		dialog.show();
		
		//ダイアログ内に表示するアニメーション
		ImageView iv = (ImageView)dialog.findViewById(R.id.image);
		iv.setBackgroundResource(R.anim.dialog_anime);
		animation = (AnimationDrawable)iv.getBackground();
		animation.start();	
    }

    @Override
    protected String doInBackground(String... params) {

        if (mode.equals("post")) {
            return getDataPost(params[0]);
        } else {
            return getData(params[0]);
        }
    }

    public void setOnCallBack(CallBackFile _cbj) {
        callbackjson = _cbj;
    }

    @Override
    protected void onPostExecute(String result) {
    	 animation.stop();
		 dialog.dismiss();
		 dialog = null;
        callbackjson.CallBack(result);
        callbackjson =null;
    }

    public String getData(String _url) {
        DefaultHttpClient objHttp = new DefaultHttpClient();
        HttpParams params = objHttp.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 3000);
        HttpConnectionParams.setSoTimeout(params, 3000);
        String _return = "";
        try {
            HttpGet objGet = new HttpGet(_url);
            HttpResponse objResponse = objHttp.execute(objGet);
            if (objResponse.getStatusLine().getStatusCode() < 400) {
                InputStream objStream = objResponse.getEntity().getContent();
                InputStreamReader objReader = new InputStreamReader(objStream);
                BufferedReader objBuf = new BufferedReader(objReader);
                StringBuilder objJson = new StringBuilder();
                String sLine;
                while ((sLine = objBuf.readLine()) != null) {
                    objJson.append(sLine);
                }
                _return = objJson.toString();
                objStream.close();
            }
        } catch (IOException e) {
            return "";
        }
        return _return;
    }

    public String getDataPost(String sUrl) {
        String sReturn = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(sUrl);
        try {
            httppost.setEntity(new UrlEncodedFormEntity(_nameValuePair));
            HttpResponse response = httpclient.execute(httppost);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            response.getEntity().writeTo(byteArrayOutputStream);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                sReturn = byteArrayOutputStream.toString();
            } else {
                return "";
            }
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
        return sReturn;
    }

    public static class CallBackFile {
        public void CallBack(String Result) {

        }
    }
}