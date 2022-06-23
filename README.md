
<div align="center">
<h1 style="margin: 0px;font-weight: 700;font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica,Arial,sans-serif,Apple Color Emoji,Segoe UI Emoji">PornHubForJava</h1>

![GitHub](https://img.shields.io/github/license/NATroutter/PornHubForJava?style=for-the-badge)
![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fhub.nat.gg%2Fjenkins%2Fjob%2FPornHubForJava%2F&style=for-the-badge)
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/r/fi.natroutter/PornHubForJava?label=Version&server=https%3A%2F%2Fhub.nat.gg%2Fnexus&style=for-the-badge)


Simple api that scrapes data from pornhub.com
 </div>

## Links
* [Jenkins](https://hub.nat.gg/jenkins/job/PornHubForJava/)
* [Nexus](https://hub.nat.gg/nexus/#browse/browse:NAT-Software)

## Maven Repository:
```xml
<repository>
    <id>NAT-Software</id>
    <url>https://hub.nat.gg/nexus/repository/NAT-Software/</url>
</repository>
```

## Maven Dependency:
Replace `{VERSION}` with the version listed at the top of this page.
```xml
<dependency>
    <groupId>fi.natroutter</groupId>
    <artifactId>PornHubForJava</artifactId>
    <version>{VERSION}</version>
    <scope>provided</scope>
</dependency>
```

## Usage

```java
import fi.natroutter.pornhubforjava.PornHubForJava;
import fi.natroutter.pornhubforjava.objects.PornStar;
import fi.natroutter.pornhubforjava.objects.VideoData;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        //Create new instance of api!
        PornHubForJava phj = new PornHubForJava();

        //Gets list of pornstars from page 1
        List<PornStar> pornStars = phj.getPornStarList(1);

        //Search pornstar with name
        PornStar pornStar = phj.searchPornStar("name");

        //Search videos with keyword if second argument is true it will check if keyword exist in title
        List<VideoData> videos = phj.searchVideo("keyword", true);

        //Get video link with viewkey
        String link = phj.getVideoURL("viewkey");

        //Get video embed link with viewkey
        String link = phj.getVideoEmbed("viewkey");

    }

}
```