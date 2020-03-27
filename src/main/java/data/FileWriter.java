package data;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Order;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.HashSet;

public class FileWriter {


    public void createBill(Order o, HashSet<MenuItem> set) {
        try {
            SimpleDateFormat form1 = new SimpleDateFormat("dd MMMMM yyyy");
            SimpleDateFormat form2 = new SimpleDateFormat("h:mm a");
            FileOutputStream file;
            String name = "bill_no";
            name += o.orderID + "_";
            name += o.date.getDay() + "-" + o.date.getMonth() + "-" + o.date.getYear();
            name += ".txt";
            file = new FileOutputStream("C:\\Users\\MirunaCuPruna\\IdeaProjects\\Restaurant\\" + name);
            OutputStreamWriter out = new OutputStreamWriter(file);
            BufferedWriter bw = new BufferedWriter(out);
            bw.write("Restaurant 'VEGAN SANCTUARY'");
            bw.newLine();
            bw.newLine();
            bw.write("Date : " + form1.format(o.getDate()) + "  " + form2.format(o.getDate()));
            bw.newLine();
            bw.newLine();
            bw.write("Ordered :");
            bw.newLine();
            int total = 0;
            for(MenuItem mi : set){
                String string = "";
                int pr = 0;
                if(mi instanceof BaseProduct){
                    string += mi.getQuantity() + " x " + mi.getName() + "  ";
                    pr += mi.getQuantity() * mi.computePrice();
                    string += pr;
                    total += pr;
                }
                else if(mi instanceof CompositeProduct){
                    pr += mi.getQuantity() * mi.computePrice();
                    string += mi.getQuantity() + " x " + mi.getName() + " ( ";
                    for(BaseProduct ba : ((CompositeProduct) mi).list){
                        string += ba.getName() + ", ";
                    }
                    string = string.substring(0, string.length()-2);
                    string += " ) " + "  ";
                    string += pr;
                    total += pr;
                }
                bw.write(string);
                bw.newLine();
            }
            bw.newLine();
            bw.write("TOTAL");
            bw.newLine();
            bw.write(String.valueOf(total));
            bw.newLine();
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
