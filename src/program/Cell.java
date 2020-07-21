package program;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class Cell implements Comparable<Cell>, Serializable {
    public Integer xPosition;
    public Integer yPosition;
    public Boolean isMine;
    public Integer neighbourMines;
    public Boolean isShown;
    public Boolean isTagged;
    public Integer type;
    public String special;
    static final long serialVersionUID = 69;

    public Cell(Integer xPosition, Integer yPosition, Boolean isMine){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.isMine = isMine;
        this.neighbourMines = 0;
        this.isShown = false;
        this.isTagged = false;
        type = 0;
        LocalDate date = LocalDate.now();
        if (date.getMonthValue() == 12 && date.getDayOfMonth() < 8){
            special = "Santa_Claus";
        }
        else if(date.getMonthValue() == 12 && date.getDayOfMonth() < 31){
            special = "Christmas";
            type = new Random().nextInt(11);
        }
        else {
            special = "Default";
        }
    }

    @Override
    public int compareTo(Cell o) {
        int i = this.xPosition.compareTo(o.xPosition);
        if (i != 0) return i;

        return this.yPosition.compareTo(o.yPosition);
    }

    public String getIconPath(){
        if (!this.isShown){
            if (this.isTagged){
                return "pics/"+special+"/flag.jpg";
            }
            else {
                return "pics/"+special+"/b" +this.type + ".jpg";
            }
        }
        else {
            if (this.isMine){
                return "pics/"+special+"/mine.jpg";
            }
            else {
                return "pics/"+special+"/" + this.neighbourMines +".jpg";
            }
        }
    }
}
