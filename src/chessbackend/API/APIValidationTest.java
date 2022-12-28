package chessbackend.API;

import org.junit.jupiter.api.Test;
import chessbackend.CustomExceptions.InvalidKeyNameException;
import chessbackend.CustomExceptions.InvalidNumberOfKeyValuesException;
import chessbackend.CustomExceptions.InvalidValueTypeException;

class APIValidationTest {

    private APIValidation _validation = new APIValidation();
    @Test
    void APIMoveModelValid()
    {
        String string = "newXCoord=1&newYCoord=1&oldXCoord=1&oldYCoord=1";

        try
        {
            _validation.APIMoveModelValid(string);
        }
        catch (InvalidNumberOfKeyValuesException | InvalidValueTypeException | InvalidKeyNameException | NoSuchFieldException e) {
            e.printStackTrace();
        }


    }

    @Test
    void APIInitModelValid()
    {
        String string = "AgainstComputer=false&Player1Color=White";

        try
        {
            _validation.APIInitModelValid(string);
        }
        catch (InvalidNumberOfKeyValuesException | InvalidKeyNameException | InvalidValueTypeException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}