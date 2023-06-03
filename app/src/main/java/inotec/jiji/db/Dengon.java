package inotec.jiji.db;

import java.io.Serializable;

/**
 * 1レコードのデータを保持するオブジェクト
 * Intentに詰めてやり取りするのでSerializableをimplementsする
 */
@SuppressWarnings("serial")
public class Dengon implements Serializable{
	// テーブル名
	public static final String TABLE_NAME = "tb_dengon";

	// カラム名
	public static final String COLUMN_CONTACT_ID = "contact_id";
	public static final String COLUMN_DENGON_NAME = "dengon_name";

	// プロパティ
	private Long contact_id = null;
	private String dengon_name = null;

	public Long getContact_id() {
		return contact_id;
	}

	public void setContact_id(Long contact_id) {
		this.contact_id = contact_id;
	}

	public String getDengon_name() {
		return dengon_name;
	}

	public void setDengon_name(String dengon_name) {
		this.dengon_name = dengon_name;
	}

	/**
	 * ListView表示の際に利用するので氏名を返す
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( getDengon_name());
		return builder.toString();
	}
}
