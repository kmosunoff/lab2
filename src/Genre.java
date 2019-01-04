import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Genre {
    private String name;
    private Set<String> subgenres;

    public Genre(String name) {
        this.name = name;
        this.subgenres = new HashSet<String>();
    }

    public Genre(String name, Set<String> subgenres) {
        this.name = name;
        this.subgenres = subgenres;
    }

    public String getName() {
        return name;
    }

    public Set<String> getSubgenres() {
        return subgenres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
