package com.xinlingyijiu.yanchat.client.controller;

import com.xinlingyijiu.yanchat.core.Constant;
import com.xinlingyijiu.yanchat.core.bean.Model;
import com.xinlingyijiu.yanchat.core.user.User;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class UserConfigController implements Initializable {
    @FXML
    private TextField nickNameText;
    @FXML
    private TextField broadcastPortText;
    @FXML
    private TextField udpPortText;
    @FXML
    private TextField tcpPortText;
    @FXML
    private RadioButton broadcastModelRadio;
    @FXML
    private RadioButton appointModelRadio;
    @FXML
    private Button nextBtn;

    public static final int[] FUNCTION_KEY_CODE = {8,35,36,37,38,39,40,48};

    private User user;
    private Model model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = new User();
        model = new Model();
        broadcastPortText.setText(String.valueOf(Constant.DEFAULT_PORT.BROADCAST));
        udpPortText.setText(String.valueOf(Constant.DEFAULT_PORT.UDP));
        tcpPortText.setText(String.valueOf(Constant.DEFAULT_PORT.TCP));
        bindingEvent();


    }
    //绑定事件
    private void bindingEvent() {
        //控制输入数字
        integerInput(broadcastPortText);
        integerInput(udpPortText);
        integerInput(tcpPortText);
    }

    private void integerInput(TextField textField) {
        textField.focusedProperty().addListener(getBooleanChangeListener(textField));
        textField.setOnKeyPressed(this::validityCheckIntKeyEvent);
        textField.setOnInputMethodTextChanged(this::validityCheckIntInputEvent);
    }

    private ChangeListener<Boolean> getBooleanChangeListener(TextField textField) {
        return (observable, oldValue, newValue) -> {//焦点事件
            if (!textField.isFocused()) {
                validityCheckInt(textField);
            }
        };
    }


    public void validityCheckIntKeyEvent(KeyEvent keyEvent) {


        if (keyEvent.isAltDown() || keyEvent.isControlDown() || keyEvent.isMetaDown()
                || keyEvent.isShiftDown() || keyEvent.isShortcutDown()){
            return;
        }
        KeyCode code = keyEvent.getCode();
        if (code.isDigitKey() || code.isLetterKey() || code.isWhitespaceKey()) {
            validityCheckIntInputEvent(keyEvent);
        }
    }

    public void validityCheckIntInputEvent(InputEvent keyEvent) {
        TextField textField = (TextField) keyEvent.getSource();
        validityCheckInt(textField);
    }

    private void validityCheckInt(TextField textField) {
        String test = textField.getText();
        if (test != null) {
            test = test.replaceAll("\\D", "");
            if (test.length() > 0 && Integer.parseInt(test) > 65535) {
                test = "65535";
            }
            textField.setText(test);
            textField.positionCaret(test.length());
//                keyEvent.consume();
        }
    }
}
