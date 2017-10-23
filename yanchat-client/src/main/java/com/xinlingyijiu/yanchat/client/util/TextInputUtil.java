package com.xinlingyijiu.yanchat.client.util;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.function.Function;

public class TextInputUtil {
    private TextInputUtil() {
    }

    /**
     * 输入框格式化
     * 文本内容处理
     * @param textInputControl
     * @param format
     */
    public static void inputFormat(TextInputControl textInputControl, Function<String,String> format){
        String text = textInputControl.getText();
        text = format.apply(text);
        textInputControl.setText(text);
        textInputControl.positionCaret(text.length());
    }

    public static void inputIntFormat(TextInputControl textInputControl){
        inputFormat(textInputControl, getIntFormatFunction());
    }

    private static Function<String, String> getIntFormatFunction() {
        return s -> s.replaceAll("\\D","");
    }

    public static void formatFromInputEvent(InputEvent inputEvent, Function<String,String> format){
        TextInputControl textField = (TextInputControl) inputEvent.getSource();
        inputFormat(textField,format);
    }
    public static void formatFromKeyEvent(KeyEvent keyEvent, Function<String,String> format){

        if (keyEvent.isAltDown() || keyEvent.isControlDown() || keyEvent.isMetaDown()
                || keyEvent.isShiftDown() || keyEvent.isShortcutDown()){
            return;
        }
        KeyCode code = keyEvent.getCode();
        if (code.isDigitKey() || code.isLetterKey() || code.isWhitespaceKey()) {
            formatFromInputEvent(keyEvent,format);
        }
    }

    public static void bindingFormatTextInput(TextInputControl textInputControl, Function<String,String> format){
        textInputControl.focusedProperty().addListener(getFocusedChangeListener(textInputControl,format));
        textInputControl.setOnKeyPressed(event -> formatFromKeyEvent(event,format));
        textInputControl.setOnInputMethodTextChanged(event -> formatFromInputEvent(event,format));
    }

    private static ChangeListener<Boolean> getFocusedChangeListener(TextInputControl textField, Function<String,String> format) {
        return (observable, oldValue, newValue) -> {//焦点事件
            if (!textField.isFocused()) {
                inputFormat(textField,format);
            }
        };
    }

    public static void bindingIntegerTextInput(TextInputControl textInputControl){
        Function<String, String> intFormatFunction = getIntFormatFunction();
        textInputControl.focusedProperty().addListener(getFocusedChangeListener(textInputControl, intFormatFunction));
        textInputControl.setOnKeyPressed(event -> formatFromKeyEvent(event,intFormatFunction));
        textInputControl.setOnInputMethodTextChanged(event -> formatFromInputEvent(event,intFormatFunction));
    }
}
