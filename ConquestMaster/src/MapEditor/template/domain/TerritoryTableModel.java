package MapEditor.template.domain;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import MapEditor.Core.ConquestMap;
import MapEditor.Domain.Territory;

public class TerritoryTableModel
  extends AbstractTableModel
{
  private static final long serialVersionUID = -5870497948686297621L;
  static final int COLS = 2;
  static final int COL_TER_NAME = 0;
  static final int COL_CONT_NAME = 1;
  static final String[] COL_NAMES = { "Territory", "Continent" };
  ConquestMap map;
  
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
    if ((this.map == null) || (territories() == null)) {
      return 0;
    }
    return territories().size();
  }
  
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    if ((territories() == null) || (columnIndex < 0) || (columnIndex >= 2) || (rowIndex < 0) || 
      (rowIndex >= territories().size())) {
      return null;
    }
    Territory ter = (Territory)territories().get(rowIndex);
    switch (columnIndex)
    {
    case 0: 
      return ter.name;
    case 1: 
      return ter.getCont().getName();
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
    if ((territories() == null) || (columnIndex < 0) || (columnIndex >= 2) || (rowIndex < 0) || 
      (rowIndex > territories().size())) {
      return;
    }
    Territory ter = (Territory)territories().get(rowIndex);
    if (ter != null) {
      switch (columnIndex)
      {
      case 0: 
        this.map.setTerritoryName(ter, aValue.toString());
        fireTableDataChanged();
        break;
      case 1: 
        this.map.setTerritoryContinentName(ter, aValue.toString());
        fireTableDataChanged();
      }
    }
  }
  
  private ArrayList<Territory> territories()
  {
    if (this.map == null) {
      return null;
    }
    return this.map.territories;
  }
}
