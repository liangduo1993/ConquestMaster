package MapEditor.Editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import MapEditor.Core.ConquestMap;
import MapEditor.template.core.MapDisplay;

public class FileChooserPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	static private final String newline = "\n";
	private JButton newButton, openButton, saveButton, saveAsButton;
	private ConquestMap map = MainFrame.map;
	private MapDisplay mapDisplay = MainFrame.mapDisplay;
	private JTextArea jta = MainFrame.lp.log;
	private JFileChooser fc;

	public FileChooserPanel() {
		super(new BorderLayout());

		fc = new JFileChooser();

		openButton = new JButton("Open Map", null);
		openButton.addActionListener(this);

		saveButton = new JButton("Save Map", null);
		saveButton.addActionListener(this);

		newButton = new JButton("New Map", null);
		newButton.addActionListener(this);

		saveAsButton = new JButton("Save As", null);
		saveAsButton.addActionListener(this);

		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(openButton);
		buttonPanel.add(newButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(saveAsButton);

		add(buttonPanel, BorderLayout.PAGE_START);

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
			int returnVal = fc.showOpenDialog(FileChooserPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				mapOpen();
				jta.append("Opening: " + file.getName() + "." + newline);
			} else {
				jta.append("Open command cancelled by user." + newline);
			}
			jta.setCaretPosition(jta.getDocument().getLength());

		} else if (e.getSource() == saveAsButton) {
			int returnVal = fc.showSaveDialog(FileChooserPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				mapSaveAs();
				jta.append("Saving: " + file.getName() + "." + newline);
			} else {
				jta.append("Save command cancelled by user." + newline);
			}
			jta.setCaretPosition(jta.getDocument().getLength());

		} else if (e.getSource() == newButton) {
			int returnVal = fc.showSaveDialog(FileChooserPanel.this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				mapNew();
				jta.append("Opening Image: " + file.getName() + "." + newline);
			} else {
				jta.append("New Map command cancelled by user." + newline);
			}
			jta.setCaretPosition(jta.getDocument().getLength());

		} else if (e.getSource() == saveButton) {
		
				mapSave();
		
				
			jta.setCaretPosition(jta.getDocument().getLength());
		}
	}

	public void mapNew() {
		this.map.clear();

		this.mapDisplay.repaint();
	}

	public void mapOpen() {

		File path = fc.getSelectedFile();
		File dirf = path.getParentFile();
		String dir = dirf.getAbsolutePath() + File.separator;
		String fn = path.getName();
		if (fn.indexOf('.') == -1) {
			fn = fn + ".map";
		}
		System.out.println(dir + fn);
		try {
			this.map.load(dir + fn);
		} catch (IOException e) {
			jta.append("Loading map failed!" + newline);
			e.printStackTrace();
		}

	}

	public void mapSave() {

		if (this.map.getMapFilePath() != null) {
			try {
				this.map.save();
			} catch (IOException e) {
				jta.append("Save failed!" + newline);
				e.printStackTrace();
			}
		}

	}

	public void mapSaveAs() {

		File path = fc.getSelectedFile();
		File dirf = path.getParentFile();
		String dir = dirf.getAbsolutePath() + File.separator;
		String fn = path.getName();
		if (fn.indexOf('.') == -1) {
			fn = fn + ".map";
		}
		System.out.println(dir + fn);
		try {
			this.map.save(dir + fn);
		} catch (IOException e) {
			jta.append("Save as failed!" + newline);
			e.printStackTrace();
		}

	}

	// public void mapSelectImage() {
	// JFileChooser fc = getMapImageFc();
	// int returnVal = fc.showOpenDialog(this);
	// if (returnVal == 0) {
	// mapSelectImage(fc.getSelectedFile());
	// }
	// }
	//
	// public void mapSelectImage(File newImage) {
	// try {
	// this.map.setImageFilePath(newImage.getAbsolutePath());
	// reloadMapImage();
	// } catch (Exception ex) {
	// errorMessage("An error occurred while loading the map image: " + ex);
	// }
	// }
	//
	// public File mapSelectImageNew() {
	// JFileChooser fc = getMapImageFc();
	// File dir = this.map.getMapDirectory();
	// if (dir != null) {
	// fc.setCurrentDirectory(dir);
	// }
	// int returnVal = fc.showSaveDialog(this);
	// if (returnVal == 0) {
	// File f = fc.getSelectedFile();
	// if ((f != null) && (f.getName() != null) && (!f.getName().contains(".")))
	// {
	// f = new File(f.getPath() + ".bmp");
	// }
	// return f;
	// }
	// return null;
	// }
	//
	// public boolean saveChangesCheck() {
	// if ((this.map == null) || (!this.map.dirty)) {
	// return true;
	// }
	// int opt = JOptionPane.showOptionDialog(this,
	// "Do you want to save the changes you made to " + this.map.getMapName() +
	// "?", "Conquest Map Maker", 1,
	// 2, null, null, null);
	// if (opt == 1) {
	// return true;
	// }
	// if (opt == 2) {
	// return false;
	// }
	// return mapSave();
	// }

	// public JFileChooser getMapFc() {
	// if (this.mapFc == null) {
	// this.mapFc = makeFc();
	// }
	// this.mapFc.resetChoosableFileFilters();
	// this.mapFc.addChoosableFileFilter(new ExFileFilter("map", "Conquest Map
	// Files"));
	// return this.mapFc;
	// }
	//
	// public JFileChooser makeFc()
	// {
	// JFileChooser fc = new JFileChooser();
	// fc.setFileSelectionMode(0);
	//// String lp = this.props.getProperty("LastPath");
	// String lp = "c:\\";
	// if ((lp != null) && (lp.length() > 0))
	// {
	// File lfp = new File(lp);
	// if (lfp.exists()) {
	// fc.setCurrentDirectory(lfp);
	// }
	// }
	// return fc;
	// }

}