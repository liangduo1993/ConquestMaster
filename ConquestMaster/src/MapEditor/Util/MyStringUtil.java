package MapEditor.Util;

import java.io.File;

public class MyStringUtil {
	public static boolean isNumeric(String str) {
		for (int i = 0; i < str.length(); i++) {
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static String getMapPath(File path){
		File dirf = path.getParentFile();
		String dir = dirf.getAbsolutePath() + File.separator;
		String fn = path.getName();
		if (fn.indexOf('.') == -1) {
			fn = fn + ".map";
		}
		return dir + fn;
	}
}
