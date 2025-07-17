import netscape.javascript.JSObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONObject;
import org.json.JSONArray;

public class RecipeSearch extends JFrame {
    public static void main (String[] args) {
        JFrame frame = new JFrame("Recipe Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(60,60,60,60));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));


        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Got any Cravings?");
        title.setFont(new Font("Inter", Font.BOLD, 36));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        Color tomatoRed = new Color(0xFF6347);
        title.setForeground(tomatoRed);
        innerPanel.add(title);

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 20));
        searchField.setMaximumSize(new Dimension(300, 30));
        searchField.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(searchField);
        innerPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(120, 40));
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        Color green = new Color(0x228B22);
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.setForeground(green);
        innerPanel.add(searchButton);

        frame.add(panel, BorderLayout.CENTER);
        JLabel ResultLabel = new JLabel("Search for a recipe by name, type , etc");
        ResultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        innerPanel.add(ResultLabel);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(innerPanel, c);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String Query = searchField.getText();
                ResultLabel.setText("Viewing options for  " + Query);
                String query = searchField.getText();
                String ApiKey = "7463621531964bf99fbe958e9ba7f126";
                String apiUrl = "https://api.spoonacular.com/recipes/complexSearch?query="
                        + query + "&number=30&apiKey=" + ApiKey + "&intolerances=gluten";

                try {
                    URL url = new URL(apiUrl);
                    java.net.HttpURLConnection connection = (java.net.HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String InputLine;
                    StringBuilder content = new StringBuilder();
                    while((InputLine = input.readLine()) != null) {
                        content.append(InputLine);
                    }
                    input.close();
                    connection.disconnect();
                    JSONObject json = new JSONObject(content.toString());
                    JSONArray results = json.getJSONArray("results");

                    StringBuilder html = new StringBuilder("<html>");
                    for (int i = 0; i < results.length(); i++) {
                        String title = results.getJSONObject(i).getString("title");
                        html.append("• ").append(title).append("<br>");
                    }
                    html.append("</html>");
                    ResultLabel.setText(html.toString());

                } catch (IOException ex) {
                    ResultLabel.setText("Error");
                    throw new RuntimeException(ex);
                }
            }
        });
        frame.setVisible(true);
    }
}