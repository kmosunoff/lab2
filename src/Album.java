import java.util.ArrayList;

public class Album extends MusicCollection {
    Album() {
        super();
    }

    Album(Artist artist, String name, int year, ArrayList<Track> tracks) {
        super(artist, name, year, tracks);
    }


}
