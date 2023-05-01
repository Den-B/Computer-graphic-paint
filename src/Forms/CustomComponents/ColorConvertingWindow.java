package Forms.CustomComponents;

import Helpers.ColorConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColorConvertingWindow extends JFrame {

    public ColorConvertingWindow(){
        this.setLocationRelativeTo(null);
        this.setSize(300,200);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        JFrame frame = this;

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Convert RGB to XYZ", new RgbToXyzConverterPanel());
        tabbedPane.addTab("Convert XYZ to RGB", new XyzToRgbConverterPanel());
        this.add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new CustomButton("Cancel");
        cancelButton.addActionListener(e -> frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING)));
        buttonPanel.add(cancelButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

    }

}

class XyzToRgbConverterPanel extends  ConverterPanel{

    static protected String[] colorsCharacteristicsRgb = {"R", "G", "B"};
    static protected String[] colorsCharacteristicsXyz = {"X", "Y", "Z"};

    public XyzToRgbConverterPanel() {
        super(colorsCharacteristicsXyz, colorsCharacteristicsRgb, new SpinnerNumberModel(0,0,110,0.001));
        updateResults();
        for(Map.Entry<String,JSpinner> spinner: this.inputFields.entrySet()){
            spinner.getValue().addChangeListener(e -> updateResults());
        }
    }

    private void updateResults(){
        Map<String, Double> resultMatrix = ColorConverter.convertXyzToRgb(
                Double.parseDouble(inputFields.get("X").getValue().toString()),
                Double.parseDouble(inputFields.get("Y").getValue().toString()),
                Double.parseDouble(inputFields.get("Z").getValue().toString()));
        for(Map.Entry<String, JTextField> field : answerFields.entrySet()) {
            field.getValue().setText((new DecimalFormat("#.#####")).format(resultMatrix.get(field.getKey())));
        }
    }
}

class RgbToXyzConverterPanel extends  ConverterPanel{

    static protected String[] colorsCharacteristicsRgb = {"R", "G", "B"};
    static protected String[] colorsCharacteristicsXyz = {"X", "Y", "Z"};

    public RgbToXyzConverterPanel() {
        super(colorsCharacteristicsRgb, colorsCharacteristicsXyz, new SpinnerNumberModel(0,0,255,1));
        updateResults();
        for(Map.Entry<String,JSpinner> spinner: this.inputFields.entrySet()){
            spinner.getValue().addChangeListener(e -> updateResults());
        }
    }

    private void updateResults(){
        Map<String, Double> resultMatrix = ColorConverter.convertRgbToXyz(
                (int)inputFields.get("R").getValue(),
                (int)inputFields.get("G").getValue(),
                (int)inputFields.get("B").getValue());
        for(Map.Entry<String, JTextField> field : answerFields.entrySet()) {
            field.getValue().setText((new DecimalFormat("#.#####")).format(resultMatrix.get(field.getKey())));
        }
    }
}


abstract class ConverterPanel extends JPanel{

    protected HashMap<String, JTextField> answerFields = new LinkedHashMap<>();
    protected HashMap<String, JSpinner> inputFields = new LinkedHashMap<>();

    public ConverterPanel(String[] inputFieldNames,String[] answerFieldNames, SpinnerNumberModel model){
        for(String name: inputFieldNames){
            inputFields.put(name, new JSpinner(new SpinnerNumberModel(model.getNumber(),model.getMinimum(),model.getMaximum(),model.getStepSize())));//model
        }
        for(String name: answerFieldNames){
            answerFields.put(name, new JTextField());
        }
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(createColumnPanelOfSpinnerColorCharacteristics(inputFields));
        this.add(createColumnPanelOfEditColorCharacteristics(answerFields));

    }

    private JPanel createInputLine(Map.Entry<String,JSpinner> field){
        JPanel panel = createStandardPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(new JLabel(field.getKey()+":"));
        JSpinner spinner = field.getValue();
        panel.add(spinner);
        return panel;
    }

    private JPanel createResultLine(Map.Entry<String,JTextField> filed){
        JPanel panel = createStandardPanel();
        new FlowLayout(FlowLayout.CENTER);
        panel.add(new JLabel(filed.getKey()+":"));
        JTextField field = filed.getValue();
        field.setEditable(false);
        field.setColumns(7);
        panel.add(field);
        return panel;
    }

    private JPanel createColumnPanelOfSpinnerColorCharacteristics(Map<String, JSpinner> colorCharacteristics){
        JPanel workPanel = createStandardPanel();
        workPanel.setLayout(new BoxLayout(workPanel,BoxLayout.Y_AXIS));
        for(Map.Entry<String, JSpinner> name: colorCharacteristics.entrySet()){
            workPanel.add(createInputLine(name));
        }
        return workPanel;
    }

    private JPanel createColumnPanelOfEditColorCharacteristics(Map<String,JTextField> fields){
        JPanel workPanel = createStandardPanel();
        workPanel.setLayout(new BoxLayout(workPanel,BoxLayout.Y_AXIS));
        for(Map.Entry<String,JTextField> filed: fields.entrySet()){
            workPanel.add(createResultLine(filed));
        }
        return workPanel;
    }

    private JPanel createStandardPanel(){
        JPanel standardPanel = new JPanel();
        standardPanel.setBackground(Color.WHITE);
        return standardPanel;
    }

}


