import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import org.jdesktop.xswingx.PromptSupport;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@SuppressWarnings("serial")
public class Main extends JFrame
{

	private static final String USER        = "root";
    private static final String PASSWORD    = "root";
    private static final String DB          = "search";
    private static final String LOCAL       = "localhost";
    private static final String PORT        = "3306";
    static SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
    static SimpleDateFormat dt2 = new SimpleDateFormat("yyyy-MM-dd");
 //  JPanel pane = new JPanel(new GridLayout(0,1));
	  JPanel pane = new JPanel(new GridBagLayout());
	  JPanel pane2 = new  JPanel(new GridBagLayout());
	  JPanel pane3 = new JPanel(new GridBagLayout());

		

	  //for first panel
	  JButton search = new JButton("Search");
	  JButton print = new JButton("Print Preview");

	  JButton edit = new JButton("Update");

	  static JTextField mobile_no=new JTextField(20);
	  static JTextField first_name=new JTextField(10);
	  static JTextField last_name=new JTextField(10);
	  

	  
	  final Object[][] data = {
			    {"Mary", "Campione", "Snowboarding", "5"},
			    {"Alison", "Huml", "Rowing", "3"},
			    {"Kathy", "Walrath", "Chasing toddlers", "2"},
			    {"Mark", "Andrews", "Speed reading", "20"},
			    {"Angela", "Lih", "Teaching high school", "4"}
			    };
	final Object[] columnNames = {"First Name",
			                              "Last Name",
			                              "Sport",
			                              "Est. Years Experience","First Name",
			                              "Last Name",
			                              "Sport",
			                              "Est. Years Experience"};
			
 
	  @SuppressWarnings("rawtypes")
	public static void setUIFont(FontUIResource f) {
	        Enumeration keys = UIManager.getDefaults().keys();
	        while (keys.hasMoreElements()) {
	            Object key = keys.nextElement();
	            Object value = UIManager.get(key);
	            if (value instanceof FontUIResource) {
	                FontUIResource orig = (FontUIResource) value;
	                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
	                UIManager.put(key, new FontUIResource(font));
	            }
	        }
	    }
	  String mob_no;
	  
	Main() throws IOException 
	  {
	    super("Search Address"); 
	  setBounds(0,0,1000,550);

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container con = this.getContentPane();
	    setUIFont(new FontUIResource(new Font("Book Antiqua", 0, 12)));
	    con.add(pane2);
	    
	    String path = "D:\\App\\logo.PNG";
	      File file = new File(path);
	      BufferedImage image = ImageIO.read(file);
	      JLabel label = new JLabel(new ImageIcon(image));
	    
	     // address.setLineWrap( true );


	      first_name .setFont(new FontUIResource(new Font("Book Antiqua", 0, 12)));
	      last_name .setFont(new FontUIResource(new Font("Book Antiqua", 0, 12)));
	    mobile_no.setFont(new FontUIResource(new Font("Book Antiqua", 0, 12)));



	    
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(5, 5, 5, 5);
	    constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 0;
		constraints.anchor = GridBagConstraints.CENTER;
	    pane2.add(label, constraints);
	    constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;

	    constraints.gridx = 0;
		constraints.gridy = 2;
	    pane2.add(pane, constraints);
	    
	    // first panel
	    constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.CENTER;
	    pane.add( new JLabel("<html><font color='#336600'>SEARCH / UPDATE PATIENT</font></html>"), constraints);
	    
	    
	    constraints.gridx = 0;
		constraints.gridy = 2;
	    pane.add(first_name, constraints);
	    constraints.gridx = 1;
	    pane.add(last_name, constraints);
		constraints.gridx = 2;
	    pane.add(mobile_no, constraints);
	    constraints.gridx = 3;
	    pane.add(search, constraints);
	    PromptSupport.setPrompt("First Name", first_name);
	    PromptSupport.setPrompt("Last Name", last_name);
	    PromptSupport.setPrompt("Contact Number", mobile_no);
	    
	    constraints.gridx = 0;
		constraints.gridy = 3;
	    pane2.add(pane3, constraints);
  
	   /*constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.anchor = GridBagConstraints.CENTER;
	    pane3.add( new JLabel("<html><font color='#336600'>DISPLAY RECORDS</font></html>"), constraints);*/
	    
	    JList table = new JList(columnNames);
	    new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        //Create the scroll pane and add the table to it.
    //JScrollPane scrollPane = JTable.createScrollPaneForTable(table);
    //scrollPane.setPreferredSize(new Dimension(400, 100));
 
   
       // add(scrollPane);
	   pane3.add(table);

	    
	    
	    search.addActionListener(new ActionListener()
		  {
		      @Override
		      public void actionPerformed(ActionEvent e)
		      {

		    	  if(mobile_no.getText().isEmpty())
		    	  {
		    		  JOptionPane.showMessageDialog(null, "Insert Mobile no first.");
		    	  }
		    	 
		    	  else if(mobile_no.getText().matches("[0-9]{5,13}"))
		    	  {
		    		  mob_no=mobile_no.getText();
		          String url = "jdbc:mysql://"+LOCAL+":"+PORT+"/"+DB;
			        try {
						Connection conn = DriverManager.getConnection(url, USER, PASSWORD);
						Statement st=conn.createStatement();
						String query="select address,first_name,last_name,age from search.check where first_name like '%"+first_name.getText()+"%'";
						ResultSet rs=st.executeQuery(query);
						GridBagConstraints constraints = new GridBagConstraints();
					    constraints.anchor = GridBagConstraints.WEST;
						constraints.insets = new Insets(5, 5, 5, 5);
						int i=1;
						while(rs.next())
						{
							 i++;
							constraints.gridx = 0;
							constraints.gridy = i;
							constraints.gridwidth = 1;
							constraints.anchor = GridBagConstraints.CENTER;
						    pane3.add( new JLabel("<html><font color='#336600'>"+rs.getString("first_name")+" "+rs.getString("last_name")+
						    		" "+rs.getString("address")+"</font></html>"), constraints);
						    pane3.validate();
						    pane3.repaint();
						   
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
		    	  }
		    	  else
		    	  {
		    		  JOptionPane.showMessageDialog(null, "Insert proper number.");
		    	  }
		      }
		  });
	    
	    print.addActionListener(new ActionListener()
		  {
		      @Override
		      public void actionPerformed(ActionEvent e)
		      {
		    	  if(mobile_no.getText().isEmpty())
		    	  {
		    		  JOptionPane.showMessageDialog(null, "Insert Mobile no first.");
		    	  }
		    	  else if(mobile_no.getText().matches("[0-9]{5,13}"))
		    	  {
		    		  mob_no=mobile_no.getText();
		    		  try {
		    			  String FILE = "D:/App/print.pdf";
		    			  Document document = new Document( PageSize.A4, 20, 20, 20, 20 );
		    		      PdfWriter.getInstance(document, new FileOutputStream(FILE));
		    		      document.open();
		    		      addTitlePage(document);
		    		      document.close();
		    		      
		    		      if (Desktop.isDesktopSupported()) {
		    		    	    try {
		    		    	        File myFile = new File(FILE);
		    		    	        Desktop.getDesktop().open(myFile);
		    		    	    } catch (IOException ex) {
		    		    	        // no application registered for PDFs
		    		    	    }
		    		    	}
		    		      
		    		    } catch (Exception e1) {
		    		      e1.printStackTrace();
		    		    }
		         
		    	  }
		    	  else
		    	  {
		    		  JOptionPane.showMessageDialog(null, "Insert proper number.");
		    	  }
		      }
		  });


	    pane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), ""));
	    pane3.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), ""));
	    
	    setVisible(true);
	  }
	  public static void main(String args[]) throws SQLException, IOException {
		  new Main();
		  KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0);
          KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
          KeyStroke ctrlTab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, KeyEvent.CTRL_DOWN_MASK);
          Set<KeyStroke> keys = new HashSet<>();
          keys.add(enter);
          keys.add(tab);
          keys.add(ctrlTab);
          KeyboardFocusManager.getCurrentKeyboardFocusManager().setDefaultFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keys);
		  }

		  private static void addTitlePage(Document document)
		      throws DocumentException, MalformedURLException, IOException {
			  document.open();
		    Paragraph preface = new Paragraph();
		    PdfPTable table = new PdfPTable(4); // 3 columns.
		    Image image1 = Image.getInstance("D:\\App\\rect.JPG");
		    Image image2 = Image.getInstance("D:\\App\\dia.JPG");
		    Image image3 = Image.getInstance("D:\\App\\mark.JPG");
		    Image image4 = Image.getInstance("D:\\App\\thy.JPG");
            PdfPCell cell1 = new PdfPCell(new Paragraph("Date: "+ dt1.format(new Date())));
            PdfPCell cell2 = new PdfPCell(new Paragraph(""));
            PdfPCell cell3 = new PdfPCell(new Paragraph("Charges:"));
            PdfPCell cell4 = new PdfPCell(image1);
        
            cell1.setBorder(Rectangle.NO_BORDER);
            cell2.setBorder(Rectangle.NO_BORDER);
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell3.setBorder(Rectangle.NO_BORDER);
            cell4.setBorder(Rectangle.NO_BORDER);
            
            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            
            float[] columnWidths1 = new float[] {20f, 15f,8f,10f};
            table.setWidths(columnWidths1);
            
            addEmptyLine(preface, 4);
            preface.add(table);
            addEmptyLine(preface, 1);
            PdfPTable table2 = new PdfPTable(3);
            PdfPCell tcell1 = new PdfPCell(new Paragraph("Name:"));
            //PdfPCell tcell2 = new PdfPCell(new Paragraph(name.getText()));
            PdfPCell tc = new PdfPCell(image3);
            tc.setBorder(Rectangle.NO_BORDER);
            tc.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tc.setFixedHeight(25f);
            tcell1.setBorder(Rectangle.NO_BORDER);
            tcell1.setFixedHeight(20f);
          //  tcell2.setBorder(Rectangle.NO_BORDER);
          //  tcell2.setFixedHeight(20f);
            table2.addCell(tcell1);
            //table2.addCell(tcell2);
            table2.addCell(tc);float[] columnWidths3 = new float[] {5f, 15f,25f};
            table2.setWidths(columnWidths3);
            preface.add(table2);
            PdfPTable table1 = new PdfPTable(2);
            
            PdfPCell tcell3 = new PdfPCell(new Paragraph("Age:"));
            //PdfPCell tcell4 = new PdfPCell(new Paragraph(age.getText()));
            PdfPCell tcell5 = new PdfPCell(new Paragraph("Tel. No.:"));
            PdfPCell tcell6 = new PdfPCell(new Paragraph(mobile_no.getText()));            
            PdfPCell tcell7 = new PdfPCell(new Paragraph("Ref. by:"));
            PdfPCell tcell8 = new PdfPCell(new Paragraph(""));
            PdfPCell tcell9 = new PdfPCell(new Paragraph("Address:"));
           // PdfPCell tcell10 = new PdfPCell(new Paragraph(address.getText()));
            PdfPCell tcell13 = new PdfPCell(new Paragraph("Diabetes:"));         
            PdfPCell tcell14 = new PdfPCell(image2);
            PdfPCell tcell15 = new PdfPCell(new Paragraph("Thyroid:"));
            PdfPCell tcell16 = new PdfPCell(image4);
            
            
            tcell3.setBorder(Rectangle.NO_BORDER);
            tcell3.setFixedHeight(20f);
           // tcell4.setBorder(Rectangle.NO_BORDER);
           // tcell4.setFixedHeight(20f);
            tcell5.setBorder(Rectangle.NO_BORDER);
            tcell5.setFixedHeight(20f);
            tcell6.setBorder(Rectangle.NO_BORDER);
            tcell6.setFixedHeight(20f);

            tcell7.setBorder(Rectangle.NO_BORDER);
            tcell7.setFixedHeight(20f);
            tcell8.setBorder(Rectangle.NO_BORDER);
            tcell8.setFixedHeight(20f);
            tcell9.setBorder(Rectangle.NO_BORDER);
            tcell9.setFixedHeight(20f);
           // tcell10.setBorder(Rectangle.NO_BORDER);
            //tcell10.setFixedHeight(40f);
            tcell13.setBorder(Rectangle.NO_BORDER);
            tcell13.setFixedHeight(20f);
            tcell14.setBorder(Rectangle.NO_BORDER);
            tcell14.setFixedHeight(20f);
            tcell15.setBorder(Rectangle.NO_BORDER);
            tcell15.setFixedHeight(32f);
            tcell16.setBorder(Rectangle.NO_BORDER);
            tcell16.setFixedHeight(32f);
            
            
            
            table1.addCell(tcell3);
           // table1.addCell(tcell4);
            table1.addCell(tcell5);
            table1.addCell(tcell6);
            table1.addCell(tcell7);
            table1.addCell(tcell8);
            table1.addCell(tcell9);
            //table1.addCell(tcell10);
            table1.addCell(tcell13);
            table1.addCell(tcell14);
            table1.addCell(tcell15);
            table1.addCell(tcell16);
            
            float[] columnWidths = new float[] {5f, 35f};
            table1.setWidths(columnWidths);
            preface.add(table1);
         
           // document.add(table1);

		    // We add one empty line
		    addEmptyLine(preface, 5);
		    preface.add(new Paragraph("Patients Signature"));
		   
		    document.add(preface);
            document.close();
            
		  }
	  
		  private static void addEmptyLine(Paragraph paragraph, int number) {
		    for (int i = 0; i < number; i++) {
		      paragraph.add(new Paragraph(" "));
		    }
}
}
