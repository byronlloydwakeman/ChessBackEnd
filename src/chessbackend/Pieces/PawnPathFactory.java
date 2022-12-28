package chessbackend.Pieces;

import chessbackend.Models.Color;
import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

import java.util.ArrayList;
import java.util.List;

public class PawnPathFactory extends IPiecePathFactory
{
    @Override
    public List<SquareModel> GetPath(SquareModel model, IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        List<SquareModel> list = new ArrayList<>();
        list.addAll(GetMovingSquares(model, attackingPlayer, defendingPlayer));
        list.addAll(GetAttackingSquares(model, defendingPlayer));

        return list;
    }

    public List<SquareModel> GetMovingSquares(SquareModel model, IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        List<SquareModel> list = new ArrayList<>();
        //The pawn can only normally move one or two squares up or down depending on whether they're at the top or bottom of the board or whether it's their first or second go
        //We can firstly check the path

        int NoOfGoes = attackingPlayer.Pieces.stream().filter(p -> p.currentSquare.equals(model)).findFirst().get().NumberOfTimesMoved;

        SquareModel oneSquare = new SquareModel();
        SquareModel twoSquare = new SquareModel();
        if(attackingPlayer.color == Color.White)
        {
            oneSquare.xCoord = model.xCoord;
            oneSquare.yCoord = model.yCoord + 1;
            twoSquare.xCoord = model.xCoord;
            twoSquare.yCoord = model.yCoord + 2;
        }
        else
        {
            oneSquare.xCoord = model.xCoord;
            oneSquare.yCoord = model.yCoord - 1;
            twoSquare.xCoord = model.xCoord;
            twoSquare.yCoord = model.yCoord - 2;
        }

        //Is there any of our pieces in the way
        var OurPieceInSquareOne = attackingPlayer.Pieces.stream().anyMatch(p -> p.currentSquare.equals(oneSquare));
        if (OurPieceInSquareOne) return list;

        var TheirPieceInSquareOne = defendingPlayer.Pieces.stream().anyMatch(p -> p.currentSquare.equals(oneSquare));
        list.add(oneSquare);
        if (TheirPieceInSquareOne) return list;

        if(NoOfGoes == 0)
        {
            var OurPieceInSquareTwo = attackingPlayer.Pieces.stream().anyMatch(p -> p.currentSquare.equals(twoSquare));
            if(OurPieceInSquareTwo) return list;

            var TheirPieceInSquareTwo = defendingPlayer.Pieces.stream().anyMatch(p -> p.currentSquare.equals(twoSquare));
            list.add(twoSquare);
            if(TheirPieceInSquareTwo) return list;
        }

        return list;
    }

    public List<SquareModel> GetAttackingSquares(SquareModel model, IPlayer defendingPlayer)
    {
        List<SquareModel> list = new ArrayList<>();
        //We need to look diagonally to the right and left to see if theres an opposing piece there
        //Loop through all pieces

        //This means we're white
        if(defendingPlayer.color == Color.Black)
        {
            SquareModel topRight = new SquareModel();
            topRight.xCoord = model.xCoord + 1;
            topRight.yCoord = model.yCoord + 1;
            boolean isTopRight = defendingPlayer.Pieces.stream().anyMatch(s -> s.currentSquare.equals(topRight));
            if(isTopRight)
            {
                list.add(topRight);
            }

            SquareModel topLeft = new SquareModel();
            topLeft.xCoord = model.xCoord -1;
            topLeft.yCoord = model.yCoord + 1;
            boolean isTopLeft = defendingPlayer.Pieces.stream().anyMatch(s -> s.currentSquare.equals(topLeft));
            if(isTopLeft)
            {
                list.add(topLeft);
            }
        }
        else
        {
            SquareModel bottomRight = new SquareModel();
            bottomRight.xCoord = model.xCoord + 1;
            bottomRight.yCoord = model.yCoord - 1;
            boolean isTopRight = defendingPlayer.Pieces.stream().anyMatch(s -> s.currentSquare.equals(bottomRight));
            if(isTopRight)
            {
                list.add(bottomRight);
            }

            SquareModel bottomLeft = new SquareModel();
            bottomLeft.xCoord = model.xCoord -1;
            bottomLeft.yCoord = model.yCoord - 1;
            boolean isTopLeft = defendingPlayer.Pieces.stream().anyMatch(s -> s.currentSquare.equals(bottomLeft));
            if(isTopLeft)
            {
                list.add(bottomLeft);
            }
        }

        return list;
    }
}
