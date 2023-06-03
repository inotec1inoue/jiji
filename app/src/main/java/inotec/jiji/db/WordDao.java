package inotec.jiji.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * tb_word用データアクセスクラス
 */
public class WordDao {

	private DatabaseOpenHelper helper = null;

	public WordDao(Context context) {
		helper = new DatabaseOpenHelper(context);
	}

	/**
	 * tb_wordの保存
	 * word_idがnullの場合はinsert、word_idが!nullの場合はupdate
	 * @param bizCard 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Word save( Word word){
		SQLiteDatabase db = helper.getWritableDatabase();
		Word result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Word.COLUMN_WORD_NAME, word.getWord_name());

			Long word_id = word.getWord_id();
			// IDがnullの場合はinsert
			if( word_id == null){
				word_id = db.insert( Word.TABLE_NAME, null, values);
			}
			else{
				db.update( Word.TABLE_NAME, values, Word.COLUMN_WORD_ID + "=?", new String[]{ String.valueOf( word_id)});
			}
			result = load( word_id);
		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * tb_wordの保存
	 * @param tb_word 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Word insert( Word word){
		SQLiteDatabase db = helper.getWritableDatabase();
		Word result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Word.COLUMN_WORD_ID, word.getWord_id());
			values.put( Word.COLUMN_WORD_NAME, word.getWord_name());

			db.insert( Word.TABLE_NAME, null, values);

		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * レコードの削除
	 * @param word 削除対象のオブジェクト
	 */
	public void delete(Word word) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Word.TABLE_NAME, Word.COLUMN_WORD_ID + "=?", new String[]{ String.valueOf( word.getWord_id())});
		} finally {
			db.close();
		}
	}

	/**
	 * 全レコードの削除
	 * @param tb_word 削除対象のオブジェクト
	 */
	public void all_delete() {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Word.TABLE_NAME, null, null);
		} finally {
			db.close();
		}
	}

	/**
	 * idでtb_wordをロードする
	 * @param word_id PK
	 * @return ロード結果
	 */
	public Word load(Long word_id) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Word word = null;
		word = clearWord();
		try {
			Cursor cursor = db.query( Word.TABLE_NAME, null, Word.COLUMN_WORD_ID + "=?", new String[]{ String.valueOf( word_id)}, null, null, null);
			cursor.moveToFirst();
			if( !cursor.isAfterLast()){
				word = getWord( cursor);
			}
		} finally {
			db.close();
		}
		return word;
	}

	/**
	 * 一覧を取得する
	 * @return 検索結果
	 */
	public List<Word> list() {
		SQLiteDatabase db = helper.getReadableDatabase();

		List<Word> WordList;
		try {
			Cursor cursor = db.query( Word.TABLE_NAME, null, null, null, null, null, Word.COLUMN_WORD_ID);
			WordList = new ArrayList<Word>();
			cursor.moveToFirst();
			while( !cursor.isAfterLast()){
				WordList.add( getWord( cursor));
				cursor.moveToNext();
			}
		} finally {
			db.close();
		}
		return WordList;
	}

	/**
	 * カーソルからオブジェクトへの変換
	 * @param cursor カーソル
	 * @return 変換結果
	 */
	private Word getWord( Cursor cursor){
		Word word = new Word();

		word.setWord_id( cursor.getLong(0));
		word.setWord_name( cursor.getString(1));
		return word;
	}
	/**
	 * 初期化
	 * @return 変換結果
	 */
	public Word clearWord() {
		Word word = new Word();

		word.setWord_name("");
		return word;
	}
}
