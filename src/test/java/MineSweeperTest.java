import com.msd.MineSweeper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author mahendrasridayarathna
 * @created 16/08/2024 - 1:12 pm
 * @project IntelliJ IDEA
 */
public class MineSweeperTest {
    private MineSweeper game;

    @BeforeEach
    public void setUp() {
        game = new MineSweeper();
    }

    @Test
    public void testInitializeGrid() {
        game.gridSize = 4;
        game.initializeGridView();
        assertEquals(4, game.gridView.length);
        assertEquals(4, game.gridView[0].length);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals('_', game.gridView[i][j]);
            }
        }
    }

    @Test
    public void testPlaceMines() {
        game.gridSize = 4;
        game.mineCount = 3;
        game.initializeGridView();
        game.randomPlaceMines();
        int mineCounter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (game.mines[i][j]) {
                    mineCounter++;
                }
            }
        }
        assertEquals(3, mineCounter);
    }

    @Test
    public void testUncover() {
        game.gridSize = 4;
        game.mineCount = 1;
        game.initializeGridView();
        game.randomPlaceMines();
        game.uncoverItem(0, 0);
        assertTrue(game.uncovered[0][0]);
    }

    @Test
    public void testCountAdjacentMines() {
        game.gridSize = 4;
        game.initializeGridView();
        game.mines[0][1] = true;
        game.mines[1][0] = true;
        assertEquals(2, game.countNextMines(0, 0));
    }

    @Test
    public void testCheckWin() {
        game.gridSize = 4;
        game.mineCount = 1;
        game.initializeGridView();
        game.randomPlaceMines();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!game.mines[i][j]) {
                    game.uncovered[i][j] = true;
                }
            }
        }
        assertTrue(game.checkWin());
    }
}
