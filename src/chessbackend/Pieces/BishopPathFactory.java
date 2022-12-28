package chessbackend.Pieces;

import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

import java.util.ArrayList;
import java.util.List;

public class BishopPathFactory extends IPiecePathFactory
{
    private MoveFactory _moveFactory = new MoveFactory();
    @Override
    public List<SquareModel> GetPath(SquareModel model, IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        return GetBishopMoves(model, attackingPlayer, defendingPlayer);
    }

    public List<SquareModel> GetBishopMoves(SquareModel model, IPlayer player1, IPlayer player2)
    {
        //Look at it's current position and find next possible moves
        List<SquareModel> list = new ArrayList<>();

        list.addAll(FindBishopMoves(1, 1, player1, player2, model));
        list.addAll(FindBishopMoves(-1, 1, player1, player2, model));
        list.addAll(FindBishopMoves(1, -1, player1, player2, model));
        list.addAll(FindBishopMoves(-1, -1, player1, player2, model));

        return list;
    }

    public List<SquareModel> FindBishopMoves(int xMultiplier, int yMultiplier, IPlayer player1, IPlayer player2, SquareModel model)
    {
        List<SquareModel> list = new ArrayList<>();
        for(int i = 1; i <= 7; i++)
        {
            int newX = model.xCoord + (i * xMultiplier);
            int newY = model.yCoord + (i * yMultiplier);
            if(_moveFactory.IsTherePieceInOurWay(newX, newY, player2))
            {
                list.addAll(_moveFactory.ReturnPiecesWithinBoard(newX, newY));
                break;
            }
            if(_moveFactory.IsTherePieceInOurWay(newX, newY, player1))
            {
                break;
            }
            list.addAll(_moveFactory.ReturnPiecesWithinBoard(newX, newY));
        }

        return list;
    }
}
