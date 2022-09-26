package email.activation;



import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultEditorKit;

import email.code.All_Data;
import email.code.Main_Frame;
import email.code.SelectAll;
import net.Hash;
import java.awt.Color;
import java.awt.Desktop;

public class ActivationFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailTF;
	private JTextField orderTF;
	private JTextField pathTF;
	JLabel emailLabel;
	JLabel orderLabel;
	JLabel pathLabel;
	JButton btnBrowse;
	JCheckBox chckbxNewCheckBox;
	JButton btnActivate;

	static String messageboxtitle = All_Data.messageboxtitle;

	static String strSerialNumber;
	static String hashKey;
	static String defaultSerialNumber = "123456";

	String orderTFvalue = "ARYMYSQLDATABASEMIGORD";
	String infofilename = All_Data.messageboxtitle.replace(" ", "") + "Win.info";

	String productversion = All_Data.messageboxtitle + All_Data.version;
	String Salesemailid = "sales@" + All_Data.domain;
	String helpuri = "http://messenger.providesupport.com/messenger/0pi295uz3ga080c7lxqxxuaoxr.html";

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				String fileKey = null;

				File licFile;
				if (System.getProperty("os.name").toLowerCase().contains("windows")) {
					licFile = new File(System.getenv("APPDATA") + File.separator + messageboxtitle + File.separator
							+ "license.lic");
				} else {
					licFile = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator
							+ "Application Support" + File.separator + messageboxtitle + File.separator
							+ "license.lic");
				}

				if (licFile.exists()) {
					try {
						FileReader fr = new FileReader(licFile);
						BufferedReader br = new BufferedReader(fr);
						fileKey = br.readLine();
						fr.close();
					} catch (Exception ex) {

					}

					if (fileKey != null) {
						strSerialNumber = ActivationFrame
								.getSerialNumber(System.getProperty("user.home").substring(0, 1));
						hashKey = new Hash().getHash(strSerialNumber);
						if (hashKey.equals(fileKey)) {
							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| UnsupportedLookAndFeelException e) {
								e.printStackTrace();
							}
							Main_Frame mf = new Main_Frame(false, 1);
							mf.setLocationRelativeTo(null);
							mf.setVisible(true);
						} else {
							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| UnsupportedLookAndFeelException e) {
								e.printStackTrace();
							}
							ActivationFrame af = new ActivationFrame();
							af.setLocationRelativeTo(null);
							af.setVisible(true);
						}
					} else {
						try {
							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| UnsupportedLookAndFeelException e) {
							e.printStackTrace();
						}
						ActivationFrame af = new ActivationFrame();
						af.setLocationRelativeTo(null);
						af.setVisible(true);
					}
				} else {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						e.printStackTrace();
					}
					ActivationFrame af = new ActivationFrame();
					af.setLocationRelativeTo(null);
					af.setVisible(true);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ActivationFrame() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ActivationFrame.class.getResource("/128x128.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				String warn = "Do you want to close offline activation ?";
				int ans = JOptionPane.showConfirmDialog(ActivationFrame.this, warn, messageboxtitle,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						new ImageIcon(ActivationFrame.class.getResource("/about-icon-2.png")));
				if (ans == JOptionPane.YES_OPTION) {
					if (Starting_Frame.mf != null) {
						Starting_Frame.mf.setEnabled(true);
					}
					File licFileon = null;
					if (System.getProperty("os.name").toLowerCase().contains("windows")) {
						licFileon = new File(System.getenv("APPDATA") + File.separator + Main_Frame.projectTitle
								+ File.separator + "licenseOnline.lic");
					} else {
						licFileon = new File(System.getProperty("user.home") + File.separator + "Library"
								+ File.separator + "Application Support" + File.separator + Main_Frame.projectTitle
								+ File.separator + "licenseOnline.lic");
					}
					boolean activatefromdemo = true;
					dispose();
					OnlineActivation mf = new OnlineActivation(Starting_Frame.mf, licFileon, activatefromdemo);

					mf.setLocationRelativeTo(null);
					mf.setVisible(true);

					System.out.println("CLOSE ON EXIT");
					mf.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent arg0) {
							String warn = "Do you want to close?";
							int ans = JOptionPane.showConfirmDialog(mf, warn, All_Data.messageboxtitle,
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
									new ImageIcon(Main_Frame.class.getResource("/about-icon-2.png")));
							if (ans == JOptionPane.YES_OPTION) {

								setEnabled(true);
								if (Starting_Frame.mf != null) {
									Starting_Frame.mf.setEnabled(true);
								}
								if (Starting_Frame.activate_btn_click == true) {
									System.exit(0);
								} else {
									Starting_Frame.activate_btn_click = false;
									mf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
								}

							} else {
								mf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
							}
						}
					});

//					setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//					System.exit(0);				
//					dispose();
				} else {
					setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				}
			}
		});
		setTitle(messageboxtitle);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 491, 251);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		emailLabel = new JLabel("Email ID :");
		emailLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		emailLabel.setBounds(6, 27, 162, 17);
		contentPane.add(emailLabel);

		orderLabel = new JLabel("Order ID :");
		orderLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		orderLabel.setBounds(6, 64, 162, 17);
		contentPane.add(orderLabel);

		pathLabel = new JLabel("Path to save :");
		pathLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
		pathLabel.setBounds(6, 102, 162, 17);
		contentPane.add(pathLabel);

		emailTF = new JTextField();
		JPopupMenu menu = new JPopupMenu();
		menu.setBackground(Color.WHITE);
		Action cut = new DefaultEditorKit.CutAction();
		cut.putValue(Action.NAME, "Cut");
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		menu.add(cut);
		Action copy = new DefaultEditorKit.CopyAction();
		copy.putValue(Action.NAME, "Copy");
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		menu.add(copy);
		Action paste = new DefaultEditorKit.PasteAction();
		paste.putValue(Action.NAME, "Paste");
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		menu.add(paste);
		Action selectAll = new SelectAll();
		menu.add(selectAll);
		emailTF.setComponentPopupMenu(menu);
		emailTF.setBounds(178, 24, 219, 20);
		contentPane.add(emailTF);
		emailTF.setColumns(10);

		orderTF = new JTextField();
		orderTF.setColumns(10);
		orderTF.setEditable(false);
		orderTF.setBounds(178, 61, 219, 20);
		contentPane.add(orderTF);

		pathTF = new JTextField();
		pathTF.setEditable(false);
		pathTF.setColumns(10);
		pathTF.setBounds(178, 99, 219, 20);
		contentPane.add(pathTF);

		btnBrowse = new JButton("...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser(
						System.getProperty("user.home") + File.separator + "Desktop");
				fileChooser.setDialogTitle("Specify a file to save");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int userSelection = fileChooser.showSaveDialog(ActivationFrame.this);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					File check = new File(fileChooser.getSelectedFile() + "");
					if (check.exists())
						pathTF.setText(fileChooser.getSelectedFile() + "");
					else
						JOptionPane.showMessageDialog(ActivationFrame.this, "Please enter valid Path", messageboxtitle,
								JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(ActivationFrame.class.getResource("/information.png")));
				}
			}
		});
		btnBrowse.setBounds(413, 97, 53, 23);
		contentPane.add(btnBrowse);

		chckbxNewCheckBox = new JCheckBox("I don't have Authentication Code");
		chckbxNewCheckBox.setBackground(Color.WHITE);
		chckbxNewCheckBox.setFocusPainted(false);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckbxNewCheckBox.isSelected() == true) {
					emailLabel.setText("Email ID  :");
					orderLabel.setVisible(true);
					pathLabel.setVisible(true);
					orderTF.setVisible(true);
					pathTF.setVisible(true);
					pathTF.setText(System.getProperty("user.home") + File.separator + "Desktop");
					btnBrowse.setVisible(true);
					btnActivate.setText("Get info File");
					emailTF.setText("");
					orderTF.setText(orderTFvalue);
				} else {
					emailLabel.setText("Activation Code :");
					orderLabel.setVisible(false);
					pathLabel.setVisible(false);
					orderTF.setVisible(false);
					pathTF.setVisible(false);
					btnBrowse.setVisible(false);
					btnActivate.setText("Activate");
					emailTF.setText("");
				}
			}
		});
		chckbxNewCheckBox.setFont(new Font("Calibri", Font.PLAIN, 14));
		chckbxNewCheckBox.setBounds(17, 144, 288, 17);
		contentPane.add(chckbxNewCheckBox);

		btnActivate = new JButton("Activate");
		btnActivate.setIcon(new ImageIcon(ActivationFrame.class.getResource("/key.png")));
		btnActivate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chckbxNewCheckBox.isSelected() == true) {
					if (emailTF.getText().isEmpty() == true || pathTF.getText().isEmpty() == true
							|| orderTF.getText().isEmpty() == true) {
						JOptionPane.showMessageDialog(null, "Please fill all fields!!", messageboxtitle,
								JOptionPane.INFORMATION_MESSAGE,
								new ImageIcon(ActivationFrame.class.getResource("/information.png")));
						return;
					} else {

						/*
						 * String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" +
						 * "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
						 * 
						 * Pattern pat = Pattern.compile(emailRegex);
						 */

						if (!emailTF.getText().contains("@")) {
							JOptionPane.showMessageDialog(null, "Please enter a valid email address", messageboxtitle,
									JOptionPane.INFORMATION_MESSAGE,
									new ImageIcon(ActivationFrame.class.getResource("/information.png")));
							return;
						} else {
							Writer writer = null;
							try {
								OutputStream fw = new FileOutputStream(
										pathTF.getText() + File.separator + infofilename);
								writer = new OutputStreamWriter(fw, "UTF-16");
								String s1 = emailTF.getText() + System.lineSeparator();
								writer.write(s1);
								String st = orderTF.getText() + System.lineSeparator();
								writer.write(st);
								writer.write(
										ActivationFrame.getSerialNumber(System.getProperty("user.home").substring(0, 1))
												+ System.lineSeparator());
								writer.write(productversion);

								JOptionPane.showMessageDialog(ActivationFrame.this,
										infofilename + " has been saved at " + pathTF.getText() + System.lineSeparator()
												+ "Please email this file to " + Salesemailid,
										messageboxtitle, JOptionPane.INFORMATION_MESSAGE,
										new ImageIcon(ActivationFrame.class.getResource("/information.png")));
//								dispose();
								ActivationFrame mf = new ActivationFrame();

								mf.setLocationRelativeTo(null);
								mf.setVisible(true);

								setVisible(false);
//								Main_Frame mf = new Main_Frame(true, 4);
//								mf.setLocationRelativeTo(null);
//								mf.setVisible(true);
							} catch (IOException ex) {

							} finally {
								if (writer != null) {
									try {
										writer.close();
									} catch (IOException e1) {

										// e1.printStackTrace();
									}
								}
							}
						}
					}
				} else if (chckbxNewCheckBox.isSelected() == false) {
					try {
						strSerialNumber = ActivationFrame
								.getSerialNumber(System.getProperty("user.home").substring(0, 1));
						hashKey = new Hash().getHash(strSerialNumber);
						String fileKey = emailTF.getText();

						fileKey = fileKey.substring(0, fileKey.length() - 1);

						System.out.println(fileKey);
						if (hashKey.equals(fileKey)) {

							File folder = null;
							if (System.getProperty("os.name").toLowerCase().contains("windows")) {
								folder = new File(System.getenv("APPDATA") + File.separator + messageboxtitle);
								folder.mkdirs();
							} else {
								folder = new File(System.getProperty("user.home") + File.separator + "Library"
										+ File.separator + "Application Support" + File.separator + messageboxtitle);
								folder.mkdirs();
							}

							FileWriter fw = new FileWriter(folder.getAbsolutePath() + File.separator + "license.lic");
							fw.write(emailTF.getText());
							fw.close();

							try {
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| UnsupportedLookAndFeelException ex) {
								// ex.printStackTrace();
							}
							JOptionPane.showMessageDialog(ActivationFrame.this, "Software Activated", messageboxtitle,
									JOptionPane.INFORMATION_MESSAGE,
									new ImageIcon(ActivationFrame.class.getResource("/check.png")));
							dispose();
							Starting_Frame.mf.setVisible(false);
							String licencetype = emailTF.getText().substring(emailTF.getText().length() - 1);
							int intlic = Integer.valueOf(licencetype);
							Main_Frame mf = new Main_Frame(false, intlic);
							mf.setLocationRelativeTo(null);
							mf.setVisible(true);

						} else {
							JOptionPane.showMessageDialog(null, "Please Enter a Valid Key!!", messageboxtitle,
									JOptionPane.INFORMATION_MESSAGE,
									new ImageIcon(ActivationFrame.class.getResource("/information.png")));
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnActivate.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnActivate.setBounds(10, 178, 196, 33);
		contentPane.add(btnActivate);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setIcon(new ImageIcon(ActivationFrame.class.getResource("/cross_black.png")));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String warn = "Do you want to close the offline activation ?";
				int ans = JOptionPane.showConfirmDialog(ActivationFrame.this, warn, messageboxtitle,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						new ImageIcon(ActivationFrame.class.getResource("/about-icon-2.png")));
				if (ans == JOptionPane.YES_OPTION) {

					
					File licFileon = null;
					if (System.getProperty("os.name").toLowerCase().contains("windows")) {
						licFileon = new File(System.getenv("APPDATA") + File.separator + Main_Frame.projectTitle
								+ File.separator + "licenseOnline.lic");
					} else {
						licFileon = new File(System.getProperty("user.home") + File.separator + "Library"
								+ File.separator + "Application Support" + File.separator + Main_Frame.projectTitle
								+ File.separator + "licenseOnline.lic");
					}
					boolean activatefromdemo = true;
					dispose();
					OnlineActivation mf = new OnlineActivation(Starting_Frame.mf, licFileon, activatefromdemo);

					mf.setLocationRelativeTo(null);
					mf.setVisible(true);

					System.out.println("CLOSE ON EXIT");
					mf.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent arg0) {
							String warn = "Do you want to close?";
							int ans = JOptionPane.showConfirmDialog(mf, warn, All_Data.messageboxtitle,
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
									new ImageIcon(Main_Frame.class.getResource("/about-icon-2.png")));
							if (ans == JOptionPane.YES_OPTION) {

								setEnabled(true);
								if (Starting_Frame.mf != null) {
									Starting_Frame.mf.setEnabled(true);
										Starting_Frame.mf.setVisible(true);
									
								}
								if (Starting_Frame.activate_btn_click == true) {
									System.exit(0);
								} else {
									Starting_Frame.activate_btn_click = false;
									mf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
								}

							} else {
								mf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
							}
						}
					});

//					setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//					System.exit(0);				
//					dispose();
				
					
					
					
//					File licFileon = null;
//					if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//						licFileon = new File(System.getenv("APPDATA") + File.separator + Main_Frame.projectTitle
//								+ File.separator + "licenseOnline.lic");
//					} else {
//						licFileon = new File(System.getProperty("user.home") + File.separator + "Library"
//								+ File.separator + "Application Support" + File.separator + Main_Frame.projectTitle
//								+ File.separator + "licenseOnline.lic");
//					}
//					boolean activatefromdemo = true;
////				dispose();
//				
//					setVisible(false);
////					new Starting_Frame();
//					OnlineActivation mf = new OnlineActivation(Starting_Frame.mf, licFileon, activatefromdemo);
//					mf.setLocationRelativeTo(null);
//					mf.setVisible(true);
//
//					setEnabled(false);
//					mf.btnBack.setVisible(false);
//					mf.addWindowListener(new WindowAdapter() {
//						@Override
//						public void windowClosing(WindowEvent arg0) {
//							String warn = "Do you want to close?";
//							int ans = JOptionPane.showConfirmDialog(mf, warn, All_Data.messageboxtitle,
//									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
//									new ImageIcon(Main_Frame.class.getResource("/about-icon-2.png")));
//							if (ans == JOptionPane.YES_OPTION) {
//								setEnabled(true);
//								if (Starting_Frame.mf != null) {
//									Starting_Frame.mf.setEnabled(true);
//								}
//								mf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//							} else {
//								mf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//							}
//						}
//					});
//					All_Data.messageboxtitle = Main_Frame.projectTitle;


				}
				if (Starting_Frame.mf != null) {
					Starting_Frame.mf.setVisible(false);
//					Starting_Frame.mf.setEnabled(false);
				}
			}
		});
		btnCancel.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnCancel.setBounds(242, 178, 99, 33);
		contentPane.add(btnCancel);

		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(helpuri));
				} catch (URISyntaxException | IOException ex) {
					// It looks like there's a problem
				}
			}
		});
		btnHelp.setIcon(new ImageIcon(ActivationFrame.class.getResource("/help4.png")));
		btnHelp.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnHelp.setBounds(379, 178, 87, 33);
		contentPane.add(btnHelp);
		init();
	}

	public void init() {
		emailLabel.setText("Activation Code :");
		orderLabel.setVisible(false);
		pathLabel.setVisible(false);
		orderTF.setVisible(false);
		pathTF.setVisible(false);
		btnBrowse.setVisible(false);
		btnActivate.setText("Activate");
		emailTF.setText("");
	}

	public static String getSerialNumber(String drive) {
		String result = "";
		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
			try {
				File file = File.createTempFile("realhowto", ".vbs");
				file.deleteOnExit();
				FileWriter fw = new java.io.FileWriter(file);

				String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
						+ "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
						+ "Wscript.Echo objDrive.SerialNumber"; // see note
				fw.write(vbs);
				fw.close();
				Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					result += line;
				}
				input.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = result.trim();

		} else {
			try {

				ProcessBuilder pb = new ProcessBuilder("bash", "-c",
						"system_profiler SPHardwareDataType | awk '/Serial/ {print $4}'");
				pb.redirectErrorStream(true);
				Process p = pb.start();
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String line;
				while ((line = input.readLine()) != null) {
					System.out.println(line);
					result = line;
				}
				input.close();
			} catch (IOException ex) {
				Logger.getLogger(ActivationFrame.class.getName()).log(Level.SEVERE, null, ex);

			}
		}

		if (result.isEmpty())
			result = defaultSerialNumber;
		return result;
	}
}


//
//package email.activation;
//
//import java.awt.EventQueue;
//import java.awt.Font;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import javax.swing.Action;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JCheckBox;
//import javax.swing.JFileChooser;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JPopupMenu;
//import javax.swing.JTextField;
//import javax.swing.KeyStroke;
//import javax.swing.UIManager;
//import javax.swing.UnsupportedLookAndFeelException;
//import javax.swing.WindowConstants;
//import javax.swing.border.EmptyBorder;
//import javax.swing.text.DefaultEditorKit;
//
//import email.code.All_Data;
//import email.code.Main_Frame;
//import email.code.SelectAll;
//
////import com.DatabaseMigration.code.Main_Frame;
////import com.DatabaseMigration.code.SelectAll;
//
//import net.Hash;
//import java.awt.Color;
//import java.awt.Desktop;
//
//public class ActivationFrame extends JFrame {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private JPanel contentPane;
//	private JTextField emailTF;
//	private JTextField orderTF;
//	private JTextField pathTF;
//	JLabel emailLabel;
//	JLabel orderLabel;
//	JLabel pathLabel;
//	JButton btnBrowse;
//	JCheckBox chckbxNewCheckBox;
//	JButton btnActivate;
//
//	static String messageboxtitle = All_Data.messageboxtitle;
//
//	static String strSerialNumber;
//	static String hashKey;
//	static String defaultSerialNumber = "123456";
//
//	String orderTFvalue = "DRSPSTCONORD";
//	String infofilename = All_Data.messageboxtitle.replace(" ", "") + "Win.info";
//
//	String productversion = All_Data.messageboxtitle + All_Data.version;
//	String Salesemailid = "sales@" + All_Data.domain;
//	String helpuri = "http://messenger.providesupport.com/messenger/0pi295uz3ga080c7lxqxxuaoxr.html";
//
//	/**
//	 * Launch the application.
//	 */
//
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//
//				String fileKey = null;
//
//				File licFile;
//				if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//					licFile = new File(System.getenv("APPDATA") + File.separator + messageboxtitle + File.separator
//							+ "license.lic");
//				} else {
//					licFile = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator
//							+ "Application Support" + File.separator + messageboxtitle + File.separator
//							+ "license.lic");
//				}
//
//				if (licFile.exists()) {
//					try {
//						FileReader fr = new FileReader(licFile);
//						BufferedReader br = new BufferedReader(fr);
//						fileKey = br.readLine();
//						fr.close();
//					} catch (Exception ex) {
//
//					}
//
//					if (fileKey != null) {
//						strSerialNumber = ActivationFrame
//								.getSerialNumber(System.getProperty("user.home").substring(0, 1));
//						hashKey = new Hash().getHash(strSerialNumber);
//						if (hashKey.equals(fileKey)) {
//							try {
//								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//									| UnsupportedLookAndFeelException e) {
//								e.printStackTrace();
//							}
//							Main_Frame mf = new Main_Frame(false, 1);
//							mf.setLocationRelativeTo(null);
//							mf.setVisible(true);
//						} else {
//							try {
//								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//									| UnsupportedLookAndFeelException e) {
//								e.printStackTrace();
//							}
//							ActivationFrame af = new ActivationFrame();
//							af.setLocationRelativeTo(null);
//							af.setVisible(true);
//						}
//					} else {
//						try {
//							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//								| UnsupportedLookAndFeelException e) {
//							e.printStackTrace();
//						}
//						ActivationFrame af = new ActivationFrame();
//						af.setLocationRelativeTo(null);
//						af.setVisible(true);
//					}
//				} else {
//					try {
//						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//							| UnsupportedLookAndFeelException e) {
//						e.printStackTrace();
//					}
//					ActivationFrame af = new ActivationFrame();
//					af.setLocationRelativeTo(null);
//					af.setVisible(true);
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public ActivationFrame() {
//		setResizable(false);
//		setIconImage(Toolkit.getDefaultToolkit().getImage(ActivationFrame.class.getResource("/128x128.png")));
//		addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent arg0) {
//				String warn = "Do you want to close offline activation ?";
//				int ans = JOptionPane.showConfirmDialog(ActivationFrame.this, warn, messageboxtitle,
//						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
//						new ImageIcon(ActivationFrame.class.getResource("/about-icon-2.png")));
//				if (ans == JOptionPane.YES_OPTION) {
//					if (Starting_Frame.mf != null) {
//						Starting_Frame.mf.setEnabled(true);
//					}
//					File licFileon = null;
//					if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//						licFileon = new File(System.getenv("APPDATA") + File.separator + Main_Frame.projectTitle
//								+ File.separator + "licenseOnline.lic");
//					} else {
//						licFileon = new File(System.getProperty("user.home") + File.separator + "Library"
//								+ File.separator + "Application Support" + File.separator + Main_Frame.projectTitle
//								+ File.separator + "licenseOnline.lic");
//					}
//					boolean activatefromdemo = true;
//					dispose();
//					OnlineActivation mf = new OnlineActivation(Starting_Frame.mf, licFileon, activatefromdemo);
//
//					mf.setLocationRelativeTo(null);
//					mf.setVisible(true);
//
//					System.out.println("CLOSE ON EXIT");
//					mf.addWindowListener(new WindowAdapter() {
//						@Override
//						public void windowClosing(WindowEvent arg0) {
//							String warn = "Do you want to close?";
//							int ans = JOptionPane.showConfirmDialog(mf, warn, All_Data.messageboxtitle,
//									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
//									new ImageIcon(Main_Frame.class.getResource("/about-icon-2.png")));
//							if (ans == JOptionPane.YES_OPTION) {
//
//								setEnabled(true);
//								if (Starting_Frame.mf != null) {
//									Starting_Frame.mf.setEnabled(true);
//								}
//								if (Starting_Frame.activate_btn_click == true) {
//									System.exit(0);
//								} else {
//									Starting_Frame.activate_btn_click = false;
//									mf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//								}
//
//							} else {
//								mf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//							}
//						}
//					});
//
////					setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
////					System.exit(0);				
////					dispose();
//				} else {
//					setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//				}
//			}
//		});
//		setTitle(messageboxtitle);
//		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		setBounds(100, 100, 491, 251);
//		contentPane = new JPanel();
//		contentPane.setBackground(Color.WHITE);
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//
//		emailLabel = new JLabel("Email ID :");
//		emailLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
//		emailLabel.setBounds(6, 27, 162, 17);
//		contentPane.add(emailLabel);
//
//		orderLabel = new JLabel("Order ID :");
//		orderLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
//		orderLabel.setBounds(6, 64, 162, 17);
//		contentPane.add(orderLabel);
//
//		pathLabel = new JLabel("Path to save :");
//		pathLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
//		pathLabel.setBounds(6, 102, 162, 17);
//		contentPane.add(pathLabel);
//
//		emailTF = new JTextField();
//		JPopupMenu menu = new JPopupMenu();
//		menu.setBackground(Color.WHITE);
//		Action cut = new DefaultEditorKit.CutAction();
//		cut.putValue(Action.NAME, "Cut");
//		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
//		menu.add(cut);
//		Action copy = new DefaultEditorKit.CopyAction();
//		copy.putValue(Action.NAME, "Copy");
//		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
//		menu.add(copy);
//		Action paste = new DefaultEditorKit.PasteAction();
//		paste.putValue(Action.NAME, "Paste");
//		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
//		menu.add(paste);
//		Action selectAll = new SelectAll();
//		menu.add(selectAll);
//		emailTF.setComponentPopupMenu(menu);
//		emailTF.setBounds(178, 24, 219, 20);
//		contentPane.add(emailTF);
//		emailTF.setColumns(10);
//
//		orderTF = new JTextField();
//		orderTF.setColumns(10);
//		orderTF.setEditable(false);
//		orderTF.setBounds(178, 61, 219, 20);
//		contentPane.add(orderTF);
//
//		pathTF = new JTextField();
//		pathTF.setEditable(false);
//		pathTF.setColumns(10);
//		pathTF.setBounds(178, 99, 219, 20);
//		contentPane.add(pathTF);
//
//		btnBrowse = new JButton("...");
//		btnBrowse.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				JFileChooser fileChooser = new JFileChooser(
//						System.getProperty("user.home") + File.separator + "Desktop");
//				fileChooser.setDialogTitle("Specify a file to save");
//				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//				int userSelection = fileChooser.showSaveDialog(ActivationFrame.this);
//
//				if (userSelection == JFileChooser.APPROVE_OPTION) {
//					File check = new File(fileChooser.getSelectedFile() + "");
//					if (check.exists())
//						pathTF.setText(fileChooser.getSelectedFile() + "");
//					else
//						JOptionPane.showMessageDialog(ActivationFrame.this, "Please enter valid Path", messageboxtitle,
//								JOptionPane.INFORMATION_MESSAGE,
//								new ImageIcon(ActivationFrame.class.getResource("/information.png")));
//				}
//			}
//		});
//		btnBrowse.setBounds(413, 97, 53, 23);
//		contentPane.add(btnBrowse);
//
//		chckbxNewCheckBox = new JCheckBox("I don't have Authentication Code");
//		chckbxNewCheckBox.setBackground(Color.WHITE);
//		chckbxNewCheckBox.setFocusPainted(false);
//		chckbxNewCheckBox.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if (chckbxNewCheckBox.isSelected() == true) {
//					emailLabel.setText("Email ID  :");
//					orderLabel.setVisible(true);
//					pathLabel.setVisible(true);
//					orderTF.setVisible(true);
//					pathTF.setVisible(true);
//					pathTF.setText(System.getProperty("user.home") + File.separator + "Desktop");
//					btnBrowse.setVisible(true);
//					btnActivate.setText("Get info File");
//					emailTF.setText("");
//					orderTF.setText(orderTFvalue);
//				} else {
//					emailLabel.setText("Activation Code :");
//					orderLabel.setVisible(false);
//					pathLabel.setVisible(false);
//					orderTF.setVisible(false);
//					pathTF.setVisible(false);
//					btnBrowse.setVisible(false);
//					btnActivate.setText("Activate");
//					emailTF.setText("");
//				}
//			}
//		});
//		chckbxNewCheckBox.setFont(new Font("Calibri", Font.PLAIN, 14));
//		chckbxNewCheckBox.setBounds(17, 144, 288, 17);
//		contentPane.add(chckbxNewCheckBox);
//
//		btnActivate = new JButton("Activate");
//		btnActivate.setIcon(new ImageIcon(ActivationFrame.class.getResource("/key.png")));
//		btnActivate.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				if (chckbxNewCheckBox.isSelected() == true) {
//					if (emailTF.getText().isEmpty() == true || pathTF.getText().isEmpty() == true
//							|| orderTF.getText().isEmpty() == true) {
//						JOptionPane.showMessageDialog(null, "Please fill all fields!!", messageboxtitle,
//								JOptionPane.INFORMATION_MESSAGE,
//								new ImageIcon(ActivationFrame.class.getResource("/information.png")));
//						return;
//					} else {
//
//						/*
//						 * String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" +
//						 * "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
//						 * 
//						 * Pattern pat = Pattern.compile(emailRegex);
//						 */
//
//						if (!emailTF.getText().contains("@")) {
//							JOptionPane.showMessageDialog(null, "Please enter a valid email address", messageboxtitle,
//									JOptionPane.INFORMATION_MESSAGE,
//									new ImageIcon(ActivationFrame.class.getResource("/information.png")));
//							return;
//						} else {
//							Writer writer = null;
//							try {
//								OutputStream fw = new FileOutputStream(
//										pathTF.getText() + File.separator + infofilename);
//								writer = new OutputStreamWriter(fw, "UTF-16");
//								String s1 = emailTF.getText() + System.lineSeparator();
//								writer.write(s1);
//								String st = orderTF.getText() + System.lineSeparator();
//								writer.write(st);
//								writer.write(
//										ActivationFrame.getSerialNumber(System.getProperty("user.home").substring(0, 1))
//												+ System.lineSeparator());
//								writer.write(productversion);
//
//								JOptionPane.showMessageDialog(ActivationFrame.this,
//										infofilename + " has been saved at " + pathTF.getText() + System.lineSeparator()
//												+ "Please email this file to " + Salesemailid,
//										messageboxtitle, JOptionPane.INFORMATION_MESSAGE,
//										new ImageIcon(ActivationFrame.class.getResource("/information.png")));
////								dispose();
//								ActivationFrame mf = new ActivationFrame();
//
//								mf.setLocationRelativeTo(null);
//								mf.setVisible(true);
//
//								setVisible(false);
////								Main_Frame mf = new Main_Frame(true, 4);
////								mf.setLocationRelativeTo(null);
////								mf.setVisible(true);
//							} catch (IOException ex) {
//
//							} finally {
//								if (writer != null) {
//									try {
//										writer.close();
//									} catch (IOException e1) {
//
//										// e1.printStackTrace();
//									}
//								}
//							}
//						}
//					}
//				} else if (chckbxNewCheckBox.isSelected() == false) {
//					try {
//						strSerialNumber = ActivationFrame
//								.getSerialNumber(System.getProperty("user.home").substring(0, 1));
//						hashKey = new Hash().getHash(strSerialNumber);
//						String fileKey = emailTF.getText();
//
//						fileKey = fileKey.substring(0, fileKey.length() - 1);
//
//						System.out.println(fileKey);
//						if (hashKey.equals(fileKey)) {
//
//							File folder = null;
//							if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//								folder = new File(System.getenv("APPDATA") + File.separator + messageboxtitle);
//								folder.mkdirs();
//							} else {
//								folder = new File(System.getProperty("user.home") + File.separator + "Library"
//										+ File.separator + "Application Support" + File.separator + messageboxtitle);
//								folder.mkdirs();
//							}
//
//							FileWriter fw = new FileWriter(folder.getAbsolutePath() + File.separator + "license.lic");
//							fw.write(emailTF.getText());
//							fw.close();
//
//							try {
//								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//									| UnsupportedLookAndFeelException ex) {
//								// ex.printStackTrace();
//							}
//							JOptionPane.showMessageDialog(ActivationFrame.this, "Software Activated", messageboxtitle,
//									JOptionPane.INFORMATION_MESSAGE,
//									new ImageIcon(ActivationFrame.class.getResource("/check.png")));
//							dispose();
//							Starting_Frame.mf.setVisible(false);
//							String licencetype = emailTF.getText().substring(emailTF.getText().length() - 1);
//							int intlic = Integer.valueOf(licencetype);
//							Main_Frame mf = new Main_Frame(false, intlic);
//							mf.setLocationRelativeTo(null);
//							mf.setVisible(true);
//
//						} else {
//							JOptionPane.showMessageDialog(null, "Please Enter a Valid Key!!", messageboxtitle,
//									JOptionPane.INFORMATION_MESSAGE,
//									new ImageIcon(ActivationFrame.class.getResource("/information.png")));
//						}
//					} catch (Exception e1) {
//						e1.printStackTrace();
//					}
//				}
//			}
//		});
//		btnActivate.setFont(new Font("Calibri", Font.PLAIN, 14));
//		btnActivate.setBounds(10, 178, 196, 33);
//		contentPane.add(btnActivate);
//
//		JButton btnCancel = new JButton("Cancel");
//		btnCancel.setIcon(new ImageIcon(ActivationFrame.class.getResource("/cross_black.png")));
//		btnCancel.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				String warn = "Do you want to close the offline activation ?";
//				int ans = JOptionPane.showConfirmDialog(ActivationFrame.this, warn, messageboxtitle,
//						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
//						new ImageIcon(ActivationFrame.class.getResource("/about-icon-2.png")));
//				if (ans == JOptionPane.YES_OPTION) {
//
//					File licFileon = null;
//					if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//						licFileon = new File(System.getenv("APPDATA") + File.separator + Main_Frame.projectTitle
//								+ File.separator + "licenseOnline.lic");
//					} else {
//						licFileon = new File(System.getProperty("user.home") + File.separator + "Library"
//								+ File.separator + "Application Support" + File.separator + Main_Frame.projectTitle
//								+ File.separator + "licenseOnline.lic");
//					}
//					boolean activatefromdemo = true;
//					dispose();
//					OnlineActivation mf = new OnlineActivation(Starting_Frame.mf, licFileon, activatefromdemo);
//
//					mf.setLocationRelativeTo(null);
//					mf.setVisible(true);
//
//					System.out.println("CLOSE ON EXIT");
//					mf.addWindowListener(new WindowAdapter() {
//						@Override
//						public void windowClosing(WindowEvent arg0) {
//							String warn = "Do you want to close?";
//							int ans = JOptionPane.showConfirmDialog(mf, warn, All_Data.messageboxtitle,
//									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
//									new ImageIcon(Main_Frame.class.getResource("/about-icon-2.png")));
//							if (ans == JOptionPane.YES_OPTION) {
//
//								setEnabled(true);
//								if (Starting_Frame.mf != null) {
//									Starting_Frame.mf.setEnabled(true);
//									Starting_Frame.mf.setVisible(true);
//
//								}
//								if (Starting_Frame.activate_btn_click == true) {
//									System.exit(0);
//								} else {
//									Starting_Frame.activate_btn_click = false;
//									mf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//								}
//
//							} else {
//								mf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//							}
//						}
//					});
//
////					setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
////					System.exit(0);				
////					dispose();
//
////					File licFileon = null;
////					if (System.getProperty("os.name").toLowerCase().contains("windows")) {
////						licFileon = new File(System.getenv("APPDATA") + File.separator + Main_Frame.projectTitle
////								+ File.separator + "licenseOnline.lic");
////					} else {
////						licFileon = new File(System.getProperty("user.home") + File.separator + "Library"
////								+ File.separator + "Application Support" + File.separator + Main_Frame.projectTitle
////								+ File.separator + "licenseOnline.lic");
////					}
////					boolean activatefromdemo = true;
//////				dispose();
////				
////					setVisible(false);
//////					new Starting_Frame();
////					OnlineActivation mf = new OnlineActivation(Starting_Frame.mf, licFileon, activatefromdemo);
////					mf.setLocationRelativeTo(null);
////					mf.setVisible(true);
////
////					setEnabled(false);
////					mf.btnBack.setVisible(false);
////					mf.addWindowListener(new WindowAdapter() {
////						@Override
////						public void windowClosing(WindowEvent arg0) {
////							String warn = "Do you want to close?";
////							int ans = JOptionPane.showConfirmDialog(mf, warn, All_Data.messageboxtitle,
////									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
////									new ImageIcon(Main_Frame.class.getResource("/about-icon-2.png")));
////							if (ans == JOptionPane.YES_OPTION) {
////								setEnabled(true);
////								if (Starting_Frame.mf != null) {
////									Starting_Frame.mf.setEnabled(true);
////								}
////								mf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
////							} else {
////								mf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
////							}
////						}
////					});
////					All_Data.messageboxtitle = Main_Frame.projectTitle;
//
//				}
//				if (Starting_Frame.mf != null) {
//					Starting_Frame.mf.setVisible(false);
////					Starting_Frame.mf.setEnabled(false);
//				}
//			}
//		});
//		btnCancel.setFont(new Font("Calibri", Font.PLAIN, 14));
//		btnCancel.setBounds(242, 178, 99, 33);
//		contentPane.add(btnCancel);
//
//		JButton btnHelp = new JButton("Help");
//		btnHelp.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					Desktop.getDesktop().browse(new URI(helpuri));
//				} catch (URISyntaxException | IOException ex) {
//					// It looks like there's a problem
//				}
//			}
//		});
//		btnHelp.setIcon(new ImageIcon(ActivationFrame.class.getResource("/help4.png")));
//		btnHelp.setFont(new Font("Calibri", Font.PLAIN, 14));
//		btnHelp.setBounds(379, 178, 87, 33);
//		contentPane.add(btnHelp);
//		init();
//	}
//
//	public void init() {
//		emailLabel.setText("Activation Code :");
//		orderLabel.setVisible(false);
//		pathLabel.setVisible(false);
//		orderTF.setVisible(false);
//		pathTF.setVisible(false);
//		btnBrowse.setVisible(false);
//		btnActivate.setText("Activate");
//		emailTF.setText("");
//	}
//
//	public static String getSerialNumber(String drive) {
//		String result = "";
//		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//			try {
//				File file = File.createTempFile("realhowto", ".vbs");
//				file.deleteOnExit();
//				FileWriter fw = new java.io.FileWriter(file);
//
//				String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
//						+ "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
//						+ "Wscript.Echo objDrive.SerialNumber"; // see note
//				fw.write(vbs);
//				fw.close();
//				Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
//				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//				String line;
//				while ((line = input.readLine()) != null) {
//					result += line;
//				}
//				input.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			result = result.trim();
//
//		} else {
//			try {
//
//				ProcessBuilder pb = new ProcessBuilder("bash", "-c",
//						"system_profiler SPHardwareDataType | awk '/Serial/ {print $4}'");
//				pb.redirectErrorStream(true);
//				Process p = pb.start();
//				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//				String line;
//				while ((line = input.readLine()) != null) {
//					System.out.println(line);
//					result = line;
//				}
//				input.close();
//			} catch (IOException ex) {
//				Logger.getLogger(ActivationFrame.class.getName()).log(Level.SEVERE, null, ex);
//
//			}
//		}
//
//		if (result.isEmpty())
//			result = defaultSerialNumber;
//		return result;
//	}
//}
//
////
////import java.awt.Desktop;
////import java.awt.EventQueue;
////import java.awt.Font;
////import java.awt.Toolkit;
////import java.awt.event.ActionEvent;
////import java.awt.event.ActionListener;
////import java.awt.event.WindowAdapter;
////import java.awt.event.WindowEvent;
////import java.io.BufferedReader;
////import java.io.File;
////import java.io.FileOutputStream;
////import java.io.FileReader;
////import java.io.FileWriter;
////import java.io.IOException;
////import java.io.InputStreamReader;
////import java.io.OutputStream;
////import java.io.OutputStreamWriter;
////import java.io.Writer;
////import java.net.URI;
////import java.net.URISyntaxException;
////import java.util.logging.Level;
////import java.util.logging.Logger;
////
////import javax.swing.ImageIcon;
////import javax.swing.JButton;
////import javax.swing.JCheckBox;
////import javax.swing.JFileChooser;
////import javax.swing.JFrame;
////import javax.swing.JLabel;
////import javax.swing.JOptionPane;
////import javax.swing.JPanel;
////import javax.swing.JTextField;
////import javax.swing.UIManager;
////import javax.swing.UnsupportedLookAndFeelException;
////import javax.swing.border.EmptyBorder;
////
////import email.code.All_Data;
////import email.code.Main_Frame;
////import net.Hash;
////import java.awt.Color;
////
////public class ActivationFrame extends JFrame {
////	/**
////	 * 
////	 */
////	private static final long serialVersionUID = 1L;
////	private JPanel contentPane;
////	private JTextField emailTF;
////	private JTextField orderTF;
////	private JTextField pathTF;
////	JLabel emailLabel;
////	JLabel orderLabel;
////	JLabel pathLabel;
////	JButton btnBrowse;
////	JCheckBox chckbxNewCheckBox;
////	JButton btnActivate;
////
////	static String messageboxtitle =  All_Data.messageboxtitle;
////
////	static String strSerialNumber;
////	static String hashKey;
////	static String defaultSerialNumber = "123456";
////
////	String orderTFvalue = "DRSPSTCONORD";
////	String infofilename =  All_Data.messageboxtitle.replace(" ", "")+"Win.info";
////
////	String productversion = All_Data.messageboxtitle+All_Data.version;
////	String Salesemailid = "sales@"+All_Data.domain;
////	String helpuri = "http://messenger.providesupport.com/messenger/0pi295uz3ga080c7lxqxxuaoxr.html";
////
////	
////	public static void main(String[] args) {
////		EventQueue.invokeLater(new Runnable() {
////			public void run() {
////
////				String fileKey = null;
////
////				File licFile;
////				if (System.getProperty("os.name").toLowerCase().contains("windows")) {
////					licFile = new File(System.getenv("APPDATA") + File.separator + messageboxtitle + File.separator
////							+ "license.lic");
////				} else {
////					licFile = new File(System.getProperty("user.home") + File.separator + "Library" + File.separator
////							+ "Application Support" + File.separator + messageboxtitle + File.separator
////							+ "license.lic");
////				}
////
////				if (licFile.exists()) {
////					try {
////						FileReader fr = new FileReader(licFile);
////						BufferedReader br = new BufferedReader(fr);
////						fileKey = br.readLine();
////						fr.close();
////					} catch (Exception ex) {
////
////					}
////
////					if (fileKey != null) {
////						strSerialNumber = ActivationFrame
////								.getSerialNumber(System.getProperty("user.home").substring(0, 1));
////						hashKey = new Hash().getHash(strSerialNumber);
////						if (hashKey.equals(fileKey)) {
////							try {
////								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
////									| UnsupportedLookAndFeelException e) {
////								e.printStackTrace();
////							}
////							Main_Frame mf = new Main_Frame(false, 1);
////							mf.setLocationRelativeTo(null);
////							mf.setVisible(true);
////						} else {
////							try {
////								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
////									| UnsupportedLookAndFeelException e) {
////								e.printStackTrace();
////							}
////							ActivationFrame af = new ActivationFrame();
////							af.setLocationRelativeTo(null);
////							af.setVisible(true);
////						}
////					} else {
////						try {
////							UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
////								| UnsupportedLookAndFeelException e) {
////							e.printStackTrace();
////						}
////						ActivationFrame af = new ActivationFrame();
////						af.setLocationRelativeTo(null);
////						af.setVisible(true);
////					}
////				} else {
////					try {
////						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
////							| UnsupportedLookAndFeelException e) {
////						e.printStackTrace();
////					}
////					ActivationFrame af = new ActivationFrame();
////					af.setLocationRelativeTo(null);
////					af.setVisible(true);
////				}
////			}
////		});
////	}
////
////	/**
////	 * Create the frame.
////	 */
////	public ActivationFrame() {
////		setResizable(false);
////		setIconImage(Toolkit.getDefaultToolkit().getImage(ActivationFrame.class.getResource("/128x128.png")));
////		addWindowListener(new WindowAdapter() {
////			@Override
////			public void windowClosing(WindowEvent arg0) {
////				String warn = "Do you want to close?";
////				int ans = JOptionPane.showConfirmDialog(ActivationFrame.this, warn, messageboxtitle,
////						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
////						new ImageIcon(ActivationFrame.class.getResource("/about-icon-2.png")));
////				if (ans == JOptionPane.YES_OPTION) {
////					System.exit(0);
////				}
////			}
////		});
////		setTitle(messageboxtitle);
////		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
////		setBounds(100, 100, 491, 251);
////		contentPane = new JPanel();
////		contentPane.setBackground(Color.WHITE);
////		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
////		setContentPane(contentPane);
////		contentPane.setLayout(null);
////
////		emailLabel = new JLabel("Email ID :");
////		emailLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
////		emailLabel.setBounds(6, 27, 162, 17);
////		contentPane.add(emailLabel);
////
////		orderLabel = new JLabel("Order ID :");
////		orderLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
////		orderLabel.setBounds(6, 64, 162, 17);
////		contentPane.add(orderLabel);
////
////		pathLabel = new JLabel("Path to save :");
////		pathLabel.setFont(new Font("Calibri", Font.PLAIN, 14));
////		pathLabel.setBounds(6, 102, 162, 17);
////		contentPane.add(pathLabel);
////
////		emailTF = new JTextField();
////		emailTF.setBounds(178, 24, 219, 20);
////		contentPane.add(emailTF);
////		emailTF.setColumns(10);
////
////		orderTF = new JTextField();
////		orderTF.setColumns(10);
////		orderTF.setEditable(false);
////		orderTF.setBounds(178, 61, 219, 20);
////		contentPane.add(orderTF);
////
////		pathTF = new JTextField();
////		pathTF.setEditable(false);
////		pathTF.setColumns(10);
////		pathTF.setBounds(178, 99, 219, 20);
////		contentPane.add(pathTF);
////
////		btnBrowse = new JButton("...");
////		btnBrowse.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent arg0) {
////				JFileChooser fileChooser = new JFileChooser(
////						System.getProperty("user.home") + File.separator + "Desktop");
////				fileChooser.setDialogTitle("Specify a file to save");
////				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
////				int userSelection = fileChooser.showSaveDialog(ActivationFrame.this);
////
////				if (userSelection == JFileChooser.APPROVE_OPTION) {
////					File check = new File(fileChooser.getSelectedFile() + "");
////					if (check.exists())
////						pathTF.setText(fileChooser.getSelectedFile() + "");
////					else
////						JOptionPane.showMessageDialog(ActivationFrame.this, "Please enter valid Path", messageboxtitle,
////								JOptionPane.INFORMATION_MESSAGE,
////								new ImageIcon(ActivationFrame.class.getResource("/information.png")));
////				}
////			}
////		});
////		btnBrowse.setBounds(413, 97, 53, 23);
////		contentPane.add(btnBrowse);
////
////		chckbxNewCheckBox = new JCheckBox("I don't have Authentication Code");
////		chckbxNewCheckBox.setBackground(Color.WHITE);
////		chckbxNewCheckBox.setFocusPainted(false);
////		chckbxNewCheckBox.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				if (chckbxNewCheckBox.isSelected() == true) {
////					emailLabel.setText("Email ID  :");
////					orderLabel.setVisible(true);
////					pathLabel.setVisible(true);
////					orderTF.setVisible(true);
////					pathTF.setVisible(true);
////					pathTF.setText(System.getProperty("user.home") + File.separator + "Desktop");
////					btnBrowse.setVisible(true);
////					btnActivate.setText("Get info File");
////					emailTF.setText("");
////					orderTF.setText(orderTFvalue);
////				} else {
////					emailLabel.setText("Activation Code :");
////					orderLabel.setVisible(false);
////					pathLabel.setVisible(false);
////					orderTF.setVisible(false);
////					pathTF.setVisible(false);
////					btnBrowse.setVisible(false);
////					btnActivate.setText("Activate");
////					emailTF.setText("");
////				}
////			}
////		});
////		chckbxNewCheckBox.setFont(new Font("Calibri", Font.PLAIN, 14));
////		chckbxNewCheckBox.setBounds(17, 144, 288, 17);
////		contentPane.add(chckbxNewCheckBox);
////
////		btnActivate = new JButton("Activate");
////		btnActivate.setIcon(new ImageIcon(ActivationFrame.class.getResource("/key.png")));
////		btnActivate.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////
////				if (chckbxNewCheckBox.isSelected() == true) {
////					if (emailTF.getText().isEmpty() == true || pathTF.getText().isEmpty() == true
////							|| orderTF.getText().isEmpty() == true) {
////						JOptionPane.showMessageDialog(null, "Please fill all fields!!", messageboxtitle,
////								JOptionPane.INFORMATION_MESSAGE,
////								new ImageIcon(ActivationFrame.class.getResource("/information.png")));
////						return;
////					} else {
////
////						/*
////						 * String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" +
////						 * "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
////						 * 
////						 * Pattern pat = Pattern.compile(emailRegex);
////						 */
////
////						if (!emailTF.getText().contains("@")) {
////							JOptionPane.showMessageDialog(null, "Please enter a valid email address", messageboxtitle,
////									JOptionPane.INFORMATION_MESSAGE,
////									new ImageIcon(ActivationFrame.class.getResource("/information.png")));
////							return;
////						} else {
////							Writer writer = null;
////							try {
////								OutputStream fw = new FileOutputStream(
////										pathTF.getText() + File.separator + infofilename);
////								writer = new OutputStreamWriter(fw, "UTF-16");
////								String s1 = emailTF.getText() + System.lineSeparator();
////								writer.write(s1);
////								String st = orderTF.getText() + System.lineSeparator();
////								writer.write(st);
////								writer.write(
////										ActivationFrame.getSerialNumber(System.getProperty("user.home").substring(0, 1))
////												+ System.lineSeparator());
////								writer.write(productversion);
////
////								JOptionPane.showMessageDialog(ActivationFrame.this,
////										infofilename + " has been saved at " + pathTF.getText() + System.lineSeparator()
////												+ "Please email this file to " + Salesemailid,
////										messageboxtitle, JOptionPane.INFORMATION_MESSAGE,
////										new ImageIcon(ActivationFrame.class.getResource("/information.png")));
////								dispose();
////								Main_Frame mf = new Main_Frame(true, 4);
////								mf.setLocationRelativeTo(null);
////								mf.setVisible(true);
////							} catch (IOException ex) {
////
////							} finally {
////								if (writer != null) {
////									try {
////										writer.close();
////									} catch (IOException e1) {
////
////										// e1.printStackTrace();
////									}
////								}
////							}
////						}
////					}
////				} else if (chckbxNewCheckBox.isSelected() == false) {
////					try {
////						strSerialNumber = ActivationFrame
////								.getSerialNumber(System.getProperty("user.home").substring(0, 1));
////						hashKey = new Hash().getHash(strSerialNumber);
////						String fileKey=emailTF.getText();
////						
////						
////						fileKey=fileKey.substring(0, fileKey.length() - 1);
////						
////						System.out.println(fileKey);
////						if (hashKey.equals(fileKey)) {
////
////							File folder = null;
////							if (System.getProperty("os.name").toLowerCase().contains("windows")) {
////								folder = new File(System.getenv("APPDATA") + File.separator + messageboxtitle);
////								folder.mkdirs();
////							} else {
////								folder = new File(System.getProperty("user.home") + File.separator + "Library"
////										+ File.separator + "Application Support" + File.separator + messageboxtitle);
////								folder.mkdirs();
////							}
////
////							FileWriter fw = new FileWriter(folder.getAbsolutePath() + File.separator + "license.lic");
////							fw.write(emailTF.getText());
////							fw.close();
////
////							try {
////								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
////							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
////									| UnsupportedLookAndFeelException ex) {
////								// ex.printStackTrace();
////							}
////							JOptionPane.showMessageDialog(ActivationFrame.this, "Software Activated", messageboxtitle,
////									JOptionPane.INFORMATION_MESSAGE,
////									new ImageIcon(ActivationFrame.class.getResource("/check.png")));
////							dispose();
////							
////							
////							String licencetype=emailTF.getText().substring(emailTF.getText().length() - 1);
////							 int intlic=Integer.valueOf(licencetype);
////							 setVisible(false);
////							 Main_Frame mf = new Main_Frame(false,intlic);
////							mf.setLocationRelativeTo(null);
////							mf.setVisible(true);
////							System.out.println("Duplicate frame");
////							
////
////						} else {
////							JOptionPane.showMessageDialog(null, "Please Enter a Valid Key!!", messageboxtitle,
////									JOptionPane.INFORMATION_MESSAGE,
////									new ImageIcon(ActivationFrame.class.getResource("/information.png")));
////						}
////					} catch (Exception e1) {
////						e1.printStackTrace();
////					}
////				}
////			}
////		});
////		btnActivate.setFont(new Font("Calibri", Font.PLAIN, 14));
////		btnActivate.setBounds(10, 178, 196, 33);
////		contentPane.add(btnActivate);
////
////		JButton btnCancel = new JButton("Cancel");
////		btnCancel.setIcon(new ImageIcon(ActivationFrame.class.getResource("/cross_black.png")));
////		btnCancel.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent arg0) {
////				String warn = "Do you want to close?";
////				int ans = JOptionPane.showConfirmDialog(ActivationFrame.this, warn, messageboxtitle,
////						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
////						new ImageIcon(ActivationFrame.class.getResource("/about-icon-2.png")));
////				if (ans == JOptionPane.YES_OPTION) {
////					System.exit(0);
////				}
////			}
////		});
////		btnCancel.setFont(new Font("Calibri", Font.PLAIN, 14));
////		btnCancel.setBounds(242, 178, 99, 33);
////		contentPane.add(btnCancel);
////
////		JButton btnHelp = new JButton("Help");
////		btnHelp.addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				try {
////					Desktop.getDesktop().browse(new URI(helpuri));
////				} catch (URISyntaxException | IOException ex) {
////					// It looks like there's a problem
////				}
////			}
////		});
////		btnHelp.setIcon(new ImageIcon(ActivationFrame.class.getResource("/help4.png")));
////		btnHelp.setFont(new Font("Calibri", Font.PLAIN, 14));
////		btnHelp.setBounds(379, 178, 87, 33);
////		contentPane.add(btnHelp);
////		init();
////	}
////
////	public void init() {
////		emailLabel.setText("Activation Code :");
////		orderLabel.setVisible(false);
////		pathLabel.setVisible(false);
////		orderTF.setVisible(false);
////		pathTF.setVisible(false);
////		btnBrowse.setVisible(false);
////		btnActivate.setText("Activate");
////		emailTF.setText("");
////	}
////
////	public static String getSerialNumber(String drive) {
////		String result = "";
////		if (System.getProperty("os.name").toLowerCase().contains("windows")) {
////			try {
////				File file = File.createTempFile("realhowto", ".vbs");
////				file.deleteOnExit();
////				FileWriter fw = new java.io.FileWriter(file);
////
////				String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"
////						+ "Set colDrives = objFSO.Drives\n" + "Set objDrive = colDrives.item(\"" + drive + "\")\n"
////						+ "Wscript.Echo objDrive.SerialNumber"; // see note
////				fw.write(vbs);
////				fw.close();
////				Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
////				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
////				String line;
////				while ((line = input.readLine()) != null) {
////					result += line;
////				}
////				input.close();
////			} catch (Exception e) {
////				e.printStackTrace();
////			}
////			result = result.trim();
////
////		} else {
////			try {
////
////				ProcessBuilder pb = new ProcessBuilder("bash", "-c",
////						"system_profiler SPHardwareDataType | awk '/Serial/ {print $4}'");
////				pb.redirectErrorStream(true);
////				Process p = pb.start();
////				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
////				String line;
////				while ((line = input.readLine()) != null) {
////					System.out.println(line);
////					result = line;
////				}
////				input.close();
////			} catch (IOException ex) {
////				Logger.getLogger(ActivationFrame.class.getName()).log(Level.SEVERE, null, ex);
////
////			}
////		}
////
////		if (result.isEmpty())
////			result = defaultSerialNumber;
////		return result;
////	}
////}
