package agh.ii.jtp.fp.utils;

import agh.ii.jtp.fp.dal.ImdbTop250;
import agh.ii.jtp.fp.model.Movie;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test1 {
    public static void main(String[] args) {
        Optional<List<Movie>> movies = ImdbTop250.movies();

        String author = "Christopher Nolan";

        // Returns the movies (only titles) directed (or co-directed) by a given director
        Set<String> foundMovies = movies.get().stream()
                .filter(s -> s.directors().contains(author))
                .map(s -> s.title())
                .collect(Collectors.toSet());
    }

    private static void myTestingOfParsingCSV() {
        File file = new File("./datasources/imdb_top250.csv");
        System.out.println(file.exists());

        String author = "Christopher Nolan";

        // Returns the movies (only titles) directed (or co-directed) by a given director
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            Stream<String> lines = reader.lines();

            lines
                    .map(s -> s.split(",")[7])
//                    .filter(s -> s.split(",")[7].contains(author))
                    .forEach(System.out::println);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
