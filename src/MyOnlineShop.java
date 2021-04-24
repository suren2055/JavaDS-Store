
import java.io.FileReader;

import CollectionsADT.*;
import CollectionsADT.Abstract.Deque;
import CollectionsADT.ArrayDeque;
import CollectionsADT.HashMap;
import CollectionsADT.HashSet;
import CollectionsADT.LinkedList;
import Models.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.util.calendar.Gregorian;

import javax.swing.plaf.synth.Region;
import javax.xml.bind.SchemaOutputResolver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyOnlineShop {
    static double rangeMin = 0.5;
    static double rangeMax = 5;
    private static ArrayDeque<Product> viewItems = new ArrayDeque<>(10);

    private static HashMap<User, ArrayDeque<Product>> recentlyViewedProductsPerUser = new HashMap<>(); //Equivalent to UsersHashMap
    private static HashMap<User, LinkedList<Product>> favouriteProductsPerUser = new HashMap<>();

    public static void main(String[] str) {

        

    }

    public static void viewItem(Product p) {

        if (viewItems.size() == 10)
            viewItems.popBack();

        viewItems.pushFront(p);
    }

    public static void viewItem(User u, Product p) {
        viewItem(p);
        recentlyViewedProductsPerUser.put(u, viewItems);
    }

    public static void printProducts(Product[] products) {
        for (int i = 0; i < products.length; i++) {
            if (products[i] != null)
                products[i].print();
        }

    }

    private static void sortProductsByRate(Product[] products) {
        // TODO Auto-generated method stub

        int n = products.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {

                if (products[j].getRating() > products[j + 1].getRating()) {

                    Product temp = products[j];
                    products[j] = products[j + 1];
                    products[j + 1] = temp;
                }

            }

        }

    }

    private static Book[] serializeBooks() throws Exception {


        Object obj = new JSONParser().parse(new FileReader("books.json"));
        JSONArray ja = (JSONArray) obj;
        Book[] books = new Book[ja.size()];
        for (int i = 0; i < ja.size(); i++) {

            JSONObject jo = (JSONObject) ja.get(i);

            String title = (String) jo.get("title");
            long pageCount = (long) jo.get("pageCount");
            String shortDescription = (String) jo.get("shortDescription");


            books[i] = new Book((int) pageCount, title, shortDescription);
        }
        return books;

    }

    private static User[] serializeUsers() throws Exception {


        Object obj = new JSONParser().parse(new FileReader("Users.json"));
        JSONArray ja = (JSONArray) obj;
        User[] users = new User[ja.size()];
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        for (int i = 0; i < ja.size(); i++) {

            JSONObject jo = (JSONObject) ja.get(i);

            String name = (String) jo.get("Name");
            String surname = (String) jo.get("Surname");
            String email = (String) jo.get("Email");
            String password = (String) jo.get("Password");
            Date dateOfBirth = dateFormat.parse((String) jo.get("DateOfBirth"));

            users[i] = new User(name, surname, email, password, dateOfBirth);
        }
        return users;

    }


}
