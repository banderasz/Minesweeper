package test;

import org.junit.*;
import program.GUI;

public class TestGUI {

    private GUI gui;

    @Before
    public void setUp(){
        gui = new GUI(400, 400);
    }

    @Test
    public void testminDimension(){
        assert(gui.minDimension(1,1).equals(330));
        assert(gui.minDimension(10,10).equals(33));
    }
}
