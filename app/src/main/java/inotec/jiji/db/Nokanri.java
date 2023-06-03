package inotec.jiji.db;

import java.io.Serializable;

/**
 * 1レコードのデータを保持するオブジェクト
 * Intentに詰めてやり取りするのでSerializableをimplementsする
 */
@SuppressWarnings("serial")
public class Nokanri implements Serializable{
	// テーブル名
	public static final String TABLE_NAME = "tb_nokanri";

	// カラム名
	public static final String COLUMN_ITEM_ID = "item_id";
	public static final String COLUMN_START_NO = "start_no";

	// プロパティ
	private String item_id = null;
	private Long start_no = 0L;

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public Long getStart_no() {
		return start_no;
	}

	public void setStart_no(Long start_no) {
		this.start_no = start_no;
	}

}
