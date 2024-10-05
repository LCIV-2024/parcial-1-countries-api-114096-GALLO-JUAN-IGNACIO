package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest

class CountryServiceTest {

  @MockBean
  private CountryRepository countryRepository;

  @MockBean
  private RestTemplate restTemplate;

//  private List<>

  @BeforeEach
  void setUp() {
  }

  @Test
  void getAllCountries() {
    when(restTemplate.getForObject(anyString(), any())).thenReturn();
  }

  @Test
  void getCountriesByNameDTO() {
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