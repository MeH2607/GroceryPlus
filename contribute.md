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

<img src=https://user-images.githubusercontent.com/113069009/232224057-2c056ec1-95e3-46fb-9081-dc15f660f7b7.png width="400" title="ER Diagram">


<h3>Logik</h3>
GroceyPlus' logik er bygget på Java som bruger springboot frameworket. Springboot frameworket lader os nemt lave API endpoints som kan tilgås med HTTP protokollen. 

Vi har designet vores kode rundt om Model-View-Controller (MVC) mønsteret der giver et mere overskueligt overblik. Med det har vi fordelt koden i 4 pakker:

- Controller
- Model
- repositories
- services

<h4>Controller</h4>
Controller klassen påtager sig ansvaret for at oprette HTTP Endpoints, hente data fra vores repository klasser og sende data til vores Frontend view.

Hver metode i controller har enten en GET mapping eller en POST mapping

GET mapping metoderne henter data fra vores database. De indholder et Model objekt som parameter som har til formål at sende data til frontend. Vi henter data fra metoder fra Service klassen og tilføjer dem til model via model.addAttribute() metoden, som skal indholde et kaldenavn for når frontend skal hente dataen, og selve dataen.

Et kode eksempel på en GET Mapping metode:


```java
@GetMapping("all_recipes/{recipeName}")
public String getSingleRecipe(@PathVariable String recipeName, Model model) {
    RecipeDTO recipe = groceryPlusService.getSingleRecipe(recipeName);
    model.addAttribute("recipe", recipe);
    return "single_Recipe";
    }
```

POST mapping metoderne sender data, som brugeren inputter fra frontend, til databasen. 

Et kode eksempel på en POST Mapping metode:

```java
  "@PostMapping("create_new_grocery")
    public String submitForm(@ModelAttribute("grocery") Grocery grocery) {
        groceryPlusService.addGrocery(grocery);
        return "redirect:/groceryplus/all_groceries";}"
```

<h4>Model</h4>
Model pakken indeholder vores model klasser som repræsenterer de objekter fra "virkeligheden". Alle modelklasser har getters og setters for hver attribut, *som kræves for at kunne bevæge objekterne via thymeleaf(?)* Vores 3 model klasser er:
- Grocery
- User
- RecipeDTO

<h5>Grocery</h5> 
Grocery klassen repræsenterer de råvarer som brugeren kan se og tilføje til deres shopping list. 
Variablen cartAmount viser hvor mange af den enkelte grocery som er i shoppinglist. Alt efter hvor man opretter Grocery objektet kan man instantiere cartAmount i parameteren eller default den til 0.

<img src=https://user-images.githubusercontent.com/113069009/232223941-c2f0cae1-a6d4-4f1c-a633-6cc60fa95d14.png width="300" title="Grocery Class">

<h5>RecipeDTO</h5>
RecipeDTO klassen repræsenterer de opskrifter brugeren kan finde. RecipeDTO indeholder en liste af Grocery objekter til at repræsentere ingredienserne.

<img src=https://user-images.githubusercontent.com/113069009/232223109-7181fb8f-92c5-49a8-93d7-70d3a842cbdf.png width="300" title="RecipeDTO Class">

<h5>User</h5>
User klassen repræsenterer brugeren som logger ind for at bruge programmet. User klassen består af en default constructor og defineres i controller. Klassen og login funktion er for nu mere et "proof of concept" end en reel login løsning.

<img src=https://user-images.githubusercontent.com/113069009/232223786-3ca2c62f-78e7-47b0-a89f-64bd7c6c113a.png width="300" title="User Class">


<h4>Repository</h4>
Repository pakken indeholder den logik der ligger bag vores CRUD metoder. Vi bruger JDBC api'en til at muliggøre kommunikation mellem Java og vores database.

Repository pakken indeholder Repository interfacet, som bestemmer de metoder repository klasserne skal implementere. I praksis vil vi have en Stub repository og en database repository. Ved hjælp af dependency injection ville man nemt vælge hvilken repository man vil anvende i controller.

Kommunikationen til databasen gøres med Statement og PreparedStatement klasserne fra JDBC api'en, som lader os sende SQL queries og modtage data som konveteres til java. 

Statement bruges når vi vil sende en SQL query uden noget ekstra input. Her er et eksempel:
<img src=https://user-images.githubusercontent.com/113069009/232226273-0b63c3b1-76d8-41dd-a6bc-5e4e67910c25.png width="350" title="Statement eksempel">

Når vi vil sende et input med til databasen skal vi bruge PreparedStatement. Her er et eksempel på et PreparedStatement:
<img src=https://user-images.githubusercontent.com/113069009/232237079-15693478-4a88-4fec-b180-1c00b762d4a9.png width="350" title="Statement eksempel">


<h5>Connection manager og Application.properties</h5>
Connection manageren har til formål at oprette forbindelsen til vores database. Det gør den ved hjælp af getConnection() metoden:

```java
   public static Connection getConnection(){
        try {
            if (conn == null) conn = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
```

URL, USER og PASSWORD bliver defineret i application.properties filen under Ressources mappen. I den fil skal man indsætte disse 3 linjer med ens SQL Workbench brugernavn, root kode og navnet på databasen. Det er også her at man laver dependency injection og peger på den repository man vil anvende:

```java
spring.datasource.url= database url
spring.datasource.username= root username
spring.datasource.password= root password

groceryplus.repository = repository klasse
```

<h4>Service</h4>
Service klassen har til opgave at forbinde repository og controller. 


TODO
- Forklar vores klasser
- kode eksempler
- dependency injection
- application.properties
- klasse diagram
