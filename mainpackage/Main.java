package mainpackage;
import java.util.Scanner;
import Database.CartDAO;
import Database.ProductDAO;

public class Main {

    public static void main(String[] args) {
    	
    	//Introduction
    	ProductDAO productDAO = new ProductDAO();
    	System.out.println("Welcome to the Gaming Supply Store!");
        System.out.println("To add an item to your cart, type in the name.");
        System.out.println("To view your cart, type 'view cart'.");
        System.out.println("To remove an item, type 'remove'.");
        System.out.println("When you're done, type 'done'.\n");
    	for (Product product : productDAO.getAllProducts()) {
    		System.out.println(product);
    	}
    
    	Scanner kb = new Scanner(System.in);
    	CartDAO cartDAO = new CartDAO();
    	boolean done = false;
    	
    	//while user is not done, scanner takes input.
    	while(!done) {
    		
    		String input = kb.nextLine();
    		for (Product product : productDAO.getAllProducts()) {
    			if (input.equalsIgnoreCase(product.getName())) {
    				cartDAO.addToCart(product.getName(), product.getPrice(), 1);
    			}
    		}
    		
    		if (input.equalsIgnoreCase("view cart")) {
    			if (cartDAO.getAllCartItems().isEmpty()) {
    				System.out.println("Cart is empty.");
    			} else {
    				for (Cart item : cartDAO.getAllCartItems()) {
        				System.out.println(item);
        			}
    			}
    			
    		}
    		
    		if (input.equalsIgnoreCase("remove")) {
    			System.out.println("What would you like to remove from your cart?");
    			String name = kb.nextLine();
    			System.out.println("How many?");
    			while (!kb.hasNextInt()) {
    		            System.out.println("Please enter a valid number.");
    		            kb.next();
    		        }
    			int number = kb.nextInt();
    		    cartDAO.removeFromCart(name, number);
    		}
    		
    		//when user is done, close scanner and print receipt.
    		if (input.equalsIgnoreCase("done")) {
    			done = true;
    			cartDAO.printReceipt();
    			kb.close();
    		}
    	}
    }
}