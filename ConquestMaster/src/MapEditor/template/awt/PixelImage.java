package MapEditor.template.awt;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;

import MapEditor.template.util.IntQueue;

public class PixelImage
{
  public PixelImage() {}
  
  public static class FillData
  {
    public ArrayList<Point> edges = null;
    public long pixelsFilled = -1L;
    public long totalX;
    public long totalY;
    
    public FillData(boolean findEdges, boolean findStatistics)
    {
      if (findEdges) {
        this.edges = new ArrayList();
      }
      if (findStatistics) {
        this.pixelsFilled = 0L;
      }
    }
    
    public Point getWeightedCenter()
    {
      if (this.pixelsFilled == -1L) {
        return null;
      }
      return new Point((int)(this.totalX / this.pixelsFilled), (int)(this.totalY / this.pixelsFilled));
    }
    
    void addPixel(int x, int y)
    {
      this.totalX += x;
      this.totalY += y;
      this.pixelsFilled += 1L;
    }
  }
  
  int imageWidth = 0;
  int imageHeight = 0;
  IntQueue pixq = null;
  MemoryImageSource memImageSource = null;
  int[] pixels;
  private boolean dirty;
  
  public PixelImage(int imageWidth, int imageHeight)
  {
    this(imageWidth, imageHeight, null);
  }
  
  public PixelImage(int imageWidth, int imageHeight, int[] pixels)
  {
    this.imageWidth = imageWidth;
    this.imageHeight = imageHeight;
    if (pixels == null)
    {
      pixels = new int[imageWidth * imageHeight];
    }
    else
    {
      if (pixels.length != imageWidth * imageHeight) {
        throw new IllegalArgumentException(
          "The size of the specified pixel array must be equal to imageWidth * imageHeight");
      }
      this.pixels = pixels;
    }
  }
  
  public PixelImage(int imageWidth, int imageHeight, Image image)
    throws PixelImageException
  {
    this.imageWidth = imageWidth;
    this.imageHeight = imageHeight;
    this.pixels = new int[imageWidth * imageHeight];
    
    PixelGrabber pg = new PixelGrabber(image, 0, 0, imageWidth, imageHeight, this.pixels, 0, imageWidth);
    try
    {
      pg.grabPixels();
    }
    catch (InterruptedException localInterruptedException) {}
    if (((pg.status() & 0x80) != 0) || ((pg.status() & 0x40) != 0)) {
      throw new PixelImageException("Pixels could not be grabbed. Status code=" + pg.status());
    }
  }
  
  public MemoryImageSource getMemoryImageSource()
  {
    if (this.memImageSource == null) {
      this.memImageSource = new MemoryImageSource(this.imageWidth, this.imageHeight, this.pixels, 0, this.imageWidth);
    }
    return this.memImageSource;
  }
  
  public void clean()
  {
    if ((this.dirty) && (this.memImageSource != null))
    {
      this.memImageSource.newPixels(0, 0, this.imageWidth, this.imageHeight);
      this.dirty = false;
    }
  }
  
  public boolean isDirty()
  {
    return this.dirty;
  }
  
  public int getPixel(int x, int y)
  {
    return this.pixels[(y * this.imageWidth + x)];
  }
  
  public void setPixel(int x, int y, int color)
  {
    if (this.pixels[(y * this.imageWidth + x)] != color)
    {
      this.pixels[(y * this.imageWidth + x)] = color;
      this.dirty = true;
    }
  }
  
  private void setPix(int x, int y, int color, FillData data)
  {
    this.pixels[(y * this.imageWidth + x)] = color;
    if (data != null) {
      data.addPixel(x, y);
    }
  }
  
  public void floodFill(int x, int y, Color fillColor)
  {
    fillImpl(x, y, fillColor, null, true, null, null);
  }
  
  public ArrayList<Point> floodFill(int x, int y, Color fillColor, Color edgeColor, FillData data)
  {
    if (data == null) {
      data = new FillData(true, false);
    }
    fillImpl(x, y, fillColor, null, true, edgeColor, data);
    return data.edges;
  }
  
  public void replaceFill(int x, int y, Color fillColor, Color targetColor)
  {
    fillImpl(x, y, fillColor, targetColor, true, null, null);
  }
  
  public ArrayList<Point> replaceFill(int x, int y, Color fillColor, Color targetColor, Color edgeColor, FillData data)
  {
    if (data == null) {
      data = new FillData(true, false);
    }
    fillImpl(x, y, fillColor, targetColor, true, edgeColor, data);
    return data.edges;
  }
  
  public void borderFill(int x, int y, Color fillColor, Color borderColor)
  {
    fillImpl(x, y, fillColor, borderColor, false, null, null);
  }
  
  protected void fillImpl(int x, int y, Color fillClr, Color targetClr, boolean replaceMode, Color highlightEdgeColor, FillData statData)
  {
    if ((this.pixels == null) || (fillClr == null) || ((targetClr == null) && (!replaceMode))) {
      return;
    }
    if ((x >= this.imageWidth) || (y >= this.imageHeight) || (x < 0) || (y < 0)) {
      return;
    }
    try
    {
      boolean findEdges = (statData != null) && (statData.edges != null);
      FillData data = (statData != null) && (statData.pixelsFilled != -1L) ? statData : null;
      
      int fillColor = fillClr.getRGB();
      int targetColor;
      if (replaceMode)
      {
        if (targetClr == null) {
          targetColor = getPixel(x, y);
        } else {
          targetColor = targetClr.getRGB();
        }
        int pixColor = getPixel(x, y);
        if ((pixColor == targetColor) && (pixColor != fillColor)) {}
      }
      else
      {
        targetColor = targetClr.getRGB();
        if (getPixel(x, y) == targetColor) {
          return;
        }
      }
      this.dirty = true;
      if (this.pixq == null) {
        this.pixq = new IntQueue(100, 1000000);
      }
      this.pixq.push((y << 16) + x);
      
      setPix(x, y, fillColor, data);
      while (!this.pixq.isEmpty())
      {
        int nextCoor = this.pixq.pop();
        x = nextCoor & 0xFFFF;
        y = nextCoor >> 16 & 0xFFFF;
        boolean isEdge = false;
        if (replaceMode)
        {
          if (x > 0)
          {
            int pixColor = getPixel(x - 1, y);
            if ((pixColor != fillColor) && (pixColor == targetColor))
            {
              setPix(x - 1, y, fillColor, data);
              this.pixq.push((y << 16) + x - 1);
            }
            else if (pixColor != fillColor)
            {
              isEdge = true;
            }
          }
          else
          {
            isEdge = true;
          }
          if (y > 0)
          {
            int pixColor = getPixel(x, y - 1);
            if ((pixColor != fillColor) && (pixColor == targetColor))
            {
              setPix(x, y - 1, fillColor, data);
              this.pixq.push((y - 1 << 16) + x);
            }
            else if (pixColor != fillColor)
            {
              isEdge = true;
            }
          }
          else
          {
            isEdge = true;
          }
          if (x < this.imageWidth - 1)
          {
            int pixColor = getPixel(x + 1, y);
            if ((pixColor != fillColor) && (pixColor == targetColor))
            {
              setPix(x + 1, y, fillColor, data);
              this.pixq.push((y << 16) + x + 1);
            }
            else if (pixColor != fillColor)
            {
              isEdge = true;
            }
          }
          else
          {
            isEdge = true;
          }
          if (y < this.imageHeight - 1)
          {
            int pixColor = getPixel(x, y + 1);
            if ((pixColor != fillColor) && (pixColor == targetColor))
            {
              setPix(x, y + 1, fillColor, data);
              this.pixq.push((y + 1 << 16) + x);
            }
            else if (pixColor != fillColor)
            {
              isEdge = true;
            }
          }
          else
          {
            isEdge = true;
          }
        }
        else
        {
          if (x > 0)
          {
            int pixColor = getPixel(x - 1, y);
            if ((pixColor != fillColor) && (pixColor != targetColor))
            {
              setPix(x - 1, y, fillColor, data);
              this.pixq.push((y << 16) + x - 1);
            }
            else if (pixColor != fillColor)
            {
              isEdge = true;
            }
          }
          else
          {
            isEdge = true;
          }
          if (y > 0)
          {
            int pixColor = getPixel(x, y - 1);
            if ((pixColor != fillColor) && (pixColor != targetColor))
            {
              setPix(x, y - 1, fillColor, data);
              this.pixq.push((y - 1 << 16) + x);
            }
            else if (pixColor != fillColor)
            {
              isEdge = true;
            }
          }
          else
          {
            isEdge = true;
          }
          if (x < this.imageWidth - 1)
          {
            int pixColor = getPixel(x + 1, y);
            if ((pixColor != fillColor) && (pixColor != targetColor))
            {
              setPix(x + 1, y, fillColor, data);
              this.pixq.push((y << 16) + x + 1);
            }
            else if (pixColor != fillColor)
            {
              isEdge = true;
            }
          }
          else
          {
            isEdge = true;
          }
          if (y < this.imageHeight - 1)
          {
            int pixColor = getPixel(x, y + 1);
            if ((pixColor != fillColor) && (pixColor != targetColor))
            {
              setPix(x, y + 1, fillColor, data);
              this.pixq.push((y + 1 << 16) + x);
            }
            else if (pixColor != fillColor)
            {
              isEdge = true;
            }
          }
          else
          {
            isEdge = true;
          }
        }
        if ((findEdges) && (isEdge)) {
          statData.edges.add(new Point(x, y));
        }
      }
      if ((findEdges) && (highlightEdgeColor != null))
      {
        int hec = highlightEdgeColor.getRGB();
        for (Point p : statData.edges) {
          setPix(p.x, p.y, hec, data);
        }
      }
      return;
    }
    catch (Exception e)
    {
      System.out.println("Exception in floodfill; pixels queued: " + this.pixq.getSize());
      e.printStackTrace();
    }
  }
  
  public final int getImageWidth()
  {
    return this.imageWidth;
  }
  
  public final int getImageHeight()
  {
    return this.imageHeight;
  }
}

