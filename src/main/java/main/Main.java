package main;

import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Restaurant;
import data.RestaurantSerializator;
import presentation.AdministratorGUI;
import presentation.ChefGUI;
import presentation.WaiterGUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observer;
import presentation.ChefGUI;

public class Main {

    public static void main(String[] args){
        RestaurantSerializator rs = new RestaurantSerializator();

            BaseProduct[] mis = new BaseProduct[3];
            CompositeProduct[] cis = new CompositeProduct[2];
            String[] names = {"potatoes", "salad", "lemonade"};
            String[] names2 = {"healthy combo", "diet mode"};
            HashSet<BaseProduct> ings = new HashSet<BaseProduct>();
            HashSet<BaseProduct> ings2 = new HashSet<BaseProduct>();

            int[] prices = {10, 12, 10};
            int[] ids2 = {4, 5};
            int[] ctimes = {15, 10, 5};
            int[] ids = {1, 2, 3};
            for(int i = 0; i < 3; i++){
                mis[i] = new BaseProduct(ids[i], names[i], prices[i], ctimes[i]);
            }
            for(int i = 0; i < 2; i++){
                cis[i] = new CompositeProduct(ids2[i], names2[i]);
            }
            ings.add(mis[0]);
            ings.add(mis[1]);
            cis[0].setList(ings);
            ings2.add(mis[1]);
            ings2.add(mis[2]);
            cis[1].setList(ings2);
            for(int i = 0; i < 2; i++){
                cis[i].setPrice(cis[i].computePrice());
                cis[i].setCTime(cis[i].computeCTime());
            }
            MenuItem[] m = new MenuItem[mis.length + cis.length];
            for(int i = 0; i < mis.length; i++){
                rs.writeInFile(mis[i]);}
            for(int j = 0; j < cis.length; j++){
                rs.writeInFile(cis[j]);}
        List<Observer> obs = new ArrayList<Observer>();
        ChefGUI cg = new ChefGUI();
        obs.add(cg);
        Restaurant res = new Restaurant(obs);
        AdministratorGUI ag = new AdministratorGUI(obs);
        WaiterGUI wg = new WaiterGUI(obs);

    }
}
