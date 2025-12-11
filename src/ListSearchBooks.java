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
public class ListSearchBooks extends JInternalFrame {
	/***************************************************************************
	 ***      declaration of the private variables used in the program       ***
	 ***************************************************************************/

	private static final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Library";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "nielit";
	private static final Font TABLE_FONT = new Font("Tahoma", Font.PLAIN, 12);
	private static final Font HEADER_FONT = new Font("Tahoma", Font.BOLD, 14);
	private static final Font BUTTON_FONT = new Font("Tahoma", Font.PLAIN, 12);
	private static final ImageIcon FRAME_ICON = new ImageIcon(ClassLoader.getSystemResource("images/List16.gif"));
	private static final ImageIcon PRINT_ICON = new ImageIcon(ClassLoader.getSystemResource("images/Print16.gif"));
	private static final Dimension TABLE_SIZE = new Dimension(990, 200);
	private static final int[] COLUMN_WIDTHS = {20, 100, 150, 50, 70, 40, 40, 40, 80, 70, 30, 30, 30};

	//for creating the North Panel
	private JPanel northPanel = new JPanel();
	//for creating the Center Panel
	private JPanel centerPanel = new JPanel();
	//for creating the label
	private JLabel label = new JLabel("THE LIST FOR THE SEARCHED BOOKS");
	//for creating the button
	private JButton printButton;
	//for creating the table
	private JTable table;
	//for creating the JScrollPane
	private JScrollPane scrollPane;

	//for creating an object for the ResultSetTableModel class
	private ResultSetTableModel tableModel;

	//constructor of listSearchBooks
	public ListSearchBooks(String query) {
		//for setting the title for the internal frame
		super("Searched Books", false, true, false, true);
		//for setting the icon
		setFrameIcon(FRAME_ICON);
		//for getting the graphical user interface components display area
		Container cp = getContentPane();

		tableModel = createTableModel(query);
		table = createConfiguredTable(tableModel);
		scrollPane = new JScrollPane(table);
		printButton = createPrintButton(query);

		configureNorthPanel();
		configureCenterPanel();

		cp.add("North", northPanel);
		cp.add("Center", centerPanel);

		//for setting the visible to true
		setVisible(true);
		//to show the frame
		pack();
	}

	private ResultSetTableModel createTableModel(String query) {
		try {
			ResultSetTableModel model = new ResultSetTableModel(JDBC_DRIVER, DATABASE_URL, USER_NAME, PASSWORD, query);
			model.setQuery(query);
			return model;
		}
		catch (ClassNotFoundException | SQLException exception) {
			throw new IllegalStateException("Unable to load the searched books list", exception);
		}
	}

	private JTable createConfiguredTable(ResultSetTableModel model) {
		JTable table = new JTable(model);
		table.setPreferredScrollableViewportSize(TABLE_SIZE);
		table.setFont(TABLE_FONT);
		configureColumnWidths(table.getColumnModel());
		return table;
	}

	private void configureColumnWidths(TableColumnModel columnModel) {
		int length = Math.min(COLUMN_WIDTHS.length, columnModel.getColumnCount());
		for (int i = 0; i < length; i++) {
			columnModel.getColumn(i).setPreferredWidth(COLUMN_WIDTHS[i]);
		}
	}

	private void configureNorthPanel() {
		label.setFont(HEADER_FONT);
		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		northPanel.add(label);
	}

	private void configureCenterPanel() {
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(printButton, BorderLayout.NORTH);
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		centerPanel.setBorder(BorderFactory.createTitledBorder("Books:"));
	}

	private JButton createPrintButton(String query) {
		JButton button = new JButton("print the books", PRINT_ICON);
		button.setToolTipText("Print");
		button.setFont(BUTTON_FONT);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startPrintJob(query);
			}
		});
		return button;
	}

	private void startPrintJob(String query) {
		Thread runner = new Thread(new Runnable() {
			@Override
			public void run() {
				performPrint(query);
			}
		}, "ListSearchBooksPrinter");
		runner.start();
	}

	private void performPrint(String query) {
		try {
			PrinterJob prnJob = PrinterJob.getPrinterJob();
			prnJob.setPrintable(new PrintingBooks(query));
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