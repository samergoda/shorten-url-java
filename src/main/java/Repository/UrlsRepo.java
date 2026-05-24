package Repository;

import utils.Base62Encoder;
import java.util.concurrent.ConcurrentHashMap;

public class UrlsRepo {
    private final ConcurrentHashMap<String, String> urlToCode = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> codeToUrl = new ConcurrentHashMap<>();
    private final Base62Encoder base62Encoder = new Base62Encoder();

    public String createShortenUrl(String url){
        // Check if already exists
        String existing = urlToCode.get(url);
        if(existing != null){
            return existing;
        }

        // Create new shortened URL
        String shortened = base62Encoder.encode(url);
        urlToCode.put(url, shortened);
        codeToUrl.put(shortened, url);
        return shortened;
    }

    public String getUrl(String shortenedCode){
        String originalUrl = codeToUrl.get(shortenedCode);
        return originalUrl != null ? originalUrl : "Not found";
    }
}