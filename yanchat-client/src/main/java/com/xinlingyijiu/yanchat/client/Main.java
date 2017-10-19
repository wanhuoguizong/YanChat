package com.xinlingyijiu.yanchat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        primaryStage.initStyle(StageStyle.TRANSPARENT);

//        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
//        System.out.println(root.getUserData());
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 300, 275));

        Pane pane  = new Pane();
        Button button = new Button("¿ªÊ¼");
        button.setOnMouseClicked(event -> {
//            primaryStage.close();
//            Stage newStage = new Stage();
//            newStage.setTitle("new");
//            newStage.show();
            Pane spane  = new Pane();
            Button sButton = new Button("½áÊø");
            spane.getChildren().add(sButton);
            primaryStage.setScene(new Scene(spane, 300, 275));
        });
        pane.getChildren().add(button);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(pane, 300, 275));
        primaryStage.show();



//        primaryStage.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
