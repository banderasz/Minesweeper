package program;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Enumeration;

public class GUI {
    Board board;
    JFrame frame;
    JPanel panel;
    JMenuBar menuBar;
    JMenu menuGame;
    JMenuItem newGame;
    JMenuItem loadGame;
    JMenuItem saveGame;
    JLabel label;
    Integer fugue = 1;


    public GUI(Integer width, Integer height){
        this.frame = new JFrame("Minesweeper");
        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                try {
                    refreshBoard();
                }
                catch (NullPointerException ex){
                }
            }
        });
        this.panel = new JPanel();
        this.panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)){
                    clickToCell(e.getX(), e.getY());
                }
                else if (SwingUtilities.isRightMouseButton(e)){
                    rightClickToCell(e.getX(), e.getY());
                }
            }
        });
        panel.setLayout(null);
        frame.add(panel, BorderLayout.CENTER);
        label = new JLabel("New Game started");
        frame.setSize(width, height);
        panel.setSize(width-20, height-20);
        this.createMenu();
    }

    void clickToCell(Integer x, Integer y){
        Integer cellextension = minDimension(this.board.xDim, this.board.yDim);
        Integer startx = (panel.getWidth() - this.board.xDim*(cellextension+fugue))/2;
        Integer starty = (panel.getHeight() - this.board.yDim*(cellextension+fugue))/2;

        float clickedX = (float) (x - startx) /(cellextension+fugue);
        float clickedY = (float) (y - starty) /(cellextension+fugue);
        if (clickedX-(int) clickedX > 0.1 & clickedY-(int) clickedY > 0.1 & (int) clickedX >= 0 & (int) clickedX < board.xDim & (int) clickedY >= 0 & (int) clickedY < board.xDim){
            board.clickCell((int) clickedX, (int) clickedY);
            refreshBoard();
        }

    }

    void rightClickToCell(Integer x, Integer y){
        Integer cellextension = minDimension(this.board.xDim, this.board.yDim);
        Integer startx = (panel.getWidth() - this.board.xDim*(cellextension+fugue))/2;
        Integer starty = (panel.getHeight() - this.board.yDim*(cellextension+fugue))/2;

        float clickedX = (float) (x - startx) /(cellextension+fugue);
        float clickedY = (float) (y - starty) /(cellextension+fugue);
        if (clickedX-(int) clickedX > 0.1 & clickedY-(int) clickedY > 0.1 & (int) clickedX >= 0 & (int) clickedX < board.xDim & (int) clickedY >= 0 & (int) clickedY < board.xDim){
            board.rightClickCell((int) clickedX, (int) clickedY);
            refreshBoard();
        }

    }

    void createMenu(){
        menuBar = new JMenuBar();
        menuGame = new JMenu("Game");
        menuBar.add(menuGame);
        newGame = new JMenuItem("New game");
        menuGame.add(newGame);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGameMenu();
            }
        });
        loadGame = new JMenuItem("Load game");
        menuGame.add(loadGame);
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                int returnValue = jfc.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    try {
                        board = loadGameMenu(selectedFile.getAbsolutePath());
                        refreshBoard();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        saveGame = new JMenuItem("Save game");
        menuGame.add(saveGame);
        saveGame.setEnabled(false);
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

                int returnValue = jfc.showSaveDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    try {
                        saveGameMenu(selectedFile.getAbsolutePath());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        frame.setJMenuBar(menuBar);

    }

    void newGameMenu(){
        NewGameFrame newGameFrame = new NewGameFrame(300,200);

        SpinnerModel dimXModel = new SpinnerNumberModel(10, 1, 100, 1);
        SpinnerModel dimYModel = new SpinnerNumberModel(10, 1, 100, 1);
        SpinnerModel mineModel = new SpinnerNumberModel(30, 1, 100, 1);
        newGameFrame.xDimSpinner = new JSpinner(dimXModel);
        newGameFrame.yDimSpinner = new JSpinner(dimYModel);
        newGameFrame.mineSpinner = new JSpinner(mineModel);

        newGameFrame.gbc.gridy = 4;
        newGameFrame.gbc.gridx = 0;

        newGameFrame.gbc.gridx = 1;
        newGameFrame.add(newGameFrame.xDimSpinner, newGameFrame.gbc);
        newGameFrame.gbc.gridx = 2;
        newGameFrame.add(newGameFrame.yDimSpinner, newGameFrame.gbc);
        newGameFrame.gbc.gridx = 3;
        newGameFrame.add(newGameFrame.mineSpinner, newGameFrame.gbc);

        JButton newGame = new JButton("New game");
        newGameFrame.gbc.gridx = 0;
        newGameFrame.gbc.gridy = 5;
        newGameFrame.add(newGame,newGameFrame.gbc);
        newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                for (Enumeration<AbstractButton> buttons = newGameFrame.group.getElements(); buttons.hasMoreElements();) {
                    AbstractButton button = buttons.nextElement();

                    if (button.isSelected()) {
                        newGameFrame.chosen = button.getText();
                    }
                }
                switch (newGameFrame.chosen){
                    case "":
                        break;
                    case "Easy":
                        newGameFrame.xDim = 9;
                        newGameFrame.yDim = 9;
                        newGameFrame.numberOfMines = 10;
                        break;
                    case "Medium":
                        newGameFrame.xDim = 16;
                        newGameFrame.yDim = 16;
                        newGameFrame.numberOfMines = 40;
                        break;
                    case "Hard":
                        newGameFrame.xDim = 16;
                        newGameFrame.yDim = 30;
                        newGameFrame.numberOfMines = 99;
                        break;
                    case "Custom":
                        newGameFrame.xDim = (Integer) newGameFrame.xDimSpinner.getValue();
                        newGameFrame.yDim = (Integer) newGameFrame.yDimSpinner.getValue();
                        newGameFrame.numberOfMines = (Integer) newGameFrame.mineSpinner.getValue();

                        if (newGameFrame.xDim * newGameFrame.yDim <= newGameFrame.numberOfMines){
                            newGameMenu();
                        }
                        break;
                }
                newGameFrame.dispose();
                createBoard(newGameFrame.xDim, newGameFrame.yDim, newGameFrame.numberOfMines);
                drawBoard();
            }
        });
    }

    void saveGameMenu(String filename) {
        try {
            board.saveGame(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Board loadGameMenu(String filename) {
        try {
            this.saveGame.setEnabled(true);
            return Board.importGame(filename);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    void drawBoard(){
        frame.setContentPane(panel);
        frame.validate();
        frame.repaint();
        frame.setVisible(true);
    }

    public Integer minDimension(Integer xDim, Integer yDim){
        if(this.panel.getWidth()/xDim > (this.panel.getHeight())/yDim){
            return (this.panel.getHeight()-50)/yDim;
        }
        else {
            return (this.panel.getWidth()-50)/xDim;
        }

    }

    void createBoard(Integer xDim, Integer yDim, Integer numberOfMines){
        this.saveGame.setEnabled(true);
        this.frame.getContentPane().removeAll();
        this.board = new Board(xDim, yDim, numberOfMines);
        this.board.createBoard();
        Integer cellextension = minDimension(this.board.xDim, this.board.yDim);
        Integer startx = (panel.getWidth() - this.board.xDim*(cellextension+fugue))/2;
        Integer starty = (panel.getHeight() - this.board.yDim*(cellextension+fugue))/2;
        for (Cell cell: this.board.cellList) {
            ImageIcon icon = new ImageIcon(cell.getIconPath());
            Image scaleImage = icon.getImage().getScaledInstance(cellextension, cellextension,Image.SCALE_DEFAULT);
            ImageIcon resizedIcon = new ImageIcon(scaleImage);
            JLabel cellgraphic = new JLabel(resizedIcon);
            cellgraphic.setBounds(startx + cell.xPosition*(cellextension+fugue), starty + cell.yPosition*(cellextension+fugue), cellextension, cellextension);
            this.panel.add(cellgraphic);
        }
        this.frame.repaint();
    }

    void refreshBoard(){
        this.frame.getContentPane().removeAll();
        Integer cellextension = minDimension(this.board.xDim, this.board.yDim);
        Integer startx = (panel.getWidth() - this.board.xDim*(cellextension+fugue))/2;
        Integer starty = (panel.getHeight() - this.board.yDim*(cellextension+fugue))/2;
        for (Cell cell: this.board.cellList) {
            ImageIcon icon = new ImageIcon(cell.getIconPath());
            Image scaleImage = icon.getImage().getScaledInstance(cellextension, cellextension,Image.SCALE_DEFAULT);
            ImageIcon resizedIcon = new ImageIcon(scaleImage);
            JLabel cellgraphic = new JLabel(resizedIcon);
            cellgraphic.setBounds(startx + cell.xPosition*(cellextension+fugue), starty + cell.yPosition*(cellextension+fugue), cellextension, cellextension);
            this.panel.add(cellgraphic);
        }
        if (this.board.loose){
            this.label.setText("Game over");
        }
        else if (this.board.win){
            this.label.setText("Win");
        }
        this.frame.repaint();
    }
}
