package com.tcc.notes;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

public class DetailActivity extends Activity implements OnClickListener {

	private ImageView contentImg;
	private VideoView contentVideo;
	private TextView contentTextView;
	private Button btnDelete;
	private Button btnSave;
	private NotesDB notesDB;
	private SQLiteDatabase writerDatabase;
	private int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		notesDB = new NotesDB(this);
		writerDatabase = notesDB.getWritableDatabase();
		

		initView();
		initWidget();

	}

	private void initWidget() {
		// TODO Auto-generated method stub
		Intent detailIntent = getIntent();
		String imgPath = detailIntent.getStringExtra(NotesDB.PATH);
		String videoPath = detailIntent.getStringExtra(NotesDB.VEDIO);
		String contentText = detailIntent.getStringExtra(NotesDB.CONTENT);
		String time = detailIntent.getStringExtra(NotesDB.TIME);
		Log.i("HHHHHH", imgPath+videoPath+contentText);
		id = detailIntent.getIntExtra(NotesDB.ID, 0);
		if (imgPath != null) {
			contentImg.setVisibility(View.VISIBLE);
			Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
			contentImg.setImageBitmap(bitmap);
		} else {
			contentImg.setVisibility(View.GONE);
		}
		if (videoPath != null) {
			contentVideo.setVisibility(View.VISIBLE);

			contentVideo.setVideoURI(Uri.parse(videoPath));
			contentVideo.start();
		} else {
			contentImg.setVisibility(View.GONE);
		}
		contentTextView.setText(contentText);

	}

	private void initView() {
		contentImg = (ImageView) findViewById(R.id.content_img);
		contentVideo = (VideoView) findViewById(R.id.content_video);
		contentTextView = (TextView) findViewById(R.id.content_text);
		btnDelete = (Button) findViewById(R.id.content_btn_delete);
		btnSave = (Button) findViewById(R.id.content_btn_save);
		btnDelete.setOnClickListener(this);
		btnSave.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.content_btn_delete:
			deleteData();

			break;
		case R.id.content_btn_save:
			
			break;

		default:
			break;
		}
	}

	private void deleteData() {
		// TODO Auto-generated method stub
		
		writerDatabase.delete(NotesDB.TABLE_NAME, "_id="+id, null);
		
	}
}
