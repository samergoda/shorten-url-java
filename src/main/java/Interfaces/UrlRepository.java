package Interfaces;

import java.util.Optional;

public interface UrlRepository {
    public String createShortenUrl(String url);

    public Optional<String> getUrl(String url);
}
