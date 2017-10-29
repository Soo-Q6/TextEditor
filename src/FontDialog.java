import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;

class FontDialog extends JDialog implements ItemListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JComboBox<String> fontFamilyComboBox = new JComboBox<>();
    private JComboBox<String> fontComboBox = new JComboBox<>();
    private JComboBox<String> fontSizeComboBox = new JComboBox<>();
    //    int style = 1;
    private SimpleAttributeSet attr=new SimpleAttributeSet();
    private JTextPane jTextPane = new JTextPane();

    void set(JTextPane n) {
        jTextPane = n;
    }

    FontDialog() {
        setTitle("字体");
        setSize(500, 600);
        setLayout(new FlowLayout());
        //panel1.setLocation(100, 200);
        JLabel fontFamilyLabel = new JLabel("字体：");
        Font f1 = new Font("隶书", Font.PLAIN, 15);
        fontFamilyLabel.setFont(f1);
        JLabel fontLabel = new JLabel("字形：");
        fontLabel.setFont(f1);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontName = ge.getAvailableFontFamilyNames();
        fontFamilyComboBox.setModel(new DefaultComboBoxModel<>(fontName));

        fontFamilyComboBox.setFont(f1);
        for (int i = 1; i < fontName.length; i++) {
            //fontFamilyComboBox.setSelectedIndex(i);
            fontFamilyComboBox.setSelectedItem(fontName);
            //fontFamilyComboBox.addItem(fontName);
        }
        String[] font = new String[]{"常规", "倾斜", "加粗"};
        fontComboBox.setModel(new DefaultComboBoxModel<>(font));
        fontComboBox.setFont(f1);
        for (int i = 1; i < font.length; i++) {
            //fontComboBox.setSelectedIndex(i);
            fontComboBox.setSelectedItem(font);
            //fontComboBox.addItem(array2);
        }
        String[] fontSize = new String[]{"14", "15", "15", "16", "17", "18"};
        fontSizeComboBox.setModel(new DefaultComboBoxModel<>(fontSize));
        fontSizeComboBox.setFont(f1);
        for (int i = 1; i < fontSize.length; i++) {
            //fontComboBox.setSelectedIndex(i);
            fontComboBox.setSelectedItem(fontSize);
            //fontSizeComboBox.addItem(fontSize);
        }

        JPanel panel1 = new JPanel();
        panel1.add(fontFamilyLabel);
        panel1.add(fontFamilyComboBox);
        fontFamilyComboBox.addItemListener(this);

        JPanel panel2 = new JPanel();
        panel2.add(fontLabel);
        panel2.add(fontComboBox);
        fontComboBox.addItemListener(this);

        JPanel panel3 = new JPanel();
        JLabel fontSizeLabel = new JLabel("字号：");
        panel3.add(fontSizeLabel);
        panel3.add(fontSizeComboBox);
        fontSizeComboBox.addItemListener(this);

        JButton OKButton = new JButton("确定");
        panel3.add(OKButton);

        JButton colorButton = new JButton("颜色");
        panel3.add(colorButton);
        colorButton.addActionListener(new MyActionListener3());

        OKButton.addActionListener(new MyActionListener3());
        add(panel1);
        add(panel2);
        add(panel3);
    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == fontFamilyComboBox) {
            String fam = fontFamilyComboBox.getSelectedItem().toString();
            StyleConstants.setFontFamily(attr,fam);
        }
        if (e.getSource() == fontComboBox) {
            String s1 = fontComboBox.getSelectedItem().toString();
            if (s1.equals("加粗")) {
//                style = Font.BOLD;
                StyleConstants.setBold(attr,true);
            }
            if (s1.equals("倾斜")) {
//                style = Font.ITALIC;
                StyleConstants.setItalic(attr,true);
            }
            if (s1.equals("常规")) {
//                style = Font.PLAIN;
                StyleConstants.setBold(attr,false);
                StyleConstants.setItalic(attr,false);
            }
        }
        if (e.getSource() == fontSizeComboBox) {
            String s2 = fontSizeComboBox.getSelectedItem().toString();
            int size = Integer.parseInt(s2);
            StyleConstants.setFontSize(attr,size);
        }

    }

    //hsq
    class MyActionListener3 implements ActionListener {
        public void actionPerformed(ActionEvent e2) {
//            Font font = new Font(name, style, size);
//            jTextPane.setFont(font);
            if (e2.getActionCommand().equals("颜色")) {
                setColor();
            }
            jTextPane.setParagraphAttributes(attr,false);
        }
    }

    private void setColor() {
        Color fontColor = JColorChooser.showDialog(this, "字体颜色选择", jTextPane.getForeground());
        //jTextPane.setForeground(fontcolor);
        //SimpleAttributeSet attr=new SimpleAttributeSet();
        StyleConstants.setForeground(attr,fontColor);
        //jTextPane.setParagraphAttributes(attr,false);
    }
}