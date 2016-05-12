/*
 * Created by JFormDesigner on Wed Jul 08 17:08:43 EEST 2015
 */

package tr.gov.tubitak.ingress.view;

import tr.gov.tubitak.ingress.controller.guifacade.MainFormFacade;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

/**
 * @author Haydar Karabas
 */
public class MainForm extends JFrame {
    public MainForm() {
        initComponents();
    }

    //Add Citizen
    private void button2ActionPerformed(ActionEvent e) {
        textField1.setText("WAIT!");
        String name = textField4.getText().toUpperCase();
        String surname = textField5.getText().toUpperCase();
        String age = textField6.getText();
        String gender = "";
        if (radioButton2.isSelected()){
            gender = "FEMALE";
        }else if (radioButton3.isSelected()){
            gender = "MALE";
        }

        String formLanguage = "";
        if (radioButton7.isSelected()){
            formLanguage = "TURKISH";
        }else if (radioButton8.isSelected()){
            formLanguage = "ENGLISH";
        }

        try{
            Integer.parseInt(age);

            if ((name.equals("")) || (surname.equals("")) || (age.equals("")) || (gender.equals("")) || (formLanguage.equals(""))){
                textField1.setText("All the fields must be filled!");
            }else {
                String ingressID = MainFormFacade.addCitizen(name, surname, age, gender, formLanguage);

                textField1.setText("Added! INGRESS ID: "+ingressID);

                MainFormFacade.openReport(ingressID,formLanguage);

                textField4.setText("");
                textField5.setText("");
                textField6.setText("");
                radioButton3.setSelected(true);
                radioButton7.setSelected(true);
            }
        }catch (Exception ex){
            textField1.setText("The field age must be numeric");
        }

    }

    //Find Citizen
    private void button3ActionPerformed(ActionEvent e) {
        String ingressID = textField7.getText();
        if(ingressID.contains("\"")){
            ingressID = ingressID.substring(1,12);
        }
        HashMap<String,String> values =  MainFormFacade.getCitizen(ingressID);
        if (values.containsKey("error")) {
            textField2.setText(values.get("error"));
        }else{
            textField8.setText(values.get("name"));
            textField9.setText(values.get("surname"));
            textField10.setText(values.get("age"));
            if (values.get("gender").equalsIgnoreCase("FEMALE")){
                radioButton1.setSelected(true);
            }else{
                radioButton4.setSelected(true);
            }

            if (values.get("formLanguage").equalsIgnoreCase("TURKISH")){
                radioButton5.setSelected(true);
            }else{
                radioButton6.setSelected(true);
            }
        }

        MainFormFacade.openReport(ingressID,"TURKISH");

    }

    //Update Citizen
    private void button5ActionPerformed(ActionEvent e) {
        String ingressID = textField7.getText();
        HashMap<String,String> values =  MainFormFacade.getCitizen(ingressID);
        if (values.containsKey("error")) {
            textField2.setText(values.get("error"));
        }else{
            String name = textField8.getText().toUpperCase();
            String surname = textField9.getText().toUpperCase();
            String age = textField10.getText();
            String gender = "";
            if (radioButton1.isSelected()){
                gender = "FEMALE";
            }else if (radioButton4.isSelected()){
                gender = "MALE";
            }


            String formLanguage = "";
            if (radioButton5.isSelected()){
                formLanguage = "TURKISH";
            }else if (radioButton6.isSelected()){
                formLanguage = "ENGLISH";
            }

            try{
                Integer.parseInt(age);

                if ((name.equals("")) || (surname.equals("")) || (age.equals("")) || (gender.equals("")) || (formLanguage.equals(""))){
                    textField2.setText("All the fields must be filled!");
                }else {
                    String infoMessage =  MainFormFacade.updateCitizen(ingressID, name, surname, age, gender, formLanguage);
                    textField2.setText(infoMessage);
                }
            }catch (Exception ex){
                textField2.setText("The field age must be numeric");
            }


        }
    }

    //Delete Citizen
    private void button6ActionPerformed(ActionEvent e) {
        String ingressID = textField7.getText();
        HashMap<String,String> values =  MainFormFacade.getCitizen(ingressID);
        if (values.containsKey("error")) {
            textField2.setText(values.get("error"));
        }else{
            String infoMessage =  MainFormFacade.deleteCitizen(ingressID);
            textField2.setText(infoMessage);
            textField8.setText("");
            textField9.setText("");
            textField10.setText("");
            radioButton1.setSelected(false);
            radioButton4.setSelected(false);
            radioButton5.setSelected(false);
            radioButton6.setSelected(false);
        }
    }

    //Download report
    private void button4ActionPerformed(ActionEvent e) {
        String ingressID = textField7.getText();
        String directory = textField3.getText();

        String formLanguage = "";
        if (radioButton5.isSelected()){
            formLanguage = "TURKISH";
        }else{
            formLanguage = "ENGLISH";
        }

        HashMap<String,String> values =  MainFormFacade.getCitizen(ingressID);
        if (values.containsKey("error")) {
            textField2.setText(values.get("error"));
        }else{
            String infoMessage = MainFormFacade.downloadReport(ingressID, directory, formLanguage);
            textField2.setText(infoMessage);
        }
    }

    //Download directory selection - Browse
    private void button1ActionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            textField3.setText(selectedFile.getAbsolutePath());
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        label8 = new JLabel();
        label9 = new JLabel();
        label10 = new JLabel();
        label11 = new JLabel();
        textField4 = new JTextField();
        textField5 = new JTextField();
        textField6 = new JTextField();
        radioButton2 = new JRadioButton();
        radioButton3 = new JRadioButton();
        button2 = new JButton();
        textField1 = new JTextField();
        label2 = new JLabel();
        radioButton7 = new JRadioButton();
        radioButton8 = new JRadioButton();
        panel2 = new JPanel();
        label13 = new JLabel();
        textField7 = new JTextField();
        label14 = new JLabel();
        label15 = new JLabel();
        label16 = new JLabel();
        label17 = new JLabel();
        textField8 = new JTextField();
        textField9 = new JTextField();
        textField10 = new JTextField();
        button3 = new JButton();
        button4 = new JButton();
        button5 = new JButton();
        button6 = new JButton();
        radioButton1 = new JRadioButton();
        radioButton4 = new JRadioButton();
        textField2 = new JTextField();
        textField3 = new JTextField();
        button1 = new JButton();
        label1 = new JLabel();
        radioButton5 = new JRadioButton();
        radioButton6 = new JRadioButton();
        label3 = new JLabel();

        //======== this ========
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 1.0, 1.0E-4};

        //======== tabbedPane1 ========
        {

            //======== panel1 ========
            {

                //---- label8 ----
                label8.setText("Name");

                //---- label9 ----
                label9.setText("Surname");

                //---- label10 ----
                label10.setText("Age");

                //---- label11 ----
                label11.setText("Gender");

                //---- radioButton2 ----
                radioButton2.setText("Female");

                //---- radioButton3 ----
                radioButton3.setText("Male");

                //---- button2 ----
                button2.setText("Add");
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button2ActionPerformed(e);
                    }
                });

                //---- textField1 ----
                textField1.setText("INFO");
                textField1.setEditable(false);

                //---- label2 ----
                label2.setText("Form Language");

                //---- radioButton7 ----
                radioButton7.setText("Turkish");

                //---- radioButton8 ----
                radioButton8.setText("English");

                GroupLayout panel1Layout = new GroupLayout(panel1);
                panel1.setLayout(panel1Layout);
                panel1Layout.setHorizontalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addComponent(label8, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(button2)
                                            .addGroup(panel1Layout.createSequentialGroup()
                                                .addGroup(panel1Layout.createParallelGroup()
                                                    .addComponent(label9)
                                                    .addComponent(label10))
                                                .addGap(54, 54, 54)
                                                .addGroup(panel1Layout.createParallelGroup()
                                                    .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(textField5, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(textField6, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(panel1Layout.createSequentialGroup()
                                            .addGroup(panel1Layout.createParallelGroup()
                                                .addGroup(panel1Layout.createSequentialGroup()
                                                    .addComponent(label11)
                                                    .addGap(68, 68, 68)
                                                    .addComponent(radioButton2))
                                                .addGroup(panel1Layout.createSequentialGroup()
                                                    .addComponent(label2)
                                                    .addGap(18, 18, 18)
                                                    .addComponent(radioButton7)))
                                            .addGap(18, 18, 18)
                                            .addGroup(panel1Layout.createParallelGroup()
                                                .addComponent(radioButton8)
                                                .addComponent(radioButton3)))))
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(textField1, GroupLayout.PREFERRED_SIZE, 449, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(20, Short.MAX_VALUE))
                );
                panel1Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {textField4, textField5});
                panel1Layout.setVerticalGroup(
                    panel1Layout.createParallelGroup()
                        .addGroup(panel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label8, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label9)
                                .addComponent(textField5, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel1Layout.createParallelGroup()
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addComponent(label10)
                                        .addComponent(textField6, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel1Layout.createParallelGroup()
                                        .addComponent(label11)
                                        .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(radioButton3)
                                            .addComponent(radioButton2)))
                                    .addGap(18, 18, 18)
                                    .addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label2)
                                        .addComponent(radioButton7)
                                        .addComponent(radioButton8))
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 175, Short.MAX_VALUE))
                                .addGroup(panel1Layout.createSequentialGroup()
                                    .addGap(0, 149, Short.MAX_VALUE)
                                    .addComponent(button2)
                                    .addGap(108, 108, 108)))
                            .addComponent(textField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(25, 25, 25))
                );
                panel1Layout.linkSize(SwingConstants.VERTICAL, new Component[] {textField4, textField5, textField6});
            }
            tabbedPane1.addTab("Add", panel1);

            //======== panel2 ========
            {

                //---- label13 ----
                label13.setText("INGRESS ID");

                //---- label14 ----
                label14.setText("Name");

                //---- label15 ----
                label15.setText("Surname");

                //---- label16 ----
                label16.setText("Age");

                //---- label17 ----
                label17.setText("Gender");

                //---- button3 ----
                button3.setText("Find");
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button3ActionPerformed(e);
                    }
                });

                //---- button4 ----
                button4.setText("Download");
                button4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button4ActionPerformed(e);
                    }
                });

                //---- button5 ----
                button5.setText("Update");
                button5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button5ActionPerformed(e);
                    }
                });

                //---- button6 ----
                button6.setText("Delete");
                button6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button6ActionPerformed(e);
                    }
                });

                //---- radioButton1 ----
                radioButton1.setText("Female");

                //---- radioButton4 ----
                radioButton4.setText("Male");

                //---- textField2 ----
                textField2.setEditable(false);
                textField2.setText("INFO");

                //---- button1 ----
                button1.setText("Browse");
                button1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        button1ActionPerformed(e);
                    }
                });

                //---- label1 ----
                label1.setText("Form Language");

                //---- radioButton5 ----
                radioButton5.setText("Turkish");

                //---- radioButton6 ----
                radioButton6.setText("English");

                GroupLayout panel2Layout = new GroupLayout(panel2);
                panel2.setLayout(panel2Layout);
                panel2Layout.setHorizontalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(panel2Layout.createParallelGroup()
                                .addGroup(panel2Layout.createSequentialGroup()
                                    .addComponent(label13, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(textField7, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(button3))
                                .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(panel2Layout.createSequentialGroup()
                                        .addGroup(panel2Layout.createParallelGroup()
                                            .addGroup(panel2Layout.createSequentialGroup()
                                                .addComponent(label14, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                                .addGap(35, 35, 35)
                                                .addComponent(textField8, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(panel2Layout.createSequentialGroup()
                                                .addGroup(panel2Layout.createParallelGroup()
                                                    .addComponent(label15)
                                                    .addComponent(label16)
                                                    .addComponent(label17))
                                                .addGap(39, 39, 39)
                                                .addGroup(panel2Layout.createParallelGroup()
                                                    .addComponent(textField9, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(panel2Layout.createSequentialGroup()
                                                        .addGap(9, 9, 9)
                                                        .addComponent(radioButton1)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(radioButton4))
                                                    .addComponent(textField10, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(panel2Layout.createSequentialGroup()
                                                .addComponent(label1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioButton5)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioButton6)))
                                        .addGap(226, 226, 226))
                                    .addGroup(GroupLayout.Alignment.LEADING, panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(button4)
                                        .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(button6, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(GroupLayout.Alignment.LEADING, panel2Layout.createSequentialGroup()
                                                .addGroup(panel2Layout.createParallelGroup()
                                                    .addGroup(panel2Layout.createSequentialGroup()
                                                        .addComponent(textField3, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                                                    .addGroup(GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                                                        .addComponent(button5)
                                                        .addGap(15, 15, 15)))
                                                .addComponent(button1)))))
                                .addComponent(textField2, GroupLayout.PREFERRED_SIZE, 463, GroupLayout.PREFERRED_SIZE))
                            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                panel2Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {textField8, textField9});
                panel2Layout.setVerticalGroup(
                    panel2Layout.createParallelGroup()
                        .addGroup(panel2Layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label13)
                                .addComponent(button3)
                                .addComponent(textField7, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label14, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                .addComponent(textField8, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(7, 7, 7)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                .addComponent(label15)
                                .addComponent(textField9, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addGap(8, 8, 8)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label16)
                                .addComponent(textField10, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label17)
                                .addComponent(radioButton4)
                                .addComponent(radioButton1))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(label1, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                                .addComponent(radioButton5)
                                .addComponent(radioButton6))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(button5)
                                .addComponent(button6))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(button1))
                            .addGap(13, 13, 13)
                            .addComponent(button4))
                );
                panel2Layout.linkSize(SwingConstants.VERTICAL, new Component[] {textField3, textField8, textField9});
            }
            tabbedPane1.addTab("Find", panel2);
        }
        contentPane.add(tabbedPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- label3 ----
        label3.setIcon(new ImageIcon(getClass().getResource("/ingressLogo.jpg")));
        label3.setHorizontalAlignment(SwingConstants.CENTER);
        label3.setOpaque(true);
        label3.setBackground(Color.white);
        contentPane.add(label3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        setSize(490, 585);
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(radioButton2);
        buttonGroup1.add(radioButton3);

        //---- buttonGroup4 ----
        ButtonGroup buttonGroup4 = new ButtonGroup();
        buttonGroup4.add(radioButton7);
        buttonGroup4.add(radioButton8);

        //---- buttonGroup2 ----
        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(radioButton1);
        buttonGroup2.add(radioButton4);

        //---- buttonGroup3 ----
        ButtonGroup buttonGroup3 = new ButtonGroup();
        buttonGroup3.add(radioButton5);
        buttonGroup3.add(radioButton6);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents

        radioButton3.setSelected(true);
        radioButton7.setSelected(true);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JLabel label8;
    private JLabel label9;
    private JLabel label10;
    private JLabel label11;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JButton button2;
    private JTextField textField1;
    private JLabel label2;
    private JRadioButton radioButton7;
    private JRadioButton radioButton8;
    private JPanel panel2;
    private JLabel label13;
    private JTextField textField7;
    private JLabel label14;
    private JLabel label15;
    private JLabel label16;
    private JLabel label17;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;
    private JRadioButton radioButton1;
    private JRadioButton radioButton4;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JLabel label1;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JLabel label3;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
