import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient extends Application {
    private TextArea EspMensajes; 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pantPrincipalUsuario) {
        pantPrincipalUsuario.setTitle("AppCliente");
        pantPrincipalUsuario.setResizable(false);

        double ancho = 1000; 
        double alto = 800; 
        pantPrincipalUsuario.setWidth(ancho);
        pantPrincipalUsuario.setHeight(alto);

        TextField messageField = new TextField();
        EspMensajes = new TextArea(); 
        EspMensajes.setEditable(false);
        double anchoM = 300; 
        double altoM = 300; 
        EspMensajes.setPrefWidth(anchoM); 
        EspMensajes.setPrefHeight(altoM); 


        Button enviarBoton = new Button("Enviar");
        //enviarBoton.setOnAction(e -> sendMessage(messageField.getText()));

        VBox root = new VBox(10, EspMensajes, messageField, enviarBoton);
        Scene scene = new Scene(root, 100, 100);

        pantPrincipalUsuario.setScene(scene);
        pantPrincipalUsuario.show();
    }
}
