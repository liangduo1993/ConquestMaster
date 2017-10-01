package MapEditor.Util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class ComponentUtil
{
  public static void boundOnScreen(Component target)
  {
    if (target == null) {
      return;
    }
    Rectangle nr = boundOnScreen(target.getBounds());
    if (nr != null) {
      target.setBounds(nr);
    }
  }
  
  public static Rectangle boundOnScreen(Rectangle tdim)
  {
    Dimension sdim = Toolkit.getDefaultToolkit().getScreenSize();
    if ((sdim == null) || (tdim == null) || ((tdim.width < 0) && (tdim.height < 0))) {
      return null;
    }
    if ((tdim.x > 0) && (tdim.y > 0) && (tdim.x + tdim.width - 1 <= sdim.width) && 
      (tdim.y + tdim.height - 1 <= sdim.height)) {
      return null;
    }
    int nw = tdim.width > sdim.width ? sdim.width : tdim.width;
    int nh = tdim.height > sdim.height ? sdim.height : tdim.height;
    
    return new Rectangle(0, 0, nw, nh);
  }
  
  public static void center(Component target, Component centerComp)
  {
    if ((centerComp != null) && (centerComp.isShowing()))
    {
      Point p = centerComp.getLocationOnScreen();
      int nx = p.x + (centerComp.getBounds().width - target.getBounds().width) / 2;
      int ny = p.y + (centerComp.getBounds().height - target.getBounds().height) / 2;
      if ((nx >= 0) && (ny >= 0))
      {
        target.setLocation(nx, ny);
        return;
      }
    }
    Dimension sdim = Toolkit.getDefaultToolkit().getScreenSize();
    int nx = (sdim.width - target.getBounds().width) / 2;
    int ny = (sdim.height - target.getBounds().height) / 2;
    if (nx < 0) {
      nx = 0;
    }
    if (ny < 0) {
      ny = 0;
    }
    target.setLocation(nx, ny);
  }
  
  public static void center(Component target)
  {
    center(target, null);
  }
  
  public static void labelLayout(Container parent)
  {
    int rows = (int)Math.ceil(parent.getComponentCount() / 2.0D);
    SpringUtilities.makeCompactGrid(parent, rows, 2, 3, 3, 3, 3);
  }
}

