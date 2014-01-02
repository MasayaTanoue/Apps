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

class BindData2 {
	  String text;
	  String text2;
	  Bitmap imageResourceId;

	  public BindData2(String text,String text2, Bitmap id) {
	    this.text = text;
	    this.text2 = text2;
	    this.imageResourceId = id;
	  }
	}

	class ViewHolder2 {
	  TextView textView;
	  TextView textView2;
	  ImageView imageView;
	}

	public class ShopAdapter2 extends ArrayAdapter<BindData2> {
	  private LayoutInflater inflater;

	  public ShopAdapter2(Context context, List<BindData2> objects) {
	    super(context, 0, objects);
	    this.inflater = (LayoutInflater) context
	      .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    ViewHolder2 holder;
	    if (convertView == null) {
	      convertView = inflater.inflate(R.layout.list_layout_shop2,
	        parent, false);
	      holder = new ViewHolder2();
	      holder.textView = (TextView) convertView
	        .findViewById(R.id.te1);
	      holder.textView2 = (TextView) convertView
	  	        .findViewById(R.id.textview2);
	      holder.imageView = (ImageView) convertView
	        .findViewById(R.id.imageview);
	      convertView.setTag(holder);
	    }
	    else {
	      holder = (ViewHolder2) convertView.getTag();
	    }

	    BindData2 data = getItem(position);
	    holder.textView.setText(data.text);
	    holder.textView2.setText(data.text2);
	    holder.imageView.setImageBitmap(data.imageResourceId);
	    return convertView;
	  }
}
