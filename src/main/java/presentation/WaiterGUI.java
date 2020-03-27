package presentation;

import business.*;
import business.MenuItem;
import com.sun.org.apache.xpath.internal.operations.Or;
import data.FileWriter;
import data.RestaurantSerializator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class WaiterGUI {

    Restaurant res;
    FileWriter fw = new FileWriter();
    //RestaurantSerializator rs = new RestaurantSerializator();
    private JFrame waiter = new JFrame("WAITER");
    private JFrame choices;
    private JTextField id = new JTextField(5);
    private JTextField day = new JTextField(5);
    private JTextField month = new JTextField(5);
    private JTextField year = new JTextField(7);
    private JTextField table = new JTextField(5);
    private JTextField items = new JTextField(25);
    private JTextField id2 = new JTextField(5);
    private JTextField qty = new JTextField(5);
    private JPanel jp = new JPanel();

    private JButton b1 = new JButton("New order");
    private JButton b2 = new JButton("Compute bill");
    private JButton b3 = new JButton("View all");
    private JButton b4 = new JButton("Add");
    private JButton b5 = new JButton("Done");

    public Order o;
    public HashSet<MenuItem> hs;
    public List<MenuItem> menu;


    public WaiterGUI(List<Observer> observer) {
        //menu = res.getList();
         res = new Restaurant(observer);
        jp.setPreferredSize(new Dimension(600, 300));
        waiter.setSize(600,300);
        jp.add(new JLabel("ID"));
        jp.add(id);
        jp.add(new JLabel("DATE:"));
        jp.add(new JLabel("day"));
        jp.add(day);
        jp.add(new JLabel("month"));
        jp.add(month);
        jp.add(new JLabel("year"));
        jp.add(year);
        jp.add(new JLabel("TABLE"));
        jp.add(table);
        jp.add(b1);
        jp.add(b2);
        jp.add(b3);
        b1.addActionListener(new AddOrderListener());
        b2.addActionListener(new ComputeBillListener());
        b3.addActionListener(new ViewOrdersListener());
        waiter.add(jp);
        waiter.setVisible(true);
        waiter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public void choicesFrame(){
        choices = new JFrame("CHOICES");
        JPanel jjp = new JPanel();
        jjp.setPreferredSize(new Dimension(500,200));
        choices.setSize(500,200);
        jjp.add(new JLabel("ID"));
        jjp.add(id2);
        jjp.add(new JLabel("QTY"));
        jjp.add(qty);
        jjp.add(b4);
        jjp.add(b5);
        b4.addActionListener(new AddChoiceListener());
        b5.addActionListener(new FinishOrderListener());
        choices.add(jjp);
        choices.setVisible(true);
    }

    public int getOrderId(){

        return Integer.parseInt(id.getText());
    }

    public int getChoiceId(){

        return Integer.parseInt(id2.getText());
    }

    public int getChoiceQty(){

        return Integer.parseInt(qty.getText());
    }

    public Date getOrderDate(){
        int o_day = Integer.parseInt(day.getText());
        int o_month = Integer.parseInt(month.getText()) - 1;
        int o_year = Integer.parseInt(year.getText()) - 1970;
        Date date = new Date(o_year, o_month, o_day);
        return date;
    }

    public String[] getOrderedItems(){
        String[] choices = items.getText().split(" ");
        return choices;
    }

    public int getOrderTable(){

        return Integer.parseInt(table.getText());
    }
    class AddOrderListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int o_id = getOrderId();
            Date date = getOrderDate();
            int tab = getOrderTable();
            //menu = rs.deserialize();
            o = new Order(o_id, date, tab);
            //String[] choices = getOrderedItems();
            //Class<MenuItem> it;
            choicesFrame();
            id.setText("");
            day.setText("");
            month.setText("");
            year.setText("");
            table.setText("");

           // HashSet<MenuItem> hs = new HashSet<MenuItem>();
            //hs.add(new BaseProduct(2, "avocado", 10, 3));
            //res.hm.put(o, hs);

        }
    }

    class AddChoiceListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            int c_id = getChoiceId();
            int c_q = getChoiceQty();
            res.createOrder(o, c_id, c_q);

            id2.setText("");
            qty.setText("");

        }
    }

    class ComputeBillListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            res.createBill(getOrderId());

        }
    }


    class ViewOrdersListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            String[] fields = {"order id", "date", "price", "table", "ordered items"};
            DefaultTableModel model = new DefaultTableModel(fields,0);
            JFrame jf = new JFrame();
            jf.setSize(800, 600);
            JPanel jpan = new JPanel();
            jpan.setPreferredSize(new Dimension(800, 600));
            JTable table = new JTable();
            table.setMinimumSize(new Dimension(800,400));
            table.setRowHeight(20);
            JScrollPane scroll = new JScrollPane(table);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            res.viewOrders(model, fields);
            table.setModel(model);
            table.setRowHeight(60);
            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(1).setWidth(300);
            columnModel.getColumn(4).setWidth(300);
            jpan.add(scroll);
            jf.add(jpan);
            jf.setVisible(true);

        }
    }

    class FinishOrderListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            choices.setVisible(false);
            choices.dispose();
        }
    }

}
