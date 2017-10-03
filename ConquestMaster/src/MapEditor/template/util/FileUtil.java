package MapEditor.template.util;


import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;

public class FileUtil
{
  public static String[] filterMatch(String directory, String spec, boolean fullPaths)
  {
    if ((directory == null) || (spec == null)) {
      return null;
    }
    File dir = new File(directory);
    if (!dir.isDirectory()) {
      return null;
    }
    File[] files = dir.listFiles();
    if ((files == null) || (files.length == 0)) {
      return new String[0];
    }
    Vector<String> results = new Vector();
    for (int i = 0; i < files.length; i++)
    {
      String item;
      if (fullPaths) {
        item = new File(dir, files[i].getName()).getPath();
      } else {
        item = files[i].getName();
      }
      if ((files[i].isFile()) && (fileWildcardMatch(files[i].getName(), spec))) {
        results.addElement(item);
      }
    }
    String[] ret = new String[results.size()];
    if (results.size() > 0) {
      for (int i = 0; i < results.size(); i++) {
        ret[i] = ((String)results.elementAt(i));
      }
    }
    return ret;
  }
  
  public static boolean fileWildcardMatch(String filename, String spec)
  {
    String[] fns = extensionSplit(filename);
    String[] sps = extensionSplit(spec);
    
    return (StringUtil.wildcardMatch(fns[0], sps[0])) && ((sps[1] == "") || (StringUtil.wildcardMatch(fns[1], sps[1])));
  }
  
  private static String[] extensionSplit(String filename)
  {
    String[] s = new String[2];
    int i = filename.indexOf('.');
    if (i == -1)
    {
      s[0] = filename;
      s[1] = "";
    }
    else
    {
      s[0] = filename.substring(0, i);
      if (i < filename.length() - 1) {
        s[1] = filename.substring(i + 1);
      } else {
        s[1] = "";
      }
    }
    return s;
  }
  
  public static File searchPath(String filename, String path)
  {
    if (path == null) {
      path = System.getProperty("java.library.path");
    }
    if (path == null) {
      return null;
    }
    StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
    while (st.hasMoreTokens())
    {
      String dir = st.nextToken();
      File f = new File(dir, filename);
      if (f.exists()) {
        return f;
      }
    }
    return null;
  }
  
//  /* Error */
//  public static boolean copyFile(File sourceFile, File destFile)
//    throws java.io.IOException
//  {
//    // Byte code:
//    //   0: aload_1
//    //   1: aload_0
//    //   2: invokevirtual 150	java/io/File:equals	(Ljava/lang/Object;)Z
//    //   5: ifeq +5 -> 10
//    //   8: iconst_0
//    //   9: ireturn
//    //   10: aload_1
//    //   11: invokevirtual 135	java/io/File:exists	()Z
//    //   14: ifeq +36 -> 50
//    //   17: aload_1
//    //   18: invokevirtual 21	java/io/File:isDirectory	()Z
//    //   21: ifeq +29 -> 50
//    //   24: aload_1
//    //   25: aload_0
//    //   26: invokevirtual 154	java/io/File:getParentFile	()Ljava/io/File;
//    //   29: invokevirtual 150	java/io/File:equals	(Ljava/lang/Object;)Z
//    //   32: ifeq +5 -> 37
//    //   35: iconst_0
//    //   36: ireturn
//    //   37: new 16	java/io/File
//    //   40: dup
//    //   41: aload_1
//    //   42: aload_0
//    //   43: invokevirtual 34	java/io/File:getName	()Ljava/lang/String;
//    //   46: invokespecial 38	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
//    //   49: astore_1
//    //   50: aload_1
//    //   51: invokevirtual 135	java/io/File:exists	()Z
//    //   54: ifne +8 -> 62
//    //   57: aload_1
//    //   58: invokevirtual 158	java/io/File:createNewFile	()Z
//    //   61: pop
//    //   62: aconst_null
//    //   63: astore_2
//    //   64: aconst_null
//    //   65: astore_3
//    //   66: new 161	java/io/FileInputStream
//    //   69: dup
//    //   70: aload_0
//    //   71: invokespecial 163	java/io/FileInputStream:<init>	(Ljava/io/File;)V
//    //   74: invokevirtual 166	java/io/FileInputStream:getChannel	()Ljava/nio/channels/FileChannel;
//    //   77: astore_2
//    //   78: new 170	java/io/FileOutputStream
//    //   81: dup
//    //   82: aload_1
//    //   83: invokespecial 172	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
//    //   86: invokevirtual 173	java/io/FileOutputStream:getChannel	()Ljava/nio/channels/FileChannel;
//    //   89: astore_3
//    //   90: aload_3
//    //   91: aload_2
//    //   92: lconst_0
//    //   93: aload_2
//    //   94: invokevirtual 174	java/nio/channels/FileChannel:size	()J
//    //   97: invokevirtual 179	java/nio/channels/FileChannel:transferFrom	(Ljava/nio/channels/ReadableByteChannel;JJ)J
//    //   100: pop2
//    //   101: goto +24 -> 125
//    //   104: astore 4
//    //   106: aload_2
//    //   107: ifnull +7 -> 114
//    //   110: aload_2
//    //   111: invokevirtual 183	java/nio/channels/FileChannel:close	()V
//    //   114: aload_3
//    //   115: ifnull +7 -> 122
//    //   118: aload_3
//    //   119: invokevirtual 183	java/nio/channels/FileChannel:close	()V
//    //   122: aload 4
//    //   124: athrow
//    //   125: aload_2
//    //   126: ifnull +7 -> 133
//    //   129: aload_2
//    //   130: invokevirtual 183	java/nio/channels/FileChannel:close	()V
//    //   133: aload_3
//    //   134: ifnull +7 -> 141
//    //   137: aload_3
//    //   138: invokevirtual 183	java/nio/channels/FileChannel:close	()V
//    //   141: iconst_1
//    //   142: ireturn
//    // Line number table:
//    //   Java source line #123	-> byte code offset #0
//    //   Java source line #124	-> byte code offset #8
//    //   Java source line #125	-> byte code offset #10
//    //   Java source line #126	-> byte code offset #24
//    //   Java source line #127	-> byte code offset #35
//    //   Java source line #133	-> byte code offset #37
//    //   Java source line #137	-> byte code offset #50
//    //   Java source line #138	-> byte code offset #57
//    //   Java source line #140	-> byte code offset #62
//    //   Java source line #141	-> byte code offset #64
//    //   Java source line #143	-> byte code offset #66
//    //   Java source line #144	-> byte code offset #78
//    //   Java source line #145	-> byte code offset #90
//    //   Java source line #147	-> byte code offset #104
//    //   Java source line #148	-> byte code offset #106
//    //   Java source line #149	-> byte code offset #110
//    //   Java source line #151	-> byte code offset #114
//    //   Java source line #152	-> byte code offset #118
//    //   Java source line #154	-> byte code offset #122
//    //   Java source line #148	-> byte code offset #125
//    //   Java source line #149	-> byte code offset #129
//    //   Java source line #151	-> byte code offset #133
//    //   Java source line #152	-> byte code offset #137
//    //   Java source line #155	-> byte code offset #141
//    // Local variable table:
//    //   start	length	slot	name	signature
//    //   0	143	0	sourceFile	File
//    //   0	143	1	destFile	File
//    //   63	67	2	source	java.nio.channels.FileChannel
//    //   65	73	3	destination	java.nio.channels.FileChannel
//    //   104	19	4	localObject	Object
//    // Exception table:
//    //   from	to	target	type
//    //   66	104	104	finally
//  }
}

