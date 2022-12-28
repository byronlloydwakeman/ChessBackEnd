package chessbackend.Pieces;

import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

import java.util.ArrayList;
import java.util.List;

public class KnightPathFactory extends IPiecePathFactory
{
    private MoveFactory _moveFactory = new MoveFactory();
    public List<SquareModel> GetKnightsMoves(SquareModel model, IPlayer player1, IPlayer player2)
    {
        //Look at it's current position and find next possible moves
        List<SquareModel> list = new ArrayList<>();

        for (int x = -2; x < 3; x++)
        {
            if (x == 0) continue;
            for(int y = -2; y < 3; y++)
            {
                if (y == 0) continue;
                if(!(Math.abs(x) == Math.abs(y)))
                {
                    if(!(_moveFactory.IsTherePieceInOurWay(x, y, player1)))
                    {
                        if(!(_moveFactory.IsTherePieceInOurWay(x, y, player2)))
                        {
                            int newX = model.xCoord + x;
                            int newY = model.yCoord + y;
                            list.addAll(_moveFactory.ReturnPiecesWithinBoard(newX, newY));
                        }
                    }
                }
            }
        }

        return list;
    }

    @Override
    public List<SquareModel> GetPath(SquareModel model, IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        return GetKnightsMoves(model, attackingPlayer, defendingPlayer);
    }
}
