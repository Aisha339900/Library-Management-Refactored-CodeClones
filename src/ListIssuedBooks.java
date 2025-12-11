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
public class ListIssuedBooks extends AbstractResultSetListFrame {

	private static final String DEFAULT_QUERY = "SELECT B.BookID, BK.Title, B.MemberID," +
	        " B.DayOfBorrowed, B.DayOfReturn, M.RegNo, M.Name, M.Email" +
	        " FROM Borrow AS B, Books AS BK, Members AS M " +
	        "WHERE (B.BookID=BK.BookID) and (B.MemberID=M.MemberID)";
	private static final Dimension TABLE_SIZE = new Dimension(990, 200);
	private static final int[] COLUMN_WIDTHS = {15, 100, 15, 30, 30, 10, 80, 100};

	public ListIssuedBooks() {
		super("Borrowed Books", "THE LIST FOR THE BORROWED BOOKS", "Borrowed books:", "print the books",
		        TABLE_SIZE, COLUMN_WIDTHS, PrintingBorrow::new);
		initializeFrame();
	}

	@Override
	protected String getQuery() {
		return DEFAULT_QUERY;
	}
}

