package MapEditor.Util;

public class MySemaphore
{
  private int num;
  private String name;
  
  public MySemaphore(String name)
  {
    this.name = name;
  }
  
  public synchronized void get()
  {
    this.num += 1;
  }
  
  public synchronized boolean isHeld()
  {
    return this.num > 0;
  }
  
  public synchronized void release()
  {
    this.num -= 1;
    if (this.num < 0)
    {
      this.num = 0;
      //ConquestMapMaker.logger.severe("Semaphore count reduced to -1 (name=" + this.name + ")");
    }
  }
  
  public String toString()
  {
    return "Semaphore " + this.name + "(count=" + this.num + ")";
  }
}
