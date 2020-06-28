
public class ChartFactory {
    public Chart getChart(String type) {
    	if(type.equals("UsageCount"))
    		return new RCMStats();
    	else if(type.equals("Expense"))
    		return new RCMStats1();
    	else if(type.equals("Empty"))
    		return new RCMStats2();
		return null;
    }
}
