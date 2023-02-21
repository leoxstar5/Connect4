package com.company;

import javax.swing.*;
import java.awt.*;

public class UIApp extends JFrame
{
    UIApp() {
        // set app
        setTitle("Connect 4 Game!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);

        add(new JLabel("Welcome to Connect 4!"), BorderLayout.CENTER);

        setVisible(true);
    }
}
