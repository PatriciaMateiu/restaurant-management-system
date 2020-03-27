package business;
import java.util.Date;
import java.io.*;
import java.util.List;
import java.util.Observable;

public class Order {

    public int orderID;
    public Date date;
    public int table;

    private String wVal;

    public Order(int orderID, Date date, int table){
        this.orderID = orderID;
        this.date = date;
        this.table = table;
    }


    public int getOrderID() {
        return orderID;
    }

    public Date getDate() {
        return date;
    }

    public int getTable() {
        return table;
    }

    public int hashCode(){
        return orderID;
    }

    public boolean equals(Object obj){

        if(this==obj){
            return true;
        }
        if(obj==null || obj.getClass() != this.getClass()){
            return false;
        }

        Order order = (Order) obj;
        return(order.orderID == this.orderID && order.date == this.date && order.table == this.table);

    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


}
