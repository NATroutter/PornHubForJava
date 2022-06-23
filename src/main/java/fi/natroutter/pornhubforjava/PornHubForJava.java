package fi.natroutter.pornhubforjava;

import fi.natroutter.pornhubforjava.objects.PornStar;
import fi.natroutter.pornhubforjava.objects.VideoData;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * PornHubForJava is a simple api for accessing pornhub with java
 *
 * @author  NATroutter
 * @version 1.0
 * @since   2022-6-23
 */
public class PornHubForJava {

    private boolean inBounds(Integer index, Integer length) {
        return (index >= 0) && (index < length);
    }

    private Connection GetConnection(URL Address) throws Exception {
        //Building Connector and getting response from pornhub server!
        Connection con = Jsoup.connect(Address.toString());
        con.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21");
        con.timeout(10000);
        Connection.Response response;
        try {
            response = con.execute();
        } catch (HttpStatusException e) {
            throw new Exception("Error While trying to query data! (" + Address.getHost() + " returned code: " + e.getStatusCode()+ ")");
        }

        if (response.statusCode() != 200) {
            throw new Exception("Error While trying to query data! (" + Address.getHost() + " returned code: " + response.statusCode()+ ")");
        }
        return con;
    }


    /**
     * Gets video link with viewkey
     * @param viewkey viewkey
     * @return url string
     */
    public String getVideoURL(String viewkey) {
        return "https://pornhub.com/view_video.php?viewkey=" + viewkey;
    }

    /**
     * Gets video embed link with viewkey
     * @param viewkey viewkey
     * @return url string
     */
    public String getVideoEmbed(String viewkey) {
        return "https://pornhub.com/embed/" + viewkey;
    }

    /**
     * Gets list of pornstars from page 1
     * @param page page
     * @return list of prornstars from selected page
     * @throws Exception when something goes wrong
     */
    public ArrayList<PornStar> getPornStarList(int page) throws Exception {
        ArrayList<PornStar> List = new ArrayList<>();

        Document doc = GetConnection(new URL("https://pornhub.com/pornstars?page=" + page)).get();

        Element popularPornstars = doc.body().getElementById("popularPornstars");
        if (popularPornstars != null) {

            for (Element li : popularPornstars.getElementsByTag("li")) {

                if (inBounds(0, li.getElementsByClass("wrap").size())) {
                    Element wrap = li.getElementsByClass("wrap").get(0);

                    PornStar star = new PornStar();

                    if (inBounds(0, wrap.getElementsByClass("rank_number").size())) {
                        Element RankElement = wrap.getElementsByClass("rank_number").get(0);
                        star.setRank(RankElement.text());
                    }

                    if (inBounds(0, wrap.getElementsByTag("img").size())) {
                        Element ImageElement = wrap.getElementsByTag("img").get(0);
                        star.setImageURL(ImageElement.attr("data-thumb_url"));
                    }

                    if (inBounds(0, wrap.getElementsByClass("title").size())) {
                        Element NameElement = wrap.getElementsByClass("title").get(0);
                        star.setName(NameElement.text());
                    }

                    List.add(star);
                }
            }
            return List;
        }
        return null;
    }

    /**
     * Search pornstar with name
     * @param name name of the pornstar
     * @return pornstar profile
     * @throws Exception when something goes wrong
     */
    public PornStar searchPornStar(String name) throws Exception {

        name = name.toLowerCase();
        name = name.replaceAll(" ", "-");


        Document doc;
        String Requ = null;
        try {
            Requ = "https://pornhub.com/pornstar/" + name;
            doc = GetConnection(new URL(Requ)).get();
        } catch (Exception e) {
            Requ = "https://pornhub.com/model/" + name;
            doc = GetConnection(new URL(Requ)).get();
        }

        PornStar star = new PornStar();
        star.setLink(Requ);

        Elements ProfileHeader = doc.body().getElementsByClass("topProfileHeader");
        if (inBounds(0, ProfileHeader.size())) {
            Element Profile = ProfileHeader.get(0);

            //get pornstar provile avatar
            Element GetAvatar = Profile.getElementById("getAvatar");
            if (GetAvatar != null) {
                star.setImageURL(GetAvatar.attr("src"));
            }

            //Get pornstar profile name
            Elements nameSubscribe = Profile.getElementsByClass("nameSubscribe");
            if (inBounds(0, nameSubscribe.size())) {
                Element nameSubscribeelement = nameSubscribe.get(0);

                Elements nameelement = nameSubscribeelement.getElementsByClass("name");
                if (inBounds(0, nameelement.size())) {
                    Element nameelElement2 = nameelement.get(0);
                    star.setName(nameelElement2.text());
                }

            }

            //Get pornstar statistics
            Elements infoboxes = Profile.getElementsByClass("infoBoxes");
            if (inBounds(0, infoboxes.size())) {
                Element infoBoxesElement = infoboxes.get(0);

                //Get pornstar rank
                Elements infobox = infoBoxesElement.getElementsByClass("infoBox");
                if (inBounds(0, infobox.size())) {
                    Element infoboxelement = infobox.get(0);

                    Elements Big = infoboxelement.getElementsByClass("big");
                    if (inBounds(0, Big.size())) {
                        Element bigelement = Big.get(0);
                        star.setRank(bigelement.text());
                    }
                }

                //get pornstar total video views
                Elements videoviews = infoBoxesElement.getElementsByClass("videoViews");
                if (inBounds(0, videoviews.size())) {
                    Element videoviewsElement = videoviews.get(0);

                    Elements span = videoviewsElement.getElementsByTag("span");
                    if (inBounds(0, span.size())) {
                        Element spanElement = span.get(0);
                        star.setTotalViews(spanElement.text());
                    }
                }

                //get pornstar Subscribers amount
                Elements SubscribersElem = infoBoxesElement.getElementsByClass("infoBox");
                if (inBounds(6, SubscribersElem.size())) {
                    Element SubscribersElement = SubscribersElem.get(6);

                    Elements span = SubscribersElement.getElementsByTag("span");
                    if (inBounds(0, span.size())) {
                        Element spanElement = span.get(0);
                        star.setSubscribers(spanElement.text());
                    }
                }

            }

            //Get pornstart description
            Elements DescriptionElem = Profile.getElementsByClass("bio");
            if (inBounds(0, DescriptionElem.size())) {
                Element DescriptionElement = DescriptionElem.get(0);

                Elements BioEleme = DescriptionElement.getElementsByAttribute("itemprop");
                if (inBounds(0, BioEleme.size())) {
                    Element BioElement = BioEleme.get(0);
                    if (BioElement.attributes().get("itemprop").equalsIgnoreCase("description")) {
                        star.setDescription(BioElement.text());
                    }
                }

            }

            Elements infotext = Profile.getElementsByClass("js-infoText");
            if (inBounds(0, infotext.size())) {
                Element infotextElements = infotext.get(0);

                Elements InfoPieces = infotextElements.getElementsByClass("infoPiece");
                HashMap<String, String> FinalList = new HashMap<>();
                for (Element InfoPiece : InfoPieces) {

                    Elements InfoContent = InfoPiece.getElementsByTag("span");

                    if (inBounds(1, InfoPieces.size())) {
                        String Title = InfoContent.get(0).text().replaceAll(":", "");

                        Element valueElement = InfoPiece.after(InfoContent.get(0));
                        Elements elems = valueElement.getElementsByTag("a");

                        if (elems.size() > 0) {
                            FinalList.put(Title, elems.get(0).attributes().get("href"));
                        } else {
                            FinalList.put(Title, InfoPiece.after(InfoContent.get(0)).text());
                        }

                    }
                }
                star.setInfoPiece(FinalList);

            }

        }
        return star;
    }

    /**
     * Search videos with keyword if second argument is true it will check if keyword exist in title
     * @param Keyword keyword what to search
     * @param KeywordInTitle name of the pornstar
     * @return pornstar profile
     * @throws Exception when something goes wrong
     */
    public ArrayList<VideoData> searchVideo(String Keyword, Boolean KeywordInTitle) throws Exception {

        //Returning Arraylist
        ArrayList<VideoData> Final = new ArrayList<VideoData>();

        Document doc = GetConnection(new URL("https://pornhub.com/video/search?search=" + Keyword)).get();

        Elements VideoBoxes = doc.body().getElementsByClass("videoBox");

        Integer ID = 0;


        //Loopping thru all videos found with keyword!
        for (Element Video : VideoBoxes) {

            Element VideoLink = Video.getElementsByClass("linkVideoThumb").get(0);

            //Get video thumbnail
            String Thumbnail = VideoLink.getElementsByTag("img").get(0).attr("data-thumb_url");

            //Get video title
            String Title = VideoLink.attr("title");
            if (KeywordInTitle) {
                if (!Title.toLowerCase().contains(Keyword.toLowerCase())) {
                    continue;
                }
            }

            //Get video viewkey
            String ViewKey = VideoLink.attr("href");
            ViewKey = ViewKey.replace("/view_video.php?viewkey=", "");


            //Get Video duration root class
            String Duration = "Unknown";
            Elements markeroverlays = Video.getElementsByClass("marker-overlays");

            if (inBounds(0, markeroverlays.size())) {
                Element dura = markeroverlays.get(0);
                Elements DurationElement = dura.getElementsByClass("duration");
                if (inBounds(0, DurationElement.size())) {
                    Duration = DurationElement.get(0).text();
                }
            }


            //Get Video duration root class
            String Author = "Unknown";
            Elements UploaderElement = Video.getElementsByClass("videoUploaderBlock");
            if (inBounds(0, UploaderElement.size())) {

                Element usernameWrapElement = UploaderElement.get(0);
                Elements usernameWrap = usernameWrapElement.getElementsByClass("usernameWrap");

                if (inBounds(0, usernameWrap.size())) {
                    Author = usernameWrap.get(0).text();
                }

            }


            //Get Video Detail class
            String Views = "Unknown";
            String LikeRatio = "Unknown";
            String Added = "Unknown";
            Elements VideoDetails = Video.getElementsByClass("videoDetailsBlock");
            if (inBounds(0, VideoDetails.size())) {
                Element VideoDetailValid = VideoDetails.get(0);

                //Get video views amount
                Elements ViewsElement = VideoDetailValid.getElementsByClass("views");
                if (inBounds(0, ViewsElement.size())) {
                    Element ViewsElementValid = ViewsElement.get(0);
                    Views = ViewsElementValid.text().replace(" views", "");
                }

                //Get video like ratio
                Elements LikeRatioElement = VideoDetailValid.getElementsByClass("value");
                if (inBounds(0, LikeRatioElement.size())) {
                    Element LikeRatioElementValid = LikeRatioElement.get(0);
                    LikeRatio = LikeRatioElementValid.text();
                }

                //Get video like ratio
                Elements addedElement = VideoDetailValid.getElementsByClass("added");
                if (inBounds(0, addedElement.size())) {
                    Element addedElementValid = addedElement.get(0);
                    Added = addedElementValid.text();
                }

            }

            Final.add(ID, new VideoData(ID, ViewKey, Title, Views, Duration, Author, LikeRatio, Added, Thumbnail));

            ID++;
        }
        return Final;
    }

}
