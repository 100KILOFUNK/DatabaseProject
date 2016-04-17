
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import jxl.write.WriteException;


public class GUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DBConnect con;
	private Container frame;
	private JTabbedPane tabbedPane;
	private JTextField highPrice;
	private JTextField lowPrice;
	private JTextField r_id;
	private JTextField cabinNr;
	private JTextField civicNr;
	private JTextField groupSize;
	private JTextField cabinPrice;
	private JTextField eqPrice;
	private JTextField startDate;
	private JTextField endDate;
	private JList<String>list;
	private DefaultListModel<String>model;
	private JList<String>priceList;
	private DefaultListModel<String>priceModel;
	private ArrayList<String> cabins;
	private ArrayList<reservation> res;
	private float[] myArray;
	private WriteExcel test;
	public GUI() throws WriteException, IOException{
		con=new DBConnect();
		test=new WriteExcel();
		buildTabs();
		initiate();
	}
	private void initiate(){
		this.frame=getContentPane();
		setTitle("Media Library");
		setSize(800,800);
		setResizable(false);
		setLocationRelativeTo(null);
		this.frame.add(this.tabbedPane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void pricePanel(JPanel panel){
		panel.setLayout(new GridLayout(1,2));
		priceModel=new DefaultListModel<String>();
		priceList=new JList<String>(priceModel);
		cabins=new ArrayList<String>();
		cabins=con.getCabins();
		for(int i=0;i<cabins.size();i++){
			priceModel.addElement(cabins.get(i));
		}
		priceList.setModel(priceModel);
		priceList.addMouseListener(priceMouseListener);
		panel.add(priceList);
	}
	public void cabinPanel(JPanel panel){
		panel.setLayout(new GridLayout(1,2));
		model=new DefaultListModel<String>();
		cabins=new ArrayList<String>();
		list=new JList<String>(model);
		list.addMouseListener(reservationMouseListener);
		cabins=con.getReservation();
		for(int i=0;i<cabins.size();i++){
			model.addElement(cabins.get(i));
		}
		list.setModel(model);
		panel.add(list);
	}
	public void changePanel(JPanel panel){
		highPrice=new JTextField(25);
		highPrice.setBorder(BorderFactory.createTitledBorder("High season Price"));
		lowPrice=new JTextField(25);
		lowPrice.setBorder(BorderFactory.createTitledBorder("Low Season Price"));
		panel.add(highPrice);
		panel.add(lowPrice);
		JButton cButton=new JButton("Change Price");
		cButton.addActionListener(new ButtonListener());
		panel.add(cButton);
	}
	public void reservation(JPanel panel){
		r_id=new JTextField(25);
		r_id.setBorder(BorderFactory.createTitledBorder("Reservation ID"));
		cabinNr=new JTextField(25);
		cabinNr.setBorder(BorderFactory.createTitledBorder("Cabin Number"));
		civicNr=new JTextField(25);
		civicNr.setBorder(BorderFactory.createTitledBorder("Civic Number"));
		groupSize=new JTextField(25);
		groupSize.setBorder(BorderFactory.createTitledBorder("Group Size"));
		cabinPrice=new JTextField(25);
		cabinPrice.setBorder(BorderFactory.createTitledBorder("Cabin Price"));
		eqPrice=new JTextField(25);
		eqPrice.setBorder(BorderFactory.createTitledBorder("Equipment Price"));
		startDate=new JTextField(25);
		startDate.setBorder(BorderFactory.createTitledBorder("Start Date"));
		endDate=new JTextField(25);
		endDate.setBorder(BorderFactory.createTitledBorder("End Date"));
		panel.add(r_id);
		panel.add(cabinNr);
		panel.add(civicNr);
		panel.add(groupSize);
		panel.add(cabinPrice);
		panel.add(eqPrice);
		panel.add(startDate);
		panel.add(endDate);
		JButton cButton=new JButton("Generate Excel Document");
		cButton.addActionListener(new ButtonListener());
		panel.add(cButton);
	}
	public void startPanel(JPanel panel){
		JButton aButton=new JButton("View Reservations");
		JButton bButton=new JButton("Change Prices");
		aButton.addActionListener(new ButtonListener());
		bButton.addActionListener(new ButtonListener());
		panel.add(aButton);
		panel.add(bButton);
	}
	private void buildTabs(){
		ImageIcon icon=new ImageIcon ("java-swing-tutorial.JPG");
		this.tabbedPane=new JTabbedPane();
		JPanel startPanel=new JPanel();
		startPanel(startPanel);
		JPanel reservationPanel=new JPanel();
		reservation(reservationPanel);
		JPanel jPanel=new JPanel();
		cabinPanel(jPanel);
		jPanel.add(reservationPanel);
		JPanel pricePanel=new JPanel();
		pricePanel(pricePanel);
		JPanel changePanel=new JPanel();
		changePanel(changePanel);
		pricePanel.add(changePanel);
		tabbedPane.addTab("Start", icon,startPanel,"Tab 1");
		tabbedPane.addTab("Reservations",icon,jPanel,"Tab 2");
		tabbedPane.addTab("Change Prices",icon,pricePanel,"Tab 3");
		
		
		
	}
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			if(e.getActionCommand().equals("View Reservations")){
				tabbedPane.setSelectedIndex(1);
			}
			if(e.getActionCommand().equals("Change Prices")){
				tabbedPane.setSelectedIndex(2);
			}
			if(e.getActionCommand().equals("Change Price")){
				myArray[0]=Float.parseFloat(highPrice.getText());
				myArray[1]=Float.parseFloat(lowPrice.getText());
				con.setPrice(myArray, priceList.getSelectedIndex());
				
				highPrice.setText("");
				lowPrice.setText("");
			}
			if(e.getActionCommand().equals("Generate Excel Document")){				
				try {
					String file="C:/temp/"+con.getFullName(list.getSelectedIndex())+".xls";
					test.setOutputFile(file);
					if(con.getSeason(list.getSelectedIndex()) == 1){
						test.writeWinter(con.getFullName(list.getSelectedIndex()),con.getAReservation(list.getSelectedIndex()),con.getSeason(list.getSelectedIndex()),con.getWinterEq(list.getSelectedIndex()));	
					}
					if(con.getSeason(list.getSelectedIndex()) == 0){
						test.writeSummer(con.getFullName(list.getSelectedIndex()),con.getAReservation(list.getSelectedIndex()),con.getSeason(list.getSelectedIndex()),con.getSummerEq(list.getSelectedIndex()));	
					}
				} catch (WriteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				r_id.setText("");
				cabinNr.setText("");
				civicNr.setText("");
				groupSize.setText("");
				cabinPrice.setText("");
				eqPrice.setText("");
				startDate.setText("");
				endDate.setText("");
			}
		}
	}
	MouseListener priceMouseListener = new MouseAdapter() {
	      public void mouseClicked(MouseEvent e) {
	        if (e.getClickCount() == 2) {
	        	myArray=new float[2];
	        		myArray=con.getPrice(priceList.getSelectedIndex());
	        		highPrice.setText(Float.toString(myArray[0]));
	        		lowPrice.setText(Float.toString(myArray[1]));
	        	}
	          }
	};
	MouseListener reservationMouseListener = new MouseAdapter() {
	      public void mouseClicked(MouseEvent e) {
	        if (e.getClickCount() == 2) {
	        		res=con.getReservations();
	        		r_id.setText(Integer.toString(res.get(list.getSelectedIndex()).getR_id()));
					cabinNr.setText(Integer.toString(res.get(list.getSelectedIndex()).getCabinNr()));
					civicNr.setText(Long.toString(res.get(list.getSelectedIndex()).getCivicNr()));
					groupSize.setText(Integer.toString(res.get(list.getSelectedIndex()).getGroupSize()));
					cabinPrice.setText(Float.toString(res.get(list.getSelectedIndex()).getCabinPrice()));
					eqPrice.setText(Float.toString(res.get(list.getSelectedIndex()).getEqPrice()));
					startDate.setText(res.get(list.getSelectedIndex()).getStartDate().toString());
					endDate.setText(res.get(list.getSelectedIndex()).getEndDate().toString());
	        	}
	          }
	};

}
