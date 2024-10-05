package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.SaveCountriesDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CountryService countryService;

  private List<CountryDTO> continentList = new ArrayList<>();
  private List<CountryDTO> savedCountries = new ArrayList<>();

  private CountryDTO mostBorderedCountry;

  @BeforeEach
  void setUp() {
    continentList = new ArrayList<>();
    continentList.add(CountryDTO.builder().code("ARG").name("Argentina").build());
    continentList.add(CountryDTO.builder().code("CHL").name("Chile").build());
    continentList.add(CountryDTO.builder().code("URY").name("Uruguay").build());



    mostBorderedCountry = CountryDTO.builder().name("China").code("CHN").build();
  }

  @Test
  void getAllCountries() throws Exception {
    Mockito.when(countryService.getAllCountriesDTO()).thenReturn(continentList);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/countries"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(continentList)));
  }

  @Test
  void getAllCountriesByName() throws Exception {
    Mockito.when(countryService.getCountriesByNameDTO(any())).thenReturn(List.of(mostBorderedCountry));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/countries?name=China"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(mostBorderedCountry))));
  }

  @Test
  void getAllCountriesByCode() throws Exception {
    Mockito.when(countryService.getCountriesByCodeDTO(any())).thenReturn(List.of(mostBorderedCountry));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/countries?code=CHN"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(mostBorderedCountry))));
  }

  @Test
  void getCountriesByContinent() throws Exception {
    Mockito.when(countryService.getCountriesByContinent(any())).thenReturn(continentList);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/countries/Americas/continent"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(continentList)));
  }

  @Test
  void getCountriesByLanguage() throws Exception {
    Mockito.when(countryService.getCountriesByLanguage(any())).thenReturn(continentList);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/countries/Spanish/language"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(continentList)));
  }

  @Test
  void getCountryWithMostBorder() throws Exception {
    Mockito.when(countryService.getCountryWithMostBorders()).thenReturn(mostBorderedCountry);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/countries/most-borders"))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(mostBorderedCountry)));
  }


  @Test
  void saveCountries() throws Exception {
    SaveCountriesDTO countryAmountToSave = new SaveCountriesDTO(3);
    Mockito.when(countryService.saveCountries(any())).thenReturn(continentList);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/countries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(countryAmountToSave)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(continentList)));
  }
}