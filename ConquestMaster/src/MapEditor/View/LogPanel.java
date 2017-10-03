package MapEditor.View;

import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogPanel extends JPanel {
	private static final String newline = "\n";
	public JTextArea log;

	public LogPanel() {
		super(new BorderLayout());

		log = new JTextArea(30,20);
		log.setMargin(new Insets(5, 5, 5, 5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);
		add(logScrollPane, BorderLayout.CENTER);

	}

}
