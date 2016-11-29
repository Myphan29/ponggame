package vn.vanlanguni.ponggame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SecondWindow extends JDialog{
	private JTextField txtUsername1;
	private JTextField txtUsername2;
	int NumberBall=1;
	Color  userColorPaddle=Color.RED;
	//Xem khai bao MyDialogResult o cuoi class nay
	public MyDialogResult dialogResult;
	//Add radiobutton to choose ball
			JRadioButton optBall1 = new JRadioButton("Ball 1"), optBall2= new JRadioButton("Ball 2"),
					optBall3 = new JRadioButton("Ball 3");
			ButtonGroup btnGroupBall= new ButtonGroup();
			//Add RadioButton to choose Paddle\
			JRadioButton optPad1 = new JRadioButton("RED"), optPad2= new JRadioButton("PINK"),
					optPad3 = new JRadioButton("BLUE");
			ButtonGroup btnGroupPad= new ButtonGroup();
			
	public SecondWindow() {
		setPreferredSize(new Dimension(400, 400));
		setTitle("Settings");
		getContentPane().setLayout(null);
		setModal(true);
		
		dialogResult = MyDialogResult.DEFAULT;
		txtUsername1 = new JTextField(10);
		txtUsername2 = new JTextField(10);
		getContentPane().add(txtUsername1);
		getContentPane().add(txtUsername2);
		txtUsername1.setBounds(90, 26, 100, 20);
		txtUsername2.setBounds(90, 66, 100, 20);
		
		JLabel lblUser_1 = new JLabel("Username 1");
		lblUser_1.setBounds(10, 29, 71, 14);
		getContentPane().add(lblUser_1);
		
		JLabel lblUser_2 = new JLabel("Username 2");
		lblUser_2.setBounds(10, 69, 71, 14);
		getContentPane().add(lblUser_2);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dialogResult = MyDialogResult.YES;
				setVisible(false);
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
		
		ChooseBall();
		ChoosePaddle();
		//add(ballPicture);
	//	ballPicture.setBounds(0,180,200,100);
		
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

	public void ChooseBall(){
		getContentPane().add(optBall1);
		getContentPane().add(optBall2);
		getContentPane().add(optBall3);
		btnGroupBall.add(optBall1);
		btnGroupBall.add(optBall2);
		btnGroupBall.add(optBall3);
		optBall1.setBounds(10, 150, 60, 25);
		optBall2.setBounds(80, 150, 60, 25);
		optBall3.setBounds(150, 150, 60, 25);
		
		optBall1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NumberBall=1;
			}
		});
		optBall2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NumberBall=2;
			}
		});
		optBall3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				NumberBall=3;
			}
		});
		
		
	}
	public void ChoosePaddle(){
		getContentPane().add(optPad1);
		getContentPane().add(optPad2);
		getContentPane().add(optPad3);
		btnGroupPad.add(optPad1);
		btnGroupPad.add(optPad2);
		btnGroupPad.add(optPad3);
		optPad1.setBounds(10, 250, 60, 25);
		optPad2.setBounds(80, 250, 60, 25);
		optPad3.setBounds(150, 250, 60, 25);
		
		optPad1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				userColorPaddle=Color.red;
			}
		});
		optPad2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				userColorPaddle=Color.pink;
			}
		});
		optPad3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				userColorPaddle=Color.blue;
			}
		});
		
		
	}
	public Settings getSetings(){
		Settings st = new Settings();
		st.setUserName1(txtUsername1.getText());
		st.setUserName2(txtUsername2.getText());
		st.setBallNumber(NumberBall);
		st.setPaddleColor(userColorPaddle);
		
		return st;
	}
}


