/**
 * 
 */
package com.project.product;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.lang.model.element.VariableElement;

/**
 * @author Rui Yang
 *
 */
public class ManagementDB {
	
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;
	
	public ManagementDB() {
		// TODO Auto-generated constructor
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/products","root","password");
			System.out.println("Database connected!");
			System.out.println("");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkCategory(String category) {
		boolean flag = false;
		try {
			ps = con.prepareStatement("select category from category where category='"+category+"'");
			rs = ps.executeQuery();
			flag = rs.next();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
	
	public void addCategory(){
		System.out.println("Please input category name: ");
		Scanner sc = new Scanner(System.in);
		String category = sc.nextLine();
		try {
			if(!checkCategory(category)){
				ps = con.prepareStatement("insert into category (category) values (?)");
				ps.setString(1, category);
				ps.executeUpdate();
				System.out.println("Category "+ category +" is added!");
			} else {
				System.out.println("Category "+category+" already exist!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//sc.close();
	}
	
	public void addProduct(){
		System.out.println("Please input the category name you want to add new product: ");
		Scanner sc = new Scanner(System.in);
		String category = sc.nextLine();
		if(checkCategory(category)){
			System.out.println("Please input product name: ");
			String name = sc.nextLine();
			System.out.println("Please input product price: ");
			double price = sc.nextDouble();
			sc.nextLine();
			System.out.println("Please input product description: ");
			String description = sc.nextLine();
			try {
				ResultSet rs = con.prepareStatement("select id from category where category ='"+category+"'").executeQuery();
				rs.next();
				int cate_id = rs.getInt(1);	
				ps = con.prepareStatement("insert into product (name, price, description, cate_id) values (?,?,?,?)");
				ps.setString(1,name);
				ps.setDouble(2,price);
				ps.setString(3,description);
				ps.setInt(4,cate_id);
				ps.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Product "+ name +" is added!");
		}else{
			System.out.println("Category "+category+" not exist!");
			System.out.println("Do you want to create a new category? Y/N");
			String c = sc.nextLine();
			if(c.equals("Y")||c.equals("y")){
				addCategory();
				addProduct();
			}else if(c.equals("N")||c.equals("n")){
			listCategories();
			addProduct();
			}
		}
		//sc.close();
	}

	public void productDetails(){	
		System.out.println("Please select product: ");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		try {
//			rs = con.prepareStatement("select count(name) from product p, category c where name = '"+name+"' and p.cate_id = c.id").executeQuery();
//			rs.next();
//			int count = rs.getInt(1);
//			if(count > 0){
			rs = con.prepareStatement("select category, name, price, description from product p, category c where name = '"+name+"' and p.cate_id = c.id").executeQuery();
			if(!rs.next()) {
				System.out.println("No product found!");
			} else {
				do {
					System.out.println("Category: "+rs.getString(1));
					System.out.println("Name: "+rs.getString(2));
					System.out.println("Price:"+rs.getString(3));
					System.out.println("Description: "+rs.getString(4));
					System.out.println("===========================================================");
				} while(rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("Category "+ name +" is added!");
		//sc.close();
	}
	
	public void listCategories(){
		try {
			ps=con.prepareStatement("select category from category");
			rs=ps.executeQuery();
			System.out.println("List of Category: ");
			while(rs.next()){
				System.out.print(rs.getString(1)+"; ");
			}
			System.out.println("");
			System.out.println("");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void listProducts() {
		System.out.println("Please select a category you would like to check: ");
		Scanner sc = new Scanner(System.in);
		String category = sc.nextLine();
		if(checkCategory(category)){
			try {
	//			rs = con.prepareStatement("select count(name) from product p, category c where category = '"+category+"' and p.cate_id = c.id").executeQuery();
	//			rs.next();
	//			int count = rs.getInt(1);
	//			if(count > 0){
				ps=con.prepareStatement("select name, price, description from product p, category c where p.cate_id = c.id and c.category = '"+category+"'");
				rs=ps.executeQuery();
				boolean flag = true;
				while(rs.next()){
					flag = false;
					System.out.println("Name: "+rs.getString(1));
					System.out.println("Price:"+rs.getString(2));
					System.out.println("Description: "+rs.getString(3));
					System.out.println("===========================================================");
				}
				if(flag)
					System.out.println("No product in this category!");
	//				listProducts();
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else {
			System.out.println("Category "+category+" not exist!");
			listCategories();
			listProducts();
		}
	}
	
	public void aveProductNum() {
		try{
			ps=con.prepareStatement("select avg(cnt) from (select count(cate_id) as cnt from product group by cate_id) as counts");
			rs=ps.executeQuery();
			rs.next();
			double ave = rs.getDouble(1);
			System.out.println("Average number of products is: " + ave);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void maxDescription() {
		try{
			ps=con.prepareStatement("select name, description from product where length(description) = (select max(length(description)) from product)");
			rs=ps.executeQuery();
			while (rs.next()) {
				System.out.println("Name: "+rs.getString(1));
				System.out.println("Description: "+rs.getString(2));
				System.out.println("===========================================================");
			};
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void delCategory(){
		System.out.println("Please select a category to delete: ");
		Scanner sc = new Scanner(System.in);
		String category= sc.nextLine();
		int x=0;
		try{
			ps=con.prepareStatement("delete from category where category = '"+category+"'");
			x = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(x>0){
			System.out.println("Category "+category+" is deleted!");
		}else {
			System.out.println("Category "+category+" not exist!");
		}
	}
	
	public void delProduct(){
		System.out.println("Please select a product to delete: ");
		Scanner sc = new Scanner(System.in);
		String name= sc.nextLine();
		try{
			ps=con.prepareStatement("delete from product where name = '"+name+"'");
			int x = ps.executeUpdate();
			if(x==0)
				System.out.println("Product "+name+" is not exist!");
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("Product "+name+" is deleted!");
	}
	
	public void delAllFromCategory(){
		System.out.println("Please select a category to delete all products inside: ");
		Scanner sc = new Scanner(System.in);
		String category= sc.nextLine();
		if(checkCategory(category)){
			try{
				ps=con.prepareStatement("delete product from product, category c where cate_id = c.id and category = '"+category+"';");
				ps.executeUpdate();
				System.out.println("Products in category "+category+" are deleted!");
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else {
			System.out.println("Category "+category+" not exist!");
		}
	}
	
	public void display5() {
		try{
			ps=con.prepareStatement("select category, name, price, description from product, category where cate_id = category.id order by product.id desc limit 5;");
			rs=ps.executeQuery();
			while (rs.next()) {
				System.out.println("Category: " + rs.getString(1));
				System.out.println("Name: "+rs.getString(2));
				System.out.println("Price: "+rs.getString(3));
				System.out.println("Description: "+rs.getString(4));
				System.out.println("=========================================================");
			};
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ManagementDB mdb = new ManagementDB();
		
		System.out.println("Management options: ");
		System.out.println("===========================================================");
		System.out.println("1. Add new product category");
		System.out.println("2. Add new product under a category"); 
		System.out.println("3. View specific product's description and other details");
		System.out.println("4. Listing of categories");
		System.out.println("5. Listing of all the products of a category");
		System.out.println("6. Display average number of products among all categories");
		System.out.println("7. Display the product which has largest description");
		System.out.println("8. Delete a category");
		System.out.println("9. Delete a product");
		System.out.println("10. Remove all products from a category");
		System.out.println("11. Display most recent 5 products");
		System.out.println("12. Exit");
		System.out.println("===========================================================");
		while(true) {
			System.out.println("Please select an option: ");
			Scanner sc = new Scanner(System.in);
			int option = 0;
			try {
				option = sc.nextInt();
			} catch (Exception e) {
				sc.nextLine();
				option = 13;
			}
			
			switch (option) {
				case 1:
					mdb.addCategory();
					//System.out.println(1);
					break;
				case 2:
					mdb.listCategories();
					mdb.addProduct();
//					System.out.println(2);
					break;
				case 3:
					mdb.productDetails();
//					System.out.println(3);
					break;
				case 4:
					mdb.listCategories();
//					System.out.println(4);
					break;				
				case 5:
					mdb.listCategories();
					mdb.listProducts();
//					System.out.println(5);
					break;
				case 6:
					mdb.aveProductNum();
//					System.out.println(6);
					break;
				case 7:
					mdb.maxDescription();
//					System.out.println(7);
					break;
				case 8:
					mdb.listCategories();
					mdb.delCategory();
//					System.out.println(8);
					break;
				case 9:
					mdb.delProduct();
//					System.out.println(9);
					break;
				case 10:
					mdb.listCategories();
					mdb.delAllFromCategory();
//					System.out.println(10);
					break;
				case 11:
					mdb.display5();
//					System.out.println(11);
					break;
				case 12:
					System.out.println("Bye!");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid option!");
					break;
			}
			System.out.println("");
			System.out.println("Do you want to exit Management System? Y/N");
			sc = new Scanner(System.in);
			String c = sc.nextLine();
			if(c.equals("Y")||c.equals("y")){
				System.out.println("Bye!");
				System.exit(0);
			}else {
				System.out.println("");
				System.out.println("Management options: ");
				System.out.println("=============================================================================================");
				System.out.print("1. Add Category         ");
				System.out.print("2. Add Product            "); 
				System.out.print("3. Product Details       ");
				System.out.println("4. List Categories");
				System.out.print("5. List of Products	");
				System.out.print("6. Average # of products  ");
				System.out.print("7. Largest Description   ");
				System.out.println("8. Delete Category");
				System.out.print("9. Delete Product       ");
				System.out.print("10. Remove Products       ");
				System.out.print("11. Recent 5 Products    ");
				System.out.println("12. Exit  ");
				System.out.println("=============================================================================================");
				
			}
			//sc.close();
		}
		
	}

}
