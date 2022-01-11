package xyz.itbs.recipes.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.itbs.recipes.commands.RecipeCommand;
import xyz.itbs.recipes.services.ImageService;
import xyz.itbs.recipes.services.RecipeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ImageControllerTest {


    ImageController imageController;
    MockMvc mockMvc;
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(imageService,recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void showUploadForm() throws Exception{
        RecipeCommand recipe = RecipeCommand.builder().id(1L).build();
        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void handleImagePost() throws Exception{
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile","testing.txt","text/plain",
                        "SzuRecipes".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(),any());
    }

    @Test
    void renderImageFromDB() throws Exception{
        //given
        RecipeCommand command = RecipeCommand.builder().id(1L).build();

        String s = "fake image text";
        Byte[] bytesBoxed = new Byte[s.getBytes().length];

        int i = 0;

        for (byte b : s.getBytes()){
            bytesBoxed[i++] = b;
        }

        command.setImage(bytesBoxed);

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(command);

        //when
        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(s.getBytes().length, responseBytes.length);
    }
}