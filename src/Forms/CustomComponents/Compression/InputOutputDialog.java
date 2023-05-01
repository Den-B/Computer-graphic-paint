package Forms.CustomComponents.Compression;

import Forms.CustomComponents.CustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;

public class InputOutputDialog extends JDialog {

    private final JTextField inputFile = new JTextField();
    private final JTextField outputFile= new JTextField();
    private final CustomButton okButton = new CustomButton("Ok");

    public InputOutputDialog (String dialogName, JFrame owner, boolean isModal){

        //this.setResizable(false);
        setBounds((owner.getX()+owner.getWidth()/4),(owner.getY()+owner.getHeight()/4), 550,170);
        setTitle(dialogName);
        setLayout(new BorderLayout());
        this.setModal(isModal);
        JDialog thisDialog = this;

        JPanel chooseArea = new JPanel();
        chooseArea.setLayout(new BoxLayout(chooseArea,BoxLayout.Y_AXIS));
        chooseArea.setBackground(Color.WHITE);

        chooseArea.add(generateLine("input file", inputFile));
        chooseArea.add(generateLine("output file", outputFile));
        add(chooseArea,BorderLayout.CENTER);

        JPanel buttonArea = new JPanel();
        buttonArea.setBackground(Color.WHITE);
        buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER,50,10));

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(thisDialog, WindowEvent.WINDOW_CLOSING));
            }
        });
        CustomButton cancelButton = new CustomButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(thisDialog, WindowEvent.WINDOW_CLOSING));
            }
        });
        buttonArea.add(okButton);
        buttonArea.add(cancelButton);
        add(buttonArea, BorderLayout.SOUTH);
    }

    public void addOkButtonActionListener(ActionListener listener){
        okButton.addActionListener(listener);
    }


    private Container generateLine(String label, JTextField field){
        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
        field.setEditable(false);
        field.setColumns(30);
        Label newLabel = new Label(label+":");
        newLabel.setPreferredSize(new Dimension(70,20));
        container.add(newLabel);
        container.add(field);
        CustomButton button = new CustomButton("Edit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(new File("C:\\Users\\User\\OneDrive\\Рабочий стол\\Компьютерная графика\\Лаба 3\\testData"));
                int ret = fileChooser.showDialog(null, "Ok");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    field.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
        container.add(button);
        return container;
    }

    public String getInputFilePath(){
        return inputFile.getText();
    }

    public String getOutputFilePath(){
        return outputFile.getText();
    }

}
