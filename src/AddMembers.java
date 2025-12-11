/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ganesh Sharma
 */
//import the packages for using the classes in them into the program

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Date;

/**
 *A public class
 */
public class AddMembers extends JInternalFrame {
	/***************************************************************************
	 ***      declaration of the private variables used in the program       ***
	 ***************************************************************************/

	private static final Font HEADER_FONT = new Font("Tahoma", Font.BOLD, 14);
	private static final Font LABEL_FONT = new Font("Tahoma", Font.BOLD, 11);
	private static final Font TEXT_FONT = new Font("Tahoma", Font.PLAIN, 11);
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.BOLD, 11);

	private JPanel northPanel = new JPanel();
	private JLabel northLabel = new JLabel("MEMBER INFORMATION");
	private JPanel centerPanel = new JPanel();
	private JPanel informationLabelPanel = new JPanel();
	private JLabel[] informationLabel = new JLabel[7];
	private String[] informaionString = {" Reg. No: ", " The Password: ", " Rewrite the password: ",
	        " The Name: ", " E-Mail: ", " Major: ", " Valid Upto: "};
	private JPanel informationTextFieldPanel = new JPanel();
	private JTextField[] informationTextField = new JTextField[4];
	private JPasswordField[] informationPasswordField = new JPasswordField[2];
	private JPanel insertInformationButtonPanel = new JPanel();
	private JButton insertInformationButton = new JButton("Insert the Information");
	private JPanel southPanel = new JPanel();
	private JButton OKButton = new JButton("Exit");
	private Members member;
	private String[] data;
	private DateButton expiry_date;

	//for checking the password
	public boolean isPasswordCorrect() {
        if (Arrays.equals(informationPasswordField[0].getPassword(),informationPasswordField[1].getPassword()))
            data[1] = new String(informationPasswordField[1].getPassword());
        else if(!Arrays.equals(informationPasswordField[0].getPassword(),informationPasswordField[1].getPassword()))
            return false;
        return true;
		/*if (informationPasswordField[0].getText().equals(informationPasswordField[1].getText()))
			data[1] = informationPasswordField[1].getText();
		else if (!informationPasswordField[0].getText().equals(informationPasswordField[1].getText()))
			return false;

		return true;*/
	}

	//for checking the information from the text field
	public boolean isCorrect() {
		data = new String[6];
		for (int i = 0; i < informationLabel.length; i++) {
			if (i == 0) {
				if (!informationTextField[i].getText().equals("")) {
					data[i] = informationTextField[i].getText();
				}
				else
					return false;
			}
			if (i == 1 || i == 2) {
				//if (informationPasswordField[i - 1].getText().equals(""))
                if (informationPasswordField[i-1].getPassword().length==0)
					return false;
			}
			if (i == 3 || i == 4 || i == 5) {
				if (!informationTextField[i - 2].getText().equals("")) {
					data[i - 1] = informationTextField[i - 2].getText();
				}
				else
					return false;
			}
            if(i==6)
            {
                if(!expiry_date.getText().equals(""))
                {
                data[i-1]=expiry_date.getText();
                }
                else
                    return false;
            }
		}
		return true;
	}

	//for setting the array of JTextField & JPasswordField to null
	public void clearTextField() {
		for (int i = 0; i < informationLabel.length; i++) {
			if (i == 0)
				informationTextField[i].setText(null);
			if (i == 1 || i == 2)
				informationPasswordField[i - 1].setText(null);
                //informationPasswordField[i - 1].setPassword("");
			if (i == 3 || i == 4 || i == 5)
				informationTextField[i - 2].setText(null);
		}
	}
	public AddMembers() {
		super("Add Members", false, true, false, true);
		setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Add16.gif")));
		expiry_date = new DateButton();
		expiry_date.setForeground(Color.red);

		Container cp = getContentPane();
		setupNorthPanel();
		setupCenterPanel();
		setupSouthPanel();

		cp.add("North", northPanel);
		cp.add("Center", centerPanel);
		cp.add("South", southPanel);

		configureActionListeners();

		setVisible(true);
		pack();
	}

	private void setupNorthPanel() {
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northLabel.setFont(HEADER_FONT);
		northPanel.add(northLabel);
	}

	private void setupCenterPanel() {
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createTitledBorder("Add a new member:"));
		informationLabelPanel.setLayout(new GridLayout(7, 1, 1, 1));
		informationTextFieldPanel.setLayout(new GridLayout(7, 1, 1, 1));
		configureInformationLabelPanel();
		configureInformationFieldPanel();
		centerPanel.add("West", informationLabelPanel);
		centerPanel.add("East", informationTextFieldPanel);
		configureInsertButtonPanel();
	}

	private void configureInformationLabelPanel() {
		for (int i = 0; i < informationLabel.length; i++) {
			informationLabelPanel.add(informationLabel[i] = new JLabel(informaionString[i]));
			informationLabel[i].setFont(LABEL_FONT);
		}
	}

	private void configureInformationFieldPanel() {
		for (int i = 0; i < informationLabel.length; i++) {
			if (i == 1 || i == 2) {
				informationTextFieldPanel.add(informationPasswordField[i - 1] = new JPasswordField(25));
				informationPasswordField[i - 1].setFont(TEXT_FONT);
			}
			if (i == 0) {
				informationTextFieldPanel.add(informationTextField[i] = new JTextField(25));
				informationTextField[i].setFont(TEXT_FONT);
				informationTextField[i].addKeyListener(new keyListener());
			}
			if (i == 3 || i == 4 || i == 5) {
				informationTextFieldPanel.add(informationTextField[i - 2] = new JTextField(25));
				informationTextField[i - 2].setFont(TEXT_FONT);
			}
			if (i == 6) {
				informationTextFieldPanel.add(expiry_date);
			}
		}
	}

	private void configureInsertButtonPanel() {
		insertInformationButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		insertInformationButton.setFont(BUTTON_FONT);
		insertInformationButtonPanel.add(insertInformationButton);
		centerPanel.add("South", insertInformationButtonPanel);
	}

	private void setupSouthPanel() {
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		OKButton.setFont(BUTTON_FONT);
		southPanel.add(OKButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
	}

	private void configureActionListeners() {
		insertInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isCorrect()) {
					if (isPasswordCorrect()) {
						runInBackground(new Runnable() {
							public void run() {
								insertMemberInformation();
							}
						}, "AddMembersInsert");
					}
					else {
						JOptionPane.showMessageDialog(null, "the passowrd is wrong", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please, complete the information", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		OKButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		});
	}

	private void runInBackground(Runnable task, String threadName) {
		Thread runner = new Thread(task, threadName);
		runner.start();
	}

	private void insertMemberInformation() {
		Date expiryDate = expiry_date.getDate();
		Date presentDate = new Date();
		if (presentDate.before(expiryDate)) {
			member = new Members();
			member.connection("SELECT * FROM Members WHERE RegNo = " + data[0]);
			int regNo = member.getRegNo();
			if (Integer.parseInt(data[0]) != regNo) {
				member.update("INSERT INTO Members (RegNo,Password,Name,EMail,Major,ValidUpto) VALUES (" +
				        data[0] + ", '" + data[1] + "','" + data[2] + "','" +
				        data[3] + "','" + data[4] + "','" + data[5] + "')");
				dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Member is in the Library", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "Expiry Date is invalid", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

    class keyListener extends KeyAdapter {

        public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!(Character.isDigit(c) ||
                        (c == KeyEvent.VK_BACK_SPACE) ||
                        (c == KeyEvent.VK_ENTER) ||
                        (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    JOptionPane.showMessageDialog(null, "This Field Only Accept Integer Number", "WARNING",JOptionPane.DEFAULT_OPTION);
                    e.consume();
                 }
            }
    }//inner class closed

}