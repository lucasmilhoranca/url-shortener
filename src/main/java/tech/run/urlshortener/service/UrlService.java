package tech.run.urlshortener.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.run.urlshortener.entity.UrlEntity;
import tech.run.urlshortener.repository.UrlRepository;

import java.net.URI;
import java.time.LocalDateTime;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String url) {
        String id;

        do {
            id = RandomStringUtils.randomAlphanumeric(5, 10);
        } while (urlRepository.existsById(id));

        urlRepository.save(new UrlEntity(id, url, LocalDateTime.now().plusMinutes(1)));

        return id;
    }

    public ResponseEntity<Void> redirect(String id) {
        var url = urlRepository.findById(id);

        if(url.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();

        headers.setLocation(URI.create(url.get().getFullUrl()));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
