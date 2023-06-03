package inotec.jiji.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * tb_dengon用データアクセスクラス
 */
public class DengonDao {

	private DatabaseOpenHelper helper = null;

	public DengonDao(Context context) {
		helper = new DatabaseOpenHelper(context);
	}

	/**
	 * tb_dengonの保存
	 * dengon_idがnullの場合はinsert、dengon_idが!nullの場合はupdate
	 * @param bizCard 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Dengon save( Dengon dengon){
		SQLiteDatabase db = helper.getWritableDatabase();
		Dengon result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Dengon.COLUMN_DENGON_NAME, dengon.getDengon_name());

			Long contact_id = dengon.getContact_id();
			// IDがnullの場合はinsert
			if( contact_id == null){
				contact_id = db.insert( Dengon.TABLE_NAME, null, values);
			}
			else{
				db.update( Dengon.TABLE_NAME, values, Dengon.COLUMN_CONTACT_ID + "=?", new String[]{ String.valueOf( contact_id)});
			}
			result = load( contact_id);
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * tb_dengonの保存
	 * @param tb_dengon 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Dengon insert( Dengon dengon){
		SQLiteDatabase db = helper.getWritableDatabase();
		Dengon result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Dengon.COLUMN_CONTACT_ID, dengon.getContact_id());
			values.put( Dengon.COLUMN_DENGON_NAME, dengon.getDengon_name());

			db.insert( Dengon.TABLE_NAME, null, values);

		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * レコードの削除
	 * @param dengon 削除対象のオブジェクト
	 */
	public void delete(Dengon dengon) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Dengon.TABLE_NAME, Dengon.COLUMN_CONTACT_ID + "=?", new String[]{ String.valueOf( dengon.getContact_id())});
		} finally {
			db.close();
		}
	}

	/**
	 * 全レコードの削除
	 * @param tb_dengon 削除対象のオブジェクト
	 */
	public void all_delete() {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Dengon.TABLE_NAME, null, null);
		} finally {
			db.close();
		}
	}

	/**
	 * idでtb_dengonをロードする
	 * @param dengon_id PK
	 * @return ロード結果
	 */
	public Dengon load(Long dengon_id) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Dengon dengon = null;
		dengon = clearDengon();
		try {
			Cursor cursor = db.query( Dengon.TABLE_NAME, null, Dengon.COLUMN_CONTACT_ID + "=?", new String[]{ String.valueOf( dengon_id)}, null, null, null);
			cursor.moveToFirst();
			if( !cursor.isAfterLast()){
				dengon = getDengon( cursor);
			}
		} finally {
			db.close();
		}
		return dengon;
	}

	/**
	 * 一覧を取得する
	 * @return 検索結果
	 */
	public List<Dengon> list() {
		SQLiteDatabase db = helper.getReadableDatabase();

		List<Dengon> DengonList;
		try {
			Cursor cursor = db.query( Dengon.TABLE_NAME, null, null, null, null, null, Dengon.COLUMN_CONTACT_ID);
			DengonList = new ArrayList<Dengon>();
			cursor.moveToFirst();
			while( !cursor.isAfterLast()){
				DengonList.add( getDengon( cursor));
				cursor.moveToNext();
			}
		} finally {
			db.close();
		}
		return DengonList;
	}

	/**
	 * カーソルからオブジェクトへの変換
	 * @param cursor カーソル
	 * @return 変換結果
	 */
	private Dengon getDengon( Cursor cursor){
		Dengon dengon = new Dengon();

		dengon.setContact_id( cursor.getLong(0));
		dengon.setDengon_name( cursor.getString(1));
		return dengon;
	}
	/**
	 * 初期化
	 * @return 変換結果
	 */
	public Dengon clearDengon() {
		Dengon dengon = new Dengon();

		dengon.setDengon_name("");
		return dengon;
	}
}
