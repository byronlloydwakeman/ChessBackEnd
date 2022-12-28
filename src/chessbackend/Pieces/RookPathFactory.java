package chessbackend.Pieces;

import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

import java.util.ArrayList;
import java.util.List;

public class RookPathFactory extends IPiecePathFactory
{
    private MoveFactory _moveFactory = new MoveFactory();
    @Override
    public List<SquareModel> GetPath(SquareModel model, IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        List<SquareModel> list = new ArrayList<>();

        list.addAll(FindRookMoves(1, model, attackingPlayer, defendingPlayer));
        list.addAll(FindRookMoves(1, model, attackingPlayer, defendingPlayer));

        return list;
    }

    public List<SquareModel> FindRookMoves(int multiplier, SquareModel model, IPlayer player1, IPlayer player2)
    {
        List<SquareModel> list = new ArrayList<>();
        //Vertical
        for(int i = 1; i < 7; i++)
        {
            int newY = model.yCoord + (i * multiplier);
            if(_moveFactory.IsTherePieceInOurWay(model.xCoord, newY, player2))
            {
                list.addAll(_moveFactory.ReturnPiecesWithinBoard(model.xCoord, newY));
                break;
            }
            if(_moveFactory.IsTherePieceInOurWay(model.xCoord, newY, player1))
            {
                break;
            }
            list.addAll(_moveFactory.ReturnPiecesWithinBoard(model.xCoord, newY));
        }

        //Horizontal
        for(int i = 1; i < 7; i++)
        {
            int newX = model.xCoord + (i * multiplier);
            if(_moveFactory.IsTherePieceInOurWay(newX, model.yCoord, player2))
            {
                list.addAll(_moveFactory.ReturnPiecesWithinBoard(newX, model.yCoord));
                break;
            }
            if(_moveFactory.IsTherePieceInOurWay(newX, model.yCoord, player1))
            {
                break;
            }
            list.addAll(_moveFactory.ReturnPiecesWithinBoard(newX, model.yCoord));
        }

        return list;
    }
}
