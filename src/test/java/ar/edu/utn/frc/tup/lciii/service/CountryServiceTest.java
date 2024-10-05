package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest

class CountryServiceTest {

  @MockBean
  private CountryRepository countryRepository;

  @Autowired
  private CountryService countryService;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private RestTemplate restTemplate;

  private List<Map<String, Object>> crudeCountries = new ArrayList<>();
  private Map<String, Object> crudeCountry = new HashMap<>();
  private List<Country> expectedCountries = new ArrayList<>();
  private Country country = new Country();

  @BeforeEach
  void setUp() {
    crudeCountry.put("name", Map.of("common", "Argentina"));
    crudeCountry.put("population", 1234);
    crudeCountry.put("area", 1234);
    crudeCountry.put("cca3", "ARG");
    crudeCountry.put("region", "Americas");
    crudeCountry.put("borders", List.of());
    crudeCountry.put("languages", Map.of());
    crudeCountries.add(crudeCountry);

    country.setName("Argentina");
    country.setPopulation(1234);
    country.setArea(1234);
    country.setCode("ARG");
    country.setRegion("Americas");
    country.setBorders(List.of());
    country.setLanguages(Map.of());

    expectedCountries.add(country);
  }

  @Test
  void getAllCountries() throws JsonProcessingException {
    when(restTemplate.getForObject(anyString(), any())).thenReturn(crudeCountries);

    List<Country> actualCountries = countryService.getAllCountries();
    Assertions.assertEquals(objectMapper.writeValueAsString(expectedCountries), objectMapper.writeValueAsString(actualCountries));
  }

  @Test
  void getCountriesByNameDTO() {
//    when(restTemplate.getForObject(anyString(), any())).thenReturn(crudeCountries);
//
//    List<Country> actualCountries = countryService.getAllCountries();
//    Assertions.assertEquals(expectedCountries, actualCountries);
  }

  @Test
  void getCountriesByCodeDTO() {
  }

  @Test
  void getCountriesByContinent() {
  }

  @Test
  void getCountriesByLanguage() {
  }

  @Test
  void getCountryWithMostBorders() {
  }

  @Test
  void getAllCountriesDTO() {
  }

  @Test
  void saveCountries() {
  }
}