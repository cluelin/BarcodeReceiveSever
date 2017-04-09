import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI extends JFrame implements ActionListener {

	static GUI GUIinstance = null;

	JPanel jPanel;
	JTextArea barcodeTextArea;
	JButton barcodeAreaClearButton;

	private GUI() {
		super("BarCode Receiver");

		barcodeTextArea = new JTextArea();
		barcodeTextArea.setEditable(false);
		barcodeTextArea.setFont(barcodeTextArea.getFont().deriveFont(20f));

		JScrollPane scrollPane = new JScrollPane(barcodeTextArea);
		add(scrollPane);

		barcodeAreaClearButton = new JButton("지우기");
		barcodeAreaClearButton.addActionListener(this);

		jPanel = new JPanel();
		jPanel.add(barcodeAreaClearButton);

		add(jPanel, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 400);
		this.setVisible(true);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	public static GUI getInstance() {

		if (GUIinstance == null) {
			GUIinstance = new GUI();
		}

		return GUIinstance;

	}

	// set barcode Area
	public void setBarcodeArea(String barcodeValue) {

		// barcodeTextArea.setText(barcodeTextArea.getText() + "\n" +
		// barcodeValue);
		barcodeTextArea.append(barcodeValue + "\n");

		// File file = new File();
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		// 현재 설정된 바코드 값들의 초기화.
		if (event.getSource() == barcodeAreaClearButton) {
			barcodeTextArea.setText("");
		}

	}
}
