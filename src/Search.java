import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.*;

class Search extends JDialog {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField t1 = new JTextField(10);
    private JTextPane jTextPane = new JTextPane();
//    int cnt = 0;
    private int offset = 0;

    //int m;
    void set(JTextPane n) {
        jTextPane = n;
    }

    private void setOffset(int offset) {
        this.offset = offset;
    }

    private void toInitiate(){
        try {
            int length=jTextPane.getDocument().getLength();
            String str=jTextPane.getDocument().getText(0,length);
            jTextPane.getDocument().remove(0,length);
            jTextPane.getDocument().insertString(0,str,null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    Search() {
        setTitle("查找");
        setSize(300, 200);
        setLayout(new FlowLayout());
        JButton b1 = new JButton("查找下一个");
        Font f1 = new Font("隶书", Font.PLAIN, 15);
        b1.setFont(f1);
        JButton b2 = new JButton("取消");
        b2.setFont(f1);
        JLabel l1 = new JLabel("查找内容");
        l1.setFont(f1);
        add(l1);
        add(t1);
        add(b1);
        add(b2);
        b2.addActionListener(new MyActionListener2());
        //hsq
        b1.addActionListener(e -> {
            int length=jTextPane.getDocument().getLength()-1;  //get the document's length.
//                String str = jTextPane.getText();
            String str= null;
            try {
                str = jTextPane.getDocument().getText(0,length);     //get the document's context,
                                                                            //str
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }

            SimpleAttributeSet attr=new SimpleAttributeSet();

//                StyleConstants.setBackground(attr,Color.white);
//                jTextPane.setParagraphAttributes(attr,true);
            StyleConstants.setBackground(attr,Color.GRAY);

            String des = t1.getText();                  //get searching contains from ...
            //jTextPane.getDocument().
            //System.out.println(offset);
            if((offset = str.indexOf(des, offset)) != -1){     //
                try {
                    jTextPane.getDocument().remove(0,length);
                    jTextPane.getDocument().insertString(0,str,null);
                    jTextPane.getDocument().remove(offset,des.length());
                    jTextPane.getDocument().insertString(offset,des,attr);
                } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
//                    jTextPane.setSelectionStart(offset);
//                    jTextPane.setSelectionEnd(offset+des.length()-1);
//                    jTextPane.setSelectionColor(Color.blue);
                jTextPane.setCaretPosition(offset);
                offset = offset + des.length();
//                    cnt++;
            }
        });
    }

    class MyActionListener2 implements ActionListener {
        public void actionPerformed(ActionEvent e1) {
            toInitiate();
            setOffset(0);
            setVisible(false);
        }
    }
}
