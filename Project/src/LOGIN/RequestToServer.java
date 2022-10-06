package LOGIN;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class RequestToServer implements Serializable {
    private String userName;
    private String msgToBeSent;
    private String toWhom;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ClientSide getUser() {
        return user;
    }

    public void setUser(ClientSide user) {
        this.user = user;
    }

    private ClientSide user;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsgToBeSent() {
        return msgToBeSent;
    }

    public void setMsgToBeSent(String msgToBeSent) {
        this.msgToBeSent = msgToBeSent;
    }

    public String getToWhom() {
        return toWhom;
    }

    public void setToWhom(String toWhom) {
        this.toWhom = toWhom;
    }

    public RequestToServer(){
        this.userName=null;
        this.toWhom=null;
    }

    public static void sendRequest(){
        RequestToServer user= new RequestToServer();
        user.connect();
    }
    public void connect(){

        try {

            Socket socket=new Socket("localhost",9090);
            System.out.println("connected.");
            this.objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream=new ObjectInputStream(socket.getInputStream());

            Scanner input=new Scanner(System.in);
            ClientSide clientInfo=new ClientSide();
            System.out.println("Pleas enter user name.");
            String userName=input.nextLine();
            clientInfo.setUserName(userName);
            objectOutputStream.writeObject(clientInfo);
            String msg=(String)objectInputStream.readObject();
            System.out.println(msg);
            messageHandler(userName);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void messageHandler(String userName) {
        Scanner keyboardInput=new Scanner(System.in);
        Thread send=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        RequestToServer client=new RequestToServer();
                        client.setUserName(userName);
                        String msg = keyboardInput.nextLine();
                        client.setMsgToBeSent(msg);
                        client.setToWhom("Beni");
                        objectOutputStream.writeObject(client);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        send.start();

        Thread readMessage=new Thread(new Runnable(){

            @Override
            public void run() {
                while (true) {
                    try {
                        ClientSide clientReceived = (ClientSide) objectInputStream.readObject();
                        String sender = clientReceived.getUserName();
                        String receivedMsg = clientReceived.getMsgToBeSent();
                        System.out.println(sender + " sent you " + receivedMsg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        readMessage.start();
    }


}
