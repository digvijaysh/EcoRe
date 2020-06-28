import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AddUser extends JFrame implements ActionListener {
	JLabel l1, l2;
	JTextField t1, t2;
	JButton b1;

	AddUser() {

		setFont(new Font("System", Font.BOLD, 22));
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int x = fm.stringWidth("Add New User");
		int y = fm.stringWidth(" ");
		int z = getWidth() - x;
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "AddUser");

		l1 = new JLabel("Username:");
		l1.setFont(new Font("Raleway", Font.BOLD, 20));

		l2 = new JLabel("Password:");
		l2.setFont(new Font("Raleway", Font.BOLD, 20));

		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 14));

		t2 = new JTextField();
		t2.setFont(new Font("Raleway", Font.BOLD, 14));

		b1 = new JButton("AddUser");
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);

		setLayout(null);

		l1.setBounds(90, 150, 375, 200);
		add(l1);

		l2.setBounds(90, 225, 375, 200);
		add(l2);

		t1.setBounds(300, 235, 230, 30);
		add(t1);

		t2.setBounds(300, 310, 230, 30);
		add(t2);

		t1.setFont(new Font("Arial", Font.BOLD, 14));

		b1.setFont(new Font("Arial", Font.BOLD, 14));
		b1.setBounds(300, 400, 100, 30);
		add(b1);

		b1.addActionListener(this);

		getContentPane().setBackground(Color.LIGHT_GRAY);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setSize(650, 650);
		setLocation(500, 200);
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String username = t1.getText();
		String password = t2.getText();

		if (username.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(null, "All fields are required");
		} else {
			try {
				Connect con = new Connect();
				String q1 = "insert into login values('" + username + "','" + password + "')";
				con.s.executeUpdate(q1);
				con.c.close();
				JOptionPane.showMessageDialog(null,"User Added Successfully");
				new Login().setVisible(true);
				setVisible(false);
			} catch (Exception exception) {
				System.out.println(exception);
			}
		}

	}
	
	public static void main(String[] args) {
		new AddUser().setVisible(true);
	}

}