package com.localnews.localnews.services.newsServices;

import com.localnews.localnews.models.BooleanResponseModel;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainNewsService {

    public List<Map<String, String>> fetchMainNews() {
        List<Map<String, String>> newsList = new ArrayList<>();

        try {
            URI uri = new URI("https://g1.globo.com/rss/g1/pr/parana/");
            URL url = uri.toURL();
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(url));

            List<SyndEntry> entries = feed.getEntries();

            int count = Math.min(entries.size(), 10);

            for (int i = 0; i < count; i++) {
                SyndEntry entry = entries.get(i);

                Map<String, String> news = new HashMap<>();
                news.put("title", entry.getTitle());
                news.put("link", entry.getLink());

                String imageUrl = getImageUrlFromDescription(entry.getDescription().getValue());
                if (imageUrl != null) {
                    news.put("image", imageUrl);
                }
                if (news.containsKey("image")) {
                    newsList.add(news);
                }
            }

        } catch (NullPointerException | URISyntaxException | MalformedURLException e) {
            new BooleanResponseModel(false, e.getMessage());
        } catch (FeedException | IOException e) {
            throw new RuntimeException(String.valueOf(new BooleanResponseModel(false,
                    e.getMessage())));
        }

        return newsList;
    }

    private String getImageUrlFromDescription(String description) {
        Pattern pattern = Pattern.compile("<img src=\"(https[^\"]+)\"");
        Matcher matcher = pattern.matcher(description);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}