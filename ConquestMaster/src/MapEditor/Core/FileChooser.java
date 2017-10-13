package MapEditor.Core;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import MapEditor.Model.ConquestMap;
import MapEditor.Util.MyStringUtil;

/**
 * 
 * This class is to realize the function of new/save/save as functions for map.
 *
 */
public class FileChooser {
	private final String NEWLINE = "\n";
	private ConquestMap map;
	private JTextArea jta = mainFrame.lp.log;
	private JFileChooser fc;

	/**
	 * The constructor of the class, to do some initial work.
	 * 
	 * @param operation
	 *            The operation inputed, like "save" or "saveas".
	 * @param map
	 *            The current reading map in memory.
	 */
	public FileChooser(String operation, ConquestMap map) {
		this.map = map;
		fc = new JFileChooser();
		if (operation.equals("save")) {
			mapSave();
			return;
		}
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			switch (operation) {
			case "load":
				mapOpen();
				break;
			case "saveas":
				mapSaveAs();
				break;
			default:
				break;
			}
		} else {
			switch (operation) {
			case "load":
				jta.append("Open command cancelled by user." + NEWLINE);
				break;
			case "saveas":
				jta.append("Save command cancelled by user." + NEWLINE);
				break;
			default:
				break;
			}
		}
		jta.setCaretPosition(jta.getDocument().getLength());
	}

	/**
	 * Open function.
	 */
	public void mapOpen() {
		File path = fc.getSelectedFile();
		System.out.println(MyStringUtil.getMapPath(path));
		try {
			this.map.load(MyStringUtil.getMapPath(path));
			jta.append("Map is successfully loaded!" + NEWLINE);
			// tablePanel.updateTable();
		} catch (IOException e) {
			jta.append("Loading map failed!" + NEWLINE);
			e.printStackTrace();
		}

	}

	/**
	 * Save function.
	 */
	public void mapSave() {
		if (this.map.getMapFilePath() != null) {
			try {
				this.map.save();
				jta.append("Map is successfully saved!" + NEWLINE);
			} catch (Exception e) {
				jta.append("Save failed!" + NEWLINE);
				e.printStackTrace();
			}
		} else {
			jta.append("There's no Map loaded!" + NEWLINE);
		}

	}

	/**
	 * Save as function.
	 */
	public void mapSaveAs() {
		File path = fc.getSelectedFile();
		System.out.println(MyStringUtil.getMapPath(path));
		try {
			this.map.save(MyStringUtil.getMapPath(path));
			jta.append("Map is successfully saved!" + NEWLINE);
		} catch (IOException e) {
			jta.append("Save as failed!" + NEWLINE);
			e.printStackTrace();
		}
	}

}