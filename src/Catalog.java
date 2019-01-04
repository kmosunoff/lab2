import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Catalog {
    private List<Artist> artists = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();
    private List<MusicCollection> collections = new ArrayList<>();

    Catalog() { }

    private Artist findArtist(String artistName) {
        return artists.stream()
                .filter(artist -> artist.getName().equals(artistName))
                .findAny()
                .orElse(null);
    }

    private Genre findGenre(String genreName) {
        return genres.stream()
                .filter(genre -> genre.getName().equals(genreName))
                .findAny()
                .orElse(null);
    }

    private Album findAlbum(String artistName, String albumName, int year) {
        return (Album) collections.stream()
                .filter(musicCollection -> musicCollection instanceof Album
                        && musicCollection.getArtist().getName().equals(artistName)
                        && musicCollection.getName().equals(albumName)
                        && musicCollection.getYear() == year)
                .findAny()
                .orElse(null);
    }

    private Compilation findCompilation(String artistName, String compilationName, int year) {
        return (Compilation) collections.stream()
                .filter(musicCollection -> musicCollection instanceof Compilation
                        && musicCollection.getArtist().getName().equals(artistName)
                        && musicCollection.getName().equals(compilationName)
                        && musicCollection.getYear() == year)
                .findAny()
                .orElse(null);
    }


    private Track findTrackInMusicCollection(MusicCollection musicCollection, String artistName, String trackName, String genreName, int year) {
        return musicCollection.getTracks().stream()
                .filter(track -> track.getArtist().getName().equals(artistName)
                        && track.getName().equals(trackName)
                        && track.getGenre().equals(genreName)
                        && track.getYear() == year)
                .findAny()
                .orElse(null);
    }

    private Track findTrackInAlbum(Album album, String artistName, String trackName, String genreName, int year) {
        return findTrackInMusicCollection(album, artistName, trackName, genreName, year);
    }

    private Track findTrackInCompilation(Compilation compilation, String artistName, String trackName, String genreName, int year) {
        return findTrackInMusicCollection(compilation, artistName, trackName, genreName, year);
    }

    public void addGenre(String genreName) throws Exception{
        Genre genre = new Genre(genreName);
        if (!genres.contains(genre)) {
            genres.add(genre);
        }
        else {
            throw new Exception("This genre already exists.");
        }
    }

    public void addGenre(String genreName, String subgenreName) throws Exception {
        if (findGenre(subgenreName) != null) {
            throw new Exception("The " + subgenreName + " genre cannot be a subgenre.");
        }
        Genre genre = findGenre(genreName);
        if (genre == null) {
            genre = new Genre(genreName);
            genre.getSubgenres().add(subgenreName);
            genres.add(genre);
        }
        else {
            genre.getSubgenres().add(subgenreName);
        }
    }

    public boolean isSubgenreOf(String subgenreName, String genreName) {
        Genre genre = findGenre(genreName);
        if (genre != null) {
            return genre.getSubgenres().contains(subgenreName);
        }
        return false;
//        return genres.contains(new Genre(genreName)) &&
//                genres.get(genres.indexOf(new Genre(genreName))).getSubgenres().contains(subgenreName);
    }

    public Artist createArtist(String artistName) throws Exception{
        Artist artist = findArtist(artistName);
        if (artist != null) {
            throw new Exception("This artist already exists");
        }
        artist = new Artist(artistName);
        artists.add(artist);
        return artist;
    }

    public Album createAlbum(String artistName, String albumName, int year) throws Exception{
        Artist artist = findArtist(artistName);
        if (artist == null) {
            throw new Exception("There is no such an artist.");
        }
        Album album = findAlbum(artistName, albumName, year);
        if (album != null) {
            throw new Exception("This album already exists.");
        }
        else {
            album = new Album(artist, albumName, year, new ArrayList<>());
            collections.add(album);
            return album;
        }
    }

    public Compilation createCompilation(String artistName, String compilationName, int year) throws Exception{
        Artist artist = findArtist(artistName);
        if (artist == null) {
            throw new Exception("There is no such an artist.");
        }
        Compilation compilation = findCompilation(artistName, compilationName, year);
        if (compilation != null) {
            throw new Exception("This compilation already exists.");
        }
        else {
            compilation = new Compilation(artist, compilationName, year, new ArrayList<>());
            collections.add(compilation);
            return compilation;
        }
    }

    public Track addTrackInAlbum(String albumName, int albumYear, String artistName, String name, String genreName, int year) throws Exception {
        Artist artist = findArtist(artistName);
        if (artist == null) {
            throw new Exception("Create an album first before adding any tracks.");
        }

        Album album = findAlbum(artist.getName(), albumName, albumYear);

        if (album == null) {
            throw new Exception("There is no such an album.");
        }

        if (album.getYear() != year) {
            throw new Exception("Album's year is not the same as track's year.");
        }

        Track track = findTrackInAlbum(album, artistName, name, genreName, year);
        if (track == null) {
            track = new Track(artist, name, genreName, year);
            album.getTracks().add(track);
            return track;
        }

        return null;
    }

    public Track addTrackInCompilation(String compilationName, int compilationYear, String artistName, String name, String genreName, int year) throws Exception{
        Artist artist = findArtist(artistName);
        if (artist == null) {
            throw new Exception("Create an compilation first before adding any tracks.");
        }

        Compilation compilation = findCompilation(artist.getName(), compilationName, compilationYear);

        if (compilation == null) {
            throw new Exception("There is no such a compilation.");
        }

        if (compilation.getYear() < year) {
            throw new Exception("Compilation's year is less than track's year.");
        }

        Track track = findTrackInCompilation(compilation, artistName, name, genreName, year);
        if (track == null) {
            track = new Track(artist, name, genreName, year);
            compilation.getTracks().add(track);
            return track;
        }

        return null;
    }

    public List<Track> getTracksByArtist(String artistName) {
        List<Track> response = new ArrayList<>();
        for (MusicCollection collection : collections) {
            for (Track track : collection.getTracks()) {
                if (track.getArtist().getName().equals(artistName)) {
                    response.add(track);
                }
            }
        }
        return response.stream().distinct().collect(Collectors.toList());
    }

    public List<Track> getTracksOfGenre(String genreName) {
        List<Track> response = new ArrayList<>();
        for (MusicCollection collection : collections) {
            for (Track track : collection.getTracks()) {
                if (track.getGenre().equals(genreName) || isSubgenreOf(track.getGenre(), genreName)) {
                    response.add(track);
                }
            }
        }
        return response.stream().distinct().collect(Collectors.toList());
    }

    public List<Track> getTracksOfYear(int year) {
        List<Track> response = new ArrayList<>();
        for (MusicCollection collection : collections) {
            for (Track track : collection.getTracks()) {
                if (track.getYear() == year) {
                    response.add(track);
                }
            }
        }
        return response.stream().distinct().collect(Collectors.toList());
    }

    public List<Track> getTracksOfAlbum(String artistName, String albumName, int year) {
        List<Track> response = new ArrayList<>();
        for (MusicCollection collection : collections) {
            if (collection instanceof Album
                    && collection.getArtist().getName().equals(artistName)
                    && collection.getName().equals(albumName)
                    && collection.getYear() == year) {
                response.addAll(collection.getTracks());
            }
        }
        return response.stream().distinct().collect(Collectors.toList());
    }

    public List<Track> getTracksOfCompilation(String artistName, String compilationName) {
        List<Track> response = new ArrayList<>();
        for (MusicCollection collection : collections) {
            if (collection instanceof Compilation
                    && collection.getArtist().getName().equals(artistName)
                    && collection.getName().equals(compilationName)) {
                response.addAll(collection.getTracks());
            }
        }
        return response.stream().distinct().collect(Collectors.toList());
    }

    public List<Album> getAlbumsByArtist(String artistName) {
        List<Album> response = new ArrayList<>();
        for (MusicCollection collection : collections) {
            if (collection instanceof Album && collection.getArtist().getName().equals(artistName)) {
                response.add((Album) collection);
            }
        }
        return response;
    }

    public List<Compilation> getCompilationsByArtist(String artistName) {
        List<Compilation> response = new ArrayList<>();
        for (MusicCollection collection : collections) {
            if (collection instanceof Compilation && collection.getArtist().getName().equals(artistName)) {
                response.add((Compilation) collection);
            }
        }
        return response;
    }

    public List<Album> getAlbumsOfYear(int year) {
        List<Album> response = new ArrayList<>();
        for (MusicCollection collection : collections) {
            if (collection instanceof Album && collection.getYear() == year) {
                response.add((Album) collection);
            }
        }
        return response;
    }
}
