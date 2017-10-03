package MapEditor.Core;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import MapEditor.mainFrame;
import MapEditor.Util.MyStringUtil;

public class FileChooser{
	private final String newline = "\n";
	private ConquestMap map = mainFrame.map;
	private JTextArea jta = mainFrame.lp.log;
	private JFileChooser fc;
	
	public FileChooser(String operation) {
		fc = new JFileChooser();
		if(operation.equals("save")){
			mapSave();
			return;
		}
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			switch (operation) {
			case "new":
				mapNew();
				break;
			case "load":
				mapOpen();
				break;
			case "saveas":
				mapSaveAs();
				break;
			default:
				break;
			}
		}else{
			switch (operation) {
			case "new":
				jta.append("New Map command cancelled by user." + newline);
				break;
			case "load":
				jta.append("Open command cancelled by user." + newline);
				break;
			case "saveas":
				jta.append("Save command cancelled by user." + newline);
				break;
			default:
				break;
			}
		}
		jta.setCaretPosition(jta.getDocument().getLength());
	}

	
	

	public void mapNew() {
		this.map.clear();
		File path = fc.getSelectedFile();
		if (MyStringUtil.checkType(path)) {
			this.map.setImageFilePath(MyStringUtil.getMapPath(path));
			this.map.setMapFilePath(MyStringUtil.getMapPath(path));
			jta.append("New Map is successfully created!" + newline);
		}else{
			jta.append("Please choose a validate type of image!" + newline);
			
		}
	}

	public void mapOpen() {
		File path = fc.getSelectedFile();
		System.out.println(MyStringUtil.getMapPath(path));
		try {
			this.map.load(MyStringUtil.getMapPath(path));
			jta.append("Map is successfully loaded!" + newline);
		} catch (IOException e) {
			jta.append("Loading map failed!" + newline);
			e.printStackTrace();
		}

	}

	public void mapSave() {
		if (this.map.getMapFilePath() != null) {
			try {
				this.map.save();
				jta.append("Map is successfully saved!" + newline);
			} catch (IOException e) {
				jta.append("Save failed!" + newline);
				e.printStackTrace();
			}
		}else{
			jta.append("There's no Map loaded!" + newline);
		}

	}

	public void mapSaveAs() {
		File path = fc.getSelectedFile();
		System.out.println(MyStringUtil.getMapPath(path));
		try {
			this.map.save(MyStringUtil.getMapPath(path));
			jta.append("Map is successfully saved!" + newline);
		} catch (IOException e) {
			jta.append("Save as failed!" + newline);
			e.printStackTrace();
		}
	}



}