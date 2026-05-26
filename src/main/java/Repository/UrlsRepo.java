package Repository;

import Interfaces.UrlRepository;
import utils.Base62Encoder;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class UrlsRepo implements UrlRepository {
    private final ConcurrentHashMap<String, String> urlToCode = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> codeToUrl = new ConcurrentHashMap<>();
    private final Base62Encoder base62Encoder;

    public UrlsRepo(Base62Encoder base62Encoder){
        this.base62Encoder = base62Encoder;
    }

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


    public Optional<String> getUrl(String shortenedCode){
        return Optional.ofNullable(codeToUrl.get(shortenedCode));
    }
}