/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *///https://www.youtube.com/watch?v=GhO5PFCZCqY&list=PLEE74DyIkwEnOAlFY3IzcFMhwOKAP20wZ&index=1
package ServerPackages;

import javax.swing.JFrame;

/**
 *
 * @author Admin
 */
public class ServerTest {
    public static void main(String[] asd){
        Server s = new Server();
        s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        s.startRunning();
    
    }
    
}
