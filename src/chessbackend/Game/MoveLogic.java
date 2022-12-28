package chessbackend.Game;

import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

public class MoveLogic
{
    //We need to get the new coord, check if theres an opposing piece there, if there is, set its
    //taken to true and then set the current coords of the given piece to
    public void SimulateMove(SquareModel newSquare, SquareModel oldSquare, IPlayer currentPlayer, IPlayer nonCurrentPlayer)
    {
        for(int i = 0; i < nonCurrentPlayer.Pieces.size(); i++)
        {
            //Is there an opposing piece on the square where we wanna go
            if(nonCurrentPlayer.Pieces.get(i).currentSquare.equals(newSquare))
            {
                nonCurrentPlayer.Pieces.get(i).Taken = true;
            }
        }

        //Now find the piece of the old square and change it's pos to the new square
        for(int i = 0; i < currentPlayer.Pieces.size(); i++)
        {
            if(currentPlayer.Pieces.get(i).currentSquare.equals(oldSquare))
            {
                currentPlayer.Pieces.get(i).currentSquare = newSquare;
            }
        }
    }

    public void UndoMove(SquareModel newSquare, SquareModel oldSquare, IPlayer currentPlayer, IPlayer nonCurrentPlayer)
    {
        for(int i = 0; i < nonCurrentPlayer.Pieces.size(); i++)
        {
            if(nonCurrentPlayer.Pieces.get(i).currentSquare.equals(newSquare))
            {
                nonCurrentPlayer.Pieces.get(i).Taken = false;
            }
        }

        //Baso do the opposite, find the newSquare, and replace it with the old one
        for(int i = 0; i < currentPlayer.Pieces.size(); i++)
        {
            if(currentPlayer.Pieces.get(i).currentSquare.equals(newSquare))
            {
                currentPlayer.Pieces.get(i).currentSquare = oldSquare;
            }
        }
    }

    public void Move(SquareModel newSquare, SquareModel oldSquare, IPlayer currentPlayer, IPlayer nonCurrentPlayer)
    {
        for(int i = 0; i < nonCurrentPlayer.Pieces.size(); i++)
        {
            //Is there an opposing piece on the square where we wanna go
            if(nonCurrentPlayer.Pieces.get(i).currentSquare.equals(newSquare))
            {
                nonCurrentPlayer.Pieces.remove(i);
            }
        }

        //Now find the piece of the old square and change it's pos to the new square
        for(int i = 0; i < currentPlayer.Pieces.size(); i++)
        {
            if(currentPlayer.Pieces.get(i).currentSquare.equals(oldSquare))
            {
                currentPlayer.Pieces.get(i).currentSquare = newSquare;
            }
        }
    }
}
