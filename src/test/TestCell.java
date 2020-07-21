package test;

import org.junit.*;
import program.Cell;

public class TestCell {

    private Cell cell1;
    private Cell cell2;
    private Cell cell3;

    @Before
    public void setUp(){
        cell1 = new Cell(1,1,false);
        cell2 = new Cell(2,2,true);
        cell3 = new Cell(3,3,false);
    }

    @Test
    public void testFetIconPath(){
        assert(cell1.getIconPath().equals("pics/b" +cell1.type + ".jpg"));
        cell2.isShown = true;
        assert(cell2.getIconPath().equals("pics/mine.jpg"));
        cell3.isShown = true;
        assert(cell3.getIconPath().equals("pics/0.jpg"));
    }

    @Test
    public void testCompareTo(){
        assert(cell1.compareTo(cell2) < 0);
        assert(cell2.compareTo(cell1) > 0);
        assert(cell2.compareTo(cell3) < 0);
        assert(cell3.compareTo(cell2) > 0);
        assert(cell1.compareTo(cell3) < 0);
        assert(cell3.compareTo(cell1) > 0);
    }
}