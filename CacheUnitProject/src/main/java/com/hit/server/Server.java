package com.hit.server;

import com.hit.services.CacheUnitController;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable, PropertyChangeListener {

    public final int port = 12345;
    private boolean isServerRunning = false;
    private  ServerSocket serverSocket;

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        switch ((String) event.getNewValue()){
            case "START":
                if (!isServerRunning)
                {
                	isServerRunning = true;
                    new Thread(this).start();
                }
                else
                	{System.out.println("The server is already running");}
                break;

            case "SHUTDOWN":
                if(!isServerRunning)
                    System.out.println("The server has already shut down");
                else
                {
                    try {
                    	isServerRunning = false;
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("hutdown server");
                }
                break;
        }
    }

    @Override
    public void run() {
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running....");
            ExecutorService executorService = Executors.newFixedThreadPool(20);
            CacheUnitController<String> cacheUnitController = new CacheUnitController<>();

            while (isServerRunning) {
                Socket request = serverSocket.accept();
                HandleRequest<String> handleRequest = new HandleRequest<>(request, cacheUnitController);
                executorService.execute(new Thread(handleRequest));

            }
            executorService.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(serverSocket != null)
                    serverSocket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
