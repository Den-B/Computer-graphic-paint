import Forms.ApplicationForm;

import javax.swing.*;
import java.io.IOException;
import java.net.MalformedURLException;

public class Runner {

    public static void main(String[] args) {
        try {
            new ApplicationForm();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
