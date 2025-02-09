package Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import mainpackage.Cart;

public class CartDAO {
private Connection conn;
	
	public CartDAO() {
		try  {
			conn = database.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//adds items from cart table. also updates product stock in product table.
	public void addToCart(String name, double price, int quantity) {
		String stockCheck = "SELECT product_stock FROM products WHERE product_name = ?";
		String insertQuery = "INSERT INTO cart (item_name, item_price, item_quantity) VALUES (?, ?, ?)"
							+ "ON DUPLICATE KEY UPDATE item_quantity = item_quantity + ?, item_price = item_price + ? ";
		try (PreparedStatement stockStmt = conn.prepareStatement(stockCheck)) {
			
			stockStmt.setString(1,  name);
			ResultSet rs = stockStmt.executeQuery();
			
			if (rs.next()) {
				int availableStock = rs.getInt("product_stock");
				
				if (availableStock >= quantity && availableStock > 0) {
					try (PreparedStatement ps = conn.prepareStatement(insertQuery)) {
						ps.setString(1, name);
						ps.setDouble(2, price);
						ps.setInt(3, quantity);
						ps.setInt(4, quantity);
						ps.setDouble(5, price);
						ps.executeUpdate();
						
						ProductDAO productDAO = new ProductDAO();
						productDAO.updateProductStock(name, quantity);
						System.out.println(name + " added to cart.");
					}
				} else {
					System.err.println("Error: Not enough stock for " + name);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//removes items from cart table. also updates product stock in product table.
	public void removeFromCart(String name, int quantity) {
	    String checkCartQuery = "SELECT item_quantity, item_price FROM cart WHERE item_name = ?";
	    String removeQuery = "UPDATE cart SET item_quantity = item_quantity - ?, item_price = item_price * ? WHERE item_name = ?";
	    String deleteQuery = "DELETE FROM cart WHERE item_name = ? AND item_quantity <= 0";

	    try (PreparedStatement checkStmt = conn.prepareStatement(checkCartQuery)) {
	        checkStmt.setString(1, name);
	        ResultSet rs = checkStmt.executeQuery();
	        
	        if (rs.next()) {
	            int currentQuantity = rs.getInt("item_quantity");
	            
	            if (currentQuantity >= quantity && currentQuantity > 0) {

	                try (PreparedStatement updateStmt = conn.prepareStatement(removeQuery)) {
	                    updateStmt.setInt(1, quantity);
	                    updateStmt.setDouble(2, (currentQuantity - quantity) / (double) currentQuantity);
	                    updateStmt.setString(3, name);
	                    int rowsUpdated = updateStmt.executeUpdate();
	                    
	                    if (rowsUpdated > 0) {
	   
	                        ProductDAO productDAO = new ProductDAO();
	                        productDAO.updateProductStock(name, -quantity); 
	                        System.out.println(quantity + " " + name + "(s) removed from cart.");
	                        
	                        if (currentQuantity - quantity <= 0) {
	                            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
	                                deleteStmt.setString(1, name);
	                                deleteStmt.executeUpdate();
	                            }
	                        }
	                    } else {
	                        System.err.println("Error: Unable to update the cart for " + name);
	                    }
	                }
	            } else {
	                System.err.println("Error: Not enough items in the cart to remove for " + name);
	            }
	        } else {
	            System.err.println("Error: Item " + name + " not found in the cart.");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Cart> getAllCartItems() {
		List<Cart> cartItems = new ArrayList<>();
		String sql = "SELECT * FROM cart";
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				cartItems.add(new Cart(
						rs.getString("item_name"),
						rs.getDouble("item_price"),
						rs.getInt("item_quantity")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cartItems;
	}
	
	public void printReceipt() {
	    if (this.getAllCartItems().isEmpty()) {
	        System.out.println("Thank you for shopping with us!");
	    } else {
	        System.out.println("Your receipt:");
	        double total = 0.0;
	        
	        for (Cart item : this.getAllCartItems()) {
	            System.out.println(item);
	            total += item.getPrice();
	        }
	        
	        System.out.println("Your total is: $" + total);
	        System.out.println("Thank you for shopping with us!");
	    }
	}
}
