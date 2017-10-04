package MapEditor.Util;

public class BStringComparableAdapter
implements BComparable
{
public int compare(Object a, Object b)
{
  return a.toString().compareTo(b.toString());
}

public int compareTo(Object a)
{
  return compare(this, a);
}
}