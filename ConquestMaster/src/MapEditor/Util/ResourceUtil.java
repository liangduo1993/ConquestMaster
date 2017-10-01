package MapEditor.Util;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.ImageIcon;

public class ResourceUtil
{
  public static void loadDataFromStream(InputStream in, OutputStream out, boolean closeOutput)
    throws IOException
  {
    if ((in == null) || (out == null)) {
      return;
    }
    int c;
    while ((c = in.read()) != -1)
    {
      out.write(c);
    }
    in.close();
    if (closeOutput) {
      out.close();
    }
  }
  
  public static byte[] loadDataFromStream(InputStream in)
    throws IOException
  {
    if (in == null) {
      return null;
    }
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    loadDataFromStream(in, out, true);
    return out.toByteArray();
  }
  
  public static void loadDataFromStream(Object source, String path, OutputStream out, boolean closeOutput)
    throws IOException
  {
    loadDataFromStream(source.getClass().getClassLoader().getResourceAsStream(path), out, 
      closeOutput);
  }
  
  public static ImageIcon loadImageIconFromStream(String path, boolean exceptionOnNull)
    throws IOException
  {
    InputStream in = ResourceUtil.class.getClassLoader().getResourceAsStream(path);
    if (in == null)
    {
      if (exceptionOnNull) {
        throw new IOException("Failed to load " + path + " as stream");
      }
      return null;
    }
    byte[] b = loadDataFromStream(in);
    if (b == null) {
      throw new IOException();
    }
    return new ImageIcon(b);
  }
  
  public static InputStream getResourceAsStream(String path, Class<?> clazz)
  {
    InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    if (in == null) {
      in = clazz.getClassLoader().getResourceAsStream(path);
    }
    return in;
  }
}
