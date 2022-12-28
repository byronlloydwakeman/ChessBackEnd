package chessbackend.Pieces;

import org.junit.jupiter.api.Test;
import chessbackend.Models.IPlayer;
import chessbackend.Models.PieceModel;
import chessbackend.Models.PlayerModel;
import chessbackend.Models.SquareModel;

class PawnPathFactoryTest
{
    PawnPathFactory _pawnPathFactory = new PawnPathFactory();

    @Test
    void getPath() {
    }

    @Test
    void getAttackingSquares()
    {
        SquareModel model = new SquareModel();
        model.xCoord = 3;
        model.yCoord = 0;

        PieceModel rook = new PieceModel();
        rook.currentSquare.xCoord = 2;
        rook.currentSquare.yCoord = 1;
        IPlayer player2 = new PlayerModel();
        player2.Pieces.add(rook);

        var list = _pawnPathFactory.GetAttackingSquares(model, player2);
    }
}