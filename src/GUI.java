import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener{

	JPanel jPanel;
	JTextArea barcodeTextArea;
	JButton barcodeAreaClearButton;
	
	public GUI(){
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
	}

	//set barcode Area
	public void setBarcodeArea(String barcodeValue){
		
		barcodeTextArea.setText(barcodeTextArea.getText() + "\n" + barcodeValue);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		//현재 설정된 바코드 값들의 초기화. 
		if(event.getSource() == barcodeAreaClearButton){
			barcodeTextArea.setText("");
		}
		
	}
}
