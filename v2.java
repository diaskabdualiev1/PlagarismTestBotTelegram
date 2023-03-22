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
    static int handler = 0;
    public void signup() {

        BorderPane bp = new BorderPane();
        Text txtx = new Text("Technological Development News");
        Button log = new Button();

        log.setText("Login");
        log.setStyle("-fx-border-radius: 5px");
        BorderPane top = new BorderPane();
        handler++;
        top.setLeft(txtx);
        top.setRight(log);
        bp.setTop(top);

        TextField tfl = new TextField();
        handler++;
        tfl.setPromptText("Username");


        TextField tfp = new TextField();
        handler++;
        tfp.setPromptText("Password");


        HBox hbb = new HBox();
        RadioButton M = new RadioButton();
        handler++;
        M.setText("Male");
        RadioButton F = new RadioButton();
        handler++;
        F.setText("Female");
        RadioButton O = new RadioButton();
        handler++;
        O.setText("Other");
        TextField mail = new TextField();
        handler++;
        mail.setPromptText("example@mail.ru");
        Button signup = new Button();
        handler++;
        signup.setText("Sign Up");

        hbb.getChildren().add(M);
        handler++;
        hbb.getChildren().add(F);
        handler++;
        hbb.getChildren().add(O);

        BorderPane hbbb = new BorderPane();
        handler++;
        hbbb.setCenter(hbb);
        VBox vb = new VBox();
        vb.getChildren().add(tfl);
        handler++;
        vb.getChildren().add(tfp);
        handler++;
        vb.getChildren().add(hbbb);
        handler++;
        vb.getChildren().add(mail);
        handler++;
        vb.getChildren().add(signup);

        vb.setPadding(new Insets(200, 150, 200, 150));
        bp.setCenter(vb);
        handler++;
        bp.setStyle("-fx-background-color: #81c483");
        Stage stage = new Stage();
        Scene sc = new Scene(bp, 600, 600);
        stage.setScene(sc);
        handler++;
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
                handler++;
                String password = tfp.getText().trim();
                String email = mail.getText().trim();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in all fields");
                }
                handler++;
                if (username.length() > 9 || password.length() > 9 || email.length() > 9) {
                    throw new IllegalArgumentException("Fields cannot exceed 9 characters");
                }

                File file = new File(username+".txt");
                handler++;
                if (!file.exists()) {
                    try (PrintWriter out = new PrintWriter(new FileOutputStream(username+".txt", true))) {
                        out.write(username + ":" + password);
                        Stage st = new Stage();
                        BorderPane oute = new BorderPane();
                        handler++;
                        oute.setCenter(new Text("User created!"));
                        Scene err = new Scene(oute, 200, 200);
                        st.setScene(err);
                        st.show();

                    } catch (Exception ee) {
                        Stage st = new Stage();
                        BorderPane out = new BorderPane();
                        handler++;
                        out.setCenter(new Text(ee.getMessage()));
                        Scene err = new Scene(out, 200, 200);
                        st.setScene(err);
                        st.show();
                    }
                } else {
                    throw new FileNotFoundException("User exists!");
                }
                handler++;
            } catch (Exception ex) {
                Stage st = new Stage();
                BorderPane out = new BorderPane();
                handler++;
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
        handler++;
        signup.setText("Signup");
        signup.setStyle("-fx-border-radius: 5px");
        TextField tfl = new TextField();
        handler++;
        tfl.setPromptText("Username");

        BorderPane bp = new BorderPane();

        BorderPane top = new BorderPane();
        top.setLeft(txtx);
        handler++;
        top.setRight(signup);
        handler++;
        bp.setTop(top);
        handler++;

        TextField tfp = new TextField();
        handler++;
        tfp.setPromptText("Password");

        Text frgt = new Text("Forgot password?");
        bp.setBottom(frgt);

        CheckBox ch = new CheckBox();
//        Text chtx = new Text("Remember me!");
        ch.setText("Remember me!");
        Button login = new Button();
        handler++;
        login.setText("Login");
        BorderPane hbb = new BorderPane();
        hbb.setLeft(ch);
        handler++;
        hbb.setRight(login);

        VBox vb = new VBox();
        handler++;
        vb.getChildren().add(tfl);
        vb.getChildren().add(tfp);
        handler++;
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
            handler++;
            String password = tfp.getText();

            try {
                File file = new File(username+".txt");
                FileReader fileReader = new FileReader(file);
                handler++;
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                if (username.isEmpty() || password.isEmpty()) {
                    throw new IllegalArgumentException("Please fill in all fields");
                }
                handler++;
                if (username.length() > 9 || password.length() > 9) {
                    throw new IllegalArgumentException("Fields cannot exceed 9 characters");
                }
                String line;
                handler++;
                boolean found = false;

                while ((line = bufferedReader.readLine()) != null) {
                    String[] parts = line.split(":");
                    handler++;
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
                    handler++;
                    ss.close();
                    alert.showAndWait();
                    afterLog();
                } else {
                    // Login failed
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Login Failed");
                    handler++;
                    alert.setHeaderText("Invalid username or password");
                    alert.showAndWait();
                }
                bufferedReader.close();
            } catch (IOException e) {
                Stage st = new Stage();
                BorderPane out = new BorderPane();
                handler++;
                out.setCenter(new Text(e.getMessage()));
                Scene err = new Scene(out, 200, 200);
                handler++;
                st.setScene(err);
                st.show();
            }
        });
    }

    public void afterLog() throws FileNotFoundException {
        Stage aftL = new Stage();
        BorderPane dp = new BorderPane();
        handler++;
        Image img = new Image(new FileInputStream("img.png"));
        ImageView Ima = new ImageView(img);
        handler++;
        Ima.setFitHeight(150);
        Ima.setFitWidth(150);
        dp.setLeft(Ima);
        VBox vb = new VBox();
        handler++;
        vb.getChildren().add(Ima);
        Button tech = new Button("Technology");
        Button sp = new Button("Sports");
        handler++;
        Button bu = new Button("Business");
        handler++;
        Button ec = new Button("Economy");
        vb.getChildren().add(tech);
        vb.getChildren().add(sp);
        handler++;
        vb.getChildren().add(bu);
        vb.getChildren().add(ec);

        tech.setOnAction(event -> {
            try {
                handler++;
                Text txt = new Text("Menu Bar:\nBiotechnology\nAI\nAudio and Visual\nInternet\nComputers");
                dp.setTop(txt);
                ImageView IA = new ImageView(new Image(new FileInputStream("tech.png")));
                handler++;
                IA.setFitWidth(200);
                handler++;
                IA.setFitHeight(200);
                handler++;
                dp.setRight(IA);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        handler++;
        sp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    handler++;
                    Text txt = new Text("Menu Bar:\nFootball\nVolleyBall\nGolf");
                    dp.setTop(txt);
                    handler++;
                    ImageView IA = new ImageView(new Image(new FileInputStream("Sports.png")));
                    handler++;
                    IA.setFitWidth(200);
                    handler++;
                    IA.setFitHeight(200);
                    handler++;
                    dp.setRight(IA);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        bu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {handler++;
                    Text txt = new Text("Menu Bar:\nBuy/Sell\nImport\nExport");
                    handler++;
                    dp.setTop(txt);
                    handler++;
                    ImageView IA = new ImageView(new Image(new FileInputStream("Business.png")));
                    handler++;
                    IA.setFitWidth(200);
                    handler++;
                    IA.setFitHeight(200);
                    handler++;
                    dp.setRight(IA);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        ec.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {handler++;
                    Text txt = new Text("Menu Bar:\nMoney\nGovernment");
                    handler++;
                    dp.setTop(txt);
                    handler++;
                    ImageView IA = new ImageView(new Image(new FileInputStream("Economy.png")));
                    handler++;
                    IA.setFitWidth(200);
                    handler++;
                    IA.setFitHeight(200);
                    handler++;
                    dp.setRight(IA);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        dp.setStyle("-fx-background-color: #311aaf");
        dp.setLeft(vb);
        handler++;
        Scene sc = new Scene(dp, 500, 250);
        aftL.setScene(sc);
        aftL.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
