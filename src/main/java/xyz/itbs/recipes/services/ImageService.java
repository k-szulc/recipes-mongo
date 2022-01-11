package xyz.itbs.recipes.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFie(Long recipeId, MultipartFile file);
}
