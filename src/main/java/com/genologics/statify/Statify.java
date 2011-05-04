package com.genologics.statify;

import com.genologics.statify.loggers.DefaultStatifyLogger;
import com.genologics.statify.loggers.StatifyLogger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author Adam Jordens
 */
public class Statify {
    private static final String DEFAULT_GUID = "DEFAULT_GUID";

    private final String guid;
    private final String hostname;

    private StatifyLogger logger = new DefaultStatifyLogger();

    public Statify(String hostname) {
        this(null, hostname);
    }

    Statify(String guid, String hostname) {
        if (guid == null) {
            this.guid = register(hostname);
        } else {
            this.guid = guid;
        }
        this.hostname = hostname;
    }

    public boolean log(String key, Object value) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(hostname + "/log");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            DataOutputStream outputStream = null;
            try {
                outputStream = new DataOutputStream(urlConnection.getOutputStream());
                String content = "" +
                        "guid=" + URLEncoder.encode(guid) + "&" +
                        "key=" + URLEncoder.encode(key) + "&" +
                        "value=" + URLEncoder.encode(value.toString());

                outputStream.writeBytes(content);
                outputStream.flush();
            } catch (IOException e) {
                logger.error("Unable to log to host '" + hostname + "'", e);
                return false;
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        // do nothing
                    }
                }
            }

            if (urlConnection.getResponseCode() < 300) {
                return true;
            }

            logger.error("Unable to log (" + urlConnection.getResponseCode() + "): key='" + key + "', value='" + value + "'");
        } catch (IOException e) {
            logger.error("Unable to connect to host '" + hostname + "'", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        return false;
    }

    String register(String hostname) {
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(hostname + "/register");
            URLConnection urlConnection = url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = bufferedReader.readLine().trim();
            if (line.length() > 0) {
                return line;
            } else {
                return DEFAULT_GUID;
            }
        } catch (IOException e) {
            logger.error("Unable to connect to host '" + hostname + "'", e);
            return DEFAULT_GUID;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }
}
