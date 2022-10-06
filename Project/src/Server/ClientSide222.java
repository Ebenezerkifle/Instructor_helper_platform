package Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientSide222  implements Serializable {
    private String userName;
    private String msgToBeSent;
    private String toWhom;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private ClientSide222 user;

    public ClientSide222 getUser() {

        return user;
    }

    public void setUser(ClientSide222 user) {

        this.user = user;
    }



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

    public ClientSide222(){
        this.userName=null;
        this.toWhom=null;
    }

    public static void main(String [] args){
        ClientSide222 user=new ClientSide222();
        user.connect();
    }
    public void connect(){

        try {
            Socket socket=new Socket("localhost",9090);
            System.out.println("connected.");
            this.objectOutputStream=new ObjectOutputStream(socket.getOutputStream());
            this.objectInputStream=new ObjectInputStream(socket.getInputStream());

            Scanner input=new Scanner(System.in);
            ClientSide222 clientInfo=new ClientSide222();
            System.out.println("Pleas enter user name.");
            String name=input.nextLine();
            clientInfo.setUserName(name);
            objectOutputStream.writeObject(clientInfo);
            String msg=(String)objectInputStream.readObject();
            System.out.println(msg);
            messageHandler(name);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void logIn(Socket socket) {
        try {
        Scanner keyboardInput=new Scanner(System.in);
        System.out.println("Enter you user name.");
        String userName=keyboardInput.nextLine();
        this.user.setUserName(userName);
        objectOutputStream.writeObject(getUser());
        ObjectInputStream object=new ObjectInputStream(socket.getInputStream());
        ClientSide222 server= (ClientSide222) object.readObject();
        System.out.println(server.getMsgToBeSent());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void messageHandler(String name) {
        Scanner keyboardInput=new Scanner(System.in);
        Thread send=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        ClientSide222 clientSide=new ClientSide222();
                        String msg = keyboardInput.nextLine();
                        clientSide.setUserName(name);
                        clientSide.setMsgToBeSent(msg);
                        clientSide.setToWhom("Abeni");
                        objectOutputStream.writeObject(clientSide);

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
                        ClientSide222 clientReceived = (ClientSide222) objectInputStream.readObject();
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
