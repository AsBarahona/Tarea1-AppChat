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
    private String nombreUsuario;
    private Socket socket;
    private PrintWriter out;
    private TextArea EspMensajes; 

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pantPrincipalUsuario) {
        pantPrincipalUsuario.setTitle("AppCliente");
        pantPrincipalUsuario.setResizable(false);

        double ancho = 400; 
        double alto = 500; 
        pantPrincipalUsuario.setWidth(ancho);
        pantPrincipalUsuario.setHeight(alto);

        TextField messageField = new TextField();
        EspMensajes = new TextArea(); 
        EspMensajes.setEditable(false);
        double anchoM = 350; 
        double altoM = 350; 
        EspMensajes.setPrefWidth(anchoM); 
        EspMensajes.setPrefHeight(altoM); 


        Button enviarBoton = new Button("Enviar");
        enviarBoton.setOnAction(e -> sendMessage(messageField.getText()));

        VBox root = new VBox(10, EspMensajes, messageField, enviarBoton);
        Scene scene = new Scene(root, 200, 400);

        pantPrincipalUsuario.setScene(scene);
        pantPrincipalUsuario.show();

        nombreUsuario = getUsername();
        ConexionServer();

    }
    private String getUsername() {
        TextInputDialog textoNombre = new TextInputDialog();
        textoNombre.setHeaderText("Ingrese su nombre de usuario:");
        textoNombre.setTitle("Perfil de usuario");
        return textoNombre.showAndWait().orElse("Anonymous");
    }

    private void ConexionServer() {
        try {
            socket = new Socket("localhost", 12345); // Server IP and port
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(nombreUsuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        out.println(message);
    }
}
