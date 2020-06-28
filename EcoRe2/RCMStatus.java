import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class RCMStatus extends JFrame{

	JTable t1;
	JButton b1;

	RCMStatus() {
		super("RCM Details");
		setSize(1200, 650);
		setLocation(200, 200);
		refresh();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void refresh() {
		try {
			Connect c1 = new Connect();
			String s1 = "select * from rcmlist";
			ResultSet rs = c1.s.executeQuery(s1);
			String[] x = { "RCM ID", "RCM Location", "Total Weight", "Current Weight", "Total Cash", "Current Cash",
					"Last Empty", "Status" };
			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			String y[][] = new String[count][8];
			int i = 0, j = 0;
			while (rs.next()) {
				y[i][j++] = rs.getString("rcmid");
				y[i][j++] = rs.getString("rcmlocation");
				y[i][j++] = rs.getString("totalWeight");
				y[i][j++] = rs.getString("currentWeight");
				y[i][j++] = rs.getString("totalCash");
				y[i][j++] = rs.getString("currentCash");
				y[i][j++] = rs.getString("lastEmpty");
				y[i][j++] = rs.getString("status");
				i++;
				j = 0;
			}
			t1 = new JTable(y, x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JScrollPane sp = new JScrollPane(t1);
		add(sp);
		t1.setFont(new Font("monospaced", Font.BOLD, 15));
	}

	public static void main(String[] args) {
		new RCMStatus().setVisible(true);
	}

}
