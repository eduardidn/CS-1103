public class WeatherData {
    private String cityName;
    private double temperature;
    private double humidity;
    private double windSpeed;
    private String conditions;
    private String description;
    
    public WeatherData(String cityName, double temperature, double humidity, 
                       double windSpeed, String conditions, String description) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.conditions = conditions;
        this.description = description;
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public double getTemperature() {
        return temperature;
    }
    
    public double getHumidity() {
        return humidity;
    }
    
    public double getWindSpeed() {
        return windSpeed;
    }
    
    public String getConditions() {
        return conditions;
    }
    
    public String getDescription() {
        return description;
    }
}
