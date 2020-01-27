package com.example.theBot;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Entity // This tells Hibernate to make a table out of this class
public class ProductKey {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private final String productKey;

    // According to https://www.baeldung.com/hibernate-date-time
    private final Timestamp created;
    private final Timestamp expire;

    private static String generateString () {
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        return salt.toString();
    }

    public ProductKey() {
        this.productKey = generateString();

        // Set now
        Timestamp now = new Timestamp(new Date().getTime());
        this.created = now;

        // Add days
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_WEEK, 14);

        // Set the expire date
        this.expire = new Timestamp(cal.getTime().getTime());
    }

    public Timestamp getCreated() {
        return created;
    }

    public String getProductKey () {
        return productKey;
    }

    public Timestamp getExpire() {
        return expire;
    }

    public Integer getId() {
        return id;
    }
}
