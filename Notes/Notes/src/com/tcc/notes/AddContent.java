package com.tcc.notes;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

public class AddContent extends Activity implements OnClickListener {

	private String flag;
	private Button btnSave, btnDelete;
	private ImageView imgImageView;
	private VideoView videoView;
	private EditText editText;
	private NotesDB notesDB;
	private SQLiteDatabase dbWriter;
	private File imgFile;
	private File videoFile;
	private int REQUEST_CODE = 998;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_content);

		flag = getIntent().getStringExtra("flag");

		btnSave = (Button) findViewById(R.id.save);
		btnDelete = (Button) findViewById(R.id.delet);

		imgImageView = (ImageView) findViewById(R.id.cImg);
		videoView = (VideoView) findViewById(R.id.cVideo);
		editText = (EditText) findViewById(R.id.cEditText);

		btnDelete.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		notesDB = new NotesDB(this);
		dbWriter = notesDB.getWritableDatabase();
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		videoFile = new File(Environment.getExternalStorageDirectory()
				.getAbsoluteFile() + "/" + getTime() + ".mp4");
		imgFile = new File(Environment.getExternalStorageDirectory()
				.getAbsoluteFile() + "/" + getTime() + ".jpg");
		if (flag.equals("1")) {
			imgImageView.setVisibility(View.GONE);
			videoView.setVisibility(View.GONE);
		}
		if (flag.equals("2")) {
			imgImageView.setVisibility(View.VISIBLE);
			videoView.setVisibility(View.GONE);
			startPicture();
		}
		if (flag.equals("3")) {
			imgImageView.setVisibility(View.GONE);
			videoView.setVisibility(View.VISIBLE);
			startVideo();
		}

	}

	private void startVideo() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));
		startActivityForResult(intent, 2);

	}

	private void startPicture() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
		startActivityForResult(intent, REQUEST_CODE);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.save:
			addDb();
			finish();
			break;
		case R.id.delet:
			finish();
			break;
		default:
			break;
		}

	}

	public void addDb() {
		ContentValues values = new ContentValues();
		values.put(NotesDB.CONTENT, editText.getText().toString());
		values.put(NotesDB.TIME, getTime());
		if (imgFile.getAbsoluteFile() != null) {
			values.put(NotesDB.PATH, imgFile.getAbsolutePath());
		}
		if (videoFile.getAbsoluteFile() != null) {
			values.put(NotesDB.VEDIO, videoFile.getAbsolutePath());
		}
		dbWriter.insert(NotesDB.TABLE_NAME, null, values);
	}

	private String getTime() {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String str = format.format(date);
		return str;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
			Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			imgImageView.setImageBitmap(bitmap);
		}
		if (requestCode == 2) {
			videoView.setVideoURI(Uri.fromFile(videoFile));
			videoView.start();
		}
	}
}
