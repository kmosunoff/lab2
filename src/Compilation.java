import java.util.ArrayList;

public class Compilation extends MusicCollection {
    Compilation() {
        super();
    }

    Compilation(Artist artist, String name, int year, ArrayList<Track> tracks) {
        super(artist, name, year, tracks);
    }
}
