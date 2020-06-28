import org.jfree.ui.RefineryUtilities;

public class RCMStats implements Chart {

	public void create() {
		CreateChartUsage chart = new CreateChartUsage("RCM Stats", "UsageCount");
		chart.createDataset();
		chart.pack();
		RefineryUtilities.centerFrameOnScreen(chart);
		chart.setVisible(true);
	}
}
