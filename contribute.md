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
  + En samling opskrifter som brugeren  kan åbne op, læse om og tilføje alle den ingredienser til sin shopping list. Opskrifter vil blive delt op i kategorierne standard, gluten free og vegan.
  + opskrifter består af et navn som primary key, en beskrivelse og en kategori som en foreign key. (recipe_name, description, category_name).
+ categories
  + En samling af de kategorier som man kan ligge opskrifter i.
  + består blot af et navn (category_name)
+ recipe_has_groceries
  + Denne tabel kobler Opskrifter med dagligvarer. Da en opskrift kan bruge mange dagligvarer og dagligvarer kan indgå i flere opskrifter har man brug for en mellemtabel for at understøtte denne mange-til-mange relation.
  + Består af navne fra groceries og recipes som to foreign keys, same mængde og mængdeenhed. (grocery_name, recipe_name, amount, unit)

ER diagrammet for vores database giver et overblik over tabellerne og deres relationer:

![ER diagram](/https://raw.githubusercontent.com/MeH2607/GroceryPlus/main/er%20diagram.PNG "ER diagram")

<h3>Logik</h3>
GroceyPlus' logik er bygget på Java som bruger springboot frameworket. Springboot frameworket lader os nemt lave API endpoints som kan tilgås med HTTP protokollen. 

Vi har designet vores kode rundt om Model-View-Controller (MVC) mønsteret der giver et mere overskueligt overblik. Med det har vi fordelt koden i 4 pakker:

- Controller
- Model
- repositories
- services



TODO
- Forklar vores klasser
- kode eksempler
- dependency injection
- application.properties
- klasse diagram
