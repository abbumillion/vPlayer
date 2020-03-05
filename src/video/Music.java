/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package video;

import java.io.File;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.*;

/**
 *
 * @author Million Sharbe
 */
public class Music extends Application {

    BorderPane root = new BorderPane();
    File f;
    //= new File("A:\\suits\\Suits 3\\Suits.S03E02--Elite  Movie Center - 0929334545---.mp4");
    MediaView mv;
    MediaPlayer next;
    MediaPlayer mp;
    Slider volume = new Slider();
    Label label1 = new Label("Volume");
    Slider progress = new Slider();
    Label label2 = new Label("bass");
    Label label3 = new Label("balance");
    Label label4 = new Label("0:0");
    Label label5 = new Label("3:25");
    ListView list = new ListView();
    BorderPane hb = new BorderPane();
    MediaController controller = new MediaController();
    Scene scene;
    Media media;
    FileChooser filechooser = new FileChooser();
    String[] l = {};

    @Override
    public void start(Stage primaryStage) {
        try {
            f = filechooser.showOpenDialog(primaryStage);
            //  ExtensionFilter[] elements = null;   
            //filechooser.getExtensionFilters().addAll(elements);
            media = new Media(f.toURI().toString());
        } catch (Exception e) {
            System.out.println(e);
        }
        mp = new MediaPlayer(media);
        mv = new MediaView(mp);
        root.setCenter(mv);
        prepareList();
        progress.setValueChanging(true);
        progress.showTickMarksProperty().set(true);
        progress.showTickLabelsProperty().setValue(Boolean.TRUE);
        list.setVisible(false);
        mp.play();
        mv.autosize();
        mv.setPreserveRatio(true);
        mv.setFitHeight(root.getHeight());
        mv.setOnMousePressed(e
                -> {
            if (primaryStage.isFullScreen()) {
                primaryStage.setFullScreen(false);
            } else {
                primaryStage.setFullScreen(true);
                mv.setFitWidth(1366);
            }
        });
        root.setOnMouseMoved(e -> {
            if (e.getX() > 1100 || e.getY() > 600) {
                mv.setFitWidth(1166);
                mv.setPreserveRatio(true);

                root.setRight(list);
                list.setVisible(true);
                root.setBottom(progress);
                progress.setValue(mp.getCurrentTime().toSeconds());
                progress.setMax(mp.getTotalDuration().toSeconds());
                progress.setMin(0);
            } else if (e.getY() > 550) {
                root.setBottom(progress);
                progress.setVisible(true);
            } else {
                root.setTop(null);
                root.setLeft(null);
                mv.setPreserveRatio(true);
                mv.setFitWidth(1366);
                list.setVisible(false);
            }
        });
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<File>() {
            @Override
            public void changed(ObservableValue<? extends File> observable, File oldValue, File newValue) {
                File newfile = new File((list.getSelectionModel().getSelectedItem().toString()));
                media = new Media(newfile.toURI().toString());
                System.out.println(media);
                if (mp.getStatus() == MediaPlayer.Status.PLAYING) {
                    mp.dispose();
                    mp = new MediaPlayer(media);
                    mp.play();
                    mv.setMediaPlayer(mp);
                    System.out.println(newfile.toURI().toString());
                } else {
                    mp.dispose();
                    mp = new MediaPlayer(media);
                    mp.play();
                }

            }
        });
        scene = new Scene(root, 1000, 500);
        scene.setOnKeyPressed(e -> {
            switch (e.getCode().name()) {
                case "SPACE":
                    controller.play_pause(mp);
                    break;
                case "RIGHT":
                    controller.Forward(mp);
                    break;
                case "LEFT":
                    controller.Backward(mp);
                    break;
                case "ENTER":
                    if (primaryStage.isFullScreen()) {
                        primaryStage.setFullScreen(false);
                    } else {
                        primaryStage.setFullScreen(true);
                    }
                    break;
                case "UP":
                    controller.UpVolume(mp);
                    break;
                case "DOWN":
                    controller.DownVolume(mp);
                    break;
                case "MUTE":
                    controller.mute(mp);
            }
        });
        primaryStage.setTitle("Player");
        primaryStage.setScene(scene);
        primaryStage.toFront();
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void prepareList() {
        {
            String path = f.getParent();
            File files = new File(path);
            File[] flist = files.listFiles();
            list.getItems().addAll(flist);
        }
    }
}
