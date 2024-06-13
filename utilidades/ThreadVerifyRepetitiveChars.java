package coilvic.utilidades;

import javafx.application.Platform;
import javafx.scene.control.TextInputControl;

public class ThreadVerifyRepetitiveChars implements Runnable{
    TextInputControl textComponent;
    String oldValue;
    public ThreadVerifyRepetitiveChars(TextInputControl textComponent, String oldValue){
        this.textComponent = textComponent;
        this.oldValue = oldValue;
    }
    @Override
    public void run(){
        Platform.runLater(() -> {
            int stringLength = textComponent.getText().length();
            if(stringLength < 3) return;
            String cadena = textComponent.getText();
            int count = 0;
            for(int i = 1; i < stringLength; i++){
                if(cadena.charAt(i) == cadena.charAt(i - 1)){
                    count++;
                }else{
                    count = 0;
                }
                if(count == 3){
                    textComponent.setText(oldValue);
                    textComponent.positionCaret(textComponent.getLength());
                }
            }
        });
    }
}
