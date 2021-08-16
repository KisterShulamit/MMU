package com.hit.client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CacheUnitClient {

    public CacheUnitClient()  {}

    public String send(String request) throws ClassNotFoundException
    {
        String res = "Empty";
        try {
            int serverPort = 12345;
            Socket socket = new Socket("localhost", serverPort);
            Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.println(request);
            writer.flush();

            res = (String) reader.nextLine();
            writer.close();
            reader.close();
            socket.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
