package Interfaces;

public interface UrlService {
    public String createShortUrl(String url);

    public String getOriginalUrl(String url);
}
