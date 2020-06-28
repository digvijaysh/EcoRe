import java.util.List;

public interface ItemsDao {
   public List<Item> fetchItems(int id);
   public void insert(int id,Item item);
   public void delete(int id,Item item);
   public void changePrice(int id,Item item);
   public boolean check(int id,Item item);
}
