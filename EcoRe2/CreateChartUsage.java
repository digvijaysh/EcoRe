import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

class Data {
	int rcmid;
	int count;
}

public class CreateChartUsage extends JFrame{

	String applicationTitle;
	String chartTitle;
	DefaultCategoryDataset dataset1;
	List<Data> list;

	public CreateChartUsage(String applicationTitle, String chartTitle) {
		super(applicationTitle);
		dataset1 = createDataset();
		JFreeChart barChart = ChartFactory.createBarChart3D(chartTitle, "RCM Number", "Count", dataset1,
				PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(barChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
		setContentPane(chartPanel);
	}

	public DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		getData();
		for (Data data : list) {
			dataset.addValue(data.count, "RCM " + Integer.toString(data.rcmid), Integer.toString(data.rcmid));
		}
		return dataset;
	}

	public void getData() {
		try {
			Connect con = new Connect();
			String q = "select rcmid,count from usagecount";
			ResultSet rs = con.s.executeQuery(q);
			list = new ArrayList<>();
			while (rs.next()) {
				Data data = new Data();
				data.rcmid = rs.getInt(1);
				data.count = rs.getInt(2);
				list.add(data);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	

}
