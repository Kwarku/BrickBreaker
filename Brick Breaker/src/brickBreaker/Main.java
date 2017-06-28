package brickBreaker;

import javax.swing.JFrame;

/**
 * Created by Pawel on 16.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        Gamplay gamePlay = new Gamplay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("Breakout Ball");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);

    }

}
