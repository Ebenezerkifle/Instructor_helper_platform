package CHAT.UserChat;

import CHAT.OwnerChat.LabelCreate;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;





public class FileManager {

    private final String[] wordExtensions={"*.doc","*.docx","*.DOCX"};
    private final String pdfExtension="*.pdf";
    private final String[] imageExtension={"*.jpg","*.PNG"};
    private final Pane flowPaneId;
    private final Text headerTextId;
    private final UserChatHandler userChatHandler;


    public FileManager(Pane flowPaneId, Text headerTextId,UserChatHandler userChatHandler){
        this.flowPaneId=flowPaneId;
        this.headerTextId=headerTextId;
        this.userChatHandler=userChatHandler;
    }

    public File browseFilesToSend(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Documents",pdfExtension),
                new FileChooser.ExtensionFilter("Image",imageExtension),
                new FileChooser.ExtensionFilter("word",wordExtensions));

        File file=fileChooser.showOpenDialog(null);
        try {
            if(file!=null) {
                FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
                String extension = "";

                int i = file.getName().lastIndexOf('.');
                if (i > 0) {
                    extension = file.getName().substring(i + 1);
                }
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
                userChatHandler.setArrayList(LabelCreate.setTextMessageLabel(headerTextId.getText() + "File"), label);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return file;
    }
}
