package com.example.fitconnect;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import fitconnect.management.InvitationManager;

import java.util.Map;



public class InvitationCodeWindow extends Application {
    InvitationManager invitationManager = new InvitationManager();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Invitation Code Manager");

        // Cargar los códigos de invitación desde el archivo
        invitationManager.loadInvitationCodesFromFile("invitation_codes.txt");

        // Crear la lista de códigos de invitación
        ObservableList<String> invitationCodeList = FXCollections.observableArrayList();
        for (Map.Entry<String, Boolean> entry : invitationManager.getInvitationCodes().entrySet()) {
            String status = entry.getValue() ? "used" : "not used";
            invitationCodeList.add(entry.getKey() + " (" + status + ")");
        }
        ListView<String> listView = new ListView<>(invitationCodeList);


        // Crear los botones
        Button generateButton = new Button("Generate Code");
        Button deleteButton = new Button("Delete Code");
        Button saveButton = new Button("Save Changes");
        Button exitButton = new Button("Exit");

        // Configurar el comportamiento de los botones
        generateButton.setOnAction(e -> {
            // Genera un código de invitación y lo añade a la lista
            String newCode = InvitationManager.generateUniqueCode();
            invitationManager.getInvitationCodes().put(newCode, false);
            // Actualiza la lista de códigos de invitación
            invitationCodeList.clear();
            for (Map.Entry<String, Boolean> entry : invitationManager.getInvitationCodes().entrySet()) {
                String status = entry.getValue() ? "usado" : "no usado";
                invitationCodeList.add(entry.getKey() + " (" + status + ")");
            }
        });


        deleteButton.setOnAction(e -> {
            // Aquí borras el código de invitación seleccionado de la lista
            String selectedCode = listView.getSelectionModel().getSelectedItem();
            if (selectedCode != null) {
                String code = selectedCode.split(" ")[0]; // Extrae el código de la cadena seleccionada
                invitationManager.getInvitationCodes().remove(code);
                // Actualiza la lista de códigos de invitación
                invitationCodeList.clear();
                for (Map.Entry<String, Boolean> entry : invitationManager.getInvitationCodes().entrySet()) {
                    String status = entry.getValue() ? "usado" : "no usado";
                    invitationCodeList.add(entry.getKey() + " (" + status + ")");
                }
            }
        });

        // Configurar el comportamiento del botón de salir
        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        saveButton.setOnAction(e -> {
            // Aquí guardas los cambios en el archivo
            invitationManager.saveInvitationCodesToFile("invitation_codes.txt");
        });

        // Configurar el layout
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().addAll(generateButton, deleteButton, saveButton, exitButton);

        vBox.getChildren().addAll(listView, hBox);

        Scene scene = new Scene(vBox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }




    public static void run(String[] args) {
        launch(args);
    }
}

