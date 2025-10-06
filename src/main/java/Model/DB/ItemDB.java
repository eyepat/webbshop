package Model.DB;

import Model.BO.Item;

import java.sql.*;
import java.util.Collection;
import java.util.Vector;




public class ItemDB extends Model.BO.Item {
    public static Collection searchItems(String group) {
        Vector v = new Vector<>();
        try{
            Connection con = DBManager.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select id, name, description, price from T_ITEM");
            while (rs.next()){
                int i = rs.getInt("id");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                float price = rs.getFloat("price");
                v.addElement(new ItemDB(i,name,desc,price));
            }
        }catch (SQLException e){e.printStackTrace();}
        return v;
    }

    public static Item getById(int id) {
        String sql = "SELECT id,name,description,price FROM T_ITEM WHERE id = ?";
        try (Connection con = DBManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new ItemDB(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getFloat("price"));
                }
            }
        } catch (SQLException e){ e.printStackTrace(); }
        return null;
    }

    private ItemDB(int id, String name, String desc,float price){
        super(id, name, desc,price);
    }
}

