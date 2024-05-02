
import com.mycompany.moviereco.MovieMetadata;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author bhats
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMovieMetadata {
    private MovieMetadata md = null;
    public TestMovieMetadata(){
        
    }
    @Test
    public void test_01() throws IOException, FileNotFoundException, CsvValidationException{
        String file = "../moviereco/data/movies_metadata.csv";
        md = new MovieMetadata(file);
        Assert.assertTrue(md.isCSVLoaded());
    }
    @Test
    public void test_02() throws IOException, CsvException{
        String file = "../moviereco/data/movies_metadata.csv";
        md = new MovieMetadata(file);
        String topGrossers = md.getTopGrossers();
        String expectedOutput = "{\"movies\":[\"Avatar\",\"Star Wars: The Force Awakens\","
                + "\"Titanic\",\"The Avengers\",\"Jurassic World\"],\"revenue\":[\"2787965087\","
                + "\"2068223624\",\"1845034188\",\"1519557910\",\"1513528810\"]}";
        Assert.assertEquals(expectedOutput, topGrossers);
        
        JSONObject j = new JSONObject(topGrossers);
        
        JSONArray movie = j.getJSONArray("movies");
        Assert.assertEquals("Avatar", movie.get(0));
        Assert.assertEquals("The Avengers", movie.get(3));
        
        JSONArray revenue = j.getJSONArray("revenue");
        Assert.assertEquals("2787965087", revenue.get(0));
        Assert.assertEquals("1513528810", revenue.get(4));
    }
    @Test
    public void test_03() throws IOException, FileNotFoundException, CsvValidationException{
        String file = "../moviereco/data/movies_metadata.csv";
        md = new MovieMetadata(file);
        String getData = md.getMovieData("Toy%20Story");
        String expectedOutput = "{\"overview\":\"Led by Woody, Andy's toys live happily in his "
                + "room until Andy's birthday brings Buzz Lightyear onto the scene. Afraid of "
                + "losing his place in Andy's heart, Woody plots against Buzz. But when "
                + "circumstances separate Buzz and Woody from their owner, the duo eventually "
                + "learns to put aside their differences.\",\"original_language\":\"en\","
                + "\"original_title\":\"Toy Story\",\"imdb_id\":\"tt0114709\",\"runtime\":\"81\","
                + "\"video\":\"FALSE\",\"title\":\"Toy Story\","
                + "\"poster_path\":\"/rhIRbceoE9lR4veEXuwCC2wARtG.jpg\","
                + "\"spoken_languages\":\"[{'iso_639_1': 'en', 'name': 'English'}]\","
                + "\"revenue\":\"373554033\",\"production_companies\":\""
                + "[{'name': 'Pixar Animation Studios', 'id': 3}]\",\"release_date\":\"30-10-1995\","
                + "\"genres\":\"[{'id': 16, 'name': 'Animation'}, {'id': 35, 'name': 'Comedy'}, "
                + "{'id': 10751, 'name': 'Family'}]\",\"popularity\":\"21.946943\",\"vote_average\":"
                + "\"7.7\",\"belongs_to_collection\":\"{'id': 10194, 'name': 'Toy Story Collection',"
                + " 'poster_path': '/7G9915LfUQ2lVfwMEEhDsn3kT4B.jpg', 'backdrop_path': '/9FBwqcd9IRruEDUrTdcaafOMKUq.jpg'}\","
                + "\"production_countries\":\"[{'iso_3166_1': 'US', 'name': 'United States of America'}]\","
                + "\"tagline\":\"\",\"id\":\"862\",\"adult\":\"FALSE\",\"vote_count\":\"5415\","
                + "\"budget\":\"30000000\",\"homepage\":\"http://toystory.disney.com/toy-story\",\"status\":\"Released\"}";
        
        Assert.assertEquals(expectedOutput, getData);
        
        JSONObject j = new JSONObject(getData);
        Assert.assertEquals("en", j.get("original_language"));
        
        Assert.assertEquals("21.946943", j.get("popularity"));
        Assert.assertEquals("[{'id': 16, 'name': 'Animation'}, {'id': 35, 'name': 'Comedy'}, {'id': 10751, 'name': 'Family'}]", j.get("genres"));
        
    }
}
