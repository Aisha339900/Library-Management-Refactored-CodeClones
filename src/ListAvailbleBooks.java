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
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.stream.IntStream;

/**
 *A public class
 */
public class ListAvailbleBooks extends JInternalFrame {
	/***************************************************************************
	 ***      declaration of the private variables used in the program       ***
	 ***************************************************************************/

	private static final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Library";
	public ListAvailbleBooks() {
		super("Available Books", false, true, false, true);
		setFrameIcon(FRAME_ICON);

		Container container = getContentPane();
		container.add(buildHeaderPanel(), BorderLayout.NORTH);
		container.add(buildContentPanel(), BorderLayout.CENTER);

		setVisible(true);
		pack();
	}

	private JPanel buildHeaderPanel() {
		JLabel headerLabel = new JLabel("THE LIST FOR THE AVAILABLE BOOKS");
		headerLabel.setFont(HEADER_FONT);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.add(headerLabel);
		return panel;
	}

	private JPanel buildContentPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(createPrintButton(), BorderLayout.NORTH);
		panel.add(buildTableScrollPane(), BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createTitledBorder("Available Books:"));
		return panel;
	}

	private JScrollPane buildTableScrollPane() {
		JTable configuredTable = createConfiguredTable();
		return new JScrollPane(configuredTable);
	}

	private JTable createConfiguredTable() {
		JTable configuredTable = new JTable(buildTableModel());
		configuredTable.setPreferredScrollableViewportSize(TABLE_SIZE);
		configuredTable.setFont(TABLE_FONT);
		applyColumnWidths(configuredTable.getColumnModel());
		return configuredTable;
	}

	private ResultSetTableModel buildTableModel() {
		try {
			ResultSetTableModel model = new ResultSetTableModel(JDBC_DRIVER, DATABASE_URL, USER_NAME, PASSWORD, DEFAULT_QUERY);
			model.setQuery(DEFAULT_QUERY);
			return model;
		}
		catch (ClassNotFoundException | SQLException exception) {
			throw new IllegalStateException("Unable to load the available books list", exception);
		}
	}

	private void applyColumnWidths(TableColumnModel columnModel) {
		IntStream.range(0, Math.min(COLUMN_WIDTHS.length, columnModel.getColumnCount()))
		        .forEach(index -> columnModel.getColumn(index).setPreferredWidth(COLUMN_WIDTHS[index]));
	}

	private JButton createPrintButton() {
		JButton button = new JButton("print the books", PRINT_ICON);
		button.setToolTipText("Print");
		button.setFont(BUTTON_FONT);
		button.addActionListener(event -> runAsyncPrint());
		return button;
	}

	private void runAsyncPrint() {
		runInBackground(this::performPrint, "ListAvailableBooksPrinter");
	}

	private void runInBackground(Runnable task, String threadName) {
		Thread runner = new Thread(task, threadName);
		runner.start();
	}

	private void performPrint() {
		try {
			PrinterJob prnJob = PrinterJob.getPrinterJob();
			prnJob.setPrintable(new PrintingBooks(DEFAULT_QUERY));
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