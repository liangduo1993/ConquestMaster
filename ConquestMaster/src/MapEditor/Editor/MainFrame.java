package MapEditor.Editor;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import MapEditor.Core.ConquestMap;
import MapEditor.Core.MapDisplay;
import MapEditor.Util.ExFileFilter;

public class MainFrame extends JFrame {
	public ConquestMap map;
	public MapDisplay mapDisplay;
	public JFileChooser mapFc = null;

	public void mapNew() {
		if (!saveChangesCheck()) {
			return;
		}
		this.map.clear();
		setMapTitle();
		setMap(this.map);
		this.mapDisplay.repaint();
	}

	public void mapPromptAndLoad(String path) {
		try {
			if (!saveChangesCheck()) {
				return;
			}
			if (path == null) {
				JFileChooser fc = getMapFc();
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == 0) {
					path = fc.getSelectedFile().getAbsolutePath();
				} else {
					return;
				}
			}
			try {
				mapLoad(path);
			} catch (FileNotFoundException e) {
				// errorMessage("Map file \"" + path + "\" was not found");
			}
			return;
		} catch (Exception e) {
			// errorMessage("An error occurred while loading the map: " + e);
		}
	}

	public boolean mapSave() {
		try {
			if (this.map.getMapFilePath() != null) {
				if (!saveImageCheck()) {
					return false;
				}
				this.map.save();
				return true;
			}
			if (!saveImageCheck()) {
				return false;
			}
			return mapSaveAs();
		} catch (Exception ex) {
			errorMessage("An error occurred while saving the map: " + ex);
		}
		return false;
	}

	public boolean mapSaveAs() {
		abortOperation(true);
		boolean saved = false;
		try {
			JFileChooser fc = getMapFc();
			if (this.map.getMapFilePath() != null) {
				fc.setSelectedFile(new File(this.map.getMapFilePath()));
			}
			int returnVal = fc.showSaveDialog(this);
			if ((returnVal == 0) && (saveImageCheck())) {
				File path = fc.getSelectedFile();
				File dirf = path.getParentFile();
				String dir = dirf.getAbsolutePath() + File.separator;
				String fn = path.getName();
				if (fn.indexOf('.') == -1) {
					fn = fn + ".map";
				}
				this.map.save(dir + fn);
				saved = true;
			}
		} catch (Exception ex) {
			errorMessage("An error occurred while saving the map: " + ex);
		}
		setMapTitle();
		return saved;
	}

	public void mapSelectImage() {
		JFileChooser fc = getMapImageFc();
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == 0) {
			mapSelectImage(fc.getSelectedFile());
		}
	}

	public void mapSelectImage(File newImage) {
		try {
			this.map.setImageFilePath(newImage.getAbsolutePath());
			reloadMapImage();
		} catch (Exception ex) {
			errorMessage("An error occurred while loading the map image: " + ex);
		}
	}

	public File mapSelectImageNew() {
		JFileChooser fc = getMapImageFc();
		File dir = this.map.getMapDirectory();
		if (dir != null) {
			fc.setCurrentDirectory(dir);
		}
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == 0) {
			File f = fc.getSelectedFile();
			if ((f != null) && (f.getName() != null) && (!f.getName().contains("."))) {
				f = new File(f.getPath() + ".bmp");
			}
			return f;
		}
		return null;
	}

	public boolean saveChangesCheck() {
		if ((this.map == null) || (!this.map.dirty)) {
			return true;
		}
		int opt = JOptionPane.showOptionDialog(this,
				"Do you want to save the changes you made to " + this.map.getMapName() + "?", "Conquest Map Maker", 1,
				2, null, null, null);
		if (opt == 1) {
			return true;
		}
		if (opt == 2) {
			return false;
		}
		return mapSave();
	}

	public JFileChooser getMapFc() {
		if (this.mapFc == null) {
			this.mapFc = makeFc();
		}
		this.mapFc.resetChoosableFileFilters();
		this.mapFc.addChoosableFileFilter(new ExFileFilter("map", "Conquest Map Files"));
		return this.mapFc;
	}

	public JFileChooser makeFc()
	  {
	    JFileChooser fc = new JFileChooser();
	    fc.setFileSelectionMode(0);
//	    String lp = this.props.getProperty("LastPath");
	    String lp = "c:\\";
	    if ((lp != null) && (lp.length() > 0))
	    {
	      File lfp = new File(lp);
	      if (lfp.exists()) {
	        fc.setCurrentDirectory(lfp);
	      }
	    }
	    return fc;
	  }
	
}
