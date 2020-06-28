import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RCMDetails extends JFrame implements ActionListener {
	JLabel j1;
	JTextField t1;
	JButton b1, b2, b3;
	JTextArea ta1;
	private Map<String, Integer> map;

	public RCMDetails() {
		setFont(new Font("System", Font.BOLD, 22));
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int x = fm.stringWidth("RCM Details");
		int y = fm.stringWidth(" ");
		int z = getWidth() - x;
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "RCM Details");

		j1 = new JLabel("RCM ID");
		j1.setFont(new Font("Raleway", Font.BOLD, 20));
		j1.setBounds(75, 100, 400, 40);
		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 20));
		t1.setBounds(200, 100, 200, 40);
		b1 = new JButton("RCM Details");
		b1.setBackground(Color.BLACK);
		b1.setForeground(Color.WHITE);
		b1.addActionListener(this);
		b1.setBounds(450, 100, 150, 40);
		ta1 = new JTextArea();
		ta1.setFont(new Font("Raleway", Font.BOLD, 20));
		ta1.setBounds(75, 200, 600, 400);
		ta1.setEditable(false);
        
		map = new HashMap<>();

		add(j1);
		add(t1);
		add(b1);
		add(ta1);

		setLayout(null);
		setSize(768, 768);
		setLocation(500, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			try {
				ta1.setText("");
				int rcmid = Integer.parseInt(t1.getText());
				Connect con = new Connect();
				String q = "select itemname,weight from itemdetail where rcmid=" + rcmid;
				ResultSet rs = con.s.executeQuery(q);
				double total = 0;
				while (rs.next()) {
					String item = rs.getString(1);
					double weight = rs.getDouble(2);
					total += weight;
					if (!map.containsKey(item)) {
						map.put(item, 1);
					} else {
						map.put(item, map.get(item) + 1);
					}
				}
				if (map.isEmpty()) {
					ta1.append("RCM " + rcmid + " Empty");
				} else {
					for (String name : map.keySet()) {
						ta1.append(name + "   = " + map.get(name));
						ta1.append("\n");
					}
					ta1.append("Total Weight of recycled items is = " + total + "lb" + "\n");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			map.clear();
		}
	}

	public static void main(String[] args) {
		new RCMDetails().setVisible(true);
	}
}
