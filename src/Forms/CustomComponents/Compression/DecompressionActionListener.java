package Forms.CustomComponents.Compression;

import Helpers.CompressionAlgorithms.CompressionException.CompressionException;
import Helpers.CompressionAlgorithms.LZ77;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class DecompressionActionListener implements ActionListener{

    private boolean isModal;
    private JFrame owner;

    public DecompressionActionListener(JFrame owner, boolean isModal){
        this.isModal = isModal;
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        InputOutputDialog dialog = new InputOutputDialog("Decompression of a file", owner, isModal);
        dialog.addOkButtonActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LZ77.decompress(dialog.getInputFilePath(),dialog.getOutputFilePath());
                    JOptionPane.showMessageDialog(owner,"File succussefully was decompressed.","Decompression success",JOptionPane.INFORMATION_MESSAGE);
                } catch (CompressionException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(owner,"An error occurred during decompression.","Decompression error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dialog.setVisible(true);
    }
}
