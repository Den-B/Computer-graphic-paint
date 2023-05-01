package Forms.CustomComponents;

import Forms.MainCanvas;
import Forms.MenuButtonEvent.BlockColorListener;
import Helpers.MosaicPattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MosaicPatternChooser {

    private Color[] colors = {Color.WHITE, Color.BLACK, Color.RED, Color.BLUE};
    private int size = 2;
    private final String toolName;

    public MosaicPatternChooser(String toolName){
        this.toolName = toolName;
    }

    public MosaicPatternChooser(MosaicPattern pattern, String toolName){
        this.toolName = toolName;
        if(pattern != null){
            this.size = pattern.getSize();
            this.colors = pattern.getColors();
        }
    }


    public JDialog createDialog(JFrame owner, MainCanvas canvas, boolean isModal){
        JDialog dialog = new JDialog(owner,isModal);
        dialog.setBounds((owner.getX()+owner.getWidth()/4),(owner.getY()+owner.getHeight()/4),
                250,270);
        dialog.setResizable(false);
        dialog.setTitle("Choose mosaic pattern");
        dialog.setLayout(new BorderLayout());

        JPanel chooseArea = new JPanel();
        chooseArea.setLayout(new BoxLayout(chooseArea,BoxLayout.Y_AXIS));
        chooseArea.setBackground(Color.WHITE);
        chooseArea.add(GenerateColorLine(0,canvas));
        chooseArea.add(GenerateColorLine(1,canvas));
        chooseArea.add(GenerateColorLine(2,canvas));
        chooseArea.add(GenerateColorLine(3,canvas));

        String[] blockSize = {"2x2", "4x4", "8x8"};
        JComboBox<String> comboBox = new JComboBox<>(blockSize);
        comboBox.setSelectedItem(size+"x"+size);
        comboBox.setSize(new Dimension(60,20));
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (e.getItem().toString()) {
                    case "2x2" -> size = 2;
                    case "4x4" -> size = 4;
                    case "8x8" -> size = 8;
                }
            }
        });
        Container comboBoxContainer = new Container();
        comboBoxContainer.setLayout(new FlowLayout());
        comboBoxContainer.add(new JLabel("Block size:"));
        comboBoxContainer.add(comboBox);
        chooseArea.add(comboBoxContainer);

        dialog.add(chooseArea,BorderLayout.CENTER);

        JPanel buttonArea = new JPanel();
        buttonArea.setBackground(Color.WHITE);
        buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER,dialog.getWidth()/5,10));

        CustomButton okButton = new CustomButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.setMosaicPattern(new MosaicPattern(colors,size));
                canvas.setDisplayedObject(toolName);
                dialog.dispatchEvent(new WindowEvent(dialog,WindowEvent.WINDOW_CLOSING));
            }
        });
        CustomButton cancelButton = new CustomButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispatchEvent(new WindowEvent(dialog,WindowEvent.WINDOW_CLOSING));
            }
        });
        buttonArea.add(okButton);
        buttonArea.add(cancelButton);
        dialog.add(buttonArea, BorderLayout.SOUTH);
        return dialog;
    }

    private Container GenerateColorLine(int index, MainCanvas canvas){
        Container container = new Container();
        container.setLayout(new FlowLayout(FlowLayout.CENTER,20,5));
        container.add(new JLabel((index + 1) +" color"));
        JPanel colorPanel = new JPanel();
        colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        colorPanel.setSize(50,50);
        colorPanel.setBackground(colors[index]);
        container.add(colorPanel);
        CustomButton editButton = new CustomButton("Edit");
        editButton.addActionListener(new BlockColorListener(canvas, this.colors, index, colorPanel));
        container.add(editButton);
        return container;
    }


}
