package business;

import javax.swing.table.DefaultTableModel;

public interface RestaurantProcessing {

    public void createOrder(Order o, int id, int qty);

    public void createBill(int id);

    public void viewOrders(DefaultTableModel model, String fields[]);

    public void addInMenu(String type, int id, String name, int price, int ctime, int[] components);

    public MenuItem editInMenu(int id, String  name, String price, String ct);

    public void deleteFromMenu(int id);

    public void viewMenu(DefaultTableModel model, String[] fields);






}
