package my.project.talkBot;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Storage {

    public String getQuote() throws IOException, InterruptedException {
        String text = "";
        HttpClient client = HttpClient.newHttpClient();

        URI url = URI.create("http://api.forismatic.com/api/1.0/?method=getQuote&format=json&jsonp=parseQuote");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            // передаем парсеру тело ответа в виде строки, содержащей данные в формате JSON
            JsonElement jsonElement = JsonParser.parseString(response.body());
            if (!jsonElement.isJsonObject()) { // проверяем, точно ли мы получили JSON-объект
                text = "Ответ от сервера не соответствует ожидаемому.";
                return text;
            }
            // преобразуем результат разбора текста в JSON-объект
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            text = jsonObject.get("quoteText").getAsString();

        }
        return text;
    }
}
