import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherApp extends JFrame {
    private WeatherService weatherService;
    private JTextField cityInput;
    private JLabel cityLabel;
    private JLabel weatherIconLabel;
    private JLabel temperatureLabel;
    private JLabel humidityLabel;
    private JLabel windSpeedLabel;
    private JLabel conditionsLabel;
    private JLabel descriptionLabel;
    private JRadioButton celsiusRadio;
    private JRadioButton fahrenheitRadio;
    private JRadioButton metersRadio;
    private JRadioButton milesRadio;
    private WeatherData currentWeatherData;
    private List<String> searchHistory;
    private DefaultListModel<String> historyListModel;
    private JList<String> historyList;
    private JPanel mainPanel;
    
    public WeatherApp() {
        weatherService = new WeatherService();
        searchHistory = new ArrayList<>();
        historyListModel = new DefaultListModel<>();
        
        setTitle("Weather Information App");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        updateBackgroundByTime();
        
        JLabel titleLabel = new JLabel("Weather Information App");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setOpaque(false);
        JLabel inputLabel = new JLabel("Enter City:");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        cityInput = new JTextField(15);
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(33, 150, 243));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setOpaque(true);
        searchButton.addActionListener(e -> searchWeather());
        inputPanel.add(inputLabel);
        inputPanel.add(cityInput);
        inputPanel.add(searchButton);
        
        JPanel unitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        unitPanel.setOpaque(false);
        JLabel unitLabel = new JLabel("Temperature Unit:");
        unitLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        celsiusRadio = new JRadioButton("Celsius", true);
        celsiusRadio.setOpaque(false);
        celsiusRadio.addActionListener(e -> updateTemperatureDisplay());
        fahrenheitRadio = new JRadioButton("Fahrenheit");
        fahrenheitRadio.setOpaque(false);
        fahrenheitRadio.addActionListener(e -> updateTemperatureDisplay());
        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(celsiusRadio);
        unitGroup.add(fahrenheitRadio);
        unitPanel.add(unitLabel);
        unitPanel.add(celsiusRadio);
        unitPanel.add(fahrenheitRadio);
        
        JPanel windUnitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        windUnitPanel.setOpaque(false);
        JLabel windUnitLabel = new JLabel("Wind Speed Unit:");
        windUnitLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        metersRadio = new JRadioButton("m/s", true);
        metersRadio.setOpaque(false);
        metersRadio.addActionListener(e -> updateWindSpeedDisplay());
        milesRadio = new JRadioButton("mph");
        milesRadio.setOpaque(false);
        milesRadio.addActionListener(e -> updateWindSpeedDisplay());
        ButtonGroup windUnitGroup = new ButtonGroup();
        windUnitGroup.add(metersRadio);
        windUnitGroup.add(milesRadio);
        windUnitPanel.add(windUnitLabel);
        windUnitPanel.add(metersRadio);
        windUnitPanel.add(milesRadio);
        
        JPanel weatherPanel = new JPanel();
        weatherPanel.setLayout(new BoxLayout(weatherPanel, BoxLayout.Y_AXIS));
        weatherPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(144, 202, 249), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        weatherPanel.setBackground(Color.WHITE);
        weatherPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        weatherPanel.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        
        cityLabel = new JLabel("City: --");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 18));
        cityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        weatherIconLabel = new JLabel("");
        weatherIconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        weatherIconLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        temperatureLabel = new JLabel("Temperature: --");
        temperatureLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        temperatureLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        humidityLabel = new JLabel("Humidity: --");
        humidityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        humidityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        windSpeedLabel = new JLabel("Wind Speed: --");
        windSpeedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        windSpeedLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        conditionsLabel = new JLabel("Conditions: --");
        conditionsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        conditionsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        descriptionLabel = new JLabel("Description: --");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setForeground(new Color(102, 102, 102));
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        weatherPanel.add(cityLabel);
        weatherPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        weatherPanel.add(weatherIconLabel);
        weatherPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        weatherPanel.add(temperatureLabel);
        weatherPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        weatherPanel.add(humidityLabel);
        weatherPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        weatherPanel.add(windSpeedLabel);
        weatherPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        weatherPanel.add(conditionsLabel);
        weatherPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        weatherPanel.add(descriptionLabel);
        
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(unitPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 2)));
        mainPanel.add(windUnitPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(weatherPanel);
        
        JButton historyButton = new JButton("View Search History");
        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyButton.setBackground(new Color(33, 150, 243));
        historyButton.setForeground(Color.WHITE);
        historyButton.setFont(new Font("Arial", Font.BOLD, 12));
        historyButton.setFocusPainted(false);
        historyButton.setBorderPainted(false);
        historyButton.setOpaque(true);
        historyButton.addActionListener(e -> showHistory());
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(historyButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        add(scrollPane);
        setVisible(true);
    }
    
    private void searchWeather() {
        String city = cityInput.getText().trim();
        
        if (city.isEmpty()) {
            showAlert("Input Error", "Please enter a city name.");
            return;
        }
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        cityInput.setEnabled(false);
        
        SwingWorker<WeatherData, Void> worker = new SwingWorker<WeatherData, Void>() {
            @Override
            protected WeatherData doInBackground() throws Exception {
                return weatherService.getWeatherData(city);
            }
            
            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());
                cityInput.setEnabled(true);
                try {
                    currentWeatherData = get();
                    addToHistory(city);
                    updateWeatherDisplay();
                } catch (Exception e) {
                    Throwable cause = e.getCause() != null ? e.getCause() : e;
                    showAlert("Error", "Could not fetch weather data.\n\n" + cause.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    private void updateWeatherDisplay() {
        if (currentWeatherData == null) {
            return;
        }
        
        cityLabel.setText("City: " + currentWeatherData.getCityName());
        weatherIconLabel.setText(getWeatherIcon(currentWeatherData.getConditions()));
        updateTemperatureDisplay();
        humidityLabel.setText("Humidity: " + currentWeatherData.getHumidity() + "%");
        updateWindSpeedDisplay();
        conditionsLabel.setText("Conditions: " + currentWeatherData.getConditions());
        descriptionLabel.setText("Description: " + currentWeatherData.getDescription());
    }
    
    private String getWeatherIcon(String condition) {
        condition = condition.toLowerCase();

        if (condition.isEmpty()) {
            return "";
        }
        
        if (condition.contains("clear")) {
            return "\u2600";
        } else if (condition.contains("cloud")) {
            return "\u2601";
        } else if (condition.contains("rain")) {
            return "\u2614";
        } else if (condition.contains("drizzle")) {
            return "\uD83C\uDF26";
        } else if (condition.contains("thunder") || condition.contains("storm")) {
            return "\u26C8";
        } else if (condition.contains("snow")) {
            return "\u2744";
        } else if (condition.contains("mist") || condition.contains("fog") || condition.contains("haze")) {
            return "\uD83C\uDF2B";
        } else {
            return "\uD83C\uDF24";
        }
    }
    
    private void updateWindSpeedDisplay() {
        if (currentWeatherData == null) {
            return;
        }
        
        double windSpeed = currentWeatherData.getWindSpeed();
        
        if (milesRadio.isSelected()) {
            windSpeed = windSpeed * 2.23694;
            windSpeedLabel.setText(String.format("Wind Speed: %.2f mph", windSpeed));
        } else {
            windSpeedLabel.setText(String.format("Wind Speed: %.2f m/s", windSpeed));
        }
    }
    
    private void updateTemperatureDisplay() {
        if (currentWeatherData == null) {
            return;
        }
        
        double temp = currentWeatherData.getTemperature();
        
        if (fahrenheitRadio.isSelected()) {
            temp = WeatherService.celsiusToFahrenheit(temp);
            temperatureLabel.setText(String.format("Temperature: %.1f°F", temp));
        } else {
            temperatureLabel.setText(String.format("Temperature: %.1f°C", temp));
        }
    }
    
    private void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private void addToHistory(String cityName) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = now.format(formatter);
        String entry = cityName + " - " + timestamp;
        
        searchHistory.add(0, entry);
        historyListModel.add(0, entry);
        
        if (historyListModel.size() > 10) {
            historyListModel.remove(10);
            searchHistory.remove(10);
        }
    }
    
    private void showHistory() {
        if (searchHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No search history yet. Try searching for a city!", 
                "Search History", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JDialog historyDialog = new JDialog(this, "Search History", true);
        historyDialog.setSize(400, 300);
        historyDialog.setLocationRelativeTo(this);
        
        JList<String> dialogHistoryList = new JList<>(historyListModel);
        dialogHistoryList.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(dialogHistoryList);
        
        historyDialog.add(scrollPane);
        historyDialog.setVisible(true);
    }
    
    private void updateBackgroundByTime() {
        int hour = LocalDateTime.now().getHour();
        Color backgroundColor;
        
        if (hour >= 6 && hour < 12) {
            backgroundColor = new Color(255, 248, 220);
        } else if (hour >= 12 && hour < 17) {
            backgroundColor = new Color(227, 242, 253);
        } else if (hour >= 17 && hour < 20) {
            backgroundColor = new Color(255, 183, 77);
        } else {
            backgroundColor = new Color(52, 73, 94);
        }
        
        mainPanel.setBackground(backgroundColor);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WeatherApp());
    }
}
