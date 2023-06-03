package inotec.jiji.db;

import java.io.Serializable;

/**
 * 1レコードのデータを保持するオブジェクト
 * Intentに詰めてやり取りするのでSerializableをimplementsする
 */
@SuppressWarnings("serial")
public class Kana implements Serializable{
	// テーブル名
	public static final String TABLE_NAME = "tb_kana";

	// カラム名
	public static final String COLUMN_KANA_ID = "kana_id";
	public static final String COLUMN_KANA_NAME = "kana_name";
	public static final String COLUMN_KANA_KEY = "kana_key";

	// プロパティ
	private Long kana_id = null;
	private String kana_name = null;
	private String kana_key = null;

	public Long getKana_id() {
		return kana_id;
	}

	public void setKana_id(Long kana_id) {
		this.kana_id = kana_id;
	}

	public String getKana_name() {
		return kana_name;
	}

	public void setKana_name(String kana_name) {
		this.kana_name = kana_name;
	}

	public String getKana_key() {
		return kana_key;
	}

	public void setKana_key(String kana_key) {
		this.kana_key = kana_key;
	}

	/**
	 * ListView表示の際に利用するので氏名を返す
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( getKana_name());
		return builder.toString();
	}
}
