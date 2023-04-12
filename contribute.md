<h1>Velkommen til GroceryPlus</h1>

Som nyt medlem af GroceryPlus teamet ville dette dokument introducere dig til hvordan GroceryPlus er bygget op så du nemmere kan blive en del af teamet og bidrage til GroceryPlus

<h2>Teknologier</h2>
GroceryPlus er en fullstack web applikation, som bliver hosted på Microsofts Azure. Azure hoster både vores web app og vores database

<h3>Backend</h3>
Vi bruger MySQL til at opbygge vores database. MySQL lader os arbejde på et meget højt niveau sprog hvor vi nemt kan få et overblik over vores data, data relationer og gøre kommunikation i teamet nemmere.

Vores Database er bygget op af 5 relaionelle tabeller

+ Groceries
  + Opbevarer alle de dagligvarer man skal kunne tilføje til sin indkøbsliste. 
  + Består af et navn som primary key, en mængde og en mængdeenhed (grocery_name, amount, unit) 
+ ShoppingList
  + brugerens indkøbsliste bestående af en liste af dagligvarer fra groceries.
  + Består af dagligvarens navn som primary key og som en foreign key fra grocerien, samt mængde og mængdeenhed (grocery_name, amount, unit)
+ recipes
  + En samling 
+ categories
+ recipe_has_categories
