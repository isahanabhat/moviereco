
import com.mycompany.moviereco.MovieMetadata;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import junit.framework.Assert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author bhats
 */
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
    public void test_02() throws IOException, CsvException{
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
        Assert.assertEquals("1513528810", movie.get(4));
    }
}
