package inotec.jiji.db;

import java.io.Serializable;

/**
 * 1レコードのデータを保持するオブジェクト
 * Intentに詰めてやり取りするのでSerializableをimplementsする
 */
@SuppressWarnings("serial")
public class Word implements Serializable{
	// テーブル名
	public static final String TABLE_NAME = "tb_word";

	// カラム名
	public static final String COLUMN_WORD_ID = "word_id";
	public static final String COLUMN_WORD_NAME = "word_name";

	// プロパティ
	private Long word_id = null;
	private String word_name = null;

	public Long getWord_id() {
		return word_id;
	}

	public void setWord_id(Long word_id) {
		this.word_id = word_id;
	}

	public String getWord_name() {
		return word_name;
	}

	public void setWord_name(String word_name) {
		this.word_name = word_name;
	}

	/**
	 * ListView表示の際に利用するので氏名を返す
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( getWord_name());
		return builder.toString();
	}
}
