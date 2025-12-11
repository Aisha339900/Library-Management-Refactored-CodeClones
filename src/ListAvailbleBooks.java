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

import java.awt.Dimension;

/**
 *A public class
 */
public class ListAvailbleBooks extends AbstractResultSetListFrame {

	private static final String DEFAULT_QUERY = "SELECT BookID,Subject,Title,Author,Publisher," +
			"Copyright,Edition,Pages,ISBN,Library,ShelfNo FROM Books WHERE Availble = true";
	private static final Dimension TABLE_SIZE = new Dimension(990, 200);
	private static final int[] COLUMN_WIDTHS = {20, 100, 150, 50, 70, 40, 40, 40, 75, 50, 30};

	public ListAvailbleBooks() {
		super("Available Books", "THE LIST FOR THE AVAILABLE BOOKS", "Available Books:", "print the books",
				TABLE_SIZE, COLUMN_WIDTHS, PrintingBooks::new);
		initializeFrame();
	}

	@Override
	protected String getQuery() {
		return DEFAULT_QUERY;
	}
}