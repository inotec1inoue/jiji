package inotec.jiji.com;

import android.os.StatFs;

public class SizeLib {

	// 総容量(トータルサイズ)を取得する
	public static long getTotalSize(String path){
		long size = -1;

		if( path != null ){
			StatFs fs = new StatFs(path);

			long bkSize = fs.getBlockSize();
			long bkCount = fs.getBlockCount();

			size = bkSize * bkCount;
		}
		return size;
	}

	// 空き容量(利用可能)を取得する
	public static long getAvailableSize(String path){
		long size = -1;

		if( path != null ){
			StatFs fs = new StatFs(path);

			long blockSize = fs.getBlockSize();
			long availableBlockSize = fs.getAvailableBlocks();

			size = blockSize * availableBlockSize;
		}
		return size;
	}
}