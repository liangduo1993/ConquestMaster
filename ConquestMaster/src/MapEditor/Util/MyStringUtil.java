package MapEditor.Util;

import java.io.File;

public class MyStringUtil {
	private static final String[] TYPES= new String[]{".jpeg", ".png", "jpg", ".bmp"};

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
		}else{
			fn = fn.substring(0, fn.indexOf('.'));
			fn += ".map";
		}
		return dir + fn;
	}
	
	public static boolean checkType(File path){
		for(String type: TYPES){
			if(path.getName().contains(type))
				return true;
		}
		return false;
	}
	
	
}
