package Service;

import Repository.UrlsRepo;

public class UrlsService {
    UrlsRepo urlsRepo = new UrlsRepo();
    public String createShortUrl(String url){
        return urlsRepo.createShortenUrl(url);
    }


    public  String getOriginalUrl(String url){
        return urlsRepo.getUrl(url);

    }
}
