package Models;

import java.util.Comparator;

public abstract class Product implements Comparable<Product>{

    private double price;
    private double rating;

    public void print() {
        System.out.println(this.toString());
    }

    protected void rate(double d) {
        setRating(d);
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Models.Product{" +
                "price=" + price +
                ", rating=" + rating +
                '}';
    }


    public static class ProductsPriceComparator implements Comparator<Product> {
        public int compare(Product o1, Product o2) {
            if (o1.price > o2.price)
                return 1;
            if (o1.price < o2.price)
                return -1;
            return 0;
        }
    }

    public static class ProductsRateComparator implements Comparator<Product> {
        public int compare(Product o1, Product o2) {
            if (o1.rating > o2.rating)
                return 1;
            if (o1.rating < o2.rating)
                return -1;
            return 0;
        }
    }


}
