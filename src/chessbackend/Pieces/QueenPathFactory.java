package chessbackend.Pieces;

import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

import java.util.ArrayList;
import java.util.List;

public class QueenPathFactory extends IPiecePathFactory
{
    private MoveFactory _moveFactory = new MoveFactory();

    @Override
    public List<SquareModel> GetPath(SquareModel model, IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        return GetQueensMoves(model, attackingPlayer, defendingPlayer);
    }

    private List<SquareModel> GetQueensMoves(SquareModel model, IPlayer player1, IPlayer player2)
    {
        //Look at its current position and find next possible moves
        List<SquareModel> list = new ArrayList<>();

        list.addAll(FindDiagonalQueenMoves(1, 1, player1, player2, model));
        list.addAll(FindDiagonalQueenMoves(-1, 1, player1, player2, model));
        list.addAll(FindDiagonalQueenMoves(1, -1, player1, player2, model));
        list.addAll(FindDiagonalQueenMoves(-1, -1, player1, player2, model));
        list.addAll(FindNonDiagonalQueenMoves(1, player1, player2, model));
        list.addAll(FindNonDiagonalQueenMoves(-1, player1, player2, model));

        return list;
    }

    private List<SquareModel> FindDiagonalQueenMoves(int xMultiplier, int yMultiplier, IPlayer player1, IPlayer player2, SquareModel model)
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

    private List<SquareModel> FindNonDiagonalQueenMoves(int multiplier, IPlayer player1, IPlayer player2, SquareModel model)
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

