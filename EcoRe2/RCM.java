import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.spec.ECParameterSpec;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RCM extends JFrame implements ActionListener, Subject {
	static int id;
	JLabel j1, j2, j3;
	JTextField t1, t2, t3;
	JList<String> list;
	Map<String, Item> itemList;
	double weight;
	double totalCash;
	double currentWeight;
	double currentCash;
	double currentSessionWeight;
	double currentSessionCash;
	JTextArea ta1;
	JButton b1, b2, b3, b4, b5;
	DefaultListModel<String> jList = new DefaultListModel<>();
	List<ItemDetail> list1 = new ArrayList<>();
	List<Observer> observers;

	RCM(int id) {
		this.id = id;
		init();
	}

	RCM() {

	}

	public void init() {
		itemList = new HashMap<>();
		setSize(1000, 1000);
		this.setTitle("RCM " + id);
		setLayout(null);
		fillList(jList);
		observers = new ArrayList<>();
		

		j1 = new JLabel("Accepted Items");
		j1.setBounds(15, 80, 200, 20);
		j1.setFont(new Font("Raleway", Font.BOLD, 16));
		add(j1);
		
		list = new JList<>(jList);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setFont(new Font("Raleway", Font.BOLD, 16));
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					String value = list.getSelectedValue();
					t1.setText(value.split(" ")[0]);
				}
			}
		});
		
		
		JScrollPane jPane = new JScrollPane(list);
		jPane.setBounds(15, 100, 200, 400);
		add(jPane);
		
		ta1 = new JTextArea();
		ta1.setFont(new Font("Raleway", Font.BOLD, 16));
		ta1.setBounds(450, 100, 500, 400);
		ta1.setEditable(false);
		add(ta1);

		j2 = new JLabel("Item Name");
		j2.setFont(new Font("Raleway", Font.BOLD, 16));
		j2.setBounds(15, 550, 100, 40);
		j2.setVisible(true);
		add(j2);

		j3 = new JLabel("Weight");
		j3.setFont(new Font("Raleway", Font.BOLD, 16));
		j3.setBounds(350, 550, 70, 40);
		add(j3);

		t1 = new JTextField();
		t1.setFont(new Font("Raleway", Font.BOLD, 16));
		t1.setBounds(130, 550, 200, 40);
		t1.setEditable(false);
		add(t1);

		t2 = new JTextField();
		t2.setFont(new Font("Raleway", Font.BOLD, 16));
		t2.setBounds(450, 550, 200, 40);
		add(t2);

		b1 = new JButton("Add Item");
		b1.setFont(new Font("Raleway", Font.BOLD, 16));
		b1.setBounds(700, 550, 150, 40);
		b1.addActionListener(this);
		add(b1);

		b2 = new JButton("Start Session");
		b2.setFont(new Font("Raleway", Font.BOLD, 16));
		b2.setBounds(100, 650, 150, 40);
		b2.addActionListener(this);
		add(b2);

		b3 = new JButton("End Session");
		b3.setFont(new Font("Raleway", Font.BOLD, 16));
		b3.setBounds(300, 650, 150, 40);
		b3.addActionListener(this);
		b3.setEnabled(false);
		add(b3);

		b4 = new JButton("Receipt");
		b4.setFont(new Font("Raleway", Font.BOLD, 16));
		b4.setBounds(500, 650, 150, 40);
		b4.addActionListener(this);
		b4.setEnabled(false);
		add(b4);

		b5 = new JButton("Coupon");
		b5.setFont(new Font("Raleway", Font.BOLD, 16));
		b5.setBounds(700, 650, 150, 40);
		b5.addActionListener(this);
		b5.setEnabled(false);
		add(b5);

		this.currentSessionCash = 0;
		this.currentSessionWeight = 0;

		String[] s1 = weight().split(" ");
		this.weight = Double.parseDouble(s1[0]);
		this.totalCash = Double.parseDouble(s1[1]);
		this.currentWeight = Double.parseDouble(s1[2]);
		this.currentCash = Double.parseDouble(s1[3]);

		setEnabled(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void setEnabled(boolean val) {
		t1.setEnabled(val);
		t2.setEnabled(val);
		ta1.setEnabled(val);
		b1.setEnabled(val);
		list.setEnabled(val);
	}

	public void fillList(DefaultListModel<String> jList) {
		try {
			Connect con = new Connect();
			String q = "select * from rcm where rcmid=" + id + ";";
			ResultSet rs = con.s.executeQuery(q);
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt(2));
				item.setName(rs.getString(3));
				item.setUnitPrice(rs.getDouble(4));
				itemList.put(item.getName(), item);
				String r = item.getName() + "     $" + item.getUnitPrice() + "/lb";
				jList.addElement(r);
			}
			con.c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String weight() {
		try {
			Connect con = new Connect();
			String q = "select totalWeight,totalCash,currentWeight,currentCash from rcmlist where rcmid=" + id;
			ResultSet rs = con.s.executeQuery(q);
			rs.next();
			double weight = rs.getDouble(1);
			double totalCash = rs.getDouble(2);
			String r = weight + " " + totalCash + " " + rs.getDouble(3) + " " + rs.getDouble(4);
			con.s.close();
			return r;
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public void insertWeight(double currentWeight, double currentCash, double couponAmt) {
		try {
			String q = "";
			Connect con = new Connect();
			if (currentCash != 0) {
				q = "update rcmlist set currentWeight = currentWeight+" + currentWeight + ","
						+ "currentCash = currentCash+" + currentCash + " where rcmid =" + id + ";";
			} else {
				q = "update rcmlist set currentWeight = currentWeight+" + currentWeight + "," + "couponAmt = couponAmt+"
						+ couponAmt + " where rcmid=" + id + ";";
			}
			con.s.executeUpdate(q);
			con.c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void updateCount() {
		try {
			Connect con = new Connect();
			String q = "update usageCount set count = count+1,date ='"
					+ new java.sql.Date(new java.util.Date().getTime()) + "' where rcmid=" + id + ";";
			con.s.executeUpdate(q);
			con.c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void insertItemDetails(ItemDetail itemDetail) {
		try {
			Connect con = new Connect();
			String q = "insert into itemdetail values(" + itemDetail.getRcmid() + "," + itemDetail.getItemid() + ","
					+ "'" + itemDetail.getItemName() + "'," + itemDetail.getWeight() + "," + itemDetail.getUnitPrice()
					+ ",'" + itemDetail.getDate() + "'" + ");";
			con.s.executeUpdate(q);
			con.c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			String name = t1.getText();
			if (name.equals("")) {
				JOptionPane.showMessageDialog(null, "Select an option from the list");
			} else {
				if (!Pattern.matches("^\\d*\\.\\d+|\\d+\\.?\\d*$", t2.getText())) {
					JOptionPane.showMessageDialog(null, "Input a number");
				} else if (t2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "weight cannot be empty");
				} else {
					if (Double.parseDouble(t2.getText()) == 0) {
						JOptionPane.showMessageDialog(null, "Weight cannot be 0");
					} else {
						if ((this.currentWeight + this.currentSessionWeight
								+ Double.parseDouble(t2.getText())) > this.weight) {
							if (this.currentSessionWeight == 0) {
								JOptionPane.showMessageDialog(null, "Machine Full");
								observers.clear();
								observers.add(Rmos.getInstance());
								notifyObserver();
								b1.setEnabled(false);
								b2.setEnabled(true);
								b3.setEnabled(false);
								t1.setText("");
								t2.setText("");
								list.setEnabled(false);
							} else {
								JOptionPane.showMessageDialog(null, "Machine Full Please Collect your cash or coupon");
								observers.clear();
								observers.add(Rmos.getInstance());
								notifyObserver();
								t1.setText("");
								t2.setText("");
								setEnabled(false);
								b4.setEnabled(true);
								b5.setEnabled(true);
								b3.setEnabled(false);
								list1.clear();
							}
						} else {
							double w = Double.parseDouble(t2.getText());
							ItemDetail item = new ItemDetail();
							item.setRcmid(id);
							item.setItemid(itemList.get(name).getId());
							item.setItemName(name);
							item.setWeight(w);
							item.setUnitPrice(itemList.get(name).getUnitPrice() * w);
							item.setDate(new java.sql.Date(new java.util.Date().getTime()));
							list1.add(item);
							ta1.append(item.getItemName() + "             " + w + "lb" + "          "
									+ itemList.get(item.getItemName()).getUnitPrice() + " x " + w + " =  $"
									+ itemList.get(item.getItemName()).getUnitPrice() * w + "\n");
							this.currentSessionCash += itemList.get(item.getItemName()).getUnitPrice() * w;
							this.currentSessionWeight += w;
							insertItemDetails(item);
						}
					}
				}
			}
		} else if (ae.getSource() == b2) {
			this.currentCash = Double.parseDouble(weight().split(" ")[3]);
			this.currentWeight = Double.parseDouble(weight().split(" ")[2]);
			setEnabled(true);
			b2.setEnabled(false);
			b3.setEnabled(true);
		} else if (ae.getSource() == b3) {
			setEnabled(false);
			ta1.append("Total Weight(lb) = "+this.currentSessionWeight+"lb"+"\n");
			ta1.append("Total Weight(kg) = "+this.currentSessionWeight*0.453592+"kg"+"\n");
			ta1.append("Toatl Amount($) = "+this.currentSessionCash+"\n");
			String text = ta1.getText();
			if (text.equals("")) {
				b4.setEnabled(false);
				b5.setEnabled(false);
				b2.setEnabled(true);
				b3.setEnabled(false);
			} else {
				t1.setText("");
				t2.setText("");
				b4.setEnabled(true);
				b5.setEnabled(true);
				b2.setEnabled(false);
				b3.setEnabled(false);
				list1.clear();
			}
		} else if (ae.getSource() == b4) {
			if (this.currentCash + this.currentSessionCash > this.totalCash) {
				JOptionPane.showMessageDialog(null, "Not Enough Money in the Machine. Here is a Coupon instead ");
				JOptionPane.showMessageDialog(null, "Please collect your coupon of $" + this.currentSessionCash);
				insertWeight(this.currentSessionWeight, 0, this.currentSessionCash);
				updateCount();
			} else {
				JOptionPane.showMessageDialog(null, "Please collect your cash of $" + this.currentSessionCash);
				insertWeight(this.currentSessionWeight, this.currentSessionCash, 0);
				updateCount();
			}
			b5.setEnabled(false);
			b4.setEnabled(false);
			this.currentSessionCash = 0;
			this.currentSessionWeight = 0;
			b2.setEnabled(true);
			b3.setEnabled(false);
			ta1.setText("");
		} else if (ae.getSource() == b5) {
			b4.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Please collect your coupon of $" + this.currentSessionCash);
			insertWeight(this.currentSessionWeight, 0, this.currentSessionCash);
			updateCount();
			b5.setEnabled(false);
			b4.setEnabled(false);
			this.currentSessionCash = 0;
			this.currentSessionWeight = 0;
			b2.setEnabled(true);
			b3.setEnabled(false);
			ta1.setText("");
		}
	}

	public static void main(String[] args) {
		new RCM(id).setVisible(true);
	}

	@Override
	public void add(Observer newObserver) {
		observers.add(newObserver);
	}

	@Override
	public void notifyObserver() {
		for (Observer observer : observers) {
			observer.update(id);
		}
	}

}
