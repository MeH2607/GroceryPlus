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
VALUES   ('Bananas', 1, 'kgs'),
         ('Apples', 3, 'pcs'),
         ('Ground beef', 500, 'grams'),
         ('Milk', 2, 'l'),
         ('Rice', 1, 'kgs'),
         ('Broccoli', 1, 'pcs'),
         ('Salmon', 2, 'pcs'),
         ('Chicken breast', 750, 'grams'),
         ('Yogurt', 500, 'grams'),
         ('Bread', 1, 'pcs'),
         ('Eggs', 12, 'pcs'),  ('Oranges', 2, 'kgs'),
         ('Onions', 1, 'kgs'),
         ('Potatoes', 2, 'kgs'),
         ('Garlic', 100, 'grams'),
         ('Lettuce', 1, 'pcs'),
         ('Cucumber', 1, 'pcs'),
         ('Avocado', 2, 'pcs'),
         ('Pineapple', 1, 'pcs'),
         ('Strawberries', 500, 'grams'),
         ('Blueberries', 250, 'grams'),
         ('Raspberries', 250, 'grams'),
         ('Blackberries', 250, 'grams'),
         ('Honeydew melon', 1, 'pcs'),
         ('Watermelon', 1, 'pcs'),
         ('Grapes', 500, 'grams'),
         ('Mango', 1, 'pcs'),
         ('Peaches', 500, 'grams'),
         ('Plums', 500, 'grams'),
         ('Cherries', 500, 'grams'),
         ('Pears', 2, 'pcs'),
         ('Green beans', 500, 'grams'),
         ('Peas', 500, 'grams'),
         ('Corn', 500, 'grams'),
         ('Spinach', 500, 'grams'),
         ('Kale', 500, 'grams'),
         ('Arugula', 500, 'grams'),
         ('Cabbage', 1, 'pcs'),
         ('Bell peppers', 500, 'grams'),
         ('Zucchini', 1, 'pcs'),
         ('Eggplant', 1, 'pcs'),
         ('Asparagus', 500, 'grams'),
         ('Artichoke', 2, 'pcs'),
         ('Cauliflower', 1, 'pcs'),
         ('Broccoli florets', 500, 'grams'),
         ('Celery', 1, 'pcs'),
         ('Radishes', 1, 'pcs'),
         ('Beets', 500, 'grams'),
         ('Sweet potatoes', 1, 'kgs'),
         ('Carrot sticks', 500, 'grams'),
         ('Cilantro', 1, 'bunch'),
         ('Basil', 1, 'bunch'),
         ('Parsley', 1, 'bunch'),
         ('Rosemary', 1, 'bunch'),
         ('Thyme', 1, 'bunch'),
         ('Oregano', 1, 'bunch'),
         ('Sage', 1, 'bunch'),
         ('Carrots', 5, 'pcs'),
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