package jp.qr.java_conf.mogpuk;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

class BindData {
	  String text;
	  Bitmap imageResourceId;

	  public BindData(String text, Bitmap id) {
	    this.text = text;
	    this.imageResourceId = id;
	  }
	}

	class ViewHolder {
	  TextView textView;
	  ImageView imageView;
	}

	public class ShopAdapter extends ArrayAdapter<BindData> {
	  private LayoutInflater inflater;

	  public ShopAdapter(Context context, List<BindData> objects) {
	    super(context, 0, objects);
	    this.inflater = (LayoutInflater) context
	      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  }
	  

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		
	    ViewHolder holder;
	    if (convertView == null) {
	      convertView = inflater.inflate(R.layout.list_layout_shop,
	        parent, false);
	      holder = new ViewHolder();
	      holder.textView = (TextView) convertView
	        .findViewById(R.id.textview2);
	      
	      
	      holder.imageView = (ImageView) convertView
	        .findViewById(R.id.imageview);
	      convertView.setTag(holder);
	    }
	    else {
	      holder = (ViewHolder) convertView.getTag();
	    }

	    BindData data = getItem(position);
	    holder.textView.setText(data.text);
	    holder.imageView.setImageBitmap(data.imageResourceId);
	    return convertView;
	  }
}
