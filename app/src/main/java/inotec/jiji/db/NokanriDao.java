package inotec.jiji.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * tb_nokanri用データアクセスクラス
 */
public class NokanriDao {

	private DatabaseOpenHelper helper = null;

	public NokanriDao(Context context) {
		helper = new DatabaseOpenHelper(context);
	}

	//2021.04.13 [add] start
	// tb_nokanriの保存
	public void Save(Nokanri nokanri) {
		String sql;
		Long count;
	    SQLiteStatement stmt = null;

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor;

		db.beginTransaction();
		try {

			// tb_nokanriの保存
			sql = "select count(*) from tb_nokanri";
			sql += " where item_id = '" +  nokanri.getItem_id() + "'";
			cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast())
			{
				count = cursor.getLong(0);
			}
			else
			{
				count = 0L;
			}
			if (count == 0L) {
				stmt = db.compileStatement("insert into tb_nokanri values (?,?);");
				stmt.bindString(1, nokanri.getItem_id());
				stmt.bindLong(2, nokanri.getStart_no());

				stmt.executeInsert();
			}
			else
			{
	    		stmt = db.compileStatement("update tb_nokanri set start_no = ? where item_id = '" +  nokanri.getItem_id() + "';");
	    		stmt.bindLong(1, nokanri.getStart_no());

				stmt.executeInsert();
			}

		    db.setTransactionSuccessful();
		} finally {
		    db.endTransaction();
		}

	}
	//2021.04.13 [add] end
	/**
	 * tb_nokanriの保存
	 * @param tb_nokanri 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Nokanri insert( Nokanri nokanri){
		SQLiteDatabase db = helper.getWritableDatabase();
		Nokanri result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Nokanri.COLUMN_ITEM_ID, nokanri.getItem_id());
			values.put( Nokanri.COLUMN_START_NO, nokanri.getStart_no());

			db.insert( Nokanri.TABLE_NAME, null, values);

		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * tb_nokanriの保存
	 * @param tb_nokanri 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Nokanri update( Nokanri nokanri){
		SQLiteDatabase db = helper.getWritableDatabase();
		Nokanri result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Nokanri.COLUMN_ITEM_ID, nokanri.getItem_id());
			values.put( Nokanri.COLUMN_START_NO, nokanri.getStart_no());

			db.update( Nokanri.TABLE_NAME, values, Nokanri.COLUMN_ITEM_ID + "=?", new String[]{ String.valueOf( nokanri.getItem_id())});

		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * レコードの削除
	 * @param tb_nokanri 削除対象のオブジェクト
	 */
	public void delete(Nokanri nokanri) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Nokanri.TABLE_NAME, Nokanri.COLUMN_ITEM_ID + "=?", new String[]{ String.valueOf( nokanri.getItem_id())});
		} finally {
			db.close();
		}
	}

	/**
	 * 全レコードの削除
	 * @param tb_nokanri 削除対象のオブジェクト
	 */
	public void all_delete() {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Nokanri.TABLE_NAME, null, null);
		} finally {
			db.close();
		}
	}

	/**
	 * idでtb_nokanriをロードする
	 * @param item_id PK
	 * @return ロード結果
	 */
	public Nokanri load(String item_id) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Nokanri nokanri = null;
		nokanri = clearNokanri();
		try {
			Cursor cursor = db.query( Nokanri.TABLE_NAME, null, Nokanri.COLUMN_ITEM_ID + "=?", new String[]{ String.valueOf( item_id)}, null, null, null);
			cursor.moveToFirst();
			if( !cursor.isAfterLast()){
				nokanri = getNokanri( cursor);
			}
		} finally {
			db.close();
		}
		return nokanri;
	}

	/**
	 * 一覧を取得する
	 * @return 検索結果
	 */
	public List<Nokanri> list() {
		SQLiteDatabase db = helper.getReadableDatabase();

		List<Nokanri> NokanriList;
		try {
			Cursor cursor = db.query( Nokanri.TABLE_NAME, null, null, null, null, null, Nokanri.COLUMN_ITEM_ID);
			NokanriList = new ArrayList<Nokanri>();
			cursor.moveToFirst();
			while( !cursor.isAfterLast()){
				NokanriList.add( getNokanri( cursor));
				cursor.moveToNext();
			}
		} finally {
			db.close();
		}
		return NokanriList;
	}

	/**
	 * カーソルからオブジェクトへの変換
	 * @param cursor カーソル
	 * @return 変換結果
	 */
	private Nokanri getNokanri( Cursor cursor){
		Nokanri nokanri = new Nokanri();

		nokanri.setItem_id( cursor.getString(0));
		nokanri.setStart_no( cursor.getLong(1));
		return nokanri;
	}
	/**
	 * 初期化
	 * @return 変換結果
	 */
	public Nokanri clearNokanri() {
		Nokanri nokanri = new Nokanri();

		nokanri.setStart_no(0L);
		return nokanri;
	}
}
