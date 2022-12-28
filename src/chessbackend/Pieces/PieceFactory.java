package chessbackend.Pieces;

import chessbackend.Models.IPlayer;
import chessbackend.Models.PieceModel;
import chessbackend.Models.PieceType;

import java.util.ArrayList;
import java.util.List;

public class PieceFactory
{
    private List<PieceModel> CreatePieces(int row, IPlayer player) {
        List<PieceModel> pieces = new ArrayList<>();

        for(int column = 0; column < 8; column++)
        {
            var piece = new PieceModel();

            piece.PieceColor = player.color;
            piece.currentSquare.xCoord = column;
            piece.currentSquare.yCoord = row;

            if(row == 1 || row == 6)
            {
                piece.pieceType = PieceType.Pawn;
            }
            else if (column == 7 || column == 0)
            {
                piece.pieceType = PieceType.Rook;
            }
            else if (column == 1 || column == 6)
            {
                piece.pieceType = PieceType.Knight;
            }
            else if (column == 2 || column == 5)
            {
                piece.pieceType = PieceType.Bishop;
            }
            else if (column == 3)
            {
                piece.pieceType = PieceType.Queen;
            }
            else if(column == 4)
            {
                piece.pieceType = PieceType.King;
            }

            pieces.add(piece);
        }

        return pieces;
    }

    //With this logic we're assuming whoever go it is
    //Their pieces are on the bottom of the board
    public List<PieceModel> InitializePieces(IPlayer player) {
        List<PieceModel> pieces = new ArrayList<>();
        if(player.IsGo)
        {
            //Bottom set of piece
            for(int row = 0; row <= 1; row++)
            {
                pieces.addAll(CreatePieces(row, player));
            }
        }
        else
        {
            //Top set of pieces
            for (int row = 7; row >= 6; row--)
            {
                pieces.addAll(CreatePieces(row, player));
            }
        }

        return pieces;
    }
}
