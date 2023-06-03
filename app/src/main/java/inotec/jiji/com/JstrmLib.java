package inotec.jiji.com;

public class JstrmLib {

	/*
	 * 全角を半角に変換する
	 */
	 public static String ZenToHan(String s) {
		 // 全角アルファベットを半角アルファベットに変換する
		 s = ZenAlphaToHanAlpha(s);
		 // 全角数字を半角数字に変換する
		 s = ZenNumToHanNum(s);
		 // 全角ひらがなを全角カタカナへ変換する
		 s = ZenHiraToZenKata(s);
		 // 全角カタカナを半角カタカナに変換する
		 s = ZenKataToHanKata(s);
		 return s;
	 }
	/*
	 * 全角アルファベットを半角アルファベットに変換する
	 */
	 public static String ZenAlphaToHanAlpha(String s) {
		 StringBuffer sb = new StringBuffer(s);
		 for (int i = 0; i < sb.length(); i++) {
			 char c = sb.charAt(i);
			 if (c >= 'ａ' && c <= 'ｚ') {
				 sb.setCharAt(i, (char) (c - 'ａ' + 'a'));
			 } else if (c >= 'Ａ' && c <= 'Ｚ') {
				 sb.setCharAt(i, (char) (c - 'Ａ' + 'A'));
			 }
		 }
		 return sb.toString();
	 }
	 /*
	  * 全角数字を半角数字に変換する
	  */
	 public static String ZenNumToHanNum(String s) {
		 StringBuffer sb = new StringBuffer(s);
		 for (int i = 0; i < sb.length(); i++) {
			 char c = sb.charAt(i);
			 if (c >= '０' && c <= '９') {
				 sb.setCharAt(i, (char)(c - '０' + '0'));
			 }
		 }
		 return sb.toString();
	 }
	 /*
	  * 全角カタカナを半角カタカナに変換する
	  */
	 private static final char[] ZENKAKU_KATAKANA = { 'ァ', 'ア', 'ィ', 'イ', 'ゥ',
		 'ウ', 'ェ', 'エ', 'ォ', 'オ', 'カ', 'ガ', 'キ', 'ギ', 'ク', 'グ', 'ケ', 'ゲ',
		 'コ', 'ゴ', 'サ', 'ザ', 'シ', 'ジ', 'ス', 'ズ', 'セ', 'ゼ', 'ソ', 'ゾ', 'タ',
		 'ダ', 'チ', 'ヂ', 'ッ', 'ツ', 'ヅ', 'テ', 'デ', 'ト', 'ド', 'ナ', 'ニ', 'ヌ',
		 'ネ', 'ノ', 'ハ', 'バ', 'パ', 'ヒ', 'ビ', 'ピ', 'フ', 'ブ', 'プ', 'ヘ', 'ベ',
		 'ペ', 'ホ', 'ボ', 'ポ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ャ', 'ヤ', 'ュ', 'ユ',
		 'ョ', 'ヨ', 'ラ', 'リ', 'ル', 'レ', 'ロ', 'ヮ', 'ワ', 'ヰ', 'ヱ', 'ヲ', 'ン',
		 'ヴ', 'ヵ', 'ヶ' };

	 private static final String[] HANKAKU_KATAKANA = { "ｧ", "ｱ", "ｨ", "ｲ", "ｩ",
		 "ｳ", "ｪ", "ｴ", "ｫ", "ｵ", "ｶ", "ｶﾞ", "ｷ", "ｷﾞ", "ｸ", "ｸﾞ", "ｹ",
		 "ｹﾞ", "ｺ", "ｺﾞ", "ｻ", "ｻﾞ", "ｼ", "ｼﾞ", "ｽ", "ｽﾞ", "ｾ", "ｾﾞ", "ｿ",
		 "ｿﾞ", "ﾀ", "ﾀﾞ", "ﾁ", "ﾁﾞ", "ｯ", "ﾂ", "ﾂﾞ", "ﾃ", "ﾃﾞ", "ﾄ", "ﾄﾞ",
		 "ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾊﾞ", "ﾊﾟ", "ﾋ", "ﾋﾞ", "ﾋﾟ", "ﾌ",
		 "ﾌﾞ", "ﾌﾟ", "ﾍ", "ﾍﾞ", "ﾍﾟ", "ﾎ", "ﾎﾞ", "ﾎﾟ", "ﾏ", "ﾐ", "ﾑ", "ﾒ",
		 "ﾓ", "ｬ", "ﾔ", "ｭ", "ﾕ", "ｮ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ",
		 "ﾜ", "ｲ", "ｴ", "ｦ", "ﾝ", "ｳﾞ", "ｶ", "ｹ" };

	 private static final char ZENKAKU_KATAKANA_FIRST_CHAR = ZENKAKU_KATAKANA[0];

	 private static final char ZENKAKU_KATAKANA_LAST_CHAR = ZENKAKU_KATAKANA[ZENKAKU_KATAKANA.length - 1];

	 public static String ZenKataToHanKata(char c) {
		 if (c >= ZENKAKU_KATAKANA_FIRST_CHAR && c <= ZENKAKU_KATAKANA_LAST_CHAR) {
			 return HANKAKU_KATAKANA[c - ZENKAKU_KATAKANA_FIRST_CHAR];
		 } else {
			 return String.valueOf(c);
		 }
	 }

	 public static String ZenKataToHanKata(String s) {
		 StringBuffer sb = new StringBuffer();
		 for (int i = 0; i < s.length(); i++) {
			 char originalChar = s.charAt(i);
			 String convertedChar = ZenKataToHanKata(originalChar);
			 sb.append(convertedChar);
		 }
		 return sb.toString();

	 }
	 /*
	  * 全角ひらがなを全角カタカナへ変換する
	  */
	 public static String ZenHiraToZenKata(String s) {
		 StringBuffer sb = new StringBuffer(s);
		 for (int i = 0; i < sb.length(); i++) {
			 char c = sb.charAt(i);
			 if (c >= 'ぁ' && c <= 'ん') {
				 sb.setCharAt(i, (char)(c - 'ぁ' + 'ァ'));
			 }
		 }
		 return sb.toString();
	 }
}
