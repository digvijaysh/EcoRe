import org.jfree.ui.RefineryUtilities;

public class RCMStats2 implements Chart {

	@Override
	public void create() {
		CreateChartEmpty chart1 = new CreateChartEmpty("RCM Stats", "Empty Count");
		chart1.createDataset();
		chart1.pack();
		RefineryUtilities.centerFrameOnScreen(chart1);
		chart1.setVisible(true);
	}

}
