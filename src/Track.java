import java.util.Objects;

public class Track {
    private Artist artist;
    private String name;
    private String genre;
    private int year;

    Track(Artist artist, String name, String genre, int year) {
        this.artist = artist;
        this.name = name;
        this.genre = genre;
        this.year = year;
    }

    public Artist getArtist() {
        return artist;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return year == track.year &&
                Objects.equals(artist, track.artist) &&
                Objects.equals(name, track.name) &&
                Objects.equals(genre, track.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, name, genre, year);
    }

    @Override
    public String toString() {
        return artist.getName() + " - " + name + " (" + genre + ", " + year + ")";
    }
}
