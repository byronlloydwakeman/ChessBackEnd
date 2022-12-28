package chessbackend.Game;

import chessbackend.API.APIModels.APIResponseMoveModel;
import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

import java.util.List;

public class PieceLogic
{
    public boolean IsSelectedPieceOnBoard(IPlayer currentPlayer, SquareModel oldSquare)
    {
        //Find selected piece on board
        if(currentPlayer.Pieces.stream().anyMatch(s -> s.currentSquare.equals(oldSquare)))
        {
            return true;
        }

        return false;
    }

    public boolean IsSquareOnPiecesPath(List<SquareModel> currentPiecePath, SquareModel newSquare, APIResponseMoveModel response)
    {
        //Check given new square is at least on its path, if not we can disregard it straight away
        if(!(currentPiecePath.contains(newSquare)))
        {
            response.IsAllowed = false;
            response.HasWon = false;
            return false;
        }

        return true;
    }
}
