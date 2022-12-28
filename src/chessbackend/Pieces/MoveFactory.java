package chessbackend.Pieces;

import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

import java.util.ArrayList;
import java.util.List;

//Contains various helper methods used by the individual piece factories
public class MoveFactory
{
    public List<SquareModel> ReturnPiecesWithinBoard(int newX, int newY)
    {
        SquareModel squareModel = new SquareModel();
        List<SquareModel> list = new ArrayList<>();
        if(newX >= 0 && newX <= 7 && newY >= 0 && newY <= 7)
        {
            squareModel.yCoord = newY;
            squareModel.xCoord = newX;
            list.add(squareModel);
        }

        return list;
    }

    public boolean IsTherePieceInOurWay(int newX, int newY, IPlayer player)
    {
        //As we're creating the moves chronologically
        //We can just stop when we have a piece in the way
        for(int i = 0; i < player.Pieces.size(); i++)
        {
            if(player.Pieces.get(i).currentSquare.xCoord == newX && player.Pieces.get(i).currentSquare.yCoord == newY)
            {
                return true;
            }
        }

        return false;
    }
}
