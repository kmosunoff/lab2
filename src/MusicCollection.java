import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class MusicCollection {
    private Artist artist;
    private String name;
    private int year;
    private List<Track> tracks;

    MusicCollection() {}

    MusicCollection(Artist artist, String name, int year, ArrayList<Track> tracks) {
        this.artist = artist;
        this.name = name;
        this.year = year;
        this.tracks = tracks;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicCollection that = (MusicCollection) o;
        return year == that.year &&
                Objects.equals(artist, that.artist) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, name, year);
    }

    @Override
    public String toString() {
        return artist.getName() + " - " + name + " (" + year + " year)";
    }
}
