package chessbackend.API;


import chessbackend.API.APIModels.APIInitModel;
import chessbackend.API.APIModels.APIMoveModel;
import chessbackend.Models.Color;

import java.util.List;

public class APIConverter
{
    private APIValidation _validation = new APIValidation();

    public APIMoveModel JsonStringToMoveModel(String string) {
        List<String> list = _validation.SplitJsonStringIntoList(string);

        APIMoveModel model = new APIMoveModel();

        for(int i = 0; i < list.size(); i += 2)
        {
            if(list.get(i).equals("newXCoord"))
            {
                model.newXCoord = Integer.parseInt(list.get(i + 1));
            }
            else if(list.get(i).equals("newYCoord"))
            {
                model.newYCoord = Integer.parseInt(list.get(i + 1));
            }
            else if(list.get(i).equals("oldXCoord"))
            {
                model.oldXCoord = Integer.parseInt(list.get(i + 1));
            }
            else if(list.get(i).equals("oldYCoord"))
            {
                model.oldYCoord = Integer.parseInt(list.get(i + 1));
            }
        }

        return model;
    }

    public APIInitModel JsonStringToInitModel(String string) throws Exception {
        List<String> list = _validation.SplitJsonStringIntoList(string);

        APIInitModel model = new APIInitModel();

        for(int i = 0; i < list.size(); i += 2)
        {
            if(list.get(i).equals("AgainstComputer"))
            {
                model.AgainstComputer = Boolean.valueOf(list.get(i + 1));
            }
            else if(list.get(i).equals("Player1Color"))
            {
                model.Player1Color = Color.valueOf(list.get(i + 1));
            }
            else if(list.get(i).equals("Player1GoFirst"))
            {
                model.Player1GoFirst = Boolean.valueOf(list.get(i + 1));
            }
        }

        return model;
    }

}
