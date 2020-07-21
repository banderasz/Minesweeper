package program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class NewGameFrame extends JFrame {
    Integer xDim;
    Integer yDim;
    Integer numberOfMines;
    ButtonGroup group;
    JRadioButton easyButton;
    JRadioButton mediumButton;
    JRadioButton hardButton;
    JRadioButton customButton;
    String chosen = "";
    JSpinner xDimSpinner;
    JSpinner yDimSpinner;
    JSpinner mineSpinner;
    GridBagConstraints gbc;

    NewGameFrame(Integer width, Integer height){
        this.setLayout(new GridBagLayout());
        this.setTitle("New Game");
        this.setSize(width, height);
//        newGame = new JButton("New game");

        gbc = new GridBagConstraints();

        easyButton = new JRadioButton("Easy");
        easyButton.setSelected(true);
        mediumButton = new JRadioButton("Medium");
        hardButton = new JRadioButton("Hard");
        customButton = new JRadioButton("Custom");


        group = new ButtonGroup();
        group.add(easyButton);
        group.add(mediumButton);
        group.add(hardButton);
        group.add(customButton);

//        easyButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e)
//            {
//                chosen = "easy";
//            }
//        });
//
//        mediumButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e)
//            {
//                chosen = "medium";
//            }
//        });
//
//        hardButton.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e)
//            {
//                chosen = "hard";
//            }
//        });






        customButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                chosen = "custom";
            }
        });



//        newGame.addActionListener(e -> {
//
//        });


        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridy = 0;
        gbc.gridx = 1;
        this.add(new JLabel("Width"),gbc);
        gbc.gridx = 2;
        this.add(new JLabel("Height"),gbc);
        gbc.gridx = 3;
        this.add(new JLabel("Mines"),gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(easyButton, gbc);
        gbc.gridx = 1;
        this.add(new JLabel("9"),gbc);
        gbc.gridx = 2;
        this.add(new JLabel("9"),gbc);
        gbc.gridx = 3;
        this.add(new JLabel("10"),gbc);


        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(mediumButton, gbc);
        gbc.gridx = 1;
        this.add(new JLabel("16"),gbc);
        gbc.gridx = 2;
        this.add(new JLabel("16"),gbc);
        gbc.gridx = 3;
        this.add(new JLabel("40"),gbc);


        gbc.gridy = 3;
        gbc.gridx = 0;
        this.add(hardButton, gbc);
        gbc.gridx = 1;
        this.add(new JLabel("16"),gbc);
        gbc.gridx = 2;
        this.add(new JLabel("30"),gbc);
        gbc.gridx = 3;
        this.add(new JLabel("99"),gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;

        this.add(customButton, gbc);

//        gbc.gridx = 0;
//        gbc.gridy = 5;
//        this.add(newGame,gbc);

        this.setVisible(true);
    }

    void close(){
        this.dispose();
    }

}
