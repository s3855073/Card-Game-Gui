/**
 * The SummaryPanel class contains all of the building blocks Summary Table in the
 * Centre Panel. 
 * It sets the layout of the panel and builds the table for the player information
 * to be stored during the games.
 * 
 * @author Vincent Tso
 * @student_id s3855073
 */

package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.interfaces.Player;

@SuppressWarnings("serial")
public class SummaryPanel extends JPanel {
	private JTable summaryTable;
	private DefaultTableModel tableModel;

	private String[] columnNames = {"ID", "Name", "Points", "Bet", "Result", "Outcome"};
	
	private final int START_ROW_COUNT = 0;
	private final int ID_COLUMN = 0;
	
	public SummaryPanel(AppFrame appFrame) {
		setBorder(BorderFactory.createTitledBorder("Summary Panel"));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(appFrame.getCentrePanel().getWidth(),
				appFrame.getCentrePanel().getHeight() / 3)); // Set height to third of Centre Panel height.
	}
	
	/**
	 * populate builds the components on top of the Summary Panel
	 * for the Card Game Application GUI. We use a DefaultTableModel
	 * object to add, remove and update the player rows as we
	 * don't need to state how many rows we want to have. This allows
	 * more dynamic functionality for game testing.
	 */
	public void populate() {
		tableModel = new DefaultTableModel(columnNames, START_ROW_COUNT);
		
		summaryTable = new JTable(tableModel) {
			public boolean editCellAt(int row, int column, EventObject e) {
				return false;
			}
		};
		
		summaryTable.getTableHeader().setReorderingAllowed(false);
		summaryTable.setGridColor(Color.LIGHT_GRAY);
		summaryTable.setFillsViewportHeight(true); // Set the height of the table to fill
		// the Summary Panel.

		add(new JScrollPane(summaryTable), BorderLayout.CENTER);
	}
	
	public void addPlayerRow(Player player) {
		Object[] newRow = {
				player.getPlayerId(),
				player.getPlayerName(),
				player.getPoints(),
				player.getBet(),
				player.getResult(),
				"NA"
		};
		
		tableModel.addRow(newRow);
	}
	
	public void removePlayerRow(Player player) {
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (tableModel.getValueAt(i, ID_COLUMN).equals(player.getPlayerId()))
				tableModel.removeRow(i); // Find player row and remove.
		}
	}
	
	public void setPlayerInfo(Player player, int column, Object obj) {
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			if (tableModel.getValueAt(i, ID_COLUMN).equals(player.getPlayerId()))
				tableModel.setValueAt(obj, i, column);
		}
	}
}
