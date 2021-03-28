// Stefan Lukic
// 101 156 711

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.Math;

/**
 * A window that displays a palette of colours and allows the user to generate a new palette.
 * Individual colors can be locked to remain unchanged when generating a new palette
 */
public class PaletteGenerator {

    private final JFrame frame;
    private final JPanel[] colorPanels = new JPanel[8];
    private final boolean[] panelLocked = new boolean[8];

    private final String LOCKED = "X";
    private final String UNLOCKED = "O";
    private final int COLORHEIGHT = 500;
    private final int COLORWIDTH = 125;

    /**
     * Constructor
     * All color locks are defaulted to unlocked
     */
    public PaletteGenerator() {
        for (int i = 0; i < 8; i++) {
            panelLocked[i] = false;
        }

        this.frame = new JFrame();
        frame.setTitle("Palette Generator");
        frame.add(createPanels());
        frame.pack();
    }

    /**
     * Display the PaletteGenerator GUI
     */
    public void displayGUI() {
        frame.setSize(8*COLORWIDTH+100, (int)(1.5*COLORHEIGHT));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });

        // the frame is not visible until we set it to be so
        frame.setVisible(true);
    }

    /**
     * Creates a JPanel containing the following 3 JPanels within it:
     * <p>
     * Header: Contains only a label with a title.
     * Body: Contains 8 JPanels with associated lock JButtons.
     * Footer: A single JButton that we can click to generate a new palette.
     *
     * @return JPanel : JPanel containing the 3 JPanels within it
     */
    private JPanel createPanels() {
        // JLabels
        JLabel headerLabel1 = new JLabel("Your Palette", SwingConstants.CENTER);
        JLabel headerLabel2 = new JLabel(UNLOCKED+": Unlocked, "+LOCKED+": Locked", SwingConstants.CENTER);

        // JPanels
        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel bodyPanel = new JPanel();
        JPanel footerPanel = new JPanel();

        // adding JLabels to the respective JPanel
        headerPanel.add(headerLabel1, BorderLayout.PAGE_START);
        headerPanel.add(headerLabel2, BorderLayout.CENTER);
        footerPanel.add(getGenerateButton());

        // add color Panels to bodyPanel
        bodyPanel = createColorPanels();

        // set the preferred sizes and colours here
        headerPanel.setPreferredSize(new Dimension(8*COLORWIDTH, COLORHEIGHT/6));
        headerPanel.setBackground(Color.white);

        bodyPanel.setPreferredSize(new Dimension(8*COLORWIDTH, COLORHEIGHT));
        bodyPanel.setBackground(Color.white);

        footerPanel.setPreferredSize(new Dimension(8*COLORWIDTH, COLORHEIGHT/6));
        footerPanel.setBackground(Color.white);

        // add JLabels to the panel
        mainPanel.add(headerPanel, BorderLayout.PAGE_START);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.PAGE_END);

        return mainPanel;
    }

    /**
     * Creates a JPanel containing 8 JPanels with associated lock JButtons.
     *
     * @return JPanel: JPanel containing 8 JPanels with associated lock JButtons
     */
    private JPanel createColorPanels() {
        JPanel ColorPanel = new JPanel(new GridLayout());
        for (int i = 0; i < 8; i++) {
            JPanel jp = new JPanel(new BorderLayout());
            JPanel colorPanel = new JPanel();

            colorPanel.setPreferredSize(new Dimension(COLORWIDTH, COLORHEIGHT-40));
            colorPanel.setBackground(getColour());

            jp.add(colorPanel, BorderLayout.CENTER);
            jp.add(getLockButton(i), BorderLayout.PAGE_END);
            colorPanels[i] = jp;

            ColorPanel.add(jp);
        }

        return ColorPanel;
    }

    /**
     * Get colours of a certain brightness. Wow!
     *
     * @return Color : A Color object with the generated colour.
     */
    private Color getColour() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        double luma = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);

        while (luma < 75) {
            r = (int) (Math.random() * 256);
            g = (int) (Math.random() * 256);
            b = (int) (Math.random() * 256);
            luma = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
        }
        return new Color(r, g, b);
    }

    /**
     * Create a new Lock Button that toggles the lock in the corresponding JPanel
     *
     * @param i int : Corresponding JPanel from leftmost = 0 to rightmost = 7
     * @return JButton: Lock Button that toggles the lock in the corresponding JPanel
     */
    private JButton getLockButton(int i) {
        JButton button = new JButton(UNLOCKED);

        // add the action listener
        button.addActionListener(new ActionListener() {

            // this method will be called when we click the button
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                panelLocked[i] = !panelLocked[i];
                JButton b = (JButton) colorPanels[i].getComponents()[1];
                if (panelLocked[i]) {
                    b.setText(LOCKED);
                } else {
                    b.setText(UNLOCKED);
                }
            }
        });
        button.setPreferredSize(new Dimension(COLORWIDTH,40));
        return button;
    }

    /**
     * Create a button that sets each unlocked color panel to a new color
     *
     * @return JButton : button that sets each unlocked color panel to a new color
     */
    private JButton getGenerateButton() {
        JButton button = new JButton("Generate Palette");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                for (int i = 0; i < 8; i++) {
                    if (!panelLocked[i]) {
                        //update JPanel Colour
                        colorPanels[i].getComponents()[0].setBackground(getColour());
                    }
                }
            }
        });
        return button;
    }

}
