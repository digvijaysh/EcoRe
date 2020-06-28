import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class ItemDAOImpl implements ItemsDao {

	public void insert(int rcmid, Item item) {
		try {
			Connect con = new Connect();
			String q = "insert into rcm (rcmid,itemname,uprice) Values(" + rcmid + ",'" + item.getName() + "',"
					+ item.getUnitPrice() + ");";
			con.s.executeUpdate(q);
			con.c.close();
			JOptionPane.showMessageDialog(null, item.getName() + " added successfully");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void delete(int rcmid, Item item) {
		try {
			Connect con = new Connect();
			String q = "delete from rcm where rcmid=" + rcmid + " && itemname=" + "'" + item.getName() + "';";
			con.s.executeUpdate(q);
			con.c.close();
			JOptionPane.showMessageDialog(null, item.getName() + " deleted successfully");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void changePrice(int rcmid, Item item) {
		try {
			Connect con = new Connect();
			String q = "update rcm set uprice=" + item.getUnitPrice() + " where rcmid=" + rcmid + " && itemname='"
					+ item.getName() + "'";
			con.s.executeUpdate(q);
			con.c.close();
			JOptionPane.showMessageDialog(null, item.getName() + " price changed successfully");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public List<Item> fetchItems(int id) {
		List<Item> list1 = new ArrayList<>();
		try {
			Connect con = new Connect();
			String q = "Select itemid,itemname,uprice from rcm where rcmid=" + id;
			ResultSet rs = con.s.executeQuery(q);
			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setUnitPrice(rs.getDouble(3));
				list1.add(item);
			}
			con.c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return list1;
	}

	@Override
	public boolean check(int id, Item item) {
		try {
			Connect con = new Connect();
			String q = "Select * from rcm where rcmid=" + id +" && itemname = '"+item.getName()+"'";
			ResultSet rs = con.s.executeQuery(q);
			if (rs.next()==false)
				return true;
			con.c.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

}
