package chessbackend.API;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import chessbackend.API.APIModels.APIMoveModel;

class APIConverterTest {

    private APIConverter _converter = new APIConverter();

    @Test
    void stringToMoveModel()
    {
        String string = "oldXCoord=1&oldYCoord=2&newXCoord=2&newYCoord=2";
        try
        {
            var model = _converter.JsonStringToMoveModel(string);
            var expected = new APIMoveModel();
            expected.newXCoord = 2;
            expected.newYCoord = 2;
            expected.oldXCoord = 1;
            expected.oldYCoord = 2;

            Assertions.assertEquals(expected.newXCoord, model.newXCoord);
            Assertions.assertEquals(expected.newYCoord, model.newYCoord);
            Assertions.assertEquals(expected.oldXCoord, model.oldXCoord);
            Assertions.assertEquals(expected.oldYCoord, model.oldYCoord);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}