package chessbackend.Models;

import java.util.ArrayList;
import java.util.List;

public abstract class IPlayer
{
    public Boolean IsGo = false;
    public List<PieceModel> Pieces = new ArrayList<>();
    public Boolean InCheck = false;
    public Color color;
}
