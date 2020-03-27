package business;

import java.util.HashSet;
import java.util.List;

public class CompositeProduct extends MenuItem {

    //public String specifications;

    public HashSet<BaseProduct> list;

    public CompositeProduct(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int computePrice(){
        int total = 0;
        for(BaseProduct bp: list){
            total += bp.computePrice();
        }
        return total;
    }

    public HashSet<BaseProduct> getList(){
        return list;
    }

    public int computeCTime(){
        int total = 0;
        for(BaseProduct bp: list){
            total += bp.getCookingTime();
        }
        return total;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public void setCTime(int ctime){
        this.cookingTime = ctime;
    }

    public void setList(HashSet<BaseProduct> list){
        this.list = list;
    }
}
