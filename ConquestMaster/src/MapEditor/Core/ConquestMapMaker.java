package MapEditor.Core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop.Action;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import MapEditor.Domain.Continent;
import MapEditor.Domain.ContinentTableModel;
import MapEditor.Domain.Territory;
import MapEditor.Domain.TerritoryTableModel;
import MapEditor.Swing.DocumentViewerObject;
import MapEditor.Swing.ListDocumentViewer;
import MapEditor.Util.Calc;
import MapEditor.Util.ComponentUtil;
import MapEditor.Util.ExtendedProperties;
import MapEditor.Util.FileUtil;
import MapEditor.Util.MySemaphore;
import MapEditor.Util.ResourceUtil;
import MapEditor.Util.StringUtil;

public class ConquestMapMaker extends JFrame implements WindowListener, MouseListener, MouseMotionListener,
		ActionListener, ListSelectionListener, DropTargetListener, TableModelListener, KeyListener {
	private static final long serialVersionUID = -4093248948827540485L;
	//
	// public class AnalyzerWorker
	// extends SwingWorker<Object, Object>
	// {
	// boolean borderAnalysis = false;
	// boolean promptForOptions = true;
	//
	// public AnalyzerWorker(boolean borderAnalysis, boolean promptForOptions)
	// {
	// this.borderAnalysis = borderAnalysis;
	// this.promptForOptions = promptForOptions;
	// }
	//
	// /* Error */
	// protected Object doInBackground()
	// throws Exception
	// {
	// // Byte code:
	// // 0: aload_0
	// // 1: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 4: aload_0
	// // 5: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 8: getfield 31 bcb/conquest/ConquestMapMaker:actionSetPrimary
	// Ljava/util/Set;
	// // 11: iconst_0
	// // 12: invokestatic 37 bcb/conquest/ConquestMapMaker:access$3
	// (Lbcb/conquest/ConquestMapMaker;Ljava/util/Collection;Z)V
	// // 15: aload_0
	// // 16: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 19: aload_0
	// // 20: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 23: getfield 41 bcb/conquest/ConquestMapMaker:actionSetAnalyzeEnabled
	// Ljava/util/Set;
	// // 26: iconst_1
	// // 27: invokestatic 37 bcb/conquest/ConquestMapMaker:access$3
	// (Lbcb/conquest/ConquestMapMaker;Ljava/util/Collection;Z)V
	// // 30: aload_0
	// // 31: getfield 18
	// bcb/conquest/ConquestMapMaker$AnalyzerWorker:borderAnalysis Z
	// // 34: ifeq +17 -> 51
	// // 37: aload_0
	// // 38: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 41: aload_0
	// // 42: getfield 20
	// bcb/conquest/ConquestMapMaker$AnalyzerWorker:promptForOptions Z
	// // 45: invokestatic 44 bcb/conquest/ConquestMapMaker:access$4
	// (Lbcb/conquest/ConquestMapMaker;Z)V
	// // 48: goto +96 -> 144
	// // 51: aload_0
	// // 52: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 55: invokestatic 48 bcb/conquest/ConquestMapMaker:access$5
	// (Lbcb/conquest/ConquestMapMaker;)V
	// // 58: goto +86 -> 144
	// // 61: astore_1
	// // 62: aload_0
	// // 63: getfield 18
	// bcb/conquest/ConquestMapMaker$AnalyzerWorker:borderAnalysis Z
	// // 66: ifeq +10 -> 76
	// // 69: aload_0
	// // 70: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 73: invokevirtual 52
	// bcb/conquest/ConquestMapMaker:clearAnalyzerRecommendations ()V
	// // 76: aload_0
	// // 77: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 80: getfield 55 bcb/conquest/ConquestMapMaker:mapDisplay
	// Lbcb/conquest/MapDisplay;
	// // 83: invokevirtual 59 bcb/conquest/MapDisplay:repaint ()V
	// // 86: aload_0
	// // 87: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 90: aload_0
	// // 91: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 94: getfield 31 bcb/conquest/ConquestMapMaker:actionSetPrimary
	// Ljava/util/Set;
	// // 97: iconst_1
	// // 98: invokestatic 37 bcb/conquest/ConquestMapMaker:access$3
	// (Lbcb/conquest/ConquestMapMaker;Ljava/util/Collection;Z)V
	// // 101: aload_0
	// // 102: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 105: getfield 64 bcb/conquest/ConquestMapMaker:actionAnalyzeCancel
	// Ljavax/swing/AbstractAction;
	// // 108: iconst_0
	// // 109: invokevirtual 68 javax/swing/AbstractAction:setEnabled (Z)V
	// // 112: goto +58 -> 170
	// // 115: astore_2
	// // 116: aload_0
	// // 117: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 120: aload_0
	// // 121: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 124: getfield 31 bcb/conquest/ConquestMapMaker:actionSetPrimary
	// Ljava/util/Set;
	// // 127: iconst_1
	// // 128: invokestatic 37 bcb/conquest/ConquestMapMaker:access$3
	// (Lbcb/conquest/ConquestMapMaker;Ljava/util/Collection;Z)V
	// // 131: aload_0
	// // 132: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 135: getfield 64 bcb/conquest/ConquestMapMaker:actionAnalyzeCancel
	// Ljavax/swing/AbstractAction;
	// // 138: iconst_0
	// // 139: invokevirtual 68 javax/swing/AbstractAction:setEnabled (Z)V
	// // 142: aload_2
	// // 143: athrow
	// // 144: aload_0
	// // 145: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 148: aload_0
	// // 149: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 152: getfield 31 bcb/conquest/ConquestMapMaker:actionSetPrimary
	// Ljava/util/Set;
	// // 155: iconst_1
	// // 156: invokestatic 37 bcb/conquest/ConquestMapMaker:access$3
	// (Lbcb/conquest/ConquestMapMaker;Ljava/util/Collection;Z)V
	// // 159: aload_0
	// // 160: getfield 13 bcb/conquest/ConquestMapMaker$AnalyzerWorker:this$0
	// Lbcb/conquest/ConquestMapMaker;
	// // 163: getfield 64 bcb/conquest/ConquestMapMaker:actionAnalyzeCancel
	// Ljavax/swing/AbstractAction;
	// // 166: iconst_0
	// // 167: invokevirtual 68 javax/swing/AbstractAction:setEnabled (Z)V
	// // 170: aconst_null
	// // 171: areturn
	// // Line number table:
	// // Java source line #128 -> byte code offset #0
	// // Java source line #129 -> byte code offset #15
	// // Java source line #131 -> byte code offset #30
	// // Java source line #132 -> byte code offset #37
	// // Java source line #134 -> byte code offset #51
	// // Java source line #136 -> byte code offset #61
	// // Java source line #138 -> byte code offset #62
	// // Java source line #139 -> byte code offset #69
	// // Java source line #140 -> byte code offset #76
	// // Java source line #143 -> byte code offset #86
	// // Java source line #144 -> byte code offset #101
	// // Java source line #142 -> byte code offset #115
	// // Java source line #143 -> byte code offset #116
	// // Java source line #144 -> byte code offset #131
	// // Java source line #145 -> byte code offset #142
	// // Java source line #143 -> byte code offset #144
	// // Java source line #144 -> byte code offset #159
	// // Java source line #146 -> byte code offset #170
	// // Local variable table:
	// // start length slot name signature
	// // 0 172 0 this AnalyzerWorker
	// // 61 2 1 e OperationCanceledException
	// // 115 28 2 localObject Object
	// // Exception table:
	// // from to target type
	// // 0 58 61 bcb/conquest/OperationCanceledException
	// // 0 86 115 finally
	// }
	// }

	static enum DragOperation {
		DRAG_OFF, DRAG_MOVE, DRAG_MOVEALL, DRAG_RESIZE;
	}

	static enum SelectionOperation {
		SET, ADD, REMOVE, TOGGLE;
	}

	// static Logger logger =
	// Logger.getLogger(ConquestMapMaker.class.getPackage().getName());
	static final String ProgramTitle = "Conquest Map Maker";
	static final String ProgramVersion = "2.0.0";
	static final String PropAnalyzeOnLoad = "AnalyzeOnLoad";
	static final String PropLogFileLevel = "LogFileLevel";
	static final String PropLogConsoleLevel = "LogConsoleLevel";
	static final String PropLastPath = "LastPath";
	static final String PropLastMap = "LastMap";
	static final String PropTerritoryLinksVisible = "TerritoryLinksVisible";
	static final String PropTerritoryLinksExisting = "TerritoryLinksExisting";
	static final String PropTerritoryLinksRecommended = "TerritoryLinksRecommended";
	static final String PropTerritoryColoring = "TerritoryColoring";
	static final String PropImageEditorPath = "ImageEditorPath";
	static final String PropLookAndFeel = "LookAndFeel";
	static final String PropVersion = "Version";
	static final String PropWindowLocationX = "WindowLocationX";
	static final String PropWindowLocationY = "WindowLocationY";
	static final String PropWindowSizeWidth = "WindowSizeWidth";
	static final String PropWindowSizeHeight = "WindowSizeHeight";
	static final String PropWindowState = "WindowState";
	static final String PropWindowDivide = "WindowDivide";
	static final String PropTableDivide = "TableDivide";
	static final String PropWrapMoveTerritories = "WrapMoveTerritories";
	ExtendedProperties props;
	ConquestMap map;
	String propsFilePath = "ConquestMapMaker.properties";
	Process imageEditProc = null;
	boolean initCompleted = false;
	boolean addingTerritory = false;
	Color infoFg = null;
	// AnalyzerWorker analyzer;
	Collection<Territory> translatingTerritories = null;
	// MapPropertiesDialog mapPropsDialog = null;
	// AnalyzeBordersOptionsDialog abOptionsDialog = null;
	Handler consoleLogHandler = null;
	Handler fileLogHandler = null;
	boolean justIdentifiedColor;
	Component centerComp = this;
	Set<Action> actionSetAll;
	Set<Action> actionSetPrimary;
	Set<Action> actionSetAnalyzeEnabled;
	MySemaphore disableValueChanged = new MySemaphore("disable-valueChanged-sem");
	DragOperation dragOp = DragOperation.DRAG_OFF;
	Territory dragVictim = null;
	MouseEvent lastDragMouseMoveEvent = null;
	JFileChooser mapFc = null;
	JMenuBar menuBar;
	JRadioButtonMenuItem menuOptionsColorTerritoriesNone;
	JRadioButtonMenuItem menuOptionsColorTerritoriesSelected;
	JRadioButtonMenuItem menuOptionsColorTerritoriesBorders;
	JRadioButtonMenuItem menuOptionsDrawLinksNone;
	JRadioButtonMenuItem menuOptionsDrawLinksSelected;
	JRadioButtonMenuItem menuOptionsDrawLinksAll;
	JRadioButtonMenuItem menuOptionsDrawRecommendedLinksNone;
	JRadioButtonMenuItem menuOptionsDrawRecommendedLinksSelected;
	JRadioButtonMenuItem menuOptionsDrawRecommendedLinksAll;
	JCheckBoxMenuItem menuOptionsDrawLinks;
	JCheckBoxMenuItem menuOptionsAnalyzeOnLoad;
	JCheckBoxMenuItem menuOptionsWrapMoveTerritories;
	JSplitPane splitPane;
	JSplitPane splitPane2;
	ButtonModel showLinksButtonModel;
	ImageIcon cmmIcon;
	ImageIcon contIcon;
	ImageIcon analyzerIcon;
	ImageIcon buttonLinksOn;
	ImageIcon buttonLinksOff;
	MapDisplay mapDisplay;
	JScrollPane msp;
	JToolBar toolBar;
	JPanel mapPanel;
	JPanel editPanel;
	TitledBorder mapTitleBorder;
	ListDocumentViewer helpViewer;
	JLabel locField;
	JLabel infoField;
	JTable terTable;
	TerritoryTableModel terTableModel;
	JTable contTable;
	ContinentTableModel contTableModel;
	JComboBox continentComboBox;
	DefaultCellEditor contComboEditor;
	// AnalyzeBordersOptions abOptions;
	// AnalyzeMapTerritoriesOptionsDialog amtOptionsDialog;
	DropTarget dropTarget;
	AbstractAction actionAbortOperation=new AbstractAction("Abort operation"){private static final long serialVersionUID=1267199667996571608L;

	public void actionPerformed(ActionEvent e){ConquestMapMaker.this.abortOperation(true);}};
	AbstractAction actionAddContinent=new AbstractAction("Add continent...",makeImageIcon("icons/Continent.gif")){private static final long serialVersionUID=-7540370619276657724L;

	public void actionPerformed(ActionEvent e){ConquestMapMaker.this.addContinent();}};
	AbstractAction actionAddTerritory=new AbstractAction("Add territory",makeImageIcon("icons/Territory.gif")){private static final long serialVersionUID=3432430673776972605L;

	public void actionPerformed(ActionEvent e){ConquestMapMaker.this.addTerritoryMode();}};
	AbstractAction actionAnalyzeApply=new AbstractAction("Apply all border recommendations",makeImageIcon("icons/AnalyzeApply.gif")){private static final long serialVersionUID=-6657632422567441564L;

	public void actionPerformed(ActionEvent e){ConquestMapMaker.this.analyzeApply(true,true);}};
	AbstractAction actionAnalyzeApplyMissing=new AbstractAction("Apply added border recommendations",makeImageIcon("icons/AnalyzeApplyNew.gif")){private static final long serialVersionUID=-3800626524039085698L;

	public void actionPerformed(ActionEvent e){ConquestMapMaker.this.analyzeApply(true,false);}};
	AbstractAction actionAnalyzeBorders=new AbstractAction("Analyze territory borders...",makeImageIcon("icons/Analyze.gif")){private static final long serialVersionUID=-2069253353252897873L;

	public void actionPerformed(ActionEvent e){ConquestMapMaker.this.analyzeBorders(true);}};
	AbstractAction actionAnalyzeCancel=new AbstractAction("Cancel current process",makeImageIcon("icons/AnalyzeCancel.gif")){private static final long serialVersionUID=-3069255353252397853L;

	public void actionPerformed(ActionEvent e){
	// if ((ConquestMapMaker.this.analyzer != null) &&
	// (!ConquestMapMaker.this.analyzer.isDone())) {
	ConquestMapMaker.this.mapDisplay.cancelAnalysis();
	// }
	}};
	AbstractAction actionAnalyzeClearRecommendations=new AbstractAction("Clear border recommendations",makeImageIcon("icons/AnalyzeClear.gif")){private static final long serialVersionUID=1092315873941501143L;

	public void actionPerformed(ActionEvent e){ConquestMapMaker.this.clearAnalyzerRecommendations();}};
	AbstractAction actionAnalyzeTerritories = new AbstractAction("Generate territories...", 
    makeImageIcon("icons/AnalyzeGenerate.gif"))
  {
    private static final long serialVersionUID = 1092315873941501143L;
    
//    public void actionPerformed(ActionEvent e)
//    {
//      if ((ConquestMapMaker.this.analyzer == null) || (ConquestMapMaker.this.analyzer.isDone()))
//      {
//        ConquestMapMaker.this.analyzer = new ConquestMapMaker.AnalyzerWorker(ConquestMapMaker.this, false, true);
//        ConquestMapMaker.this.analyzer.execute();
//      }
//    }
//  };
  AbstractAction actionCenterSelectedTerritories = new AbstractAction("Center selected territories", 
    makeImageIcon("icons/Center.gif"))
  {
    private static final long serialVersionUID = 1214754808755489280L;
    
    public void actionPerformed(ActionEvent e)
    {
      Collection<Territory> ters = ConquestMapMaker.this.mapDisplay.getSelectedTerritories();
      for (Territory ter : ters)
      {
        Point center = ConquestMapMaker.this.mapDisplay.findTerritoryCenter(ter, true);
        ConquestMapMaker.this.moveTerritory(ter, center.x, center.y);
      }
    }
  };
  AbstractAction actionDeleteContinent = new AbstractAction("Delete selected continent", 
    makeImageIcon("icons/DeleteContinent.gif"))
  {
    private static final long serialVersionUID = 1220955548635015396L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.deleteSelectedContinent();
    }
  };
  AbstractAction actionDeleteTerritory = new AbstractAction("Delete selected territories", 
    makeImageIcon("icons/DeleteTerritory.gif"))
  {
    private static final long serialVersionUID = 1414626415962284569L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.deleteSelectedTerritories();
    }
  };
  AbstractAction actionEditMapImage = new AbstractAction("Edit map image", makeImageIcon("icons/Edit.gif"))
  {
    private static final long serialVersionUID = -2513724909006789497L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.editMapImage();
    }
  };
  AbstractAction actionHelp = new AbstractAction("Help", makeImageIcon("icons/Help.gif"))
  {
    private static final long serialVersionUID = 1799673268883879698L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.showHelp(null);
    }
  };
  AbstractAction actionHelpAbout = new AbstractAction("About", makeImageIcon("icons/About.gif"))
  {
    private static final long serialVersionUID = -2899104592323895701L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.showHelpAbout();
    }
  };
  AbstractAction actionMapPropertiesDialog = new AbstractAction("Map properties...", 
    makeImageIcon("icons/MapProperties.gif"))
  {
    private static final long serialVersionUID = 1220955548635015396L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.openMapPropertiesDialog();
    }
  };
  AbstractAction actionMouseControls = new AbstractAction("Mouse controls lock")
  {
    private static final long serialVersionUID = 2039584638495948348L;
    
    public void actionPerformed(ActionEvent e) {}
  };
  AbstractAction actionMoveDown = new AbstractAction("Move selected territory down")
  {
    private static final long serialVersionUID = 1706564850598935923L;
    
    public void actionPerformed(ActionEvent e)
    {
      boolean moveAll = (e.getModifiers() & 0x2) == 2;
      
      ConquestMapMaker.this.translateTerritory(0, 1, moveAll);
    }
  };
  AbstractAction actionMoveLeft = new AbstractAction("Move selected territory left")
  {
    private static final long serialVersionUID = -5253914878224977882L;
    
    public void actionPerformed(ActionEvent e)
    {
      boolean moveAll = (e.getModifiers() & 0x2) == 2;
      
      ConquestMapMaker.this.translateTerritory(-1, 0, moveAll);
    }
  };
  AbstractAction actionMoveRight = new AbstractAction("Move selected territory right")
  {
    private static final long serialVersionUID = -1152696171238282415L;
    
    public void actionPerformed(ActionEvent e)
    {
      boolean moveAll = (e.getModifiers() & 0x2) == 2;
      
      ConquestMapMaker.this.translateTerritory(1, 0, moveAll);
    }
  };
  AbstractAction actionMoveUp = new AbstractAction("Move selected territory up")
  {
    private static final long serialVersionUID = -8606005834586426912L;
    
    public void actionPerformed(ActionEvent e)
    {
      boolean moveAll = (e.getModifiers() & 0x2) == 2;
      ConquestMapMaker.this.translateTerritory(0, -1, moveAll);
    }
  };
  AbstractAction actionNew = new AbstractAction("New...", makeImageIcon("icons/New.gif"))
  {
    private static final long serialVersionUID = 1487354370601821572L;
    
    public void actionPerformed(ActionEvent e)
    {
      SwingWorker<Void, Void> w = new SwingWorker()
      {
        protected Void doInBackground()
          throws Exception
        {
          ConquestMapMaker.this.mapNew();
          return null;
        }
      };
      w.execute();
    }
  };
  AbstractAction actionOpen = new AbstractAction("Open...", makeImageIcon("icons/Open.gif"))
  {
    private static final long serialVersionUID = 2278244415607102235L;
    
    public void actionPerformed(ActionEvent e)
    {
      SwingWorker<Void, Void> w = new SwingWorker()
      {
        protected Void doInBackground()
          throws Exception
        {
          ConquestMapMaker.this.mapPromptAndLoad(null);
          return null;
        }
      };
      w.execute();
    }
  };
  AbstractAction actionPlafJava = new AbstractAction("Java")
  {
    private static final long serialVersionUID = 4530795481732034849L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.setPlaf(UIManager.getCrossPlatformLookAndFeelClassName());
    }
  };
  AbstractAction actionPlafMotif = new AbstractAction("CDE/Motif")
  {
    private static final long serialVersionUID = -2774973161384962497L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.setPlaf("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
    }
  };
  AbstractAction actionPlafNative = new AbstractAction(System.getProperty("os.name"))
  {
    private static final long serialVersionUID = -3086771453335711713L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.setPlaf(UIManager.getSystemLookAndFeelClassName());
    }
  };
  AbstractAction actionReloadMapImage = new AbstractAction("Reload map image", 
    makeImageIcon("icons/Refresh.gif"))
  {
    private static final long serialVersionUID = 1027881585993218614L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.reloadMapImage();
    }
  };
  AbstractAction actionRenameTerritoriesAll = new AbstractAction("Auto-name all territories", 
    makeImageIcon("icons/AutoName.gif"))
  {
    private static final long serialVersionUID = -7689770480431585628L;
    
    public void actionPerformed(ActionEvent e)
    {
      if (ConquestMapMaker.this.confirm("This will rename all territories based on their continent names and sequential number.\nAll current territory names will be lost!\n\nDo you wish to proceed and overwrite current territory names?", null))
      {
        ConquestMapMaker.this.map.renameTerritories();
        ConquestMapMaker.this.updateTerritoryData();
      }
    }
  };
  AbstractAction actionResizeMap = new AbstractAction("Resize map...", makeImageIcon("icons/Resize.gif"))
  {
    private static final long serialVersionUID = 1220955548635015396L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.scaleMap();
    }
  };
  AbstractAction actionRevert = new AbstractAction("Revert to saved", makeImageIcon("icons/Open.gif"))
  {
    private static final long serialVersionUID = -2827384629865478608L;
    
    public void actionPerformed(ActionEvent e)
    {
      if (ConquestMapMaker.this.map != null)
      {
        SwingWorker<Void, Void> w = new SwingWorker()
        {
          protected Void doInBackground()
            throws Exception
          {
            ConquestMapMaker.this.mapPromptAndLoad(ConquestMapMaker.this.map.getMapFilePath());
            return null;
          }
        };
        w.execute();
      }
    }
  };
  AbstractAction actionSave = new AbstractAction("Save", makeImageIcon("icons/Save.gif"))
  {
    private static final long serialVersionUID = -3800626524039085698L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.mapSave();
    }
  };
  AbstractAction actionSaveAs = new AbstractAction("Save as...", makeImageIcon("icons/SaveAs.gif"))
  {
    private static final long serialVersionUID = -5481781621872527092L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.mapSaveAs();
    }
  };
  AbstractAction actionSelectAllTerritories = new AbstractAction("Select all territories")
  {
    private static final long serialVersionUID = -6657632422567441564L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.selectTerritories(ConquestMapMaker.this.map.territories);
    }
  };
  AbstractAction actionSelectImageEditor = new AbstractAction("Select image editor...")
  {
    private static final long serialVersionUID = -1152696171238282415L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.selectImageEditor();
    }
  };
  AbstractAction actionSetLogLevel = new AbstractAction("Logging options...")
  {
    private static final long serialVersionUID = 2048531740338129803L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.logOptionsPrompt();
    }
  };
  AbstractAction actionShowContinentInfo = new AbstractAction("Details for selected continent", 
    makeImageIcon("icons/ContinentDetails.gif"))
  {
    private static final long serialVersionUID = 1092315873941501143L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.showContinentInfo(ConquestMapMaker.this.getSelectedContinent());
    }
  };
  AbstractAction actionToggleShowTerritoryLinks = new AbstractAction("Draw territory connections", 
    makeImageIcon("icons/ToggleLinkLines.gif"))
  {
    private static final long serialVersionUID = 1487354370601821572L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.menuOptionsDrawLinks.setSelected(!ConquestMapMaker.this.menuOptionsDrawLinks.isSelected());
      ConquestMapMaker.this.optionsSetDrawLinks();
    }
  };
  AbstractAction actionVerifyMap = new AbstractAction("Check map for problems", 
    makeImageIcon("icons/MapVerify.gif"))
  {
    private static final long serialVersionUID = 7230518090916658904L;
    
    public void actionPerformed(ActionEvent e)
    {
      ConquestMapMaker.this.verifyMap(true);
    }
  };
  
  public static void main(String[] args)
  {
    ConquestMapMaker cmm = new ConquestMapMaker();
    cmm.init(args.length == 0 ? null : args[0]);
  }
  
  void abortDragOperation()
  {
    if (this.dragOp != DragOperation.DRAG_OFF)
    {
      setDragOp(DragOperation.DRAG_OFF, null);
      this.mapDisplay.selectTerritory(this.mapDisplay.getSingleSelectedTerritory(), SelectionOperation.SET);
      this.mapDisplay.repaint();
      this.mapDisplay.setCursor(new Cursor(0));
    }
  }
  
  void abortOperation(boolean cancelEdits)
  {
    if (cancelEdits)
    {
      this.disableValueChanged.get();
      abortTableCellEdit(this.terTable);
      abortTableCellEdit(this.contTable);
      this.disableValueChanged.release();
    }
    abortDragOperation();
    setAddTerritoryMode(false);
    updateInfoField();
    updateLocField();
    this.mapDisplay.requestFocus();
  }
  
  public boolean abortTableCellEdit(JTable table)
  {
    try
    {
      int col = table.getEditingColumn();
      if (col > -1)
      {
        TableCellEditor editor = table.getColumnModel().getColumn(col).getCellEditor();
        if (editor == null) {
          editor = table.getDefaultEditor(table.getColumnClass(col));
        }
        if (editor != null) {
          editor.stopCellEditing();
        }
      }
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if ((e.getSource() == this.menuOptionsColorTerritoriesNone) || 
      (e.getSource() == this.menuOptionsColorTerritoriesSelected) || 
      (e.getSource() == this.menuOptionsColorTerritoriesBorders)) {
      optionsSetTerritoryColor();
    } else if ((e.getSource() == this.menuOptionsDrawLinks) || (e.getSource() == this.menuOptionsDrawLinksNone) || 
      (e.getSource() == this.menuOptionsDrawLinksSelected) || 
      (e.getSource() == this.menuOptionsDrawLinksAll) || 
      (e.getSource() == this.menuOptionsDrawRecommendedLinksNone) || 
      (e.getSource() == this.menuOptionsDrawRecommendedLinksSelected) || 
      (e.getSource() == this.menuOptionsDrawRecommendedLinksAll)) {
      optionsSetDrawLinks();
    } else if (e.getSource() == this.menuOptionsWrapMoveTerritories) {
      this.props.setProperty("WrapMoveTerritories", this.menuOptionsWrapMoveTerritories.getState());
    }
  }
  
  private void addActions(Collection<Action> c, Action... actions)
  {
    Action[] arrayOfAction;
    int j = (arrayOfAction = actions).length;
    for (int i = 0; i < j; i++)
    {
      Action a = arrayOfAction[i];
      c.add(a);
    }
  }
  
  public Continent addContinent()
  {
    abortOperation(true);
    Continent cont = newContinentPrompt();
    if (cont != null)
    {
      this.map.addContinent(cont);
      updateContinentData();
    }
    return cont;
  }
  
  public void addTerritory(int x, int y)
  {
    if (this.map.continents.isEmpty()) {
      if (addContinent() == null) {
        return;
      }
    }
    if (!this.map.continents.isEmpty())
    {
      Continent cont = getSelectedContinent();
      if ((cont == null) && (this.map.lastContinentUsed != null)) {
        cont = this.map.findContinent(this.map.lastContinentUsed);
      }
      if (cont == null) {
        cont = (Continent)this.map.continents.get(0);
      }
      String tname = null;
      int num = 0;
      do
      {
        num++;
        tname = cont.name + " " + num;
      } while (this.map.findTerritory(tname) != null);
      Territory ter = new Territory();
      ter.setCenter(x, y);
      ter.name = tname;
      ter.cont = cont;
      this.map.lastContinentUsed = ter.cont.name;
      
      this.map.addTerritory(ter);
      updateTerritoryData();
      scrollTableToTerritory(ter.name);
      this.mapDisplay.repaint();
      if (this.map.territories.size() > 255) {
        infoMessage("This map has exceeded the maximum number of territories supported by conquest.\nTo ensure proper functioning, please delete one or more territories.");
      }
      selectTerritory(ter, true, true, SelectionOperation.SET);
    }
    else
    {
      warnMessage("You must add at least one continent before adding territories.");
    }
  }
  
  public void addTerritoryMode()
  {
    abortOperation(true);
    setAddTerritoryMode(true);
  }
  
  JMenuItem addTip(JMenuItem mi, String tip)
  {
    mi.setToolTipText(tip);
    return mi;
  }
  
  private void analyzeApply(boolean applyAdditions, boolean applyRemovals)
  {
    if (this.analyzer == null)
    {
      infoMessage("You must run the border analyzer before applying recommendations.");
      return;
    }
    int adds = applyAdditions ? countRecommendations(true) : 0;
    int removes = applyRemovals ? countRecommendations(false) : 0;
    if ((adds == 0) && (removes == 0))
    {
      infoMessage("There are no recommended changes at this time.");
      return;
    }
    String msg = "This will apply the following analyzer recommendations:\n\n";
    if (adds > 0) {
      msg = msg + "   - " + adds + " new territory connection(s) will be added\n";
    }
    if (removes > 0) {
      msg = msg + "   - " + removes + " existing territory connection(s) will be removed\n";
    }
    msg = msg + "\nDo you wish to proceed?";
    if (confirm(msg, "Confirm Apply Analyzer Recommendations", 2, false)) {
      for (Territory ter : this.map.territories)
      {
        if (applyAdditions)
        {
          ArrayList<Territory> l = ter.getMissingLinks();
          if (l != null) {
            for (Territory border : l) {
              linkTerritories(ter, border, true, false);
            }
          }
        }
        if (applyRemovals)
        {
          ArrayList<Territory> l = ter.getExtraLinks();
          if (l != null) {
            for (Territory notBorder : l) {
              linkTerritories(ter, notBorder, false, false);
            }
          }
        }
      }
    }
  }
  
  void analyzeBorders(boolean prompt)
  {
    if ((this.analyzer == null) || (this.analyzer.isDone()))
    {
      this.analyzer = new AnalyzerWorker(true, prompt);
      this.analyzer.execute();
    }
  }
  
  private void analyzeMap(boolean prompt)
    throws OperationCanceledException
  {
    if ((!prompt) || (openAnalyzeOptionsDialog()))
    {
      if (this.abOptions == null) {
        this.abOptions = new AnalyzeBordersOptions(this.props);
      }
      this.mapDisplay.analyzeBorders(this.abOptions);
      
      this.menuOptionsDrawLinks.setSelected(true);
      this.menuOptionsDrawRecommendedLinksAll.setSelected(true);
      optionsSetDrawLinks();
      
      updateTerritoryData();
    }
  }
  
  private void analyzeMapTerritories()
    throws OperationCanceledException
  {
    if (this.amtOptionsDialog == null)
    {
      this.amtOptionsDialog = new AnalyzeMapTerritoriesOptionsDialog(this);
      this.amtOptionsDialog.initialize();
      this.amtOptionsDialog.setAnalyzerOptions(Color.WHITE, 200, "", "");
    }
    ComponentUtil.center(this.amtOptionsDialog, this.centerComp);
    this.amtOptionsDialog.setVisible(true);
    if (!this.amtOptionsDialog.isCanceled())
    {
      int gen = this.mapDisplay.analyzeGenerateTerritories(this.amtOptionsDialog.getGroundColor(), 
        this.amtOptionsDialog.getMinimumGroundSize(), this.amtOptionsDialog.getContinentName(), 
        this.amtOptionsDialog.getTerritoryPrefix());
      updateTerritoryData();
      updateContinentData();
      if (gen != -1) {
        infoMessage(gen + " new territories were generated.");
      }
    }
  }
  
  void clearAnalyzerRecommendations()
  {
    for (Territory ter : this.map.territories) {
      ter.clearRecommendedLinks();
    }
    this.analyzer = null;
    this.mapDisplay.repaint();
  }
  
  boolean confirm(String question, String title)
  {
    return confirm(question, title == null ? "Conquest Map Maker" : title, 3, true);
  }
  
  boolean confirm(String question, String title, int messageType, boolean defaultButton)
  {
    Object[] options = { "Yes", "No" };
    int sel = JOptionPane.showOptionDialog(this.centerComp, question, title == null ? "Conquest Map Maker" : title, 
      -1, messageType, null, options, options[1]);
    
    this.mapDisplay.requestFocus();
    
    return sel == 0;
  }
  
  void contSelect(Continent cont)
  {
    if (cont == null) {
      return;
    }
    abortOperation(true);
    HashSet<Territory> ters = this.map.getContinentTerritories(cont);
    
    selectTerritories(ters);
    
    boolean first = true;
    for (Territory ter : ters)
    {
      selectTerritoryInTable(ter, first ? SelectionOperation.SET : SelectionOperation.ADD, false);
      first = false;
    }
    this.contTable.requestFocus();
  }
  
  private int countRecommendations(boolean adds)
  {
    int count = 0;
    for (Territory ter : this.map.territories)
    {
      ArrayList<Territory> al = adds ? ter.getMissingLinks() : ter.getExtraLinks();
      if (al != null) {
        count += al.size();
      }
    }
    return count / 2;
  }
  
  public boolean createBlankBitmap(File path, int wide, int high)
  {
    try
    {
      this.mapDisplay.createBlankBitmap(path, wide, high);
      return true;
    }
    catch (IOException e)
    {
      logger.severe("Failed to create image \"" + path + "\": " + e);
      errorMessage("Unable to create image file \"" + path + "\":\n\n" + e.getMessage());
    }
    return false;
  }
  
  void deleteSelectedContinent()
  {
    abortOperation(true);
    
    ListSelectionModel lsm = this.contTable.getSelectionModel();
    if (lsm.isSelectionEmpty())
    {
      infoMessage("You must select a continent in the continent table in order to delete it.");
    }
    else
    {
      int selrow = lsm.getMinSelectionIndex();
      selrow = this.contTable.convertRowIndexToModel(selrow);
      Continent cont = (Continent)this.map.continents.get(selrow);
      if (cont != null)
      {
        int doomedTers = this.map.countTerritories(cont);
        String cm = "Are you sure you want to delete continent \"" + cont.name + "\"?";
        if (doomedTers > 0) {
          cm = 
            cm + "\nThis will also delete its " + doomedTers + (doomedTers > 1 ? " territories!" : " territory.");
        }
        if (confirm(cm, "Confirm Continent Deletion", 2, false))
        {
          this.map.deleteContinent(cont);
          selectTerritoryNone();
          updateContinentData();
          this.terTableModel.fireTableDataChanged();
          this.contTableModel.fireTableDataChanged();
        }
      }
    }
  }
  
  void deleteSelectedTerritories()
  {
    Collection<Territory> ters = this.mapDisplay.getSelectedTerritories();
    
    abortOperation(true);
    if (!ters.isEmpty())
    {
      StringBuilder sb = new StringBuilder();
      if (ters.size() == 1)
      {
        sb.append("Are you sure you want to delete territory \"");
        for (Territory ter : ters) {
          sb.append(ter.name);
        }
        sb.append("\"?");
      }
      else
      {
        sb.append("Are you sure you want to delete all ").append(ters.size()).append(
          " selected territories?");
      }
      if (confirm(sb.toString(), "Confirm Territory Deletion", 3, false))
      {
        for (Territory ter : ters) {
          this.map.deleteTerritory(ter);
        }
        selectTerritoryNone();
        updateTerritoryData();
      }
    }
    else
    {
      updateInfoFieldBold("You must select a territory in order to delete it");
    }
  }
  
  public void dragEnter(DropTargetDragEvent dtde)
  {
    dtde.acceptDrag(3);
  }
  
  public void dragExit(DropTargetEvent dte) {}
  
  public void dragOver(DropTargetDragEvent dtde) {}
  
  public void drop(DropTargetDropEvent dtde)
  {
    try
    {
      Transferable dtdeTrans = dtde.getTransferable();
      if (dtdeTrans.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
      {
        dtde.acceptDrop(3);
        List<File> files = (List)dtdeTrans.getTransferData(DataFlavor.javaFileListFlavor);
        if (files.size() > 0)
        {
          File f = (File)files.get(0);
          if (f.getPath().toLowerCase().endsWith(".map")) {
            mapPromptAndLoad(((File)files.get(0)).getPath());
          } else {
            infoMessage("Incorrect file type: " + f.getPath());
          }
        }
        dtde.getDropTargetContext().dropComplete(true);
      }
      else
      {
        dtde.rejectDrop();
      }
    }
    catch (IOException e)
    {
      logger.warning("Exception during drag/drop operation [" + e + "]");
      dtde.rejectDrop();
    }
    catch (UnsupportedFlavorException e)
    {
      logger.warning("Exception during drag/drop operation [" + e + "]");
      dtde.rejectDrop();
    }
  }
  
  public void dropActionChanged(DropTargetDragEvent dtde) {}
  
  void editContinentCell(Continent cont, int column)
  {
    int row = getContinentTableRow(cont.name);
    if (row != -1)
    {
      this.contTable.editCellAt(row, column);
      
      Component ec = this.contTable.getEditorComponent();
      if ((ec != null) && ((ec instanceof JTextField))) {
        ((JTextField)ec).selectAll();
      }
      this.contTable.requestFocus();
    }
  }
  
  void editMapImage()
  {
    if (this.imageEditProc != null) {
      return;
    }
    if (!imageEditorCheck()) {
      return;
    }
    if ((this.map.getImageFilePath() == null) || (this.map.getImageFilePath().isEmpty()))
    {
      infoMessage("You must select or create a map image from the map properties dialog before running the image editor.");
      return;
    }
    imageEditorExecute();
  }
  
  void editTerritoryCell(Territory ter, int column)
  {
    int row = getTerritoryTableRow(ter.name);
    if (row != -1)
    {
      this.terTable.editCellAt(row, column);
      
      Component ec = this.terTable.getEditorComponent();
      if ((ec != null) && ((ec instanceof JTextField))) {
        ((JTextField)ec).selectAll();
      }
      this.terTable.requestFocus();
    }
  }
  
  public void errorMessage(Object info)
  {
    popupMessage(info, null, 0);
  }
  
  int getContinentTableRow(String contName)
  {
    int row = this.map.findContinentIndex(contName);
    if (row != -1) {
      row = this.contTable.convertRowIndexToView(row);
    }
    return row;
  }
  
  JFileChooser getMapFc()
  {
    if (this.mapFc == null) {
      this.mapFc = makeFc();
    }
    this.mapFc.resetChoosableFileFilters();
    this.mapFc.addChoosableFileFilter(new ExFileFilter("map", "Conquest Map Files"));
    return this.mapFc;
  }
  
  JFileChooser getMapImageEditorFc()
  {
    JFileChooser fc = makeFc();
    fc.addChoosableFileFilter(new ExFileFilter("exe", "Program Files"));
    return fc;
  }
  
  public JFileChooser getMapImageFc()
  {
    if (this.mapFc == null) {
      this.mapFc = makeFc();
    }
    this.mapFc.resetChoosableFileFilters();
    this.mapFc.addChoosableFileFilter(new ExFileFilter("bmp", "Bitmap Files"));
    return this.mapFc;
  }
  
  private Level getPropLogLevel(String propName, Level defaultLevel)
  {
    try
    {
      String lstr = this.props.getProperty(propName);
      if (lstr == null) {
        return defaultLevel;
      }
      return Level.parse(this.props.getProperty(propName));
    }
    catch (Exception e) {}
    return defaultLevel;
  }
  
  Continent getSelectedContinent()
  {
    int selrow = this.contTable.getSelectionModel().getMinSelectionIndex();
    if (selrow == -1) {
      return null;
    }
    selrow = this.contTable.convertRowIndexToModel(selrow);
    return (Continent)this.map.continents.get(selrow);
  }
  
  private ArrayList<Integer> getSelectedModelIndices(ListSelectionModel lsm, JTable table)
  {
    ArrayList<Integer> al = new ArrayList();
    int selStart = lsm.getMinSelectionIndex();
    int selEnd = lsm.getMaxSelectionIndex();
    for (int i = selStart; i <= selEnd; i++) {
      if (this.terTable.isRowSelected(i))
      {
        int selrow = this.terTable.convertRowIndexToModel(i);
        al.add(Integer.valueOf(selrow));
      }
    }
    return al;
  }
  
  int getTerritoryTableRow(String terName)
  {
    int row = this.map.findTerritoryIndex(terName);
    if (row != -1) {
      row = this.terTable.convertRowIndexToView(row);
    }
    return row;
  }
  
  boolean imageEditorCheck()
  {
    String imed = this.props.getProperty("ImageEditorPath");
    if (imed == null)
    {
      File paint = FileUtil.searchPath("mspaint.exe", System.getenv("SystemRoot") + "/system32");
      if (paint != null)
      {
        imed = paint.getPath();
        this.props.setProperty("ImageEditorPath", imed);
      }
    }
    if (new File(imed).exists()) {
      return true;
    }
    return selectImageEditor();
  }
  
  void imageEditorExecute()
  {
    try
    {
      this.imageEditProc = Runtime.getRuntime()
        .exec(this.props.getProperty("ImageEditorPath") + " \"" + this.map.getImageFilePath() + 
        "\"");
      
      Runnable imageEditorRunner = new Runnable()
      {
        public void run()
        {
          if (ConquestMapMaker.this.imageEditProc != null) {
            try
            {
              ConquestMapMaker.this.actionEditMapImage.setEnabled(false);
              ConquestMapMaker.this.imageEditProc.waitFor();
              ConquestMapMaker.this.actionEditMapImage.setEnabled(true);
            }
            catch (InterruptedException localInterruptedException) {}
          }
          if (ConquestMapMaker.this.map != null) {
            ConquestMapMaker.this.reloadMapImage();
          }
          ConquestMapMaker.this.imageEditProc = null;
        }
      };
      new Thread(imageEditorRunner).start();
    }
    catch (IOException e)
    {
      logger.severe("Unable to invoke image editor to edit map image: " + e);
      errorMessage("Unable to invoke image editor to edit map image: " + e);
    }
  }
  
  public void infoMessage(Object info)
  {
    popupMessage(info, null, 1);
  }
  
  public void infoMessage(Object info, String title)
  {
    popupMessage(info, title, 1);
  }
  
  public void init(String mapPath)
  {
    this.props = new ExtendedProperties();
    this.props.setDefaultIndicatorValue("default");
    
    loadProperties();
    logger.fine("Initialization sequence: properties file loaded");
    setDefaultCloseOperation(0);
    
    logger.fine("Initialization sequence: initializing territory table");
    initTerritoryTable();
    
    logger.fine("Initialization sequence: initializing continent table");
    initContinentTable();
    
    initActionSets();
    
    logger.fine("Initialization sequence: initializing GUI");
    initGui();
    
    logger.fine("Initialization sequence: initializing help");
    initHelp();
    
    optionsReadTerritoryColor();
    optionsReadDrawLinks();
    
    initKeyStrokes();
    
    this.initCompleted = true;
    if (mapPath == null) {
      mapPath = this.props.getProperty("LastMap");
    }
    if ((mapPath != null) && (mapPath.length() > 0)) {
      try
      {
        logger.fine("Initialization sequence: loading map [" + mapPath + "]");
        mapLoad(mapPath);
        logger.fine("Initialization sequence: loaded map [" + mapPath + "]");
      }
      catch (FileNotFoundException e)
      {
        setMap(new ConquestMap(this, this.props));
      }
    } else {
      setMap(new ConquestMap(this, this.props));
    }
    this.msp.setHorizontalScrollBarPolicy(30);
    updateInfoField();
    
    String propVersion = this.props.getProperty("Version");
    if ((propVersion == null) || (!"2.0.0".equals(propVersion))) {
      showHelpAbout();
    }
  }
  
  void initActionSets()
  {
    this.actionSetAll = new HashSet();
    addActions(this.actionSetAll, new Action[] { this.actionShowContinentInfo, this.actionAnalyzeBorders, this.actionAnalyzeTerritories, 
      this.actionSelectAllTerritories, this.actionAnalyzeCancel, this.actionAnalyzeClearRecommendations, 
      this.actionToggleShowTerritoryLinks, this.actionAnalyzeApply, this.actionAnalyzeApplyMissing, this.actionMoveUp, 
      this.actionAbortOperation, this.actionMouseControls, this.actionMoveDown, this.actionMoveLeft, this.actionMoveRight, 
      this.actionNew, this.actionOpen, this.actionResizeMap, this.actionSave, this.actionRevert, this.actionSaveAs, 
      this.actionMapPropertiesDialog, this.actionVerifyMap, this.actionReloadMapImage, this.actionEditMapImage, 
      this.actionSelectImageEditor, this.actionHelpAbout, this.actionHelp, this.actionAddTerritory, 
      this.actionDeleteTerritory, this.actionAddContinent, this.actionDeleteContinent, this.actionRenameTerritoriesAll, 
      this.actionCenterSelectedTerritories, this.actionPlafJava, this.actionPlafNative, this.actionPlafMotif, 
      this.actionSetLogLevel });
    
    this.actionSetPrimary = new HashSet();
    this.actionSetPrimary.addAll(this.actionSetAll);
    this.actionSetPrimary.remove(this.actionHelp);
    this.actionSetPrimary.remove(this.actionHelpAbout);
    
    this.actionSetAnalyzeEnabled = new HashSet();
    addActions(this.actionSetAnalyzeEnabled, new Action[] { this.actionAnalyzeCancel, this.actionHelp, this.actionHelpAbout });
    
    this.actionAnalyzeCancel.setEnabled(false);
  }
  
  void initContinentTable()
  {
    this.contTableModel = new ContinentTableModel();
    this.contTableModel.addTableModelListener(this);
    
    this.contTable = new JTable(this.contTableModel);
    this.contTable.setRowHeight(18);
    this.contTable.setIntercellSpacing(new Dimension(7, 1));
    this.contTable.setRowSorter(new TableRowSorter(this.contTableModel));
    this.contTable.setSelectionMode(0);
    this.contTable.addMouseListener(this);
    
    ListSelectionModel rowSM = this.contTable.getSelectionModel();
    rowSM.addListSelectionListener(this);
    
    TableColumn contCol = this.contTable.getColumnModel().getColumn(0);
    contCol.setPreferredWidth(100);
    
    TableColumn contBonusCol = this.contTable.getColumnModel().getColumn(1);
    contBonusCol.setPreferredWidth(20);
  }
  
  void initGui()
  {
    setPlaf(this.props.getProperty("LookAndFeel", UIManager.getSystemLookAndFeelClassName()));
    
    this.cmmIcon = makeImageIcon("icons/Conquest_solid.gif");
    this.contIcon = makeImageIcon("icons/Continent.gif");
    this.analyzerIcon = makeImageIcon("icons/Analyze.gif");
    this.buttonLinksOn = makeImageIcon("icons/ToggleLinkLinesEnabled.gif");
    this.buttonLinksOff = makeImageIcon("icons/ToggleLinkLines.gif");
    
    setIconImage(this.cmmIcon.getImage());
    
    initToolbar();
    
    this.mapDisplay = new MapDisplay();
    this.mapDisplay.addMouseListener(this);
    this.mapDisplay.addKeyListener(this);
    this.mapDisplay.addMouseMotionListener(this);
    this.mapDisplay.loadProperties(this.props);
    
    this.msp = new JScrollPane();
    this.msp.getViewport().add(this.mapDisplay);
    this.mapPanel = new JPanel(new BorderLayout());
    
    this.mapTitleBorder = BorderFactory.createTitledBorder("Map Display");
    Border tb = BorderFactory.createCompoundBorder(BorderFactory.createLoweredBevelBorder(), 
      BorderFactory.createRaisedBevelBorder());
    this.mapPanel.setBorder(BorderFactory.createCompoundBorder(this.mapTitleBorder, tb));
    
    this.mapPanel.add(this.msp);
    
    JPanel terTablePanel = new JPanel(new BorderLayout());
    JScrollPane ttsp = new JScrollPane(this.terTable);
    terTablePanel.add(ttsp);
    terTablePanel.setBorder(makeTableBorder("Territories"));
    
    JPanel contTablePanel = new JPanel(new BorderLayout());
    JScrollPane ctsp = new JScrollPane(this.contTable);
    contTablePanel.add(ctsp);
    contTablePanel.setBorder(makeTableBorder("Continents"));
    
    JPanel statusPanel = new JPanel(new BorderLayout());
    this.locField = new JLabel();
    this.infoField = new JLabel("No territory selected", 0);
    statusPanel.add(this.infoField);
    statusPanel.add(this.locField, "East");
    
    JPanel leftPanel = new JPanel(new BorderLayout());
    leftPanel.add(this.mapPanel);
    leftPanel.add(statusPanel, "South");
    
    this.splitPane2 = new JSplitPane(0, terTablePanel, contTablePanel);
    
    this.splitPane2.setResizeWeight(1.0D);
    
    this.splitPane = new JSplitPane(1, leftPanel, this.splitPane2);
    this.splitPane.setResizeWeight(1.0D);
    
    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(this.splitPane, "Center");
    getContentPane().add(this.toolBar, "North");
    
    initMenu();
    setJMenuBar(this.menuBar);
    
    boolean locFound = this.props.containsKey("WindowLocationX");
    
    setLocation(this.props.getIntProperty("WindowLocationX", 0), 
      this.props.getIntProperty("WindowLocationY", 0));
    setSize(this.props.getIntProperty("WindowSizeWidth", 800), this.props
      .getIntProperty("WindowSizeHeight", 600));
    setExtendedState(this.props.getIntProperty("WindowState", 0));
    
    this.splitPane.setDividerLocation(this.props.getIntProperty("WindowDivide", 520));
    this.splitPane2.setDividerLocation(this.props.getIntProperty("TableDivide", 350));
    
    this.centerComp = this.mapDisplay;
    if (!locFound) {
      ComponentUtil.center(this);
    }
    setVisible(true);
    
    addWindowListener(this);
    
    this.dropTarget = new DropTarget(this, this);
    
    this.actionAnalyzeCancel.setEnabled(false);
  }
  
  void initHelp()
  {
    if (this.helpViewer == null)
    {
      DocumentViewerObject[] helpTopics = {
        new DocumentViewerObject("About Conquest Map Maker", "help/about.html"), 
        new DocumentViewerObject("Quick start", "help/quickstart.html"), 
        new DocumentViewerObject("The user interface", "help/display.html"), 
        new DocumentViewerObject("Controls", "help/controls.html"), 
        new DocumentViewerObject("Using the territory generator", "help/generator.html"), 
        new DocumentViewerObject("Using the border analyzer", "help/analyzer.html"), 
        new DocumentViewerObject("Territory scaling", "help/scaling.html") };
      this.helpViewer = new ListDocumentViewer(helpTopics);
      this.helpViewer.init(null);
      this.helpViewer.selectByName("Quick start");
      this.helpViewer.setSelectorTitle("Help Topics");
    }
  }
  
  void initKeyStrokes()
  {
    registerKeyAction(this.mapDisplay, "F1", this.actionHelp, true);
    registerKeyAction(this.mapDisplay, "ESCAPE", this.actionAbortOperation, false);
    
    registerKeyAction(this.terTable, "DELETE", this.actionDeleteTerritory, false);
    registerKeyAction(this.mapDisplay, "DELETE", this.actionDeleteTerritory, true);
    registerKeyAction(this.terTable, "INSERT", this.actionAddTerritory, true);
    registerKeyAction(this.terTable, "control A", this.actionSelectAllTerritories, true);
    
    registerKeyAction(this.mapDisplay, "UP", this.actionMoveUp, false);
    registerKeyAction(this.mapDisplay, "DOWN", this.actionMoveDown, false);
    registerKeyAction(this.mapDisplay, "LEFT", this.actionMoveLeft, false);
    registerKeyAction(this.mapDisplay, "RIGHT", this.actionMoveRight, false);
    registerKeyAction(this.mapDisplay, "control UP", this.actionMoveUp, false);
    registerKeyAction(this.mapDisplay, "control DOWN", this.actionMoveDown, false);
    registerKeyAction(this.mapDisplay, "control LEFT", this.actionMoveLeft, false);
    registerKeyAction(this.mapDisplay, "control RIGHT", this.actionMoveRight, false);
  }
  
  void initMenu()
  {
    JMenu menuFile = new JMenu("File");
    menuFile.add(new JMenuItem(this.actionNew));
    menuFile.addSeparator();
    menuFile.add(new JMenuItem(this.actionOpen));
    menuFile.add(new JMenuItem(this.actionRevert));
    menuFile.addSeparator();
    menuFile.add(new JMenuItem(this.actionSave));
    menuFile.add(new JMenuItem(this.actionSaveAs));
    
    JMenu menuMap = new JMenu("Map");
    menuMap.add(new JMenuItem(this.actionAddTerritory));
    menuMap.add(new JMenuItem(this.actionDeleteTerritory));
    menuMap.addSeparator();
    menuMap.add(new JMenuItem(this.actionAddContinent));
    menuMap.add(new JMenuItem(this.actionDeleteContinent));
    menuMap.add(new JMenuItem(this.actionShowContinentInfo));
    menuMap.addSeparator();
    menuMap.add(new JMenuItem(this.actionReloadMapImage));
    menuMap.add(new JMenuItem(this.actionEditMapImage));
    menuMap.addSeparator();
    menuMap.add(this.actionMapPropertiesDialog);
    
    JMenu menuTools = new JMenu("Tools");
    menuTools.add(new JMenuItem(this.actionAnalyzeTerritories));
    menuTools.addSeparator();
    menuTools.add(new JMenuItem(this.actionAnalyzeBorders));
    menuTools.add(new JMenuItem(this.actionAnalyzeClearRecommendations));
    menuTools.add(new JMenuItem(this.actionAnalyzeApply));
    menuTools.add(new JMenuItem(this.actionAnalyzeApplyMissing));
    menuTools.addSeparator();
    menuTools.add(new JMenuItem(this.actionResizeMap));
    menuTools.addSeparator();
    menuTools.add(new JMenuItem(this.actionRenameTerritoriesAll));
    menuTools.addSeparator();
    menuTools.add(new JMenuItem(this.actionCenterSelectedTerritories));
    menuTools.addSeparator();
    menuTools.add(this.actionVerifyMap);
    menuTools.addSeparator();
    menuTools.add(new JMenuItem(this.actionAnalyzeCancel));
    
    this.menuOptionsColorTerritoriesNone = new JRadioButtonMenuItem("No territory coloring");
    this.menuOptionsColorTerritoriesSelected = new JRadioButtonMenuItem("Color selected territory only");
    this.menuOptionsColorTerritoriesBorders = new JRadioButtonMenuItem(
      "Color selected and bordering territories (default)");
    
    ButtonGroup bg = new ButtonGroup();
    bg.add(this.menuOptionsColorTerritoriesNone);
    bg.add(this.menuOptionsColorTerritoriesSelected);
    bg.add(this.menuOptionsColorTerritoriesBorders);
    
    JMenu menuColorTerritories = new JMenu("Territory coloring");
    menuColorTerritories.add(this.menuOptionsColorTerritoriesNone);
    menuColorTerritories.add(this.menuOptionsColorTerritoriesSelected);
    menuColorTerritories.add(this.menuOptionsColorTerritoriesBorders);
    
    this.menuOptionsColorTerritoriesNone.addActionListener(this);
    this.menuOptionsColorTerritoriesSelected.addActionListener(this);
    this.menuOptionsColorTerritoriesBorders.addActionListener(this);
    
    this.menuOptionsDrawLinks = new JCheckBoxMenuItem("Draw territory connections");
    this.menuOptionsDrawLinks.setToolTipText("Toggles territory connection lines on and off");
    this.menuOptionsAnalyzeOnLoad = new JCheckBoxMenuItem("Analyze borders after loading", 
      this.props.getBooleanProperty("AnalyzeOnLoad", false));
    this.menuOptionsAnalyzeOnLoad
      .setToolTipText("Runs the border analyzer using the current settings immediately after loading a map");
    this.menuOptionsDrawLinksNone = new JRadioButtonMenuItem("Hide existing connections (default)");
    this.menuOptionsDrawLinksSelected = new JRadioButtonMenuItem(
      "Show existing connections from selected territory only");
    this.menuOptionsDrawLinksAll = new JRadioButtonMenuItem("Show existing connections");
    this.menuOptionsDrawRecommendedLinksNone = new JRadioButtonMenuItem("Hide recommended connections");
    this.menuOptionsDrawRecommendedLinksSelected = new JRadioButtonMenuItem(
      "Show recommended connections from selected territory only");
    this.menuOptionsDrawRecommendedLinksAll = new JRadioButtonMenuItem(
      "Show recommended connections (default)");
    
    bg = new ButtonGroup();
    bg.add(this.menuOptionsDrawLinksNone);
    bg.add(this.menuOptionsDrawLinksSelected);
    bg.add(this.menuOptionsDrawLinksAll);
    ButtonGroup bg2 = new ButtonGroup();
    bg2.add(this.menuOptionsDrawRecommendedLinksNone);
    bg2.add(this.menuOptionsDrawRecommendedLinksSelected);
    bg2.add(this.menuOptionsDrawRecommendedLinksAll);
    
    JMenu menuDrawLinks = new JMenu("Territory connection lines");
    menuDrawLinks.add(this.menuOptionsDrawLinks);
    menuDrawLinks.addSeparator();
    menuDrawLinks.add(this.menuOptionsDrawLinksNone);
    menuDrawLinks.add(this.menuOptionsDrawLinksSelected);
    menuDrawLinks.add(this.menuOptionsDrawLinksAll);
    menuDrawLinks.addSeparator();
    menuDrawLinks.add(this.menuOptionsDrawRecommendedLinksNone);
    menuDrawLinks.add(this.menuOptionsDrawRecommendedLinksSelected);
    menuDrawLinks.add(this.menuOptionsDrawRecommendedLinksAll);
    
    this.menuOptionsDrawLinks.addActionListener(this);
    this.menuOptionsDrawLinksNone.addActionListener(this);
    this.menuOptionsDrawLinksSelected.addActionListener(this);
    this.menuOptionsDrawLinksAll.addActionListener(this);
    this.menuOptionsDrawRecommendedLinksNone.addActionListener(this);
    this.menuOptionsDrawRecommendedLinksSelected.addActionListener(this);
    this.menuOptionsDrawRecommendedLinksAll.addActionListener(this);
    
    JMenu menuPlaf = new JMenu("Skin");
    menuPlaf.add(new JMenuItem(this.actionPlafJava));
    menuPlaf.add(new JMenuItem(this.actionPlafNative));
    menuPlaf.add(new JMenuItem(this.actionPlafMotif));
    
    this.menuOptionsWrapMoveTerritories = new JCheckBoxMenuItem("Wrap territory moves", 
      this.props.getTrueFalseProperty("WrapMoveTerritories", false));
    this.menuOptionsWrapMoveTerritories.addActionListener(this);
    
    JMenu menuOptions = new JMenu("Options");
    menuOptions.add(menuColorTerritories);
    menuOptions.add(menuDrawLinks);
    menuOptions.addSeparator();
    menuOptions.add(
      addTip(this.menuOptionsWrapMoveTerritories, "If this option is set, when moving all territories using control-drag, territories that are moved off of one edge of the map will wrap to the opposite edge"));
    
    menuOptions.addSeparator();
    menuOptions
      .add(addTip(
      new JMenuItem(this.actionSelectImageEditor), 
      "Use this option to select which external image editor to use to edit map images.\nThe selected editor must support the Windows bitmap (.bmp) format."));
    
    menuOptions.addSeparator();
    menuOptions.add(this.menuOptionsAnalyzeOnLoad);
    menuOptions.addSeparator();
    menuOptions.add(menuPlaf);
    menuOptions.addSeparator();
    menuOptions.add(new JMenuItem(this.actionSetLogLevel));
    
    JMenu menuHelp = new JMenu("Help");
    menuHelp.add(new JMenuItem(this.actionHelp));
    menuHelp.addSeparator();
    menuHelp.add(new JMenuItem(this.actionHelpAbout));
    
    this.menuBar = new JMenuBar();
    this.menuBar.add(menuFile);
    this.menuBar.add(menuMap);
    this.menuBar.add(menuTools);
    this.menuBar.add(menuOptions);
    this.menuBar.add(menuHelp);
  }
  
  void initTerritoryTable()
  {
    this.terTableModel = new TerritoryTableModel();
    this.terTableModel.addTableModelListener(this);
    
    this.terTable = new JTable(this.terTableModel);
    this.terTable.setRowHeight(18);
    this.terTable.setIntercellSpacing(new Dimension(7, 1));
    this.terTable.setRowSorter(new TableRowSorter(this.terTableModel));
    this.terTable.setSelectionMode(2);
    
    ListSelectionModel rowSM = this.terTable.getSelectionModel();
    rowSM.addListSelectionListener(this);
    
    this.continentComboBox = new JComboBox();
    this.continentComboBox.setEditable(false);
    this.contComboEditor = new DefaultCellEditor(this.continentComboBox);
    
    TableColumn col = this.terTable.getColumnModel().getColumn(0);
    col.setPreferredWidth(60);
    
    col = this.terTable.getColumnModel().getColumn(1);
    col.setPreferredWidth(60);
    col.setCellEditor(this.contComboEditor);
  }
  
  void initToolbar()
  {
    if (this.toolBar == null) {
      this.toolBar = new JToolBar();
    } else {
      this.toolBar.removeAll();
    }
    this.toolBar.add(initToolbarButton(new JButton(this.actionNew)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionOpen)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionSave)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionMapPropertiesDialog)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionReloadMapImage)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionEditMapImage)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionAddTerritory)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionDeleteTerritory)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionAddContinent)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionDeleteContinent)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionToggleShowTerritoryLinks)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionCenterSelectedTerritories)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionAnalyzeTerritories)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionAnalyzeBorders)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionAnalyzeClearRecommendations)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionAnalyzeApply)));
    this.toolBar.add(initToolbarButton(new JButton(this.actionAnalyzeApplyMissing)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionAnalyzeCancel)));
    this.toolBar.addSeparator();
    this.toolBar.add(initToolbarButton(new JButton(this.actionVerifyMap)));
  }
  
  JButton initToolbarButton(JButton b)
  {
    if (b != null)
    {
      b.setToolTipText(b.getText());
      b.setText("");
    }
    return b;
  }
  
  private boolean isControlAdd(MouseEvent e, boolean dragging)
  {
    return (!dragging) && (SwingUtilities.isLeftMouseButton(e)) && (e.isShiftDown()) && (!e.isControlDown()) && (!e.isAltDown());
  }
  
  private boolean isControlLinkOneWay(MouseEvent e, boolean dragging)
  {
    return (!dragging) && (SwingUtilities.isRightMouseButton(e)) && (!e.isShiftDown()) && (e.isControlDown()) && (!e.isAltDown());
  }
  
  private boolean isControlLinkToggle(MouseEvent e, boolean dragging)
  {
    return (!dragging) && (SwingUtilities.isRightMouseButton(e)) && (!e.isShiftDown()) && (!e.isControlDown()) && (!e.isAltDown());
  }
  
  private boolean isControlMoveAll(MouseEvent e, boolean dragging)
  {
    return (dragging) && (SwingUtilities.isLeftMouseButton(e)) && (!e.isShiftDown()) && (e.isControlDown()) && (!e.isAltDown());
  }
  
  private boolean isControlMoveOne(MouseEvent e, boolean dragging)
  {
    return (dragging) && (SwingUtilities.isLeftMouseButton(e)) && (!e.isShiftDown()) && (!e.isControlDown()) && (!e.isAltDown());
  }
  
  private boolean isControlScaleAll(MouseEvent e, boolean dragging)
  {
    return (dragging) && (SwingUtilities.isLeftMouseButton(e)) && (!e.isShiftDown()) && (e.isControlDown()) && (e.isAltDown());
  }
  
  private boolean isControlSelect(MouseEvent e, boolean dragging)
  {
    return (!dragging) && (SwingUtilities.isLeftMouseButton(e)) && (!e.isShiftDown()) && (!e.isControlDown()) && (!e.isAltDown());
  }
  
  private boolean isControlSelectToggle(MouseEvent e, boolean dragging)
  {
    return (!dragging) && (SwingUtilities.isLeftMouseButton(e)) && (!e.isShiftDown()) && (e.isControlDown()) && (!e.isAltDown());
  }
  
  private boolean isControlSetBridgeColor(MouseEvent e, boolean dragging)
  {
    return (!dragging) && (SwingUtilities.isLeftMouseButton(e)) && (e.isShiftDown()) && (e.isControlDown()) && (!e.isAltDown());
  }
  
  public void keyPressed(KeyEvent e) {}
  
  public void keyReleased(KeyEvent e)
  {
    if (this.translatingTerritories != null)
    {
      this.mapDisplay.selectTerritories(this.translatingTerritories);
      this.translatingTerritories = null;
      this.mapDisplay.repaint();
    }
  }
  
  public void keyTyped(KeyEvent e) {}
  
  void linkTerritories(Territory a, Territory b, boolean link, boolean oneWay)
  {
    if ((a == null) || (b == null) || (a == b)) {
      return;
    }
    this.map.dirty = true;
    if (!link)
    {
      a.removeLink(b);
      b.removeLink(a);
    }
    else
    {
      a.addLink(b);
      if (!oneWay) {
        b.addLink(a);
      }
    }
    this.mapDisplay.colorTerritory(a);
    this.mapDisplay.colorTerritory(b);
    this.mapDisplay.repaint();
  }
  
  void linkTerritoriesOneWay(Territory a, Territory b)
  {
    if ((a == null) || (b == null) || (a == b)) {
      return;
    }
    if ((a.links.contains(b)) && (!b.links.contains(a))) {
      linkTerritories(a, b, false, false);
    } else {
      linkTerritories(a, b, true, true);
    }
  }
  
  void linkTerritoriesToggle(Territory a, Territory b)
  {
    if ((a == null) || (b == null) || (a == b)) {
      return;
    }
    linkTerritories(a, b, (!a.links.contains(b)) && (!b.links.contains(a)), false);
  }
  
  void loadProperties()
  {
    try
    {
      FileInputStream in = new FileInputStream(this.propsFilePath);
      this.props.load(in);
      
      logger.setUseParentHandlers(false);
      
      this.consoleLogHandler = null;
      Level consoleLevel = getPropLogLevel("LogConsoleLevel", Level.WARNING);
      setConsoleLogLevel(consoleLevel);
      
      this.fileLogHandler = null;
      Level fileLevel = getPropLogLevel("LogFileLevel", Level.WARNING);
      setFileLogLevel(fileLevel);
      
      logger.fine("Debug logging active");
    }
    catch (IOException localIOException) {}
  }
  
  private void logOptionsPrompt()
  {
    String[] items = { "off", "severe", "warning", "info", "fine", "finer", "finest", "all" };
    JComboBox cl = new JComboBox(items);
    Level curLevel = this.consoleLogHandler == null ? Level.OFF : this.consoleLogHandler.getLevel();
    cl.setSelectedItem(curLevel.toString().toLowerCase());
    JComboBox fl = new JComboBox(items);
    curLevel = this.fileLogHandler == null ? Level.OFF : this.fileLogHandler.getLevel();
    fl.setSelectedItem(curLevel.toString().toLowerCase());
    
    CompactGridPanel pan = new CompactGridPanel();
    pan.add(new JLabel("Console logging level:  ", 2));
    pan.add(cl);
    pan.add(new JLabel("File logging level:  ", 2));
    pan.add(fl);
    pan.finishGrid(0, 2, 3);
    
    pan.setBorder(
      BaseDialog.createCompoundBorder(new Border[] { BorderFactory.createEmptyBorder(10, 10, 7, 10), BorderFactory.createEtchedBorder(), BorderFactory.createEmptyBorder(7, 15, 8, 15) }));
    
    JOptionPane.showMessageDialog(this, pan);
    
    String level = (String)cl.getSelectedItem();
    if (level != null) {
      setConsoleLogLevel(parseLoggerLevel(level));
    }
    level = (String)fl.getSelectedItem();
    if (level != null) {
      setFileLogLevel(parseLoggerLevel(level));
    }
  }
  
  JFileChooser makeFc()
  {
    JFileChooser fc = new JFileChooser();
    fc.setFileSelectionMode(0);
    
    String lp = this.props.getProperty("LastPath");
    if ((lp != null) && (lp.length() > 0))
    {
      File lfp = new File(lp);
      if (lfp.exists()) {
        fc.setCurrentDirectory(lfp);
      }
    }
    return fc;
  }
  
  public ImageIcon makeImageIcon(String path)
  {
    try
    {
      InputStream in = getClass().getClassLoader().getResourceAsStream(path);
      byte[] b = ResourceUtil.loadDataFromStream(in);
      if (b == null) {
        throw new IOException();
      }
      return new ImageIcon(b);
    }
    catch (IOException e)
    {
      logger.warning("Unable to load image [" + path + "]");
    }
    return null;
  }
  
  private Border makeTableBorder(String title)
  {
    return BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(2, 2, 3, 2), 
      BorderFactory.createTitledBorder(title));
  }
  
  /* Error */
  void mapLoad(String path)
    throws FileNotFoundException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_0
    //   2: getfield 1648	bcb/conquest/ConquestMapMaker:actionSetPrimary	Ljava/util/Set;
    //   5: iconst_0
    //   6: invokespecial 2347	bcb/conquest/ConquestMapMaker:readyActions	(Ljava/util/Collection;Z)V
    //   9: new 844	bcb/conquest/ConquestMap
    //   12: dup
    //   13: aload_0
    //   14: aload_0
    //   15: getfield 803	bcb/conquest/ConquestMapMaker:props	Lbcb/util/ExtendedProperties;
    //   18: invokespecial 1616	bcb/conquest/ConquestMap:<init>	(Lbcb/conquest/ConquestMapMaker;Lbcb/util/ExtendedProperties;)V
    //   21: astore_2
    //   22: aload_2
    //   23: aload_1
    //   24: invokevirtual 2351	bcb/conquest/ConquestMap:load	(Ljava/lang/String;)V
    //   27: aload_0
    //   28: aload_2
    //   29: invokevirtual 1619	bcb/conquest/ConquestMapMaker:setMap	(Lbcb/conquest/ConquestMap;)V
    //   32: aload_0
    //   33: invokevirtual 2353	bcb/conquest/ConquestMapMaker:reloadMapImage	()V
    //   36: aload_0
    //   37: iconst_0
    //   38: invokevirtual 2356	bcb/conquest/ConquestMapMaker:verifyMap	(Z)V
    //   41: goto +68 -> 109
    //   44: astore_2
    //   45: aload_2
    //   46: athrow
    //   47: astore_2
    //   48: aload_0
    //   49: new 881	java/lang/StringBuilder
    //   52: dup
    //   53: ldc_w 2359
    //   56: invokespecial 890	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   59: aload_2
    //   60: invokevirtual 1173	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   63: invokevirtual 900	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   66: invokevirtual 1190	bcb/conquest/ConquestMapMaker:errorMessage	(Ljava/lang/Object;)V
    //   69: aload_0
    //   70: aload_0
    //   71: getfield 1648	bcb/conquest/ConquestMapMaker:actionSetPrimary	Ljava/util/Set;
    //   74: iconst_1
    //   75: invokespecial 2347	bcb/conquest/ConquestMapMaker:readyActions	(Ljava/util/Collection;Z)V
    //   78: aload_0
    //   79: getfield 392	bcb/conquest/ConquestMapMaker:actionAnalyzeCancel	Ljavax/swing/AbstractAction;
    //   82: iconst_0
    //   83: invokevirtual 1661	javax/swing/AbstractAction:setEnabled	(Z)V
    //   86: goto +40 -> 126
    //   89: astore_3
    //   90: aload_0
    //   91: aload_0
    //   92: getfield 1648	bcb/conquest/ConquestMapMaker:actionSetPrimary	Ljava/util/Set;
    //   95: iconst_1
    //   96: invokespecial 2347	bcb/conquest/ConquestMapMaker:readyActions	(Ljava/util/Collection;Z)V
    //   99: aload_0
    //   100: getfield 392	bcb/conquest/ConquestMapMaker:actionAnalyzeCancel	Ljavax/swing/AbstractAction;
    //   103: iconst_0
    //   104: invokevirtual 1661	javax/swing/AbstractAction:setEnabled	(Z)V
    //   107: aload_3
    //   108: athrow
    //   109: aload_0
    //   110: aload_0
    //   111: getfield 1648	bcb/conquest/ConquestMapMaker:actionSetPrimary	Ljava/util/Set;
    //   114: iconst_1
    //   115: invokespecial 2347	bcb/conquest/ConquestMapMaker:readyActions	(Ljava/util/Collection;Z)V
    //   118: aload_0
    //   119: getfield 392	bcb/conquest/ConquestMapMaker:actionAnalyzeCancel	Ljavax/swing/AbstractAction;
    //   122: iconst_0
    //   123: invokevirtual 1661	javax/swing/AbstractAction:setEnabled	(Z)V
    //   126: aload_0
    //   127: invokevirtual 2361	bcb/conquest/ConquestMapMaker:setMapTitle	()V
    //   130: getstatic 258	bcb/conquest/ConquestMapMaker:logger	Ljava/util/logging/Logger;
    //   133: getstatic 2364	java/util/logging/Level:FINER	Ljava/util/logging/Level;
    //   136: invokevirtual 2367	java/util/logging/Logger:isLoggable	(Ljava/util/logging/Level;)Z
    //   139: ifeq +32 -> 171
    //   142: getstatic 258	bcb/conquest/ConquestMapMaker:logger	Ljava/util/logging/Logger;
    //   145: new 881	java/lang/StringBuilder
    //   148: dup
    //   149: ldc_w 2371
    //   152: invokespecial 890	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   155: aload_1
    //   156: invokevirtual 893	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: ldc_w 1360
    //   162: invokevirtual 893	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: invokevirtual 900	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   168: invokevirtual 2373	java/util/logging/Logger:finer	(Ljava/lang/String;)V
    //   171: aload_0
    //   172: getfield 2051	bcb/conquest/ConquestMapMaker:menuOptionsAnalyzeOnLoad	Ljavax/swing/JCheckBoxMenuItem;
    //   175: invokevirtual 2375	javax/swing/JCheckBoxMenuItem:isSelected	()Z
    //   178: ifeq +8 -> 186
    //   181: aload_0
    //   182: iconst_0
    //   183: invokevirtual 2378	bcb/conquest/ConquestMapMaker:analyzeBorders	(Z)V
    //   186: return
    // Line number table:
    //   Java source line #1944	-> byte code offset #0
    //   Java source line #1945	-> byte code offset #9
    //   Java source line #1946	-> byte code offset #22
    //   Java source line #1947	-> byte code offset #27
    //   Java source line #1948	-> byte code offset #32
    //   Java source line #1949	-> byte code offset #36
    //   Java source line #1951	-> byte code offset #44
    //   Java source line #1952	-> byte code offset #45
    //   Java source line #1954	-> byte code offset #47
    //   Java source line #1955	-> byte code offset #48
    //   Java source line #1958	-> byte code offset #69
    //   Java source line #1959	-> byte code offset #78
    //   Java source line #1957	-> byte code offset #89
    //   Java source line #1958	-> byte code offset #90
    //   Java source line #1959	-> byte code offset #99
    //   Java source line #1960	-> byte code offset #107
    //   Java source line #1958	-> byte code offset #109
    //   Java source line #1959	-> byte code offset #118
    //   Java source line #1961	-> byte code offset #126
    //   Java source line #1962	-> byte code offset #130
    //   Java source line #1963	-> byte code offset #142
    //   Java source line #1965	-> byte code offset #171
    //   Java source line #1966	-> byte code offset #181
    //   Java source line #1967	-> byte code offset #186
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	187	0	this	ConquestMapMaker
    //   0	187	1	path	String
    //   21	8	2	map	ConquestMap
    //   44	2	2	e	FileNotFoundException
    //   47	13	2	e	IOException
    //   89	19	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   0	41	44	java/io/FileNotFoundException
    //   0	41	47	java/io/IOException
    //   0	69	89	finally
  }
  
  void mapNew()
  {
    abortOperation(true);
    if (!saveChangesCheck()) {
      return;
    }
    this.contComboEditor.stopCellEditing();
    
    selectTerritoryNone();
    this.map.clear();
    setMapTitle();
    setMap(this.map);
    this.mapDisplay.repaint();
    this.props.put("LastMap", "");
    openMapPropertiesDialog();
  }
  
  void mapPromptAndLoad(String path)
  {
    abortOperation(true);
    try
    {
      if (!saveChangesCheck()) {
        return;
      }
      if (path == null)
      {
        JFileChooser fc = getMapFc();
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == 0) {
          path = fc.getSelectedFile().getAbsolutePath();
        } else {
          return;
        }
      }
      try
      {
        mapLoad(path);
      }
      catch (FileNotFoundException e)
      {
        errorMessage("Map file \"" + path + "\" was not found");
      }
      return;
    }
    catch (Exception e)
    {
      errorMessage("An error occurred while loading the map: " + e);
    }
  }
  
  boolean mapSave()
  {
    abortOperation(true);
    try
    {
      if (this.map.getMapFilePath() != null)
      {
        if (!saveImageCheck()) {
          return false;
        }
        this.map.save();
        return true;
      }
      if (!saveImageCheck()) {
        return false;
      }
      return mapSaveAs();
    }
    catch (Exception ex)
    {
      errorMessage("An error occurred while saving the map: " + ex);
    }
    return false;
  }
  
  boolean mapSaveAs()
  {
    abortOperation(true);
    boolean saved = false;
    try
    {
      JFileChooser fc = getMapFc();
      if (this.map.getMapFilePath() != null) {
        fc.setSelectedFile(new File(this.map.getMapFilePath()));
      }
      int returnVal = fc.showSaveDialog(this);
      if ((returnVal == 0) && 
        (saveImageCheck()))
      {
        File path = fc.getSelectedFile();
        File dirf = path.getParentFile();
        String dir = dirf.getAbsolutePath() + File.separator;
        String fn = path.getName();
        if (fn.indexOf('.') == -1) {
          fn = fn + ".map";
        }
        this.map.save(dir + fn);
        saved = true;
      }
    }
    catch (Exception ex)
    {
      errorMessage("An error occurred while saving the map: " + ex);
    }
    setMapTitle();
    return saved;
  }
  
  public void mapSelectImage()
  {
    JFileChooser fc = getMapImageFc();
    int returnVal = fc.showOpenDialog(this);
    if (returnVal == 0) {
      mapSelectImage(fc.getSelectedFile());
    }
  }
  
  void mapSelectImage(File newImage)
  {
    try
    {
      this.map.setImageFilePath(newImage.getAbsolutePath());
      reloadMapImage();
    }
    catch (Exception ex)
    {
      errorMessage("An error occurred while loading the map image: " + ex);
    }
  }
  
  public File mapSelectImageNew()
  {
    JFileChooser fc = getMapImageFc();
    File dir = this.map.getMapDirectory();
    if (dir != null) {
      fc.setCurrentDirectory(dir);
    }
    int returnVal = fc.showSaveDialog(this);
    if (returnVal == 0)
    {
      File f = fc.getSelectedFile();
      if ((f != null) && (f.getName() != null) && 
        (!f.getName().contains("."))) {
        f = new File(f.getPath() + ".bmp");
      }
      return f;
    }
    return null;
  }
  
  public void mouseClicked(MouseEvent e)
  {
    if ((e.getSource() == this.mapDisplay) && (!mouseControlsSupressed())) {
      if (((!this.addingTerritory) && (isControlAdd(e, false))) || (
        (this.addingTerritory) && (isControlSelect(e, false))))
      {
        setAddTerritoryMode(false);
        addTerritory(e.getX(), e.getY());
      }
      else if (this.addingTerritory)
      {
        abortOperation(true);
      }
    }
  }
  
  boolean mouseControlsSupressed()
  {
    return (this.mapDisplay.supressMouseControls()) || (!this.actionMouseControls.isEnabled());
  }
  
  public void mouseDragged(MouseEvent e)
  {
    if ((e.getSource() == this.mapDisplay) && (!mouseControlsSupressed()))
    {
      if (this.dragOp == DragOperation.DRAG_OFF)
      {
        Territory target = this.map.findClosestTerritory(e.getX(), e.getY(), 10);
        if (target != null) {
          if (isControlMoveAll(e, true))
          {
            this.mapDisplay.selectTerritoryNone();
            setDragOp(DragOperation.DRAG_MOVEALL, target);
          }
          else if (isControlScaleAll(e, true))
          {
            this.mapDisplay.selectTerritoryNone();
            setDragOp(DragOperation.DRAG_RESIZE, target);
            this.mapDisplay.setCursor(new Cursor(13));
          }
          else if (isControlMoveOne(e, true))
          {
            this.mapDisplay.selectTerritoryNone();
            setDragOp(DragOperation.DRAG_MOVE, target);
          }
        }
      }
      if (this.dragOp != DragOperation.DRAG_OFF) {
        if (this.dragOp == DragOperation.DRAG_MOVE)
        {
          moveTerritory(this.dragVictim, e.getX(), e.getY());
        }
        else if (this.dragOp == DragOperation.DRAG_MOVEALL)
        {
          moveAllTerritories(this.dragVictim, e.getX(), e.getY(), this.props
            .getTrueFalseProperty("WrapMoveTerritories", false));
        }
        else if (this.dragOp == DragOperation.DRAG_RESIZE)
        {
          this.lastDragMouseMoveEvent = e;
          this.mapDisplay.repaint();
        }
      }
    }
  }
  
  public void mouseEntered(MouseEvent e) {}
  
  public void mouseExited(MouseEvent e) {}
  
  public void mouseMoved(MouseEvent e)
  {
    if (e.isControlDown())
    {
      this.justIdentifiedColor = true;
      Color c = this.mapDisplay.getImagePixel(e.getX(), e.getY());
      if (c != null)
      {
        updateLocField(e.getX(), e.getY());
        updateInfoField("Color at mouse location: " + this.mapDisplay.colorString(c), Color.BLACK, true);
        return;
      }
    }
    if (this.justIdentifiedColor)
    {
      updateInfoField();
      updateLocField();
      this.justIdentifiedColor = false;
    }
  }
  
  public void mousePressed(MouseEvent e)
  {
    if ((e.getSource() == this.mapDisplay) && (!mouseControlsSupressed()))
    {
      if (this.addingTerritory) {
        return;
      }
      boolean linkToggle = isControlLinkToggle(e, false);
      boolean linkOneWay = isControlLinkOneWay(e, false);
      if ((linkToggle) || (linkOneWay))
      {
        Territory ter = this.map.findClosestTerritory(e.getX(), e.getY(), 25);
        if (linkOneWay) {
          linkTerritoriesOneWay(this.mapDisplay.getSingleSelectedTerritory(), ter);
        } else {
          linkTerritoriesToggle(this.mapDisplay.getSingleSelectedTerritory(), ter);
        }
      }
      else if (isControlSelectToggle(e, false))
      {
        Territory ter = this.map.findClosestTerritory(e.getX(), e.getY(), 50);
        if (ter != null) {
          selectTerritory(ter, true, false, SelectionOperation.TOGGLE);
        }
      }
      else if (isControlSelect(e, false))
      {
        selectTerritory(this.map.findClosestTerritory(e.getX(), e.getY(), 50));
      }
      else if (isControlSetBridgeColor(e, false))
      {
        Color c = this.mapDisplay.getImagePixel(e.getX(), e.getY());
        if (c != null)
        {
          setBridgeColor(c);
          updateInfoField("Bridge color set to: " + this.mapDisplay.colorString(c), null, true);
        }
      }
      this.mapDisplay.requestFocus();
    }
    else if (e.getSource() == this.contTable)
    {
      if (e.isShiftDown()) {
        moveSelectedToContinent(getSelectedContinent());
      } else if (e.isControlDown()) {
        showContinentInfo(getSelectedContinent());
      }
    }
  }
  
  public void mouseReleased(MouseEvent e)
  {
    if ((e.getSource() == this.mapDisplay) && (!mouseControlsSupressed()) && 
      (this.dragOp != DragOperation.DRAG_OFF))
    {
      Territory ter = this.dragVictim;
      if ((this.dragOp == DragOperation.DRAG_RESIZE) && (!SwingUtilities.isRightMouseButton(e))) {
        scaleAllTerritories(this.dragVictim, e.getX(), e.getY());
      }
      abortDragOperation();
      selectTerritory(ter);
    }
  }
  
  void moveAllTerritories(Territory origin, int x, int y, boolean wrap)
  {
    if (origin == null) {
      return;
    }
    int dx = x - origin.getCenterX();
    int dy = y - origin.getCenterY();
    if (!wrap) {
      for (Territory ter : this.map.territories) {
        if ((!Calc.contains(ter.getCenterX() + dx, 0, this.mapDisplay.getImageWidth())) || 
          (!Calc.contains(ter.getCenterY() + dy, 0, this.mapDisplay.getImageHeight()))) {
          return;
        }
      }
    }
    for (Territory ter : this.map.territories) {
      ter.setCenter((ter.getCenterX() + dx + this.mapDisplay.getImageWidth()) % this.mapDisplay.getImageWidth(), 
        (ter.getCenterY() + dy + this.mapDisplay.getImageHeight()) % this.mapDisplay.getImageHeight());
    }
    this.map.dirty = true;
    this.mapDisplay.repaint();
    if (this.mapDisplay.getSingleSelectedTerritory() != null) {
      updateLocField(this.mapDisplay.getSingleSelectedTerritory().getCenterX(), 
        this.mapDisplay.getSingleSelectedTerritory().getCenterY());
    }
  }
  
  void moveSelectedToContinent(Continent cont)
  {
    Collection<Territory> ters = this.mapDisplay.getSelectedTerritories();
    
    Iterator<Territory> iter = ters.iterator();
    while (iter.hasNext())
    {
      Territory ter = (Territory)iter.next();
      if (ter.cont == cont) {
        iter.remove();
      }
    }
    if (ters.isEmpty()) {
      return;
    }
    if (!confirm("Move the selected " + ters.size() + " territories to continent " + cont.name, 
      "Confirm continent change")) {
      return;
    }
    for (Territory ter : ters) {
      ter.setContinent(cont);
    }
    updateTerritoryData();
  }
  
  void moveTerritory(Territory ter, int x, int y)
  {
    if (ter == null) {
      return;
    }
    if ((!Calc.contains(x, 0, this.mapDisplay.getImageWidth())) || 
      (!Calc.contains(y, 0, this.mapDisplay.getImageHeight()))) {
      return;
    }
    this.map.dirty = true;
    ter.setCenter(x, y);
    this.mapDisplay.repaint();
    updateLocField(x, y);
  }
  
  Continent newContinentPrompt()
  {
    JPanel pan = new JPanel(new GridLayout(2, 2));
    JTextField nf = new JTextField("");
    JTextField bf = new JTextField("3");
    pan.add(new JLabel("New continent name: "));
    pan.add(nf);
    pan.add(new JLabel("New continent bonus: "));
    pan.add(bf);
    String cname;
    int cbonus;
    for (;;)
    {
      NewContinentDialog newContDialog = new NewContinentDialog(this);
      newContDialog.initialize();
      ComponentUtil.center(newContDialog, this.centerComp);
      newContDialog.setVisible(true);
      if (newContDialog.isCanceled()) {
        return null;
      }
      cname = newContDialog.getName();
      cbonus = newContDialog.getBonus();
      if ((cname == null) || (cname.isEmpty()))
      {
        warnMessage("Continent name may not be blank");
      }
      else
      {
        if (this.map.findContinent(cname) == null) {
          break;
        }
        warnMessage("Continent name \"" + cname + "\" is already in use");
      }
    }
    return new Continent(cname, cbonus);
  }
  
  boolean openAnalyzeOptionsDialog()
  {
    if (this.abOptions == null) {
      this.abOptions = new AnalyzeBordersOptions(this.props);
    }
    if (this.abOptionsDialog == null)
    {
      this.abOptionsDialog = new AnalyzeBordersOptionsDialog(this);
      this.abOptionsDialog.initialize();
    }
    this.abOptionsDialog.setBorderAnalyzerOptions(this.abOptions);
    ComponentUtil.center(this.abOptionsDialog, this.centerComp);
    this.abOptionsDialog.setVisible(true);
    if (this.abOptionsDialog.isCanceled()) {
      return false;
    }
    this.abOptions = this.abOptionsDialog.getBorderAnalyzerOptions();
    return true;
  }
  
  private void openMapPropertiesDialog()
  {
    String path = this.map.getImageFilePath();
    if (this.mapPropsDialog == null)
    {
      this.mapPropsDialog = new MapPropertiesDialog(this);
      this.mapPropsDialog.initialize();
    }
    this.mapPropsDialog.setMap(this.map);
    this.mapPropsDialog.pack();
    ComponentUtil.center(this.mapPropsDialog, this.centerComp);
    
    this.mapPropsDialog.setVisible(true);
    if ((!this.mapPropsDialog.isCanceled()) && 
      (!StringUtil.equal(path, this.map.getImageFilePath(), true))) {
      reloadMapImage();
    }
  }
  
  void optionsReadDrawLinks()
  {
    this.menuOptionsDrawLinks.setSelected(this.props.getBooleanProperty("TerritoryLinksVisible", true));
    
    String opts = this.props.getProperty("TerritoryLinksExisting");
    MapDisplay.TerritoryLinkOptions optExisting;
    try
    {
      optExisting = MapDisplay.TerritoryLinkOptions.valueOf(opts);
    }
    catch (Exception e)
    {
      MapDisplay.TerritoryLinkOptions optExisting;
      optExisting = MapDisplay.TerritoryLinkOptions.NONE;
    }
    this.menuOptionsDrawLinksNone.setSelected(optExisting == MapDisplay.TerritoryLinkOptions.NONE);
    this.menuOptionsDrawLinksSelected.setSelected(optExisting == MapDisplay.TerritoryLinkOptions.SELECTED);
    this.menuOptionsDrawLinksAll.setSelected(optExisting == MapDisplay.TerritoryLinkOptions.ALL);
    
    opts = this.props.getProperty("TerritoryLinksRecommended");
    MapDisplay.TerritoryLinkOptions optRecommended;
    try
    {
      optRecommended = MapDisplay.TerritoryLinkOptions.valueOf(opts);
    }
    catch (Exception e)
    {
      MapDisplay.TerritoryLinkOptions optRecommended;
      optRecommended = MapDisplay.TerritoryLinkOptions.ALL;
    }
    this.menuOptionsDrawRecommendedLinksNone.setSelected(optRecommended == MapDisplay.TerritoryLinkOptions.NONE);
    this.menuOptionsDrawRecommendedLinksSelected.setSelected(optRecommended == MapDisplay.TerritoryLinkOptions.SELECTED);
    this.menuOptionsDrawRecommendedLinksAll.setSelected(optRecommended == MapDisplay.TerritoryLinkOptions.ALL);
    
    boolean drawLinks = this.props.getBooleanProperty("TerritoryLinksVisible", true);
    this.menuOptionsDrawLinks.setSelected(drawLinks);
    this.actionToggleShowTerritoryLinks
      .putValue("SmallIcon", drawLinks ? this.buttonLinksOn : this.buttonLinksOff);
    
    this.mapDisplay.setOptionDrawLinks(this.menuOptionsDrawLinks.isSelected(), optExisting, optRecommended);
  }
  
  void optionsReadTerritoryColor()
  {
    String opts = this.props.getProperty("TerritoryColoring");
    MapDisplay.TerritoryColorOptions opt;
    try
    {
      opt = MapDisplay.TerritoryColorOptions.valueOf(opts);
    }
    catch (Exception e)
    {
      MapDisplay.TerritoryColorOptions opt;
      opt = MapDisplay.TerritoryColorOptions.BORDERING;
    }
    this.menuOptionsColorTerritoriesNone.setSelected(opt == MapDisplay.TerritoryColorOptions.NONE);
    this.menuOptionsColorTerritoriesSelected.setSelected(opt == MapDisplay.TerritoryColorOptions.SELECTED);
    this.menuOptionsColorTerritoriesBorders.setSelected(opt == MapDisplay.TerritoryColorOptions.BORDERING);
    if (opt != this.mapDisplay.getOptionColorTerritories()) {
      this.mapDisplay.setOptionColorTerritories(opt);
    }
  }
  
  void optionsSetDrawLinks()
  {
    MapDisplay.TerritoryLinkOptions optExisting;
    MapDisplay.TerritoryLinkOptions optExisting;
    if (this.menuOptionsDrawLinksNone.isSelected())
    {
      optExisting = MapDisplay.TerritoryLinkOptions.NONE;
    }
    else
    {
      MapDisplay.TerritoryLinkOptions optExisting;
      if (this.menuOptionsDrawLinksSelected.isSelected()) {
        optExisting = MapDisplay.TerritoryLinkOptions.SELECTED;
      } else {
        optExisting = MapDisplay.TerritoryLinkOptions.ALL;
      }
    }
    MapDisplay.TerritoryLinkOptions optRecommended;
    MapDisplay.TerritoryLinkOptions optRecommended;
    if (this.menuOptionsDrawRecommendedLinksNone.isSelected())
    {
      optRecommended = MapDisplay.TerritoryLinkOptions.NONE;
    }
    else
    {
      MapDisplay.TerritoryLinkOptions optRecommended;
      if (this.menuOptionsDrawRecommendedLinksSelected.isSelected()) {
        optRecommended = MapDisplay.TerritoryLinkOptions.SELECTED;
      } else {
        optRecommended = MapDisplay.TerritoryLinkOptions.ALL;
      }
    }
    boolean drawLinks = this.menuOptionsDrawLinks.isSelected();
    this.mapDisplay.setOptionDrawLinks(drawLinks, optExisting, optRecommended);
    this.actionToggleShowTerritoryLinks
      .putValue("SmallIcon", drawLinks ? this.buttonLinksOn : this.buttonLinksOff);
  }
  
  void optionsSetTerritoryColor()
  {
    MapDisplay.TerritoryColorOptions opt;
    MapDisplay.TerritoryColorOptions opt;
    if (this.menuOptionsColorTerritoriesNone.isSelected())
    {
      opt = MapDisplay.TerritoryColorOptions.NONE;
    }
    else
    {
      MapDisplay.TerritoryColorOptions opt;
      if (this.menuOptionsColorTerritoriesSelected.isSelected()) {
        opt = MapDisplay.TerritoryColorOptions.SELECTED;
      } else {
        opt = MapDisplay.TerritoryColorOptions.BORDERING;
      }
    }
    this.mapDisplay.setOptionColorTerritories(opt);
  }
  
  private Level parseLoggerLevel(String levelString)
  {
    try
    {
      return Level.parse(levelString.toUpperCase());
    }
    catch (Exception e)
    {
      logger.warning("Console log level string not understood: " + levelString);
    }
    return null;
  }
  
  public void popupMessage(Object info, String title)
  {
    popupMessage(info, title, 1);
  }
  
  public void popupMessage(Object info, String title, int type)
  {
    if (this.initCompleted) {
      JOptionPane.showMessageDialog(this.centerComp, info, title == null ? "Conquest Map Maker" : title, type);
    } else {
      System.out.println((title == null ? "Conquest Map Maker" : title) + ": " + info);
    }
    this.mapDisplay.requestFocus();
  }
  
  private void readyActions(Collection<Action> actions, boolean enabled)
  {
    for (Action a : actions) {
      if ((a == this.actionEditMapImage) && (enabled)) {
        this.actionEditMapImage.setEnabled(this.imageEditProc == null);
      } else {
        a.setEnabled(enabled);
      }
    }
  }
  
  private void registerKeyAction(JComponent comp, String keyChar, Action action, boolean window)
  {
    comp.getInputMap(window ? 2 : 0).put(
      KeyStroke.getKeyStroke(keyChar), action.getValue("Name"));
    
    comp.getActionMap().put(action.getValue("Name"), action);
  }
  
  void reloadMapImage()
  {
    boolean success = this.mapDisplay.loadImage();
    this.mapDisplay.repaint();
    setMapTitle();
    if (!success) {
      errorMessage("Map file could not be loaded:\n\n" + this.map.getImageFilePath());
    }
  }
  
  boolean saveChangesCheck()
  {
    if ((this.map == null) || (!this.map.dirty)) {
      return true;
    }
    int opt = JOptionPane.showOptionDialog(this.centerComp, "Do you want to save the changes you made to " + 
      this.map.getMapName() + "?", "Conquest Map Maker", 1, 
      2, null, null, null);
    if (opt == 1) {
      return true;
    }
    if (opt == 2) {
      return false;
    }
    return mapSave();
  }
  
  boolean saveImageCheck()
  {
    if (this.mapDisplay.saveImageIfAltered()) {
      return true;
    }
    return confirm(
      "The resized map image could not be saved. Do you want to save changes to the map anyway?", 
      "Confirm save");
  }
  
  void saveProperties()
  {
    this.props.setProperty("Version", "2.0.0");
    this.props.setProperty("WindowState", getExtendedState());
    
    this.props.setProperty("WindowLocationX", getLocation().x);
    this.props.setProperty("WindowLocationY", getLocation().y);
    this.props.setProperty("WindowSizeWidth", getSize().width);
    this.props.setProperty("WindowSizeHeight", getSize().height);
    
    this.props.setProperty("WindowDivide", this.splitPane.getDividerLocation());
    this.props.setProperty("TableDivide", this.splitPane2.getDividerLocation());
    
    this.props.setProperty("TerritoryColoring", this.mapDisplay.getOptionColorTerritories());
    
    this.props.setProperty("TerritoryLinksExisting", this.mapDisplay.getOptionDrawExistingLinks());
    this.props.setProperty("TerritoryLinksRecommended", this.mapDisplay.getOptionDrawRecommendedLinks());
    this.props.setProperty("TerritoryLinksVisible", this.mapDisplay.getOptionDrawLinks());
    if (this.consoleLogHandler != null) {
      this.props.setProperty("LogConsoleLevel", this.consoleLogHandler.getLevel().toString());
    }
    if (this.fileLogHandler != null) {
      this.props.setProperty("LogFileLevel", this.fileLogHandler.getLevel().toString());
    }
    this.props.setProperty("AnalyzeOnLoad", this.menuOptionsAnalyzeOnLoad.isSelected());
    if (this.abOptions != null) {
      this.abOptions.setProperties(this.props);
    }
    this.mapDisplay.saveProperties(this.props);
    try
    {
      FileOutputStream out = new FileOutputStream(this.propsFilePath);
      this.props.store(out, "ConquestMapMaker configuration settings");
      out.close();
    }
    catch (IOException localIOException) {}
  }
  
  void scaleAllTerritories(Territory origin, int x, int y)
  {
    if ((origin != null) && (origin.getCenterX() != 0) && (origin.getCenterY() != 0))
    {
      float sx = x / origin.getCenterXFloat();
      float sy = y / origin.getCenterYFloat();
      this.map.scaleAllTerritories(sx, sy);
    }
  }
  
  void scaleMap()
  {
    ResizeMapDialog d = new ResizeMapDialog(this, this.mapDisplay.getImageWidth(), this.mapDisplay.getImageHeight());
    d.initialize();
    ComponentUtil.center(d, this.centerComp);
    d.setVisible(true);
    if (!d.isCanceled())
    {
      int w = d.getNewWidth();
      int h = d.getNewHeight();
      if ((w != this.mapDisplay.getImageWidth()) || (h != this.mapDisplay.getImageHeight()))
      {
        abortOperation(true);
        this.mapDisplay.scaleMap(w, h);
      }
    }
  }
  
  void scrollTableToContinent(String name)
  {
    int row = getContinentTableRow(name);
    if (row != -1)
    {
      this.contTable.getSelectionModel().setSelectionInterval(row, row);
      
      this.contTable.scrollRectToVisible(this.contTable.getCellRect(row, 0, true));
    }
  }
  
  void scrollTableToTerritory(String terName)
  {
    int row = getTerritoryTableRow(terName);
    if ((row != -1) && (this.terTable.isRowSelected(row))) {
      this.terTable.scrollRectToVisible(this.terTable.getCellRect(row, 0, true));
    }
  }
  
  boolean selectImageEditor()
  {
    JFileChooser fc = getMapImageEditorFc();
    int returnVal = fc.showOpenDialog(this);
    if (returnVal == 0)
    {
      this.props.setProperty("ImageEditorPath", fc.getSelectedFile().getAbsolutePath());
      return true;
    }
    return false;
  }
  
  void selectTerritories(Collection<Territory> ters)
  {
    if ((ters == null) || (ters.isEmpty()))
    {
      selectTerritoryNone();
      return;
    }
    abortOperation(false);
    
    this.mapDisplay.selectTerritories(ters);
    this.mapDisplay.repaint();
    
    updateContinentTableSelection();
    
    updateLocField();
    updateInfoField();
    
    this.terTable.requestFocus();
  }
  
  void selectTerritory(Territory ter)
  {
    selectTerritory(ter, true, false, SelectionOperation.SET);
  }
  
  void selectTerritory(Territory ter, boolean selectInTable, boolean selectForEditing, SelectionOperation op)
  {
    if (op == null) {
      return;
    }
    abortOperation(true);
    
    this.mapDisplay.selectTerritory(ter, op);
    this.mapDisplay.repaint();
    
    updateContinentTableSelection();
    if (ter != null)
    {
      if (selectInTable) {
        selectTerritoryInTable(ter, op, true);
      }
      if (selectForEditing) {
        editTerritoryCell(ter, 0);
      }
    }
    else
    {
      this.terTable.getSelectionModel().clearSelection();
      this.mapDisplay.requestFocus();
    }
    updateLocField();
    updateInfoField();
    if (ter != null) {
      this.terTable.requestFocus();
    }
  }
  
  void selectTerritoryInTable(Territory ter, SelectionOperation op, boolean scrollToSelection)
  {
    this.disableValueChanged.get();
    int row = getTerritoryTableRow(ter.name);
    if (row != -1)
    {
      if (op == SelectionOperation.SET) {
        this.terTable.getSelectionModel().setSelectionInterval(row, row);
      } else if ((op == SelectionOperation.ADD) || (!this.terTable.isRowSelected(row))) {
        this.terTable.getSelectionModel().addSelectionInterval(row, row);
      } else {
        this.terTable.getSelectionModel().removeSelectionInterval(row, row);
      }
      if (scrollToSelection) {
        scrollTableToTerritory(ter.name);
      }
    }
    this.disableValueChanged.release();
  }
  
  void selectTerritoryNone()
  {
    selectTerritory(null, true, false, SelectionOperation.SET);
  }
  
  void setAddTerritoryMode(boolean state)
  {
    if (this.addingTerritory != state)
    {
      this.addingTerritory = state;
      if (this.addingTerritory)
      {
        this.mapDisplay.setCursor(new Cursor(1));
        updateInfoField();
      }
      else
      {
        this.mapDisplay.setCursor(new Cursor(0));
      }
    }
  }
  
  void setBridgeColor(Color c)
  {
    if (this.abOptions == null) {
      this.abOptions = new AnalyzeBordersOptions();
    }
    this.abOptions.bridgeColors = new int[] { c.getRGB() };
  }
  
  private void setConsoleLogLevel(Level level)
  {
    if (level != null)
    {
      if (this.consoleLogHandler == null)
      {
        this.consoleLogHandler = new ConsoleHandler();
        this.consoleLogHandler.setFormatter(new ConsoleFormatter());
        logger.addHandler(this.consoleLogHandler);
      }
      this.consoleLogHandler.setLevel(level);
      if ((logger.getLevel() == null) || (level.intValue() < logger.getLevel().intValue())) {
        logger.setLevel(level);
      }
      logger.info("Console log level set to " + level);
    }
  }
  
  private void setDragOp(DragOperation dragOp, Territory ter)
  {
    this.dragOp = dragOp;
    this.dragVictim = ter;
  }
  
  private void setFileLogLevel(Level level)
  {
    String logFileName = "ConquestMapMaker.log";
    if (level != null) {
      try
      {
        if (this.fileLogHandler == null)
        {
          this.fileLogHandler = new FileHandler(logFileName);
          this.fileLogHandler.setFormatter(new SimpleFormatter());
          logger.addHandler(this.fileLogHandler);
        }
        this.fileLogHandler.setLevel(level);
        if ((logger.getLevel() == null) || (level.intValue() < logger.getLevel().intValue())) {
          logger.setLevel(level);
        }
        logger.info("File log level set to " + level);
      }
      catch (IOException e)
      {
        logger.warning("Unable to create log file: " + logFileName);
      }
    }
  }
  
  void setMap(ConquestMap map)
  {
    this.map = map;
    this.mapDisplay.setMap(map);
    setMapTitle();
    this.mapDisplay.revalidate();
    this.terTableModel.setMap(map);
    this.terTable.setRowSorter(new TableRowSorter(this.terTableModel));
    this.contTableModel.setMap(map);
    this.contTable.setRowSorter(new TableRowSorter(this.contTableModel));
    updateContinentData();
    this.terTable.revalidate();
    this.splitPane.repaint();
  }
  
  void setMapTitle()
  {
    if ((this.map == null) || (this.map.getMapFilePath() == null))
    {
      setTitle("Conquest Map Maker - Untitled Map");
      String s = " Untitled Map ";
      if (this.map.getImageFilePath() != null) {
        s = s + "(" + this.map.getImageFileName() + ") ";
      }
      this.mapTitleBorder.setTitle(s);
    }
    else
    {
      setTitle("Conquest Map Maker - " + new File(this.map.getMapFilePath()).getName());
      
      String s = " " + new File(this.map.getMapFilePath()).getName() + " ";
      if (this.map.getImageFilePath() != null) {
        s = s + "(" + this.map.getImageFileName() + ") ";
      }
      this.mapTitleBorder.setTitle(s);
    }
  }
  
  protected void setPlaf(String plafName)
  {
    try
    {
      if (plafName == null) {
        return;
      }
      UIManager.setLookAndFeel(plafName);
      if (this.initCompleted)
      {
        SwingUtilities.updateComponentTreeUI(this);
        pack();
      }
      this.props.setProperty("LookAndFeel", plafName);
    }
    catch (Exception e)
    {
      errorMessage("Unable to set look and feel \"" + plafName + "\": " + e);
    }
  }
  
  void showContinentInfo(Continent cont)
  {
    if (cont == null) {
      return;
    }
    HashSet<Territory> ters = this.map.getContinentTerritories(cont);
    
    HashSet<Territory> externalBorders = new HashSet();
    HashSet<Territory> internalBorders = new HashSet();
    Iterator localIterator2;
    label134:
    for (Iterator localIterator1 = this.map.territories.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      Territory ter = (Territory)localIterator1.next();
      if ((ter.cont == cont) || (ter.links == null)) {
        break label134;
      }
      localIterator2 = ter.links.iterator(); continue;Territory ter2 = (Territory)localIterator2.next();
      if (ter2.cont == cont)
      {
        externalBorders.add(ter);
        internalBorders.add(ter2);
      }
    }
    CompactGridPanel pan = new CompactGridPanel();
    pan.add(new JLabel("Continent name: ", 4));
    pan.add(new JLabel(cont.name));
    pan.add(new JLabel("Continent bonus: ", 4));
    pan.add(new JLabel(cont.bonus));
    pan.add(new JLabel("Territories: ", 4));
    pan.add(new JLabel(ters.size()));
    pan.add(new JLabel("Internal borders: ", 4));
    pan.add(new JLabel(internalBorders.size()));
    pan.add(new JLabel("External borders: ", 4));
    pan.add(new JLabel(externalBorders.size()));
    pan.finishGrid(0, 2, 3);
    
    infoMessage(pan, "Continent information");
  }
  
  void showHelp(String subject)
  {
    if (subject != null) {
      this.helpViewer.selectByName(subject);
    }
    HelpDialog helpDialog = new HelpDialog(this, this.helpViewer);
    helpDialog.setVisible(true);
  }
  
  void showHelpAbout()
  {
    showHelp("About Conquest Map Maker");
  }
  
  void shutDown()
  {
    saveProperties();
    System.exit(0);
  }
  
  public void tableChanged(TableModelEvent e)
  {
    if (e.getSource() == this.contTableModel) {
      updateTerritoryData();
    }
  }
  
  void translateAllTerritories(int xx, int yy)
  {
    if (this.map.territories.isEmpty()) {
      return;
    }
    this.mapDisplay.selectTerritoryNone();
    Territory ter = (Territory)this.map.territories.get(0);
    moveAllTerritories(ter, ter.getCenterX() + xx, ter.getCenterY() + yy, this.props
      .getTrueFalseProperty("WrapMoveTerritories", false));
    this.mapDisplay.requestFocus();
  }
  
  void translateCurrentTerritory(int xx, int yy)
  {
    Collection<Territory> ters = this.mapDisplay.getSelectedTerritories();
    if (!ters.isEmpty())
    {
      logger.finer("Translating newly selected territories: " + ters);
      this.translatingTerritories = ters;
      this.mapDisplay.selectTerritoryNone();
    }
    if (this.translatingTerritories != null)
    {
      logger.fine("Translating selected territories: " + this.translatingTerritories);
      for (Territory ter : this.translatingTerritories) {
        moveTerritory(ter, ter.getCenterX() + xx, ter.getCenterY() + yy);
      }
      this.mapDisplay.repaint();
      this.mapDisplay.requestFocus();
    }
  }
  
  void translateTerritory(int xx, int yy, boolean moveAll)
  {
    if (moveAll) {
      translateAllTerritories(xx, yy);
    } else {
      translateCurrentTerritory(xx, yy);
    }
  }
  
  void uncolorAndMove(Territory ter, int x, int y)
  {
    this.mapDisplay.uncolorTerritory(ter);
    ter.setCenter(x, y);
  }
  
  void updateContinentData()
  {
    this.contTableModel.dataChanged();
    
    SortedSet<String> ss = new TreeSet();
    for (Continent c : this.map.continents) {
      ss.add(c.toString());
    }
    this.continentComboBox.removeAllItems();
    for (String s : ss) {
      this.continentComboBox.addItem(s);
    }
    this.continentComboBox.addItem("<new continent>");
  }
  
  void updateContinentTableSelection()
  {
    this.disableValueChanged.get();
    
    Territory ter = this.mapDisplay.getSingleSelectedTerritory();
    if (ter == null) {
      this.contTable.clearSelection();
    } else {
      scrollTableToContinent(ter.cont.getName());
    }
    this.disableValueChanged.release();
  }
  
  void updateInfoField()
  {
    int sts = this.mapDisplay.getSelectedTerritoriesSize();
    Territory ter = sts == 1 ? this.mapDisplay.getSingleSelectedTerritory() : null;
    if (this.infoFg != null) {
      this.infoField.setForeground(this.infoFg);
    }
    this.infoField.setHorizontalAlignment(0);
    if (this.addingTerritory) {
      updateInfoFieldBold("Click on a map location for the new territory");
    } else if (sts > 1) {
      this.infoField.setText(sts + " territories selected");
    } else if (ter != null) {
      this.infoField.setText("territory \"" + ter.name + "\", continent \"" + ter.cont.name + "\"");
    } else {
      this.infoField.setText("No territories selected");
    }
  }
  
  void updateInfoField(String msg, Color c, boolean center)
  {
    if (center) {
      this.infoField.setHorizontalAlignment(0);
    } else {
      this.infoField.setHorizontalAlignment(10);
    }
    if (msg == null)
    {
      this.infoField.setText("");
    }
    else
    {
      if ((this.infoFg == null) && (c != null)) {
        this.infoFg = this.infoField.getForeground();
      }
      if (c != null) {
        this.infoField.setForeground(c);
      } else if (this.infoFg != null) {
        this.infoField.setForeground(this.infoFg);
      }
      this.infoField.setText(msg);
    }
  }
  
  void updateInfoFieldBold(String msg)
  {
    updateInfoField(msg, new Color(160, 0, 40), true);
  }
  
  void updateLocField()
  {
    Territory ter = this.mapDisplay.getSingleSelectedTerritory();
    if (ter == null) {
      this.locField.setText("");
    } else {
      updateLocField(ter.getCenterX(), ter.getCenterY());
    }
  }
  
  void updateLocField(int x, int y)
  {
    this.locField.setText("  (" + x + ", " + y + ")  ");
  }
  
  void updateLocField(Point p)
  {
    if (p == null) {
      this.locField.setText("");
    } else {
      updateLocField(p.x, p.y);
    }
  }
  
  void updateTerritoryData()
  {
    this.terTableModel.dataChanged();
  }
  
  public void valueChanged(ListSelectionEvent e)
  {
    if (e.getValueIsAdjusting()) {
      return;
    }
    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
    if (lsm == this.terTable.getSelectionModel())
    {
      if (!this.disableValueChanged.isHeld()) {
        if (lsm.isSelectionEmpty())
        {
          selectTerritoryNone();
        }
        else
        {
          this.disableValueChanged.get();
          ArrayList<Integer> al = getSelectedModelIndices(lsm, this.terTable);
          ArrayList<Territory> ters = new ArrayList();
          for (Iterator localIterator = al.iterator(); localIterator.hasNext();)
          {
            int i = ((Integer)localIterator.next()).intValue();
            ters.add((Territory)this.map.territories.get(i));
          }
          selectTerritories(ters);
          this.disableValueChanged.release();
        }
      }
    }
    else if (lsm == this.contTable.getSelectionModel()) {
      if (!this.disableValueChanged.isHeld()) {
        if (lsm.isSelectionEmpty())
        {
          contSelect(null);
        }
        else
        {
          int selrow = lsm.getMinSelectionIndex();
          int selIndex = this.contTable.convertRowIndexToModel(selrow);
          contSelect((Continent)this.map.continents.get(selIndex));
          
          this.disableValueChanged.get();
          lsm.setSelectionInterval(selrow, selrow);
          this.disableValueChanged.release();
        }
      }
    }
  }
  
  void verifyMap(boolean messageIfOk)
  {
    updateInfoField("Checking map for problems", Color.BLACK, true);
    ArrayList<String> problems = this.map.validityCheck();
    if (problems != null)
    {
      StringBuffer sb = new StringBuffer("The following problems were found with this map:   \n\n");
      for (String s : problems) {
        sb.append("     - ").append(s).append("\n");
      }
      sb.append("\n");
      warnMessage(sb.toString());
    }
    else if (messageIfOk)
    {
      infoMessage("No problems found.");
    }
    updateInfoField();
  }
  
  public void warnMessage(Object info)
  {
    popupMessage(info, null, 2);
  }
  
  public void windowActivated(WindowEvent e) {}
  
  public void windowClosed(WindowEvent e) {}
  
  public void windowClosing(WindowEvent e)
  {
    if (saveChangesCheck()) {
      shutDown();
    }
  }
  
  public void windowDeactivated(WindowEvent e) {}
  
  public void windowDeiconified(WindowEvent e) {}
  
  public void windowIconified(WindowEvent e) {}
  
  public void windowOpened(WindowEvent e) {}


@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragEnter(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragOver(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dropActionChanged(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragExit(DropTargetEvent dte) {
	// TODO Auto-generated method stub
	
}
@Override
public void drop(DropTargetDropEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragEnter(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragOver(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dropActionChanged(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragExit(DropTargetEvent dte) {
	// TODO Auto-generated method stub
	
}
@Override
public void drop(DropTargetDropEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragEnter(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragOver(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dropActionChanged(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragExit(DropTargetEvent dte) {
	// TODO Auto-generated method stub
	
}
@Override
public void drop(DropTargetDropEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragEnter(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragOver(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dropActionChanged(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragExit(DropTargetEvent dte) {
	// TODO Auto-generated method stub
	
}
@Override
public void drop(DropTargetDropEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragEnter(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragOver(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dropActionChanged(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragExit(DropTargetEvent dte) {
	// TODO Auto-generated method stub
	
}
@Override
public void drop(DropTargetDropEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragEnter(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragOver(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dropActionChanged(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragExit(DropTargetEvent dte) {
	// TODO Auto-generated method stub
	
}
@Override
public void drop(DropTargetDropEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragEnter(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragOver(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dropActionChanged(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragExit(DropTargetEvent dte) {
	// TODO Auto-generated method stub
	
}
@Override
public void drop(DropTargetDropEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void tableChanged(TableModelEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragEnter(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragOver(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dropActionChanged(DropTargetDragEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void dragExit(DropTargetEvent dte) {
	// TODO Auto-generated method stub
	
}
@Override
public void drop(DropTargetDropEvent dtde) {
	// TODO Auto-generated method stub
	
}
@Override
public void valueChanged(ListSelectionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseDragged(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseMoved(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseClicked(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowOpened(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosing(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowClosed(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowIconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeiconified(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowActivated(WindowEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void windowDeactivated(WindowEvent e) {
	// TODO Auto-generated method stub
}
}