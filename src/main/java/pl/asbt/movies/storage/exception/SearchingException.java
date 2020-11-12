package pl.asbt.movies.storage.exception;

public class SearchingException extends Exception{
    public static String ERR_NO_ACTOR = "There are no such actor in DB";
    public static String ERR_ACTOR_ALREADY_EXIST = "Actor already exist in DB";
    public static String ERR_NO_DIRECTOR = "There are no such director in DB";
    public static String ERR_DIRECTOR_ALREADY_EXIST = "Director already exist in DB";
    public static String ERR_NO_GENRE = "There are no such genre in DB";
    public static String ERR_GENRE_ALREADY_EXIST = "Genre already exist in DB";
    public static String ERR_NO_MOVIE = "There are no such movie  in DB";
    public static String ERR_MOVIE_ALREADY_EXIST = "Movie already exist in DB";
    public static String ERR_NO_STORAGE_ITEM = "There are no such storage item  in DB";
    public static String ERR_STORAGE_ITEM_ALREADY_EXIST = "Storage item already exist in DB";
    public static String ERR_NO_WRITER = "There are no such writer in DB";
    public static String ERR_WRITER_ALREADY_EXIST = "Writer already exist in DB";

//    public SearchingException(String message) {
//        super(message);
//    }

}
