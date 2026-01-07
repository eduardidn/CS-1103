import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WeatherService {
    private static final String API_KEY = loadApiKey();
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";
    
    private static String loadApiKey() {
        try (BufferedReader reader = new BufferedReader(new FileReader(".env"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("API_KEY=")) {
                    String key = line.substring(8).trim();
                    System.out.println("API Key loaded: " + key.substring(0, Math.min(8, key.length())) + "...");
                    return key;
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading API key from .env file: " + e.getMessage());
        }
        return "";
    }
    
    public WeatherData getWeatherData(String cityName) throws Exception {
        String encodedCity = URLEncoder.encode(cityName, "UTF-8");
        String urlString = String.format("%s?q=%s&appid=%s&units=metric", 
                                        BASE_URL, encodedCity, API_KEY);
        
        System.out.println("Requesting weather for: " + cityName);
        
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        
        int responseCode = connection.getResponseCode();
        
        if (responseCode != 200) {
            BufferedReader errorReader = new BufferedReader(
                new InputStreamReader(connection.getErrorStream()));
            StringBuilder errorResponse = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorResponse.append(errorLine);
            }
            errorReader.close();
            throw new Exception("API Error (" + responseCode + "): " + errorResponse.toString());
        }
        
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        
        return parseWeatherData(response.toString());
    }
    
    private WeatherData parseWeatherData(String json) {
        String cityName = extractValue(json, "\"name\":\"", "\"");
        
        double temperature = Double.parseDouble(extractValue(json, "\"temp\":", ","));
        double humidity = Double.parseDouble(extractValue(json, "\"humidity\":", ",").replace("}", ""));
        double windSpeed = Double.parseDouble(extractValue(json, "\"speed\":", ",").replace("}", ""));
        
        int weatherStart = json.indexOf("\"weather\":[{");
        int weatherEnd = json.indexOf("}]", weatherStart);
        String weatherSection = json.substring(weatherStart, weatherEnd);
        
        String conditions = extractValue(weatherSection, "\"main\":\"", "\"");
        String description = extractValue(weatherSection, "\"description\":\"", "\"");
        
        return new WeatherData(cityName, temperature, humidity, 
                              windSpeed, conditions, description);
    }
    
    private String extractValue(String json, String key, String delimiter) {
        int startIndex = json.indexOf(key);
        if (startIndex == -1) {
            return "";
        }
        startIndex += key.length();
        
        int endIndex = json.indexOf(delimiter, startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }
        
        return json.substring(startIndex, endIndex).trim();
    }
    
    public static double celsiusToFahrenheit(double celsius) {
        return (celsius * 9/5) + 32;
    }
    
    public static double fahrenheitToCelsius(double fahrenheit) {
        return (fahrenheit - 32) * 5/9;
    }
}
