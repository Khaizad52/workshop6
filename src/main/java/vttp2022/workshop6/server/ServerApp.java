package vttp2022.workshop6.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerApp 
{
    public static void main( String[] args )throws IOException
    {
        System.out.println( "Server App" );
        //2 parameter port number from string to integer
        int port = 3001;
        if(args.length > 0)
            port = Integer.parseInt(args[0]);
        //path of the cookie
        String cookieFile = args[1];
        //print to test connection
        System.out.printf("Server app started at %s \n", port);
        //only 2 connection can be at once
        ExecutorService threadPool = Executors.newFixedThreadPool(2);
        // running server that is why never close, ignore the leak
        ServerSocket server = new ServerSocket(port);

            while(true){
                System.out.println("Waiting for Client connection");
                Socket sock = server.accept();
                System.out.println("Connected..");
                //once connected the pass over to client handler
                CookieClientHandler thr = new CookieClientHandler(sock, cookieFile);
                threadPool.submit(thr);
                System.out.println("Submitted to threadpool");

            }
    }
 }
