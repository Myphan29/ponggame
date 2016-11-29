package vn.vanlanguni.ponggame;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class SecondWindow extends JDialog{
	public JTextField txtPlayer1;
	private JTextField txtPlayer2;
	//Xem khai bao MyDialogResult o cuoi class nay
	public MyDialogResult dialogResult;
	public String sPlayer1, sPlayer2;
	
	public SecondWindow() {
		setPreferredSize(new Dimension(300, 200));
		setTitle("Second Window");
		getContentPane().setLayout(null);
		setModal(true);
		
		dialogResult = MyDialogResult.DEFAULT;
		txtPlayer1 = new JTextField(10);
		txtPlayer2 = new JTextField(10);
		getContentPane().add(txtPlayer1);
		getContentPane().add(txtPlayer2);
		txtPlayer1.setBounds(90, 26, 100, 20);
		txtPlayer2.setBounds(90, 66, 100, 20);
		
		
		JLabel lblPlayer_1 = new JLabel("Player 1");
		lblPlayer_1.setBounds(10, 29, 71, 14);
		getContentPane().add(lblPlayer_1);
		
		JLabel lblPlayer_2 = new JLabel("Player 2");
		lblPlayer_2.setBounds(10, 69, 71, 14);
		getContentPane().add(lblPlayer_2);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialogResult = MyDialogResult.YES;
				setVisible(false);
				//String sPlayer1, sPlayer2;
//				sPlayer1 = txtPlayer1.getText();
//				sPlayer2 = txtPlayer2.getText();
			}
		});
		btnSave.setBounds(44, 114, 89, 23);
		getContentPane().add(btnSave);
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dialogResult = MyDialogResult.CANCEL;
				setVisible(false);
			}
		});
		btnCancel.setBounds(154, 114, 89, 23);
		getContentPane().add(btnCancel);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				int result = JOptionPane.showConfirmDialog(SecondWindow.this, "Exit?");
				if(result == JOptionPane.YES_OPTION){
					setVisible(false);
				}				
			}
		});
	}
	
	public Settings getSetings(){
		Settings st = new Settings();
		st.setPlayer1(txtPlayer1.getText());
		st.setPlayer2(txtPlayer2.getText());
		st.setBallNumber(1);
		return st;
	}
}


