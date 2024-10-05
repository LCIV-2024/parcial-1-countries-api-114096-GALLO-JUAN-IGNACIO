package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        private final CountryRepository countryRepository;
        private final RestTemplate restTemplate;

        private List<Country> countryCache;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream()
                    .map(this::mapToCountry)
//                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        public List<CountryDTO> getCountriesByNameDTO(String name) {
                if(Objects.isNull(countryCache)) {
                        countryCache = getAllCountries();
                }
                return countryCache
                    .stream()
                    .filter(country -> country.getName().equalsIgnoreCase(name))
                    .map(this::mapToDTO)
                    .toList();
        }

        public List<CountryDTO> getCountriesByCodeDTO(String code) {
                if(Objects.isNull(countryCache)) {
                        countryCache = getAllCountries();
                }
                return countryCache
                    .stream()
                    .filter(country -> country.getCode().equalsIgnoreCase(code))
                    .map(this::mapToDTO)
                    .toList();
        }

        public List<CountryDTO> getCountriesByContinent(String continent) {
                if(Objects.isNull(countryCache)) {
                        countryCache = getAllCountries();
                }
                return countryCache
                    .stream()
                    .filter(country -> country.getRegion().equalsIgnoreCase(continent))
                    .map(this::mapToDTO)
                    .toList();
        }


        public List<CountryDTO> getCountriesByLanguage(String language) {
                if(Objects.isNull(countryCache)) {
                        countryCache = getAllCountries();
                }
                return countryCache
                    .stream()
                    .filter(country -> countrySpeaksLanguage(country, language))
                    .map(this::mapToDTO)
                    .toList();
        }


        public CountryDTO getCountryWithMostBorders() {
                if(Objects.isNull(countryCache)) {
                        countryCache = getAllCountries();
                }
                Country mostBorderedCountry = findMostBorderedCountry(countryCache);
                return mapToDTO(mostBorderedCountry);
        }

        private Country findMostBorderedCountry(List<Country> countries) {
                Country recordCountry = null;
                for (Country country : countries) {
                        if(countryHasMoreBorders(country, recordCountry)) {
                                recordCountry = country;
                        }
                }
                return recordCountry;
        }

        private boolean countryHasMoreBorders(Country competitor, Country recordHolder) {
                return Objects.nonNull(competitor.getBorders()) &&
                    competitor.getBorders().size() > 0 &&
                    (
                        Objects.isNull(recordHolder) ||
                        competitor.getBorders().size() > recordHolder.getBorders().size()
                    );
        }

        private boolean countrySpeaksLanguage(Country country, String language) {
                if(Objects.isNull(country.getLanguages()) ||
                    country.getLanguages().size() == 0){
                        return false;
                }

                List<String> spokenLanguages = country.getLanguages()
                    .values()
                    .stream()
                    .filter(lang -> lang.equalsIgnoreCase(language))
                    .toList();

                return spokenLanguages.size() > 0;
        }

        /**
         * Agregar mapeo de campo cca3 (String)                 |DONE
         * Agregar mapeo campos borders ((List<String>))        |DONE
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                Country test = Country.builder()
                    .name((String) nameData.get("common"))
                    .population(((Number) countryData.get("population")).longValue())
                    .area(((Number) countryData.get("area")).doubleValue())
                    .code((String) countryData.get("cca3"))
                    .region((String) countryData.get("region"))
                    .borders((List<String>) countryData.get("borders"))
                    .languages((Map<String, String>) countryData.get("languages"))
                    .build();
                return test;
        }

        public  List<CountryDTO> getAllCountriesDTO() {
                return getAllCountries().stream()
                    .map(this::mapToDTO)
                    .toList();
        }



        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

}