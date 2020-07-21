package test;

import org.junit.*;
import program.Board;
import program.Cell;

public class TestBoard {
    Board board;
    Cell cell;
    Board test_board;

    @Before
    public void setUp(){
        board = new Board(4,4,4);
        board.createBoard();
        cell = board.cellList.get(0);
        test_board = new Board(4,4,4);
    }

    @Test
    public void testCreateBoard(){
        test_board.createBoard();
        assert(test_board.cellList.size() == 16);
        Integer numOfMines = 0;
        for (Cell each_cell: test_board.cellList) {
            if (each_cell.isMine){
                numOfMines++;
            }
        }
        assert(numOfMines == 4);
    }

    @Test
    public void testGameOver(){
        assert(!board.loose);
        board.gameOver();
        assert(board.loose);
    }

    @Test
    public void testWin(){
        assert(!board.win);
        board.win();
        assert(board.win);
    }

    @Test
    public void testChooseCell(){
        assert(board.chooseCell(0,0) == cell);
    }

    @Test
    public void testIsNeighbour(){
        Cell firstCell = board.chooseCell(0,0);
        Cell secondCell = board.chooseCell(0,1);
        Cell thirdCell = board.chooseCell(0,2);

        assert(board.isNeighbour(firstCell, secondCell));
        assert(!board.isNeighbour(firstCell, thirdCell));
        assert(board.isNeighbour(thirdCell, secondCell));
    }

    @Test
    public void testClickCell(){
        Cell firstCell = board.chooseCell(0,0);
        assert (!firstCell.isShown);
        board.clickCell(firstCell);
        assert (firstCell.isShown);
    }

    @Test
    public void testRightClickCell(){
        Cell firstCell = board.chooseCell(0,0);
        assert (!firstCell.isTagged);
        board.rightClickCell(firstCell);
        assert (firstCell.isTagged);
        board.rightClickCell(firstCell);
        assert (!firstCell.isTagged);
    }
}
