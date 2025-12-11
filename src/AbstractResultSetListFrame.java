import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.Objects;
import java.util.function.Function;

/**
 * Shared template for the tabular list internal frames that render ResultSet-backed tables
 * and expose a print action.
 */
public abstract class AbstractResultSetListFrame extends JInternalFrame {

    private static final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/Library";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "nielit";
    private static final ImageIcon FRAME_ICON = new ImageIcon(ClassLoader.getSystemResource("images/List16.gif"));
    private static final Font TABLE_FONT = new Font("Tahoma", Font.PLAIN, 12);
    private static final Font HEADER_FONT = new Font("Tahoma", Font.BOLD, 14);
    private static final Font BUTTON_FONT = new Font("Tahoma", Font.PLAIN, 12);

    private final String headerText;
    private final String borderTitle;
    private final String printButtonText;
    private final Dimension tableSize;
    private final int[] columnWidths;
    private final Function<String, Printable> printableFactory;

    protected AbstractResultSetListFrame(String title,
            String headerText,
            String borderTitle,
            String printButtonText,
            Dimension tableSize,
            int[] columnWidths,
            Function<String, Printable> printableFactory) {
        super(title, false, true, false, true);
        this.headerText = Objects.requireNonNull(headerText, "headerText");
        this.borderTitle = Objects.requireNonNull(borderTitle, "borderTitle");
        this.printButtonText = Objects.requireNonNull(printButtonText, "printButtonText");
        this.tableSize = Objects.requireNonNull(tableSize, "tableSize");
        this.columnWidths = Objects.requireNonNull(columnWidths, "columnWidths");
        this.printableFactory = Objects.requireNonNull(printableFactory, "printableFactory");
        setFrameIcon(FRAME_ICON);
    }

    protected abstract String getQuery();

    protected final void initializeFrame() {
        buildLayout();
        setVisible(true);
        pack();
    }

    private void buildLayout() {
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(buildHeaderPanel(), BorderLayout.NORTH);
        container.add(buildContentPanel(), BorderLayout.CENTER);
    }

    private JPanel buildHeaderPanel() {
        JLabel label = new JLabel(headerText);
        label.setFont(HEADER_FONT);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(label);
        return panel;
    }

    private JPanel buildContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createPrintButton(), BorderLayout.NORTH);
        panel.add(new JScrollPane(buildTable()), BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createTitledBorder(borderTitle));
        return panel;
    }

    private JTable buildTable() {
        JTable table = new JTable(buildTableModel());
        table.setPreferredScrollableViewportSize(tableSize);
        table.setFont(TABLE_FONT);
        applyColumnWidths(table.getColumnModel());
        return table;
    }

    private ResultSetTableModel buildTableModel() {
        String query = getQuery();
        try {
            ResultSetTableModel model = new ResultSetTableModel(JDBC_DRIVER, DATABASE_URL, USER_NAME, PASSWORD, query);
            model.setQuery(query);
            return model;
        }
        catch (ClassNotFoundException | SQLException exception) {
            throw new IllegalStateException("Unable to load list data", exception);
        }
    }

    private void applyColumnWidths(TableColumnModel columnModel) {
        int bound = Math.min(columnWidths.length, columnModel.getColumnCount());
        for (int i = 0; i < bound; i++) {
            columnModel.getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }

    private JButton createPrintButton() {
        JButton button = new JButton(printButtonText, new ImageIcon(ClassLoader.getSystemResource("images/Print16.gif")));
        button.setToolTipText("Print");
        button.setFont(BUTTON_FONT);
        button.addActionListener(event -> runInBackground(this::performPrint, getTitle() + "Printer"));
        return button;
    }

    private void runInBackground(Runnable runnable, String threadName) {
        Thread runner = new Thread(runnable, threadName);
        runner.start();
    }

    private void performPrint() {
        try {
            PrinterJob prnJob = PrinterJob.getPrinterJob();
            prnJob.setPrintable(printableFactory.apply(getQuery()));
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
