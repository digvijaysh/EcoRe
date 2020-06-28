import org.jfree.ui.RefineryUtilities;

public class RCMStats1 implements Chart {

	@Override
	public void create() {
		CreateChartExpense chart = new CreateChartExpense("RCM Stats", "Cash vs Coupon");
		chart.createDataset();
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);	
	}

}
