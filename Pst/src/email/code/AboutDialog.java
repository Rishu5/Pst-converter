
package email.code;
import java.awt.Cursor;
import java.awt.Desktop;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import email.code.Main_Frame;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	String aboutTitle;
	private final JPanel contentPanel = new JPanel();
	String labeltext="";
	Main_Frame mf;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the dialog.
	 * @param modal 
	 * @param parent 
	 */
	public AboutDialog(JFrame parent, boolean modal,String labeltext) {
		super(parent,true);
		this.mf=(Main_Frame) parent;
		this.labeltext=labeltext;
		aboutTitle=All_Data.messageboxtitle;
		
		setResizable(false);
		setTitle(aboutTitle);
		setBounds(100, 100, 397, 376);
		getContentPane().setLayout(null);
		contentPanel.setBackground(new Color(255, 255, 255));
		contentPanel.setBounds(0, 0, 390, 347);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setIcon(new ImageIcon(AboutDialog.class.getResource("/about__1_.png")));
		lblNewLabel.setBounds(0, 0, 390, 85);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(aboutTitle);
		lblNewLabel_1.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		lblNewLabel_1.setBounds(11, 94, 328, 23);
		contentPanel.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(15, 153, 165, 112);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Edition:            Standard ");
		lblNewLabel_2.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		lblNewLabel_2.setIcon(new ImageIcon(AboutDialog.class.getResource("/arrow.png")));
		lblNewLabel_2.setBounds(10, 11, 152, 24);
		panel.add(lblNewLabel_2);
		
		JLabel lblVersionStandard = new JLabel("Version:           "+All_Data.version);
		lblVersionStandard.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		lblVersionStandard.setIcon(new ImageIcon(AboutDialog.class.getResource("/arrow.png")));
		lblVersionStandard.setBounds(10, 42, 152, 24);
		panel.add(lblVersionStandard);
		
		JLabel lblLicencseStandard = new JLabel("Licensed to:     "+this.labeltext);
		lblLicencseStandard.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		lblLicencseStandard.setIcon(new ImageIcon(AboutDialog.class.getResource("/arrow.png")));
		lblLicencseStandard.setBounds(10, 73, 152, 21);
		panel.add(lblLicencseStandard);
		{
			JButton okButton = new JButton("");
			okButton.setContentAreaFilled(false);
			okButton.setBorderPainted(false);
			okButton.setBackground(Color.WHITE);
			okButton.setFocusPainted(false);
			okButton.setIcon(new ImageIcon(AboutDialog.class.getResource("/ok_about.png")));
			okButton.setBounds(154, 278, 75, 28);
			contentPanel.add(okButton);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					AboutDialog.this.dispose();
				}
			});
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(AboutDialog.class.getResource("/bottom_about.png")));
		lblNewLabel_3.setBounds(1, 321, 389, 25);
		contentPanel.add(lblNewLabel_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(204, 154, 186, 112);
		contentPanel.add(panel_1);
		
		JLabel saleslink = new JLabel("support@data-recovery-solutions.com");
		saleslink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().mail(new URI("mailto:support@data-recovery-solutions.com"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
			}
		});
		saleslink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		saleslink.setFont(new Font("Segoe UI Semibold", Font.BOLD, 9));
		saleslink.setBounds(10, 73, 176, 23);
		panel_1.add(saleslink);
		saleslink.setIcon(new ImageIcon(AboutDialog.class.getResource("/arrow.png")));
		saleslink.setForeground(new Color(30, 144, 255));
		
		JLabel supportlink = new JLabel("");
		supportlink.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		supportlink.setBounds(10, 45, 98, 16);
		panel_1.add(supportlink);
		supportlink.setIcon(new ImageIcon(AboutDialog.class.getResource("/arrow.png")));
		supportlink.setForeground(new Color(30, 144, 255));
		supportlink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
                    Desktop.getDesktop().browse(new URI("http://messenger.providesupport.com/messenger/sysinfotools.html"));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
			}
		});
		supportlink.setText("Live Chat");
		supportlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		supportlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		JLabel websitelink = new JLabel("");
		websitelink.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		websitelink.setIcon(new ImageIcon(AboutDialog.class.getResource("/arrow.png")));
		websitelink.setBounds(10, 12, 98, 20);
		panel_1.add(websitelink);
		websitelink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
                    Desktop.getDesktop().browse(new URI(All_Data.websiteurl));
                } catch (URISyntaxException | IOException ex) {
                    //It looks like there's a problem
                }
			}
		});
		websitelink.setForeground(new Color(30, 144, 255));
		websitelink.setText("Home");
		websitelink.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		JLabel lblSupportInformation = new JLabel("Support Information");
		lblSupportInformation.setFont(new Font("Segoe UI Semibold", Font.BOLD, 12));
		lblSupportInformation.setBounds(204, 121, 176, 22);
		contentPanel.add(lblSupportInformation);
	}
}

