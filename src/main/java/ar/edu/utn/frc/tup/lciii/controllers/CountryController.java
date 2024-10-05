package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.CountryDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/countries")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getAllCountries(
        @RequestParam Optional<String> name,
        @RequestParam Optional<String> code
    ) {
        if(name.isPresent()) {
            return ResponseEntity.ok(countryService.getCountriesByNameDTO(name.get()));
        } else if (code.isPresent()) {
            return ResponseEntity.ok(countryService.getCountriesByCodeDTO(code.get()));
        } else {
            return ResponseEntity.ok(countryService.getAllCountriesDTO());
        }
    }

    @GetMapping("{continent}/continent")
    public ResponseEntity<List<CountryDTO>> getCountriesByContinent(
        @PathVariable String continent
    ) {
        return ResponseEntity.ok(countryService.getCountriesByContinent(continent));
    }

    @GetMapping("{language}/language")
    public ResponseEntity<List<CountryDTO>> getCountriesByLanguage (
        @PathVariable String language
    ) {
        return ResponseEntity.ok(countryService.getCountriesByLanguage(language));
    }

    @GetMapping("most-borders")
    public ResponseEntity<CountryDTO> getCountryWithMostBorder() {
        return ResponseEntity.ok(countryService.getCountryWithMostBorders());
    }

}