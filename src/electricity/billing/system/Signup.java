package electricity.billing.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Signup extends JFrame implements ActionListener {
    Choice loginASCho;
    TextField meterText,userNameText,nameText,passwordText;
    JButton  Signup ,Login;
    Signup(){
        super("Signup Page");
//        getContentPane().setBackground(new Color(168,203,255));
        getContentPane().setBackground(new Color(3,252,202));


        JLabel heading = new JLabel("Sign Up");
        heading.setBounds(200,10,300,40);
        heading.setFont(new Font("Tahoma",Font.BOLD,20));
        add(heading);


        JLabel heading2 = new JLabel("Already have an account ?");
        heading2.setBounds(130,400,300,30);
        heading2.setFont(new Font("Tahoma",Font.PLAIN,16));
        add(heading2);


        JLabel createAs = new JLabel("Create Account As");
        createAs.setBounds(30,110,125,20);
        add(createAs);

        loginASCho = new Choice();
        loginASCho.add("Customer");
        loginASCho.add("Admin");
        loginASCho.setBounds(170,110,120,20);
        add(loginASCho);

        JLabel meterNo = new JLabel("Meter Number");
        meterNo.setBounds(30,160,125,20);
        meterNo.setVisible(false);
        add(meterNo);

        meterText = new TextField();
        meterText.setBounds(170,160,125,20);
        meterText.setVisible(false);
        add(meterText);

//        JLabel Employer = new JLabel("Employer ID");
//        Employer.setBounds(30,160,125,20);
//        Employer.setVisible(true);
//        add(Employer);
//
//        EmployerText = new TextField();
//        EmployerText.setBounds(170,160,125,20);
//        EmployerText.setVisible(true);
//        add(EmployerText);

        JLabel userName = new JLabel("UserName");
        userName.setBounds(30,200,125,20);
        add(userName);

        userNameText = new TextField();
        userNameText.setBounds(170,200,125,20);
        add(userNameText);


        JLabel name = new JLabel("Name");
        name.setBounds(30,240,125,20);
        add(name);

        nameText = new TextField("");
        nameText.setBounds(170,240,125,20);
        add(nameText);

        meterText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                try{
                    database c = new database();
                    ResultSet resultSet = c.statement.executeQuery("select * from Signup  where meter_no = '"+meterText.getText()+"'");
                    if (resultSet.next()){
                        nameText.setText(resultSet.getString("name"));
                    }

                }catch (Exception E){
                    E.printStackTrace();
                }
            }
        });

        JLabel password = new JLabel("Password");
        password.setBounds(30,280,125,20);
        add(password);

        passwordText = new TextField();
        passwordText.setBounds(170,280,125,20);
        add(passwordText);


        loginASCho.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String user = loginASCho.getSelectedItem();
                if (user.equals("Customer")){
//                    Employer.setVisible(false);
                    nameText.setEditable(false);
//                    EmployerText.setVisible(false);
                    meterNo.setVisible(true);
                    meterText.setVisible(true);
                }else {
//                    Employer.setVisible(true);
//                    EmployerText.setVisible(true);
                    nameText.setEditable(true);
                    meterNo.setVisible(false);
                    meterText.setVisible(false);
                }

            }
        });

        Signup = new JButton("Sign Up");
        Signup.setFont(new Font("Tahoma",Font.BOLD,13));
        Signup.setBackground(new Color(66,127,219));
        Signup.setForeground(Color.black);
        Signup.setBounds(130,350,90,22);
        Signup.addActionListener(this);
        add( Signup );

        Login = new JButton("Login");
        Login.setFont(new Font("Tahoma",Font.BOLD,13));
        Login.setBackground(new Color(66,127,219));
        Login.setForeground(Color.black);
        Login.setBounds(320,405,90,22);
        Login.addActionListener(this);
        add(Login);

        ImageIcon boyIcon = new ImageIcon(ClassLoader.getSystemResource("icon/boy.png"));
        Image boyImg = boyIcon.getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT);
        ImageIcon boyIcon2 = new ImageIcon(boyImg);
        JLabel boyLabel = new JLabel(boyIcon2);
        boyLabel.setBounds(340,60,250,250);
        add(boyLabel);


        setSize(650,500);
        setLocation(500,200);
        setLayout(null);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== Signup ){
            String sloginAs = loginASCho.getSelectedItem();
            String susername = userNameText.getText();
            String sname = nameText.getText();
            String spassword = passwordText.getText();
            String smeter = meterText.getText();
            try{
                database c = new database();
                String query= null;


                if (loginASCho.equals("Admin")) {
                 query = "insert into Signup values('" + smeter + "', '" + susername + "', '" + sname + "','" + spassword + "','" + sloginAs + "')";
                }else {
                    query = "update Signup set username = '"+susername+"', password = '"+spassword+"', usertype = '"+sloginAs+"', name = '"+sname+"' where meter_no = '"+smeter+"'";
                }
                c.statement.executeUpdate(query);



                JOptionPane.showMessageDialog(null,"Account Created");
                setVisible(false);
                new Login();

            }catch (Exception E){
                E.printStackTrace();
            }
        } else if (e.getSource()==Login) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
