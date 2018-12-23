/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package youtube_live;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Mayur
 */
public class WidgetOfSubscriber extends javax.swing.JWindow {

    private int pressedx;
    private int pressedy;
    private NumberFormat nf;
    private final String APIKey = "AIzaSyD4a5qZCNfXIq7j0EcOB6pbonCY7eeEyFg";
    private String ChannelLink;
    boolean past_update = true;
    double new_sub;
    double new_sub1;
    private double subscribeMileStoneAlert = 12000.0; // you need to set the milestone base on your subscribe
    private boolean milestone_update = true;
    private boolean UpdateSubWhenStart = true;
    private double subscriberUpdateWhenStart;
    private double subscriberUpdateWhenStartforminus;
    private int countforlink = 0;
    private int countformusic = 0;
    Image image = null;
    ImageIcon img = new ImageIcon("C:\\Users\\Mayur\\Documents\\NetBeansProjects\\YouTube_Live\\src\\Images\\refresh.png");
    ImageIcon img1 = new ImageIcon("C:\\Users\\Mayur\\Documents\\NetBeansProjects\\YouTube_Live\\src\\Images\\mute.png");
    ImageIcon imgexit = new ImageIcon("C:\\Users\\Mayur\\Documents\\NetBeansProjects\\YouTube_Live\\src\\Images\\music-player.png");
    ImageIcon img1exit = new ImageIcon("C:\\Users\\Mayur\\Documents\\NetBeansProjects\\YouTube_Live\\src\\Images\\mute_trans.png");
    String workingDir = System.getProperty("user.dir");
    File file;
    FileWriter fileWriter;
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;

    /**
     * Creates new form WidgetOfSubscriber
     *
     * @param Id
     */
    public WidgetOfSubscriber(String Id) throws Exception {
        initComponents();
        jLabel1.setBackground(new Color(0, 0, 0, 0));
        jLabel5.setText(Id);
        jLabel3.setText("X");
        jLabel6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(ChannelLink);
                            desktop.browse(uri);
                        } catch (IOException ex) {
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(WidgetOfSubscriber.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }

        });
        this.setLocation(width-410,1);
        this.setBackground(new Color(0, 0, 0, 0));

    }

    private WidgetOfSubscriber() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void Call_me(String id) throws Exception {
        nf = NumberFormat.getInstance(Locale.ENGLISH);
        Thread thread;
        thread = new Thread() {
            public void run() {
                try {
                    for (;;) {
                        String url = "https://www.googleapis.com/youtube/v3/channels?part=snippet,statistics&id=" + id + "&key=" + APIKey;
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
                            JSONObject myResponse3 = new JSONObject(jsonobject.getJSONObject("snippet").toString());
                            JSONObject myResponse4 = new JSONObject(myResponse3.getJSONObject("thumbnails").toString());
                            JSONObject myResponse5 = new JSONObject(myResponse4.getJSONObject("default").toString());
                            String Profilelink = myResponse5.getString("url");
                            URL url11 = new URL(Profilelink);
                            image = ImageIO.read(url11);
                            jLabel6.setIcon(new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
                            String Username = myResponse3.getString("title");
                            String link = myResponse3.getString("customUrl");
                            ChannelLink = "www.youtube.com/" + link;
                            jLabel4.setText(Username);
                            double subscribe = Integer.parseInt(myResponse2.getString("subscriberCount"));
                            file = new File(workingDir + "\\ID.txt");
                            fileWriter = new FileWriter(file);
                            fileWriter.write(id);
                            fileWriter.close();
                            //below inside of if condition it's minus the subscriber only in the begining 
                            //only for show the counting........ so it's look similar to socialblade
                            if (past_update) {
                                double new_sub = subscribe - 10;
                                //System.out.println(new_sub);
                                jLabel5.setText(nf.format(new_sub));
                                for (int j = 0; j < 10; j++) {
                                    new_sub1 = new_sub + j;
                                    TimeUnit.MILLISECONDS.sleep(3000);
                                    jLabel5.setText(nf.format(new_sub1));
                                    past_update = false;
                                }
                            }

                            if (UpdateSubWhenStart) {
                                subscriberUpdateWhenStart = subscribe;
                                subscriberUpdateWhenStart += 1;
                                UpdateSubWhenStart = false;
                            }

                            if (subscribe == subscriberUpdateWhenStart) {
                                subscriberUpdateWhenStart += 1;
                                if (countformusic % 2 == 0) {
                                    playAlertOnEachSubscribe();
                                }
                                subscriberUpdateWhenStartforminus = subscribe;
                                subscriberUpdateWhenStartforminus -= 1;

                            } else if (subscribe == subscriberUpdateWhenStartforminus) {
                                subscriberUpdateWhenStartforminus -= 1;
                                if (countformusic % 2 == 0) {
                                    playAlertForLostSub();
                                }
                                subscriberUpdateWhenStart = subscribe;
                                subscriberUpdateWhenStart += 1;
                            } else {
                                subscriberUpdateWhenStart = subscribe;
                                subscriberUpdateWhenStart += 1;
                                subscriberUpdateWhenStartforminus = subscribe;
                                subscriberUpdateWhenStartforminus -= 1;

                            }

                            if (milestone_update) {
                                if (subscribe == subscribeMileStoneAlert) {
                                    playAlertOnReachMilestone();
                                    milestone_update = false;
                                }

                            }

                            jLabel5.setText(nf.format(subscribe));
                        }
                    }
                } catch (MalformedURLException ex) {

                } catch (IOException | JSONException | InterruptedException ex) {

                }

            }
        };
        thread.start();

    }

    public void playAlertOnReachMilestone() {
        try {
            File sound = new File("C:\\Users\\Mayur\\Documents\\NetBeansProjects\\YouTube_Live\\src\\Sound\\beep-07.wav");
            AudioInputStream audio = null;
            audio = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {

        }

    }

    public void playAlertOnEachSubscribe() {
        try {
            File sound = new File("C:\\Users\\Mayur\\Documents\\NetBeansProjects\\YouTube_Live\\src\\Sound\\coin.wav");
            AudioInputStream audio = null;
            audio = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {

        }
    }

    public void playAlertForLostSub() {
        try {
            File sound = new File("C:\\Users\\Mayur\\Documents\\NetBeansProjects\\YouTube_Live\\src\\Sound\\lost.wav");
            AudioInputStream audio = null;
            audio = AudioSystem.getAudioInputStream(sound);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(451, 164));
        setMinimumSize(new java.awt.Dimension(451, 164));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/widget1.png"))); // NOI18N

        jLabel2.setAlignmentY(0.0F);
        jLabel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel2MouseDragged(evt);
            }
        });
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel2MousePressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(238, 238, 238));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("X");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 0, 0));
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 31)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(156, 8, 8));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel6MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(238, 238, 238));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/music-player.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel7MouseExited(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(75, 75, 75)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(361, 361, 361)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MousePressed
        pressedx = evt.getX();
        pressedy = evt.getY();
    }//GEN-LAST:event_jLabel2MousePressed

    private void jLabel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseDragged
        int corX = evt.getXOnScreen();
        int corY = evt.getYOnScreen();
        this.setLocation(corX - pressedx, corY - pressedy);
    }//GEN-LAST:event_jLabel2MouseDragged

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered

    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        jLabel3.setForeground(new Color(156, 8, 8));
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        jLabel3.setForeground(new Color(238, 238, 238));
    }//GEN-LAST:event_jLabel3MouseExited

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited

    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked

    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel6MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked

        if (countformusic % 2 == 0) {
            jLabel7.setIcon(img1);
            countformusic++;
        } else {
            jLabel7.setIcon(img);
            countformusic++;

        }

    }//GEN-LAST:event_jLabel7MouseClicked

    private void jLabel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseEntered
        if (countformusic % 2 == 0) {
            jLabel7.setIcon(img);
        } else {
            jLabel7.setIcon(img1);

        }
    }//GEN-LAST:event_jLabel7MouseEntered

    private void jLabel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseExited
        if (countformusic % 2 == 0) {
            jLabel7.setIcon(imgexit);
        } else {
            jLabel7.setIcon(img1exit);
        }
    }//GEN-LAST:event_jLabel7MouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WidgetOfSubscriber.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WidgetOfSubscriber().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    // End of variables declaration//GEN-END:variables
}
