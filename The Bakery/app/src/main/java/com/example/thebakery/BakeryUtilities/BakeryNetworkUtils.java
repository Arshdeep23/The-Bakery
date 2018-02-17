package com.example.thebakery.BakeryUtilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class BakeryNetworkUtils {

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection bakeryUrlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream bakeryInputStream = bakeryUrlConnection.getInputStream();

            Scanner scanner = new Scanner(bakeryInputStream);
            scanner.useDelimiter("\\A");

            boolean hasSomeInput = scanner.hasNext();
            if (hasSomeInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            bakeryUrlConnection.disconnect();
        }
    }

}
