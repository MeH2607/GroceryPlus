DROP DATABASE GroceryPlus;
CREATE DATABASE GroceryPlus;
USE GroceryPlus;

CREATE TABLE Groceries(
  name VARCHAR(255) PRIMARY KEY,
  amount INT NULL,
  unit VARCHAR(255)
  );

CREATE TABLE Recipe (
    id INT AUTO_INCREMENT PRIMARY KEY,
    recipeName VARCHAR(255),
    grocery_name VARCHAR(255) NOT NULL,
    amount INT NULL,
    unit VARCHAR(255),
    FOREIGN KEY(grocery_name) REFERENCES Groceries(name)
);

CREATE TABLE ShoppingList (
  grocery_name VARCHAR(255) PRIMARY KEY,
  amount INT NULL,
  unit VARCHAR(255),
  FOREIGN KEY(grocery_name) REFERENCES Groceries(name)
  );
  
INSERT INTO Groceries (name, amount, unit) VALUES 
('Apples', 10, 'pcs'),
('Carrots', 5, 'pcs'),
('Lettuce', 3, 'pcs'),
('Tomatoes', 6, 'pcs');

INSERT INTO Recipe (recipeName, grocery_name, amount, unit) VALUES 
('Apple Salad', 'Apples', 3, 'pcs'), 
('Apple Salad', 'Carrots', 1, 'pcs'), 
('Apple Salad', 'Lettuce', 2, 'pcs'),
('Apple Salad', 'Tomatoes', 2, 'pcs'),
('Tomato Soup', 'Tomatoes', 4, 'pcs'),
('Tomato Soup', 'Carrots', 2, 'pcs'),
('Tomato Soup', 'Lettuce', 1, 'pcs');

INSERT INTO ShoppingList (grocery_name, amount, unit) VALUES 
('Apples', 2, 'pcs'),
('Carrots', 1, 'pcs'),
('Lettuce', 2, 'pcs'),
('Tomatoes', 3, 'pcs');