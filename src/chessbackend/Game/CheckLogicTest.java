package chessbackend.Game;

import org.junit.jupiter.api.Test;
import chessbackend.Models.*;
import chessbackend.Pieces.PieceFactory;

class CheckLogicTest
{
    private CheckLogic _checkLogic = new CheckLogic();
    private PieceFactory _pieceFactory = new PieceFactory();

    @Test
    void isStillInCheck()
    {
        IPlayer attackingPlayer = new PlayerModel();
        IPlayer defendingPlayer = new PlayerModel();

        attackingPlayer.color = Color.White;
        defendingPlayer.color = Color.Black;

        //Initialize pieces for both players

        attackingPlayer.Pieces = _pieceFactory.InitializePieces(attackingPlayer);
        defendingPlayer.Pieces = _pieceFactory.InitializePieces(defendingPlayer);

        long start = System.nanoTime();

        var actual = _checkLogic.IsStillInCheck(defendingPlayer, attackingPlayer, _checkLogic.GetKingsSquare(defendingPlayer));

        long stop = System.nanoTime();

        long time = stop - start;
    }

    @Test
    void isStillInCheckAsync()
    {
        IPlayer attackingPlayer = new PlayerModel();
        IPlayer defendingPlayer = new PlayerModel();

        attackingPlayer.color = Color.White;
        defendingPlayer.color = Color.Black;

        //Initialize pieces for both players

        attackingPlayer.Pieces = _pieceFactory.InitializePieces(attackingPlayer);
        defendingPlayer.Pieces = _pieceFactory.InitializePieces(defendingPlayer);

        long start = System.nanoTime();

        var actual = _checkLogic.IsStillInCheckAsync(defendingPlayer, attackingPlayer);

        long stop = System.nanoTime();

        //4918100
        //6357800
        //12687900
        //7340800
        var time = stop - start;
    }
}