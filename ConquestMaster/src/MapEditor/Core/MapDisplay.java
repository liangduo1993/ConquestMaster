package MapEditor.Core;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import MapEditor.Awt.PixelImage;
import MapEditor.Awt.PixelImageException;
import MapEditor.Domain.Continent;
import MapEditor.Domain.Territory;
import MapEditor.Util.Calc;
import MapEditor.Util.ExtendedProperties;

public class MapDisplay
  extends JPanel
{
  static final String PropColorFillPlain = "ColorFillPlain";
  static final String PropColorFillSelected = "ColorFillSelected";
  static final String PropColorFillLink = "ColorFillLink";
  static final String PropColorFillLinkOneWay = "ColorFillLinkOneWay";
  static final String PropColorLinkLineExtra = "ColorLinkLineExtra";
  static final String PropColorLinkLineMissing = "ColorLinkLineMissing";
  static final String PropColorLinkLineMissingOneWay = "ColorLinkLineMissingOneWay";
  static final String PropColorTerritoryCenter = "ColorTerritoryCenter";
  static final String PropColorTerritoryEdge = "ColorTerritoryEdge";
  static final String PropColorLinkLine = "ColorLinkLine";
  static final String PropColorLinkLineOneWay = "ColorLinkLineOneWay";
  static final String PropColorContinentCircle = "ColorContinentCircle";
  static final String PropColorContinentOutlineCircle = "ColorContinentOutlineCircle";
  static final String PropColorSelectionCircle = "ColorSelectionCircle";
  private static final long serialVersionUID = -833381892178199280L;
  
  static class BridgeSettings
  {
    int[] colors;
    int minSize;
    int maxDist;
    
    BridgeSettings(int[] colors, int minSize, int maxDist)
    {
      this.colors = colors;
      this.minSize = minSize;
      this.maxDist = maxDist;
    }
  }
  
  public static enum TerritoryColorOptions
  {
    NONE,  SELECTED,  BORDERING;
  }
  
  public static enum TerritoryLinkOptions
  {
    NONE,  SELECTED,  ALL,  RECOMMENDED;
  }
  
  private static Logger logger = Logger.getLogger(ConquestMapMaker.class.getPackage().getName());
  public static final int MINHEIGHT = 50;
  public static final int MINWIDTH = 50;
  public static final int MAXHEIGHT = 5000;
  public static final int MAXWIDTH = 5000;
  static final Stroke exisingLinkLineStroke = new BasicStroke(2.0F, 1, 
    0, 10.0F, null, 0.0F);
  static final Stroke recommendedLinkLineStroke = new BasicStroke(2.0F, 1, 
    0, 10.0F, new float[] { 3.0F, 3.0F }, 0.0F);
  static final Stroke scaleStroke = new BasicStroke(3.0F, 1, 0, 
    10.0F, null, 0.0F);
  static final Color DefaultColorFillPlain = Color.WHITE;
  static final Color DefaultColorFillSelected = new Color(0, 114, 138);
  static final Color DefaultColorFillLink = new Color(0, 180, 0);
  static final Color DefaultColorFillLinkOneWay = new Color(190, 190, 0);
  static final Color DefaultColorTerritoryCenter = new Color(0, 100, 50);
  static final Color DefaultColorTerritoryEdge = new Color(0, 220, 240);
  static final Color DefaultColorFillEdgeFinder = new Color(0, 111, 133);
  static final Color DefaultColorContinentCircle = new Color(255, 200, 0);
  static final Color DefaultColorContinentOutlineCircle = new Color(120, 90, 0);
  static final Color DefaultColorSelectionCircle = new Color(0, 150, 20);
  static final Color DefaultColorLinkLine = new Color(0, 140, 0);
  static final Color DefaultColorLinkLineOneWay = new Color(150, 150, 0);
  static final Color DefaultColorLinkLineMissing = Color.GREEN;
  static final Color DefaultColorLinkLineMissingOneWay = new Color(50, 140, 0);
  static final Color DefaultColorLinkLineExtra = Color.RED;
  ConquestMap map;
  private HashSet<Territory> selectedTers;
  Color colorFillPlain;
  Color colorFillSelected;
  Color colorFillLink;
  Color colorFillLinkOneWay;
  Color colorTerritoryCenter;
  Color colorTerritoryEdge;
  Color colorFillEdgeFinder = new Color(0, 111, 133);
  Color colorContinentCircle;
  Color colorContinentOutlineCircle;
  Color colorSelectionCircle;
  Color colorLinkLine;
  Color colorLinkLineOneWay;
  Color colorLinkLineMissing;
  Color colorLinkLineMissingOneWay;
  Color colorLinkLineExtra;
  TerritoryColorOptions optionColorTerritories = TerritoryColorOptions.BORDERING;
  boolean optionDrawLinks = true;
  TerritoryLinkOptions optionDrawExistingLinks = TerritoryLinkOptions.NONE;
  TerritoryLinkOptions optionDrawRecommendedLinks = TerritoryLinkOptions.ALL;
  int imageWidth;
  int imageHeight;
  BufferedImage mapImage;
  BufferedImage mapImageUnscaled;
  boolean imageAltered;
  PixelImage pixImage;
  PixelImage pixImageOriginal;
  Image memImage;
  ArrayList<Territory> bridgeTerritories;
  HashMap<Territory, Color> curTerColors;
  boolean analyzing;
  boolean generating;
  boolean cancelAnalysis;
  
  public MapDisplay()
  {
    setDoubleBuffered(false);
    
    clearMapImage();
  }
  
//  boolean analyzeBorders(AnalyzeBordersOptions baOptions)
//    throws OperationCanceledException
//  {
//    if (logger.isLoggable(Level.FINE)) {
//      logger.fine("Analyzer - starting analysis [minimum distance=" + baOptions.maxDistance + 
//        "][minimum distance SC=" + baOptions.maxDistanceCont + "][minimum points=" + 
//        baOptions.minPoints + "]");
//    }
//    if ((this.memImage == null) || (this.mapImage == null) || (this.analyzing) || (this.generating)) {
//      return false;
//    }
//    this.analyzing = true;
//    this.cancelAnalysis = false;
//    try
//    {
//      for (Territory ter : this.map.territories) {
//        ter.clearRecommendedLinks();
//      }
//      this.bridgeTerritories = null;
//      
//      setupMemoryImage(false);
//      repaint();
//      if (baOptions.findBridges())
//      {
//        statusMessage("Locating bridges");
//        findBridges(baOptions);
//      }
//      statusMessage("Analyzing territory edges");
//      findAllTerritoryEdges();
//      
//      statusMessage("Analyzing territory proximity");
//      analyzeBordersFindLinks(baOptions);
//      for (Territory ter : this.map.territories)
//      {
//        ter.clearCachedRecommendationCalculations();
//        if (ter.getRecommendedLinks() == null) {
//          ter.clearRecommendedLinks(true);
//        }
//      }
//      return true;
//    }
//    catch (PixelImageException e)
//    {
//      logger.warning("Analyzer - unable to reset map image [" + e + "]");
//      return false;
//    }
//    finally
//    {
//      this.analyzing = false;
//      this.cancelAnalysis = false;
//      statusMessage(null);
//      refreshMemoryImage(false);
//      repaint();
//    }
//  }
//  
//  private void analyzeBordersFindLinks(AnalyzeBordersOptions baOptions)
//    throws OperationCanceledException
//  {
//    Set<Territory> checked = new HashSet();
//    
//    int total = this.map.territories.size();
//    int done = 0;
//    Iterator localIterator2;
//    int mp;
//    for (Iterator localIterator1 = this.map.territories.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
//    {
//      Territory ter1 = (Territory)localIterator1.next();
//      if (this.cancelAnalysis) {
//        throw new OperationCanceledException();
//      }
//      statusMessage("Analyzing territory proximities - " + (int)(done / total * 100.0F) + 
//        "% - " + ter1);
//      done++;
//      repaint();
//      if (logger.isLoggable(Level.FINE)) {
//        logger.fine("Analyzer - locating bordering territories [territory=" + ter1.name + "]");
//      }
//      checked.add(ter1);
//      
//      colorTerritory(ter1, this.colorTerritoryCenter);
//      paintTerritoryEdge(this.pixImage, ter1, this.colorTerritoryEdge);
//      
//      localIterator2 = getAllAreas().iterator(); continue;Territory ter2 = (Territory)localIterator2.next();
//      if (this.cancelAnalysis) {
//        throw new OperationCanceledException();
//      }
//      if (!checked.contains(ter2))
//      {
//        int md = baOptions.maxDistance;
//        mp = baOptions.minPoints;
//        if ((baOptions.findBridges()) && (ter2.isBridge))
//        {
//          md = baOptions.bridgeMaxDistance;
//          mp = 1;
//        }
//        else if (ter1.cont == ter2.cont)
//        {
//          md = baOptions.maxDistanceCont;
//        }
//        boolean near = ter1.isNear(ter2, md, mp);
//        if ((near) && (logger.isLoggable(Level.FINER))) {
//          logger.finer("Analyzer - located bordering territories [territory=" + ter1.name + 
//            "][territory=" + ter2 + "]");
//        }
//        if (near)
//        {
//          ter2.addRecommendedLink(ter1);
//          if (!ter2.isBridge) {
//            ter1.addRecommendedLink(ter2);
//          }
//        }
//      }
//    }
//    if (this.bridgeTerritories != null) {
//      for (Territory bridge : this.bridgeTerritories) {
//        if (bridge.getRecommendedLinks() != null) {
//          for (localIterator2 = bridge.getRecommendedLinks().iterator(); localIterator2.hasNext(); mp.hasNext())
//          {
//            Territory ter = (Territory)localIterator2.next();
//            mp = bridge.getRecommendedLinks().iterator(); continue;Territory ter2 = (Territory)mp.next();
//            if (ter != ter2)
//            {
//              ter.addRecommendedLink(ter2);
//              if (logger.isLoggable(Level.FINE)) {
//                logger.fine("Bridged territories connected: " + ter.name + ", " + 
//                  ter2.name);
//              }
//            }
//          }
//        }
//      }
//    }
//  }
//  
//  int analyzeGenerateTerritories(Color groundColor, int minSize, String contName, String territoryPrefix)
//    throws OperationCanceledException
//  {
//    if (logger.isLoggable(Level.FINE)) {
//      logger.fine("Territory Analyzer - starting analysis");
//    }
//    if ((this.memImage == null) || (this.mapImage == null) || (this.analyzing) || (this.generating)) {
//      return -1;
//    }
//    this.generating = true;
//    this.cancelAnalysis = false;
//    try
//    {
//      setupMemoryImage(false);
//      repaint();
//      
//      statusMessage("Filling existing territories");
//      for (Territory ter : this.map.territories) {
//        colorTerritory(ter, Color.DARK_GRAY);
//      }
//      int fertileColor = groundColor.getRGB();
//      
//      Object colonies = new ArrayList();
//      findColorAreas(new int[] { fertileColor }, minSize, (ArrayList)colonies, null, 
//        "Scanning for new territory locations");
//      if (this.cancelAnalysis) {
//        return -1;
//      }
//      if ((colonies != null) && (!((ArrayList)colonies).isEmpty()))
//      {
//        if ((contName == null) || (contName.isEmpty())) {
//          contName = "Discovered Territories";
//        }
//        Continent cont = this.map.findContinent(contName);
//        if (cont == null)
//        {
//          cont = new Continent(contName, 3);
//          this.map.addContinent(cont);
//          this.map.cmm.updateContinentData();
//        }
//        if ((territoryPrefix == null) || (territoryPrefix.isEmpty())) {
//          territoryPrefix = "Territory ";
//        }
//        int i = 0;
//        for (Point p : (ArrayList)colonies)
//        {
//          i++;
//          Territory ter = new Territory();
//          ter.setCenter(p.x, p.y);
//          ter.cont = cont;
//          i = this.map.getNextUnusedNameNum(territoryPrefix, i);
//          ter.name = (territoryPrefix + i);
//          this.map.addTerritory(ter);
//          this.map.cmm.updateTerritoryData();
//        }
//      }
//      return ((ArrayList)colonies).size();
//    }
//    catch (PixelImageException e)
//    {
//      logger.warning("Territory Analyzer - unable to reset map image [" + e + "]");
//      return -1;
//    }
//    finally
//    {
//      this.generating = false;
//      this.cancelAnalysis = false;
//      statusMessage(null);
//      refreshMemoryImage(false);
//      repaint();
//    }
//  }
  
  public void cancelAnalysis()
  {
    this.cancelAnalysis = true;
  }
  
  void clearDisplay(Graphics g, boolean leaveImageArea)
  {
    if (g != null)
    {
      g.setColor(Color.darkGray);
      if (!leaveImageArea)
      {
        g.fillRect(0, 0, getWidth(), getHeight());
      }
      else
      {
        g.fillRect(this.imageWidth, 0, getWidth() - this.imageWidth, this.imageHeight);
        g.fillRect(0, this.imageHeight, getWidth(), getHeight() - this.imageHeight);
      }
    }
  }
  
  public void clearMapImage()
  {
    this.mapImage = null;
    this.mapImageUnscaled = null;
    this.imageWidth = 5000;
    this.imageHeight = 5000;
    
    this.memImage = null;
    this.pixImage = null;
    this.pixImageOriginal = null;
    this.curTerColors = null;
    if ((this.map != null) && (this.map.territories != null)) {
      for (Territory ter : this.map.territories)
      {
        ter.clearEdges();
        ter.clearRecommendedLinks();
      }
    }
    this.selectedTers = new HashSet();
  }
  
  public void colorContinent(Continent oldCont, Continent newCont)
  {
    Set<Territory> ters = new HashSet();
    for (Territory ter : this.map.territories)
    {
      if ((oldCont != null) && (ter.getCont() == oldCont)) {
        ters.add(ter);
      }
      if ((newCont != null) && (ter.getCont() == newCont)) {
        ters.add(ter);
      }
    }
    colorTerritories(ters);
  }
  
  String colorString(Color c)
  {
    if (c == null) {
      return "null";
    }
    return "r=" + c.getRed() + ", g=" + c.getGreen() + ", b=" + c.getBlue();
  }
  
  public void colorTerritories(Collection<Territory> ters)
  {
    if (ters != null) {
      for (Territory ter : ters) {
        colorTerritory(ter);
      }
    }
  }
  
  public void colorTerritory(Territory ter)
  {
    if ((this.optionColorTerritories == TerritoryColorOptions.NONE) || (ter == null)) {
      return;
    }
    if (this.selectedTers.contains(ter))
    {
      colorTerritory(ter, this.colorFillSelected);
    }
    else
    {
      Territory ster = getSingleSelectedTerritory();
      if ((this.optionColorTerritories == TerritoryColorOptions.BORDERING) && (ster != null) && 
        (ster.links.contains(ter)))
      {
        boolean oneWayLink = !ter.links.contains(ster);
        colorTerritory(ter, oneWayLink ? this.colorFillLinkOneWay : this.colorFillLink);
      }
      else
      {
        colorTerritory(ter, null);
      }
    }
  }
  
  private void colorTerritory(Territory ter, Color fillColor)
  {
    if ((ter != null) && (this.pixImage != null))
    {
      if (this.curTerColors == null) {
        this.curTerColors = new HashMap();
      }
      Color lastFilledColor = (Color)this.curTerColors.get(ter);
      
      Color originalColor = getTerritoryOriginalColor(ter);
      if (Color.BLACK.equals(originalColor)) {
        return;
      }
      if (fillColor == null)
      {
        if (lastFilledColor == null) {
          return;
        }
        fillColor = originalColor;
      }
      if ((lastFilledColor == null) || (!lastFilledColor.equals(fillColor)))
      {
        if (logger.isLoggable(Level.FINEST)) {
          logger.finest("Coloring territory " + ter + " [original=" + colorString(originalColor) + 
            "][last=" + colorString(lastFilledColor) + "][fill=" + colorString(fillColor) + 
            ']');
        }
        if (ter.getEdges() != null)
        {
          this.pixImage.replaceFill(ter.getCenterX(), ter.getCenterY(), fillColor, null);
        }
        else
        {
          ArrayList<Point> edges = this.pixImage.replaceFill(ter.getCenterX(), ter.getCenterY(), 
            fillColor, null, null, null);
          ter.setEdges(edges);
        }
        this.curTerColors.put(ter, fillColor);
      }
    }
  }
  
  private Point crawl(Point start, Point destination)
  {
    int x = start.x;
    int y = start.y;
    int dx = destination.x;
    int dy = destination.y;
    int dirx = (int)Math.signum(dx - x);
    int diry = (int)Math.signum(dy - y);
    int floor = this.pixImage.getPixel(x, y);
    
    boolean stepped = false;
    do
    {
      stepped = false;
      if ((x != dx) && (this.pixImage.getPixel(x + dirx, y) == floor))
      {
        x += dirx;
        stepped = true;
      }
      if ((y != dy) && (this.pixImage.getPixel(x, y + diry) == floor))
      {
        y += diry;
        stepped = true;
      }
    } while ((stepped) && ((x != dx) || (y != dy)));
    return new Point(x, y);
  }
  
  void createBlankBitmap(File path, int wide, int high)
    throws IOException
  {
    wide = Calc.bound(wide, 50, 5000);
    high = Calc.bound(wide, 50, 5000);
    
    BufferedImage img = new BufferedImage(wide, high, 1);
    Graphics g = img.getGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, wide, high);
    saveImage(img, path);
  }
  
  public void findAllTerritoryEdges()
  {
    for (Territory ter : this.map.territories)
    {
      if (this.cancelAnalysis) {
        break;
      }
      if (ter.getEdges() == null)
      {
        ArrayList<Point> edges = findTerritoryEdges(ter);
        if ((edges == null) || (edges.isEmpty())) {
          logger.warning("Analyzer - territory edge not determined [territory=" + ter.name + 
            "][edge points=" + (edges == null ? "null" : Integer.valueOf(edges.size())) + "]");
        } else if (logger.isLoggable(Level.FINE)) {
          logger.fine("Analyzer - territory edge points determined [territory=" + ter.name + 
            "][edge points=" + (edges == null ? 0 : edges.size()) + "]");
        }
        ter.setEdges(edges);
      }
    }
    refreshMemoryImage(false);
    repaint();
  }
  
  public Point findAreaCenter(Point origin, Color preFillColor, Color dataFillColor, Color postFillColor)
  {
    if (preFillColor != null) {
      this.pixImage.floodFill(origin.x, origin.y, preFillColor, null, null);
    }
    PixelImage.FillData data = new PixelImage.FillData(false, true);
    this.pixImage.floodFill(origin.x, origin.y, dataFillColor, null, data);
    Point center = data.getWeightedCenter();
    
    int inst1 = this.pixImage.getPixel(center.x, center.y);
    Color checkFillColor = dataFillColor.darker();
    if (checkFillColor.equals(Color.BLACK)) {
      checkFillColor = Color.GRAY;
    }
    this.pixImage.floodFill(origin.x, origin.y, checkFillColor, null, null);
    if (inst1 == this.pixImage.getPixel(center.x, center.y)) {
      center = crawl(origin, center);
    }
    if (postFillColor != null) {
      this.pixImage.floodFill(origin.x, origin.y, postFillColor, null, null);
    }
    return center;
  }
  
//  void findBridges(AnalyzeBordersOptions baOptions)
//  {
//    ArrayList<Point> centers = new ArrayList();
//    ArrayList<ArrayList<Point>> edges = new ArrayList();
//    
//    logger.fine("Scanning for bridges");
//    findColorAreas(baOptions.bridgeColors, baOptions.bridgeMinSize, centers, edges, 
//      "Scanning for bridges");
//    
//    Continent bridgeContinent = new Continent("Bridges", 0);
//    this.bridgeTerritories = new ArrayList();
//    for (int i = 0; i < centers.size(); i++)
//    {
//      Territory ter = new Territory();
//      ter.isBridge = true;
//      ter.setCenter(((Point)centers.get(i)).x, ((Point)centers.get(i)).y);
//      ter.setEdges((ArrayList)edges.get(i));
//      ter.name = ("Bridge#" + i);
//      ter.cont = bridgeContinent;
//      this.bridgeTerritories.add(ter);
//    }
//  }
  
  void findColorAreas(int[] groundColors, int minSize, ArrayList<Point> centers, ArrayList<ArrayList<Point>> edges, String statusString)
  {
    int groundColor = groundColors[0];
    boolean singleColor = groundColors.length == 1;
    
    int w = this.pixImage.getImageWidth();
    int h = this.pixImage.getImageHeight();
    for (int j = 0; j < h; j++)
    {
      if (this.cancelAnalysis) {
        return;
      }
      //if ((statusString != null) && (j % 5 == 0)) {
    //    statusMessage(statusString + " - " + (int)(j / h * 100.0F) + "% - " + centers.size() + 
       //   " found");
      //}
      for (int i = 0; i < w; i++)
      {
        int patch = this.pixImage.getPixel(i, j);
        boolean match;
        if (singleColor)
        {
          match = patch == groundColor;
        }
        else
        {
          match = false;
          int[] arrayOfInt;
          int jj = (arrayOfInt = groundColors).length;
          for (int ii = 0; i < jj; i++)
          {
            int c = arrayOfInt[ii];
            if (patch == c)
            {
              match = true;
              break;
            }
          }
        }
        if (match)
        {
          PixelImage.FillData data = new PixelImage.FillData(edges != null, true);
          this.pixImage.floodFill(i, j, Color.GREEN, null, data);
          if (data.pixelsFilled > minSize)
          {
            Point center = data.getWeightedCenter();
            int inst1 = this.pixImage.getPixel(center.x, center.y);
            this.pixImage.floodFill(i, j, Color.GREEN.darker(), null, null);
            if (inst1 == this.pixImage.getPixel(center.x, center.y))
            {
              Point closer = crawl(new Point(i, j), center);
              centers.add(closer);
            }
            else
            {
              centers.add(new Point(center.x, center.y));
            }
            if (edges != null) {
              edges.add(data.edges);
            }
            if (logger.isLoggable(Level.FINE)) {
              logger.fine("Found area of pixel count: " + data.pixelsFilled + ", location=" + 
                i + "," + j + ", weighted center=" + center);
            }
          }
        }
      }
    }
  }
  
  public Point findTerritoryCenter(Territory ter, boolean restoreColorAfter)
  {
    Color curColor = new Color(this.pixImage.getPixel(ter.getCenterX(), ter.getCenterY()));
    Color dataColor = curColor.equals(Color.GREEN) ? Color.GREEN.darker() : Color.GREEN;
    
    Point center = findAreaCenter(new Point(ter.getCenterX(), ter.getCenterY()), null, dataColor, 
      restoreColorAfter ? curColor : null);
    if (logger.isLoggable(Level.FINER)) {
      logger.fine("Territory center determined from (" + ter.getCenterX() + ", " + ter.getCenterY() + 
        ") and found to be (" + center.x + ", " + center.y + ")");
    }
    return center;
  }
  
  private ArrayList<Point> findTerritoryEdges(Territory ter)
  {
    if ((ter != null) && (this.pixImage != null))
    {
      Color originalColor = getTerritoryOriginalColor(ter);
      if (Color.BLACK.equals(originalColor)) {
        return null;
      }
      return this.pixImage.replaceFill(ter.getCenterX(), ter.getCenterY(), this.colorFillEdgeFinder, null, 
        null, null);
    }
    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Unable to find territory edges for " + ter);
    }
    return null;
  }
  
  private Collection<Territory> getAllAreas()
  {
    Collection<Territory> allAreas = new HashSet();
    if (this.map.territories != null) {
      allAreas.addAll(this.map.territories);
    }
    if (this.bridgeTerritories != null) {
      allAreas.addAll(this.bridgeTerritories);
    }
    return allAreas;
  }
  
  BufferedImage getBufferedImage(Image img, boolean forceCopy)
  {
    if (((img instanceof BufferedImage)) && (!forceCopy))
    {
      logger.fine("Getting buffered image: provided Image is already a BufferedImage");
      return (BufferedImage)img;
    }
    logger.fine("Getting buffered image: creating new BufferedImage for Image");
    BufferedImage bufImage = new BufferedImage(img.getWidth(null), img.getHeight(null), 
      1);
    Graphics2D g = bufImage.createGraphics();
    g.drawImage(img, 0, 0, null);
    return bufImage;
  }
  
  public int getImageHeight()
  {
    return this.imageHeight < 0 ? 5000 : this.imageHeight;
  }
  
  Color getImagePixel(int x, int y)
  {
    if ((this.pixImage == null) || (x >= this.imageWidth) || (y >= this.imageHeight)) {
      return null;
    }
    return new Color(this.pixImage.getPixel(x, y));
  }
  
  public int getImageWidth()
  {
    return this.imageWidth < 0 ? 5000 : this.imageWidth;
  }
  
  public TerritoryColorOptions getOptionColorTerritories()
  {
    return this.optionColorTerritories;
  }
  
  public TerritoryLinkOptions getOptionDrawExistingLinks()
  {
    return this.optionDrawExistingLinks;
  }
  
  public boolean getOptionDrawLinks()
  {
    return this.optionDrawLinks;
  }
  
  public TerritoryLinkOptions getOptionDrawRecommendedLinks()
  {
    return this.optionDrawRecommendedLinks;
  }
  
  Color getOriginalImagePixel(int x, int y)
  {
    if ((this.mapImage == null) || (x >= this.imageWidth) || (y >= this.imageHeight)) {
      return null;
    }
    if (this.pixImageOriginal == null)
    {
      logger.warning("Unable to get original image pixel: mapImage is not null but pixImageOriginal is null");
      return null;
    }
    return new Color(this.pixImageOriginal.getPixel(x, y));
  }
  
  public Dimension getPreferredSize()
  {
    if ((this.map == null) || (this.mapImage == null)) {
      return super.getPreferredSize();
    }
    return new Dimension(this.mapImage.getWidth(this), this.mapImage.getHeight(this));
  }
  
  public HashSet<Territory> getSelectedTerritories()
  {
    if (this.selectedTers == null) {
      this.selectedTers = new HashSet();
    }
    return (HashSet)this.selectedTers.clone();
  }
  
  public int getSelectedTerritoriesSize()
  {
    if (this.selectedTers != null) {
      return this.selectedTers.size();
    }
    return 0;
  }
  
  public int getSelectedTerritoryCount()
  {
    if (this.selectedTers == null) {
      return 0;
    }
    return this.selectedTers.size();
  }
  
  public Territory getSingleSelectedTerritory()
  {
    if (this.selectedTers.size() != 1) {
      return null;
    }
    Iterator<Territory> iter = this.selectedTers.iterator();
    return (Territory)iter.next();
  }
  
  Color getTerritoryOriginalColor(Territory ter)
  {
    Color c = ter.getOriginalColor();
    if (c == null)
    {
      if (logger.isLoggable(Level.FINER)) {
        logger.finer("Extracting original color for territory " + ter);
      }
      c = getOriginalImagePixel(ter.getCenterX(), ter.getCenterY());
      if (c == null)
      {
        logger.warning("Unable to determine original color for territory " + ter);
        c = Color.BLACK;
      }
      ter.setOriginalColor(c);
    }
    return c;
  }
  
  boolean loadImage()
  {
    if (logger.isLoggable(Level.FINE)) {
      logger.fine("Loading map image [" + this.map.getImageFilePath() + "]");
    }
    boolean success = true;
    clearMapImage();
    String imageFilePath = this.map.getImageFilePath();
    if ((imageFilePath != null) && (imageFilePath.length() > 0))
    {
      try
      {
        logger.finer("Loading map image from file");
        FileInputStream in = new FileInputStream(imageFilePath);
        this.mapImage = ImageIO.read(in);
        in.close();
      }
      catch (IOException e)
      {
        this.mapImage = null;
        success = false;
        logger.warning("Unable to load map image \"" + this.map.getImageFilePath() + "\": " + e);
      }
      if (this.mapImage != null)
      {
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(this.mapImage, 0);
        try
        {
          logger.finer("Waiting for map image processing");
          mt.waitForAll();
        }
        catch (Exception localException1) {}
        this.imageWidth = this.mapImage.getWidth(this);
        this.imageHeight = this.mapImage.getHeight(this);
      }
      else
      {
        this.imageWidth = 5000;
        this.imageHeight = 5000;
      }
      try
      {
        setupMemoryImage(true);
      }
      catch (Exception e)
      {
        clearMapImage();
      }
    }
    return success;
  }
  
  void loadProperties(ExtendedProperties props)
  {
    this.colorFillPlain = props.getColorProperty("ColorFillPlain", DefaultColorFillPlain);
    this.colorFillSelected = props.getColorProperty("ColorFillSelected", DefaultColorFillSelected);
    this.colorFillLink = props.getColorProperty("ColorFillLink", DefaultColorFillLink);
    this.colorFillLinkOneWay = props.getColorProperty("ColorFillLinkOneWay", DefaultColorFillLinkOneWay);
    this.colorLinkLineExtra = props.getColorProperty("ColorLinkLineExtra", DefaultColorLinkLineExtra);
    this.colorLinkLineMissing = props.getColorProperty("ColorLinkLineMissing", DefaultColorLinkLineMissing);
    this.colorLinkLineMissingOneWay = props
      .getColorProperty("ColorLinkLineMissingOneWay", DefaultColorLinkLineMissingOneWay);
    this.colorTerritoryCenter = props.getColorProperty("ColorTerritoryCenter", DefaultColorTerritoryCenter);
    this.colorTerritoryEdge = props.getColorProperty("ColorTerritoryEdge", DefaultColorTerritoryEdge);
    this.colorLinkLine = props.getColorProperty("ColorLinkLine", DefaultColorLinkLine);
    this.colorLinkLineOneWay = props.getColorProperty("ColorLinkLineOneWay", DefaultColorLinkLineOneWay);
    this.colorContinentCircle = props.getColorProperty("ColorContinentCircle", DefaultColorContinentCircle);
    this.colorContinentOutlineCircle = props
      .getColorProperty("ColorContinentOutlineCircle", DefaultColorContinentOutlineCircle);
    this.colorSelectionCircle = props.getColorProperty("ColorSelectionCircle", DefaultColorSelectionCircle);
  }
  
  public void paint(Graphics g)
  {
    if (this.map == null)
    {
      super.paint(g);
    }
    else
    {
      if (this.memImage != null)
      {
        clearDisplay(g, true);
        if (this.pixImage != null) {
          this.pixImage.clean();
        }
        g.drawImage(this.memImage, 0, 0, this);
      }
      else if (this.mapImage != null)
      {
        clearDisplay(g, true);
        g.drawImage(this.mapImage, 0, 0, this);
      }
      else
      {
        clearDisplay(g, false);
      }
      if (this.map.territories != null)
      {
        Territory msel = getSingleSelectedTerritory();
        if ((this.optionColorTerritories != TerritoryColorOptions.NONE) && 
          (this.map.cmm.dragOp == ConquestMapMaker.DragOperation.DRAG_OFF) && 
          (msel != null)) {
          paintTerritoryEdge(g, msel, this.colorTerritoryEdge);
        }
        if (!this.generating) {
          paintLinks(g, msel);
        }
        if ((!this.analyzing) && (!this.generating))
        {
          float x = 0.0F;
          float y = 0.0F;
          if ((this.map.cmm.dragOp == ConquestMapMaker.DragOperation.DRAG_RESIZE) && (this.map.cmm.dragVictim != null) && 
            (this.map.cmm.lastDragMouseMoveEvent != null))
          {
            x = this.map.cmm.lastDragMouseMoveEvent.getX() / this.map.cmm.dragVictim.getCenterX();
            y = this.map.cmm.lastDragMouseMoveEvent.getY() / this.map.cmm.dragVictim.getCenterY();
          }
          paintTerritoryIndicators(g, getSelectedTerritoryCount() == 0 ? null : 
            getSelectedTerritories(), x, y);
        }
      }
    }
  }
  
  private void paintLinks(Graphics g, Territory msel)
  {
    if ((this.analyzing) || (
      (this.optionDrawLinks) && ((this.optionDrawExistingLinks == TerritoryLinkOptions.ALL) || (this.optionDrawRecommendedLinks == TerritoryLinkOptions.ALL))))
    {
      ArrayList<Territory> processed = new ArrayList();
      for (Territory ter : this.map.territories) {
        paintLinksForTerritory(g, ter, msel == ter, processed);
      }
    }
    else if ((msel != null) && 
      (this.optionDrawLinks) && (
      (this.optionDrawExistingLinks != TerritoryLinkOptions.NONE) || (this.optionDrawRecommendedLinks != TerritoryLinkOptions.NONE)))
    {
      paintLinksForTerritory(g, msel, true, null);
    }
  }
  
  private void paintLinksForTerritory(Graphics g, Territory ter, boolean selected, ArrayList<Territory> processed)
  {
    boolean drawExisting = (this.analyzing) || (this.optionDrawExistingLinks == TerritoryLinkOptions.ALL) || ((selected) && (this.optionDrawExistingLinks == TerritoryLinkOptions.SELECTED));
    boolean drawRecommended = (this.analyzing) || (this.optionDrawRecommendedLinks == TerritoryLinkOptions.ALL) || ((selected) && (this.optionDrawRecommendedLinks == TerritoryLinkOptions.SELECTED));
    boolean offsetRecommended = this.optionDrawExistingLinks != TerritoryLinkOptions.NONE;
    if (processed != null) {
      processed.add(ter);
    }
    if (drawExisting) {
      paintLinksImpl(g, ter, ter.links, this.colorLinkLine, this.colorLinkLineOneWay, false, false, false, 
        processed);
    }
    if (drawRecommended)
    {
      paintLinksImpl(g, ter, ter.getMissingLinks(), this.colorLinkLineMissing, this.colorLinkLineMissingOneWay, 
        offsetRecommended, true, false, processed);
      paintLinksImpl(g, ter, ter.getExtraLinks(), this.colorLinkLineExtra, 
        this.colorLinkLineExtra.darker().darker(), offsetRecommended, false, true, processed);
    }
  }
  
  private void paintLinksImpl(Graphics g1, Territory ter, ArrayList<Territory> links, Color linkColor, Color linkOneWayColor, boolean useOffset, boolean linksAreRecommendedMissing, boolean linksAreRecommendedExtra, ArrayList<Territory> processed)
  {
    Graphics2D g = (Graphics2D)g1;
    if (links != null)
    {
      int xoffset = 0;int yoffset = 0;
      for (Territory ter2 : links)
      {
        boolean oneWay;
        if (linksAreRecommendedMissing)
        {
          ArrayList<Territory> ml = ter2.getMissingLinks();
          oneWay = (ml == null) || (!ml.contains(ter));
        }
        else
        {
          if (linksAreRecommendedExtra)
          {
            ArrayList<Territory> ml = ter2.getExtraLinks();
            oneWay = (ml == null) || (!ml.contains(ter));
          }
          else
          {
            oneWay = !ter2.links.contains(ter);
          }
        }
        if ((oneWay) || (processed == null) || (!processed.contains(ter2)))
        {
          if (useOffset) {
            if (((ter.getCenterX() < ter2.getCenterX()) && (ter.getCenterY() < ter2.getCenterY())) || (
              (ter.getCenterX() > ter2.getCenterX()) && (ter.getCenterY() > ter2.getCenterY())))
            {
              xoffset = 2;
              yoffset = -2;
            }
            else
            {
              xoffset = 2;
              yoffset = 2;
            }
          }
          g.setColor(linkColor);
          g
            .setStroke((linksAreRecommendedExtra) || (linksAreRecommendedMissing) ? recommendedLinkLineStroke : 
            exisingLinkLineStroke);
          if (!oneWay)
          {
            g.drawLine(ter.getCenterX() + xoffset, ter.getCenterY() + yoffset, ter2.getCenterX() + 
              xoffset, ter2.getCenterY() + yoffset);
          }
          else
          {
            g.drawLine(ter.getCenterX() + xoffset, ter.getCenterY() + yoffset, 
              (ter.getCenterX() + ter2.getCenterX()) / 2 + xoffset, 
              (ter.getCenterY() + ter2.getCenterY()) / 
              2 + yoffset);
            g.setColor(linkOneWayColor);
            g.drawLine((ter.getCenterX() + ter2.getCenterX()) / 2 + xoffset, 
              (ter.getCenterY() + ter2.getCenterY()) / 
              2 + yoffset, ter2.getCenterX() + xoffset, ter2.getCenterY() + yoffset);
          }
        }
      }
    }
  }
  
  private void paintTerritoryEdge(Graphics g, Territory ter, Color edgeColor)
  {
    if ((ter != null) && (ter.getEdges() != null))
    {
      g.setColor(edgeColor);
      for (Point p : ter.getEdges()) {
        g.drawLine(p.x, p.y, p.x, p.y);
      }
    }
  }
  
  private void paintTerritoryEdge(PixelImage pi, Territory ter, Color edgeColor)
  {
    if ((ter != null) && (ter.getEdges() != null))
    {
      int clr = edgeColor.getRGB();
      for (Point p : ter.getEdges()) {
        pi.setPixel(p.x, p.y, clr);
      }
    }
  }
  
  private void paintTerritoryIndicators(Graphics g, Collection<Territory> selected, float projectX, float projectY)
  {
    Continent selCont = null;
    if ((selected != null) && (selected.size() == 1)) {
      for (Territory ter : selected) {
        selCont = ter.getCont();
      }
    }
    int d = 6;
    int cd = 10;
    int cod = 12;
    int sd = selCont == null ? 12 : 18;
    for (Territory ter : this.map.territories)
    {
      int cx = ter.getCenterX();
      int cy = ter.getCenterY();
      if (projectX != 0.0F) {
        cx = (int)(cx * projectX);
      }
      if (projectY != 0.0F) {
        cy = (int)(cy * projectY);
      }
      if (selected != null)
      {
        if (selected.contains(ter))
        {
          g.setColor(this.colorSelectionCircle);
          g.fillOval(cx - (sd + 1) / 2, cy - (sd + 1) / 2, sd, sd);
        }
        if (ter.getCont() == selCont)
        {
          g.setColor(this.colorContinentOutlineCircle);
          g.fillOval(cx - (cod + 1) / 2, cy - (cod + 1) / 2, cod, cod);
          g.setColor(this.colorContinentCircle);
          g.fillOval(cx - (cd + 1) / 2, cy - (cd + 1) / 2, cd, cd);
        }
      }
      g.setColor(this.colorTerritoryCenter);
      g.fillOval(cx - (d + 1) / 2, cy - (d + 1) / 2, d, d);
    }
  }
  
  boolean refreshMemoryImage(boolean refreshOriginalPixImage)
  {
    try
    {
      setupMemoryImage(refreshOriginalPixImage);
      return true;
    }
    catch (PixelImageException e) {}
    return false;
  }
  
  void saveImage(BufferedImage buffed, File target)
    throws IOException
  {
    ImageIO.write(buffed, "BMP", target);
  }
  
  boolean saveImageIfAltered()
  {
    if (!this.imageAltered) {
      return true;
    }
    File f = null;File backup = null;
    boolean justBackedUp = false;
    try
    {
      f = new File(this.map.getImageFilePath());
      
      String backupName = f.getName();
      if (backupName.toLowerCase().endsWith(".bmp")) {
        backupName = backupName.substring(0, backupName.length() - 4);
      }
      backupName = backupName + "_original.bmp";
      backup = new File(f.getParent(), backupName);
      if (!backup.exists())
      {
        if (!f.renameTo(backup)) {
          throw new IOException(
            "Attempt to backup existing map image file failed - aborting image save");
        }
        justBackedUp = true;
      }
      saveImage(this.mapImage, f);
      this.imageAltered = false;
      return true;
    }
    catch (IOException e)
    {
  //    logger.severe("Exception while attempting to save resized map image: " + e);
   //   this.map.cmm.errorMessage("An error occurred while attempting to save resized map images:\n\n" + e);
      if (justBackedUp) {
        if ((backup != null) && (!backup.renameTo(f)))
        {
          String s = "Attempt to restore backup copy of map image (" + backup + 
            ") to original file name (" + f + ") failed";
    //      logger.severe(s);
    //      this.map.cmm.errorMessage(s);
        }
      }
    }
    return false;
  }
  
  void saveProperties(ExtendedProperties props)
  {
    setColorProperty(props, "ColorFillPlain", this.colorFillPlain, DefaultColorFillPlain);
    setColorProperty(props, "ColorFillSelected", this.colorFillSelected, DefaultColorFillSelected);
    setColorProperty(props, "ColorFillLink", this.colorFillLink, DefaultColorFillLink);
    setColorProperty(props, "ColorFillLinkOneWay", this.colorFillLinkOneWay, DefaultColorFillLinkOneWay);
    setColorProperty(props, "ColorLinkLineExtra", this.colorLinkLineExtra, DefaultColorLinkLineExtra);
    setColorProperty(props, "ColorLinkLineMissing", this.colorLinkLineMissing, DefaultColorLinkLineMissing);
    setColorProperty(props, "ColorLinkLineMissingOneWay", this.colorLinkLineMissingOneWay, 
      DefaultColorLinkLineMissingOneWay);
    setColorProperty(props, "ColorTerritoryCenter", this.colorTerritoryCenter, DefaultColorTerritoryCenter);
    setColorProperty(props, "ColorTerritoryEdge", this.colorTerritoryEdge, DefaultColorTerritoryEdge);
    setColorProperty(props, "ColorLinkLine", this.colorLinkLine, DefaultColorLinkLine);
    setColorProperty(props, "ColorLinkLineOneWay", this.colorLinkLineOneWay, DefaultColorLinkLineOneWay);
    setColorProperty(props, "ColorContinentCircle", this.colorContinentCircle, DefaultColorContinentCircle);
    setColorProperty(props, "ColorContinentOutlineCircle", this.colorContinentOutlineCircle, 
      DefaultColorContinentOutlineCircle);
    setColorProperty(props, "ColorSelectionCircle", this.colorSelectionCircle, DefaultColorSelectionCircle);
  }
  
  void scaleMap(int newWidth, int newHeight)
  {
    if ((this.mapImage == null) || (this.map == null)) {
      return;
    }
    this.mapImage = scaleMapImage(newWidth, newHeight);
    this.map.scaleAllTerritories(newWidth / this.imageWidth, newHeight / this.imageHeight);
    this.imageWidth = newWidth;
    this.imageHeight = newHeight;
    refreshMemoryImage(true);
  }
  
  BufferedImage scaleMapImage(int newWidth, int newHeight)
  {
    if (this.mapImageUnscaled == null) {
      this.mapImageUnscaled = getBufferedImage(this.mapImage, true);
    }
    Image scaled = this.mapImageUnscaled.getScaledInstance(newWidth, newHeight, 2);
    this.imageAltered = true;
    
    return getBufferedImage(scaled, false);
  }
  
  public void selectTerritories(Collection<Territory> newTers)
  {
    HashSet<Territory> cters = new HashSet();
    
    cters.addAll(this.selectedTers);
    if (this.selectedTers.size() == 1) {
      for (Territory ter : this.selectedTers) {
        if (ter.links != null) {
          cters.addAll(ter.links);
        }
      }
    }
    this.selectedTers.clear();
    if (newTers != null)
    {
      this.selectedTers.addAll(newTers);
      cters.addAll(newTers);
    }
    if (this.selectedTers.size() == 1) {
      for (Territory ter : this.selectedTers) {
        if (ter.links != null) {
          cters.addAll(ter.links);
        }
      }
    }
    colorTerritories(cters);
    if (logger.isLoggable(Level.FINEST)) {
      logger.finest("Selected territories changed to: " + this.selectedTers);
    }
  }
  
  void selectTerritory(Territory newTer, ConquestMapMaker.SelectionOperation op)
  {
    if (newTer == null)
    {
      selectTerritories(null);
    }
    else
    {
      Collection<Territory> c = new HashSet();
      if (op == ConquestMapMaker.SelectionOperation.SET)
      {
        c.add(newTer);
      }
      else
      {
        c.addAll(this.selectedTers);
        if ((op == ConquestMapMaker.SelectionOperation.REMOVE) || (
          (op == ConquestMapMaker.SelectionOperation.TOGGLE) && (this.selectedTers.contains(newTer)))) {
          c.remove(newTer);
        } else {
          c.add(newTer);
        }
      }
      selectTerritories(c);
    }
  }
  
  void selectTerritoryNone()
  {
    selectTerritory(null, ConquestMapMaker.SelectionOperation.SET);
  }
  
  private void setColorProperty(ExtendedProperties props, String prop, Color val, Color defaultVal)
  {
    if ((val == null) || ((defaultVal != null) && (val.equals(defaultVal)))) {
      props.setProperty(prop, "default");
    } else {
      props.setProperty(prop, val);
    }
  }
  
  public void setMap(ConquestMap map)
  {
    clearDisplay(getGraphics(), false);
    this.map = map;
    clearMapImage();
  }
  
  public void setOptionColorTerritories(TerritoryColorOptions optionColorTerritories)
  {
    if (this.optionColorTerritories != optionColorTerritories)
    {
      Collection<Territory> remember = (Collection)this.selectedTers.clone();
      selectTerritoryNone();
      this.optionColorTerritories = optionColorTerritories;
      if (this.map != null) {
        selectTerritories(remember);
      }
    }
  }
  
  public void setOptionDrawLinks(boolean drawLinks, TerritoryLinkOptions existingOption, TerritoryLinkOptions recommendedOption)
  {
    boolean changed = false;
    if (this.optionDrawExistingLinks != existingOption)
    {
      this.optionDrawExistingLinks = existingOption;
      changed = true;
    }
    if (this.optionDrawRecommendedLinks != recommendedOption)
    {
      this.optionDrawRecommendedLinks = recommendedOption;
      changed = true;
    }
    if (this.optionDrawLinks != drawLinks)
    {
      this.optionDrawLinks = drawLinks;
      changed = true;
    }
    if (changed) {
      repaint();
    }
  }
  
  private void setupMemoryImage(boolean refreshOriginalPixImage)
    throws PixelImageException
  {
    logger.finer("Setting up memory image");
    this.pixImage = null;
    
    this.curTerColors = null;
    for (Territory ter : this.map.territories) {
      ter.setOriginalColor(null);
    }
    if (this.mapImage != null)
    {
      this.pixImage = new PixelImage(this.imageWidth, this.imageHeight, this.mapImage);
      if ((this.pixImageOriginal == null) || (refreshOriginalPixImage)) {
        this.pixImageOriginal = new PixelImage(this.imageWidth, this.imageHeight, this.mapImage);
      }
      MemoryImageSource mis = this.pixImage.getMemoryImageSource();
      mis.setAnimated(true);
      
      this.memImage = createImage(mis);
    }
  }
  
//  void statusMessage(String message)
//  {
//    if (message != null) {
//      this.map.cmm.updateInfoField("   " + message, Color.BLUE, false);
//    } else {
//      this.map.cmm.updateInfoField();
//    }
//  }
  
  boolean supressMouseControls()
  {
    return (this.analyzing) || (this.generating);
  }
  
  public void uncolorTerritory(Territory ter)
  {
    if ((this.optionColorTerritories == TerritoryColorOptions.NONE) || (ter == null)) {
      return;
    }
    colorTerritory(ter, null);
  }
  
  public void update(Graphics g)
  {
    paint(g);
  }
}
