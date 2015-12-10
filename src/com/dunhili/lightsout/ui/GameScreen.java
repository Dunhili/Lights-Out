package com.dunhili.lightsout.ui;

import com.dunhili.lightsout.board.Level;
import com.dunhili.lightsout.utils.LevelUtil;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class GameScreen extends JPanel implements ActionListener {
	static {
		loadLog4jFile();
		levels = LevelUtil.readLevelsFromFile();
	}
	
	private static final long serialVersionUID = 4263327705957161268L;
	private static final Logger log = Logger.getLogger(GameScreen.class);
	
	private static List<Level> levels;
	
	public GameScreen() {
		setupTitleWidgets();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    if ("begin".equals(e.getActionCommand())) {
	        setupGameWidgets();
	    }
	}
	
	private void setupTitleWidgets() {
		log.info("setting up title screen widgets...");
		clearScreen();
		
		JLabel titleLabel = new JLabel("Lights Out");
        
        JButton startButton = new JButton("Begin");
        startButton.setPreferredSize(new Dimension(100, 50));
        startButton.setActionCommand("begin");
        startButton.addActionListener(this);
        
        add(titleLabel);
        add(startButton);
        log.info("done setting up title screen.");
	}
	
	private void setupGameWidgets() {
		log.info("setting up game widgets...");
		clearScreen();
		
		
		revalidate();
		log.info("done setting up game.");
	}
	
	private void clearScreen() {
		removeAll();
		revalidate(); 
		repaint();
	}
	
	private static void loadLog4jFile() {
		try {
			Properties props = new Properties();
			props.load(GameScreen.class.getResourceAsStream("/resources/log4j.properties"));
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
    
    private static void createAndShowGUI() {
    	log.info("Displaying title screen.");
        JFrame frame = new JFrame("Lights Out");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(400, 400));
        
        GameScreen gameScreen = new GameScreen();
        frame.setContentPane(gameScreen);
        
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(WindowEvent e) {
            	LevelUtil.saveLevels(levels);
            }
        });
        
        log.info("Done creating title screen.");
    }
    
    public static void main(String[] args) {
        log.info("Starting Lights Out game...");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
