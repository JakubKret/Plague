import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigScreen extends JFrame {
    private JSlider speedSlider;
    private JSlider contagiousnessSlider;
    private JSlider deathRateSlider;
    private JSlider healRateSlider;
    private JSlider delaySlider;
    private JSlider scaleSlider;
    //private JSlider heightSlider;
    private JSlider densitySlider;
    private JSlider maxPeopleSlider;
    private JSlider maxAnimalsSlider;
    private JButton startButton;
    private ConfigListener listener;

    public ConfigScreen(ConfigListener listener) {
        this.listener = listener;
        setTitle("Configuration");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1));
        speedSlider = new JSlider(1, 1000, 10);
        contagiousnessSlider = new JSlider(0,100,30);// /100
        deathRateSlider = new JSlider(0,100,15);// /100
        healRateSlider = new JSlider(0, 100, 15); // /100
        densitySlider = new JSlider(0, 100, 10); // /100
        delaySlider = new JSlider(0,1000,200);
        scaleSlider = new JSlider(1,50,25);// /10
        //heightSlider = new JSlider(0,1000,400);//
        maxPeopleSlider = new JSlider(0,20,5);
        maxAnimalsSlider = new JSlider(0,20,1);

        JPanel speedPanel = new JPanel(new BorderLayout());
        speedPanel.add(new JLabel("Speed:"), BorderLayout.NORTH);
        speedPanel.add(speedSlider, BorderLayout.CENTER);

        JPanel contagiousnessPanel = new JPanel(new BorderLayout());
        contagiousnessPanel.add(new JLabel("Contagiousness:"), BorderLayout.NORTH);
        contagiousnessPanel.add(contagiousnessSlider, BorderLayout.CENTER);

        JPanel deathRatePanel = new JPanel(new BorderLayout());
        deathRatePanel.add(new JLabel("Death rate:"), BorderLayout.NORTH);
        deathRatePanel.add(deathRateSlider, BorderLayout.CENTER);

        JPanel healRatePanel = new JPanel(new BorderLayout());
        healRatePanel.add(new JLabel("Heal rate:"), BorderLayout.NORTH);
        healRatePanel.add(healRateSlider, BorderLayout.CENTER);

        JPanel delayPanel = new JPanel(new BorderLayout());
        delayPanel.add(new JLabel("Delay:"), BorderLayout.NORTH);
        delayPanel.add(delaySlider, BorderLayout.CENTER);

        JPanel densityPanel = new JPanel(new BorderLayout());
        densityPanel.add(new JLabel("Density:"), BorderLayout.NORTH);
        densityPanel.add(densitySlider, BorderLayout.CENTER);

        JPanel scalePanel = new JPanel(new BorderLayout());
        scalePanel.add(new JLabel("Scale:"), BorderLayout.NORTH);
        scalePanel.add(scaleSlider, BorderLayout.CENTER);

//        JPanel heightPanel = new JPanel(new BorderLayout());
//        heightPanel.add(new JLabel("Speed:"), BorderLayout.NORTH);
//        heightPanel.add(heightSlider, BorderLayout.CENTER);

        JPanel maxPeoplePanel = new JPanel(new BorderLayout());
        maxPeoplePanel.add(new JLabel("Max People per Tile:"), BorderLayout.NORTH);
        maxPeoplePanel.add(maxPeopleSlider, BorderLayout.CENTER);

        JPanel maxAnimalsPanel = new JPanel(new BorderLayout());
        maxAnimalsPanel.add(new JLabel("Max Animal per Tile:"), BorderLayout.NORTH);
        maxAnimalsPanel.add(maxAnimalsSlider, BorderLayout.CENTER);

        startButton = new JButton("Start");
        add(speedPanel);
        add(contagiousnessPanel);
        add(deathRatePanel);
        add(healRatePanel);
        add(delayPanel);
        add(densityPanel);
        add(scalePanel);
//        add(heightPanel);
        add(maxPeoplePanel);
        add(maxAnimalsPanel);
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int speed = speedSlider.getValue();
                double contagiousness = contagiousnessSlider.getValue();
                double deathRate = deathRateSlider.getValue();
                double healRate = healRateSlider.getValue();
                int delay = delaySlider.getValue();
                double density = densitySlider.getValue();
                double scale  = scaleSlider.getValue();
//                int height = heightSlider.getValue();
                int maxPeople = maxPeopleSlider.getValue();
                int maxAnimals = maxAnimalsSlider.getValue();
                listener.onConfigSelected(speed, contagiousness,deathRate,healRate,delay,density,scale,/*,height*/maxPeople,maxAnimals);
                dispose(); // Close the configuration screen
            }
        });
    }

    public interface ConfigListener {
        void onConfigSelected(int speed, double contagiousness, double deathRate,double healRate,
                              int delay, double density, double scale/*,int height*/,
                              int maxPeople,int maxAnimals);
    }
}
