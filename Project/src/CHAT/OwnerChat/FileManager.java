package CHAT.OwnerChat;


import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private final HBox textWriteHBox;
    private final VBox chatBox;
    private final TextField textMessage;
    private final ArrayList<Label> textMessageLabelArray;
    private final Button optionButton;
    private final MessageHandler messageHandler;
    private final ObjectOutputStream objectOutputStream;
    private final ArrayList<Label> groupChatArrayList;
    private final FlowPane flowPaneId;
    private String filePath;
    private final String[] wordExtensions={"*.doc","*.docx","*.DOCX"};
    private final String pdfExtension="*.pdf";
    private final String[] imageExtension={"*.jpg","*.PNG"};

    public String[] getImageExtension() {
        return imageExtension;
    }

    public String getPdfExtension() {
        return pdfExtension;
    }

    public String[] getWordExtensions() {
        return wordExtensions;
    }

    private VBox membersVBox;
    private ArrayList<Label> membersList;
    private Text headerTextId;
    private String idNumber;


    public String getIdNumber() {
        return idNumber;
    }

    public FileManager(
            VBox membersVBox, Text headerTextId,
            HBox textWriteHBox, VBox chatBox,
            TextField textMessage, ArrayList<Label> textMessageLabelArray,
            Button optionButton, MessageHandler messageHandler ,
            ObjectOutputStream objectOutputStream,ArrayList<Label> groupChatArrayList
            ,FlowPane flowPaneId){
        this.headerTextId=headerTextId;
        this.membersVBox=membersVBox;
        this.textWriteHBox=textWriteHBox;
        this.chatBox=chatBox;
        this.textMessage=textMessage;
        this.textMessageLabelArray=textMessageLabelArray;
        this.optionButton=optionButton;
        this.messageHandler=messageHandler;
        this.objectOutputStream=objectOutputStream;
        this.groupChatArrayList=groupChatArrayList;
        this.flowPaneId=flowPaneId;
    }




    public ArrayList<Label> getMembersList(){
        return membersList;
    }

    public  void browseFile(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("File",wordExtensions));
        File file=fileChooser.showOpenDialog(null);
        if(file!=null) {
            this.filePath=file.getAbsolutePath();
            String extension = "";

            int i = file.getName().lastIndexOf('.');
            if (i > 0) {
                extension = file.getName().substring(i+1);
            }
          readFile(extension);
        }

    }

    public  void readFile(String extension)  {
        Label label=null;
        WordExtractor extractor=null;
        membersList=new ArrayList<>();
        ArrayList<String> members=new ArrayList<>();
        members.add(headerTextId.getText());
        try {
            FileInputStream fileInputStream=new FileInputStream(filePath);
            if(extension.equals("docx")){
                XWPFDocument document= new XWPFDocument(fileInputStream);
                List<XWPFParagraph> paragraphs = document.getParagraphs();

                for (XWPFParagraph para : paragraphs) {
                    if(para.getText().contains("ATR")) {
                        System.out.println(para.getText());
                        String[] name=para.getText().split("\\s");

                        label=LabelCreate.setGroupMembersLabel(name[0]+" "+name[1]);

                        messageHandler.setGroupArrayList(headerTextId.getText(),para.getText());


                        members.add(para.getText());
                        label.setOnMouseClicked((event -> {
                            this.idNumber=name[2];
                            LabelActionHandler.handleGroupMembersEvent(event,
                                    headerTextId,para.getText(),
                                    chatBox,optionButton,messageHandler,textWriteHBox);

                        }));
                        membersVBox.getChildren().add(label);
                        this.membersList.add(label);
                    }
                }
                objectOutputStream.writeObject("addMembers");
                objectOutputStream.writeObject(members);
                fileInputStream.close();
            }
            else {
                HWPFDocument document = new HWPFDocument(fileInputStream);
               extractor = new WordExtractor(document);
                String[] fileData = extractor.getParagraphText();
                for (int i = 0; i < fileData.length; i++)
                {
                    if (fileData[i] != null) {
                        System.out.println(fileData[i]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File browseFilesToSend(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documents",pdfExtension),
                new FileChooser.ExtensionFilter("Image",getImageExtension()),
                new FileChooser.ExtensionFilter("word",getWordExtensions()));

        File file=fileChooser.showOpenDialog(null);
        if(file!=null) {
            this.filePath = file.getAbsolutePath();
            try {
                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                String extension = "";

                int i = file.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = file.getName().substring(i + 1);
                }
                System.out.println(extension);
                Image image = null;
                if (extension.equals("jpg") || extension.equals("PNG")) {
                    image = new Image(fileInputStream, 100, 120,
                            false, false);
                } else if (extension.equals("pdf")) {
                    FileInputStream fileInputStream1 = new FileInputStream(
                            "/Users/mac/Documents/JAVA/Project/src/Adobe-PDF-Document-icon.png");
                    image = new Image(fileInputStream1, 100, 60, false, false);
                }
                Label label = new Label(file.getName());
                ImageView imageView = new ImageView(image);
                label.setGraphic(imageView);
                label.setTextFill(Color.WHITE);
                label.setContentDisplay(ContentDisplay.TOP);
                flowPaneId.getChildren().add(label);
                messageHandler.setArrayList(LabelCreate.setTextMessageLabel(headerTextId.getText()+ "File"), label);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return file;
    }
}
