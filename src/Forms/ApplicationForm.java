package Forms;

import Forms.CustomComponents.Compression.CompressionActionListener;
import Forms.CustomComponents.Compression.DecompressionActionListener;
import Forms.CustomComponents.CustomButton;
import Forms.CustomComponents.SizeChangingWindow;
import Forms.MenuButtonEvent.*;
import Forms.MenuEvents.*;
import Forms.MenuEvents.Files.CreateNewBmpFileListener;
import Forms.MenuEvents.Files.OpenBmpFileListener;
import Helpers.Files.BmpFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class ApplicationForm  extends JFrame {

    private final MainCanvas canvas = new MainCanvas(500, 300);
    private final JMenuBar menuBar = new JMenuBar();
    private BmpFile file; //в процессе


    public ApplicationForm() throws IOException {
        super("Paint");
        //this.setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(ImageIO.read(new File("./images/paint.jpg")));
        this.setSize(950,500);
        this.setLocationRelativeTo(null);
        menuBar.setBackground(Color.WHITE);
        menuBar.setMargin(new Insets(5,0,2,0));
        menuBar.add(this.createFileMenu());
        menuBar.add(this.createViewMenu());
        menuBar.add(this.createPaintToolMenu());
        menuBar.add(this.createAdditionToolsMenu());
        menuBar.add(this.createOptionMenu());
        menuBar.add(this.createPenColorButton());
        menuBar.add(this.createFillColorButton());
        menuBar.add(this.createThicknessButton());
        menuBar.add(this.createDirectionButtons());
        menuBar.add(this.createClearButton());
        menuBar.add(this.createAboutButton());
        this.setJMenuBar(menuBar);
        this.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
        add(canvas);
        this.getContentPane().setBackground(new Color(200,200,200));
        this.getRootPane().setDoubleBuffered(true);
        revalidate();
        setVisible(true);
    }

    private JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Create file"));
        fileMenu.add(createMenuItem("Open bmp file", new OpenBmpFileListener(canvas)));
        fileMenu.add(new JMenuItem("Save"));
        fileMenu.add(createMenuItem("Save as bmp",new CreateNewBmpFileListener(canvas)));
        fileMenu.add(new JMenuItem("Close file"));
        return fileMenu;
    }

    private JMenuItem createMenuItem(String name, ActionListener listener){
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(listener);
        return item;
    }

    private JButton createButton(String name, ActionListener listener){
        CustomButton button = new CustomButton(name);
        button.addActionListener(listener);
        return button;
    }

    private JMenu createViewMenu(){
        JMenu viewMenu = new JMenu("View");

        return viewMenu;
    }

    private JMenu createAdditionToolsMenu(){
        JMenu menu = new JMenu("Additional tools");
        menu.add(createMenuItem("Convert color",new ColorConverterListener()));
        menu.add(createCompressionAlgorithmMenu());
        menu.add(createMenuItem("Basic raster algorithm", new BasicRasterAlgorithmListener(canvas, this)));
        return menu;
    }

    private JMenu createCompressionAlgorithmMenu(){
        JMenu menu = new JMenu("Compression algorithm LZ77");
        menu.add(createMenuItem("Compress a file", new CompressionActionListener(this, true)));
        menu.add(createMenuItem("Decompress a file", new DecompressionActionListener(this, true)));
        return menu;
    }


    private JMenu createPaintToolMenu(){
        JMenu paintToolMenu = new JMenu("Paint tool");
        paintToolMenu.add(createMenuItem("Brush", new ToolListener(canvas)));
        paintToolMenu.add(createMenuItem("Rectangle", new ToolListener(canvas)));
        paintToolMenu.add(createMenuItem("Line", new ToolListener(canvas)));
        paintToolMenu.add(new JSeparator());
        paintToolMenu.add(createMenuItem("Mosaic",new ToolListener(canvas)));
        paintToolMenu.add(createMenuItem("Random mosaic", new MosaicListener(canvas, this)));
        return paintToolMenu;
    }

    private JMenu createOptionMenu(){
        JFrame thisFrame = this;
        JMenu paintToolMenu = new JMenu("Options");
        paintToolMenu.add(createMenuItem("Change canvas size", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog frame = new SizeChangingWindow(thisFrame,canvas,true);
                frame.setVisible(true);
            }
        }));
        return paintToolMenu;
    }

    private JButton createFillColorButton(){
        JButton colorMenu = new CustomButton("Fill color");
        colorMenu.addActionListener(new FillColorListener(canvas));
        return colorMenu;
    }

    private Container createThicknessButton(){
        SpinnerModel model = new SpinnerNumberModel(4, 1, 30000, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setMaximumSize(new Dimension(100,20));
        spinner.addChangeListener(new ThicknessListener(canvas));
        Container container = new Container();
        container.setLayout(new FlowLayout());
        container.add(new JLabel("Thickness: "));
        container.add(spinner);
        container.setMaximumSize(new Dimension(170,30));
        return container;
    }

    private JButton createPenColorButton(){
        JButton colorMenu = new CustomButton("Pen color");
        colorMenu.addActionListener(new PenColorListener(canvas));
        return colorMenu;
    }

    private JButton createClearButton(){
        JButton clearButton = new CustomButton("Clear");
        clearButton.addActionListener(new ClearEvent(canvas));
        return clearButton;
    }

    private Container createDirectionButtons(){
        Container container = new Container();
        container.setLayout(new FlowLayout());
        container.add(createButton("Back", new GoBackListener(canvas)));
        container.add(createButton("Forward", new GoForwardListener(canvas)));
        container.setMaximumSize(new Dimension(160,40));
        return container;
    }

    private JButton createAboutButton(){
        JButton fileMenu = new CustomButton("About");

        return fileMenu;
    }

}