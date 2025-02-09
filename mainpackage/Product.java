package mainpackage;

import java.text.DecimalFormat;

public class Product {
	public int product_id;
	public String product_name;
	public double product_price;
	public int product_stock;

	public Product(int product_id, String product_name, double product_price, int product_stock) {
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.product_stock = product_stock;
	}
	
	public int getID() {
		return product_id;
	}
	
	public String getName() {
		return product_name;
	}
	
	public double getPrice() {
		return product_price;
	}
	
	public int getStock() {
		return product_stock;
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.00");
		return product_name + ": $" + df.format(product_price) + ", " + product_stock + " left in stock.";
	}
}
