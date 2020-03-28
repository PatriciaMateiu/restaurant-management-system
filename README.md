# restaurant-management-system

The project simulates a restaurant management system, which has three types of users: administrator, waiter and chef. The administrator can add, delete and modify existing products from the menu, the waiter can create a new order for a table, add elements from the menu, and compute the bill for an order. The chef is notified each time it must cook food ordered through a waiter.
There is a GUI for each type, and when the application is run, the user can choose which role is more suitable, and, depending on each, another frame opens for the CRUD operations. The Composite Design Pattern is used for the menu items, and the  Observer Design Pattern is used to notify the chef about a new order.
