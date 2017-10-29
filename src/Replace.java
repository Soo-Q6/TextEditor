import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Replace extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField t1 = new JTextField(10);
    private JTextField t2 = new JTextField(10);
    private JTextPane a1 = new JTextPane();

    //int m;
    void set(JTextPane n) {
        a1 = n;
    }

    Replace() {
        setTitle("替换");
        setSize(500, 300);
        setLocation(200, 300);
        setLayout(new FlowLayout());
        JLabel l1 = new JLabel("查找内容");
        Font f1 = new Font("隶书", Font.PLAIN, 15);
        l1.setFont(f1);
        JLabel l2 = new JLabel("替换为");
        l2.setFont(f1);
        t1.setFont(f1);
        t2.setFont(f1);
        JButton b2 = new JButton("全部替换");
        b2.setFont(f1);
        JButton b3 = new JButton("取消");
        b3.setFont(f1);
        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(b2);
        add(b3);
        b2.addActionListener(new MyActionListener3());
        b3.addActionListener(new MyActionListener3());
    }

    class MyActionListener3 implements ActionListener {

        public void actionPerformed(ActionEvent e2) {
            int m;
            String source = a1.getText();
            String find = t1.getText();
            String change = t2.getText();
            if (e2.getActionCommand().equals("取消")) {
                setVisible(false);
                t1.setText("");
                t2.setText("");
            }
            if (e2.getActionCommand().equals("替换")) {
                m = source.indexOf(find, 0);
                String s1 = source.substring(0, m);
                String s2 = source.substring(m + find.length());
                source = s1 + change + s2;
                a1.setText(source);
            }
            if (e2.getActionCommand().equals("全部替换")) {
                m = -change.length();
                while (true) {
                    m = source.indexOf(find, m + change.length());
                    if (m == -1)
                        break;
                    else {
                        String s1 = source.substring(0, m);
                        String s2 = source.substring(m + find.length());
                        source = s1 + change + s2;
                    }
                }
                a1.setText(source);
            }

        }
    }

}