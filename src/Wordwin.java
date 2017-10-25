import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.*;
import java.awt.event.*;

public class Wordwin extends JFrame implements DocumentListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("文件");
    JMenu edit = new JMenu("编辑");
    JMenu geshi = new JMenu("格式");
    JTextArea wordArea = new JTextArea();
    JTextPane wordPanel = new JTextPane();
    HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
    HTMLDocument text_html = (HTMLDocument) htmlEditorKit.createDefaultDocument();

    JScrollPane imgScrollPane = new JScrollPane(wordArea);
    JScrollPane imgScrollPane2 = new JScrollPane(wordPanel);
    String[] str1 = {"新建", "打开", "保存", "另存为", "退出"};
    String[] str2 = {"网络保存", "网络读取", "查找", "替换", "insert"};
    String[] str3 = {"字体"};
    Font f1 = new Font("隶书", Font.PLAIN, 15);
    Search d1 = new Search();
    Font1 z1 = new Font1();
    Change c1 = new Change();
    String source = "";
    private boolean isNoChanged = true;
    private String nowFilePath = "";
    private boolean isSaved = false;
    class MyActionListener1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JMenuItem) {
                if (e.getActionCommand().equals("新建")) {
                    newfile();
                }
                if (e.getActionCommand().equals("打开")) {
                    open();
                }
                if (e.getActionCommand().equals("保存")) {
                    save();
                }
                if (e.getActionCommand().equals("另存为")) {
                    saveAs();
                }
                if (e.getActionCommand().equals("退出")) {
                    exit();
                }
                if (e.getActionCommand().equals("查找")) {
                    d1.setVisible(true);
                }
                if (e.getActionCommand().equals("替换")) {
                    c1.setVisible(true);
                }
                if (e.getActionCommand().equals("字体")) {
                    z1.setVisible(true);
                }
                if (e.getActionCommand().equals("insert")) {
                    insert();
                    String txt = wordPanel.getText();
                    wordArea.setText(txt);
                }
                if (e.getActionCommand().equals("网络保存")) {
                    wordArea.cut();
                }
                if (e.getActionCommand().equals("网络读取")) {
                    wordArea.copy();
                }
            }
        }
    }

    Wordwin() {
        c1.set(wordArea);
        z1.set(wordArea);
        setTitle("文本编辑器");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();//获取屏幕分辨率
        setSize(screenSize.width / 2, screenSize.height / 2);//大小
        setLocation(screenSize.width / 4, screenSize.height / 4);//位置
        Container container = getContentPane();
        container.setLayout(new GridLayout(2, 1));
        add(imgScrollPane);
        add(imgScrollPane2);
        setJMenuBar(menubar);
        file.setFont(f1);
        edit.setFont(f1);
        geshi.setFont(f1);
        menubar.add(file);
        menubar.add(edit);
        menubar.add(geshi);
        wordPanel.setEditorKit(htmlEditorKit);
        wordPanel.setContentType("text/html");
        wordPanel.setDocument(text_html);

        wordArea.getDocument().addDocumentListener(this);
        wordPanel.getDocument().addDocumentListener(this);
        for (int i = 0; i < str1.length; i++) {
            JMenuItem item1 = new JMenuItem(str1[i]);
            item1.addActionListener(new MyActionListener1());
            item1.setFont(f1);
            file.add(item1);
        }
        for (int i = 0; i < str2.length; i++) {
            JMenuItem item2 = new JMenuItem(str2[i]);
            item2.addActionListener(new MyActionListener1());
            item2.setFont(f1);
            edit.add(item2);
        }
        for (int i = 0; i < str3.length; i++) {
            JMenuItem item3 = new JMenuItem(str3[i]);
            item3.addActionListener(new MyActionListener1());
            item3.setFont(f1);
            geshi.add(item3);
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                exit();
            }
        });
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        isNoChanged = false;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        isNoChanged = false;
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        isNoChanged = false;
    }

    private void open() {
        FileDialog filedialog = new FileDialog(this, "打开", FileDialog.LOAD);
        filedialog.setVisible(true);
        String path = filedialog.getDirectory();
        String name = filedialog.getFile();
        if (path != null && name != null) {
            FileInputStream file = null;
            try {
                file = new FileInputStream(path + name);
            } catch (FileNotFoundException e) {

            }
            InputStreamReader put = new InputStreamReader(file);
            BufferedReader in = new BufferedReader(put);
            String temp = "";
            String now = null;
            try {
                now = in.readLine();
            } catch (IOException e) {

                e.printStackTrace();
            }
            while (now != null) {
                temp += now + "\r\n";
                try {
                    now = in.readLine();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

            wordArea.setText(temp);
        }

    }

    //LZN
    private void save() {
        FileDialog fileDialog = new FileDialog(this, "保存", FileDialog.SAVE);
        fileDialog.setVisible(false);
        if (!isSaved) {
            fileDialog.setVisible(true);
            nowFilePath = fileDialog.getDirectory() + fileDialog.getFile();
            isSaved = true;
        }
        saveInner(nowFilePath);
    }

    //LZN
    private void saveAs() {
        FileDialog fileDialog = new FileDialog(this, "另存为", FileDialog.SAVE);
        fileDialog.setVisible(true);
        saveInner(fileDialog.getDirectory() + fileDialog.getFile());
    }

    //LZN
    private void saveInner(String filePath) {
        File file = new File(filePath);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            out.write(wordArea.getText());
            isNoChanged = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                source = wordArea.getText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void newfile() {
        if (isNoChanged) {
            wordArea.setText("");
        } else {
            int m = JOptionPane.showConfirmDialog(this, "是否保存该文件");
            if (m == 0) {
                save();
                wordArea.setText("");
            }

            if (m == 1) {
                //System.exit(0);
                wordArea.setText("");
                isNoChanged = false;
            }
        }
    }

    private void exit() {
        if (isNoChanged) {
            System.exit(0);
        } else {
            int m = JOptionPane.showConfirmDialog(this, "是否保存该文件");
            if (m == 0) {
                save();
            }
            if (m == 1) {
                System.exit(0);
            }
        }
    }

    private void insert() {
        try {
            FileDialog filedialog = new FileDialog(this, "打开", FileDialog.LOAD);
            filedialog.setVisible(true);
            String path = filedialog.getDirectory();
            String url = filedialog.getFile();
            String html = "<img src=\"file:///" + path + url + "\">";
            System.out.println(html);
            htmlEditorKit.insertHTML(text_html, wordPanel.getCaretPosition(), html, 0, 0, HTML.Tag.IMG);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        JFrame wordwin = new Wordwin();
        wordwin.setVisible(true);
    }
}