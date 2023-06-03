package inotec.jiji.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * tb_kankyo用データアクセスクラス
 */
public class KankyoDao {

	private DatabaseOpenHelper helper = null;
	private String user = null;
	private String password = null;
	private String data_folder = null;		//2017.09.07 [追加]

	private int zei_hasuu = 0;
	private int zei_ritu = 10;	//2019.10.01 [8 -> 10]
	//private int nebiki_houhou = 1;
	//private int waribiki_houhou = 1;
	//private int waribiki_hasuu = 0;
	//private int ryouritu = 0;	//2014.12.04 [追加]
	//private int nyuko_job = 0;		//2012.05.01 [追加]

	private int denpyo1 = 1;
	private int denpyo2 = 1;
	private int denpyo3 = 0;	//2013.10.24 [追加]
	private int nouhin1 = 1;	//2019.09.18 [add]
	private int nouhin2 = 0;	//2019.09.18 [add]

	private String den_msg = null;

	public KankyoDao(Context context) {
		helper = new DatabaseOpenHelper(context);
	}
	//2016.05.17 [追加] Start
	// 環境テーブルの更新
	public void Save(Kankyo kankyo) {
		String sql;
		Long count;
	    SQLiteStatement stmt = null;

		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor;

		db.beginTransaction();
		try {

			// tb_kankyoの保存
			sql = "select count(*) from tb_kankyo";
			sql += " where item_id = '" +  kankyo.getItem_id() + "'";
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
				stmt = db.compileStatement("insert into tb_kankyo values (?,?,?,?);");
				stmt.bindString(1, kankyo.getItem_id());
				stmt.bindLong(2, kankyo.getItem_val());
				stmt.bindString(3, kankyo.getNaiyo1());
				stmt.bindString(4, kankyo.getNaiyo2());

				stmt.executeInsert();
			}
			else
			{
	    		stmt = db.compileStatement("update tb_kankyo set item_val = ?, naiyo1 = ?, naiyo2 = ? where item_id = '" +  kankyo.getItem_id() + "';");
	    		stmt.bindLong(1, kankyo.getItem_val());
	    		stmt.bindString(2, kankyo.getNaiyo1());
	    		stmt.bindString(3, kankyo.getNaiyo2());

				stmt.executeInsert();
			}

		    db.setTransactionSuccessful();
		} finally {
		    db.endTransaction();
		}

	}
	//2016.05.17 [追加] End
	/**
	 * tb_kankyoの保存
	 * @param tb_kankyo 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Kankyo insert( Kankyo kankyo){
		SQLiteDatabase db = helper.getWritableDatabase();
		Kankyo result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Kankyo.COLUMN_ITEM_ID, kankyo.getItem_id());
			values.put( Kankyo.COLUMN_ITEM_VAL, kankyo.getItem_val());
			values.put( Kankyo.COLUMN_NAIYO1, kankyo.getNaiyo1());
			values.put( Kankyo.COLUMN_NAIYO2, kankyo.getNaiyo2());

			db.insert( Kankyo.TABLE_NAME, null, values);

		} finally {
			db.close();
		}
		return result;
	}
	/*
	 * ユーザー名
	 */
	public String getUser() {
		Kankyo kankyo = this.load( "user");
		user = kankyo.getNaiyo1();
		return user;
	}
	/*
	 * パスワード
	 */
	public String getPassword() {
		Kankyo kankyo = this.load( "password");
		password = kankyo.getNaiyo1();
		return password;
	}
	//2017.09.07 [追加] start
	/*
	 * データフォルダ
	 */
	public String getData_folder() {
		Kankyo kankyo = this.load( "data_folder");
		data_folder = kankyo.getNaiyo1();
		return data_folder;
	}
	//2017.09.07 [追加] end
	/*
	 * 消費税率
	 */
	public int getZei_ritu() {
		Kankyo kankyo = this.load( "zei_ritu");
		zei_ritu = kankyo.getItem_val().intValue();
		return zei_ritu;
	}
	/*
	 * 消費税端数
	 */
	public int getZei_hasuu() {
		Kankyo kankyo = this.load( "zei_hasuu");
		zei_hasuu = kankyo.getItem_val().intValue();
		return zei_hasuu;
	}
	/*
	 * 値引方法
	 */
	/*
	public int getNebiki_houhou() {
		Kankyo kankyo = this.load( "nebiki_houhou");
		nebiki_houhou = kankyo.getItem_val().intValue();
		return nebiki_houhou;
	}
	*/
	/*
	 * 割引方法
	 */
	/*
	public int getWaribiki_houhou() {
		Kankyo kankyo = this.load( "waribiki_houhou");
		waribiki_houhou = kankyo.getItem_val().intValue();
		return waribiki_houhou;
	}
	*/
	/*
	 * 割引端数
	 */
	/*
	public int getWaribiki_hasuu() {
		Kankyo kankyo = this.load( "waribiki_hasuu");
		waribiki_hasuu = kankyo.getItem_val().intValue();
		return waribiki_hasuu;
	}
	*/
	//2014.12.04 [追加] Start
	/*
	 * 料率計算
	 */
	/*
	public int getRyouritu() {
		Kankyo kankyo = this.load( "ryouritu");
		ryouritu = kankyo.getItem_val().intValue();
		return ryouritu;
	}
	*/
	//2014.12.04 [追加] End
	//2012.05.01 [追加] Start
	/*
	 * 入庫処理
	 */
	/*
	public int getNyuko_job() {
		Kankyo kankyo = this.load( "nyuko_job");
		nyuko_job = kankyo.getItem_val().intValue();
		return nyuko_job;
	}
	*/
	//2012.05.01 [追加] End
	/*
	 * お預り証印字
	 */
	public int getDenpyo1() {
		Kankyo kankyo = this.load( "denpyo1");
		denpyo1 = kankyo.getItem_val().intValue();
		return denpyo1;
	}
	/*
	 * （控え）印字
	 */
	public int getDenpyo2() {
		Kankyo kankyo = this.load( "denpyo2");
		denpyo2 = kankyo.getItem_val().intValue();
		return denpyo2;
	}
	//2013.10.24 [追加] Start
	/*
	 * 納品伝票印字
	 */
	public int getDenpyo3() {
		Kankyo kankyo = this.load( "denpyo3");
		denpyo3 = kankyo.getItem_val().intValue();
		return denpyo3;
	}
	//2013.10.24 [追加] End
	//2019.09.18 [add] start
	/*
	 * 納品書印字
	 */
	public int getNouhin1() {
		Kankyo kankyo = this.load( "nouhin1");
		if (kankyo.getItem_id() != null) {
			nouhin1 = kankyo.getItem_val().intValue();
		}
		return nouhin1;
	}
	/*
	 * （控え）印字
	 */
	public int getNouhin2() {
		Kankyo kankyo = this.load( "nouhin2");
		if (kankyo.getItem_id() != null) {
			nouhin2 = kankyo.getItem_val().intValue();
		}
		return nouhin2;
	}
	//2019.09.18 [add] end
	/*
	 * 伝票メッセージ
	 */
	public String getDen_msg() {
		Kankyo kankyo = this.load( "den_msg");
		den_msg = kankyo.getNaiyo1();
		return den_msg;
	}

	/**
	 * tb_kankyoの保存
	 * @param tb_kankyo 保存対象のオブジェクト
	 * @return 保存結果
	 */
	public Kankyo update( Kankyo kankyo){
		SQLiteDatabase db = helper.getWritableDatabase();
		Kankyo result = null;
		try {
			ContentValues values = new ContentValues();
			values.put( Kankyo.COLUMN_ITEM_ID, kankyo.getItem_id());
			values.put( Kankyo.COLUMN_ITEM_VAL, kankyo.getItem_val());
			values.put( Kankyo.COLUMN_NAIYO1, kankyo.getNaiyo1());
			values.put( Kankyo.COLUMN_NAIYO2, kankyo.getNaiyo2());

			db.update( Kankyo.TABLE_NAME, values, Kankyo.COLUMN_ITEM_ID + "=?", new String[]{ String.valueOf( kankyo.getItem_id())});

		} finally {
			db.close();
		}
		return result;
	}

	/**
	 * レコードの削除
	 * @param tb_kankyo 削除対象のオブジェクト
	 */
	public void delete(Kankyo kankyo) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Kankyo.TABLE_NAME, Kankyo.COLUMN_ITEM_ID + "=?", new String[]{ String.valueOf( kankyo.getItem_id())});
		} finally {
			db.close();
		}
	}

	/**
	 * 全レコードの削除
	 * @param tb_kankyo 削除対象のオブジェクト
	 */
	public void all_delete() {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.delete( Kankyo.TABLE_NAME, null, null);
		} finally {
			db.close();
		}
	}

	/**
	 * idでtb_kankyoをロードする
	 * @param item_id PK
	 * @return ロード結果
	 */
	public Kankyo load(String item_id) {
		SQLiteDatabase db = helper.getReadableDatabase();

		Kankyo kankyo = null;
		kankyo = clearKankyo();
		try {
			Cursor cursor = db.query( Kankyo.TABLE_NAME, null, Kankyo.COLUMN_ITEM_ID + "=?", new String[]{ String.valueOf( item_id)}, null, null, null);
			cursor.moveToFirst();
			if( !cursor.isAfterLast()){
				kankyo = getKankyo( cursor);
			}
		} finally {
			db.close();
		}
		return kankyo;
	}

	/**
	 * 一覧を取得する
	 * @return 検索結果
	 */
	public List<Kankyo> list() {
		SQLiteDatabase db = helper.getReadableDatabase();

		List<Kankyo> KankyoList;
		try {
			Cursor cursor = db.query( Kankyo.TABLE_NAME, null, null, null, null, null, Kankyo.COLUMN_ITEM_ID);
			KankyoList = new ArrayList<Kankyo>();
			cursor.moveToFirst();
			while( !cursor.isAfterLast()){
				KankyoList.add( getKankyo( cursor));
				cursor.moveToNext();
			}
		} finally {
			db.close();
		}
		return KankyoList;
	}

	/**
	 * カーソルからオブジェクトへの変換
	 * @param cursor カーソル
	 * @return 変換結果
	 */
	private Kankyo getKankyo( Cursor cursor){
		Kankyo kankyo = new Kankyo();

		kankyo.setItem_id( cursor.getString(0));
		kankyo.setItem_val( cursor.getLong(1));
		kankyo.setNaiyo1( cursor.getString(2));
		kankyo.setNaiyo2( cursor.getString(3));
		return kankyo;
	}
	/**
	 * 初期化
	 * @return 変換結果
	 */
	public Kankyo clearKankyo() {
		Kankyo kankyo = new Kankyo();

		kankyo.setItem_val(0L);
		kankyo.setNaiyo1("");
		kankyo.setNaiyo2("");
		return kankyo;
	}
}
