package Forms.CustomComponents.Compression;

import Helpers.CompressionAlgorithms.CompressionException.CompressionException;
import Helpers.CompressionAlgorithms.LZ77;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompressionActionListener implements ActionListener {

    private boolean isModal;
    private JFrame owner;

    public CompressionActionListener(JFrame owner, boolean isModal){
        this.isModal = isModal;
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InputOutputDialog dialog = new InputOutputDialog("Compression of a file", owner, isModal);
        dialog.addOkButtonActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LZ77.compress(dialog.getInputFilePath(),dialog.getOutputFilePath());
                    JOptionPane.showMessageDialog(owner,"File succussefully was compressed.","Compression success",JOptionPane.INFORMATION_MESSAGE);
                } catch (CompressionException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(owner,"An error occurred during compression.","Compression error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dialog.setVisible(true);
    }
}
