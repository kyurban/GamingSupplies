package mainpackage;

import java.text.DecimalFormat;

public class Cart {
	public String item_name;
	public double item_price;
	public int item_quantity;
	
	public Cart(String item_name, double item_price, int item_quantity) {
		this.item_name = item_name;
		this.item_price = item_price;
		this.item_quantity = item_quantity;
	}
	
	public String getName() {
		return this.item_name;
	}
	
	public double getPrice() {
		return this.item_price;
	}
	
	public int getQuantity() {
		return this.item_quantity;
	}
	
	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.00");
		return item_name + ": $" + df.format(item_price) + " Quantity: " + item_quantity;
	}
}
