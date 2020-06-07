package sample;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.*;

public class Main extends Application {

    int counter = 0, counter2 = 0, counterBlocker = 0, counterBlocker2 = 0, lastId, lastId2;
    String name;
    List<Button> buttons = new ArrayList<>();
    List<Button> buttons2 = new ArrayList<>();
    LinkedHashSet<Integer> listNumbers = new LinkedHashSet<>();
    LinkedHashSet<Integer> listNumbers2 = new LinkedHashSet<>();
    Random random = new Random();

    @Override
    public void start(Stage firstStage) throws Exception {

        FlowPane flow = new FlowPane(Orientation.VERTICAL);
        flow.setAlignment(Pos.CENTER);
        flow.setVgap(15);
        firstStage.setScene(new Scene(flow, 300, 350));

        Label label = new Label("Witaj w grze Memory!");
        label.setFont(new Font("Arial", 25));
        Label label2 = new Label("Podaj imię i przejdź do gry:");
        label2.setFont(new Font("Arial", 15));
        flow.getChildren().add(label);
        flow.getChildren().add(label2);

        TextField field = new TextField();
        field.setFont(new Font("Arial", 20));
        field.setPrefSize(120, 20);
        flow.getChildren().add(field);

        Button buttonPlay = new Button("Play");
        buttonPlay.setMaxSize(100, 30);
        flow.getChildren().add(buttonPlay);

        Button buttonExit = new Button("Exit");
        buttonExit.setMaxSize(100, 30);
        flow.getChildren().add(buttonExit);
        buttonExit.setOnAction(eventExit -> {
            firstStage.close();
        });

        buttonPlay.setOnAction(event -> {
            counterBlocker = 0;
            counterBlocker2 = 0;
            name = field.getText();
            firstStage.hide();
            Stage secondStage = new Stage();
            FlowPane flow2 = new FlowPane();
            flow2.setAlignment(Pos.CENTER);

            Label label3 = new Label("Memory: level 1    Gracz: " + name);
            label3.setFont(new Font("Arial", 20));
            flow2.getChildren().add(label3);

            Button buttonNext2 = new Button();
            buttonNext2.setText("Next");
            buttonNext2.setDisable(true);

            flow2.setVgap(5);
            flow2.setHgap(5);

            // tworzę zbiór, w którym umieszczę 4 wylosowane liczby w różnej kolejności i przekaże do tablicy
            while (listNumbers.size() != 4) {
                int randomNumber = 1 + random.nextInt(4);
                listNumbers.add(randomNumber);
            }
            Integer[] array1 = new Integer[listNumbers.size()];
            listNumbers.toArray(array1);

            int id;          // nadaje id obrazkom
            for (int i = 1; i <= 8; i++) {    // 4 pierwsze obrazki są w stałej kolejności, 4 kolejne - w lsosowej
                Button button = new Button();
                id = i;
                if (id > 4)
                    id = array1[i - 5];

                button.setPrefHeight(120);
                button.setPrefWidth(120);
                button.setId(String.valueOf(id)); // nadajemy id przyciskowi
                buttons.add(button);
                button.setOnAction(event2 -> {
                    counter++; // zliczamy kliknięcia w przyciski
                    Button clickedButton = (Button) event2.getSource();  // = kliknięty przycisk
                    Image image = new Image(getClass().getResourceAsStream("/" + clickedButton.getId() + ".png")); //tworzymy obrazek odwołując się do nazwy
                    clickedButton.setGraphic(new ImageView(image));   // wstawianie grafiki na przycisk
                    if (counter % 2 == 0) {  // jeśli są dwa kliknięcia to sprawdzamy 
                        if (buttons.get(lastId).getId().equals(clickedButton.getId())) {  // sprawdzamy czy ostatnio kliknięty obrazek ma to samo id co aktualnie kliknięty
                            clickedButton.setDisable(true);     // jeśli tak, to blokuje te przyciski
                            buttons.get(lastId).setDisable(true);
                            counterBlocker++;
                        } else {
                            buttons.get(lastId).setGraphic(null);  // jeśli nie, to zakrywamy poprzednio kliknięty przycisk
                            counter = 1;
                        }
                    }
                    lastId = buttons.indexOf(clickedButton); // bierze z listy id osattnio klikniętego przycisku
                    if (counterBlocker == 4) // counterBlocker to zmienna, która liczy ile przycisków jest zablokowanych, jeśli wszystkie, można grać na następnym poziomie
                        buttonNext2.setDisable(false);
                });
                flow2.getChildren().add(button);
            }
            buttonNext2.setOnAction(event2 -> {

                Stage thirdStage = new Stage();
                secondStage.hide();
                FlowPane flow3 = new FlowPane();
                flow3.setAlignment(Pos.CENTER);
                flow3.setVgap(8);
                flow3.setHgap(4);

                Label label4 = new Label("Memory: level 2 \t\t\t\t Gracz: " + name);
                label4.setFont(new Font("Arial", 20));
                flow3.getChildren().add(label4);

                while (listNumbers2.size() != 8) {
                    int randomNumber = 5 + random.nextInt(8);
                    listNumbers2.add(randomNumber);
                }
                Integer[] array2 = new Integer[listNumbers2.size()];
                listNumbers2.toArray(array2);
                int id2;
                for (int i = 5; i <= 20; i++) {
                    Button button2 = new Button();
                    id2 = i;
                    if (id2 > 12)
                        id2 = array2[i - 13];
                    button2.setPrefHeight(120);
                    button2.setPrefWidth(120);
                    button2.setId(String.valueOf(id2));
                    buttons2.add(button2);
                    button2.setOnAction(event3 -> {
                        counter2++;
                        Button clickedButton = (Button) event3.getSource();
                        Image image = new Image(getClass().getResourceAsStream("/" + clickedButton.getId() + ".png"));
                        clickedButton.setGraphic(new ImageView(image));
                        if (counter2 % 2 == 0) {
                            if (buttons2.get(lastId2).getId().equals(clickedButton.getId())) {
                                clickedButton.setDisable(true);
                                buttons2.get(lastId2).setDisable(true);
                                counterBlocker2++;
                            } else {
                                buttons2.get(lastId2).setGraphic(null);
                                counter2 = 1;
                            }
                        }

                        lastId2 = buttons2.indexOf(clickedButton);
                        Label label5 = new Label("Brawo " + name + "! Wygrałeś grę w Memory!!! ");
                        label5.setFont(new Font("Arial", 25));
                        Button buttonPlayAgain = new Button("Play Again");
                        buttonPlayAgain.setPrefSize(200, 40);

                        if (counterBlocker2 == 8) {
                            flow3.getChildren().removeAll(buttons2);
                            flow3.getChildren().removeAll(label4);
                            flow3.getChildren().add(label5);
                            flow3.getChildren().add(buttonPlayAgain);
                            buttonPlayAgain.setOnAction(eventPlayAgain -> {
                                thirdStage.close();
                                firstStage.show();
                            });
                        }
                    });
                    flow3.getChildren().add(button2);
                }

                Button buttonExit3 = new Button("Exit");
                buttonExit3.setPrefSize(200, 40);
                flow3.getChildren().add(buttonExit3);
                buttonExit3.setOnAction(eventExit -> {
                    thirdStage.close();
                });
                flow3.getStylesheets().add("Memory.css");
                thirdStage.setScene(new Scene(flow3, 500, 700));
                thirdStage.initStyle(StageStyle.UNDECORATED);
                thirdStage.show();
            });
            buttonNext2.setPrefHeight(120);
            buttonNext2.setPrefWidth(120);
            flow2.getChildren().add(buttonNext2);

            Button buttonExit2 = new Button("Exit");
            buttonExit2.setPrefSize(200, 40);
            flow2.getChildren().add(buttonExit2);
            buttonExit2.setOnAction(eventExit -> {
                secondStage.close();
            });
            flow2.getStylesheets().add("Memory.css");
            secondStage.setScene(new Scene(flow2, 370, 500));
            secondStage.initStyle(StageStyle.UNDECORATED);
            secondStage.show();
        });
        flow.getStylesheets().add("Memory.css");
        buttonPlay.setPrefHeight(35);
        buttonPlay.setPrefWidth(490);
        firstStage.initStyle(StageStyle.UNDECORATED);
        firstStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
