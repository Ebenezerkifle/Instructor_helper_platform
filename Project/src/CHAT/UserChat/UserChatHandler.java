package CHAT.UserChat;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class UserChatHandler {
    ArrayList<ArrayList<Label>> messageArray;

    public UserChatHandler(){
        this.messageArray=new ArrayList<>();
    }

    public ArrayList<ArrayList<Label>> getMessageArray() {
        return messageArray;
    }

    public void setMessageArray(ArrayList<ArrayList<Label>> messageArray) {
        this.messageArray = messageArray;
    }

    public ArrayList<Label> getMessageList(String firstElementName){

        ArrayList<Label> textList=new ArrayList<>();
        for(int i=0;i<messageArray.size();i++){
            if(messageArray.get(i).get(0).getText().equals(firstElementName)) {
                textList=messageArray.get(i);
                break;
            }

        }
        return textList;
    }

    public void setArrayList(Label firstElementName,Label actualText){

        if(messageArray.isEmpty()){
            createNewArray(firstElementName,actualText);
        }
        else {
            for (int i = 0; i < messageArray.size(); i++) {
                if (firstElementName.getText().equals(messageArray.get(i).get(0).getText())) {
                    messageArray.get(i).add(actualText);
                    break;
                } else {
                    if (i == (messageArray.size() - 1)) {
                        createNewArray(firstElementName, actualText);
                        break;
                    }
                }
            }
        }
    }
    private void createNewArray(Label firstElementName, Label actualText) {
        ArrayList<Label> arrayList= new ArrayList<>();
        arrayList.add(firstElementName);
        arrayList.add(actualText);
        messageArray.add(arrayList);
    }

}
