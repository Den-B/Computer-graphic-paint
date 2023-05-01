package Forms.CustomComponents;

import Forms.MainCanvas;
import Forms.MenuButtonEvent.BlockColorListener;
import Helpers.MosaicPattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class SizeChangingWindow extends JDialog {

    public SizeChangingWindow(JFrame owner, MainCanvas canvas, boolean isModal){
        Dimension dimension = canvas.getSize();

        setResizable(false);
        setBounds((owner.getX()+owner.getWidth()/4),(owner.getY()+owner.getHeight()/4), 250,150);
        setTitle("Choose canvas size");
        setLayout(new BorderLayout());
        this.setModal(isModal);
        JDialog thisDialog = this;

        JPanel chooseArea = new JPanel();
        chooseArea.setLayout(new BoxLayout(chooseArea,BoxLayout.Y_AXIS));
        chooseArea.setBackground(Color.WHITE);
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(dimension.height,0,1600,1));
        JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(dimension.width,0,1024,1));
        chooseArea.add(GenerateSizeLine("width:  ",widthSpinner));
        chooseArea.add(GenerateSizeLine("height: ",heightSpinner));
        add(chooseArea,BorderLayout.CENTER);

        JPanel buttonArea = new JPanel();
        buttonArea.setBackground(Color.WHITE);
        buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER,getWidth()/5,10));

        CustomButton okButton = new CustomButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.resizeScreen(new Dimension((int)widthSpinner.getValue(),(int)heightSpinner.getValue()));
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
        container.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
        container.add(new JLabel(name));
        container.add(spinner);
        return container;
    }

}
