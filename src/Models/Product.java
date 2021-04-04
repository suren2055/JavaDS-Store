package Models;

public abstract class Product  {

    private double price;
    private double rating;

    public void print() {
        System.out.println(this.toString());
    }

    protected void rate(double d) { setRating(d); }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return "Models.Product{" +
                "price=" + price +
                ", rating=" + rating +
                '}';
    }


}
