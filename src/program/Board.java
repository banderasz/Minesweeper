package program;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Board implements Serializable {
    public Integer xDim;
    public Integer yDim;
    public Integer numberOfMines;
    public ArrayList<Cell> cellList;
    public Boolean win = false;
    public Boolean loose = false;
    static final long serialVersionUID = 666;


    public Board(Integer xDim, Integer yDim, Integer numberOfMines){
        this.xDim = xDim;
        this.yDim = yDim;
        this.numberOfMines = numberOfMines;
        this.cellList = new ArrayList<Cell>();
    }


    public Cell chooseCell(Integer x, Integer y){
        Cell out = null;
        for (Cell each_cell: this.cellList) {
            if (each_cell.xPosition.equals(x) & each_cell.yPosition.equals(y)){
                out = each_cell;
                break;
            }
        }
        return out;
    }

    public void clickCell(Cell cell){
        if (!cell.isShown) {
            if (cell.isMine) {
                this.gameOver();
                return;
            }
            cell.isShown = true;
            if (cell.neighbourMines.equals(0)) {
                for (Cell each_cell : this.cellList) {
                    if (!each_cell.isMine & !each_cell.isShown) {
                        if (isNeighbour(cell, each_cell)) {
                            this.clickCell(each_cell);
                        }
                    }
                }
            }
        }
        Boolean win = true;
        for (Cell selfCell: this.cellList) {
            if (!selfCell.isMine & !selfCell.isShown){
                win = false;
            }
        }
        if (win){
            this.win();
        }
    }

    public void clickCell(Integer x, Integer y){
        this.clickCell(this.chooseCell(x, y));
    }

    public void rightClickCell(Cell cell){
        cell.isTagged = !cell.isTagged;
    }

    public void rightClickCell(Integer x, Integer y){
        this.rightClickCell(this.chooseCell(x, y));
    }

    public void gameOver(){
//        System.out.println("program.Game over");
        for (Cell selfCell: this.cellList) {
            selfCell.isShown = true;
        }
        this.loose = true;

    }

    public void win(){
//        System.out.println("Win");
        for (Cell selfCell: this.cellList) {
            selfCell.isShown = true;
        }
        this.win = true;
    }

    public Boolean isNeighbour(Cell selfCell, Cell otherCell){
        return (Math.abs(selfCell.xPosition - otherCell.xPosition) == 1 && Math.abs(selfCell.yPosition - otherCell.yPosition) == 1)
                || (Math.abs(selfCell.xPosition - otherCell.xPosition) == 1 && Math.abs(selfCell.yPosition - otherCell.yPosition) == 0)
                || (Math.abs(selfCell.xPosition - otherCell.xPosition) == 0 && Math.abs(selfCell.yPosition - otherCell.yPosition) == 1);
    }

    public void countNeighbourMines(){
        for (Cell selfCell: this.cellList) {
            for (Cell otherCell: this.cellList) {
                if (this.isNeighbour(selfCell, otherCell)){
                    if (otherCell.isMine)
                    {
                        selfCell.neighbourMines -=-1;
                    }
                }

            }

        }
    }

    public void sortBoard(){
        Collections.sort(this.cellList);
    }

    public void createBoard(){
        while (this.numberOfMines > this.cellList.size()){
            Integer xDimCell = (int)(Math.random() * this.xDim);
            Integer yDimCell = (int)(Math.random() * this.yDim);
            if(!isTaken(xDimCell, yDimCell)){
                this.cellList.add(new Cell(xDimCell, yDimCell, true));
            }
        }
        while (this.xDim * this.yDim > this.cellList.size()){
            for (int i = 0; i < this.xDim; i++) {
                for (int j = 0; j < this.yDim; j++) {
                    if(!isTaken(i, j)){
                        this.cellList.add(new Cell(i, j, false));
                    }
                }
            }
        }
        this.sortBoard();
        this.countNeighbourMines();
    }

    public Boolean isTaken(Integer xDimCell, Integer yDimCell){
        Boolean taken = false;
        for (Cell cell: this.cellList) {
            if(cell.xPosition.equals(xDimCell) && cell.yPosition.equals(yDimCell)){
                taken = true;
            }
        }
        return taken;
    }

    public void saveGame(String filename) throws Exception{
        FileOutputStream file = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(file);

        // Method for serialization of object
        out.writeObject(this);

        out.close();
        file.close();
    }

    public static Board importGame(String filename) throws Exception{
        FileInputStream file = new FileInputStream(filename);
        ObjectInputStream in = new ObjectInputStream(file);

        Board out = (Board)in.readObject();

        in.close();
        file.close();

        return out;
    }

}
