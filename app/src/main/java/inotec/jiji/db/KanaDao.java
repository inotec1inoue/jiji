package inotec.jiji.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * tb_iro用データアクセスクラス
 */
public class KanaDao {

	private DatabaseOpenHelper helper = null;

	public KanaDao(Context context) {
		helper = new DatabaseOpenHelper(context);
	}

	/**
	 * tb_kanaの保存
	 * kana_idがnullの場合はinsert、kana_idが!nullの場合はupdate
	 * @param bizCard 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Kana save( Kana kana){
		SQLiteDatabase db = helper.getWritableDatabase();
		Kana result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Kana.COLUMN_KANA_NAME, kana.getKana_name());

			Long kana_id = kana.getKana_id();
			// IDがnullの場合はinsert
			if( kana_id == null){
				kana_id = db.insert( Kana.TABLE_NAME, null, values);
			}
			else{
				db.update( Kana.TABLE_NAME, values, Kana.COLUMN_KANA_ID + "=?", new String[]{ String.valueOf( kana_id)});
			}
			result = load( kana_id);
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * レコードの削除
	 * @param kana 削除対象のオブジェクト
	 */
	public void delete(Kana kana) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Kana.TABLE_NAME, Kana.COLUMN_KANA_ID + "=?", new String[]{ String.valueOf( kana.getKana_id())});
		} finally {
			db.close();
		}
	}

	/**
	 * 全レコードの削除
	 * @param tb_kana 削除対象のオブジェクト
	 */
	public void all_delete() {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Kana.TABLE_NAME, null, null);
		} finally {
			db.close();
		}
	}

	/**
	 * idでtb_kankyoをロードする
	 * @param kana_id PK
	 * @return ロード結果
	 */
	public Kana load(Long kana_id) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Kana kana = null;
		kana = clearKana();
		try {
			Cursor cursor = db.query( Kana.TABLE_NAME, null, Kana.COLUMN_KANA_ID + "=?", new String[]{ String.valueOf( kana_id)}, null, null, null);
			cursor.moveToFirst();
			if( !cursor.isAfterLast()){
				kana = getKana( cursor);
			}
		} finally {
			db.close();
		}
		return kana;
	}

	/**
	 * 一覧を取得する
	 * @return 検索結果
	 */
	public List<Kana> list() {
		SQLiteDatabase db = helper.getReadableDatabase();

		List<Kana> KanaList;
		try {
			Cursor cursor = db.query( Kana.TABLE_NAME, null, null, null, null, null, Kana.COLUMN_KANA_ID);
			KanaList = new ArrayList<Kana>();
			cursor.moveToFirst();
			while( !cursor.isAfterLast()){
				KanaList.add( getKana( cursor));
				cursor.moveToNext();
			}
		} finally {
			db.close();
		}
		return KanaList;
	}

	/**
	 * カーソルからオブジェクトへの変換
	 * @param cursor カーソル
	 * @return 変換結果
	 */
	private Kana getKana( Cursor cursor){
		Kana kana = new Kana();

		kana.setKana_id( cursor.getLong(0));
		kana.setKana_name( cursor.getString(1));
		kana.setKana_key( cursor.getString(2));
		return kana;
	}
	/**
	 * 初期化
	 * @return 変換結果
	 */
	public Kana clearKana() {
		Kana kana = new Kana();

		kana.setKana_name("");
		kana.setKana_key("");
		return kana;
	}
}
