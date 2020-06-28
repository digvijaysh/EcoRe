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

public class AddRCM extends JFrame implements ActionListener {
	JLabel l1, l2, l3, l4;
	JTextField t1, t2, t3, t4;
	JButton b;

	AddRCM() {

		setFont(new Font("System", Font.BOLD, 22));
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int x = fm.stringWidth("NEW RCM DETAILS");
		int y = fm.stringWidth(" ");
		int z = getWidth() - x;
		int w = z / y;
		String pad = "";
		// for (int i=0; i!=w; i++) pad +=" ";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "NEW RCM DETAILS");
		setLayout(null);

		l1 = new JLabel("RCM ID");
		l1.setFont(new Font("Raleway", Font.BOLD, 20));

		l2 = new JLabel("RCM Location");
		l2.setFont(new Font("Raleway", Font.BOLD, 22));

		l3 = new JLabel("Weight");
		l3.setFont(new Font("Raleway", Font.BOLD, 20));

		l4 = new JLabel("Total Cash");
		l4.setFont(new Font("Raleway", Font.BOLD, 20));

		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 14));
        setRCMID();

		t2 = new JTextField();
		t2.setFont(new Font("Raleway", Font.BOLD, 14));

		t3 = new JTextField();
		t3.setFont(new Font("Raleway", Font.BOLD, 14));

		t4 = new JTextField();
		t4.setFont(new Font("Raleway", Font.BOLD, 14));

		b = new JButton("ADD RCM");
		b.setFont(new Font("Raleway", Font.BOLD, 14));
		b.setForeground(Color.BLACK);
		b.setBounds(200, 420, 200, 50);
		add(b);

		l1.setBounds(100, 100, 600, 30);
		add(l1);

		t1.setBounds(400, 100, 200, 30);
		t1.setEditable(false);
		t1.setBackground(Color.lightGray);
		add(t1);

		l2.setBounds(100, 175, 600, 30);
		add(l2);

		t2.setBounds(400, 175, 200, 30);
		add(t2);

		l3.setBounds(100, 250, 600, 30);
		add(l3);

		t3.setBounds(400, 250, 200, 30);
		add(t3);

		l4.setBounds(100, 325, 600, 30);
		add(l4);

		t4.setBounds(400, 325, 200, 30);
		add(t4);

		b.addActionListener(this);

		setSize(650, 650);
		setLocation(500, 90);
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	
	
	public void setRCMID() {
		try {
			Connect con = new Connect();
			String q = "select count(rcmid) from rcmlist";
			ResultSet rs = con.s.executeQuery(q);
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count == 0)
					t1.setText(1 + "");
				else
					t1.setText((count + 1) + "");
			}
			con.c.close();
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b) {
			if (t2.getText().equals("") || t3.getText().equals("") || t4.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Enter all fields");
			} else {
				try {
					Connect con = new Connect();
					int id = Integer.parseInt(t1.getText());
					String location = t2.getText();
					double weight = Double.parseDouble(t3.getText());
					double cash = Double.parseDouble(t4.getText());
					double currentWeight = 0;
					double currentCash = 0;
					double coupon = 0;
					java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
					String status = "inactive";
					String q1 = "insert into rcmlist values(" + id + "," + "'" + location + "'" + "," + weight + ","
							+ currentWeight + "," + cash + "," + currentCash + "," + "'"+date+"'" + "," + "'" + status + "',"
							+ coupon +");";
					con.s.executeUpdate(q1);
					JOptionPane.showMessageDialog(null, "RCM added successfully");
					String q2 = "insert into usageCount(rcmid,count) values("+id+","+0+");";
					con.s.executeUpdate(q2);
					String q3 = "insert into emptyCount(rcmid,count) values("+id+","+0+");";
					con.s.executeUpdate(q3);
					con.c.close();
					setRCMID();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	public static void main(String[] args) {
		new AddRCM().setVisible(true);
	}
}
