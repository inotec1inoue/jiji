package inotec.jiji.com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import inotec.jiji.db.Dengon;
import inotec.jiji.db.DengonDao;
import inotec.jiji.db.Kankyo;
import inotec.jiji.db.KankyoDao;
import inotec.jiji.db.Nokanri;
import inotec.jiji.db.NokanriDao;
import inotec.jiji.db.Word;
import inotec.jiji.db.WordDao;
import inotec.jiji.utils.Constants;

public class ExportLib{

	private String Path = Environment.getExternalStorageDirectory().getPath() + Constants.BASE_PATH;
	/**
	 * tb_nokanri Export
	 */
	public void Export_Nokanri(Context context, ProgressDialog progressDialog) {
		List<Nokanri> NokanriList = null;

		NokanriDao dao = new NokanriDao(context);
		NokanriList = dao.list();
        dao = null;

		progressDialog.setProgress(0);
		try {
			File csv = new File(Path + Nokanri.TABLE_NAME + ".csv"); // CSVデータファイル
			// newFile.txtを新規作成する
			csv.createNewFile();
			// 追記モード
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv));

			// ヘッダ行の追加
        	bw.write("項目ID" + ",");
        	bw.write("開始番号");
			bw.newLine();

			int i = 0;
            for(Nokanri nokanri: NokanriList) {
				// 新たなデータ行の追加
            	bw.write("\"" + nokanri.getItem_id().toString() + "\"" + ",");
            	bw.write(nokanri.getStart_no().toString());
				bw.newLine();
	            i++;
	            // プログレスダイアログの値を設定します
	            progressDialog.setProgress(100*i/NokanriList.size());
			}

	        bw.close();

		} catch (FileNotFoundException e) {
			// Fileオブジェクト生成時の例外捕捉
		    e.printStackTrace();
	    } catch (IOException e) {
		    // BufferedWriterオブジェクトのクローズ時の例外捕捉
		    e.printStackTrace();
		}
	}
	/**
	 * tb_kankyo Export
	 */
	public void Export_Kankyo(Context context, ProgressDialog progressDialog) {
		List<Kankyo> KankyoList = null;

		KankyoDao dao = new KankyoDao(context);
		KankyoList = dao.list();
        dao = null;

		progressDialog.setProgress(0);
		try {
			File csv = new File(Path + Kankyo.TABLE_NAME + ".csv"); // CSVデータファイル
			// newFile.txtを新規作成する
			csv.createNewFile();
			// 追記モード
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv));

			// ヘッダ行の追加
        	bw.write("項目ID" + ",");
			bw.write("値" + ",");
			bw.write("内容1" + ",");
			bw.write("内容2");
			bw.newLine();

			int i = 0;
            for(Kankyo kankyo: KankyoList) {
				// 新たなデータ行の追加
            	bw.write("\"" + kankyo.getItem_id().toString() + "\"" + ",");
    			bw.write(kankyo.getItem_val().toString() + ",");
	            if (kankyo.getNaiyo1() == null) {
	            	bw.write("" + ",");
	            } else {
	    			//2012.03.16 [修正] Start
	            	//bw.write("\"" + kankyo.getNaiyo1().toString() + "\"" + ",");
	    			bw.write("\"" + kankyo.getNaiyo1().toString().replace("\n", "|") + "\"" + ",");
	    			//2012.03.16 [修正] End
	            }
	            if (kankyo.getNaiyo2() == null) {
	            	bw.write("" + ",");
	            } else {
	    			//2012.03.16 [修正] Start
	            	//bw.write("\"" + kankyo.getNaiyo2().toString() + "\"" + ",");
	    			bw.write("\"" + kankyo.getNaiyo2().toString().replace("\n", "|") + "\"" + ",");
	    			//2012.03.16 [修正] End
	            }
				bw.newLine();
	            i++;
	            // プログレスダイアログの値を設定します
	            progressDialog.setProgress(100*i/KankyoList.size());
			}

	        bw.close();

		} catch (FileNotFoundException e) {
			// Fileオブジェクト生成時の例外捕捉
		    e.printStackTrace();
	    } catch (IOException e) {
		    // BufferedWriterオブジェクトのクローズ時の例外捕捉
		    e.printStackTrace();
		}
	}
	/**
	 * tb_word Export
	 */
	public void Export_Word(Context context, ProgressDialog progressDialog) {
		List<Word> WordList = null;

		WordDao dao = new WordDao(context);
		WordList = dao.list();
        dao = null;

		progressDialog.setProgress(0);
		try {
			File csv = new File(Path + Word.TABLE_NAME + ".csv"); // CSVデータファイル
			// newFile.txtを新規作成する
			csv.createNewFile();
			// 追記モード
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv));

			// ヘッダ行の追加
			bw.write("文章コード" + ",");
			bw.write("文章");
			bw.newLine();

			int i = 0;
            for(Word word: WordList) {
				// 新たなデータ行の追加
    			bw.write(word.getWord_id().toString() + ",");
    			bw.write("\"" + word.getWord_name().toString() + "\"");
				bw.newLine();
	            i++;
	            // プログレスダイアログの値を設定します
	            progressDialog.setProgress(100*i/WordList.size());
			}

	        bw.close();

		} catch (FileNotFoundException e) {
			// Fileオブジェクト生成時の例外捕捉
		    e.printStackTrace();
	    } catch (IOException e) {
		    // BufferedWriterオブジェクトのクローズ時の例外捕捉
		    e.printStackTrace();
		}
	}
	/**
	 * tb_dengon Export
	 */
	public void Export_Dengon(Context context, ProgressDialog progressDialog) {
		List<Dengon> DengonList = null;

		DengonDao dao = new DengonDao(context);
		DengonList = dao.list();
        dao = null;

		progressDialog.setProgress(0);
		try {
			File csv = new File(Path + Dengon.TABLE_NAME + ".csv"); // CSVデータファイル
			// newFile.txtを新規作成する
			csv.createNewFile();
			// 追記モード
			BufferedWriter bw = new BufferedWriter(new FileWriter(csv));

			// ヘッダ行の追加
			bw.write("連絡先コード" + ",");
			bw.write("伝言");
			bw.newLine();

			int i = 0;
            for(Dengon dengon: DengonList) {
				// 新たなデータ行の追加
    			bw.write(dengon.getContact_id().toString() + ",");
    			bw.write("\"" + dengon.getDengon_name().toString() + "\"");
				bw.newLine();
	            i++;
	            // プログレスダイアログの値を設定します
	            progressDialog.setProgress(100*i/DengonList.size());
			}

	        bw.close();

		} catch (FileNotFoundException e) {
			// Fileオブジェクト生成時の例外捕捉
		    e.printStackTrace();
	    } catch (IOException e) {
		    // BufferedWriterオブジェクトのクローズ時の例外捕捉
		    e.printStackTrace();
		}
	}
}
