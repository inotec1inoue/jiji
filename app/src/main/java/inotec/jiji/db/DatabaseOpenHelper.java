package inotec.jiji.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import inotec.jiji.utils.Constants;

/**
 * データベース処理クラス
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

	// データベース名の定数
	private static final String DB_NAME = Constants.DB_NAME;

	/**
	 * 初期投入データ
	 */
	// 番号管理データ
	private String[][] nokanri_datas = new String[][]{
			{"word_jun", "1"}
	};
	// かなデータ
	private String[][] kana_datas = new String[][]{
			{"1", "あ", "ｱ"},
			{"2", "い", "ｲ"},
			{"3", "う", "ｳ"},
			{"4", "え", "ｴ"},
			{"5", "お", "ｵ"},
			{"6", "か", "ｶ"},
			{"7", "き", "ｷ"},
			{"8", "く", "ｸ"},
			{"9", "け", "ｹ"},
			{"10", "こ", "ｺ"},
			{"11", "さ", "ｻ"},
			{"12", "し", "ｼ"},
			{"13", "す", "ｽ"},
			{"14", "せ", "ｾ"},
			{"15", "そ", "ｿ"},
			{"16", "た", "ﾀ"},
			{"17", "ち", "ﾁ"},
			{"18", "つ", "ﾂ"},
			{"19", "て", "ﾃ"},
			{"20", "と", "ﾄ"},
			{"21", "な", "ﾅ"},
			{"22", "に", "ﾆ"},
			{"23", "ぬ", "ﾇ"},
			{"24", "ね", "ﾈ"},
			{"25", "の", "ﾉ"},
			{"26", "は", "ﾊ"},
			{"27", "ひ", "ﾋ"},
			{"28", "ふ", "ﾌ"},
			{"29", "へ", "ﾍ"},
			{"30", "ほ", "ﾎ"},
			{"31", "ま", "ﾏ"},
			{"32", "み", "ﾐ"},
			{"33", "む", "ﾑ"},
			{"34", "め", "ﾒ"},
			{"35", "も", "ﾓ"},
			{"36", "や", "ﾔ"},
			{"37", "ゆ", "ﾕ"},
			{"38", "よ", "ﾖ"},
			{"39", "ら", "ﾗ"},
			{"40", "り", "ﾘ"},
			{"41", "る", "ﾙ"},
			{"42", "れ", "ﾚ"},
			{"43", "ろ", "ﾛ"},
			{"44", "わ", "ﾜ"},
			{"45", "ん", "ﾝ"},
			{"46", "０", "0"},
			{"47", "１", "1"},
			{"48", "２", "2"},
			{"49", "３", "3"},
			{"50", "４", "4"},
			{"51", "５", "5"},
			{"52", "６", "6"},
			{"53", "７", "7"},
			{"54", "８", "8"},
			{"55", "９", "9"},
			{"56", "全", "%"}
	};
	// 環境マスタ
	private String[][] kankyo_datas = new String[][]{
			{"den_msg", "1", "毎度ありがとうございます。", ""}
	};
	// 文章マスタ
	private String[][] word_datas = new String[][]{
			{"1", "電話ください"},
			{"2", "連絡ください"},
			{"3", "ありがとう"},
			{"4", "お願いします"},
			{"5", "お迎えお願いします"},
			{"6", "よろしくお願いいたします"},
			{"7", "お迎えお願いできますか"},
			{"8", "ごめんね"},
			{"9", "了解です"},
			{"10", "よろしく"},
			{"11", "おはよう"},
			{"12", "おやすみ"},
			{"13", "お疲れ様でした"},
			{"14", "待ってます"},
			{"15", "おめでとう!!"},
			{"16", "今から帰ります"},
			{"17", "到着しました"},
			{"18", "少し遅れます"},
			{"19", "また連絡します"},
			{"20", "お疲れ様"},
			{"21", "早く帰ります"},
			{"22", "遅くなります"},
			{"23", "いいね!!"},
			{"24", "感謝!!"},
			{"25", "何時??"},
			{"26", "今どこ??"},
			{"27", "出発します"},
			{"28", "また明日"},
			{"29", "帰宅しました"},
			{"30", "家にいます"},
			{"31", "大丈だよ"},
			{"32", "友達と一緒です"},
			{"33", "ではまた!!"},
			{"34", "また後で!!"},
			{"35", "体調悪い"}
	};

	/**
	 * コンストラクタ
	 */
	public DatabaseOpenHelper(Context context) {
		// 指定したデータベース名が存在しない場合は、新たに作成されonCreate()が呼ばれる
		// バージョンを変更するとonUpgrade()が呼ばれる
		super(context, DB_NAME, null, 1);
	}

	/**
	 * データベースの生成に呼び出されるので、 スキーマの生成を行う
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		// tb_kana
		createKana(db);

		// tb_nokanri
		createNokanri(db);

		// tb_kankyo
		createKankyo(db);

		// tb_word
		createWord(db);

		// tb_dengon
		createDengon(db);

	}

	/**
	 * create tb_kana
	 */
	public void createKana(SQLiteDatabase db){
		// tb_kana
		db.beginTransaction();
		try{
			// テーブルの生成
			StringBuilder createSql = new StringBuilder();
			createSql.append("create table " + Kana.TABLE_NAME + " (");
			createSql.append(Kana.COLUMN_KANA_ID + " integer primary key not null,");
			createSql.append(Kana.COLUMN_KANA_NAME + " text not null,");
			createSql.append(Kana.COLUMN_KANA_KEY + " text not null");
			createSql.append(")");

			db.execSQL( createSql.toString());

			// データの投入
			for( String[] data: kana_datas){
				ContentValues values = new ContentValues();
				values.put(Kana.COLUMN_KANA_ID, Integer.parseInt(data[ 0]));
				values.put(Kana.COLUMN_KANA_NAME, data[ 1]);
				values.put(Kana.COLUMN_KANA_KEY, data[ 2]);
				db.insert(Kana.TABLE_NAME, null, values);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * create tb_nokanri
	 */
	public void createNokanri(SQLiteDatabase db){
		// tb_nokanri
		db.beginTransaction();
		try{
			// テーブルの生成
			StringBuilder createSql = new StringBuilder();
			createSql.append("create table " + Nokanri.TABLE_NAME + " (");
			createSql.append(Nokanri.COLUMN_ITEM_ID + " text primary key not null,");
			createSql.append(Nokanri.COLUMN_START_NO + " integer");
			createSql.append(")");

			db.execSQL( createSql.toString());

			// データの投入
			for( String[] data: nokanri_datas){
				ContentValues values = new ContentValues();
				values.put(Nokanri.COLUMN_ITEM_ID, data[ 0]);
				values.put(Nokanri.COLUMN_START_NO, Integer.parseInt(data[ 1]));
				db.insert(Nokanri.TABLE_NAME, null, values);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * create tb_kankyo
	 */
	public void createKankyo(SQLiteDatabase db){
		// tb_kankyo
		db.beginTransaction();
		try{
			// テーブルの生成
			StringBuilder createSql = new StringBuilder();
			createSql.append("create table " + Kankyo.TABLE_NAME + " (");
			createSql.append(Kankyo.COLUMN_ITEM_ID + " text primary key not null,");
			createSql.append(Kankyo.COLUMN_ITEM_VAL + " integer,");
			createSql.append(Kankyo.COLUMN_NAIYO1 + " text,");
			createSql.append(Kankyo.COLUMN_NAIYO2 + " text");
			createSql.append(")");

			db.execSQL( createSql.toString());

			// データの投入
			for( String[] data: kankyo_datas){
				ContentValues values = new ContentValues();
				values.put(Kankyo.COLUMN_ITEM_ID, data[ 0]);
				values.put(Kankyo.COLUMN_ITEM_VAL, Integer.parseInt(data[ 1]));
				values.put(Kankyo.COLUMN_NAIYO1, data[ 2]);
				values.put(Kankyo.COLUMN_NAIYO2, data[ 3]);
				db.insert(Kankyo.TABLE_NAME, null, values);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * create tb_word
	 */
	public void createWord(SQLiteDatabase db){
		// tb_word
		db.beginTransaction();
		try{
			// テーブルの生成
			StringBuilder createSql = new StringBuilder();
			createSql.append("create table " + Word.TABLE_NAME + " (");
			createSql.append(Word.COLUMN_WORD_ID + " integer primary key not null,");
			createSql.append(Word.COLUMN_WORD_NAME + " text not null");
			createSql.append(")");

			db.execSQL( createSql.toString());

			// データの投入
			for( String[] data: word_datas){
				ContentValues values = new ContentValues();
				values.put(Word.COLUMN_WORD_ID, Integer.parseInt(data[ 0]));
				values.put(Word.COLUMN_WORD_NAME, data[ 1]);
				db.insert(Word.TABLE_NAME, null, values);
			}
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	/**
	 * create tb_dengon
	 */
	public void createDengon(SQLiteDatabase db){
		// tb_dengon
		db.beginTransaction();
		try{
			// テーブルの生成
			StringBuilder createSql = new StringBuilder();
			createSql.append("create table " + Dengon.TABLE_NAME + " (");
			createSql.append(Dengon.COLUMN_CONTACT_ID + " integer primary key not null,");
			createSql.append(Dengon.COLUMN_DENGON_NAME + " text not null");
			createSql.append(")");

			db.execSQL( createSql.toString());

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * drop tb_kana
	 */
	public void dropKana(SQLiteDatabase db){
		// tb_kana
		db.beginTransaction();
		try{
			// テーブルのdrop
			StringBuilder dropSql = new StringBuilder();
			dropSql.append("drop table " + Kana.TABLE_NAME );

			db.execSQL( dropSql.toString());

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	/**
	 * drop tb_nokanri
	 */
	public void dropNokanri(SQLiteDatabase db){
		// tb_nokanri
		db.beginTransaction();
		try{
			// テーブルのdrop
			StringBuilder dropSql = new StringBuilder();
			dropSql.append("drop table " + Nokanri.TABLE_NAME );

			db.execSQL( dropSql.toString());

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	/**
	 * drop tb_kankyo
	 */
	public void dropKankyo(SQLiteDatabase db){
		// tb_nokanri
		db.beginTransaction();
		try{
			// テーブルのdrop
			StringBuilder dropSql = new StringBuilder();
			dropSql.append("drop table " + Kankyo.TABLE_NAME );

			db.execSQL( dropSql.toString());

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	/**
	 * drop tb_word
	 */
	public void dropWord(SQLiteDatabase db){
		// tb_word
		db.beginTransaction();
		try{
			// テーブルのdrop
			StringBuilder dropSql = new StringBuilder();
			dropSql.append("drop table " + Word.TABLE_NAME );

			db.execSQL( dropSql.toString());

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	/**
	 * drop tb_dengon
	 */
	public void dropDengon(SQLiteDatabase db){
		// tb_dengon
		db.beginTransaction();
		try{
			// テーブルのdrop
			StringBuilder dropSql = new StringBuilder();
			dropSql.append("drop table " + Dengon.TABLE_NAME );

			db.execSQL( dropSql.toString());

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}
	/**
	 * table 存在 check
	 */
	public boolean table_check(SQLiteDatabase db, String table_name){
		String sql;

		Cursor cursor;

		boolean ret = false;
		try {
			sql = "select count(*) from sqlite_master";
			sql += " where type = 'table'";
			sql += "   and name = '" + table_name + "'";
			cursor = db.rawQuery(sql, null);
			cursor.moveToFirst();
			if (!cursor.isAfterLast())
			{
				ret = (cursor.getLong(0) > 0);
			}
		} finally {
		}
		return ret;
	}
	/**
	 * column 存在 check
	 */
	public boolean column_check(SQLiteDatabase db, String table_name, String column_name){
		String sql;

		Cursor cursor;

		boolean ret = false;
		try {
			sql = "PRAGMA table_info(" + table_name + ")";
			cursor = db.rawQuery(sql, null);
            int rowcnt = cursor.getCount();
			cursor.moveToFirst();
            for (int i = 0; i < rowcnt; i++) {
                if (cursor.getString(1).equals(column_name)) {
                  	ret = true;
                   	break;
                }
                cursor.moveToNext();
            }
		} finally {
		}
		return ret;
	}
	/**
	 * column 追加
	 */
	public void column_add(SQLiteDatabase db, String table_name, String column_name, String column_type){
		db.beginTransaction();
		try{
			// カラムのadd
			StringBuilder execSql = new StringBuilder();
			execSql.append("alter table " + table_name + " add column " + column_name + " " + column_type);

			db.execSQL( execSql.toString());

			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * データベースの更新
	 *
	 * 親クラスのコンストラクタに渡すversionを変更したときに呼び出される
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// データベースの更新
	}
}
