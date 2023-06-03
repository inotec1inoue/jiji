package inotec.jiji.db;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

/**
 * tb_contact用データアクセスクラス
 */
public class ContactDao {

	private Context mContext = null;

	public ContactDao(Context context) {
		mContext = context;
	}

	/**
	 * 一覧を取得する
	 * @return 検索結果
	 */
	public List<Contact> list() {
		List<Contact> ContactList = new ArrayList<Contact>();

		Contact contact = null;
		ContactList.clear();
		//アドレス情報の取得
        ContentResolver cr = mContext.getContentResolver();
        Cursor c = cr.query(ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null);

        while (c.moveToNext()) {
            //グループ
            int group = c.getInt(c.getColumnIndex(
                ContactsContract.Contacts.IN_VISIBLE_GROUP));
            if (group != 1) continue;

            //ID
            String id = c.getString(
                c.getColumnIndex(ContactsContract.Contacts._ID));

            //表示名
            String displayName = c.getString(c.getColumnIndex(
                ContactsContract.Contacts.DISPLAY_NAME));

            //ふりかな
            String kana = c.getString(c.getColumnIndex(
                ContactsContract.Contacts.PHONETIC_NAME));
            //写真
            int photoID = c.getInt(c.getColumnIndex(
                ContactsContract.Contacts.PHOTO_ID));
            Bitmap icon = null;
            if (photoID != 0) {
                Uri uri = ContentUris.withAppendedId(
                    ContactsContract.Contacts.CONTENT_URI, Long.parseLong(id));
                InputStream in = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
                icon = BitmapFactory.decodeStream(in);
            }

            //電話
            String dial = "";
            if (Integer.parseInt(c.getString(
                c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor cp = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                    new String[]{id}, null);
                while (cp.moveToNext()) {
                    dial = cp.getString(cp.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DATA1));
                }
                cp.close();
            }

            //メール
            String mail = "";
            Cursor cm = cr.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID+" = ?",
                new String[]{id},null);
            while (cm.moveToNext()) {
                mail = cm.getString(cm.getColumnIndex(
                    ContactsContract.CommonDataKinds.Email.DATA));
            }
            cm.close();

            //追加
            contact = new Contact();
            contact.setId(id);
            contact.setIcon(icon);
            contact.setSimei(displayName);
            contact.setKana(kana);
            contact.setDial(dial);
        	contact.setMail(mail);
            ContactList.add(contact);
            contact = null;
        }
		return ContactList;
	}

}
