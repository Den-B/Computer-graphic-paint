package Forms.CustomComponents;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {

    public CustomButton(String name){
        super(name);
        this.setFocusPainted(false);
        this.setBackground(Color.WHITE);
    }

}
