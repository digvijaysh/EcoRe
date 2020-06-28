import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.jfree.ui.RefineryUtilities;

public class RCMStatsAll extends JFrame implements ActionListener {
	JButton b1, b2, b3;
	ChartFactory chartFactory;

	public RCMStatsAll() {
		setFont(new Font("System", Font.BOLD, 22));
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int x = fm.stringWidth("RCMStatsAll");
		int y = fm.stringWidth(" ");
		int z = getWidth() - x;
		int w = z / y;
		String pad = "";
		pad = String.format("%" + w + "s", pad);
		setTitle(pad + "RCMStatsAll");
		setSize(400, 400);
		setLocation(500, 200);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		b1 = new JButton("Cash-Coupon");
		b1.setFont(new Font("Raleway", Font.BOLD, 16));
		b1.setBounds(100, 100, 175, 40);
		add(b1);
		b1.addActionListener(this);
		
		b2 = new JButton("EmptyCount");
		b2.setFont(new Font("Raleway", Font.BOLD, 16));
		b2.setBounds(100, 200, 175, 40);
		add(b2);
		b2.addActionListener(this);
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		chartFactory = new ChartFactory();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == b1) {
			Chart chart =chartFactory.getChart("Expense");
			chart.create();
		} else if(ae.getSource()==b2) {
			Chart chart =chartFactory.getChart("Empty");
			chart.create();
		}
	}
}
