# _Airtel Eats_ Restaurant management System
This is the project I created during my internship @airtel. It is an online food-ordering service which uses Java, Springboot, Hibernate, MySQL etc. The project supports the following functionalities:
- __User authentication and authorization using JWT and Spring security__: External users can register as a Customer or Restaurant owner. Additionally, this app also supports the role of a _Super admin_ for select internal users, who are assigned the task to moderate the activities on the platform.
- __CRUD Operations on Restaurant__: Restaurant owners can create, update and delete restaurant information using the app. Customers can search and view restaurant information (both by name and cuisine) and also add a restaurant into favorites.
- __CRUD Operations on FoodItems__: Restaurant owners can create and add FoodItems into their restaurant using this service along with assigning them a wide variety of categories like Veg/Non-veg, Seasonal/Non-seasonal etc. Customers can search and view FoodItems by name, by restaurant name or by categories.
- __Ability to customize your order__: Customers can decide what special ingredients/toppings they want or which ones they don't want along with their order and restaurant owners can track the details corresponding to every order.
- __Cart functionality and checkout__: Customers can add a FoodItem to their cart and proceed to checkout and place an order and track it's status. Restaurant owners can view and update the status of all orders placed into their restaurant.

## ER Schema

![airtel-eats-schema-diagram-dark drawio](https://github.com/user-attachments/assets/2573c405-7e3c-4c20-b5f9-334833d86ef9)
