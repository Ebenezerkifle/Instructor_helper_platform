package CHAT.OwnerChat;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class MessageHandler {
    private ArrayList<ArrayList<Label>> textMessages;
    private ArrayList<ArrayList<String>> groupHandler;

    public ArrayList<ArrayList<Label>> getTextMessages() {
        return textMessages;
    }

    public void setTextMessages(ArrayList<ArrayList<Label>> textMessages) {
        this.textMessages = textMessages;
    }

    public ArrayList<ArrayList<String>> getGroupHandler() {
        return groupHandler;
    }

    public void setGroupHandler(ArrayList<ArrayList<String>> groupHandler) {
        this.groupHandler = groupHandler;
    }

    MessageHandler(){
        this.groupHandler=new ArrayList<>();
        this.textMessages=new ArrayList<>();
    }


    public ArrayList<Label> getMessageList(String firstElementName){

        ArrayList<Label> textList=new ArrayList<>();
        for(int i=0;i<textMessages.size();i++){
                if(textMessages.get(i).get(0).getText().equals(firstElementName)) {
                    textList=textMessages.get(i);
                    break;
                }

        }
        return textList;
    }

    public void setArrayList(Label firstElementName,Label actualText){
        if(textMessages.isEmpty()){
            createNewArray(firstElementName,actualText);
        }
        else {
            for (int i = 0; i < textMessages.size(); i++) {
                if (firstElementName.getText().equals(textMessages.get(i).get(0).getText())) {
                    textMessages.get(i).add(actualText);
                    break;
                } else {
                    if (i == (textMessages.size() - 1)) {
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
        textMessages.add(arrayList);
    }

    public ArrayList<String> getGroupsList(String firstElementName){
        ArrayList<String> textList=new ArrayList<>();
        for(int i=0;i<textMessages.size();i++){
            if(groupHandler.get(i).get(0).equals(firstElementName)) {
                textList=groupHandler.get(i);
                break;
            }
        }
        return textList;
    }

    public void setGroupArrayList(String firstElementName,String actualText){
            for (int i = 0; i < groupHandler.size(); i++) {
                if (firstElementName.equals(groupHandler.get(i).get(0))) {
                    groupHandler.get(i).add(actualText);
                    break;
                }
            }
    }
    public void createGroupArrayList(String firstElementName) {
        ArrayList<String> arrayList= new ArrayList<>();
        arrayList.add(firstElementName);
        groupHandler.add(arrayList);
    }

}
