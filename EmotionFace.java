/*
Chris Schmidt
7/28/2022
CIT 111
Week 8 SwingII Gui Assignment
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmotionFace extends JFrame {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 550;
    
    public static final int FACE_X = 50;
    public static final int FACE_Y = 50;
    public static final int FACE_DIAMETER = 400;
    public static final int LEFT_EYE_X = 175;
    public static final int RIGHT_EYE_X = 310;
    public static final int EYE_Y = 175;
    public static final int EYE_DIAMETER = 20;
    public static final int LEFT_EYEBROW_X = LEFT_EYE_X - 10;
    public static final int RIGHT_EYEBROW_X = RIGHT_EYE_X - 10;
    public static final int EYEBROW_Y = EYE_Y - 15;
    public static final int EYEBROW_LENGTH = EYE_DIAMETER*2;
    public static final int MOUTH_Y = 350;

    private Font fontNeutral = new Font("Helvetica", Font.PLAIN, 20);
    private Font fontHappy = new Font("Serif", Font.BOLD, 20);
    private Font fontSad = new Font("Times", Font.ITALIC, 20);
    private Font fontAngry = new Font("SansSerif", Font.BOLD|Font.ITALIC, 20);
    private Font fontSurprise = new Font("Times", Font.PLAIN, 18);
    private Font fontDejected = new Font("SansSerif", Font.PLAIN, 14);
    private Font fontMenu = new Font("Monospaced", Font.ITALIC, 26);
    private String emotion = "Neutral";
    private String mood = "";
    private static int fontPlacer = 0;
    private FontMetrics fMetrics;


    public static void main(String[] args) {
        EmotionFace bob = new EmotionFace();
        bob.setVisible(true);
    }

    private class ConfirmExit extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            ConfirmWindow check = new ConfirmWindow();
            check.setVisible(true);
        }
        public void windowIconified(WindowEvent e) {
            emotion = "Dejected";
        }
    }

    //Handling Window Closing
    private class ConfirmWindow extends JFrame implements ActionListener {
        public ConfirmWindow() {
            setSize(250, 200);
            getContentPane().setBackground(Color.WHITE);
            setLayout(new BorderLayout());
            
            JLabel exitConfirmation = new JLabel("Done with Bob?");
            exitConfirmation.setFont(fontMenu);
            add(exitConfirmation, BorderLayout.CENTER);
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout());
            JButton yesButton = new JButton("Yes");
            yesButton.addActionListener(this);
            JButton noButton = new JButton("No");
            noButton.addActionListener(this);
            buttonPanel.add(yesButton);
            buttonPanel.add(noButton);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            if(actionCommand.equals("Yes")){
                System.exit(0);
            } else if(actionCommand.equals("No")){
                dispose();
            } else {
                System.out.println("ABORT, WILL ROBINSON! ABORT!");
            }
        }
    }

    public EmotionFace() {
        super("Emotional Bob");
        //setLocationRelativeTo(null);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
        JPanel emotionPanel = new JPanel();
        add(emotionPanel, BorderLayout.SOUTH);
        emotionPanel.setLayout(new GridLayout(1, 5));
        JButton happyButton = new JButton("Happy");
        happyButton.addActionListener(new emotionListener());
        emotionPanel.add(happyButton);
        JButton sadButton = new JButton("Sad");
        sadButton.addActionListener(new emotionListener());
        emotionPanel.add(sadButton);
        JButton neutralButton = new JButton("Neutral");
        neutralButton.addActionListener(new emotionListener());
        emotionPanel.add(neutralButton);
        JButton angryButton = new JButton("Angry");
        angryButton.addActionListener(new emotionListener());
        emotionPanel.add(angryButton);
        JButton surpriseButton = new JButton("Surprised");
        surpriseButton.setActionCommand("Surprise");
        surpriseButton.addActionListener(new emotionListener());
        emotionPanel.add(surpriseButton);
        addWindowListener(new ConfirmExit());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        System.out.println("emotion is " + emotion);
    }

    public void paint(Graphics g) {
        super.paint(g);
         //Face Outline
         g.drawOval(FACE_X, FACE_Y, FACE_DIAMETER, FACE_DIAMETER);
        //Left Eye
        g.fillOval(LEFT_EYE_X, EYE_Y, EYE_DIAMETER, EYE_DIAMETER);
        //Right Eye
        g.fillOval(RIGHT_EYE_X, EYE_Y, EYE_DIAMETER, EYE_DIAMETER);

        if(emotion.equals("Neutral")){
            //Left Eyebrow
            g.drawLine(LEFT_EYEBROW_X, EYEBROW_Y, LEFT_EYEBROW_X + EYEBROW_LENGTH, EYEBROW_Y);
            //Righ Eyebrow
            g.drawLine(RIGHT_EYEBROW_X, EYEBROW_Y, RIGHT_EYEBROW_X + EYEBROW_LENGTH, EYEBROW_Y);
            //Mouth
            g.drawLine(LEFT_EYE_X, MOUTH_Y, RIGHT_EYE_X + 20, MOUTH_Y);
            g.setFont(fontNeutral);
            mood = "Bob feels nothing inside";
            fMetrics = g.getFontMetrics(fontNeutral);
            fontPlacer = (500 - fMetrics.stringWidth(mood))/2;
            g.drawString(mood, fontPlacer, FACE_DIAMETER + 100);
        } else if(emotion.equals("Happy")){
            g.drawArc(LEFT_EYEBROW_X, EYEBROW_Y, EYEBROW_LENGTH, EYE_DIAMETER, 20, 140);
            g.drawArc(RIGHT_EYEBROW_X, EYEBROW_Y, EYEBROW_LENGTH, EYE_DIAMETER, 20, 140);
            g.drawArc(LEFT_EYE_X + EYE_DIAMETER/2, MOUTH_Y - (RIGHT_EYE_X-LEFT_EYE_X)/2, RIGHT_EYE_X - LEFT_EYE_X, (RIGHT_EYE_X-LEFT_EYE_X)/2, 0, -180);
            g.setFont(fontHappy);
            mood = "Bob's elation knows no bounds";
            fMetrics = g.getFontMetrics(fontNeutral);
            fontPlacer = (500 - fMetrics.stringWidth(mood))/2;
            g.drawString(mood, fontPlacer, FACE_DIAMETER + 100);
        } else if(emotion.equals("Sad")){
            g.drawArc(LEFT_EYEBROW_X - 85, EYEBROW_Y -50, 3*EYEBROW_LENGTH, 3*EYE_DIAMETER, 0, -70);
            g.drawArc(RIGHT_EYEBROW_X, EYEBROW_Y - 50, 3*EYEBROW_LENGTH, 3*EYE_DIAMETER, 180, 70);
            g.drawArc(LEFT_EYE_X + EYE_DIAMETER/2, MOUTH_Y, RIGHT_EYE_X - LEFT_EYE_X, (RIGHT_EYE_X-LEFT_EYE_X)/2, 0, 180);
            g.setFont(fontSad);
            mood = "Bob wonders if he'll ever love again";
            fMetrics = g.getFontMetrics(fontNeutral);
            fontPlacer = (500 - fMetrics.stringWidth(mood))/2;
            g.drawString(mood, fontPlacer, FACE_DIAMETER + 100);
        }else if(emotion.equals("Angry")){
            g.drawLine(LEFT_EYEBROW_X, EYE_Y - (EYEBROW_LENGTH)/2, LEFT_EYEBROW_X + EYEBROW_LENGTH, EYE_Y);
            g.drawLine(RIGHT_EYEBROW_X, EYE_Y, RIGHT_EYEBROW_X + EYEBROW_LENGTH, EYE_Y - (EYEBROW_LENGTH)/2);
            g.drawArc(LEFT_EYE_X + EYE_DIAMETER/2, MOUTH_Y, RIGHT_EYE_X - LEFT_EYE_X, (RIGHT_EYE_X-LEFT_EYE_X)/2, 0, 180);
            g.setFont(fontAngry);
            mood = "Bob's fury breaks like an endless wave";
            fMetrics = g.getFontMetrics(fontNeutral);
            fontPlacer = (500 - fMetrics.stringWidth(mood))/2;
            g.drawString(mood, fontPlacer, FACE_DIAMETER + 100);
        }else if(emotion.equals("Surprise")){
            g.drawArc(LEFT_EYEBROW_X, EYEBROW_Y - (EYE_DIAMETER)/2, EYEBROW_LENGTH, EYE_DIAMETER/2, 20, 140);
            g.drawArc(RIGHT_EYEBROW_X, EYEBROW_Y - (EYE_DIAMETER)/2, EYEBROW_LENGTH, EYE_DIAMETER/2, 20, 140);
            g.drawOval(LEFT_EYE_X + EYE_DIAMETER/2, MOUTH_Y, RIGHT_EYE_X - LEFT_EYE_X, (RIGHT_EYE_X - LEFT_EYE_X)/2);
            g.setFont(fontSurprise);
            mood = "Bob did not expext that";
            fMetrics = g.getFontMetrics(fontNeutral);
            fontPlacer = (500 - fMetrics.stringWidth(mood))/2;
            g.drawString(mood, fontPlacer, FACE_DIAMETER + 100);
        }else if(emotion.equals("Dejected")){
            g.drawArc(LEFT_EYEBROW_X - 85, EYEBROW_Y -50, 3*EYEBROW_LENGTH, 3*EYE_DIAMETER, 0, -70);
            g.drawArc(RIGHT_EYEBROW_X, EYEBROW_Y - 50, 3*EYEBROW_LENGTH, 3*EYE_DIAMETER, 180, 70);
            g.drawArc(LEFT_EYE_X + EYE_DIAMETER/2, MOUTH_Y, RIGHT_EYE_X - LEFT_EYE_X, (RIGHT_EYE_X-LEFT_EYE_X)/2, 0, 180);
            g.setFont(fontDejected);
            mood = "Bob is feeling dejected";
            fMetrics = g.getFontMetrics(fontDejected);
            fontPlacer = (500 - fMetrics.stringWidth(mood))/2;
            g.drawString(mood, fontPlacer, FACE_DIAMETER + 100);
        }
    }

    private class emotionListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        emotion = e.getActionCommand();
        repaint();
        //System.out.println("Current emotion is " + emotion);
    }

    }
}
