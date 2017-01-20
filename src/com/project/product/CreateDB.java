/**
 * 
 */
package com.project.product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * @author Rui Yang
 *
 */
public class CreateDB {

	/**
	 * @param args
	 */
	
	Connection con;
	Statement st;
	
	
	CreateDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection
					("jdbc:mysql://localhost:3306/products","root","password");
			if(con != null) {
				System.out.println("Connected!");
				st = con.createStatement();
				st.execute("create table category (id int primary key auto_increment, category varchar(20) not null unique);");
				st.execute("create table product (id int primary key auto_increment, name varchar(80) not null, price double, description varchar(300), cate_id int, constraint fk_prodcate foreign key (cate_id) references category(id));");
				System.out.println("Tables are created!");
				String[] queries = {"insert into category (category) values ('Beauty')",
									"insert into category (category) values ('Electronic')",
									"insert into category (category) values ('Home')",
									"insert into category (category) values ('Kitchen')",
									"insert into category (category) values ('Sport')",
									"insert into product (name, price, description, cate_id) values ('Foundation', 59.95, 'Tone plus texture-fitting foundation for the ultimate natural fit; Erases pores and matches natural tone', 1)",
									"insert into product (name, price, description, cate_id) values ('Lipstick', 24.95, 'Locks colour to lips for up to 8 hours wear; 50% more colour; Soft, smooth, comfortable formula', 1)",
									"insert into product (name, price, description, cate_id) values ('Laptop', 589.99, 'Laptop', 2)",
									"insert into product (name, price, description, cate_id) values ('Phone', 694.99, 'Smart phone', 2)",
									"insert into product (name, price, description, cate_id) values ('TV', 1099.99, 'TV', 2)",
									"insert into product (name, price, description, cate_id) values ('Mattress Foundation', 264.97, 'Modern, clean styling and strong mattress support; Luxurious padded espresso-colored faux leather; Available in Twin, Full, Queen, King', 3)",
									"insert into product (name, price, description, cate_id) values ('Blender', 199.99, 'Blender', 4)",
									"insert into product (name, price, description, cate_id) values ('Pot', 29.99, 'Pot', 4)",
									"insert into product (name, price, description, cate_id) values ('Bastket Ball', 33.00, 'Bastket ball', 5)",
									"insert into product (name, price, description, cate_id) values ('Skateboard', 49.00, 'Material: Maple Wood, PU Wheels, Alloy Bearing', 5)"
				};
				for (String query:queries){
					st.addBatch(query);
				}
				st.executeBatch();			
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CreateDB();
	}

}