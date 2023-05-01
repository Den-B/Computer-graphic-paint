package Forms.CustomComponents;

import Forms.MainCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import Painter.BasicRasterAlgorithm;

public class BasicRasterAlgorithmWindow extends JDialog{

    public BasicRasterAlgorithmWindow(JFrame owner, MainCanvas canvas, boolean isModal){
        Dimension dimension = canvas.getSize();

        setResizable(false);
        setBounds((owner.getX()+owner.getWidth()/4),(owner.getY()+owner.getHeight()/4), 250,200);
        setTitle("Choose algorithm options.");
        setLayout(new BorderLayout());
        this.setModal(isModal);
        JDialog thisDialog = this;

        JPanel chooseArea = new JPanel();
        chooseArea.setLayout(new BoxLayout(chooseArea,BoxLayout.Y_AXIS));
        chooseArea.setBackground(Color.WHITE);
        chooseArea.setMaximumSize(new Dimension(50, 200));
        JSpinner startDegree = new JSpinner(new SpinnerNumberModel(0,0,360,0.1));
        JSpinner endDegree = new JSpinner(new SpinnerNumberModel(360,0,360,0.1));
        JSpinner step = new JSpinner(new SpinnerNumberModel(1,0,360,0.1));
        chooseArea.add(GenerateSizeLine("From degree:  ", startDegree));
        chooseArea.add(GenerateSizeLine("To degree: ", endDegree));
        chooseArea.add(GenerateSizeLine("Using step: ", step));
        add(chooseArea,BorderLayout.CENTER);

        JPanel buttonArea = new JPanel();
        buttonArea.setBackground(Color.WHITE);
        buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER,20,10));

        CustomButton okButton = new CustomButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedImage image = canvas.getBufferedImage();
                BasicRasterAlgorithm.paint(image, (Double)startDegree.getValue(), (Double)endDegree.getValue(), (Double)step.getValue());
                canvas.addImage(image);
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

    private Container GenerateSizeLine(String name, JSpinner spinner){
        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.TRAILING, 30, 8));
        container.add(new JLabel(name));
        container.add(spinner);
        return container;
    }
}
