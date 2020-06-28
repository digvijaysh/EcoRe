import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ActivateRCM extends JFrame implements ActionListener {
	JLabel j1;
	JTextField t1;
	JButton b1, b2, b3;

	public ActivateRCM() {
		setFont(new Font("System", Font.BOLD, 22));
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int x = fm.stringWidth("ACTIVATE RCM");
		int y = fm.stringWidth(" ");
		int z = getWidth() - x;
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "ACTIVATE RCM");

		j1 = new JLabel("RCM ID");
		j1.setFont(new Font("Raleway", Font.BOLD, 20));
		j1.setBounds(75, 100, 400, 40);
		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 20));
		t1.setBounds(200, 100, 200, 40);
		b1 = new JButton("Activate RCM");
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);
		b1.addActionListener(this);
		b1.setBounds(150, 200, 150, 40);

		add(j1);
		add(t1);
		add(b1);

		setLayout(null);
		setSize(450, 450);
		setLocation(500, 200);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String id = t1.getText();
		if (id.equals("")) {
			JOptionPane.showMessageDialog(null, "Blank field");
		} else {
			if (ae.getSource() == b1) {
				activateRCM();
			}
		}
	}

	public void activateRCM() {
		try {
			Connect con = new Connect();
			int id1 = Integer.parseInt(t1.getText());
			String q1 = "select status from rcmlist where rcmid=" + id1 + "";
			ResultSet rs = con.s.executeQuery(q1);
			if (!rs.next()) {
				JOptionPane.showMessageDialog(null, "Not Valid ID");
			} else if (rs.getString(1).equals("active")) {
				JOptionPane.showMessageDialog(null, "Already Active Opening the Window");
				new RCM(id1).setVisible(true);
				this.dispose();
			} else {
				String q = "update rcmlist set status='active' where rcmid=" + id1 + "";
				con.s.executeUpdate(q);
				JOptionPane.showMessageDialog(null, "RCM " + id1 + " Activated");
				new RCM(id1).setVisible(true);
				this.dispose();
			}
			con.c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) {
		new ActivateRCM().setVisible(true);
	}

}
