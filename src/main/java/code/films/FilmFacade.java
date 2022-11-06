package code.films;

import code.films.dto.AddFilmDTO;
import code.films.dto.FilmDTO;
import code.films.exception.FilmNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class FilmFacade {

    private final FilmRepository filmRepository;

    public FilmDTO add(AddFilmDTO dto) {
        var film = new Film(
                dto.title(),
                dto.filmCategory(),
                FilmYear.of(dto.year())
        );
        return filmRepository
                .save(film)
                .toDTO();
    }

    public FilmDTO read(Long filmId) {
        return filmRepository
                .findById(filmId)
                .map(Film::toDTO)
                .orElseThrow(() -> new FilmNotFoundException(filmId));
    }

    public List<FilmDTO> readAll(Map<String, Object> parameters) {
        var example = Example.of(
                Film
                        .builder()
                        .category((FilmCategory) parameters.get("category"))
                        .build()
        );
        return filmRepository
                .findAll(example)
                .stream()
                .map(Film::toDTO)
                .toList();
    }

    public boolean isPresent(Long filmId) {
        return filmRepository.findById(filmId).isPresent();
    }
}
