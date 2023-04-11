-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

DROP DATABASE if exists GroceryPlus;
CREATE
DATABASE GroceryPlus;
USE
GroceryPlus;

CREATE TABLE Groceries
(
    grocery_name VARCHAR(255) PRIMARY KEY,
    amount       Double NULL,
    unit         VARCHAR(255)
);

CREATE TABLE Categories
(
    category_name VARCHAR(255) NOT NULL UNIQUE,
    primary key (category_name)
);

CREATE TABLE Recipes
(
    recipe_name VARCHAR(255) PRIMARY KEY,
    description VARCHAR(255),
    category_name VARCHAR(255),
    FOREIGN KEY (category_name) REFERENCES Categories (category_name)
);

CREATE TABLE ShoppingList
(
    grocery_name VARCHAR(255) PRIMARY KEY,
    cart_amount INT NULL,
    unit         VARCHAR(255),
    FOREIGN KEY (grocery_name) REFERENCES Groceries (grocery_name)
);

CREATE TABLE recipes_has_groceries
(
    grocery_name VARCHAR(255),
    recipe_name  VARCHAR(255),
    amount       INT NULL,
    unit         VARCHAR(255),
    foreign key (grocery_name) references Groceries (grocery_name),
    foreign key (recipe_name) references Recipes (recipe_name)
);

INSERT INTO Categories (category_name)
VALUES ('standard'),
       ('Vegan'),
       ('Gluten free');

INSERT INTO Groceries (grocery_name, amount, unit)
VALUES ('Apples', 10, 'pcs'),
       ('Carrots', 5, 'pcs'),
       ('Lettuce', 3, 'pcs'),
       ('Tomatoes', 6, 'pcs'),
       ('Chicken thighs', 5 , 'pcs'),
       ('Onion', 1 , 'pcs'),
       ('Galic', 1 , 'pcs'),
       ('Smooth peanut butter', 500 , 'grams'),
       ('bacon', 100, 'gram'),
       ('Whipped cream', 100 , 'gram'),
       ('Spaghetti', 500, 'gram'),
       ('Parmesan', 250, 'gram');

INSERT INTO Recipes (recipe_name, description, category_name)
VALUES ('Apple Salad', 'very good apple taste', 'vegan'),
       ('Hot Gazpacho', 'soupy and tomatoey soup', 'vegan'),
       ('Bolognese', 'I cooka da pasta', 'standard'),
       ('Peanut butter chicken','You''ll love this new, budget chicken dish. Any leftovers freeze well and make a handy lunch.','Gluten free');

INSERT INTO recipes_has_groceries (recipe_name, grocery_name, amount, unit)
VALUES ('Apple Salad', 'Apples', 3, 'pcs'),
       ('Apple Salad', 'Carrots', 1, 'pcs'),
       ('Apple Salad', 'Lettuce', 2, 'pcs'),
       ('Apple Salad', 'Tomatoes', 2, 'pcs'),
       ('Hot Gazpacho', 'Tomatoes', 4, 'pcs'),
       ('Hot Gazpacho', 'Carrots', 2, 'pcs'),
       ('Hot Gazpacho', 'Lettuce', 1, 'pcs'),
       ('Bolognese','bacon', 100, 'gram'),
       ('Bolognese','Whipped cream', 100 , 'gram'),
       ('Bolognese','Spaghetti', 250, 'gram'),
       ('Bolognese','Parmesan', 50, 'gram'),
       ('Peanut butter chicken','Chicken thighs', 5 , 'pcs'),
       ('Peanut butter chicken','Onion', 1 , 'pcs'),
       ('Peanut butter chicken','Galic', 1 , 'pcs'),
       ('Peanut butter chicken','Smooth peanut butter', 500 , 'grams');

INSERT INTO ShoppingList (grocery_name, cart_amount, unit)
VALUES ('Apples', 2, 'pcs'),
       ('Carrots', 1, 'pcs'),
       ('Lettuce', 2, 'pcs'),
       ('Tomatoes', 3, 'pcs');
       
       SELECT Groceries.grocery_name, amount, Groceries.unit, cart_amount FROM Groceries, ShoppingList WHERE Groceries.grocery_name = shoppinglist.grocery_name;
       SELECT Groceries.grocery_name, amount, Groceries.unit, cart_amount FROM Groceries JOIN ShoppingList using (grocery_name);