package inotec.jiji.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import inotec.jiji.db.Nokanri;
import inotec.jiji.db.NokanriDao;
import inotec.jiji.utils.Constants;

public class CsvLib{

	private String Path = Environment.getExternalStorageDirectory().getPath() + Constants.BASE_PATH;

    /**
     * コンストラクタ
     */
    public CsvLib() {
        super();
    }
    /**
     * コンストラクタ
     * @param base 起点になるディレクトリ、又はファイル
     */
    public CsvLib(String Path) {
        super();
        this.Path = Path;
    }
	/**
	 * CSV解析を行うタスク
	 */
	public boolean CSVParser_Denno(Context context, ProgressDialog progressDialog) {
		boolean result = true;

		progressDialog.setProgress(0);
	    try {
			File csv = new File(this.Path + "tb_denno.csv"); // CSVデータファイル

	        BufferedReader br = new BufferedReader(new FileReader(csv));

			// 読み込む
	        int len = 0;
	        String line = "";
	        if ((line = br.readLine()) != null) {

				Nokanri nokanri = new Nokanri();
				nokanri.setItem_id("den_no");
				nokanri.setStart_no(Long.parseLong(line));
				// update
				NokanriDao dao = new NokanriDao(context);
				dao.update( nokanri);
				dao = null;
				nokanri = null;
	            // プログレスダイアログの値を設定します
				len += line.length()+2;
	            progressDialog.setProgress((int)(100*len/csv.length()));
	        }
	        br.close();

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
	 * CSV解析を行うタスク
	 */
	public boolean CSVParser_Tagno(Context context, ProgressDialog progressDialog) {
		boolean result = true;

		progressDialog.setProgress(0);
	    try {
			File csv = new File(this.Path + "tb_tagno.csv"); // CSVデータファイル

	        BufferedReader br = new BufferedReader(new FileReader(csv));

			// 読み込む
	        int len = 0;
	        String line = "";
	        if ((line = br.readLine()) != null) {

				Nokanri nokanri = new Nokanri();
				nokanri.setItem_id("tag_no");
				nokanri.setStart_no(Long.parseLong(line));
				// update
				NokanriDao dao = new NokanriDao(context);
				dao.update( nokanri);
				dao = null;
				nokanri = null;
	            // プログレスダイアログの値を設定します
				len += line.length()+2;
	            progressDialog.setProgress((int)(100*len/csv.length()));
	        }
	        br.close();

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
	 * SDカードのファイルを削除
	 */
	public boolean Delete(String table_name, ProgressDialog progressDialog) {
    	boolean result = true;

		progressDialog.setProgress(0);
		try	{
			File csv = new File(this.Path + table_name); // データファイル
		    if (csv.exists()){
		    	csv.delete();
		    }
		}
    	catch (Exception e)	{
    		result = false;
    	}
    	return result;
	}
    /**
     * CSVデータの１行分の文字列を解析しリストに格納して返却します。
     * @param csvLine １行分の文字列
     * @return １行分のデータの解析結果を格納したList
     */
	public List<String> getCsvLineList(String csvLine){
        /** 文字コード (default:UTF-8) */
        String encode = "UTF-8";
        /** CSV区切り文字 (カンマ) */
        String separator = ",";
        /** ダブルクォート */
        String enclose = "\"";

        List<String> list = new ArrayList<String>();

        int charCnt = 0;
        int charIdx = 0;

        StringBuilder builder = new StringBuilder();
        while (charCnt < csvLine.length())
        {

            boolean isDoubleQuote =
                csvLine.substring(charCnt, charCnt+1).equals(enclose);

            if (isDoubleQuote) ++charCnt;

            charIdx = csvLine.indexOf(isDoubleQuote
                                        ? enclose
                                        : separator , charCnt);

            if (charIdx < 0) {
                charIdx = csvLine.length();
            }

            String workStr = csvLine.substring(charCnt, charIdx);

            if (isDoubleQuote
                && charIdx < csvLine.length() - 1
                && csvLine.substring(charIdx + 1, charIdx + 2).equals(enclose)){
                // 文字列内のクォートと判断
                builder.append(workStr);
                builder.append(enclose);
                charCnt = charIdx + 1;

            }else{
                list.add(builder.toString() + workStr);
                builder.setLength(0);
                charCnt = charIdx + (isDoubleQuote ? 2 : 1);
            }

            // 最後の文字が"SEPARATOR" modify:2008.04.16
            if (csvLine.length() == charCnt) {
                String lastStr = csvLine.substring(charCnt - 1, charCnt);
                if (lastStr.equals(separator)) {
                    list.add("");
                }

            }
        }

        return list;
    }
	/**
	 * コピー元のパス[srcPath]から、コピー先のパス[destPath]へ
	 * ファイルのコピーを行います。
	 * コピー処理にはFileChannel#transferToメソッドを利用します。
	 * 尚、コピー処理終了後、入力・出力のチャネルをクローズします。
	 * @param srcPath    コピー元のパス
	 * @param destPath    コピー先のパス
	 * @throws IOException    何らかの入出力処理例外が発生した場合
	 */
	public void copyTransfer(String srcPath, String destPath)
	    throws IOException {

	    FileChannel srcChannel = new
	        FileInputStream(srcPath).getChannel();
	    FileChannel destChannel = new
	        FileOutputStream(destPath).getChannel();
	    try {
	        srcChannel.transferTo(0, srcChannel.size(), destChannel);
	    } finally {
	        srcChannel.close();
	        destChannel.close();
	    }

	}
}
