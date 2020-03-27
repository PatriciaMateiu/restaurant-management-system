package business;

public class MenuItem implements java.io.Serializable{

    public int id;
    public String name;
    public int price;
    public int cookingTime;
    public int quantity;

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int computePrice(){

        return price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }
}
