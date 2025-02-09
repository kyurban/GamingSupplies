# GamingSupplies E-Commerce Project  

## Description  

GamingSupplies is a simple console-based e-commerce application designed to manage gaming products and simulate a shopping experience.  

Users can:  
- Browse products.  
- Add items to their shopping cart.  
- View stock and price.
- Check their receipt at checkout.  

The project was developed using Java and demonstrates object-oriented programming concepts. It includes classes for:  
- Main application logic.  
- Product management.  
- Shopping cart manangement.  

---

## Features  

- View a list of gaming products.  
- Add/remove products from the shopping cart.  
- Automatically adjusting stock levels.  
- Display the contents of the shopping cart.  
- View a receipt to view the total price of items in the cart.

## Future Enhancements  

- Add a user authentication system.
- Add sorting/filtering.
- Implement UI functionality.

---

## Database Setup

To get started with the project and set up the database, please follow the instructions below.

### 1. Download the SQL File

The `gamingsupplies.sql` file contains the database schema and sample data. Please download this file from this repository.

### 2. Import the Database Using MySQL Workbench

1. Open MySQL Workbench and connect to your MySQL server.
2. Go to Server > Data Import.
3. Select Import from Self-Contained File and choose the downloaded `gamingsupplies.sql` file.
4. Click Start Import to import the database.

Once the import is complete, verify the database setup:

```sql
SHOW DATABASES;
USE GamingSupplies;
SHOW TABLES;
```
## Database Connection Configuration

Please modify the `config.properties` file and update the following:
```
db.url=jdbc:mysql://localhost:3306/GamingSupplies
db.username=your_username
db.password=your_password
