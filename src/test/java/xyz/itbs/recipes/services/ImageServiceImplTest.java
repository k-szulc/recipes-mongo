package xyz.itbs.recipes.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import xyz.itbs.recipes.domain.Recipe;
import xyz.itbs.recipes.exceptions.NotFoundException;
import xyz.itbs.recipes.repositories.RecipeRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ImageServiceImplTest {

    ImageService imageService;
    @Mock
    RecipeRepository recipeRepository;

    MultipartFile multiPartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageService = new ImageServiceImpl(recipeRepository);

        multiPartFile = new MockMultipartFile(
                "imagefile","testing.txt","text/plain","SzuRecipes".getBytes());

    }

    @Test
    void saveImageFie() throws Exception{
        //given

        Recipe recipe = Recipe.builder().id(1L).build();
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        //when
        imageService.saveImageFile(1L,multiPartFile);

        //then
        verify(recipeRepository,times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multiPartFile.getBytes().length, savedRecipe.getImage().length);
    }

    @Test
    void getRecipeByIdNotFound() {
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(NotFoundException.class,
                () -> imageService.saveImageFile(anyLong(),multiPartFile));

        assertEquals("Recipe not found for ID: 0", exception.getMessage());

    }
}