DROP DATABASE if exists GroceryPlus;
CREATE DATABASE GroceryPlus;
USE GroceryPlus;

CREATE TABLE Groceries(
  grocery_name VARCHAR(255) PRIMARY KEY,
  amount Double NULL,
  unit VARCHAR(255)
  );

CREATE TABLE Recipes (
    recipe_name VARCHAR(255) PRIMARY KEY,
    description varchar(255)
);

CREATE TABLE ShoppingList (
  grocery_name VARCHAR(255) PRIMARY KEY,
  amount INT NULL,
  unit VARCHAR(255),
  FOREIGN KEY(grocery_name) REFERENCES Groceries(grocery_name)
  );
  
CREATE TABLE recipes_has_groceries (
grocery_name VARCHAR(255),
recipe_name VARCHAR(255),
amount INT NULL,
  unit VARCHAR(255),
foreign key(grocery_name) references Groceries(grocery_name),
foreign key(recipe_name) references Recipes(recipe_name)
);
  
INSERT INTO Groceries (grocery_name, amount, unit) VALUES 
('Apples', 10, 'pcs'),
('Carrots', 5, 'pcs'),
('Lettuce', 3, 'pcs'),
('Tomatoes', 6, 'pcs');

INSERT INTO Recipes (recipe_name, description) VALUES 
('Apple Salad', 'very good apple taste'), 
('Hot Gazpacho', 'soupy and tomatoey soup');

INSERT INTO recipes_has_groceries (recipe_name, grocery_name, amount, unit) VALUES
('Apple Salad', 'Apples', 3, 'pcs'), 
('Apple Salad', 'Carrots', 1, 'pcs'), 
('Apple Salad', 'Lettuce', 2, 'pcs'),
('Apple Salad', 'Tomatoes', 2, 'pcs'),
('Hot Gazpacho', 'Tomatoes', 4, 'pcs'),
('Hot Gazpacho', 'Carrots', 2, 'pcs'),
('Hot Gazpacho', 'Lettuce', 1, 'pcs');

INSERT INTO ShoppingList (grocery_name, amount, unit) VALUES 
('Apples', 2, 'pcs'),
('Carrots', 1, 'pcs'),
('Lettuce', 2, 'pcs'),
('Tomatoes', 3, 'pcs');