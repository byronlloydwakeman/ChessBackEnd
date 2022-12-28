package chessbackend.Pieces;

import chessbackend.Models.IPlayer;
import chessbackend.Models.PieceModel;
import chessbackend.Models.PieceType;
import chessbackend.Models.SquareModel;

import java.util.ArrayList;
import java.util.List;

public class KingPathFactory extends IPiecePathFactory
{

    @Override
    public List<SquareModel> GetPath(SquareModel model, IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        List<SquareModel> list = new ArrayList<>();

        //Every Square in a 1 square radius, can't go next to king of other player
        PieceModel DefendingKing = defendingPlayer.Pieces.stream().filter(p -> p.pieceType.equals(PieceType.King)).findFirst().get();

        //Get our pieces
        var OurAttackingSquares = Path(model);
        var TheirAttackingSquares = Path(DefendingKing.currentSquare);
        for(int i = 0; i < OurAttackingSquares.size(); i++)
        {
            for (int j = 0; j < TheirAttackingSquares.size(); j++)
            {
                if(OurAttackingSquares.get(i).equals(TheirAttackingSquares.get(j))) list.add(OurAttackingSquares.get(i));
            }
        }

        //Is Our piece in way
        for(int i = 0; i < attackingPlayer.Pieces.size(); i++)
        {
            for(int j = 0; j < list.size(); j++)
            {
                if(attackingPlayer.Pieces.get(i).equals(list.get(j)))
                {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    private List<SquareModel> Path(SquareModel king)
    {
        List<SquareModel> list = new ArrayList<>();
        list.add(new SquareModel(-1, 1));
        list.add(new SquareModel(0, 1));
        list.add(new SquareModel(1, 1));
        list.add(new SquareModel(1, 0));
        list.add(new SquareModel(1, -1));
        list.add(new SquareModel(0, -1));
        list.add(new SquareModel(-1, -1));
        list.add(new SquareModel(-1, 0));

        return list;
    }
}