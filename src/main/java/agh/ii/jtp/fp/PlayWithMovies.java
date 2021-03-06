package agh.ii.jtp.fp;

import agh.ii.jtp.fp.dal.ImdbTop250;
import agh.ii.jtp.fp.model.Movie;
import agh.ii.jtp.fp.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface PlayWithMovies {

    /**
     * Returns the movies (only titles) directed (or co-directed) by a given director
     */
    static Set<String> ex01(String director) {
        if (ImdbTop250.movies().isEmpty()) {
            return new HashSet<>();
        }

        return ImdbTop250.movies().get().stream()
                .filter(s -> s.directors().contains(director))
                .map(s -> s.title())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the movies (only titles) in which an actor played
     */
    static Set<String> ex02(String actor) {
        return ImdbTop250.movies().get().stream()
                .filter(s -> s.actors().contains(actor))
                .map(s -> s.title())
                .collect(Collectors.toSet());
    }

    /**
     * Returns the number of movies per director (as a map)
     */
    static Map<String, Long> ex03() {
        //TODO: simple test --> go to Scratch_1_for_ex3
        return ImdbTop250.movies().get().stream()
                .map(s -> Utils.oneToManyByDirector(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        key -> key.directors().get(0),
                        value -> 1L,
                        (previousValue, nextValue) -> previousValue + 1L
                ));
    }

    /**
     * Returns the 10 directors with the most films on the list
     */
    static Map<String, Long> ex04() {
        return ex03().entrySet().stream()
                //TODO: simple test --> go to Scratch_1_for_ex4
                .sorted((e1, e2) -> e1.getValue() == e2.getValue() ? 0 : (e1.getValue() > e2.getValue() ? -1 : 1)) //FIXED - see ex07
//                .sorted((s1, s2) -> s1.getValue() >= s2.getValue() ? -1 : 1)
//                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())  //FIXME -> alternative; go to ex07
                .limit(10)
                .collect(Collectors.toMap(
                        eSet -> eSet.getKey(),
                        eSet -> eSet.getValue(),
                        (previous, next) -> previous,
                        LinkedHashMap::new
                ));
    }

    /**
     * Returns the movies (only titles) made by each of the 10 directors found in {@link PlayWithMovies#ex04 ex04}
     */
    static Map<String, Set<String>> ex05() {
        // I cannot use previous code, because I need map director:titles
        // --> in ex03 there is map director:amount_of_movies
        Map<String, Set<String>> directorMovies = ImdbTop250.movies().get().stream()
                .map(s -> Utils.oneToManyByDirector(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        key -> key.directors().get(0),
                        value -> {                                  // I add movies to set
                            Set<String> movies = new HashSet<>();
                            movies.add(value.title());
                            return movies;
                        },
                        (previousValue, nextValue) ->
                        {
                            previousValue.addAll(nextValue);
                            return previousValue;
                        }
                ));

        // this is almost the same like for ex4 -> I sort by size of the set where movies are stored
        LinkedHashMap<String, Set<String>> directorMoviesTenSorted = directorMovies.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().size() == e2.getValue().size() ?
                        0 : (e1.getValue().size() > e2.getValue().size() ? -1 : 1)) //FIXED - see ex07
//                .sorted((e1, e2) -> e1.getValue().size() >= e2.getValue().size() ? -1 : 1)
                .limit(10)
                .collect(Collectors.toMap(
                        key -> key.getKey(),
                        value -> value.getValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        return directorMoviesTenSorted;
    }

    /**
     * Returns the number of movies per actor (as a map)
     */
    static Map<String, Long> ex06() {
        return ImdbTop250.movies().get().stream()
                .map(s -> Utils.oneToManyByActor(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        key -> key.actors().get(0),
                        value -> 1L,
                        (previousValue, nextValue) -> previousValue + 1L
                ));
    }

    /**
     * Returns the 9 actors with the most films on the list
     */
    static Map<String, Long> ex07() {
        LinkedHashMap<String, Long> collect = ex06().entrySet().stream()
                .sorted((e1, e2) -> e1.getValue() == e2.getValue() ? 0 : (e1.getValue() > e2.getValue() ? -1 : 1)) //FIXED: >= ? is wrong
//                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) //FIXME: or can be used this line
//                .sorted((e1, e2) -> {                                          //FIXME: or large form
//                    Long valuePrevious = e1.getValue();
//                    Long valueNext = e2.getValue();
//                    if (valuePrevious == valueNext) {
//                        return 0;
//                    } else if (valuePrevious > valueNext) {
//                        return -1;
//                    } else {
//                        return 1;
//                    }
//                })
                .limit(9)
                .collect(Collectors.toMap(
                        key -> key.getKey(),
                        value -> value.getValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        return collect;
    }

    /**
     * Returns the movies (only titles) of each of the 9 actors from {@link PlayWithMovies#ex07 ex07}
     */
    static Map<String, Set<String>> ex08() {
        Map<String, Set<String>> actorsMovies = ImdbTop250.movies().get().stream()
                .map(s -> Utils.oneToManyByActor(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        key -> key.actors().get(0),
                        value -> {
                            Set<String> movies = new HashSet<>();
                            movies.add(value.title());
                            return movies;
                        },
                        (previousValue, nextValue) -> {
                            previousValue.addAll(nextValue);
                            return previousValue;
                        }
                ));

        return actorsMovies.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().size() == e2.getValue().size() ?
                        0 : (e1.getValue().size() > e2.getValue().size() ? -1 : 1))
                .limit(9)
                .collect(Collectors.toMap(
                        key -> key.getKey(),
                        value -> value.getValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Returns the 5 most frequent actor partnerships (i.e., appearing together most often)
     */
    static Map<String, Long> ex09() {
        Map<String, Long> actorsDuosAmountOfFilms = ImdbTop250.movies().get().stream()
                .map(s -> Utils.oneToManyByActorDuo(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        key -> {
                            String pair = key.actors().toString();
                            return pair.substring(1, pair.length() - 1);
                        },
                        value -> 1L,
                        (previousValue, nextValue) -> previousValue + 1
                ));

        LinkedHashMap<String, Long> top5duos = actorsDuosAmountOfFilms.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        key -> key.getKey(),
                        value -> value.getValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));


//        System.out.println(top5duos);
        return top5duos;
    }

    /**
     * Returns the movies (only titles) of each of the 5 most frequent actor partnerships
     */
    static Map<String, Set<String>> ex10() {
        Map<String, Set<String>> topDuosTitles = ImdbTop250.movies().get().stream()
                .map(s -> Utils.oneToManyByActorDuo(s))
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(
                        key -> {
                            String pair = key.actors().toString();
                            return pair.substring(1, pair.length() - 1);
                        },
                        value -> {
                            Set<String> titles = new HashSet<>();
                            String title = value.title();
                            titles.add(title);
                            return titles;
                        },
                        (previousValue, nextValue) -> {
                            previousValue.addAll(nextValue);
                            return previousValue;
                        }));

        LinkedHashMap<String, Set<String>> topDuosTitlesSorted = topDuosTitles.entrySet().stream()
                .sorted((e1, e2) -> e1.getValue().size() == e2.getValue().size() ?
                        0 : (e1.getValue().size() > e2.getValue().size() ? -1 : 1))
                .limit(5)
                .collect(Collectors.toMap(
                        key -> key.getKey(),
                        value -> value.getValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

//        System.out.println(topDuosTitlesSorted);
        return topDuosTitlesSorted;
    }
}


