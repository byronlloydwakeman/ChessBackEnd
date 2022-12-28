package chessbackend.DependencyInjection;

import chessbackend.API.APIConverter;
import chessbackend.Game.GameThread;

public class Container
{
    public static GameThread GetGameThread()
    {
        return new GameThread();
    }
    public static APIConverter GetAPIConverter()
    {
        return new APIConverter();
    }

}
