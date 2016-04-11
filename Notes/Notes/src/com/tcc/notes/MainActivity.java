package com.tcc.notes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener, OnItemClickListener {

	private Button btnText, btnImg, btnVideo;
	private Intent intent;
	private ListView listView;
	private NotesDB notesDB;
	private SQLiteDatabase dbReaderDatabase;
	private Cursor cursor;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		notesDB = new NotesDB(this);
		dbReaderDatabase = notesDB.getWritableDatabase();
		// TODO Auto-generated method stub
		btnText = (Button) findViewById(R.id.text);
		btnImg = (Button) findViewById(R.id.img);
		btnVideo = (Button) findViewById(R.id.video);
		listView = (ListView) findViewById(R.id.list_view);

		btnText.setOnClickListener(this);
		btnImg.setOnClickListener(this);
		btnVideo.setOnClickListener(this);
		listView.setOnItemClickListener(this);
		Toast.makeText(this, getDatabasePath("note").toString(), Toast.LENGTH_LONG).show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		int id = v.getId();
		intent = new Intent(MainActivity.this, AddContent.class);
		switch (id) {
		case R.id.text:
			intent.putExtra("flag", "1");

			startActivity(intent);
			break;
		case R.id.img:
			intent.putExtra("flag","2");
			startActivity(intent);

			break;
		case R.id.video:
			intent.putExtra("flag", "3");
			startActivity(intent);

			break;

		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		selectDb();
	}

	private void selectDb() {
		cursor = dbReaderDatabase.query(NotesDB.TABLE_NAME, null, null,
				null, null, null, null);
		MyAdapter adapter = new MyAdapter(this, cursor);
		listView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
		cursor.moveToPosition(position);
		Intent intent = new Intent(MainActivity.this,DetailActivity.class);
		intent.putExtra(NotesDB.ID, cursor.getColumnIndex(NotesDB.ID));
		intent.putExtra(NotesDB.CONTENT, cursor.getColumnIndex(NotesDB.CONTENT));
		intent.putExtra(NotesDB.PATH, cursor.getColumnIndex(NotesDB.PATH));
		intent.putExtra(NotesDB.VEDIO, cursor.getColumnIndex(NotesDB.VEDIO));
		intent.putExtra(NotesDB.TIME, cursor.getColumnIndex(NotesDB.TIME));
		startActivity(intent);
	}

}
