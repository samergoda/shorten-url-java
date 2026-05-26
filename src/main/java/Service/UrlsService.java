package Service;

import Interfaces.UrlService;
import Repository.UrlsRepo;

public class UrlsService implements UrlService {
    private final UrlsRepo urlsRepo;

    public UrlsService(UrlsRepo urlsRepo){
        this.urlsRepo = urlsRepo;
    }

    public String createShortUrl(String url){
        return urlsRepo.createShortenUrl(url);
    }


    public String getOriginalUrl(String url){
        return urlsRepo.getUrl(url).orElse("Not found");

    }
}
