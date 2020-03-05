/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package video;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 *
 * @author Million Sharbe
 */
public class MediaController {

    Status status;

    public void play_pause(MediaPlayer mp) {
        status = mp.getStatus();
        if (status == mp.getStatus().PAUSED) {
            mp.play();
        } else if (status == mp.getStatus().PLAYING) {
            mp.pause();
        }
    }

    public void next(MediaPlayer mp, Media media) {
        mp = new MediaPlayer(media);
        mp.play();
    }

    public void prev(MediaPlayer mp, Media media) {
        mp = new MediaPlayer(media);
        mp.play();
    }

    public void mute(MediaPlayer mp) {
        mp.setMute(true);
        if (mp.isMute()) {
            mp.setMute(false);
        } else {
            mp.setMute(true);
        }
    }

    public void Forward(MediaPlayer mp) {
        double total = mp.getTotalDuration().toMillis();
        double current = mp.getCurrentTime().toMillis();
        double n = current + total / 10;
        Duration d = new Duration(n);
        mp.seek(d);
    }

    public void Backward(MediaPlayer mp) {
        double total = mp.getTotalDuration().toMillis();
        double current = mp.getCurrentTime().toMillis();
        double n = current - total / 10;
        Duration d = new Duration(n);
        mp.seek(d);
    }

    public void UpVolume(MediaPlayer mp) {
        mp.setVolume(mp.getVolume() + 0.1);
    }

    public void DownVolume(MediaPlayer mp) {
        mp.setVolume(mp.getVolume() - 0.1);
    }

}
