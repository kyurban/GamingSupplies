package Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mainpackage.Product;

public class ProductDAO {
	private Connection conn;
	
	public ProductDAO() {
		try  {
			conn = database.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM products";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				products.add(new Product(
						rs.getInt("product_id"),
						rs.getString("product_name"),
						rs.getDouble("product_price"),
						rs.getInt("product_stock")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}
	
	//manage the stock of items in the available product table.
	public void updateProductStock(String name, int quantitySold) {
		String sql = "UPDATE products SET product_stock = product_stock - ? WHERE product_name = ? AND product_stock >= ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			stmt.setInt(1, quantitySold);
			stmt.setString(2, name);
			stmt.setInt(3, quantitySold);
			int rowsAffected = stmt.executeUpdate();
			
			if (rowsAffected == 0) {
				System.err.println("Not enough stock to update for "+name);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
