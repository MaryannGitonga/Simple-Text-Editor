import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;

public class TextEditor implements ActionListener{
    
    JFrame editorFrame;
    JTextArea text;
    
    TextEditor(){
        editorFrame = new JFrame("TextEditor");
        
        try{
            // Set theme of frame
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        }catch(Exception e){}
        
        text = new JTextArea();
        
        // Create menu bar       
        JMenuBar menuBar = new JMenuBar();
        
        // Create File menu       
        JMenu menu1 = new JMenu("File");
        
        JMenuItem item1 = new JMenuItem("New");
        JMenuItem item2 = new JMenuItem("Open");
        JMenuItem item3 = new JMenuItem("Save");
        JMenuItem item4 = new JMenuItem("Print");
        
        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        item4.addActionListener(this);
        
        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);
        menu1.add(item4);
        
        // Create Edit Menu       
        JMenu menu2 = new JMenu("Edit");
        
        JMenuItem item5 = new JMenuItem("Cut");
        JMenuItem item6 = new JMenuItem("Copy");
        JMenuItem item7 = new JMenuItem("Paste");
        
        item5.addActionListener(this);
        item6.addActionListener(this);
        item7.addActionListener(this);
        
        menu2.add(item5);
        menu2.add(item6);
        menu2.add(item7);
        
        JMenuItem close = new JMenuItem("Close");
        
        close.addActionListener(this);
        
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(close);
        
        editorFrame.setJMenuBar(menuBar);
        editorFrame.add(text);
        editorFrame.setSize(500, 500);
        editorFrame.show();
    }
    
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        
        if(command.equals("Cut")){
            text.cut();
        }
        else if(command.equals("Copy")){
            text.copy();
        }
        else if(command.equals("Paste")){
            text.paste();
        }
        else if(command.equals("Save")){
            JFileChooser chooser = new JFileChooser("f:");
            
            int saveDialog = chooser.showSaveDialog(null);
            if(saveDialog == JFileChooser.APPROVE_OPTION){
                File filePath = new File(chooser.getSelectedFile().getAbsolutePath());
                
                try{
                    FileWriter writer = new FileWriter(filePath, false);
                    
                    BufferedWriter buffer_w = new BufferedWriter(writer);
                    
                    buffer_w.write(text.getText());
                    buffer_w.flush();
                    buffer_w.close();
                }
                catch(Exception evt){
                    JOptionPane.showMessageDialog(editorFrame, evt.getMessage());
                }
            }
            else{
                JOptionPane.showMessageDialog(editorFrame, "Operation Has Been Cancelled");
            }
        }
        else if(command.equals("Print")){
            try{
                text.print();
            }
            catch(Exception evt){
                JOptionPane.showMessageDialog(editorFrame, evt.getMessage());
            }
        }
        else if(command.equals("Open")){
            JFileChooser chooser = new JFileChooser("f:");
            
            int dialog = chooser.showOpenDialog(null);
            if(dialog == JFileChooser.APPROVE_OPTION){
                File filePath = new File(chooser.getSelectedFile().getAbsolutePath());
                
                try{
                    String line1 = "", lines = "";
                    
                    FileReader reader = new FileReader(filePath);
                    BufferedReader buffer_r = new BufferedReader(reader);
                    
                    lines = buffer_r.readLine();
                    
                    while((line1 = buffer_r.readLine()) != null){
                        lines = lines + "/n" + line1;
                    }
                    
                    // Set the text from file
                    text.setText(lines);
                }
                catch(Exception evt){
                    JOptionPane.showMessageDialog(editorFrame, evt.getMessage());
                }
            }
        }
        else if(command.equals("New")){
            text.setText("");
        }
        else if(command.equals("Close")){
            editorFrame.setVisible(false);
        }
    }
    
    public static void main(String args[]){
        TextEditor editor = new TextEditor();
    }
}
