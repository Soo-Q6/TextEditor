import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.net.Socket;

public class Wordwin extends JFrame implements DocumentListener {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JMenuBar menubar = new JMenuBar();
    private JMenu file = new JMenu("文件");
    private JMenu edit = new JMenu("编辑");
    private JMenu geshi = new JMenu("格式");
//    private JTextArea wordPanel = new JTextArea();
    private JTextPane wordPanel = new JTextPane();
    private HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
    private HTMLDocument text_html = (HTMLDocument) htmlEditorKit.createDefaultDocument();

//    private JScrollPane imgScrollPane = new JScrollPane(wordPanel);
    private JScrollPane imgScrollPane2 = new JScrollPane(wordPanel);
    private  String[] str1 = {"新建", "打开", "保存", "另存为", "退出"};
    private String[] str2 = {"网络保存", "网络读取", "查找", "替换", "insert"};
    private String[] str3 = {"字体"};
    private Font f1 = new Font("隶书", Font.PLAIN, 15);
    private Search search1 = new Search();
    private Font1 z1 = new Font1();
    private  Change change1 = new Change();
    String source = "";
    private boolean isNoChanged = true;
    private String nowFilePath = "";
    private boolean isSaved = false;
    private NetDIskDialog netDIskDialog;
    private Socket s;

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
                    search1.setVisible(true);
                }
                if (e.getActionCommand().equals("替换")) {
                    change1.setVisible(true);
                }
                if (e.getActionCommand().equals("字体")) {
                    z1.setVisible(true);
                }
                if (e.getActionCommand().equals("insert")) {
                    insert();
                    String txt = wordPanel.getText();
                    wordPanel.setText(txt);
                }
                if (e.getActionCommand().equals("网络保存")) {
                    connect();

                    netDIskDialog = new NetDIskDialog(false, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            saveOnNet(s, netDIskDialog.getTextFieldText());
                            netDIskDialog.setVisible(false);
                        }
                    });

                    netDIskDialog.setVisible(true);
                    //建立连接，显示目录
                    showFiles(s);
                }
                if (e.getActionCommand().equals("网络读取")) {
                    connect();

                    netDIskDialog = new NetDIskDialog(true, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //读取文件
                            readFromNet(s, netDIskDialog.getTextFieldText());
                            netDIskDialog.setVisible(false);
                        }
                    });
                    netDIskDialog.setVisible(true);
                    //建立连接，显示目录
                    showFiles(s);
                }
            }
        }
    }

    Wordwin() {
        change1.set(wordPanel);
        search1.set(wordPanel);
        z1.set(wordPanel);
        setTitle("文本编辑器");
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();//获取屏幕分辨率
        setSize(screenSize.width / 2, screenSize.height / 2);//大小
        setLocation(screenSize.width / 4, screenSize.height / 4);//位置
        Container container = getContentPane();
        container.setLayout(new GridLayout(1, 1));
//        add(imgScrollPane);
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

//        wordPanel.getDocument().addDocumentListener(this);
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

            wordPanel.setText(temp);
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
            out.write(wordPanel.getText());
            isNoChanged = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
                source = wordPanel.getText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void newfile() {
        if (isNoChanged) {
            wordPanel.setText("");
        } else {
            int m = JOptionPane.showConfirmDialog(this, "是否保存该文件");
            if (m == 0) {
                save();
                wordPanel.setText("");
            }

            if (m == 1) {
                //System.exit(0);
                wordPanel.setText("");
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
//            System.out.println(html);
            htmlEditorKit.insertHTML(text_html, wordPanel.getCaretPosition(), html, 0, 0, HTML.Tag.IMG);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //LZN
    private void readFromNet(Socket socket, String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    System.out.println("获取流对象");
                    //选择文件
                    out.write("rece".getBytes());//发送命令
                    out.write(fileName.getBytes());//发送文件名

                    newfile();
                    //接受文件
                    String temp="";
                    byte[] buf = new byte[1024];
                    int len = 0;
                    while ((len = in.read(buf)) != -1)//接收文件
                    {
                        System.out.println(new String(buf, 0, len));
//                        SimpleAttributeSet attrset = new SimpleAttributeSet();
//                        StyleConstants.setFontSize(attrset,24);
//                        try {
//                            wordPanel.getDocument().insertString(wordPanel.getDocument().getLength(),new String(buf, 0, len),attrset);
                            temp+=new String(buf, 0, len);
//                        }catch (BadLocationException e){
//                            e.printStackTrace();
//                        }
                    }
                    try {
                        htmlEditorKit.insertHTML(text_html, wordPanel.getCaretPosition(), temp, 0, 0, HTML.Tag.IMG);
                    }catch (BadLocationException e){
                        e.printStackTrace();
                    }
                    out.write("下载成功".getBytes());//发送成功
                    System.out.println("下载成功");
                    //展示文件
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //LZN
    private void saveOnNet(Socket socket,String fileName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream();
                    System.out.println("获取流对象");
                    //选择文件
                    out.write("send".getBytes());//发送命令
                    out.write(fileName.getBytes());//发送文件名

                    Thread.sleep(50);
                    //发送文件
                    String text=wordPanel.getText();
                    out.write(text.getBytes());
                    socket.shutdownOutput();

                    byte[] bufIn = new byte[1024];
                    int num = in.read(bufIn);
                    System.out.println(new String(bufIn, 0, num));
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //LZN
    private void showFiles(Socket s){
        try {
            InputStream in = s.getInputStream();
            byte[] bufIn = new byte[1024];
            int num = in.read(bufIn);
            netDIskDialog.sertTextAreaText((new String(bufIn, 0, num)));//显示文件目录
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    //LZN
    private void connect(){
        try {
            s = new Socket("127.0.0.1", 10003);
        }catch (IOException e1){
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame wordwin = new Wordwin();
        wordwin.setVisible(true);
    }
}