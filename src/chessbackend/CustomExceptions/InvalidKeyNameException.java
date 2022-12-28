package chessbackend.CustomExceptions;

public class InvalidKeyNameException extends Exception
{
    public InvalidKeyNameException(String name)
    {
        super(String.format("Invalid key name: " + name));
    }
}
