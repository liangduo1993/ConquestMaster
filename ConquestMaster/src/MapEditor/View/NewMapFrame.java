package MapEditor.View;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import MapEditor.mainFrame;
import MapEditor.Core.ConquestMap;
import MapEditor.Core.ConquestMap.ScrollOptions;
import MapEditor.Util.MyStringUtil;

public class NewMapFrame {
	private final String newline = "\n";
	private ConquestMap map = mainFrame.map;
	private JFrame frame;
	private JTextField tAuthor;
	private JCheckBox wrapCheckBox, warnCheckBox;
	private JButton imgBtn, confirmBtn, cancelBtn;
	private JFileChooser fc;
	private JTextArea jta = mainFrame.lp.log;
	private TablePanel tablePanel = mainFrame.infoPanel;
	private JLabel errMsg = new JLabel();
private JComboBox comboBox;
	/**
	 * Create the application.
	 */
	public NewMapFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		map.clear();
		fc = new JFileChooser();
		frame = new JFrame();
		frame.setBounds(400, 100, 380, 370);
		frame.setResizable(false);
		frame.setTitle("New Map");
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tAuthor = new JTextField();
		tAuthor.setBounds(140, 5, 100, 21);
		tAuthor.setColumns(10);

		wrapCheckBox = new JCheckBox("");
		wrapCheckBox.setBounds(140, 52, 21, 21);

		warnCheckBox = new JCheckBox("");
		warnCheckBox.setBounds(140, 91, 21, 21);

		 comboBox = new JComboBox();
		comboBox.setBounds(140, 145, 110, 21);
		ScrollOptions[] values = ScrollOptions.values();
		comboBox.setModel(new DefaultComboBoxModel(values));

		JLabel lblNewLabel_1 = new JLabel("ImagePath:");
		lblNewLabel_1.setBounds(40, 217, 100, 15);

		// ImagePath button no Name needed
		imgBtn = new JButton("Please select a image!");
		imgBtn.setBounds(140, 213, 165, 23);
		imgBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File path = fc.getSelectedFile();
					if (MyStringUtil.checkType(path)) {
						map.setImageFilePath(path.getAbsolutePath());
						map.setMapFilePath(MyStringUtil.getMapPath(path));
						System.out.println(path.getAbsolutePath());
						jta.append("You have chose a image in the path:" + path.getAbsolutePath() + newline);
					} else {
						jta.append("Please choose a validate type of image!" + newline);
					}
				}
			}
		});

		confirmBtn = new JButton("Create");
		confirmBtn.setBounds(70, 270, 100, 23);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errMsg.setText("");
				if (validateInfo()) {
					mapNew();
					frame.setVisible(false);
				}
			}
		});
		cancelBtn = new JButton("Create");
		cancelBtn.setBounds(220, 270, 100, 23);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jta.append("New Map command cancelled by user." + newline);
				frame.setVisible(false);
			}
		});

		JLabel lblNewLabel_4 = new JLabel("Scroll:");
		lblNewLabel_4.setBounds(40, 148, 42, 15);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(lblNewLabel_4);
		frame.getContentPane().add(warnCheckBox);
		frame.getContentPane().add(wrapCheckBox);
		frame.getContentPane().add(tAuthor);
		frame.getContentPane().add(imgBtn);
		frame.getContentPane().add(confirmBtn);
		frame.getContentPane().add(cancelBtn);
		frame.getContentPane().add(comboBox);

		JLabel lblNewLabel = new JLabel("Author:");
		lblNewLabel.setBounds(40, 10, 54, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Warp:");
		lblNewLabel_2.setBounds(40, 56, 54, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Warn:");
		lblNewLabel_3.setBounds(40, 96, 54, 15);
		frame.getContentPane().add(lblNewLabel_3);

		frame.setVisible(true);
	}

	private boolean validateInfo() {

		return true;
	}

	public void mapNew() {
		map.setAuthor(tAuthor.getText().trim());
		map.setScroll((ConquestMap.ScrollOptions)comboBox.getSelectedItem());
		map.setWarn(warnCheckBox.isSelected());
		map.setWrap(wrapCheckBox.isSelected());
		tablePanel.updateTable();
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMapFrame window = new NewMapFrame();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}