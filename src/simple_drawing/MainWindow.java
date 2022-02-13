package simple_drawing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private DrawingComponent _drawingComponent;
    private Timer _timer;

    public MainWindow() {
        initComponents();
    }

    private void initComponents() {
        // Close the application when the user closes the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setTitle("Simple drawing in Java");

        // Create all the components in the window
        getContentPane().add(createDrawingComponent(), BorderLayout.CENTER);
        getContentPane().add(createControls(), BorderLayout.SOUTH);

        // Perform layout of components
        pack();
    }

    private Component createDrawingComponent() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder());

        _drawingComponent = new DrawingComponent();
        _drawingComponent.setMultiSegmentLines(30, 5, 30);

        panel.add(_drawingComponent);
        return panel;
    }

    private Component createControls() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        GridBagConstraints gbc;
        JComponent comp;

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        comp = new JCheckBox(new EnableAnimationAction());
        panel.add(comp, gbc);

        return panel;
    }

    private void startTimer() {
        // Stop the existing timer if there is one
        stopTimer();

        // Create a new timer and start it
        _timer = new Timer(30, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                _drawingComponent.repaint();
            }
        });

        _timer.start();
    }

    private void stopTimer() {
        if (_timer != null) {
            _timer.stop();
            _timer = null;
        }
    }

    ///////////
    private class EnableAnimationAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        public EnableAnimationAction() {
            super("Enable animation");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox jcb = (JCheckBox) e.getSource();
            if (jcb.isSelected()) {
                startTimer();
            } else {
                stopTimer();
            }
        }
    }

}
