import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class EditJson {
    private static final String Qvoornaam = "r875d2dc4d7f2424fa89121d343f9e626";
    private static final String Qachternaam = "rbf48bed74e064dd399073656bb7f1dc0";
    private static final String QDatumWeekStart = "r38904b1e7ace4b33b98628a47ee48b59";
    private static final String QDatumWeekEind = "r2a5963384c5e4b399c5e53c68746eac5";

    private final String naam, achternaam, datum;

    /**
     * datum is jaar-maand-dag als YYYY-MM-DD
     */
    public EditJson(String naam, String achternaam, String datum) {
        this.naam = naam;
        this.achternaam = achternaam;
        this.datum = datum;
        makeEntriesFromStream(getClass().getResourceAsStream("/payload_empty.json"));
    }

    private void makeEntriesFromStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
        parsePayload(json);
    }

    private void parsePayload(JsonObject json) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX");
        LocalDateTime time = LocalDateTime.now();

        String start = time.atOffset(ZoneOffset.UTC).format(dtf);
        String submit = time.plusMinutes(2).atOffset(ZoneOffset.UTC).format(dtf);

        json.addProperty("startDate", start);
        json.addProperty("submitDate", submit);


        JsonArray array = json.getAsJsonArray("answers");

        JsonArray newArray = new JsonArray();
        for (JsonElement element : array) {
            JsonObject editable = element.getAsJsonObject();
            String id = editable.get("questionId").getAsString();
            if (id.equals(Qvoornaam))
                editable.add("answer1", JsonParser.parseString(naam));
            if (id.equals(Qachternaam))
                editable.add("answer1", JsonParser.parseString(achternaam));
            if (id.equals(QDatumWeekEind) || id.equals(QDatumWeekStart))
                editable.add("answer1", JsonParser.parseString(datum));

            newArray.add(editable);
        }
        json.remove("answers");
        json.add("answers", newArray);
        send(json);
    }

    private void send(JsonObject json) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("https://forms.office.com/Pages/ResponsePage.aspx?id=xWb_C9tF7UaLgYeVngabkIfoF4GgY69Ep0-fqc8spZBUQ1NPUzJGUjM0OEhEVkFOM0MxN1RUOE1SNy4u");
            StringEntity params = new StringEntity(json.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
