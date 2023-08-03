package com.mypersonalnetwork.allert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AllertDialogs {

    private static Alert alert;

    public AllertDialogs(){}

    public static String viewPopUp(String typeDialog,String msg){

        if(typeDialog.equals("INFORMATION")){
            return informationPopUp(msg);
        }else if(typeDialog.equals("WARNING")){
            return warningPopUp(msg);
        } else if (typeDialog.equals(("ERROR"))) {
            return errorPopUp(msg);
        } else if (typeDialog.equals(("CONFIRMATION"))){
            return confirmationPopUp(msg);
        }

        return null;
    }

    private static String informationPopUp(String msg) {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
        return "INFORMATION_POP_UP";
    }

    private static String warningPopUp(String msg) {
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
        return "WARNING_POP_UP";
    }

    private static String errorPopUp(String msg) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText(msg);

        alert.showAndWait();
        return "ERROR_POP_UP";
    }

    private static String confirmationPopUp(String msg) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return "ConfirmationOK";
        } else {
            return "ConfirmationNO";
        }
    }
}
