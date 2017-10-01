package MapEditor.Util;


import java.io.Serializable;

public class IntQueue
  implements Serializable
{
  private static final long serialVersionUID = -7278351861869164660L;
  public static final int DEFAULTINITIAL = 256;
  public static final int DEFAULTMAX = 4096;
  int[] k;
  int max = 0;
  int cur = 0;
  int num = 0;
  
  public IntQueue()
  {
    this(256, 4096);
  }
  
  public IntQueue(int initial)
  {
    this(initial, 4096);
  }
  
  public IntQueue(int initial, int max)
  {
    this.cur = 0;
    this.num = 0;
    if (initial < 1) {
      initial = 1;
    }
    if (initial > max) {
      max = initial;
    }
    this.max = max;
    this.k = new int[initial];
  }
  
  public int getCapacity()
  {
    return this.k.length;
  }
  
  public synchronized boolean isEmpty()
  {
    return this.num <= 0;
  }
  
  public int getMaxCapacity()
  {
    return this.max;
  }
  
  public int getSize()
  {
    return this.num;
  }
  
  public synchronized void clear()
  {
    this.num = 0;
  }
  
  public int ensureCapacity(int c)
  {
    if (c > this.max) {
      c = this.max;
    }
    if (this.k.length < c) {
      return resizeCapacity(c);
    }
    return getCapacity();
  }
  
  public synchronized int setMaxCapacity(int m)
  {
    if (m >= this.num) {
      this.max = m;
    }
    return this.max;
  }
  
  public synchronized int resizeCapacity(int s)
  {
    if (s < this.num) {
      s = this.num;
    } else if (s > this.max) {
      s = this.max;
    }
    if (s != getCapacity())
    {
      int[] nk = new int[s];
      if (this.cur + this.num <= this.k.length)
      {
        System.arraycopy(this.k, this.cur, nk, 0, this.num);
      }
      else
      {
        int taillen = this.k.length - this.cur;
        int headlen = this.num - taillen;
        System.arraycopy(this.k, this.cur, nk, 0, taillen);
        System.arraycopy(this.k, 0, nk, taillen, headlen);
      }
      this.k = nk;
      this.cur = 0;
    }
    return getCapacity();
  }
  
  public int pop(long waittime, int failInt)
  {
    if (this.num <= 0)
    {
      if (waittime == 0L) {
        synchronized (this)
        {
          try
          {
            wait();
          }
          catch (InterruptedException localInterruptedException) {}
        }
      }
      if (waittime > 0L) {
        synchronized (this)
        {
          try
          {
            wait(waittime);
          }
          catch (InterruptedException localInterruptedException1) {}
        }
      }
    }
    return pop(failInt);
  }
  
  public synchronized int pop(int failInt)
  {
    if (this.num <= 0) {
      return failInt;
    }
    int n = this.k[this.cur];
    this.cur = ((this.cur + 1) % this.k.length);
    this.num -= 1;
    return n;
  }
  
  public synchronized int pop()
  {
    return pop(0);
  }
  
  public synchronized boolean push(int key)
  {
    if (this.num == this.k.length)
    {
      if (this.k.length < this.max) {
        resizeCapacity(Calc.min(this.max, this.k.length * 2));
      }
      if (this.num == this.k.length) {
        return false;
      }
    }
    this.k[((this.cur + this.num) % this.k.length)] = key;
    
    this.num += 1;
    notify();
    return true;
  }
  
  public String toString()
  {
    return 
      getClass().getName() + ": " + "size=" + getSize() + ", capacity=" + getCapacity() + ", maxcapacity=" + getMaxCapacity();
  }
}
