package MapEditor.Swing;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import javax.swing.text.html.HTMLEditorKit;

public class DocumentViewer
  extends JPanel
{
  private static final long serialVersionUID = -5373974937427938348L;
  JEditorPane display;
  JScrollPane displayScrollPane;
  protected TitledBorder displayBorder;
  protected TitledBorder selectorBorder;
  JComponent selector;
  protected boolean initialized;
  
  public DocumentViewer() {}
  
  public DocumentViewer(DocumentViewerObject doc)
  {
    this();
    init(null);
    displayDocumentUnconditional(doc);
  }
  
  public void init(Dimension dim)
  {
    if (this.initialized) {
      return;
    }
    initGui(dim);
    this.initialized = true;
  }
  
  protected void initGui(Dimension dim)
  {
    this.display = new JEditorPane();
    this.display.setEditable(false);
    this.display.setEditorKit(new HTMLEditorKit());
    
    this.displayScrollPane = new JScrollPane(this.display);
    this.displayScrollPane.setVerticalScrollBarPolicy(20);
    this.displayScrollPane.setPreferredSize(dim == null ? new Dimension(700, 600) : dim);
    
    this.displayBorder = BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(1), "");
    
    this.displayScrollPane.setBorder(
      BorderFactory.createCompoundBorder(this.displayBorder, BorderFactory.createBevelBorder(1)));
    
    setLayout(new BorderLayout());
    
    getSelector();
    if (this.selector == null)
    {
      add(this.displayScrollPane);
    }
    else
    {
      this.selectorBorder = BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(1), "");
      this.selector.setBorder(this.selectorBorder);
      
      JSplitPane split = new JSplitPane(1, getSelector(), this.displayScrollPane);
      add(split);
    }
  }
  
  public void setDisplayTitle(String title)
  {
    if (this.displayBorder != null) {
      this.displayBorder.setTitle(title);
    }
  }
  
  public void setSelectorTitle(String title)
  {
    if (this.selectorBorder != null) {
      this.selectorBorder.setTitle(title);
    }
  }
  
  protected JComponent initSelector()
  {
    return null;
  }
  
  public void setSelectorVisible(boolean visible)
  {
    if (this.selector != null) {
      this.selector.setVisible(visible);
    }
  }
  
  protected JComponent getSelector()
  {
    if (this.selector == null) {
      this.selector = initSelector();
    }
    return this.selector;
  }
  
  private void initCheck()
    throws IOException
  {
    if (!this.initialized) {
      throw new IOException("DocumentViewer has not been initialized");
    }
  }
  
  public void clearDisplay()
  {
    this.display.setText("");
  }
  
  public void displayDocument(String doc, Object desc)
    throws IOException
  {
    initCheck();
    clearDisplay();
    InputStream in = getClass().getClassLoader().getResourceAsStream(doc);
    this.display.read(in, desc);
  }
  
  public void displayDocument(File doc, Object desc)
    throws IOException
  {
    initCheck();
    clearDisplay();
    FileInputStream in = new FileInputStream(doc);
    this.display.read(in, desc);
  }
  
  public void displayDocument(URL doc, Object desc)
    throws IOException
  {
    initCheck();
    clearDisplay();
    this.display.read(doc.openStream(), desc);
  }
  
  public void displayDocument(Object doc, Object desc)
    throws IOException
  {
    initCheck();
    clearDisplay();
    if (doc == null) {
      return;
    }
    if ((doc instanceof DocumentViewerObject)) {
      displayDocument(((DocumentViewerObject)doc).getDocument(), null);
    } else if ((doc instanceof File)) {
      displayDocument((File)doc, desc);
    } else if ((doc instanceof URL)) {
      displayDocument((URL)doc, desc);
    } else if ((doc instanceof String)) {
      displayDocument((String)doc, desc);
    } else {
      throw new IOException("Unsupported display class: " + doc.getClass());
    }
  }
  
  public void displayDocument(Object doc)
    throws IOException
  {
    displayDocument(doc, null);
  }
  
  public void displayDocumentUnconditional(Object doc, Object desc)
  {
    try
    {
      displayDocument(doc, desc);
    }
    catch (IOException e)
    {
    //  displayError("Unable to display document " + doc.toString() + " (" + e + ")");
    }
  }
  
  public void displayDocumentUnconditional(Object doc)
  {
    displayDocumentUnconditional(doc, null);
  }
  
//  protected void displayError(String message)
//  {
//    System.err.println("Error during display: " + message);
//    try
//    {
//      this.display.read(null, null);
//    }
//    catch (Exception localException) {}
//  }
  
  public static void displayPopup(Object doc, String title, Dimension dimension)
  {
    displayPopup(doc, title, dimension, null);
  }
  
  public static void displayPopup(Object doc, String title, Dimension dimension, DocumentViewer viewer)
  {
    if (viewer == null)
    {
      viewer = new DocumentViewer();
      viewer.init(dimension);
    }
    if (doc != null) {
      viewer.displayDocumentUnconditional(doc);
    }
    JOptionPane.showMessageDialog(null, viewer, title == null ? doc.toString() : title, 
      -1);
  }
  
  
  
}
