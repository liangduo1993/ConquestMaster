package MapEditor.template.util;


import java.awt.Color;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;

public class ExtendedProperties
  extends Properties
{
  private static final long serialVersionUID = -19504867258791872L;
  private String defaultIndicatorValue = null;
  
  public ExtendedProperties() {}
  
  public ExtendedProperties(Properties defaults)
  {
    super(defaults);
  }
  
  public String getProperty(String key)
  {
    String p = super.getProperty(key);
    if ((p != null) && (this.defaultIndicatorValue != null) && (this.defaultIndicatorValue.equals(p))) {
      return null;
    }
    return p;
  }
  
  public int getIntProperty(String key, int defaultValue)
  {
    return StringUtil.parseInt(getProperty(key), defaultValue);
  }
  
  public boolean getBooleanProperty(String key, boolean defaultValue)
  {
    String val = getProperty(key);
    if (val != null) {
      return StringUtil.parseBoolean(val, defaultValue);
    }
    return defaultValue;
  }
  
  public boolean getTrueFalseProperty(String key, boolean defaultValue)
  {
    String val = getProperty(key);
    if (val != null) {
      return StringUtil.parseTrueFalse(val, defaultValue);
    }
    return defaultValue;
  }
  
  public boolean getYesNoProperty(String key, boolean defaultValue)
  {
    String val = getProperty(key);
    if (val != null) {
      return StringUtil.parseYesNo(val, defaultValue);
    }
    return defaultValue;
  }
  
  public Color getColorProperty(String key, Color defaultValue)
  {
    String val = getProperty(key);
    if (val != null) {
      try
      {
        StringTokenizer st = new StringTokenizer(val);
        int r = StringUtil.parseInt(st.nextToken(), -1);
        int g = StringUtil.parseInt(st.nextToken(), -1);
        int b = StringUtil.parseInt(st.nextToken(), -1);
        if ((r < 0) || (g < 0) || (b < 0) || (r > 255) || (g > 255) || (b > 255)) {
          return defaultValue;
        }
        return new Color(r, g, b);
      }
      catch (NoSuchElementException e)
      {
        return defaultValue;
      }
    }
    return defaultValue;
  }
  
  public synchronized Object setProperty(String key, Color value)
  {
    return put(key, value.getRed() + " " + value.getGreen() + " " + value.getBlue());
  }
  
  public synchronized Object setProperty(String key, String value)
  {
    return put(key, value);
  }
  
  public synchronized Object setProperty(String key, boolean value)
  {
    return put(key, value ? "true" : "false");
  }
  
  public synchronized Object setProperty(String key, int value)
  {
    return put(key, value);
  }
  
  public final String getDefaultIndicatorValue()
  {
    return this.defaultIndicatorValue;
  }
  
  public final void setDefaultIndicatorValue(String defaultIndicatorValue)
  {
    this.defaultIndicatorValue = defaultIndicatorValue;
  }
}
