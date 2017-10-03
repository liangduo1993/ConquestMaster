package MapEditor.template.swing;


import java.io.File;
import java.net.URL;
import java.security.InvalidParameterException;

public class DocumentViewerObject
{
  String displayName;
  Object document;
  
  public DocumentViewerObject(String displayName, File document)
  {
    setDisplayName(displayName);
    setDocument(document);
  }
  
  public DocumentViewerObject(String displayName, String document)
  {
    setDisplayName(displayName);
    setDocument(document);
  }
  
  public DocumentViewerObject(String displayName, URL document)
  {
    setDisplayName(displayName);
    setDocument(document);
  }
  
  public String getDisplayName()
  {
    return this.displayName;
  }
  
  public Object getDocument()
  {
    return this.document;
  }
  
  public void setDisplayName(String displayName)
  {
    this.displayName = displayName;
  }
  
  public void setDocument(Object document)
  {
    if (((document instanceof File)) || 
      ((document instanceof String)) || 
      ((document instanceof URL))) {
      this.document = document;
    } else {
      throw new InvalidParameterException(
        "Document class of " + 
        document.getClass().getName() + 
        " is not handled by DocumentViewerObject (must be String, File, or URL)");
    }
  }
  
  public String toString()
  {
    return this.displayName;
  }
}

