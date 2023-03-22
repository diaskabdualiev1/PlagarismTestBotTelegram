package com.example.proj2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {
    static Stage ss;
    static final String FILENAME = "users.txt";
    int[] arr = new int[1];
    public void signup() {

        BorderPane bp = new BorderPane();
        Text txtx = new Text("Technological Development News");
        Button log = new Button();
        log.setText("Login");
        log.setStyle("-fx-border-radius: 5px");
        BorderPane top = new BorderPane();
        top.setLeft(txtx);
        top.setRight(log);
        bp.setTop(top);

        TextField tfl = new TextField();
        tfl.setPromptText("Username");


        TextField tfp = new TextField();
        tfp.setPromptText("Password");


        HBox hbb = new HBox();
        RadioButton M = new RadioButton();
        M.setText("Male");
        RadioButton F = new RadioButton();
        F.setText("Female");
        RadioButton O = new RadioButton();
        O.setText("Other");
        TextField mail = new TextField();
        mail.setPromptText("example@mail.ru");
        Button signup = new Button();
        signup.setText("Sign Up");

        hbb.getChildren().add(M);
        hbb.getChildren().add(F);
        hbb.getChildren().add(O);

        BorderPane hbbb = new BorderPane();
        hbbb.setCenter(hbb);
        VBox vb = new VBox();
        vb.getChildren().add(tfl);
        vb.getChildren().add(tfp);
        vb.getChildren().add(hbbb);
        vb.getChildren().add(mail);
        vb.getChildren().add(signup);

        vb.setPadding(new Insets(200, 150, 200, 150));
        bp.setCenter(vb);
        bp.setStyle("-fx-background-color: #81c483");
        Stage stage = new Stage();
        Scene sc = new Scene(bp, 600, 600);
        stage.setScene(sc);
        stage.show();
        log.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
                try {
                    start(ss);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        signup.setOnAction(e -> {
            try {
                String username = tfl.getText().trim();
                String password = tfp.getText().trim();
                String email = mail.getText().trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in all fields");
                }

                if (username.length() > 9 || password.length() > 9 || email.length() > 9) {
                    throw new IllegalArgumentException("Fields cannot exceed 9 characters");
                }

                File file = new File(username+".txt");
                if (!file.exists()) {
                    try (PrintWriter out = new PrintWriter(new FileOutputStream(username+".txt", true))) {
                        out.write(username + ":" + password);
                        Stage st = new Stage();
                        BorderPane oute = new BorderPane();
                        oute.setCenter(new Text("User created!"));
                        Scene err = new Scene(oute, 200, 200);
                        st.setScene(err);
                        st.show();

                    } catch (Exception ee) {
                        Stage st = new Stage();
                        BorderPane out = new BorderPane();
                        out.setCenter(new Text(ee.getMessage()));
                        Scene err = new Scene(out, 200, 200);
                        st.setScene(err);
                        st.show();
                    }
                } else {
                    throw new FileNotFoundException("User exists!");
                }
            } catch (Exception ex) {
                Stage st = new Stage();
                BorderPane out = new BorderPane();
                out.setCenter(new Text(ex.getMessage()));
                Scene err = new Scene(out, 200, 200);
                st.setScene(err);
                st.show();
            }
        });
    }

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        ss = new Stage();
        ss = stage;
        Text txtx = new Text("Technological Development News");
        Button signup = new Button();
        signup.setText("Signup");
        signup.setStyle("-fx-border-radius: 5px");
        TextField tfl = new TextField();
        tfl.setPromptText("Username");

        BorderPane bp = new BorderPane();

        BorderPane top = new BorderPane();
        top.setLeft(txtx);
        top.setRight(signup);
        bp.setTop(top);


        TextField tfp = new TextField();
        tfp.setPromptText("Password");

        Text frgt = new Text("Forgot password?");
        bp.setBottom(frgt);

        CheckBox ch = new CheckBox();
//        Text chtx = new Text("Remember me!");
        ch.setText("Remember me!");
        Button login = new Button();
        login.setText("Login");
        BorderPane hbb = new BorderPane();
        hbb.setLeft(ch);
        hbb.setRight(login);

        VBox vb = new VBox();
        vb.getChildren().add(tfl);
        vb.getChildren().add(tfp);
        vb.getChildren().add(hbb);
        vb.setPadding(new Insets(200, 150, 200, 150));
        bp.setCenter(vb);

        bp.setStyle("-fx-background-color: #81c483");
        Scene sc = new Scene(bp, 600, 600);

        ss.setScene(sc);
        ss.show();
        signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ss.close();
                signup();
            }
        });
        login.setOnAction(actionEvent -> {
            String username = tfl.getText();
            String password = tfp.getText();

            try {
                File file = new File(username+".txt");
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                if (username.isEmpty() || password.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in all fields");
                }

                if (username.length() > 9 || password.length() > 9) {
                    throw new IllegalArgumentException("Fields cannot exceed 9 characters");
                }
                String line;
                boolean found = false;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts[0].equals(username) && parts[1].equals(password)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    // Login successful
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Successful");
                    alert.setHeaderText("Welcome " + username);
                    ss.close();
                    alert.showAndWait();
                    afterLog();
                } else {
                    // Login failed
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    alert.setHeaderText("Invalid username or password");
                    alert.showAndWait();
                }
                bufferedReader.close();
            } catch (IOException e) {
                Stage st = new Stage();
                BorderPane out = new BorderPane();
                out.setCenter(new Text(e.getMessage()));
                Scene err = new Scene(out, 200, 200);
                st.setScene(err);
                st.show();
            }
        });
    }

    public void afterLog() throws FileNotFoundException {
        Stage aftL = new Stage();
        BorderPane dp = new BorderPane();
        Image img = new Image(new FileInputStream("img.png"));
        ImageView Ima = new ImageView(img);
        Ima.setFitHeight(150);
        Ima.setFitWidth(150);
        dp.setLeft(Ima);
        VBox vb = new VBox();
        vb.getChildren().add(Ima);
        Button tech = new Button("Technology");
        Button sp = new Button("Sports");
        Button bu = new Button("Business");
        Button ec = new Button("Economy");
        vb.getChildren().add(tech);
        vb.getChildren().add(sp);
        vb.getChildren().add(bu);
        vb.getChildren().add(ec);

        tech.setOnAction(event -> {
            try {
                Text txt = new Text("Menu Bar:\nBiotechnology\nAI\nAudio and Visual\nInternet\nComputers");
                dp.setTop(txt);
                ImageView IA = new ImageView(new Image(new FileInputStream("tech.png")));
                IA.setFitWidth(200);
                IA.setFitHeight(200);
                dp.setRight(IA);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        sp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Text txt = new Text("Menu Bar:\nFootball\nVolleyBall\nGolf");
                    dp.setTop(txt);
                    ImageView IA = new ImageView(new Image(new FileInputStream("Sports.png")));
                    IA.setFitWidth(200);
                    IA.setFitHeight(200);
                    dp.setRight(IA);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        bu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Text txt = new Text("Menu Bar:\nBuy/Sell\nImport\nExport");
                    dp.setTop(txt);
                    ImageView IA = new ImageView(new Image(new FileInputStream("Business.png")));
                    IA.setFitWidth(200);
                    IA.setFitHeight(200);
                    dp.setRight(IA);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        ec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Text txt = new Text("Menu Bar:\nMoney\nGovernment");
                    dp.setTop(txt);
                    ImageView IA = new ImageView(new Image(new FileInputStream("Economy.png")));
                    IA.setFitWidth(200);
                    IA.setFitHeight(200);
                    dp.setRight(IA);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        dp.setStyle("-fx-background-color: #311aaf");
        dp.setLeft(vb);
        Scene sc = new Scene(dp, 500, 250);
        aftL.setScene(sc);
        aftL.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
