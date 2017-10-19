package demo.multibhashi.com.demoapp.network.models;

import java.io.Serializable;

/**
 * Created by sumanhaque on 10/18/2017.
 */

public class Lesson implements Serializable {

    String type;
    String conceptName;
    String pronunciation;
    String targetScript;
    String audio_url;

    public Lesson() {
    }

    public String getType() {
        return type;
    }

    public String getConceptName() {
        return conceptName;
    }

    public String getPronunciation() {
        return pronunciation;
    }

    public String getTargetScript() {
        return targetScript;
    }

    public String getAudio_url() {
        return audio_url;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "type='" + type + '\'' +
                ", conceptName='" + conceptName + '\'' +
                ", pronunciation='" + pronunciation + '\'' +
                ", targetScript='" + targetScript + '\'' +
                ", audio_url='" + audio_url + '\'' +
                '}';
    }
}
