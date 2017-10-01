package MapEditor.Util;

public class Calc
{
  public static final float PI = 3.1415927F;
  
  public static float abs(float x)
  {
    return x >= 0.0F ? x : -x;
  }
  
  public static int abs(int x)
  {
    return x >= 0 ? x : -x;
  }
  
  public static long abs(long x)
  {
    return x >= 0L ? x : -x;
  }
  
  public static boolean contains(int a, int min, int max)
  {
    return (a >= min) && (a <= max);
  }
  
  public static float bound(float a, float min, float max)
  {
    if (a < min) {
      return min;
    }
    if (a > max) {
      return max;
    }
    return a;
  }
  
  public static int bound(int a, int min, int max)
  {
    if (a < min) {
      return min;
    }
    if (a > max) {
      return max;
    }
    return a;
  }
  
  public static int dist(int x1, int y1, int x2, int y2)
  {
    x1 -= x2;
    y1 -= y2;
    return (int)StrictMath.sqrt(x1 * x1 + y1 * y1);
  }
  
  public static float dist(float x1, float y1, float x2, float y2)
  {
    x1 -= x2;
    y1 -= y2;
    return (float)StrictMath.sqrt(x1 * x1 + y1 * y1);
  }
  
  public static float lg(float x)
  {
    return (float)(StrictMath.log(x) / StrictMath.log(2.0D));
  }
  
  public static float matchSign(float x, float s)
  {
    if (((x < 0.0F) && (s < 0.0F)) || ((x >= 0.0F) && (s >= 0.0F))) {
      return x;
    }
    return -x;
  }
  
  public static int matchSign(int x, int s)
  {
    if (((x < 0) && (s < 0)) || ((x >= 0) && (s >= 0))) {
      return x;
    }
    return -x;
  }
  
  public static float max(float a, float b)
  {
    if (a > b) {
      return a;
    }
    return b;
  }
  
  public static int max(int a, int b)
  {
    if (a > b) {
      return a;
    }
    return b;
  }
  
  public static long max(long a, long b)
  {
    if (a > b) {
      return a;
    }
    return b;
  }
  
  public static float min(float a, float b)
  {
    if (a < b) {
      return a;
    }
    return b;
  }
  
  public static int min(int a, int b)
  {
    if (a < b) {
      return a;
    }
    return b;
  }
  
  public static long min(long a, long b)
  {
    if (a < b) {
      return a;
    }
    return b;
  }
  
  public static float sign(float x)
  {
    return x < 0.0F ? -1.0F : x == 0.0F ? 0.0F : 1.0F;
  }
  
  public static int sign(int x)
  {
    return x < 0 ? -1 : x == 0 ? 0 : 1;
  }
  
  public static int intPercent(float p)
  {
    return Math.round(p * 100.0F);
  }
}
