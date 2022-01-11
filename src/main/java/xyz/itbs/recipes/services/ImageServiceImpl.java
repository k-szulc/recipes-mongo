package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Override
    public void saveImageFie(Long recipeId, MultipartFile file) {
        log.info("Saving image for recipe :: " + recipeId);
    }
}
