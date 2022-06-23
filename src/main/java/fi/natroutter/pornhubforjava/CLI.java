package fi.natroutter.pornhubforjava;

import fi.natroutter.pornhubforjava.objects.PornStar;
import fi.natroutter.pornhubforjava.objects.VideoData;
import org.apache.maven.plugin.lifecycle.Execution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * CLI for PornHubForJava
 * Usage: java -jar PornHubForJava.jar help
 *
 * @author  NATroutter
 * @version 1.0
 * @since   2022-6-23
 */
public class CLI {

    private static void print(String m) {System.out.println(m);}
    private static void invalidCMD() {print("Invalid Command! Use 'java -jar PornHubForJava.jar help' to get more information");}
    private static void invalidArgs() {print("Invalid Argument! Use 'java -jar PornHubForJava.jar help' to get more information");}

    /**
     * CLI's main method
     * @param args command arguments
     */
    public static void main(String[] args) throws Exception {
        PornHubForJava phj = new PornHubForJava();

        if (args.length == 0)  {
            invalidCMD(); return;
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                print(" ");
                print("PornHubForJava - Version: 2.0.0");
                print("Developed by: NATroutter");
                print("Website: https://NATroutter.net");
                print(" ");
                print("Help:");
                print("  - All commands need to be send like this");
                print("    java -jar PornHubForJava.jar <command> <arg-1> <arg-2>...");
                print("  - If you need to use spaces in command arguments you need to use '_'");
                print(" ");
                print("Commands:");
                print("  - searchPornStar <name>(string)");
                print("  - searchVideo <keyword(string)> <keywordInTitle(true/false)>");
                print("  - getVideoURL <viewkey>(string)");
                print("  - getEmbed <viewkey>(string)");
                print("  - getPornStarList <page>(integer)");
                print(" ");
            }
        } else {
            String command = args[0];
            args = Arrays.copyOfRange(args, 1, args.length);

            switch (command.toLowerCase()) {
                case "searchpornstar"-> {
                    if (args.length == 1) {
                        int count = 0;
                        PornStar star = phj.searchPornStar(args[0].replaceAll("_", ""));
                        print(" ");
                        print("{");
                        print("     \"searchPornStar\": {");
                        print("         \"Name\": \"" + star.getName() + "\",");
                        print("         \"Rank\": \"" + star.getRank() + "\",");
                        print("         \"Description\": \"" + star.getDescription() + "\",");
                        print("         \"Subscribers\": \"" + star.getSubscribers() + "\",");
                        print("         \"Link\": \"" + star.getLink() + "\",");
                        print("         \"ImageURL\": \"" + star.getImageURL() + "\",");
                        print("         \"TotalViews\": \"" + star.getTotalViews() + "\",");
                        if (star.getInfoPiece() != null && star.getInfoPiece().entrySet().size() > 0) {
                            print("         \"InfoPieces\": {");
                            for (Map.Entry<String, String> entry : star.getInfoPiece().entrySet()) {
                                count++;
                                print(count == star.getInfoPiece().entrySet().size() ? "             \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"" : "             \"" + entry.getKey() + "\": \"" + entry.getValue() + "\",");
                            }
                            print("         }");
                        }
                        print("     }");
                        print("}");
                        print(" ");
                        return;
                    } else {invalidArgs();}
                }
                case "searchvideo"-> {
                    if (args.length == 2) {
                        int count = 0;
                        List<VideoData> videos = phj.searchVideo(args[0].replaceAll("_", " "), Boolean.getBoolean(args[0]));
                        print(" ");
                        print("{");
                        print("     \"videos\": [");
                        for (VideoData video : videos) {
                            count++;
                            print("         {");
                            print("             \"Title\": \"" + video.getTitle() + "\",");
                            print("             \"Id\": \"" + video.getId() + "\",");
                            print("             \"Views\": \"" + video.getViews() + "\",");
                            print("             \"ViewKey\": \"" + video.getViewKey() + "\",");
                            print("             \"Thumbnail\": \"" + video.getThumbnail() + "\",");
                            print("             \"Author\": \"" + video.getAuthor() + "\",");
                            print("             \"Added\": \"" + video.getAdded() + "\",");
                            print("             \"LikeRatio\": \"" + video.getLikeRatio() + "\",");
                            print("             \"Duration\": \"" + video.getDuration() + "\"");
                            print(count == videos.size() ? "         }" : "         },");
                        }
                        print("     ]");
                        print("}");
                        print(" ");
                        return;
                    } else {invalidArgs();}
                }
                case "getvideourl"-> {
                    if (args.length == 1) {
                        print(" ");
                        print("{");
                        print("     \"url\": \"" + phj.getVideoURL(args[0]) + "\"");
                        print("}");
                        print(" ");
                        return;
                    } else {invalidArgs();}
                }
                case "getembed"-> {
                    if (args.length == 1) {
                        print(" ");
                        print("{");
                        print("     \"url\": \"" + phj.getVideoEmbed(args[0]) + "\"");
                        print("}");
                        print(" ");
                        return;
                    } else {invalidArgs();}
                }
                case "getpornstarlist"-> {
                    if (args.length == 1) {
                        int count = 0;

                        int page;
                        try {
                            page = Integer.parseInt(args[0]);
                        }catch (Exception e) {
                            print("Invalid page!");
                            return;
                        }

                        List<PornStar> stars = phj.getPornStarList(page);
                        print(" ");
                        print("{");
                        print("     \"pornstars\": [");
                        for(PornStar star : stars) {
                            count++;
                            int count2 = 0;
                            print("         {");
                            print("             \"Name\": \"" + star.getName() + "\",");
                            print("             \"Rank\": \"" + star.getRank() + "\",");
                            print("             \"Description\": \"" + star.getDescription() + "\",");
                            print("             \"Subscribers\": \"" + star.getSubscribers() + "\",");
                            print("             \"Link\": \"" + star.getLink() + "\",");
                            print("             \"ImageURL\": \"" + star.getImageURL() + "\",");
                            print("             \"TotalViews\": \"" + star.getTotalViews() + "\"" + (star.getInfoPiece() != null && star.getInfoPiece().entrySet().size() > 0 ? ", ": ""));
                            if (star.getInfoPiece() != null && star.getInfoPiece().entrySet().size() > 0) {
                                print("             \"InfoPieces\": {");
                                for (Map.Entry<String, String> entry : star.getInfoPiece().entrySet()) {
                                    count2++;
                                    print(count2 == star.getInfoPiece().entrySet().size() ? "                \"" + entry.getKey() + "\": \"" + entry.getValue() + "\"" : "               \"" + entry.getKey() + "\": \"" + entry.getValue() + "\",");
                                }
                                print(count == stars.size() ? "         }" : "         },");
                            }
                            print(count == stars.size() ? "         }" : "         },");
                        }
                        print("     ]");
                        print("}");
                        print(" ");
                        return;
                    } else {invalidArgs();}
                }
            }
            invalidCMD();
        }

    }

}
