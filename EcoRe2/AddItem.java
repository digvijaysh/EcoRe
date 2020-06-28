import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddItem extends JFrame implements ActionListener {
	JLabel j1, j2, j3;
	JTextField t1, t2, t3;
	JButton b1, b2, b3, b4;
	JTable tb2;
	ItemsDao itemDao;

	AddItem() {
		setFont(new Font("System", Font.BOLD, 22));
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int x = fm.stringWidth("Add/Delete Item");
		int y = fm.stringWidth(" ");
		int z = getWidth() - x;
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "Add/Delete Item");
		setLayout(null);

		j1 = new JLabel("RCM ID");
		j1.setFont(new Font("Raleway", Font.BOLD, 16));
		j1.setBounds(50, 50, 100, 40);
		add(j1);

		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 16));
		t1.setBounds(300, 50, 200, 40);
		add(t1);

		j2 = new JLabel("ITEM NAME");
		j2.setFont(new Font("Raleway", Font.BOLD, 16));
		j2.setBounds(50, 125, 100, 40);
		add(j2);

		t2 = new JTextField();
		t2.setFont(new Font("Raleway", Font.BOLD, 16));
		t2.setBounds(300, 125, 200, 40);
		add(t2);

		j3 = new JLabel("UNIT PRICE");
		j3.setFont(new Font("Raleway", Font.BOLD, 16));
		j3.setBounds(50, 200, 100, 40);
		add(j3);

		t3 = new JTextField();
		t3.setFont(new Font("Raleway", Font.BOLD, 16));
		t3.setBounds(300, 200, 200, 40);
		add(t3);

		b1 = new JButton("Add Item");
		b1.setFont(new Font("Raleway", Font.BOLD, 16));
		b1.setBounds(25, 326, 150, 40);
		add(b1);
		b1.addActionListener(this);

		b2 = new JButton("Delete Item");
		b2.setFont(new Font("Raleway", Font.BOLD, 16));
		b2.setBounds(215, 325, 150, 40);
		add(b2);
		b2.addActionListener(this);

		b3 = new JButton("Change Price");
		b3.setFont(new Font("Raleway", Font.BOLD, 16));
		b3.setBounds(400, 325, 150, 40);
		add(b3);
		b3.addActionListener(this);

		b4 = new JButton("Item List");
		b4.setFont(new Font("Raleway", Font.BOLD, 16));
		b4.setBounds(25, 400, 150, 40);
		add(b4);
		b4.addActionListener(this);
		itemDao = new ItemDAOImpl();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 750);
		setLocation(500, 200);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			String id = t1.getText();
			String name = t2.getText();
			String price = t3.getText();
			if (id.equals("") || name.equals("") || price.equals("")) {
				JOptionPane.showMessageDialog(null, "Enter all the values");
			} else {
				Item item = new Item();
				int id1 = Integer.parseInt(id);
				item.setUnitPrice(Double.parseDouble(price));
				item.setName(name);
				itemDao.insert(id1,item);
			}
		} else if (ae.getSource() == b2) {
			String id = t1.getText();
			String name = t2.getText();
			if (id.equals("") || name.equals("")) {
				JOptionPane.showMessageDialog(null, "Enter ID and Name");
			} else {
				Item item = new Item();
				item.setName(name);
				int id1 = Integer.parseInt(id);
				itemDao.delete(id1, item);
			}
		} else if (ae.getSource() == b3) {
			String id = t1.getText();
			String name = t2.getText();
			String uprice = t3.getText();
			if (id.equals("") || name.equals("") || uprice.equals("")) {
				JOptionPane.showMessageDialog(null, "Enter RCM id,Name and Price to change price");
			} else {
				Item item = new Item();
				int id1 = Integer.parseInt(id);
				item.setUnitPrice(Double.parseDouble(uprice));
				item.setName(name);
				if(!itemDao.check(id1, item))
				  itemDao.changePrice(id1,item);
				else 
					JOptionPane.showMessageDialog(null,"Item does not exist");
			}
		} else if (ae.getSource() == b4 ) {
			String id = t1.getText();
			if (id.equals("")) {
				JOptionPane.showMessageDialog(null, "Enter id to view the Item List");
			} else {
				int id1 = Integer.parseInt(id);
				List<Item> itemList = itemDao.fetchItems(id1);
				String[] x = { "Item ID", "Item Name", "Unit Price" };
				int count = itemList.size();
				String[][] y = new String[count][3];
				int i = 0, j = 0;
				for (Item item : itemList) {
					y[i][j] = "" + item.getId();
					y[i][j + 1] = item.getName();
					y[i][j + 2] = "$" + item.getUnitPrice();
					i++;
				}
				tb2 = new JTable(y,x);
				JScrollPane sp = new JScrollPane(tb2);
				sp.setBounds(25, 450, 550, 250);
				add(sp);
				tb2.setFont(new Font("monospaced", Font.BOLD, 16));
			}
		}
	}

	public static void main(String[] args) {
		new AddItem().setVisible(true);
	}

}
