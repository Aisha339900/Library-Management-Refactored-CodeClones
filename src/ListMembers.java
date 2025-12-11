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
public class ListMembers extends AbstractResultSetListFrame {

	private static final String DEFAULT_QUERY = "SELECT MemberID, RegNo, Name, EMail," +
	        "Major, ValidUpto FROM Members";
	private static final Dimension TABLE_SIZE = new Dimension(700, 200);
	private static final int[] COLUMN_WIDTHS = {30, 20, 150, 120, 20, 40};

	public ListMembers() {
		super("Members", "THE LIST FOR THE MEMBER", "Members:", "print the members",
		        TABLE_SIZE, COLUMN_WIDTHS, PrintingMembers::new);
		initializeFrame();
	}

	@Override
	protected String getQuery() {
		return DEFAULT_QUERY;
	}
}