package xyz.itbs.recipes.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.exceptions.NotFoundException;
import xyz.itbs.recipes.repositories.RecipeRepository;

import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        Optional<Recipe> recipeOptional= recipeRepository.findById(recipeId);
        if(!recipeOptional.isPresent()){
            log.error("Recipe not found !");
            throw new NotFoundException("Recipe Not Found");
        } else {
            try {
                Recipe recipe = recipeOptional.get();
                Byte[] byteObjects = new Byte[file.getBytes().length];

                int i = 0;
                for (byte b : file.getBytes()) byteObjects[i++] = b;
                recipe.setImage(byteObjects);
                log.info("Saving image for recipe :: " + recipeId);
                recipeRepository.save(recipe);
            } catch(Exception ex) {
                log.error("Saving failed :: " + ex);
                ex.printStackTrace();
            }

        }

    }
}
