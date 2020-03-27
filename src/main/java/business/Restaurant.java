package business;

import data.FileWriter;
import data.RestaurantSerializator;
import javafx.beans.value.ObservableValue;
import presentation.AdministratorGUI;
import presentation.ChefGUI;
import presentation.WaiterGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Restaurant extends Observable implements RestaurantProcessing{

    public HashMap<Order, HashSet<MenuItem>> hm = new HashMap<Order, HashSet<MenuItem>>();
    RestaurantSerializator rs = new RestaurantSerializator();
    public List<MenuItem> menu;
    public HashSet<MenuItem> hs;
    private List<Observer> ob;
    FileWriter fw = new FileWriter();
    Order o;
   // public void createOrder()

    public Restaurant(List<Observer> ob){
        menu = rs.deserialize();
        this.ob = ob;
    }

    public void notifyAllObsevers(){
        for(Observer obs: ob){
            obs.update(null,null);
        }
    }


    public List<MenuItem> getList() {
          return menu;
    }
    public void setList(List<MenuItem> list){
        this.menu = list;
    }

    /**
     *
     * @pre o != null
     * @pre id != 0
     * @post hm.size() != null
     */

    public void createOrder(Order o, int id, int qty){
        hs = new HashSet<MenuItem>();
        for(MenuItem m : menu){
            if(m.getId() == id){
                m.setQuantity(qty);
                hs.add(m);
                //System.out.println("Menu with id " + m.getId() +" and name " + m.getName() + " added in a quantity of " + m.getQuantity());
            }
        }
        hm.put(o, hs);
        setChanged();
        notifyAllObsevers();
    }

    /**
     *
     * @pre id !=0
     * @post set != null
     */
    public void createBill(int id){
        Object[] al = hm.keySet().toArray();
        for(int i = 0; i < hm.size(); i++) {
            if (al[i] != null) {
                Order or = (Order) al[i];
                if (or.getOrderID() == id) {
                    HashSet<MenuItem> set = hm.get(or);
                    fw.createBill(or, set);
                }
            }
        }
    }

    public void viewOrders(DefaultTableModel model, String fields[]){

        //System.out.println("Nr of rows: " + hm.size());
        String[] row = new String[fields.length];
        Object[] al = hm.keySet().toArray();

        for(int i = 0; i < hm.size(); i++){
            if(al[i] != null){
                Order or = (Order) al[i];
                String createdString = "";
                HashSet<MenuItem> ha = hm.get(or);
                int pr = 0;
                for(MenuItem m : ha){
                    if(m instanceof BaseProduct){
                        createdString += m.getQuantity() + " x " + m.getName() + '\n';
                        pr += m.getQuantity() * m.computePrice();
                    }
                    else if(m instanceof CompositeProduct){
                        pr += m.getQuantity() * m.computePrice();
                        createdString += m.getQuantity() + " x " + m.getName() + " ( ";
                        for(BaseProduct ba : ((CompositeProduct) m).list){
                            createdString += ba.getName() + ", ";
                        }
                        createdString = createdString.substring(0, createdString.length()-2);
                        createdString += " ) " + '\n';
                    }
                }
                row[0] = String.valueOf(or.getOrderID());
                row[1] = String.valueOf(or.getDate());
                row[2] = String.valueOf(pr);
                row[3] = String.valueOf(or.getTable());
                row[4] = createdString;

                model.addRow(row);
            }
        }
    }

    /**
     *
     * @pre type.length() != 0
     * @pre id != 0
     * @pre name.length() != 0
     * @pre price != 0
     * @pre ctime != 0
     * @post getList() != null
     */

    public void addInMenu(String type, int id, String name, int price, int ctime, int[] components){
        if(type.equals("basic")){
            BaseProduct newp = new BaseProduct(id, name, price, ctime);
            List<MenuItem> l = getList();
            l.add(newp);
            setList(l);
            rs.writeInFile(newp);
        }
        else if(type.equals("composite")) {
            CompositeProduct newp = new CompositeProduct(id, name);
            HashSet<BaseProduct> list2 = new HashSet<BaseProduct>();
            for (int i = 0; i < components.length; i++) {
                for (MenuItem it : getList()) {
                    if (components[i] == it.getId()) {
                        if (it instanceof BaseProduct) {
                            list2.add((BaseProduct) it);
                        }
                    }
                }
            }
            newp.setList(list2);
            newp.setPrice(newp.computePrice());
            newp.setCTime(newp.computeCTime());
            List<MenuItem> l = getList();
            l.add(newp);
            rs.writeInFile(newp);
            setList(l);
        }
    }

    /**
     *
     * @pre id != 0
     * @inv getList().size() != 0
     */

    public MenuItem editInMenu(int id, String  name, String price, String ct){
        List<MenuItem> l = getList();
        for (MenuItem mi : l) {
            if (mi.getId() == id) {
                if (name.length() != 0) {
                    mi.setName(name);
                }
                if (price.length() != 0) {
                    mi.setPrice(Integer.parseInt(price));
                }
                if (ct.length() != 0) {
                    mi.setCookingTime(Integer.parseInt(ct));
                }
                if(mi instanceof CompositeProduct){
                   return mi;
                }
            }
        }
        return null;
    }

    /**
     *
     * @pre id != 0
     * @inv getList().size() != 0
     */

    public void deleteFromMenu(int id){

        List<MenuItem> l = getList();
        Iterator<MenuItem> iter = l.iterator();
        while(iter.hasNext()){
            MenuItem mi = iter.next();
            if(mi.getId() == id){
                iter.remove();
            }
        }
        setList(l);
        rs.overwriteFile(l);
    }

    public void viewMenu(DefaultTableModel model, String[] fields){

        String[] row = new String[fields.length];
        for(MenuItem it: getList()) {
            if (it instanceof BaseProduct) {
                row[0] = String.valueOf(it.getId());
                row[1] = it.getName();
                row[2] = String.valueOf(it.computePrice());
                row[3] = String.valueOf(it.getCookingTime());

                model.addRow(row);
            } else if (it instanceof CompositeProduct) {
                String createdString = "";
                row[0] = String.valueOf(it.getId());
                //System.out.println(it.getName());
                createdString += it.getName() + " ( ";
                for (BaseProduct ba : ((CompositeProduct) it).list) {
                    createdString += ba.getName() + ", ";
                }
                createdString = createdString.substring(0, createdString.length() - 2);
                createdString += " ) ";

                row[1] = createdString;
                row[2] = String.valueOf(((CompositeProduct) it).computePrice());
                row[3] = String.valueOf(((CompositeProduct) it).computeCTime());

                model.addRow(row);
            }
        }
    }

}
