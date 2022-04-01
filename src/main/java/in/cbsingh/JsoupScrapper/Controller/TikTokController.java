package in.cbsingh.JsoupScrapper.Controller;



import in.cbsingh.JsoupScrapper.Entity.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/tiktok")
public class TikTokController {

    @PostMapping("/")
    public Stats getStats(@RequestBody Input content) {

        String path = null;
        String url = content.getUrl();
        try {
            path = new URL(url).getPath().substring(1);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (path != null) {

            if (  path.indexOf('/') == -1) {
                return getStats(url, "User");
            } else {
                if (path.contains("music")) {
                    return getStats(url, "Music");
                } else if (path.contains("video")) {
                    return getStats(url, "Video");
                } else if (path.contains("tag")) {
                    return getStats(url, "Tag");
                }
            }
        }
        return null;
    }

    private Stats getStats(String url, String contentType) {

        Stats stats = null;

        try {
            Document doc = Jsoup.connect(url).get();

            if(contentType.equals("Music")) {
                stats = new MusicStats();
                String audioViewCount = doc.select("[data-e2e=\"music-video-count\"]").get(0).text();
                audioViewCount = audioViewCount.substring(0, audioViewCount.indexOf(' '));
                ((MusicStats) stats).setAudioViewCount(audioViewCount);

                String musicTitle = doc.select("[data-e2e=\"music-title\"]").get(0).text();
                ((MusicStats) stats).setMusicTitle(musicTitle);

            } else if (contentType.equals("Video")) {
                stats = new VideoStats();

                String likeCount = doc.select("[data-e2e=\"like-count\"]").get(0).text();
                ((VideoStats) stats).setLikes(likeCount);

                String commentCount = doc.select("[data-e2e=\"comment-count\"]").get(0).text();
                ((VideoStats) stats).setComments(commentCount);

                String shareCount = doc.select("[data-e2e=\"share-count\"]").get(0).text();
                ((VideoStats) stats).setShares(shareCount);

                String userId = doc.select("[data-e2e=\"video-author-uniqueid\"]").get(0).text();
                ((VideoStats) stats).setUserId(userId);

                String musicUrl = doc.select("[data-e2e=\"video-music\"]").get(0).child(0).absUrl("href");
                ((VideoStats) stats).setMusicUrl(musicUrl);

            } else if (contentType.equals("Tag")){
                stats = new TagStats();

                String tagViews = doc.select("[data-e2e=\"challenge-vvcount\"]").get(0).text();
                tagViews = tagViews.substring(0, tagViews.indexOf(' '));
                ((TagStats) stats).setTagView(tagViews);

                String title = doc.select("[data-e2e=\"challenge-title\"]").get(0).text();
                ((TagStats) stats).setTitle(title);
            } else if (contentType.equals("User")) {
                stats = new UserStats();

                String id = doc.select("[data-e2e=\"user-title\"]").get(0).text();
                ((UserStats) stats).setId(id);

                String subTitle = doc.select("[data-e2e=\"user-subtitle\"]").get(0).text();
                ((UserStats) stats).setSubTitle(subTitle);

                String following = doc.select("[data-e2e=\"following-count\"]").get(0).text();
                ((UserStats) stats).setFollowing(following);

                String followers = doc.select("[data-e2e=\"followers-count\"]").get(0).text();
                ((UserStats) stats).setFollowers(followers);

                String likesCount = doc.select("[data-e2e=\"likes-count\"]").get(0).text();
                ((UserStats) stats).setLikes(likesCount);

                String userBio = doc.select("[data-e2e=\"user-bio\"]").get(0).text();
                ((UserStats) stats).setUserBio(userBio);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        stats.setContentType(contentType);
        return stats;
    }
}
