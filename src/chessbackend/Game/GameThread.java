package chessbackend.Game;

import chessbackend.API.APIModels.APIInitModel;
import chessbackend.API.APIModels.APIMoveModel;
import chessbackend.API.APIModels.APIResponseMoveModel;
import chessbackend.Models.*;
import chessbackend.Pieces.*;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class GameThread
{
    private ModelConverter _modelConverter = new ModelConverter();
    private PieceFactory _pieceFactory = new PieceFactory();
    private CheckLogic _checkLogic = new CheckLogic();
    private MoveLogic _moveLogic = new MoveLogic();
    private PieceLogic _pieceLogic = new PieceLogic();
    private EndGameLogic _endGameLogic = new EndGameLogic();

    private Dictionary<PieceType, IPiecePathFactory> _piecePathFactory = new Hashtable<>();
    private IPlayer player1 =  new PlayerModel();
    private IPlayer player2;

    public GameThread()
    {
        _piecePathFactory.put(PieceType.Queen, new QueenPathFactory());
        _piecePathFactory.put(PieceType.Knight, new KnightPathFactory());
        _piecePathFactory.put(PieceType.King, new KingPathFactory());
        _piecePathFactory.put(PieceType.Pawn, new PawnPathFactory());
        _piecePathFactory.put(PieceType.Bishop, new BishopPathFactory());
        _piecePathFactory.put(PieceType.Rook, new RookPathFactory());
    }

    public APIResponseMoveModel PerformMove(APIMoveModel move) throws Exception
    {
        APIResponseMoveModel response = new APIResponseMoveModel();
        SquareModel newSquare = _modelConverter.TwoCoordsToSquare(move.newXCoord, move.newYCoord);
        SquareModel oldSquare = _modelConverter.TwoCoordsToSquare(move.oldXCoord, move.oldYCoord);

        //We have been given a move to perform
        //We first need to find that piece of the player whose go it is
        IPlayer currentPlayer;
        IPlayer noncurrentPlayer;
        PieceModel selectedPiece = new PieceModel();

        if (player1.IsGo)
        {
            currentPlayer = player1;
            noncurrentPlayer = player2;
        }
        else
        {
            currentPlayer = player2;
            noncurrentPlayer = player1;
        }

        //Find selected piece on board
        boolean pieceFound = _pieceLogic.IsSelectedPieceOnBoard(currentPlayer, oldSquare);

        //Check if the given piece which has been asked to move has been found on the board
        if(!pieceFound)
        {
            response.IsAllowed = false;
            response.HasDrawn = false;
            response.HasWon = false;
            return response;
        }

        //Get the selected piece
        selectedPiece = currentPlayer.Pieces.stream()
                .filter(s -> s.currentSquare.equals(oldSquare))
                .findFirst()
                .get();

        //Get the path factory for the given piece type
        IPiecePathFactory _pathFac = _piecePathFactory.get(selectedPiece.pieceType);

        //Get the list of squares for the given piece's path
        List<SquareModel> currentPiecePath = _pathFac.GetPath(oldSquare, currentPlayer, noncurrentPlayer);

        //Check given new square is at least on its path, if not we can disregard it straight away
        if(!_pieceLogic.IsSquareOnPiecesPath(currentPiecePath, newSquare, response))
        {
            response.IsAllowed = false;
            response.HasDrawn = false;
            response.HasWon = false;
            return response;
        }

        //(IPlayer currentPlayer, IPlayer noncurrentPlayer, SquareModel newSquare, SquareModel oldSquare, APIResponseMoveModel response)
        //Now for the check logic
        if(currentPlayer.InCheck)
        {
            //If we're in check we need to simulate the move, and then check if we're still in check
            if(_checkLogic.SimulateMoveAreWeStillInCheck(currentPlayer, noncurrentPlayer, newSquare, oldSquare, response))
            {
                response.IsAllowed = false;
                response.HasDrawn = false;
                response.HasWon = false;
                return response;
            }
            else //If the simulated move resulted us no longer being in check
            {
                currentPlayer.InCheck = false;
            }
        }

        //If we've simulated the move and it's okay that means we can do the move
        _moveLogic.Move(newSquare, oldSquare, currentPlayer, noncurrentPlayer);
        response.IsAllowed = true;

        //Switch Goes
        if (player1.IsGo)
        {
            player1.IsGo = false;
            player2.IsGo = true;
        }
        else
        {
            player1.IsGo = true;
            player2.IsGo = false;
        }

        //We now need to check whether the other player will be in check
        if(_checkLogic.IsOtherPlayerInCheck(currentPlayer, noncurrentPlayer))
        {
            noncurrentPlayer.InCheck = true;
        }

        if(_endGameLogic.CheckMate(currentPlayer, noncurrentPlayer))
        {
            response.HasDrawn = false;
            response.HasWon = true;
            return response;
        }

        if(_endGameLogic.StaleMate(currentPlayer, noncurrentPlayer))
        {
            response.HasWon = false;
            response.HasDrawn = true;
            return response;
        }

        response.HasWon = false;
        response.HasDrawn = false;
        return response;
    }


    public void InitializeGame(APIInitModel initModel){
        //Initialize the players
        if(initModel.AgainstComputer)
        {
            //player2 = new ComputerModel();
        }
        else
        {
            player2 = new PlayerModel();
        }

        //Set the properties of each player
        player1.color = initModel.Player1Color;
        player1.IsGo = initModel.Player1GoFirst;

        if(player1.IsGo)
        {
            player2.color = Color.Black;
        }
        else
        {
            player2.color = Color.White;
        }

        //Initialize pieces for both players
        player1.Pieces = _pieceFactory.InitializePieces(player1);
        player2.Pieces = _pieceFactory.InitializePieces(player2);
    }
}
