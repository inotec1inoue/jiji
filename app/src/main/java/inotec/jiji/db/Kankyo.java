package inotec.jiji.db;

import java.io.Serializable;

/**
 * 1レコードのデータを保持するオブジェクト
 * Intentに詰めてやり取りするのでSerializableをimplementsする
 */
@SuppressWarnings("serial")
public class Kankyo implements Serializable{
	// テーブル名
	public static final String TABLE_NAME = "tb_kankyo";

	// カラム名
	public static final String COLUMN_ITEM_ID = "item_id";
	public static final String COLUMN_ITEM_VAL = "item_val";
	public static final String COLUMN_NAIYO1 = "naiyo1";
	public static final String COLUMN_NAIYO2 = "naiyo2";

	// プロパティ
	private String item_id = null;
	private Long item_val = 0L;
	private String naiyo1 = null;
	private String naiyo2 = null;

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public Long getItem_val() {
		return item_val;
	}

	public void setItem_val(Long item_val) {
		this.item_val = item_val;
	}

	public String getNaiyo1() {
		return naiyo1;
	}

	public void setNaiyo1(String naiyo1) {
		this.naiyo1 = naiyo1;
	}

	public String getNaiyo2() {
		return naiyo2;
	}

	public void setNaiyo2(String naiyo2) {
		this.naiyo2 = naiyo2;
	}

}
