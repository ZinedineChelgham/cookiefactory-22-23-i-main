package fr.unice.spring;

import fr.unice.interfaces.RecipeCalculator;
import fr.unice.services.CatalogService;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@CucumberContextConfiguration
@SpringBootTest
public class RunCucumberConfig {

    @Autowired
    @MockBean
    private CatalogService catalogServiceMock;
}
