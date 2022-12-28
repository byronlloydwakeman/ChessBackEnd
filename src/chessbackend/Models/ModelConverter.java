package chessbackend.Models;

public class ModelConverter
{
    public SquareModel TwoCoordsToSquare(int x, int y)
    {
        SquareModel model = new SquareModel();
        model.xCoord = x;
        model.yCoord = y;

        return model;
    }
}
