package chessbackend.Models;

public class PieceModel
{
    public SquareModel currentSquare = new SquareModel();
    public Color PieceColor;
    public boolean Taken = false;
    public PieceType pieceType;
    public int NumberOfTimesMoved = 0; //Only relevant to pawn
}
