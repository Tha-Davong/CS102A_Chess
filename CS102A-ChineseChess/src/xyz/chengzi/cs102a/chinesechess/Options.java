package xyz.chengzi.cs102a.chinesechess;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Options extends JFrame implements ActionListener {
    private JRadioButton jcomp1;
    private JList jcomp2;
    private JSlider Sound;

    public Options() {
        //construct preComponents
        String[] jcomp2Items = {"Theme 1", "Theme 2", "Theme 3"};

        //construct components
        jcomp1 = new JRadioButton ("Enable Sound");
        jcomp2 = new JList (jcomp2Items);
        Sound = new JSlider (0, 20);

        //set components properties
        Sound.setOrientation (JSlider.HORIZONTAL);
        Sound.setMinorTickSpacing (1);
        Sound.setMajorTickSpacing (5);
        Sound.setPaintTicks (false);
        Sound.setPaintLabels (false);

        //adjust size and set layout
        setPreferredSize (new Dimension (140, 231));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (Sound);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (15, 10, 150, 25);
        jcomp2.setBounds (25, 105, 90, 85);
        Sound.setBounds (20, 40, 100, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

