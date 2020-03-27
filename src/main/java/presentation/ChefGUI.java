package presentation;

import business.Order;
import javafx.beans.value.ObservableValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class ChefGUI implements Observer {

    private JFrame chef = new JFrame("CHEF");
    private JTextArea jtx = new JTextArea();
    private JPanel jp = new JPanel();

    //private ObservableValue ov = null;

    public ChefGUI() {

        jp.setPreferredSize(new Dimension(600, 400));
        jp.add(jtx);
        chef.setSize(600,400);
        chef.add(jp);
        chef.setVisible(true);
        chef.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void setText(JTextArea ta, String str){

        String s = ta.getText();
        if(s != ""){
            s = s + "\n" + str;}
        else {s = str;}
        ta.setText(s);
    }

    public void update(Observable obs, Object obj) {
        setText(jtx, "A new order waits to be cooked");
        chef.invalidate();
        chef.validate();
        chef.repaint();
    }
}
