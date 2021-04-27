package Models;

import java.util.Objects;

public class Book extends Product implements IBook {

    private int NumberOfPages;
    private String  Title;
    private String Genre;

    public Book(String title) {
        Title = title;
    }
    public Book(int numberOfPages, String title, String genre) {

        NumberOfPages = numberOfPages;
        Title = title;
        Genre = genre;

    }

    public int getNumberOfPages() {
        return NumberOfPages;
    }

    public String getTitle() {
        return Title;
    }




    @Override
    public String toString() {
        return "Models.Book{" +
                "NumberOfPages=" + NumberOfPages +
                ", Title='" + Title + '\'' +
                ", Genre='" + Genre + '\'' +
                '}'+"-" +super.toString();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Title.equals(book.Title);
    }
    @Override
    public int hashCode() {
        return Objects.hash(Title)+ getNumberOfPages() * 31 + 345;
    }

    @Override
    public int compareTo(Product o) {
        if ( o instanceof Book){

            if (Title.compareTo(((Book)o).getTitle())==0)
                return NumberOfPages-((Book)o).getNumberOfPages();

            return Title.compareTo(((Book)o).getTitle());

        }

        return -1;

    }
}
