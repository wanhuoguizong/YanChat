package com.xinlingyijiu.yanchat.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Parent root = FXMLLoader.load(getClass().getResource("/view/fxml/UserConfig.fxml"));
//        System.out.println(root.getUserData());
        primaryStage.setTitle("YanChat");
        primaryStage.setScene(new Scene(root, 300, 400));
        primaryStage.show();

        //1。启动后基础设置
        //2.启动
        //3.接收命令
        //3.1 获取列表
        //3.2 选择用户聊天
        //3.2.1 创建聊天室(会话)
        //3.2.2 聊天室（会话）列表
        //3.2.3 切换聊天室

        TextField textField = new TextField();

        System.out.println(""+textField.getText());
//        primaryStage.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
