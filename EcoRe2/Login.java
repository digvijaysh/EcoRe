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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
	JLabel l1, l2, l3;
	JTextField tf1;
	JPasswordField pf2;
	JButton b1, b2, b3;

	Login() {

		setFont(new Font("System", Font.BOLD, 22));
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int x = fm.stringWidth("RMOS");
		int y = fm.stringWidth(" ");
		int z = getWidth() - x;
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "RMOS");

		l1 = new JLabel("Remote Monitoring Station");
		l1.setFont(new Font("Osward", Font.BOLD, 38));

		l2 = new JLabel("Username:");
		l2.setFont(new Font("Raleway", Font.BOLD, 28));

		l3 = new JLabel("Password:");
		l3.setFont(new Font("Raleway", Font.BOLD, 28));

		tf1 = new JTextField(15);
		pf2 = new JPasswordField(15);

		b1 = new JButton("Login");
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);

		b2 = new JButton("CLEAR");
		b2.setBackground(Color.BLACK);
		b2.setForeground(Color.WHITE);

		b3 = new JButton("Add User");
		b3.setBackground(Color.BLACK);
		b3.setForeground(Color.WHITE);

		setLayout(null);

		l1.setBounds(110, 50, 550, 200);
		add(l1);

		l2.setBounds(125, 150, 375, 200);
		add(l2);

		tf1.setBounds(300, 235, 230, 30);
		add(tf1);

		l3.setBounds(125, 225, 375, 200);
		add(l3);

		tf1.setFont(new Font("Arial", Font.BOLD, 14));

		pf2.setFont(new Font("Arial", Font.BOLD, 14));
		pf2.setBounds(300, 310, 230, 30);
		add(pf2);

		b1.setFont(new Font("Arial", Font.BOLD, 14));
		b1.setBounds(300, 400, 100, 30);
		add(b1);

		b2.setFont(new Font("Arial", Font.BOLD, 14));
		b2.setBounds(430, 400, 100, 30);
		add(b2);

		b3.setFont(new Font("Arial", Font.BOLD, 14));
		b3.setBounds(300, 450, 230, 30);
		add(b3);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);

		getContentPane().setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(750, 750);
		setLocation(500, 200);
		setVisible(true);

	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			try {
				Connect con = new Connect();
				String username = tf1.getText();
				String password = pf2.getText();
				String q = "select * from login where username = '" + username + "' and password = '" + password + "'";
				ResultSet rs = con.s.executeQuery(q);

				if (rs.next()) {
					Rmos rmos = Rmos.getInstance();
					RmosvCommand rmosvCommand = new RmosvCommand(rmos);
					RmosV rmosv = new RmosV(rmosvCommand);
					rmosv.pressed();
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
				}
				con.c.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} else if (ae.getSource() == b2) {
			tf1.setText("");
			pf2.setText("");
		} else if (ae.getSource() == b3) {
			new AddUser().setVisible(true);
			setVisible(false);
		}

	}

	public static void main(String[] args) {
		new Login().setVisible(true);
	}

}
