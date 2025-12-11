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
public class ListSearchMembers extends AbstractResultSetListFrame {

	private static final Dimension TABLE_SIZE = new Dimension(700, 200);
	private static final int[] COLUMN_WIDTHS = {30, 20, 150, 120, 20, 40};
	private final String query;

	public ListSearchMembers(String query) {
		super("Searched Members", "THE LIST FOR THE SEARCHED MEMBERS", "Members:", "print the members",
		        TABLE_SIZE, COLUMN_WIDTHS, PrintingMembers::new);
		this.query = query;
		initializeFrame();
	}

	@Override
	protected String getQuery() {
		return query;
	}
}