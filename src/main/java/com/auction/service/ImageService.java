package com.auction.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@Service
public class ImageService {

    public String uploadImage(MultipartFile file) {
        // Return simulated cloud storage CDN URL
        return "https://images.unsplash.com/photo-luxury-asset-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
