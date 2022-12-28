package chessbackend;

import chessbackend.API.APICommunicator;

public class Main {

    public static void main(String[] args)
    {
        //Initialize the web server
        APICommunicator com = new APICommunicator();
        com.APIThread();
    }
}
