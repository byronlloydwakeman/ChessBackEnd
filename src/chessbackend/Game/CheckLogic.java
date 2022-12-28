package chessbackend.Game;

import chessbackend.API.APIModels.APIResponseMoveModel;
import chessbackend.Models.IPlayer;
import chessbackend.Models.PieceModel;
import chessbackend.Models.PieceType;
import chessbackend.Models.SquareModel;
import chessbackend.Pieces.*;

import java.util.*;

public class CheckLogic
{
    private MoveLogic _moveLogic = new MoveLogic();

    private Dictionary<PieceType, IPiecePathFactory> _piecePathFactory = new Hashtable<>();

    public CheckLogic()
    {
        _piecePathFactory.put(PieceType.Queen, new QueenPathFactory());
        _piecePathFactory.put(PieceType.Knight, new KnightPathFactory());
        _piecePathFactory.put(PieceType.King, new KingPathFactory());
        _piecePathFactory.put(PieceType.Pawn, new PawnPathFactory());
        _piecePathFactory.put(PieceType.Bishop, new BishopPathFactory());
        _piecePathFactory.put(PieceType.Rook, new RookPathFactory());
    }

    public SquareModel GetKingsSquare(IPlayer currentPlayer)
    {
        //Finding the kings position
        SquareModel kingsPosition = new SquareModel();
        for (int i = 0; i < currentPlayer.Pieces.size(); i++)
        {
            if(currentPlayer.Pieces.get(i).pieceType.equals(PieceType.King))
            {
                kingsPosition = currentPlayer.Pieces.get(i).currentSquare;
            }
        }

        return kingsPosition;
    }

    public boolean IsStillInCheck(IPlayer noncurrentPlayer, IPlayer currentPlayer, SquareModel kingsPosition)
    {
        //We need to loop through every single noncurrentPlayer's pieces' paths and check if our king
        //is at the end of them

        for(int i = 0; i < noncurrentPlayer.Pieces.size(); i++)
        {
            PieceModel piece = noncurrentPlayer.Pieces.get(i);
            if(!piece.Taken)
            {
                IPiecePathFactory _pathFac = _piecePathFactory.get(piece.pieceType);
                List<SquareModel> currentPiecePath = _pathFac.GetPath(piece.currentSquare, noncurrentPlayer, currentPlayer);

                if(currentPiecePath.contains(kingsPosition))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean IsStillInCheckAsync(IPlayer defendingPlayer, IPlayer attackingPlayer)
    {
        //Finding the kings position
        SquareModel kingsPosition = new SquareModel();
        for (int i = 0; i < defendingPlayer.Pieces.size(); i++)
        {
            if(defendingPlayer.Pieces.get(i).pieceType.equals(PieceType.King))
            {
                kingsPosition = defendingPlayer.Pieces.get(i).currentSquare;
            }
        }

        //We need an async collection to add the results of the IsStillInCheck calls
        Collection<Boolean> PieceAttackingKingCollection = Collections.synchronizedCollection(new ArrayList<>());
        Collection<Thread> CollectionOfThreads = Collections.synchronizedCollection(new ArrayList<>());
        for (int i = 0; i < attackingPlayer.Pieces.size(); i++)
        {
            int finalI = i;
            SquareModel finalKingsPosition = kingsPosition;
            var thread = new Thread(() ->
            {
                PieceAttackingKingCollection.addAll(Collections.singleton(IsPieceAttackingPlayersSquare(attackingPlayer.Pieces.get(finalI), finalKingsPosition, attackingPlayer, defendingPlayer)));
            });
            thread.run();
            CollectionOfThreads.add(thread);
        }

        for(Thread thread : CollectionOfThreads)
        {
            try
            {
                thread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        if(PieceAttackingKingCollection.contains(true))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean IsPieceAttackingPlayersSquare(PieceModel piece, SquareModel squareModel, IPlayer attackingPlayer, IPlayer defendingPlayer)
    {
        if(!piece.Taken)
        {
            IPiecePathFactory _pathFac = _piecePathFactory.get(piece.pieceType);
            List<SquareModel> currentPiecePath = _pathFac.GetPath(piece.currentSquare, attackingPlayer, defendingPlayer);

            if(currentPiecePath.contains(squareModel))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    public boolean SimulateMoveAreWeStillInCheck(IPlayer currentPlayer, IPlayer noncurrentPlayer, SquareModel newSquare, SquareModel oldSquare, APIResponseMoveModel response)
    {
        //We first need to simulate the move and have a look on the
        //board to see if anyone is attacking the king
        _moveLogic.SimulateMove(newSquare, oldSquare, currentPlayer, noncurrentPlayer);
        if(IsStillInCheck(noncurrentPlayer, currentPlayer, GetKingsSquare(currentPlayer)))
        {
            response.IsAllowed = false;
            response.HasWon = false;
            _moveLogic.UndoMove(newSquare, oldSquare, currentPlayer, noncurrentPlayer);
            return true;
        }
        _moveLogic.UndoMove(newSquare, oldSquare, currentPlayer, noncurrentPlayer);
        return false;
    }

    public boolean IsOtherPlayerInCheck(IPlayer currentPlayer, IPlayer noncurrentPlayer)
    {
         return IsStillInCheck(currentPlayer, noncurrentPlayer, GetKingsSquare(noncurrentPlayer));
    }

    public boolean CanOtherPlayersKingMove(IPlayer attackingPlayer, IPlayer defendingPlayer, SquareModel kingsSquare)
    {
        //Check every square around him to see if either we're attacking it, or their piece is in the way
        List<SquareModel> path = _piecePathFactory.get(PieceType.King).GetPath(kingsSquare, attackingPlayer, defendingPlayer);

        //Loop through the king's path, if we're not attacking a given square and there isn't a piece in the way, then we can return true
        for(int i = 0; i < path.size(); i++)
        {
            for(int j = 0; j < attackingPlayer.Pieces.size(); j++)
            {
                //Are we attacking?
                if(!IsPieceAttackingPlayersSquare(attackingPlayer.Pieces.get(j), path.get(i), attackingPlayer, defendingPlayer))
                {
                    boolean IsPieceInWay = false;
                    for(int k = 0; k < defendingPlayer.Pieces.size(); k++)
                    {
                        if(path.get(i).equals(defendingPlayer.Pieces.get(k).currentSquare))
                        {
                            IsPieceInWay = true;
                        }
                    }
                    if(!IsPieceInWay) return true;
                }
            }
        }

        return false;
    }
}
