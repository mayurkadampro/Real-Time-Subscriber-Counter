package youtube_live;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YouTube_Live extends Thread {

    private JSONObject myResponse1;
    private JSONObject myResponse2;
    private long subscribe;
    private JLabel label1;
    //private JLabel label3;
    private final JWindow win;
    private final String id = "UCeVmancWx92vTZ9IPYOKnKg"; //paste channel id here
    private final String Username = "Mighty Ghost Hack";
    private final String APIKey = "AIzaSyD4a5qZCNfXIq7j0EcOB6pbonCY7eeEyFg";
    String URL = "https://www.youtube.com/channel/UCeVmancWx92vTZ9IPYOKnKg"; //channel url
    private final JLabel label2;
    private NumberFormat nf;
    private final int width = 800;
    private final int height = 800;
    Toolkit tk = Toolkit.getDefaultToolkit();
    Dimension screenSize = tk.getScreenSize();
    int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    boolean past_update = true;
    double new_sub;
    double new_sub1;

    public void Call_me() throws Exception {
        nf = NumberFormat.getInstance(Locale.ENGLISH);

        Thread thread = new Thread() {
            public void run() {
                try {
                    for (;;) {
                        String url = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + id + "&key=" + APIKey;
                        URL obj = new URL(url);
                        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                        // optional default is GET
                        con.setRequestMethod("GET");
                        //add request header
                        con.setRequestProperty("User-Agent", "Mozilla/5.0");
                        StringBuilder response;
                        try (
                                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                            String inputLine;
                            response = new StringBuilder();
                            while ((inputLine = in.readLine()) != null) {
                                response.append(inputLine);
                            }
                        }

                        JSONObject myResponse = new JSONObject(response.toString());
                        JSONArray jsonarray = new JSONArray(myResponse.getJSONArray("items").toString());

                        for (int i = 0; i < jsonarray.length(); i++) {

                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            JSONObject myResponse1 = new JSONObject(jsonobject.toString(i));
                            JSONObject myResponse2 = new JSONObject(jsonobject.getJSONObject("statistics").toString());
                            double subscribe = Integer.parseInt(myResponse2.getString("subscriberCount"));
                            //below inside of if condition it's minus the subscriber only in the begining 
                            //only for show the counting........ so it's look similalr to socialblade
                            if (past_update) {
                                double new_sub = subscribe - 10;
                                System.out.println(new_sub);
                                label1.setText(nf.format(new_sub));
                                for (int j = 0; j < 10; j++) {
                                    new_sub1 = new_sub + j;
                                    TimeUnit.MILLISECONDS.sleep(3000);
                                    System.out.println(new_sub1);
                                    label1.setText(nf.format(new_sub1));
                                    label1.setSize(200, 80);
                                    label1.setLocation(width / 2 - 80, height / 2 + 40);
                                    label1.setFont(new Font("Roboto", Font.BOLD, 28));
                                }
                            }
                            label1.setText(nf.format(subscribe));
                            label1.setSize(200, 80);
                            label1.setLocation(width / 2 - 80, height / 2 + 40);
                            label1.setFont(new Font("Roboto", Font.BOLD, 28));
                            past_update = false;
                        }
                    }
                } catch (MalformedURLException ex) {
                    Logger.getLogger(YouTube_Live.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(YouTube_Live.class.getName()).log(Level.SEVERE, null, ex);
                } catch (JSONException ex) {
                    Logger.getLogger(YouTube_Live.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(YouTube_Live.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        };
        thread.start();

    }

    public YouTube_Live() throws Exception {

        win = new JWindow();
        label1 = new JLabel(String.valueOf(new_sub), SwingConstants.CENTER);
        label2 = new JLabel(Username, SwingConstants.CENTER);
        label2.setForeground(new Color(179, 56, 44));
        label2.setSize(350, 50);
        label2.setLocation(width / 2 - 155, height / 2);
        label2.setFont(new Font("Roboto", Font.BOLD, 35));
        label2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(URL);
                            desktop.browse(uri);
                        } catch (IOException | URISyntaxException ex) {
                        }
                    }
                }
            }
        }
        );
        win.add(label2);
        win.add(label1);
        win.pack();
        win.getContentPane().setBackground(new Color(238, 238, 238));
        win.setShape(new RoundRectangle2D.Double(width / 2 - 190, height / 2 - 20, width / 2, 130, 50, 50));
        win.setLayout(null);
        win.setSize(width, height);
        win.setLocation(screenWidth-620, screenHeight-1225);
        win.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        YouTube_Live yt = new YouTube_Live();
        yt.Call_me();
    }

}
