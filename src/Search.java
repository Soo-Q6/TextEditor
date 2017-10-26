import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Search extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel l1 = new JLabel("查找内容");
    JTextField t1 = new JTextField(10);
    JButton b1 = new JButton("查找下一个");
    JButton b2 = new JButton("取消");
    Font f1 = new Font("隶书", Font.PLAIN, 15);
    JTextPane jTextPane = new JTextPane();
    int cnt = 0;
    int offset = 0;

    //int m;
    void set(JTextPane n) {
        jTextPane = n;
    }

    public Search() {
        setTitle("查找");
        setSize(300, 200);
        setLayout(new FlowLayout());
        b1.setFont(f1);
        b2.setFont(f1);
        l1.setFont(f1);
        add(l1);
        add(t1);
        add(b1);
        add(b2);
        b2.addActionListener(new MyActionListener2());
        b1.addActionListener(new ActionListener() {
            //hsq
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = jTextPane.getText();
                String des = t1.getText();
                System.out.println(offset);
                if((offset = str.indexOf(des, offset)) != -1){
//                    jTextPane.setSelectionStart(offset);
//                    jTextPane.setSelectionEnd(offset+des.length()-1);
//                    jTextPane.setSelectionColor(Color.blue);
                    offset = offset + des.length();
                    cnt++;
                }
            }
        });
    }

    class MyActionListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e1) {
            setVisible(false);
        }
    }
}
