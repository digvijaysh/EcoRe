import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class RCMOperations extends JFrame implements ActionListener {
	JLabel l1, l2, l3;
	JTextField t1;
	JButton b1, b2, b3;

	public RCMOperations() {
		setFont(new Font("System", Font.BOLD, 22));
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int x = fm.stringWidth("RCMOperations");
		int y = fm.stringWidth(" ");
		int z = getWidth() - x;
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "RCMOperations");
		setSize(500, 400);
		setLocation(500, 200);
		setLayout(null);

		l1 = new JLabel("RCM ID");
		l1.setFont(new Font("Raleway", Font.BOLD, 16));
		l1.setBounds(75, 75, 100, 40);
		add(l1);

		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 16));
		t1.setBounds(250, 75, 150, 40);
		add(t1);

		b1 = new JButton("Empty RCM");
		b1.setFont(new Font("Raleway", Font.BOLD, 16));
		b1.setBounds(25, 200, 125, 40);
		add(b1);
		b1.addActionListener(this);

		b2 = new JButton("Refill RCM");
		b2.setFont(new Font("Raleway", Font.BOLD, 16));
		b2.setBounds(175, 200, 125, 40);
		add(b2);
		b2.addActionListener(this);

		b3 = new JButton("Remove RCM");
		b3.setFont(new Font("Raleway", Font.BOLD, 16));
		b3.setBounds(325, 200, 140, 40);
		add(b3);
		b3.addActionListener(this);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void emptyRcm(int id) {
		try {
			Connect con = new Connect();
			String q1 = "select * from rcmlist where rcmid =" + id + ";";
			ResultSet rs = con.s.executeQuery(q1);
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Enter a valid RCM ID");
			} else {
				Date date = new java.sql.Date(new java.util.Date().getTime());
				String q = "update rcmlist set currentWeight=0,lastEmpty ='" + date + "' where rcmid =" + id + ";";
				con.s.executeUpdate(q);
				String q2 = "update emptyCount set count = count+1 where rcmid=" + id;
				con.s.executeUpdate(q2);
				con.c.close();
				JOptionPane.showMessageDialog(null, "RCM " + id + " Empty");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void refillRcm(int id) {
		try {
			Connect con = new Connect();
			String q1 = "select * from rcmlist where rcmid =" + id + ";";
			ResultSet rs = con.s.executeQuery(q1);
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Enter a valid RCM ID");
			} else {
				String q = "update rcmlist set currentCash=0 where rcmid =" + id + ";";
				con.s.executeUpdate(q);
				con.c.close();
				JOptionPane.showMessageDialog(null, "Refill successfull");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void removeRcm(int id) {
		try {
			Connect con = new Connect();
			String q1 = "select * from rcmlist where rcmid =" + id + ";";
			ResultSet rs = con.s.executeQuery(q1);
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Enter a valid RCM ID");
			} else {
				String q = "update rcmlist set status='inactive' where rcmid =" + id;
				con.s.executeUpdate(q);
				con.c.close();
				JOptionPane.showMessageDialog(null, "Rcm removed successfully");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1 && !t1.getText().equals("")) {
			int id = Integer.parseInt(t1.getText());
			emptyRcm(id);
		} else if (ae.getSource() == b2 && !t1.getText().equals("")) {
			int id = Integer.parseInt(t1.getText());
			refillRcm(id);
		} else if (ae.getSource() == b3 && !t1.getText().equals("")) {
			int id = Integer.parseInt(t1.getText());
			removeRcm(id);
		}
	}

	public static void main(String[] args) {
		new RCMOperations().setVisible(true);
	}
}
