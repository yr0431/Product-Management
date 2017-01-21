# product-management

## Functionalities
> * Add new product category
- Add new product under a category
- View specific product's description and other details
- Listing of categories
- Listing of all the products of a category
- Display Average number of products among all categories
- Display the product which has largest description
- Delete Category
- Delete Product
- Remove Product from a category
- Display most recent 5 products

## How to do the test
 
  Create database first to test the functions, category table must be created first



```
create table category (
	id int primary key auto_increment,
	category varchar(20) not null unique
);
```
``` 
create table product (
	id int primary key auto_increment,
	name varchar(80) not null;
	price double,
	description varchar(300),
	cate_id int,
	constraint fk_prodcate foreign key (cate_id) references category(id) on delete cascade
);
```
  Here are some data for test purpose
```
insert into category (category) values ('Beauty');
insert into category (category) values ('Electronic');
insert into category (category) values ('Home');
insert into category (category) values ('Kitchen');
insert into category (category) values ('Sport');
```
```
insert into product (name, price, description, cate_id) values ('Foundation', 59.95, 'Tone plus texture-fitting foundation for the ultimate natural fit; Erases pores and matches natural tone', 1);
insert into product (name, price, description, cate_id) values ('Lipstick', 24.95, 'Locks colour to lips for up to 8 hours wear; 50% more colour; Soft, smooth, comfortable formula', 1);
insert into product (name, price, description, cate_id) values ('Laptop', 589.99, 'Laptop', 2);
insert into product (name, price, description, cate_id) values ('Phone', 694.99, 'Smart phone', 2);
insert into product (name, price, description, cate_id) values ('TV', 1099.99, 'TV', 2);
insert into product (name, price, description, cate_id) values ('Mattress Foundation', 264.97, 'Modern, clean styling and strong mattress support; Luxurious padded espresso-colored faux leather; Available in Twin, Full, Queen, King', 3);
insert into product (name, price, description, cate_id) values ('Blender', 199.99, 'Blender', 4);
insert into product (name, price, description, cate_id) values ('Pot', 29.99, 'Pot', 4);
insert into product (name, price, description, cate_id) values ('Bastket Ball', 33.00, 'Bastket ball', 5);
insert into product (name, price, description, cate_id) values ('Skateboard', 49.00, 'Material: Maple Wood, PU Wheels, Alloy Bearing', 5);
```
