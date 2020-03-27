package data;

import business.BaseProduct;
import business.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RestaurantSerializator {

    MenuItem mi = null;


    public void writeInFile(MenuItem mis){

        try{
            /*FileOutputStream file = new FileOutputStream("menu.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(mis);
            out.close();
            file.close();*/
            FileOutputStream file = new FileOutputStream("menu.txt", true);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(mis);
            out.flush();
            out.close();
            file.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void overwriteFile(List<MenuItem> mis){

        try{
            FileOutputStream file = new FileOutputStream("menu.txt");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(mis);
            out.close();
            file.close();

        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public List<MenuItem> deserialize(){
        MenuItem m = new MenuItem();
        List <MenuItem> l = new ArrayList<MenuItem>();
        try{
            FileInputStream filei = new FileInputStream("menu.txt");
           //in = new ObjectInputStream(filei);
            while(filei.available() > 0){
                ObjectInputStream oi = new ObjectInputStream(filei);
                m = (MenuItem) oi.readObject();
                if(m != null){
                    l.add(m);
                }
            }
            filei.close();
        }catch (IOException e1){
            e1.printStackTrace();
        }catch (ClassNotFoundException e2){
            System.out.println("menu item class not found!!");
            e2.printStackTrace();
            return null;
        }
        return l;
    }
}
