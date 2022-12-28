package chessbackend.Pieces;

import chessbackend.Models.IPlayer;
import chessbackend.Models.SquareModel;

import java.util.List;

public abstract class IPiecePathFactory
{
    public abstract List<SquareModel> GetPath(SquareModel model, IPlayer attackingPlayer, IPlayer defendingPlayer);
}
