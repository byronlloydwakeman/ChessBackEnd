package chessbackend.CustomExceptions;

public class InvalidValueTypeException extends Exception
{
    public InvalidValueTypeException(String value)
    {
        super(String.format("Value " + value + " is not a valid value type"));
    }
}
