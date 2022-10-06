package Server;

public class ServerMain {

    public static void main(String [] args){
        int port=9090;
        ServerNextToMain server=new ServerNextToMain(port);
        server.start();
    }
}
