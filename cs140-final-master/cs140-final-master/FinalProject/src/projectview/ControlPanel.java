package projectview;

import java.awt.GridLayout;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class ControlPanel implements Observer{
    private JMenuItem view;
    private JMenuItem stepButton = new JMenuItem("Step");
    private JMenuItem clearButton = new JMenuItem("Clear");
    private JMenuItem runButton = new JMenuItem("Run");
    private JMenuItem reloadButton = new JMenuItem("Reload");

    public ControlPanel(ViewMediator gui) { 
        view = gui; gui.addObserver(this); 
    }

    public JComponent createControlDisplay(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,0));

        stepButton.addActionListener(e -> view.step());
        panel.add(stepButton);

        clearButton.addActionListener(e -> view.clearJob());
        panel.add(clearButton);

        runButton.addActionListener(e -> view.toggleAutoStep());
        panel.add(runButton);

        reloadButton.addActionListener(e -> view.reload());
        panel.add(reloadButton);

        JSlider slider = new JSlider(5,1000);
        slider.addChangeListener(e -> view.setPeriod(slider.getValue()));
        panel.add(slider);

        return panel;
    }

    @Override
    public void update(Observable arg0, Object arg1){
        runButton.setEnabled(view.getCurrentState().getRunPauseActive());
        stepButton.setEnabled(view.getCurrentState().getStepActive());
        clearButton.setEnabled(view.getCurrentState().getClearActive());
        reloadButton.setEnabled(view.getCurrentState().getReloadActive());
    }
}