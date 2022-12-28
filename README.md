# ChessBackEnd
ChessBackEnd is the back end logic of a chess application written in Java. The application includes an API which gets called by the front end of the application
(ChessFrontEnd) The API allows the sender to initialize the board by sending key-value pairs which declare the player 1's color and whether we're playing against
a computer. The front-end then sends moves in the form of json to the backend, which the application then uses to keep track of the board. It will then return a 
json object to the sender of whether its an illegal move, or if the game has been won or drawn.
