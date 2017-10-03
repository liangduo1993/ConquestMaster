package MapEditor.template.awt;

public class PixelImageException
extends Exception
{
private static final long serialVersionUID = 1030534462860425092L;

public PixelImageException() {}

public PixelImageException(String message)
{
  super(message);
}

public PixelImageException(Throwable cause)
{
  super(cause);
}

public PixelImageException(String message, Throwable cause)
{
  super(message, cause);
}
}
