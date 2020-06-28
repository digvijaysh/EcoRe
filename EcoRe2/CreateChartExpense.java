import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

class DataExp {
	int id;
	double cashAmount;
	double couponAmount;
}

public class CreateChartExpense extends JFrame{

	String applicationTitle;
	String chartTitle;
	DefaultCategoryDataset dataset1;
	List<DataExp> list;

	public CreateChartExpense(String applicationTitle, String chartTitle) {
		super(applicationTitle);
		dataset1 = createDataset();
		JFreeChart barChart = ChartFactory.createBarChart3D(chartTitle, "RCM Number", "Amount($)", dataset1,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(850, 400));
		setContentPane(chartPanel);
	}

	public DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		getData();
		for (DataExp data : list) {
			dataset.addValue(data.cashAmount,"Cash",data.id+"");
			dataset.addValue(data.couponAmount,"Coupon",data.id+"");
		}
		return dataset;
	}

	public void getData() {
		try {
			Connect con = new Connect();
			String q = "select rcmid,currentCash,couponAmt from rcmlist";
			ResultSet rs = con.s.executeQuery(q);
			list = new ArrayList<>();
			while (rs.next()) {
				DataExp data = new DataExp();
				data.id = rs.getInt(1);
				data.cashAmount = rs.getDouble(2);
				data.couponAmount = rs.getDouble(3);
				list.add(data);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
