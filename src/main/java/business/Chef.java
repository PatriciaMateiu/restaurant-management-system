package business;

import javafx.beans.value.ObservableValue;

import java.util.Observer;
import java.util.Observable;

public class Chef implements Observer {

    private ObservableValue ov = null;
    public Chef(ObservableValue ov)
    {
        this.ov = ov;
    }
    public void update(Observable obs, Object obj)
    {
        if (obs == ov)
        {
            System.out.println(ov.getValue());
        }
    }
}
