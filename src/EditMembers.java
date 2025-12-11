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
public class EditMembers extends JInternalFrame {
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
	private JPanel editPanel = new JPanel();
	private JPanel editInformationPanel = new JPanel();
	private JPanel editInformationLabelPanel = new JPanel();
	private JPanel editInformationTextFieldPanel = new JPanel();
	private JPanel editButtonPanel = new JPanel();
	private JLabel editLabel = new JLabel("MemberID: ");
	private JTextField editTextField = new JTextField(25);
	private JButton editButton = new JButton("Edit");
	private JPanel informationPanel = new JPanel();
	private JPanel informationLabelPanel = new JPanel();
	private JLabel[] informationLabel = new JLabel[7];
	private String[] informaionString = {" Reg. No: ", " The Password: ", " Rewrite the password: ",
	        " The Name: ", " E-Mail: ", " Major: ", " Valid Upto: "};
	private JPanel informationTextFieldPanel = new JPanel();
	private JTextField[] informationTextField = new JTextField[4];
	private JPasswordField[] informationPasswordField = new JPasswordField[2];
	private JPanel updateInformationButtonPanel = new JPanel();
	private JButton updateInformationButton = new JButton("Update the Information");
	private JPanel southPanel = new JPanel();
	private JButton OKButton = new JButton("Exit");
	private Members member;
	private String[] data;
	private DateButton expiry_date;

	public EditMembers() {
		super("Edit Members", false, true, false, true);
		setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/Edit16.gif")));
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
		configureEditPanel();
		configureInformationPanel();
		centerPanel.add("North", editPanel);
		centerPanel.add("Center", informationPanel);
	}

	private void setupSouthPanel() {
		southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		OKButton.setFont(BUTTON_FONT);
		southPanel.add(OKButton);
		southPanel.setBorder(BorderFactory.createEtchedBorder());
	}

	private void configureEditPanel() {
		editPanel.setLayout(new BorderLayout());
		editPanel.setBorder(BorderFactory.createTitledBorder("MemberID: "));
		configureEditInformationPanel();
		configureEditButtonPanel();
	}

	private void configureEditInformationPanel() {
		editInformationPanel.setLayout(new BorderLayout());
		editInformationLabelPanel.setLayout(new GridLayout(1, 1, 1, 1));
		editInformationLabelPanel.add(editLabel);
		editLabel.setFont(LABEL_FONT);
		editInformationPanel.add("West", editInformationLabelPanel);

		editInformationTextFieldPanel.setLayout(new GridLayout(1, 1, 1, 1));
		editInformationTextFieldPanel.add(editTextField);
		editTextField.setFont(TEXT_FONT);
		editTextField.addKeyListener(new keyListener());
		editInformationPanel.add("East", editInformationTextFieldPanel);
		editPanel.add("North", editInformationPanel);
	}

	private void configureEditButtonPanel() {
		editButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		editButton.setFont(BUTTON_FONT);
		editButtonPanel.add(editButton);
		editPanel.add("Center", editButtonPanel);
	}

	private void configureInformationPanel() {
		informationPanel.setLayout(new BorderLayout());
		informationPanel.setBorder(BorderFactory.createTitledBorder("Edit a member: "));
		informationLabelPanel.setLayout(new GridLayout(7, 1, 1, 1));
		informationTextFieldPanel.setLayout(new GridLayout(7, 1, 1, 1));
		configureInformationLabelPanel();
		configureInformationFieldPanel();
		informationPanel.add("West", informationLabelPanel);
		informationPanel.add("East", informationTextFieldPanel);
		configureUpdateButtonPanel();
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
				informationTextField[i].setEnabled(false);
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

	private void configureUpdateButtonPanel() {
		updateInformationButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		updateInformationButton.setFont(BUTTON_FONT);
		updateInformationButtonPanel.add(updateInformationButton);
		informationPanel.add("South", updateInformationButtonPanel);
	}

	private void configureActionListeners() {
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isEditCorrect()) {
					runInBackground(new Runnable() {
						public void run() {
							loadMemberInformation();
						}
					}, "EditMembersLoad");
				}
				else {
					JOptionPane.showMessageDialog(null, "Please, write the MemberID", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		updateInformationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (isCorrect()) {
					if (isPasswordCorrect()) {
						runInBackground(new Runnable() {
							public void run() {
								updateMemberInformation();
							}
						}, "EditMembersUpdate");
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

	private void loadMemberInformation() {
		member = new Members();
		member.connection("SELECT * FROM Members WHERE MemberID = " + editTextField.getText());
		int regNo = member.getRegNo();
		if (regNo > 0) {
			populateMemberFields();
		}
		else {
			JOptionPane.showMessageDialog(null, "Please, write a correct MemberID", "Error", JOptionPane.ERROR_MESSAGE);
			editTextField.setText(null);
			clearTextField();
		}
	}

	private void populateMemberFields() {
		informationTextField[0].setText(member.getRegNo() + "");
		informationTextField[1].setText(member.getName());
		informationTextField[2].setText(member.getEmail());
		informationTextField[3].setText(member.getMajor());
		expiry_date.setDate(member.getValidUpto());
		informationPasswordField[0].setText(member.getPassword());
		informationPasswordField[1].setText(member.getPassword());
	}

	private void updateMemberInformation() {
		Date expiryDate = expiry_date.getDate();
		Date presentDate = new Date();
		if (presentDate.before(expiryDate)) {
			member = new Members();
			member.update("UPDATE Members SET RegNo = " + data[0] + ", Password = '" + data[1] + "', Name = '" +
			        data[2] + "', EMail = '" + data[3] + "', Major = '" + data[4] + "', ValidUpto = '" +
			        data[5] + "' WHERE MemberID = " + editTextField.getText());
			dispose();
		}
		else {
			JOptionPane.showMessageDialog(null, "Expiry Date is invalid", "Warning", JOptionPane.WARNING_MESSAGE);
		}
	}

	//for checking the password
	public boolean isPasswordCorrect() {
		if (Arrays.equals(informationPasswordField[0].getPassword(), informationPasswordField[1].getPassword())) {
			data[1] = new String(informationPasswordField[0].getPassword());
			return true;
		}
		return false;
	}

	//for checking the information from the text field
	public boolean isCorrect() {
		data = new String[6];
		for (int i = 0; i < informationLabel.length; i++) {
			if (i == 0) {
				if (!informationTextField[i].getText().equals("")) {
					data[i] = informationTextField[i].getText();
				}
				else {
					return false;
				}
			}
			if (i == 1 || i == 2) {
				if (informationPasswordField[i - 1].getPassword().length == 0) {
					return false;
				}
			}
			if (i == 3 || i == 4 || i == 5) {
				if (!informationTextField[i - 2].getText().equals("")) {
					data[i - 1] = informationTextField[i - 2].getText();
				}
				else {
					return false;
				}
			}
			if (i == 6) {
				if (!expiry_date.getText().equals("")) {
					data[i - 1] = expiry_date.getText();
				}
				else {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isEditCorrect() {
		return !editTextField.getText().equals("");
	}

	public void clearTextField() {
		editTextField.setText(null);
		for (int i = 0; i < informationLabel.length; i++) {
			if (i == 0) {
				if (informationTextField[i] != null) {
					informationTextField[i].setText(null);
				}
			}
			if (i == 1 || i == 2) {
				if (informationPasswordField[i - 1] != null) {
					informationPasswordField[i - 1].setText(null);
				}
			}
			if (i == 3 || i == 4 || i == 5) {
				if (informationTextField[i - 2] != null) {
					informationTextField[i - 2].setText(null);
				}
			}
		}
	}

	class keyListener extends KeyAdapter {
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (!(Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_ENTER) ||
			        (c == KeyEvent.VK_DELETE))) {
				getToolkit().beep();
				JOptionPane.showMessageDialog(null, "This Field Only Accept Integer Number", "WARNING", JOptionPane.DEFAULT_OPTION);
				e.consume();
			}
		}
	}//inner class closed
}
            }
    }//inner class closed

}