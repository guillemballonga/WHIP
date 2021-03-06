
package com.bernal.jonatan.whip;


import android.os.StrictMode;

import com.bernal.jonatan.whip.Views.UserLoggedIn;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalendarGoogle {

    private static final String APPLICATION_NAME = "Whip";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    private UserLoggedIn ul = UserLoggedIn.getUsuariLogejat("", "", "");


    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @param in
     * @param authCode
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final HttpTransport HTTP_TRANSPORT, InputStream in, String authCode) throws Exception {


        String REDIRECT_URI = "";


        if (in == null) {
            throw new FileNotFoundException("Resource not found: ");
        }

        GoogleClientSecrets clientSecrets;
        try {
            clientSecrets = GoogleClientSecrets.load(JacksonFactory.getDefaultInstance(), new InputStreamReader(in));

        } catch (Exception e) {
            throw new Exception("error en getCredentials. clientSecrets");
        }

        System.out.println("clientsecret correcta");

        GoogleTokenResponse tokenResponse;
        String accessToken = "", refreshToken = "";
        Long expiresInSeconds;
        try {

            String clientId = clientSecrets.getDetails().getClientId();
            String clientId2 = "165813394161-mrjtm7dj255ejnm0o64qn5842hq508df.apps.googleusercontent.com";
            String clientSecret = "jFt8NzVu2-gkjxPnzQ14i3dk";
            String authUri = clientSecrets.getDetails().getAuthUri();
            String tokenUri = clientSecrets.getDetails().getTokenUri();
            HttpTransport transport = new NetHttpTransport();
            JacksonFactory jsonFactory = new JacksonFactory();

            System.out.println("client id i client secret: ");
            System.out.println(clientId);
            System.out.println(clientSecret);

            tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    transport,
                    jsonFactory,
                    clientId,
                    clientSecret,
                    authCode,
                    REDIRECT_URI)
                    .execute();


            System.out.println("clientsecret correcta");
            accessToken = tokenResponse.getAccessToken();
            refreshToken = tokenResponse.getRefreshToken();
            expiresInSeconds = tokenResponse.getExpiresInSeconds();
        } catch (Exception e) {
            throw new Exception("error en tokenResponse: " + e.getMessage());
        }


        GoogleCredential credential;
        try {
            credential = new GoogleCredential.Builder()
                    .setTransport(new NetHttpTransport())
                    .setJsonFactory(JacksonFactory.getDefaultInstance())
                    .setClientSecrets(clientSecrets)
                    .build();
            credential.setAccessToken(accessToken);
            credential.setExpiresInSeconds(expiresInSeconds);
            credential.setRefreshToken(refreshToken);
        } catch (Exception e) {
            throw new Exception("error en credentials: " + e.getMessage());
        }

        return credential;
    }


    public static com.google.api.services.calendar.Calendar apiCalendar(InputStream im, String token) throws GeneralSecurityException, IOException {


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // Build a new authorized API client service.
        final HttpTransport HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

        Credential credentials = null;
        try {
            credentials = getCredentials(HTTP_TRANSPORT, im, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credentials)
                .setApplicationName(APPLICATION_NAME)
                .build();


        createEvent(credentials);



        return service;

    }


    public void listEventsFromPrimaryCalendar(com.google.api.services.calendar.Calendar service) throws IOException {
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
    }


    public static void createEvent(Credential mCredential) {

        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        com.google.api.services.calendar.Calendar service = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, mCredential)
                .setApplicationName("R_D_Location Callendar")
                .build();


        Event event = new Event()
                .setSummary("Event- April 2019")
                .setLocation("Dhaka")
                .setDescription("New Event 1");

        DateTime startDateTime = new DateTime("2019-05-25T18:10:00+06:00");
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("Europe/Amsterdam");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2019-05-25T18:40:00+06:00");
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("Europe/Amsterdam");
        event.setEnd(end);

        String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
        event.setRecurrence(Arrays.asList(recurrence));

        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail("lauramunne97@gmail.com"),
                new EventAttendee().setEmail("lauramunne1997@gmail.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);

        String calendarId = "primary";
        try {
            event = service.events().insert(calendarId, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Event created: %s\n", event.getHtmlLink());

    }


}
