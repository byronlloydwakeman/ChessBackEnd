package chessbackend.CustomExceptions;

public class CoordOutOfBoundsException extends Exception
{
    public CoordOutOfBoundsException(int coord)
    {
        super(String.format("Coord " + coord + " is out of bounds"));
    }
}
