/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepad;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
//
import FileMenuItems.FileMenuItems;
import EditMenuItems.EditMenuItems;
import FormatMenuItems.FormatMenuItems;
import ViewMenuItems.ViewMenuItems;
import HelpMenuItems.HelpmenuItems;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Aashi
 */
public class Notepad extends JFrame {

    JTextArea TextArea;
    File file;

    public static void main(String[] args) {
        Notepad app = new Notepad();
        app.setVisible(true);
        app.setTitle("Untitled Notepad");
        app.setBackground(Color.RED);
        app.setSize(500, 500);

    }

    public Notepad() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);
        JMenuBar menuBar = new JMenuBar();

        FileMenuItems FMenuItem = new FileMenuItems();
        EditMenuItems EMenuItem = new EditMenuItems();
        FormatMenuItems FoMenuItem = new FormatMenuItems();
        ViewMenuItems VMenuItem = new ViewMenuItems();
        HelpmenuItems HMenuItems = new HelpmenuItems();

        JMenu FileMenu = new JMenu("File");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.HORIZONTAL;

        FileMenu.add(FMenuItem.NewMenuItem);
        FileMenu.add(FMenuItem.NewWindowMenuItem);
        FileMenu.add(FMenuItem.OpenMenuItem);
        FileMenu.add(FMenuItem.SaveMenuItem);
        FileMenu.add(FMenuItem.SaveAsMenuItem);
        FileMenu.add(FMenuItem.PageSetupMenuItem);
        FileMenu.add(FMenuItem.PrintMenuItem);
        FileMenu.add(FMenuItem.ExitMenuItem);

        menuBar.add(FileMenu, c);

        JMenu EditMenu = new JMenu("Edit");
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 3;

        EditMenu.add(EMenuItem.UndoMenuItem);
        EditMenu.add(EMenuItem.CopyMenuItem);
        EditMenu.add(EMenuItem.CutMenuItem);
        EditMenu.add(EMenuItem.PasteMenuItem);
        EditMenu.add(EMenuItem.DeleteMenuItem);
        EditMenu.add(EMenuItem.SearchMenuItem);
        EditMenu.add(EMenuItem.FindMenuItem);
        EditMenu.add(EMenuItem.FindNextMenuItem);
        EditMenu.add(EMenuItem.FindPreviousMenuItem);
        EditMenu.add(EMenuItem.RepalceMenuItem);
        EditMenu.add(EMenuItem.GoToMenuItem);
        EditMenu.add(EMenuItem.SelectAllMenuItem);
        EditMenu.add(EMenuItem.TimeDateMenuItem);

        menuBar.add(EditMenu, c);

        JMenu FormatMenu = new JMenu("Format");
        c.gridx = 6;
        c.gridy = 0;
        c.gridwidth = 3;

        FormatMenu.add(FoMenuItem.WordWrapMenuItem);
        FormatMenu.add(FoMenuItem.FontMenuItem);

        menuBar.add(FormatMenu, c);

        JMenu ViewMenu = new JMenu("View");
        c.gridx = 9;
        c.gridy = 0;
        c.gridwidth = 3;

        ViewMenu.add(VMenuItem.ZoomMenuItem);
        ViewMenu.add(VMenuItem.StatusMenuItem);

        menuBar.add(ViewMenu, c);

        JMenu HelpMenu = new JMenu("Help");
        c.gridx = 12;
        c.gridy = 0;
        c.gridwidth = 3;

        HelpMenu.add(HMenuItems.ViewHelpMenuItem);
        HelpMenu.add(HMenuItems.SendFeedbackMenuItem);
        HelpMenu.add(HMenuItems.AboutNotepadMenuItem);

        menuBar.add(HelpMenu, c);

        setJMenuBar(menuBar);

        TextArea = new JTextArea(500, 500);
        TextArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(TextArea);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 27;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        add(scrollPane, c);
        
        FMenuItem.NewMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextArea.setText("");
            }
        });

        FMenuItem.ExitMenuItem.addActionListener((event) -> System.exit(0));
        FMenuItem.SaveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                saveToFile();
            }
        });

        FMenuItem.OpenMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openFile();
                } catch (IOException ex) {
                    System.out.println("Some Error Occoured");
                }
            }
        });

        HMenuItems.AboutNotepadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame();
                JOptionPane.showMessageDialog(f,"Notepad version 1.0.2\n©Copyright 2020 Aashish Shrestha");
                
            }
        });

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    protected void saveToFile() {
        JFileChooser fileChooser = new JFileChooser();
        int retval = fileChooser.showSaveDialog(null);
        if (retval == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (file == null) {
                return;
            }
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getParentFile(), file.getName() + ".txt");
            }
            try {
                TextArea.write(new OutputStreamWriter(new FileOutputStream(file),
                        "utf-8"));
                setTitle(file.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void openFile() throws FileNotFoundException, IOException {
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null); //replace null with your swing container
        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        BufferedReader in = new BufferedReader(new FileReader(file));
        setTitle(file.getName());
        String line = in.readLine();
        while (line != null) {
            TextArea.append(line + "\n");
            line = in.readLine();
        }

    }

}
