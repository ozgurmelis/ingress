package tr.gov.tubitak.ingress;

import tr.gov.tubitak.ingress.view.MainForm;

import javax.swing.*;

/**
 * Created by melis on 06/07/15.
 */
public class App {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainForm mainForm = new MainForm();
                mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainForm.setVisible(true);
            }

        });
    }
}
