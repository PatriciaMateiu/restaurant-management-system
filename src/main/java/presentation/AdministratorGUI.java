package presentation;


import business.*;
import business.MenuItem;
import data.RestaurantSerializator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observer;

public class AdministratorGUI extends JFrame {

        private JFrame administrator = new JFrame("ADMINISTRATOR");
        private JFrame editf;
        private JPanel jp = new JPanel();
        private JTextField id = new JTextField(5);
        private JTextField name = new JTextField(5);
        private JTextField price = new JTextField(5);
        private JTextField ctime = new JTextField(5);
        private JTextField type = new JTextField(5);
        private JTextField components = new JTextField(15);
        private JTextField id2 = new JTextField(5);
        private JButton b1 = new JButton("Create");
        private JButton b2 = new JButton("Delete");
        private JButton b3 = new JButton("Edit");
        private JButton b4 = new JButton("View all");
        private JButton b5 = new JButton("Delete");
        private JButton b6 = new JButton("Add");
        private JButton b7 = new JButton("Done");

        Restaurant res;
        RestaurantSerializator rs = new RestaurantSerializator();
        CompositeProduct aux;

        public AdministratorGUI(List<Observer> observer) {
            res = new Restaurant(observer);
            jp.setPreferredSize(new Dimension(800, 300));
            administrator.setSize(800,300);
            jp.add(new JLabel("ID"));
            jp.add(id);
            jp.add(new JLabel("NAME"));
            jp.add(name);
            jp.add(new JLabel("PRICE"));
            jp.add(price);
            jp.add(new JLabel("COOKING TIME"));
            jp.add(ctime);
            jp.add(new JLabel("TYPE (basic or composite) "));
            jp.add(type);
            jp.add(new JLabel("COMPONENTS"));
            jp.add(components);
            jp.add(b1);
            jp.add(b2);
            jp.add(b3);
            jp.add(b4);
            b1.addActionListener(new AdditionListener());
            b2.addActionListener(new DeletionListener());
            b3.addActionListener(new EditListener());
            b4.addActionListener(new ViewListener());
            administrator.add(jp);
            administrator.setVisible(true);
            administrator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        }

    public void editFrame(){
        editf = new JFrame("EDIT INGREDIENTS");
        JPanel jjp = new JPanel();
        jjp.setPreferredSize(new Dimension(500,200));
        editf.setSize(500,200);
        jjp.add(new JLabel("ID"));
        jjp.add(id2);
        jjp.add(b5);
        b5.addActionListener(new delIngListener());
        jjp.add(b6);
        b6.addActionListener(new addIngListener());
        jjp.add(b7);
        b7.addActionListener(new exitListener());
        editf.add(jjp);
        editf.setVisible(true);
    }


        public int getId() {
            return Integer.parseInt(id.getText());
        }
        public int get2Id(){
            return Integer.parseInt(id2.getText());
        }

        public String getItemName(){
                return name.getText();
        }

        public int getPrice(){
                return Integer.parseInt(price.getText());
        }

        public int getCookingTime(){
            return Integer.parseInt(ctime.getText());
        }

        public String getItemType(){
            return type.getText();
        }
        public int[] getItemComponents(){
            String[] choices = components.getText().split(" ");
            int[] numbers = new int[choices.length];
            for(int i = 0 ; i < choices.length; i++){
                numbers[i] = Integer.parseInt(choices[i]);
            }
            return numbers;
        }

        class delIngListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                HashSet<BaseProduct> h = ((CompositeProduct)aux).getList();
                for (BaseProduct p : h) {
                    if (p.getId() == getId()) {
                        h.remove(p);
                    }
                }
                aux.setList(h);
                id2.setText("");
            }
        }

        class addIngListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                HashSet<BaseProduct> h = ((CompositeProduct)aux).getList();
                for (BaseProduct p : h) {
                    if (p.getId() == getId()) {
                        h.add(p);
                    }
                }
                aux.setList(h);
                id2.setText("");
            }
        }

        class exitListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {
                editf.setVisible(false);
                editf.dispose();
            }
        }

        class EditListener implements ActionListener {

                public void actionPerformed(ActionEvent e) {

                    MenuItem m = res.editInMenu(getId(), getItemName(), price.getText(), ctime.getText());
                    if(m != null)
                        aux = (CompositeProduct)m;
                        editFrame();
                        ((CompositeProduct) m).setList(aux.getList());
                }

        }

        class DeletionListener implements ActionListener {

                public void actionPerformed(ActionEvent e) {

                    res.deleteFromMenu(getId());
                }
        }


        class AdditionListener implements ActionListener {

                public void actionPerformed(ActionEvent e) {

                    String type = getItemType();
                    int id = getId();
                    String name = getItemName();
                    int prc = 0;
                    int ct = 0;
                    int[] comp = null;
                    if (type.equals("basic")) {
                        prc = getPrice();
                        ct = getCookingTime();
                    }
                    if (type.equals("composite")) {
                        comp = getItemComponents();
                    }
                    res.addInMenu(type, id, name, prc, ct, comp);
                }
        }

        class ViewListener implements ActionListener {

                public void actionPerformed(ActionEvent e) {

                    String[] fields = {"item id", "name", "price", "cooking time"};
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
                    //System.out.println("Nr of rows: " + res.hm.size());
                    res.viewMenu(model, fields);

                    table.setModel(model);
                    table.setRowHeight(60);
                    TableColumnModel columnModel = table.getColumnModel();
                    jpan.add(scroll);
                    jf.add(jpan);
                    jf.setVisible(true);
                }
        }

}
