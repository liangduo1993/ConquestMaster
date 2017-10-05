package MapEditor.View;

import java.awt.Color;
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
	private JLabel pathMsg = new JLabel();
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
		frame.setBounds(400, 100, 450, 370);
		frame.setResizable(false);
		frame.setTitle("New Map");
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		tAuthor = new JTextField();
		tAuthor.setBounds(140, 35, 100, 21);
		tAuthor.setColumns(10);

		wrapCheckBox = new JCheckBox("");
		wrapCheckBox.setBounds(140, 82, 21, 21);

		warnCheckBox = new JCheckBox("");
		warnCheckBox.setBounds(140, 121, 21, 21);

		comboBox = new JComboBox();
		comboBox.setBounds(140, 175, 110, 21);
		ScrollOptions[] values = ScrollOptions.values();
		comboBox.setModel(new DefaultComboBoxModel(values));

		JLabel lblNewLabel_1 = new JLabel("ImagePath:");
		lblNewLabel_1.setBounds(40, 247, 100, 15);

		pathMsg.setBounds(40, 210, 410, 15);
		pathMsg.setForeground(Color.RED);

		errMsg.setBounds(40, 10, 300, 15);
		errMsg.setForeground(Color.RED);

		// ImagePath button no Name needed
		imgBtn = new JButton("Please select a image!");
		imgBtn.setBounds(140, 243, 165, 23);
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
						pathMsg.setText(path.getAbsolutePath());
					} else {
						jta.append("Please choose a validate type of image!" + newline);
						errMsg.setText("Please choose a validate type of image!");
					}
				}
			}
		});

		confirmBtn = new JButton("Create");
		confirmBtn.setBounds(70, 300, 100, 23);
		confirmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errMsg.setText("");
				if (validateInfo()) {
					mapNew();
					frame.setVisible(false);
				}
			}
		});
		cancelBtn = new JButton("Cancel");
		cancelBtn.setBounds(220, 300, 100, 23);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jta.append("New Map command cancelled by user." + newline);
				frame.setVisible(false);
			}
		});

		JLabel lblNewLabel_4 = new JLabel("Scroll:");
		lblNewLabel_4.setBounds(40, 178, 42, 15);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(lblNewLabel_4);
		frame.getContentPane().add(warnCheckBox);
		frame.getContentPane().add(wrapCheckBox);
		frame.getContentPane().add(tAuthor);
		frame.getContentPane().add(pathMsg);
		frame.getContentPane().add(errMsg);
		frame.getContentPane().add(imgBtn);
		frame.getContentPane().add(confirmBtn);
		frame.getContentPane().add(cancelBtn);
		frame.getContentPane().add(comboBox);

		JLabel lblNewLabel = new JLabel("Author:");
		lblNewLabel.setBounds(40, 40, 54, 15);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("Warp:");
		lblNewLabel_2.setBounds(40, 86, 54, 15);
		frame.getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Warn:");
		lblNewLabel_3.setBounds(40, 126, 54, 15);
		frame.getContentPane().add(lblNewLabel_3);

		frame.setVisible(true);
	}

	
	
	private boolean validateInfo() {
		if (tAuthor.getText().trim().equals("") || tAuthor.getText() == null) {
			errMsg.setText("Author cannot be blank!");
			return false;
		}
		if (map.getImageFilePath() == null) {
			errMsg.setText("Please choose a valid image path!");
			return false;
		}
		return true;
	}

	public void mapNew() {
		map.setAuthor(tAuthor.getText().trim());
		map.setScroll((ConquestMap.ScrollOptions) comboBox.getSelectedItem());
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