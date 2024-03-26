package com.example.soketprogrammeing;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public AnchorPane mp_main;
    @FXML
    public Button button_send;
    @FXML
    public TextField massage;
    @FXML
    public VBox VBox_messages;
    @FXML
    public ScrollPane plain;



    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            server = new Server(new ServerSocket(1234));
        }catch (IOException e){
                e.printStackTrace();
            System.out.println("Error Creating Server");
        }

        VBox_messages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                plain.setVvalue((double) t1);
            }
        });

        server.reeiveMassageFromClient(VBox_messages);

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String MessagesToSend = massage.getText();
                if(!MessagesToSend.isEmpty()){
                    HBox hBox= new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));
                    Text text = new Text(MessagesToSend);
                    TextFlow  textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-color: rgb(239, 242, 255);" +
                            "-fx-background-color: rgb(15, 125, 246);" + "-fx-background-radius: 20px;"
                            );
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.944, 0.945, 0.956));

                    HBox hBoxForName = new HBox();
                    hBoxForName.setAlignment(Pos.CENTER_RIGHT);
                    Text textForName = new Text("Server");
                    textForName.setFont(Font.font(10));
                    TextFlow textFlowForName = new TextFlow(textForName);
                    textFlowForName.setPadding(new Insets(5, 10, 0, 5));
                    hBoxForName.getChildren().add(textFlowForName);

                    hBox.getChildren().add(textFlow);
                    VBox_messages.getChildren().add(hBoxForName);
                    VBox_messages.getChildren().add(hBox);
                    server.sendMassageToClient(MessagesToSend);
                    massage.clear();
                }
            }
        });

    }

    public static void addLabel(String messageFromTheClient, VBox vbox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));


        Text text = new Text(messageFromTheClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(235, 233, 255);" +
                "-fx-background-radius: 20px;");

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);

            }
        });
    }
}

