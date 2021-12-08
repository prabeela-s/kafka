CREATE TABLE orders (id int NOT NULL AUTO_INCREMENT,
					 customer_id int NOT NULL,
					 amount float,
					 status int, 
					 create_ts timestamp DEFAULT CURRENT_TIMESTAMP, 
                     update_ts timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
					  PRIMARY KEY (id));


					 
CREATE TABLE order_items(
					 id int NOT NULL AUTO_INCREMENT,
					 order_id int NOT NULL,
					 product_id int NOT NULL,
					 qty float NOT NULL,
					 price float NOT NULL,
					 amount float NOT NULL,
 					 create_ts timestamp DEFAULT CURRENT_TIMESTAMP, 
                     update_ts timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
					 PRIMARY KEY (id)
)
