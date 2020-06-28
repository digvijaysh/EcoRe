import java.sql.Date;

public class ItemDetail {
	private int rcmid;
	private int itemid;
	private String itemName;
	private double weight;
	private double unitPrice;
	private Date date;

	public int getRcmid() {
		return rcmid;
	}

	public void setRcmid(int rcmid) {
		this.rcmid = rcmid;
	}

	public int getItemid() {
		return itemid;
	}

	public void setItemid(int itemid) {
		this.itemid = itemid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ItemDetail [rcmid=" + rcmid + ", itemid=" + itemid + ", itemName=" + itemName + ", weight=" + weight
				+ ", unitPrice=" + unitPrice + ", date=" + date + "]";
	}
}
