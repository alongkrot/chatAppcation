/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerPackages;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Admin
 */
public class Server extends JFrame{
    private JTextField userText; 
    private JTextArea chatWindow; //หน้าจอเเสดงผล
    private ObjectOutputStream output; //ตัวส่งข้อมูลไปหา server
    private ObjectInputStream input; //รับข้อมูล
    private ServerSocket server; 
    private  Socket connection;

    public Server() {
        super("Server Computer");
        userText = new JTextField();
        chatWindow = new JTextArea();
        userText.setEditable(false); //เริ่มต้อไม่สามารถพิมอะไรได้
        userText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMesage(e.getActionCommand());
                userText.setText(""); 
            }
        });  
        
        add(userText,BorderLayout.NORTH);
        add(new JScrollPane(chatWindow));
        setSize(300, 300);
        setVisible(true);
    }
    public void startRunning(){
        try{
            server = new ServerSocket(6789, 100); //6789 เป็น port ส่วน 100 คือจำนวนเครื่องที่มาเชื่อมต่อ
            while(true){
                
                try{
                    waitForConnection();
                    setUpSteam();
                    whileChatting();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    closeObject();
                
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    public void sendMesage(String str) {
        try{
            output.writeObject("SERVER :"+str);
            output.flush();
            showMessage("\nSERVER :"+str);
            
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
        
    }

    private void waitForConnection() {
        try{
            showMessage("รอการเชื่อต่อจาก Client...\n");
            connection = server.accept();
            showMessage("มีการเชื่อต่อเเล้ว :"+connection.getInetAddress().getHostName());
        }catch(Exception e){
            e.printStackTrace();
        
        }
    }

    private void setUpSteam() {
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private void whileChatting() {
        String message = "Now Connecred";
        ableToType(true);
        do{
            try{
                message = (String)input.readObject(); 
                showMessage("\n"+message);
            }catch(Exception e){
                e.printStackTrace();
            }
        }while(!message.equals("CLIENT-END"));
    
    }

    private void ableToType(boolean b) {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                userText.setEditable(b);
            }
        });
    }

    private void closeObject() {
        showMessage("Close Connection\n");
        ableToType(false);
        try{
            output.close();
            input.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void showMessage(String txt) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatWindow.append(txt);
            }
        });    
    }
   
} 
