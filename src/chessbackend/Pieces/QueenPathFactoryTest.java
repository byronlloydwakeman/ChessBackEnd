package chessbackend.Pieces;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import chessbackend.Models.PlayerModel;
import chessbackend.Models.SquareModel;

class QueenPathFactoryTest {

    QueenPathFactory _queenFactory = new QueenPathFactory();

    @Test
    void CheckStraightRowsAreCorrect()
    {
        PlayerModel player1 = new PlayerModel();
        PlayerModel player2 = new PlayerModel();
        SquareModel origin = new SquareModel();
        origin.yCoord = 7;
        origin.xCoord = 3;

        SquareModel square = new SquareModel();
        square.yCoord = 6;
        square.xCoord = 3;
        SquareModel square2 = new SquareModel();
        square2.yCoord = 5;
        square2.xCoord = 3;
        SquareModel square3 = new SquareModel();
        square3.yCoord = 4;
        square3.xCoord = 3;
        SquareModel square4 = new SquareModel();
        square4.yCoord = 3;
        square4.xCoord = 3;
        SquareModel square5 = new SquareModel();
        square5.yCoord = 2;
        square5.xCoord = 3;
        SquareModel square6 = new SquareModel();
        square6.yCoord = 1;
        square6.xCoord = 3;
        SquareModel square7 = new SquareModel();
        square7.yCoord = 0;
        square7.xCoord = 3;
        SquareModel square8 = new SquareModel();
        square8.yCoord = 7;
        square8.xCoord = 4;
        SquareModel square9 = new SquareModel();
        square9.yCoord = 7;
        square9.xCoord = 5;
        SquareModel square10 = new SquareModel();
        square10.yCoord = 7;
        square10.xCoord = 6;
        SquareModel square11 = new SquareModel();
        square11.yCoord = 7;
        square11.xCoord = 7;
        SquareModel square12 = new SquareModel();
        square12.yCoord = 7;
        square12.xCoord = 2;
        SquareModel square13 = new SquareModel();
        square13.yCoord = 6;
        square13.xCoord = 1;
        SquareModel square14 = new SquareModel();
        square14.yCoord = 6;
        square14.xCoord = 0;

        var actual = _queenFactory.GetPath(origin, player1, player2);

        boolean found = actual.stream().anyMatch(c -> c.equals(square));
        boolean found2 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found3 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found4 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found5 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found6 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found7 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found8 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found9 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found10 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found11 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found12 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found13 = actual.stream().anyMatch(c -> c.equals(square));
        boolean found14 = actual.stream().anyMatch(c -> c.equals(square));

        Assertions.assertEquals(found, true);
        Assertions.assertEquals(found2, true);
        Assertions.assertEquals(found3, true);
        Assertions.assertEquals(found4, true);
        Assertions.assertEquals(found5, true);
        Assertions.assertEquals(found6, true);
        Assertions.assertEquals(found7, true);
        Assertions.assertEquals(found8, true);
        Assertions.assertEquals(found9, true);
        Assertions.assertEquals(found10, true);
        Assertions.assertEquals(found11, true);
        Assertions.assertEquals(found12, true);
        Assertions.assertEquals(found13, true);
        Assertions.assertEquals(found14, true);

    }
}