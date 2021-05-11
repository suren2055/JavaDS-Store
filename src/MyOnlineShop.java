
import java.io.FileReader;


import CollectionsADT.ArrayDeque;
import CollectionsADT.HashMap;
import CollectionsADT.HashSet;
import CollectionsADT.LinkedList;
import CollectionsADT.TreeMap;
import CollectionsADT.TreeSet;
import Helpers.SortingHelper;
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

        User[] users = new User[0];
        try {
            users = serializeUsers();

        } catch (Exception ex) { }
        //#region N1
        System.out.println("*********************TreeSet*********************");
        TreeSet<Integer> tSet = new TreeSet<>();
        tSet.add(8);
        tSet.add(18);
        tSet.add(5);
        tSet.add(15);
        tSet.add(17);
        tSet.add(25);
        tSet.add(40);
        tSet.add(80);
        tSet.remove(25);
        tSet.prettyPrint();

        tSet.subset(15, 18);

        if (!tSet.contains(25))
            System.out.println("SUCCESS");
        else
            System.out.println("FAILURE");

        //#endregion

        //#region N2 ProductsPriceComparator, ProductsRateComparator implemented in Product class
        //#endregion

        //#region N3
        System.out.println("****************TreeMap*********************");
        TreeMap<Integer, Integer> tMap = new TreeMap<Integer, Integer>();
        tMap.put(8, 8);
        tMap.put(18, 18);
        tMap.put(5, 5);
        tMap.put(15, 15);
        tMap.put(17, 17);
        tMap.put(25, 25);
        tMap.put(40, 40);
        tMap.put(80, 80);
        tMap.remove(25);
        tMap.prettyPrint();

        HashSet set = tMap.getEntriesSmallerThan(18);

        if (set.size() == 4)
            System.out.println("SUCCESS");
        else
            System.out.println("FAILURE");
        //#endregion

        //#region 5
        ArrayDeque<Product> p = new ArrayDeque<>();
        Product p1 = new Book(230,"MS-SQL","Programming");
        Product p2 = new Book(510,"Freeman A.","Programming");
        Product p3 = new Book(649,"Richter","Programming");
        Product p4 = new Book(591,"Lock A.","Programming");
        Product p5 = new Book(128,"Asp.NET-Core","Programming");

        p1.setRating(5.2);
        p2.setRating(6.8);
        p3.setRating(9.5);
        p4.setRating(1.64);
        p5.setRating(4.2);

        p.pushFront(p1);
        p.pushBack(p2);
        p.pushBack(p3);
        p.pushBack(p4);
        p.pushBack(p5);

        recentlyViewedProductsPerUser.put(users[5], p);
        recentlyViewedProductsPerUser.put(users[7], p);
        recentlyViewedProductsPerUser.put(users[3], p);
        recentlyViewedProductsPerUser.put(users[1], p);

        HashSet x= getUserFavourites(users[7], new Product.ProductsRateComparator());
        if (x.size()==5)
            System.out.println("SUCCESS");
        else
            System.out.println("FAILURE");
        //#endregion


        System.out.println("Sorting with radix");

        int[] arr = new int[]{2,23,2453,655,655,12,6,899,87,34,59};
        SortingHelper.radixSort(arr);
        for (int i=0;i< arr.length;i++){
            System.out.println(arr[i]);
        }
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

    private  static HashSet<Product> getUserFavourites(User u, Comparator<Product> p) {
        HashSet set = new HashSet();
        Product[] products = new Product[5];
        int index = 0;
        for (HashMap.Entry<User, ArrayDeque<Product>> x : recentlyViewedProductsPerUser) {
            if (x._key.equals(u)) {
                System.out.println(u.toString());
                for (Product y: x._value) {
                    System.out.println(y.toString());
                    products[index] = y;
                    index++;
                }
            }
        }
        SortingHelper.insertionSort(products,p);
        for (int i = 0;i<products.length;i++) {
            set.add(products[i]);
        }

        return set;
    }


}
