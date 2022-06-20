package vttp2022.workshop6.client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApp 
{
    public static void main( String[] args )
    {
            System.out.println("Cookie Client");
            String[] arr = args[0].split(":");
            boolean stop = false;
            InputStream is = null;
            DataInputStream dis = null;
            Socket sock = null;
            OutputStream os = null;
            DataOutputStream dos = null;


            try{
                Console cons = System.console();
                while(!stop){
                    String response = null;
                    String input = cons.readLine("Send command to server");
                    sock = new Socket(arr[0], Integer.parseInt(arr[1]));
                    is = sock.getInputStream();
                    dis = new DataInputStream(is);
                    os = sock.getOutputStream();
                    dos = new DataOutputStream(os);
                    
                    if(input.equals("exit"))
                        stop = true;
                        dos.writeUTF(input);
                        dos.flush();

                    if(!stop){
                        try{
                            response = dis.readUTF();
                        }catch(EOFException e){
                            //to suppress the reading if call twice
                        }

                    if(response != null){
                        if(response.contains("cookie-text") || response.contains("error")){
                            System.out.println(response);
                            String[] cookieValue = response.split(",");
                            if(response.contains("error")){
                                System.out.printf("Error from server >> %s\n", cookieValue[1]);
                            }
                            if(response.contains("cookie-text,")){
                                System.out.printf("Cookie from server >> %s\n", cookieValue[1]);
                            }
                }

            }
        }

    }
    System.out.println("Closing...");
    is.close();
    os.close();
    sock.close();


            } catch(NumberFormatException ex){
                ex.printStackTrace();

            } catch(UnknownHostException ex){
                ex.printStackTrace();

            } catch(IOException ex){
                ex.printStackTrace();
            }
    }
}