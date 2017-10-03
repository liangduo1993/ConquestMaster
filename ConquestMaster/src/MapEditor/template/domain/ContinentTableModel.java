package MapEditor.template.domain;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import MapEditor.Core.ConquestMap;
import MapEditor.Domain.Continent;
import MapEditor.template.util.StringUtil;

public class ContinentTableModel
  extends AbstractTableModel
{
  private static final long serialVersionUID = 1386722639912768470L;
  static final int COL_NAME = 0;
  static final int COL_BONUS = 1;
  static final int COLS = 2;
  static final String[] COL_NAMES = { "Continent", "Bonus" };
  ConquestMap map;
  
  private ArrayList<Continent> continents()
  {
    if (this.map == null) {
      return null;
    }
    return this.map.continents;
  }
  
  public void dataChanged()
  {
    fireTableDataChanged();
  }
  
  public Class<?> getColumnClass(int c)
  {
    Object o = getValueAt(0, c);
    if (o == null) {
      return Object.class;
    }
    return getValueAt(0, c).getClass();
  }
  
  public int getColumnCount()
  {
    return 2;
  }
  
  public String getColumnName(int columnIndex)
  {
    if (columnIndex < COL_NAMES.length) {
      return COL_NAMES[columnIndex];
    }
    return super.getColumnName(columnIndex);
  }
  
  public ConquestMap getMap()
  {
    return this.map;
  }
  
  public int getRowCount()
  {
    if ((this.map == null) || (continents() == null)) {
      return 0;
    }
    return continents().size();
  }
  
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    if ((continents() == null) || (columnIndex < 0) || (columnIndex >= 2) || (rowIndex < 0) || 
      (rowIndex >= continents().size())) {
      return null;
    }
    Continent cont = (Continent)continents().get(rowIndex);
    switch (columnIndex)
    {
    case 0: 
      return cont.name;
    case 1: 
      return new Integer(cont.bonus);
    }
    return null;
  }
  
  public boolean isCellEditable(int rowIndex, int columnIndex)
  {
    return true;
  }
  
  public void setMap(ConquestMap map)
  {
    this.map = map;
  }
  
  public void setValueAt(Object aValue, int rowIndex, int columnIndex)
  {
    if ((continents() == null) || (columnIndex < 0) || (columnIndex >= 2) || (rowIndex < 0) || 
      (rowIndex > continents().size())) {
      return;
    }
    Continent cont = (Continent)continents().get(rowIndex);
    if (cont != null) {
      switch (columnIndex)
      {
      case 0: 
        this.map.setContinentName(cont, aValue.toString());
        fireTableDataChanged();
        break;
      case 1: 
        int val = StringUtil.parseInt(aValue.toString(), -1);
        if (val >= 0)
        {
          cont.bonus = val;
          fireTableDataChanged();
        }
        break;
      }
    }
  }
}
