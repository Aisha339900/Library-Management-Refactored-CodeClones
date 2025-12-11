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
public class ListBorrowedBooks extends AbstractResultSetListFrame {

	private static final String DEFAULT_QUERY = "select BookID,Subject,Title,Author,Publisher,Copyright,Edition," +
	        "Pages,ISBN, Library from Books where NumberOfBorrowedBooks > 0";
	private static final Dimension TABLE_SIZE = new Dimension(990, 200);
	private static final int[] COLUMN_WIDTHS = {50, 100, 150, 50, 70, 40, 40, 40, 75, 50};

	public ListBorrowedBooks() {
		super("Borrowed Books", "THE LIST FOR THE BORROWED BOOKS", "Borrowed books:", "print the books",
		        TABLE_SIZE, COLUMN_WIDTHS, PrintingBooks::new);
		initializeFrame();
	}

	@Override
	protected String getQuery() {
		return DEFAULT_QUERY;
	}
}