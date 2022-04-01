package in.cbsingh.JsoupScrapper.Entity;

public class TagStats extends Stats{
    private String tagView;
    private String title;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTagView() {
        return tagView;
    }

    public void setTagView(String tagView) {
        this.tagView = tagView;
    }
}
