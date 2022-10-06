package CHAT.OwnerChat;

public class StringHandler  {

    public static String nameAfterIgnoringSpace(String nameToBeSplit) {
        String[] arrayListName=nameToBeSplit.split("\\s");
        String textArrayListName="";
        for (String name:arrayListName) {
            textArrayListName+=name;
        }
        return textArrayListName;
    }

    public static String splitText(String msg){
        String[] msgSplit=msg.split(" ");
        String text="";
        int length=10;
        for(int i=0;i<msgSplit.length;i++){
            text+=msgSplit[i]+" ";
            if(i%length==0&& i!=0){
                text+="\n";
            }
        }
        return text;
    }

}
