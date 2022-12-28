package chessbackend.API;

import chessbackend.API.APIModels.APIInitModel;
import chessbackend.API.APIModels.APIMoveModel;
import chessbackend.CustomExceptions.InvalidKeyNameException;
import chessbackend.CustomExceptions.InvalidNumberOfKeyValuesException;
import chessbackend.CustomExceptions.InvalidValueTypeException;
import chessbackend.Models.Color;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class APIValidation
{
    public void APIMoveModelValid(String json) throws InvalidNumberOfKeyValuesException, InvalidKeyNameException, InvalidValueTypeException, NoSuchFieldException {
        List<String> list = SplitJsonStringIntoList(json);
        //Check list has 8 elements
        InvalidNumberOfKeyValuesThrowException(list, 8);

        List<String> listOfPropNames = new ArrayList<>();
        listOfPropNames.add("newXCoord");
        listOfPropNames.add("newYCoord");
        listOfPropNames.add("oldXCoord");
        listOfPropNames.add("oldYCoord");

        InvalidKeyNameThrowException(list, listOfPropNames);

        for(int i = 0; i < list.size(); i += 2)
        {
            Field f1 = APIMoveModel.class.getField(list.get(i));
            Class type = f1.getType();
            String name = type.getSimpleName();
            if (!CanConvertIntoNumber(list.get(i + 1)) || !name.equals("int"))
            {
                throw new InvalidValueTypeException(list.get(i + 1));
            }
        }
    }

    public void APIInitModelValid(String json) throws InvalidNumberOfKeyValuesException, InvalidKeyNameException, InvalidValueTypeException, NoSuchFieldException {
        List<String> list = SplitJsonStringIntoList(json);
        //Check there's the correct amount of key value pairs
        InvalidNumberOfKeyValuesThrowException(list, 6);

        List<String> listOfPropNames = new ArrayList<>();
        listOfPropNames.add("AgainstComputer");
        listOfPropNames.add("Player1Color");
        listOfPropNames.add("Player1GoFirst");

        InvalidKeyNameThrowException(list, listOfPropNames);

        for(int i = 0; i < list.size(); i+=2)
        {
            Field f1 = APIInitModel.class.getField(list.get(i));
            Class type = f1.getType();
            if (!CanConvertIntoBoolean(list.get(i + 1)) || !type.getSimpleName().equals("Boolean")) {

                Boolean a = CanConvertIntoEnum(list.get(i + 1));
                String name = type.getSimpleName();
                if (!a || !name.equals("Color")) {
                    throw new InvalidValueTypeException(list.get(i + 1));
                }
            }
        }
    }

    private void InvalidNumberOfKeyValuesThrowException(List<String> list, int num) throws InvalidNumberOfKeyValuesException {
        if (list.size() != num)
        {
            throw new InvalidNumberOfKeyValuesException(list.size());
        }
    }

    private void InvalidKeyNameThrowException(List<String> list, List<String> propNames) throws InvalidKeyNameException {
        //Check key names are valid
        for(int i = 0; i < list.size(); i+=2)
        {
            if(!propNames.contains(list.get(i)))
            {
                throw new InvalidKeyNameException(list.get(i));
            }
        }
    }

    private boolean CanConvertIntoEnum(String string)
    {
        try
        {
            Color.valueOf(string);
            return true;
        }
        catch(IllegalArgumentException e)
        {
            return false;
        }
    }

    private boolean CanConvertIntoBoolean(String string)
    {
        String compare = string.toLowerCase();
        if(compare.equals("false") || compare.equals("true"))
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    private boolean CanConvertIntoNumber(String string)
    {
        try
        {
            Double.parseDouble(string);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    public List<String> SplitJsonStringIntoList(String json)
    {
        List<String> listOfAllFieldsAndValues = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < json.length(); i++)
        {
            if(json.charAt(i) == '=' || json.charAt(i) == '&')
            {
                listOfAllFieldsAndValues.add(temp);
                temp = "";
            }
            else
            {
                temp += json.charAt(i);
            }
        }

        listOfAllFieldsAndValues.add(temp);

        return listOfAllFieldsAndValues;
    }
}
