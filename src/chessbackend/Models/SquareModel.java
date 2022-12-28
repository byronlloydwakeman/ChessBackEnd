package chessbackend.Models;

public class SquareModel
{
    public int xCoord;
    public int yCoord;

    public SquareModel()
    {

    }

    public SquareModel(int xCoord, int yCoord)
    {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    @Override
    public boolean equals(Object o)
    {
        SquareModel compare = (SquareModel)o;
        if(compare.xCoord == this.xCoord && compare.yCoord == this.yCoord)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
