package com.tcc.notes;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private Cursor cursor;
	private View view;

	public MyAdapter(Context context, Cursor cursor) {
		this.context = context;
		this.cursor = cursor;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public Bitmap getImageThumNail(String url, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(url, options);
		options.inJustDecodeBounds = false;
		int beWidth = options.outWidth / width;
		int beHeight = options.outHeight / height;
		int be = 1;
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (be < 0) {
			be = 1;
		}

		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(url, options);

		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

		return bitmap;
	}

	public Bitmap getVideoThumNail(String url, int width, int height, int kind) {
		Bitmap bitmap = null;
		bitmap = ThumbnailUtils.createVideoThumbnail(url, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

		return bitmap;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView != null) {
			view = convertView;
		} else {
			view = (LinearLayout) LayoutInflater.from(context).inflate(
					R.layout.list_item, parent, false);
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		if (holder == null) {
			holder = new ViewHolder();
			holder.imgView = (ImageView) view.findViewById(R.id.item_img);
			holder.videoView = (ImageView) view.findViewById(R.id.item_video);
			holder.textView = (TextView) view.findViewById(R.id.item_text);
			holder.timeText = (TextView) view.findViewById(R.id.item_time);
			view.setTag(holder);
		}
		cursor.moveToPosition(position);
		String contentString = cursor.getString(cursor
				.getColumnIndex("content"));
		String timeString = cursor.getString(cursor.getColumnIndex("time"));
		String url = cursor.getString(cursor.getColumnIndex("path"));
		String videoUrl = cursor.getString(cursor.getColumnIndex("vidio"));

		holder.textView.setText(contentString);
		holder.timeText.setText(timeString);
		holder.imgView.setImageBitmap(getImageThumNail(url, 200, 200));
		holder.videoView.setImageBitmap(getVideoThumNail(videoUrl, 200, 200,
				MediaStore.Images.Thumbnails.MICRO_KIND));

		return view;
	}

	private class ViewHolder {
		ImageView imgView;
		ImageView videoView;
		TextView textView;
		TextView timeText;

	}

}
