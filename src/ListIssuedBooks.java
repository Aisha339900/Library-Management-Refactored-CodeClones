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
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;

/**
 *A public class
 */
public class ListIssuedBooks extends JInternalFrame {

	/***************************************************************************
	 ***      declaration of the private variables used in the program       ***
	 ***************************************************************************/

	private static final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Library";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "nielit";
	private static final String DEFAULT_QUERY = "SELECT B.BookID, BK.Title, B.MemberID," +
	        " B.DayOfBorrowed, B.DayOfReturn, M.RegNo, M.Name, M.Email" +
	        " FROM Borrow AS B, Books AS BK, Members AS M " +
	        "WHERE (B.BookID=BK.BookID) and (B.MemberID=M.MemberID)";
	private static final Font TABLE_FONT = new Font("Tahoma", Font.PLAIN, 12);
	private static final Font HEADER_FONT = new Font("Tahoma", Font.BOLD, 14);
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.PLAIN, 12);
	private static final ImageIcon FRAME_ICON = new ImageIcon(ClassLoader.getSystemResource("images/List16.gif"));
	private static final ImageIcon PRINT_ICON = new ImageIcon(ClassLoader.getSystemResource("images/Print16.gif"));
	private static final Dimension TABLE_SIZE = new Dimension(990, 200);
	private static final int[] COLUMN_WIDTHS = {15, 100, 15, 30, 30, 10, 80, 100};

	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JLabel label = new JLabel("THE LIST FOR THE BORROWED BOOKS");
	private JButton printButton;
	private JTable table;
	private JScrollPane scrollPane;
	private ResultSetTableModel tableModel;

	public ListIssuedBooks() {
		super("Borrowed Books", false, true, false, true);
		setFrameIcon(FRAME_ICON);

		Container cp = getContentPane();
		tableModel = createTableModel();
		table = createConfiguredTable(tableModel);
		scrollPane = new JScrollPane(table);
		printButton = createPrintButton();

		configureNorthPanel();
		configureCenterPanel();

		cp.add("North", northPanel);
		cp.add("Center", centerPanel);

		setVisible(true);
		pack();
	}

	private ResultSetTableModel createTableModel() {
		try {
			ResultSetTableModel model = new ResultSetTableModel(JDBC_DRIVER, DATABASE_URL, USER_NAME, PASSWORD, DEFAULT_QUERY);
			model.setQuery(DEFAULT_QUERY);
			return model;
		}
		catch (ClassNotFoundException | SQLException exception) {
			JOptionPane.showMessageDialog(null, "Cannot retrieve data from tables," + exception.getMessage());
			throw new IllegalStateException("Unable to load issued books", exception);
		}
	}

	private JTable createConfiguredTable(ResultSetTableModel model) {
		JTable configuredTable = new JTable(model);
		configuredTable.setPreferredScrollableViewportSize(TABLE_SIZE);
		configuredTable.setFont(TABLE_FONT);
		configureColumnWidths(configuredTable.getColumnModel());
		return configuredTable;
	}

	private void configureColumnWidths(TableColumnModel columnModel) {
		int bound = Math.min(COLUMN_WIDTHS.length, columnModel.getColumnCount());
		for (int i = 0; i < bound; i++) {
			columnModel.getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
		}
	}

	private void configureNorthPanel() {
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		label.setFont(HEADER_FONT);
		northPanel.add(label);
	}

	private void configureCenterPanel() {
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(printButton, BorderLayout.NORTH);
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		centerPanel.setBorder(BorderFactory.createTitledBorder("Borrowed books:"));
	}

	private JButton createPrintButton() {
		JButton button = new JButton("print the books", PRINT_ICON);
		button.setToolTipText("Print");
		button.setFont(BUTTON_FONT);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startPrintJob();
			}
		});
		return button;
	}

	private void startPrintJob() {
		Thread runner = new Thread(new Runnable() {
			@Override
			public void run() {
				performPrint();
			}
		}, "ListIssuedBooksPrinter");
		runner.start();
	}

	private void performPrint() {
		try {
			PrinterJob prnJob = PrinterJob.getPrinterJob();
			prnJob.setPrintable(new PrintingBorrow(DEFAULT_QUERY));
			if (!prnJob.printDialog()) {
				return;
			}
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			prnJob.print();
		}
		catch (PrinterException ex) {
			System.out.println("Printing error: " + ex.toString());
		}
		finally {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
}

