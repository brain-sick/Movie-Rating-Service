package com.aditya.moviecatalogservice.resource;

import com.aditya.moviecatalogservice.models.CatalogItem;
import com.aditya.moviecatalogservice.models.Movie;
import com.aditya.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        // get all movies rated by user

        List<Rating> ratings = Arrays.asList(
            new Rating("trfrm" , 8),
            new Rating("rb2",7)
        );
        // get details for those movies
        // combine to return the result
        return ratings.stream().map(rating-> {
            Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(),Movie.class);
            return new CatalogItem(movie.getName(),"Desc",rating.getRating());
        }).collect(Collectors.toList());
    }
}
