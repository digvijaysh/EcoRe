import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Rmos extends JFrame implements ActionListener,Observer{
	private static Rmos instance = new Rmos();
	ChartFactory chartFactory;

	private Rmos() {
		super("Remote Monitoring System");
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = e.getActionCommand();
		if (msg.equals("Add RCM")) {
			new AddRCM().setVisible(true);
		} else if (msg.equals("Activate RCM")) {
			new ActivateRCM().setVisible(true);
		} else if (msg.equals("Logout")) {
			int x = JOptionPane.showConfirmDialog(null, "Are you sure");
			if (x == 0)
				System.exit(0);
		} else if (msg.equals("RCM Status")) {
			new RCMStatus().setVisible(true);
		} else if (msg.equals("Detailed Report")) {
			new RCMDetails().setVisible(true);
		} else if (msg.equals("Add Items/Delete items")) {
			new AddItem().setVisible(true);
		} else if (msg.equals("Most Used RCM")) {
			Chart chart = chartFactory.getChart("UsageCount");
			chart.create();
		} else if (msg.equals("Graph Report")) {
			new RCMStatsAll().setVisible(true);
		} else if (msg.equals("RCM operations")) {
			new RCMOperations().setVisible(true);
		}
	}

	public void init() {
		setSize(1920, 1030);
		chartFactory = new ChartFactory();

		ImageIcon ic = new ImageIcon(ClassLoader.getSystemResource("images/image1.jpg"));
		Image i3 = ic.getImage().getScaledInstance(1920, 1030, Image.SCALE_DEFAULT);
		ImageIcon icc3 = new ImageIcon(i3);
		JLabel l1 = new JLabel(icc3);
		add(l1);

		JMenuBar mb = new JMenuBar();

		JMenu menu = new JMenu("Menu");
		JMenuItem m1 = new JMenuItem("Add RCM");
		JMenuItem m2 = new JMenuItem("Add Items/Delete items");
		JMenuItem m3 = new JMenuItem("RCM operations");
		JMenuItem m5 = new JMenuItem("Activate RCM");
		menu.setForeground(Color.BLACK);

		menu.setFont(new Font("monospaced", Font.PLAIN, 16));
		m1.addActionListener(this);
		m2.addActionListener(this);
		m3.addActionListener(this);
		m5.addActionListener(this);

		// Reports and Statistics
		JMenu report = new JMenu("Statistics");
		JMenuItem r1 = new JMenuItem("Most Used RCM");
		JMenuItem r2 = new JMenuItem("Detailed Report");
		JMenuItem r3 = new JMenuItem("Graph Report");
		report.setForeground(Color.BLACK);
		report.setFont(new Font("monospaced", Font.PLAIN, 16));

		r1.addActionListener(this);
		r2.addActionListener(this);
		r3.addActionListener(this);

		// View
		JMenu view = new JMenu("View");
		JMenuItem v1 = new JMenuItem("RCM Status");
		view.setForeground(Color.BLACK);
		view.setFont(new Font("monospaced", Font.PLAIN, 16));

		v1.addActionListener(this);

		// Exit
		JMenu exit = new JMenu("Logout");
		JMenuItem ex = new JMenuItem("Logout");
		exit.setForeground(Color.BLACK);
		exit.setFont(new Font("monospaced", Font.PLAIN, 16));
		ex.addActionListener(this);

		menu.add(m1);
		menu.add(m5);
		menu.add(m3);
		menu.add(m2);
		mb.add(menu);

		report.add(r1);
		report.add(r2);
		report.add(r3);
		mb.add(report);

		view.add(v1);
		mb.add(view);

		exit.add(ex);
		mb.add(exit);

		setJMenuBar(mb);
		setFont(new Font("Senserif", Font.BOLD, 16));
		setLayout(new FlowLayout());
		setVisible(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static Rmos getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		Rmos rmos = getInstance();
		rmos.setVisible(true);
	}

	@Override
	public void update(int id) {
		JOptionPane.showMessageDialog(null,"RCM "+id+" full please empty the machine");
	}

}
