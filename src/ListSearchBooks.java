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
public class ListSearchBooks extends AbstractResultSetListFrame {

	private static final Dimension TABLE_SIZE = new Dimension(990, 200);
	private static final int[] COLUMN_WIDTHS = {20, 100, 150, 50, 70, 40, 40, 40, 80, 70, 30, 30, 30};
	private final String query;

	public ListSearchBooks(String query) {
		super("Searched Books", "THE LIST FOR THE SEARCHED BOOKS", "Books:", "print the books",
		        TABLE_SIZE, COLUMN_WIDTHS, PrintingBooks::new);
		this.query = query;
		initializeFrame();
	}

	@Override
	protected String getQuery() {
		return query;
	}
}