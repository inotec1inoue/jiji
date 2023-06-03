package inotec.jiji.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Environment;
import inotec.jiji.db.DatabaseOpenHelper;
import inotec.jiji.db.Dengon;
import inotec.jiji.db.DengonDao;
import inotec.jiji.db.Kankyo;
import inotec.jiji.db.KankyoDao;
import inotec.jiji.db.Nokanri;
import inotec.jiji.db.NokanriDao;
import inotec.jiji.db.Word;
import inotec.jiji.db.WordDao;
import inotec.jiji.utils.Constants;

public class InportLib{

	private String Path = Environment.getExternalStorageDirectory().getPath() + Constants.BASE_PATH;

    /**
     * コンストラクタ
     * @param base 起点になるディレクトリ、又はファイル
     */
    public InportLib(String Path) {
        super();
        this.Path = Path;
    }
	/**
	 * tb_nokanri Inport
	 */
	public boolean Inport_Nokanri(Context context, ProgressDialog progressDialog) {
		boolean result = true;
		Nokanri nokanri = null;
		DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
	    SQLiteStatement stmt = null;
		SQLiteDatabase db = helper.getReadableDatabase();

		progressDialog.setProgress(0);
	    try {
			File csv = new File(this.Path + Nokanri.TABLE_NAME + ".csv"); // CSVデータファイル

	        BufferedReader br = new BufferedReader(new FileReader(csv));

			NokanriDao dao = new NokanriDao( context);
			// delete
			dao.all_delete();
			// 最終行まで読み込む
	        int len = 0;
	        String line = "";
        	List<String> st = null;

    		db.beginTransaction();
    		try {
            	stmt = db.compileStatement("insert into tb_nokanri values (?,?);");
    	        while ((line = br.readLine()) != null) {
    	        	// 1行をデータの要素に分割
    				CsvLib csvlib = new CsvLib();
    	        	st = csvlib.getCsvLineList(line);
    	        	csvlib = null;

    				if (!st.get(0).equals("項目ID")) {
    					nokanri = new Nokanri();
    					for (int i=0; i<st.size(); i++)
    					{
    		        		switch (i)
    		        		{
    		        		case 0:		nokanri.setItem_id( st.get(0));	break;
    		        		case 1:		nokanri.setStart_no( Long.parseLong(st.get(1)));	break;
    		        		}
    		        	}
    					// insert
    	        		stmt.bindString(1, nokanri.getItem_id());
    	        		stmt.bindLong(2, nokanri.getStart_no());

    					stmt.executeInsert();
    					nokanri = null;
    				}
    	            // プログレスダイアログの値を設定します
    				len += line.length()+2;
    	            progressDialog.setProgress((int)(100*len/csv.length()));
    	        }

    			db.setTransactionSuccessful();
    		} finally {
    		    db.endTransaction();
    		}
	        br.close();
			dao = null;

	    } catch (FileNotFoundException e) {
	    	// Fileオブジェクト生成時の例外捕捉
	    	e.printStackTrace();
	        result = false;
	    } catch (IOException e) {
	    	// BufferedReaderオブジェクトのクローズ時の例外捕捉
	        e.printStackTrace();
	        result = false;
	    }
		return result;
	}
	/**
	 * tb_kankyo Inport
	 */
	public boolean Inport_Kankyo(Context context, ProgressDialog progressDialog) {
		boolean result = true;
		Kankyo kankyo = null;
		DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
	    SQLiteStatement stmt = null;
		SQLiteDatabase db = helper.getReadableDatabase();

		progressDialog.setProgress(0);
	    try {
			File csv = new File(this.Path + Kankyo.TABLE_NAME + ".csv"); // CSVデータファイル

	        BufferedReader br = new BufferedReader(new FileReader(csv));

			KankyoDao dao = new KankyoDao( context);
			// delete
			dao.all_delete();
			// 最終行まで読み込む
	        int len = 0;
	        String line = "";
        	List<String> st = null;

    		db.beginTransaction();
    		try {
            	stmt = db.compileStatement("insert into tb_kankyo values (?,?,?,?);");
    	        while ((line = br.readLine()) != null) {
    	        	// 1行をデータの要素に分割
    				CsvLib csvlib = new CsvLib();
    	        	st = csvlib.getCsvLineList(line);
    	        	csvlib = null;

    				if (!st.get(0).equals("項目ID")) {
    					kankyo = new Kankyo();
    					for (int i=0; i<st.size(); i++)
    					{
    		        		switch (i)
    		        		{
    		        		case 0:		kankyo.setItem_id( st.get(0));	break;
    		        		case 1:		kankyo.setItem_val( Long.parseLong(st.get(1)));	break;
    		        		//2012.03.16 [修正] Start
    		        		//case 2:		kankyo.setNaiyo1( st.get(2));	break;
    		        		//case 3:		kankyo.setNaiyo2( st.get(3));	break;
    		        		case 2:		kankyo.setNaiyo1( st.get(2).replace("|", "\n"));	break;
    		        		case 3:		kankyo.setNaiyo2( st.get(3).replace("|", "\n"));	break;
    		        		//2012.03.16 [修正] End
    		        		}
    		        	}
    					// insert
    	        		stmt.bindString(1, kankyo.getItem_id());
    	        		stmt.bindLong(2, kankyo.getItem_val());
    	        		stmt.bindString(3, kankyo.getNaiyo1());
    	        		stmt.bindString(4, kankyo.getNaiyo2());

    					stmt.executeInsert();
    					kankyo = null;
    				}
    	            // プログレスダイアログの値を設定します
    				len += line.length()+2;
    	            progressDialog.setProgress((int)(100*len/csv.length()));
    	        }

    			db.setTransactionSuccessful();
    		} finally {
    		    db.endTransaction();
    		}
	        br.close();
			dao = null;

	    } catch (FileNotFoundException e) {
	    	// Fileオブジェクト生成時の例外捕捉
	    	e.printStackTrace();
	        result = false;
	    } catch (IOException e) {
	    	// BufferedReaderオブジェクトのクローズ時の例外捕捉
	        e.printStackTrace();
	        result = false;
	    }
		return result;
	}
	/**
	 * tb_word Inport
	 */
	public boolean Inport_Word(Context context, ProgressDialog progressDialog) {
		boolean result = true;
		Word word = null;
		DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
	    SQLiteStatement stmt = null;
		SQLiteDatabase db = helper.getReadableDatabase();

		progressDialog.setProgress(0);
	    try {
			File csv = new File(this.Path + Word.TABLE_NAME + ".csv"); // CSVデータファイル

	        BufferedReader br = new BufferedReader(new FileReader(csv));

			WordDao dao = new WordDao( context);
			// delete
			dao.all_delete();
			// 最終行まで読み込む
	        int len = 0;
	        String line = "";
        	List<String> st = null;

    		db.beginTransaction();
    		try {
            	stmt = db.compileStatement("insert into tb_word values (?,?);");
    	        while ((line = br.readLine()) != null) {
    	        	// 1行をデータの要素に分割
    				CsvLib csvlib = new CsvLib();
    	        	st = csvlib.getCsvLineList(line);
    	        	csvlib = null;

    				try {
    					Long.parseLong(st.get(0));

    					word = new Word();
    					for (int i=0; i<st.size(); i++)
    					{
    		        		switch (i)
    		        		{
    		        		case 0:		word.setWord_id( Long.parseLong(st.get(0)));	break;
    		        		case 1:		word.setWord_name( st.get(1));		break;
    		        		}
    		        	}
    					// insert
    					stmt.bindLong(1, word.getWord_id());
    					stmt.bindString(2, word.getWord_name());

    					stmt.executeInsert();
    					word = null;
    				} catch(Exception e) {}
    	            // プログレスダイアログの値を設定します
    				len += line.length()+2;
    	            progressDialog.setProgress((int)(100*len/csv.length()));
    	        }

    			db.setTransactionSuccessful();
    		} finally {
    		    db.endTransaction();
    		}
	        br.close();
			dao = null;

	    } catch (FileNotFoundException e) {
	    	// Fileオブジェクト生成時の例外捕捉
	    	e.printStackTrace();
	        result = false;
	    } catch (IOException e) {
	    	// BufferedReaderオブジェクトのクローズ時の例外捕捉
	        e.printStackTrace();
	        result = false;
	    }
		return result;
	}
	/**
	 * tb_dengon Inport
	 */
	public boolean Inport_Dengon(Context context, ProgressDialog progressDialog) {
		boolean result = true;
		Dengon dengon = null;
		DatabaseOpenHelper helper = new DatabaseOpenHelper(context);
	    SQLiteStatement stmt = null;
		SQLiteDatabase db = helper.getReadableDatabase();

		progressDialog.setProgress(0);
	    try {
			File csv = new File(this.Path + Dengon.TABLE_NAME + ".csv"); // CSVデータファイル

	        BufferedReader br = new BufferedReader(new FileReader(csv));

			DengonDao dao = new DengonDao( context);
			// delete
			dao.all_delete();
			// 最終行まで読み込む
	        int len = 0;
	        String line = "";
        	List<String> st = null;

    		db.beginTransaction();
    		try {
            	stmt = db.compileStatement("insert into tb_dengon values (?,?);");
    	        while ((line = br.readLine()) != null) {
    	        	// 1行をデータの要素に分割
    				CsvLib csvlib = new CsvLib();
    	        	st = csvlib.getCsvLineList(line);
    	        	csvlib = null;

    				try {
    					Long.parseLong(st.get(0));

    					dengon = new Dengon();
    					for (int i=0; i<st.size(); i++)
    					{
    		        		switch (i)
    		        		{
    		        		case 0:		dengon.setContact_id( Long.parseLong(st.get(0)));	break;
    		        		case 1:		dengon.setDengon_name( st.get(1));		break;
    		        		}
    		        	}
    					// insert
    					stmt.bindLong(1, dengon.getContact_id());
    					stmt.bindString(2, dengon.getDengon_name());

    					stmt.executeInsert();
    					dengon = null;
    				} catch(Exception e) {}
    	            // プログレスダイアログの値を設定します
    				len += line.length()+2;
    	            progressDialog.setProgress((int)(100*len/csv.length()));
    	        }

    			db.setTransactionSuccessful();
    		} finally {
    		    db.endTransaction();
    		}
	        br.close();
			dao = null;

	    } catch (FileNotFoundException e) {
	    	// Fileオブジェクト生成時の例外捕捉
	    	e.printStackTrace();
	        result = false;
	    } catch (IOException e) {
	    	// BufferedReaderオブジェクトのクローズ時の例外捕捉
	        e.printStackTrace();
	        result = false;
	    }
		return result;
	}
}
