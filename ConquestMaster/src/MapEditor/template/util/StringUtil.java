package MapEditor.template.util;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil
{
  public static String fromRectangle(Rectangle rec)
  {
    if (rec == null) {
      return "";
    }
    return rec.x + " " + rec.y + " " + rec.width + " " + rec.height;
  }
  
  public static boolean equal(String a, String b)
  {
    if (a == b) {
      return true;
    }
    if ((a == null) || (b == null)) {
      return false;
    }
    return a.equals(b);
  }
  
  public static boolean equal(String a, String b, boolean equateNullAndEmptyString)
  {
    if (a == b) {
      return true;
    }
    if (equateNullAndEmptyString)
    {
      if ((a == null) && (b.isEmpty())) {
        return true;
      }
      if ((b == null) && (a.isEmpty())) {
        return true;
      }
    }
    if ((a == null) || (b == null)) {
      return false;
    }
    return a.equals(b);
  }
  
  public static boolean isBlank(String s)
  {
    return (s == null) || (s.length() == 0);
  }
  
  public static String lookup(String[] sa, int index)
  {
    return lookup(sa, index, null);
  }
  
  public static String lookup(String[] sa, int index, String def)
  {
    if ((index >= 0) && (index <= sa.length)) {
      return sa[index];
    }
    return def;
  }
  
  public static boolean parseBoolean(String str, boolean def)
  {
    if (str == null) {
      return def;
    }
    if ((str.toLowerCase().equals("true")) || (str.toLowerCase().equals("t")) || 
      (str.toLowerCase().equals("yes")) || (str.toLowerCase().equals("y"))) {
      return true;
    }
    if ((str.toLowerCase().equals("false")) || (str.toLowerCase().equals("f")) || 
      (str.toLowerCase().equals("no")) || (str.toLowerCase().equals("n"))) {
      return false;
    }
    return def;
  }
  
//  public static ArrayList<String> parseDelimitedEncodedString(String des, String delimiter, boolean encoded)
//  {
//    ArrayList<String> al = new ArrayList();
//    StringTokenizer st = new StringTokenizer(des, delimiter);
//    while (st.hasMoreTokens()) {
//      if (encoded) {
//        al.add(new String(Base64Util.base64ToByteArray(st.nextToken())));
//      } else {
//        al.add(st.nextToken());
//      }
//    }
//    return al;
//  }
  
  public static ArrayList<Object> parseDelimitedString(String str, String regEx, boolean includeEmpty, boolean trim)
  {
    ArrayList<Object> list = new ArrayList();
    if (str != null)
    {
      String[] sa = str.split(regEx);
      for (int i = 0; i < sa.length; i++)
      {
        if (trim) {
          sa[i] = sa[i].trim();
        }
        if ((includeEmpty) || (sa[i].length() > 0)) {
          list.add(sa[i]);
        }
      }
    }
    return list;
  }
  
  public static float parseFloat(String str, float def)
  {
    if (str == null) {
      return def;
    }
    try
    {
      return Float.parseFloat(str);
    }
    catch (NumberFormatException e) {}
    return def;
  }
  
  public static int parseInt(String str, int def)
  {
    if (str == null) {
      return def;
    }
    try
    {
      return Integer.parseInt(str);
    }
    catch (NumberFormatException e) {}
    return def;
  }
  
  public static long parseLong(String str, long def)
  {
    if (str == null) {
      return def;
    }
    try
    {
      return Long.parseLong(str);
    }
    catch (NumberFormatException e) {}
    return def;
  }
  
  public static Rectangle parseRectangle(String str, Rectangle def)
  {
    if ((str == null) || (str.length() == 0)) {
      return def;
    }
    try
    {
      StringTokenizer st = new StringTokenizer(str);
      return new Rectangle(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), 
        Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    }
    catch (Exception e) {}
    return def;
  }
  
  public static boolean parseTrueFalse(String str, boolean def)
  {
    if (str == null) {
      return def;
    }
    if (str.toLowerCase().equals("true")) {
      return true;
    }
    if (str.toLowerCase().equals("false")) {
      return false;
    }
    return def;
  }
  
  public static boolean parseYesNo(String str, boolean def)
  {
    if (str == null) {
      return def;
    }
    if (str.toLowerCase().equals("yes")) {
      return true;
    }
    if (str.toLowerCase().equals("no")) {
      return false;
    }
    return def;
  }
  
  public static String replace(String source, String s1, String s2)
  {
    if (source == null)
    {
      if (s1 == null) {
        return s2;
      }
      return null;
    }
    if (s1 == null) {
      return source;
    }
    if (s2 == null) {
      s2 = "";
    }
    int loc = source.indexOf(s1);
    if (loc == -1) {
      return source;
    }
    String temp = source.substring(0, loc) + s2 + source.substring(loc + s1.length());
    return temp;
  }
  
  public static String tail(String s, int len)
  {
    if (s.length() <= len) {
      return s;
    }
    if (len < 1) {
      return "";
    }
    return s.substring(s.length() - len, s.length());
  }
  
  public static boolean wildcardMatch(String str, String spec)
  {
    if ((spec == null) || (str == null)) {
      return spec == str;
    }
    int wild = spec.indexOf('*');
    if (wild == 0) {
      return true;
    }
    if (wild == -1) {
      return spec.equals(str);
    }
    return spec.substring(0, wild).equals(str.substring(0, wild));
  }
  
  public static String[] toStringArray(List<Object> list)
  {
    String[] sa = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      sa[i] = list.get(i).toString();
    }
    return sa;
  }
}
