package inotec.jiji.db;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * 1レコードのデータを保持するオブジェクト
 * Intentに詰めてやり取りするのでSerializableをimplementsする
 */
@SuppressWarnings("serial")
public class Contact implements Serializable{
	// テーブル名
	public static final String TABLE_NAME = "tb_contact";

	// カラム名

	// プロパティ
	private String id = null;
	private Bitmap icon = null;
	private String simei = null;
	private String kana = null;
	private String dial = null;
	private String mail = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String getSimei() {
		return simei;
	}

	public void setSimei(String simei) {
		this.simei = simei;
	}

	public String getKana() {
		return kana;
	}

	public void setKana(String kana) {
		this.kana = kana;
	}

	public String getDial() {
		return dial;
	}

	public void setDial(String dial) {
		this.dial = dial;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * ListView表示の際に利用するので氏名を返す
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( getSimei());
		return builder.toString();
	}
}
