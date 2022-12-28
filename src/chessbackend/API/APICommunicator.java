package chessbackend.API;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import jsonserializer.JsonConverter;
import chessbackend.API.APIModels.APIErrorModel;
import chessbackend.API.APIModels.APIInitModel;
import chessbackend.API.APIModels.APIMoveModel;
import chessbackend.API.APIModels.APIResponseMoveModel;
import chessbackend.CustomExceptions.InvalidKeyNameException;
import chessbackend.CustomExceptions.InvalidNumberOfKeyValuesException;
import chessbackend.CustomExceptions.InvalidValueTypeException;
import chessbackend.DependencyInjection.Container;
import chessbackend.Game.GameThread;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


public class APICommunicator
{
    private JsonConverter _converter = new JsonConverter();
    private APIConverter _apiConverter = Container.GetAPIConverter();
    private GameThread _gameThread = Container.GetGameThread();
    private APIValidation _APIValidation = new APIValidation();

    private HttpServer _server;
    private int _port = 1234;
    private Boolean initialized = false;
    private APIInitModel _initModel;

    public APICommunicator()
    {
        InitAPI();
    }

    public void APIThread()
    {
        ChessAPIInitialize();

        ChessAPIMove();

        _server.setExecutor(null); // creates a default executor
        _server.start();
    }

    private void InitAPI()
    {
        if(_server == null)
        {
            try
            {
                _server = HttpServer.create(new InetSocketAddress(_port), 0);
                DebugAPI("Server creation complete");
            }
            catch (IOException e)
            {
                DebugAPI("Error creating server at port " + _port);
                e.printStackTrace();
            }
        }
    }

    private void ChessAPIMove()
    {
        _server.createContext("/api/ChessAPI/Move", (exchange ->
        {
            if("POST".equals(exchange.getRequestMethod()))
            {
                if(initialized == false)
                {
                    SendData(exchange, "{\"Error\": \"Please initialize the application first\"}");
                    exchange.close();
                }

                DebugAPI("Post request successfully received");

                try
                {
                    //Get data from the API
                    String value = GetPOSTData(exchange);
                    DebugAPI("Data received: " + value);

                    _APIValidation.APIMoveModelValid(value);

                    //Convert json into model
                    APIMoveModel model = _apiConverter.JsonStringToMoveModel(value);

                    //Get the ResponseMove model
                    var responseMove = PerformMove(model);

                    //Convert APIResponseMoveModel to json string
                    String jsonString = _converter.ObjectToString(responseMove);

                    SendData(exchange, jsonString);
                }
                catch(IllegalArgumentException e)
                {
                    SendData(exchange, String.format("{\"Error\": \"Invalid value\"}"));
                    DebugAPI(e.getMessage());
                }
                catch(NoSuchFieldException e)
                {
                    SendData(exchange, String.format("{\"Error\": \"Invalid key\"}"));
                    DebugAPI(e.getMessage());
                }
                catch(Exception e)
                {
                    SendData(exchange, String.format("{\"Error\": \"" + e.getMessage() +  "\"}"));
                }
            }
            else
            {
                SendData(exchange, "{\"Error\": \"Invalid request\"}");
                DebugAPI("Invalid request");
            }
            exchange.close();
        }));
    }

    private void ChessAPIInitialize()
    {
        _server.createContext("/api/ChessAPI/Initialize", (exchange ->
        {
            var error = new APIErrorModel();
            if(initialized)
            {
                error.ErrorName = "Initialized";
                error.ErrorMessage = "Game has already been initialized";
                SendError(exchange, error);
                exchange.close();
            }

            if("POST".equals(exchange.getRequestMethod()))
            {
                //Get the data sent from the API caller
                String value = GetPOSTData(exchange);

                try
                {
                    //Make sure the data is valid
                    _APIValidation.APIInitModelValid(value);
                    //Convert API data into model
                    _initModel = _apiConverter.JsonStringToInitModel(value);
                    //So we don't re-initialize the board
                    initialized = true;
                    //Initialize the game
                    _gameThread.InitializeGame(_initModel);

                    DebugAPI("Playing against computer: " + _initModel.AgainstComputer);
                    SendData(exchange, "{ \"IsSuccessful\": true}");
                }
                catch (InvalidKeyNameException e)
                {
                    error.ErrorName = "InvalidKeyName";
                    error.ErrorMessage = e.getMessage();
                    SendError(exchange, error);
                    DebugAPI(e.getMessage());
                }
                catch (InvalidNumberOfKeyValuesException e)
                {
                    error.ErrorName = "InvalidNumberOfKeyValues";
                    error.ErrorMessage = "The given request contains invalid number of key value pairs: " + value;
                    SendError(exchange, error);
                    DebugAPI(e.getMessage());
                }
                catch (InvalidValueTypeException e)
                {
                    error.ErrorName = "InvalidValueTypeException";
                    error.ErrorMessage = "The given request has an invalid type for one of it's values: " + value;
                    SendError(exchange, error);
                    DebugAPI(e.getMessage());
                }
                catch (Exception e)
                {
                    error.ErrorName = "Error";
                    error.ErrorMessage = e.getMessage();
                    SendError(exchange, error);
                    DebugAPI(e.getMessage());
                }
            }
            else
            {
                error.ErrorName = "Invalid API request";
                error.ErrorMessage = "The given API request was invalid";
                SendError(exchange, error);
                DebugAPI("Invalid request");
            }
            exchange.close();
        }));
    }

    private String GetPOSTData(HttpExchange exchange) throws IOException
    {
        //Get POST data from API
        var inputStream = exchange.getRequestBody();
        var inputStreamAsByteArray = inputStream.readAllBytes();
        String value = "";
        for (int i = 0; i < inputStreamAsByteArray.length; i++)
        {
            value += (char)inputStreamAsByteArray[i];
        }

        return value;
    }

    private void SendData(HttpExchange exchange, String s) throws IOException
    {
        //Convert string to a json string
        exchange.sendResponseHeaders(200, s.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(s.getBytes());
        output.flush();
    }

    private void SendError(HttpExchange exchange, APIErrorModel e)
    {
        try
        {
            String string = _converter.ObjectToString(e);
            //Convert exception to a json string
            exchange.sendResponseHeaders(200, string.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(string.getBytes());
            output.flush();
        }
        catch (Exception ex)
        {
            DebugAPI(ex.getMessage());
        }
    }

    private APIResponseMoveModel PerformMove(APIMoveModel move) throws Exception {
        //Send move to the back end to be performed, this will give us a returning move model
        //Which can then be sent back to the api caller
        APIResponseMoveModel model = _gameThread.PerformMove(move);

        return model;
    }

    private void DebugAPI(String message)
    {
        System.out.println(message);
    }
}
