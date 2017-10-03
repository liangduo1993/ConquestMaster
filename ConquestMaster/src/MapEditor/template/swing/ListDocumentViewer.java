package MapEditor.template.swing;


import java.awt.Dimension;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListDocumentViewer
  extends DocumentViewer
  implements ListSelectionListener
{
  private static final long serialVersionUID = 5419659858908417361L;
  JList list;
  DefaultListModel listModel;
  
  public ListDocumentViewer() {}
  
  public ListDocumentViewer(Collection<DocumentViewerObject> docs)
  {
    setDocuments(docs);
  }
  
  public ListDocumentViewer(DocumentViewerObject[] docs)
  {
    setDocuments(docs);
  }
  
  public void setDocuments(Collection<DocumentViewerObject> docs)
  {
    DefaultListModel m = new DefaultListModel();
    if (docs != null) {
      for (DocumentViewerObject doc : docs) {
        m.addElement(doc);
      }
    }
    setModel(m);
  }
  
  public void addDocument(Object doc)
  {
    this.listModel.addElement(doc);
  }
  
  public void setDocuments(DocumentViewerObject[] docs)
  {
    DefaultListModel m = new DefaultListModel();
    if (docs != null)
    {
      DocumentViewerObject[] arrayOfDocumentViewerObject;
      int j = (arrayOfDocumentViewerObject = docs).length;
      for (int i = 0; i < j; i++)
      {
        DocumentViewerObject doc = arrayOfDocumentViewerObject[i];
        m.addElement(doc);
      }
    }
    setModel(m);
  }
  
  protected void setModel(DefaultListModel model)
  {
    this.listModel = model;
    if (this.list != null) {
      this.list.setModel(model);
    }
  }
  
  protected JComponent initSelector()
  {
    if (this.listModel == null) {
      this.listModel = new DefaultListModel();
    }
    this.list = new JList(this.listModel);
    this.list.setSelectionMode(0);
    
    JScrollPane listScroll = new JScrollPane(this.list);
    listScroll.setVerticalScrollBarPolicy(20);
    this.list.addListSelectionListener(this);
    
    return listScroll;
  }
  
  public void select(Object o)
  {
    this.list.setSelectedValue(o, true);
  }
  
  public void selectByName(String name)
  {
    Enumeration en = this.listModel.elements();
    while (en.hasMoreElements())
    {
      Object o = en.nextElement();
      if (o.toString().equals(name))
      {
        this.list.setSelectedValue(o, true);
        return;
      }
    }
    clearDisplay();
  }
  
  public void valueChanged(ListSelectionEvent e)
  {
    if (e.getSource() == this.list) {
      displayDocumentUnconditional(this.list.getSelectedValue());
    }
  }
  
  public static void displayPopup(Collection<DocumentViewerObject> docs, Object initialDocument, Object selected, String title, Dimension dimension)
  {
    ListDocumentViewer view = new ListDocumentViewer(docs);
    view.init(null);
    
    Object o = initialDocument;
    if (o == null)
    {
      Iterator<DocumentViewerObject> it = docs.iterator();
      o = it.hasNext() ? (DocumentViewerObject)it.next() : null;
    }
    view.select(o);
    displayPopup(o, title, dimension, view);
  }
  
  public static void displayPopup(DocumentViewerObject[] docs, int intialIndex, String title, Dimension dimension)
  {
    ListDocumentViewer view = new ListDocumentViewer(docs);
    view.init(null);
    view.select(docs[intialIndex]);
    displayPopup(docs[intialIndex], title, dimension, view);
  }
  
  public static void main(String[] args)
  {
    displayPopup(new DocumentViewerObject[] {
      new DocumentViewerObject("A text file", "testfiles/testdoc.txt"), 
      new DocumentViewerObject("An HTML file", "testfiles/testdoc.html") }, 1, null, null);
    
    System.exit(0);
  }
}
