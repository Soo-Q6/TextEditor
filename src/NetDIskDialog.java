import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

//LZN
public class NetDIskDialog extends JDialog {
    private Font font1 = new Font("隶书", Font.PLAIN, 15);
    private JButton button1 ;
    private JTextField textField1 = new JTextField(23);
    private JTextArea textArea1=new JTextArea(15,40);
    NetDIskDialog(boolean isRead,ActionListener actionListener){
        setTitle("网络读取");
        setSize(600, 400);
        setLayout(new FlowLayout());
        if (isRead)
            button1=new JButton("输入文件名，从服务器加载");
        else
            button1=new JButton("输入文件名，保存到服务器");
        textField1.setFont(font1);
        button1.setFont(font1);
        textArea1.setFont(font1);

        add(textField1);
        add(button1);
        add(textArea1);

        button1.addActionListener(actionListener);
    }
    public String getTextFieldText(){
        return textField1.getText();
    }
    public void sertTextAreaText(String s){
        textArea1.setText(s);
    }
}
