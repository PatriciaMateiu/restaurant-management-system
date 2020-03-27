package business;

public class BaseProduct extends MenuItem{

    public BaseProduct(int id, String name, int price, int cookingTime){
        this.id = id;
        this.name = name;
        this.price = price;
        this.cookingTime = cookingTime;
    }
}
