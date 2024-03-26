package com.example.soketprogrammeing;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public Server(ServerSocket s1) throws IOException {
        this.serverSocket = s1;
        this.socket = serverSocket.accept();
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void close(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader){
        try {
                if(bufferedReader != null && bufferedWriter != null){
                    bufferedReader.close();
                    bufferedWriter.close();
                    socket.close();
                }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void reeiveMassageFromClient(VBox vBox_messages) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        String messages = bufferedReader.readLine();
                        System.out.println(messages);
                        HelloController.addLabel(messages, vBox_messages);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("Error in getting messages from client");
                        close(socket, bufferedWriter, bufferedReader);
                        break;
                    }
                }
            }
        }).start();
    }

    public void sendMassageToClient(String messagesToSend) {
        try{
            bufferedWriter.write(messagesToSend);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("Error in messages to client");
            close(socket, bufferedWriter, bufferedReader);
        }

    }
}
