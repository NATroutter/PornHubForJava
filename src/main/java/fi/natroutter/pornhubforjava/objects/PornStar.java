package fi.natroutter.pornhubforjava.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;

/**
 * Information about pornstar
 *
 * @author  NATroutter
 * @version 1.0
 * @since   2022-6-23
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PornStar {

    private String name;
    private String rank;
    private String imageURL;
    private String link;
    private String subscribers;
    private String totalViews;
    private String description;
    private HashMap<String, String> infoPiece;

}
