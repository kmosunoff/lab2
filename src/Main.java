public class Main {
    public static void main(String[] args) {

        try {
            Catalog catalog = new Catalog();

            catalog.addGenre("genre0");
            catalog.addGenre("genre0", "genre00");
            catalog.addGenre("genre0", "genre01");
            catalog.addGenre("genre0", "genre02");

            catalog.addGenre("genre1");
            catalog.addGenre("genre1", "genre10");
            catalog.addGenre("genre1", "genre11");
            catalog.addGenre("genre1", "genre12");

            catalog.addGenre("genre2");
            catalog.addGenre("genre2", "genre20");
            catalog.addGenre("genre2", "genre21");
            catalog.addGenre("genre2", "genre22");

            catalog.createArtist("artist0");
            catalog.createAlbum("artist0", "album0", 0);
            catalog.addTrackInAlbum("album0", 0, "artist0", "track00", "genre1", 0);
            catalog.addTrackInAlbum("album0", 0, "artist0", "track01", "genre01", 0);
            catalog.addTrackInAlbum("album0", 0, "artist0", "track02", "genre02", 0);

            catalog.createArtist("artist1");
            catalog.createCompilation("artist1", "compilation1", 1);
            catalog.addTrackInCompilation("compilation1", 1, "artist1", "track10", "genre10", 1);
            catalog.addTrackInCompilation("compilation1", 1, "artist1", "track11", "genre11", 0);
            catalog.addTrackInCompilation("compilation1", 1, "artist0", "track12", "genre12", 0);

            System.out.println(catalog.getTracksOfGenre("genre1"));
//            System.out.println(catalog.getAlbumsByArtist("artist0"));
//            System.out.println(catalog.getTracksByArtist("artist1"));
//            System.out.println(catalog.getTracksOfYear(1));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
