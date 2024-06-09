package coilvic.utilidades;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.scene.control.TextInputControl;



public class VerifyValidCharsThread implements Runnable{

    TextInputControl textControl;
    Pattern patronCoincidencias;
    String oldValue;
    public VerifyValidCharsThread(TextInputControl textControl, Pattern patronCoincidencias, String oldValue){
        this.textControl = textControl;
        this.patronCoincidencias = patronCoincidencias;
        this.oldValue = oldValue;
    }
    @Override
    public void run() {
        Platform.runLater(() ->{
            Matcher coincidencia = patronCoincidencias.matcher(textControl.getText());
            if(!coincidencia.matches()){
                textControl.setText(oldValue);
                textControl.positionCaret(textControl.getLength());
            }
        });
    }
}
