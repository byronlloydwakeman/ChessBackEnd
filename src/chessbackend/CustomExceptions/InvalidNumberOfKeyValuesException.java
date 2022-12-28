package chessbackend.CustomExceptions;

public class InvalidNumberOfKeyValuesException extends Exception
{
    public InvalidNumberOfKeyValuesException(int no)
    {
        super(String.format("Invalid number of key value pairs: ", no));
    }
}
