package chessbackend.Game;

import chessbackend.Models.IPlayer;

public class EndGameLogic
{
    private CheckLogic _checkLogic = new CheckLogic();

    public boolean CheckMate(IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        if (_checkLogic.IsOtherPlayerInCheck(attackingPlayer, defendingPlayer))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean StaleMate(IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        //We know need to check whether we've put the other player in check and if so, have we won the game?
        //We need to look at our attacking squares and look if the king is at the end of them

        if(!_checkLogic.CanOtherPlayersKingMove(attackingPlayer, defendingPlayer, _checkLogic.GetKingsSquare(attackingPlayer)))
        {
            //If the other players king cant move it's either checkmate or stalemate
            if (!_checkLogic.IsOtherPlayerInCheck(attackingPlayer, defendingPlayer))
            {
                return true;
            }
        }

        return false;
    }
}
