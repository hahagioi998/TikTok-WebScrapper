package in.cbsingh.JsoupScrapper.Entity;

public class MusicStats extends Stats {

    private String audioViewCount;
    private String musicTitle;

    public String getMusicTitle() {
        return musicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    public MusicStats() {

    }

    public MusicStats(String audioViewCount) {
        this.audioViewCount = audioViewCount;
    }

    public String getAudioViewCount() {
        return audioViewCount;
    }

    public void setAudioViewCount(String audioViewCount) {
        this.audioViewCount = audioViewCount;
    }
}
