package fi.natroutter.pornhubforjava.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Information about video
 *
 * @author  NATroutter
 * @version 1.0
 * @since   2022-6-23
 */
@Getter
@AllArgsConstructor
public class VideoData {

    private Integer id;
    private String viewKey;
    private String title;
    private String views;
    private String duration;
    private String author;
    private String likeRatio;
    private String added;
    private String thumbnail;

    @Override
    public String toString() {
        return "id = "+id+", key = "+ viewKey +", views = "+views+", title = "+title+", duration = "+duration+", author = "+author+", likeratio = "+likeRatio+", added = "+added+", thumbnail = "+thumbnail;
    }

}
