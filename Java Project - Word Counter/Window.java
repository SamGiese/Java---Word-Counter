import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;



/**
 * Creates the window along with the button to press to choose the file.
 * @author Sam Giese
 *
 */
public class Window extends JFrame {

	    
	    JButton fileButton = new JButton( "Select Text File");
	    
	    public Window() {
	        this.setTitle("Word Counter");
	        this.setBounds( 200, 300, 250, 150);
	        this.getContentPane().setLayout(null);
	        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
	        
	       
	        
	        this.fileButton.setBounds(45, 20, 150, 30);
	        this.getContentPane().add(fileButton);
	        this.fileButton.addActionListener( new FileButtonListener());
	        
	      //Create the JFrame to display the table
	        JFrame mainFrame = new JFrame();
	        mainFrame.setTitle("Word Counter");
	        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        mainFrame.setSize(520, 520);

	        //Then a panel to keep our Main frame available to display other contents
	        JPanel myPanel = new JPanel();
	        myPanel.setBounds(mainFrame.getBounds());
	        myPanel.setBackground(Color.DARK_GRAY);
	        myPanel.setVisible(true);

	        //Add the panel to the frame
	        mainFrame.add(myPanel);
	        
	        
	    }
	    
	    /**
	     * Allows the user to choose the file. It will then call the readCount function
	     * to count and sort the words.
	     * @author Sam Giese
	     *
	     */
	    public class FileButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed( ActionEvent e) {
	            System.out.println("hit the file button");
	            JFileChooser chooser = new JFileChooser();
	            chooser.setFileFilter( new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
	            System.out.println("I created the file chooser");
	            int chooserSuccess = chooser.showOpenDialog( null);
	            System.out.println("I opended the file chooser, it returned " + chooserSuccess);
	            if( chooserSuccess == JFileChooser.APPROVE_OPTION) {
	                File chosenFile = chooser.getSelectedFile();
	                // Pass this file to your function
	                System.out.println("You chose the file " + chosenFile.getAbsolutePath());
	                System.out.println("You chose the file " + chosenFile.getName());
	                System.out.println("Now counting through the file:");
	                
	                String file = chosenFile.toString();
	                
	                //Calling the ReadCount function for the file
	        		readCount(file);
	                
	        		
	        		
	        		
	        		
	            }
	            else {
	                System.out.println("You hit cancel");
	            }
	        }
	        
	        /**
	    	*Reads through the given text file, counts and stores occurances of words, and sorts that list.
	    	* @param file - takes in a string which in this case is the text file.
	    	*/
	    	public void readCount(String file) {
	    		
	    		file.replaceAll("," , ".");
	    		file.replaceAll("[A-Z]", "[a-z]");
	    		
	    		//using TreeMap to store, and sort words
	    		//Words are sorted from capital letters alphabetical first, then lower case alphabetical
	    		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
	    		
	    		
	    		Scanner txtFile;
	    		
	    		try {
	    			//using scanner to read the file
	    			txtFile = new Scanner(new File(file));
	    			
	    			
	    			
	    			//enter loop reading through text file
	    			while (txtFile.hasNext()) {
	    				//word is current word
	    				String word = txtFile.next();
	    				
	    				//Making upper case letters lower case, and removing all punctuation
	    				word = word.toLowerCase();
	    				word = word.replaceAll("[^a-zA-Z ]", "");
	    				
	    				
	    				//if the list of words already has an instance of the current word
	    				if(map.containsKey(word)) {
	    					//add 1 to word count if it has existed before
	    					int count = map.get(word) + 1;
	    					map.put(word, count);
	    				}
	    				else {
	    					//if the list doesn't have an instance of the word, put new instance with count of 1
	    					map.put(word, 1);
	    				}
	    				
	    			}
	    			//Close the file
	    			txtFile.close();
	    			//call on the write function to write out the list
	    			write(map);
	    			showList(map);
	    			
	    			
	    			
	    		} catch (FileNotFoundException e) {
	    			System.out.println("Something went wrong with finding the given file. Please try again.");
	    		}
	    		
	    	}//end readcount
	    	
	        
	        
	    	/**
	    	 * prints out the list of words.
	    	 * @param map - Takes in a TreeMap which it will print.
	    	 */
	    	public void write(TreeMap<String, Integer> map) {
	    		//Print the TreeMap of words
	    		for (Map.Entry<String, Integer> entry: map.entrySet()) {
	    			System.out.println(entry);
	    		}
	    	}
	    	
	    	
	    	/**
	    	 * Opens a JFrame that shows the contents of a given TreeMap.
	    	 * @param map - the TreeMap to be shown in the window.
	    	 */
	    	public void showList(TreeMap<String, Integer> map) {
	    		
	    	    //Creating the table & panel
	    	    JFrame mainFrame = new JFrame();
	    	    mainFrame.setTitle("Word Count");
	    	    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    	    mainFrame.setSize(520, 520);
	    	    JPanel myPanel = new JPanel();
	    	    myPanel.setBounds(mainFrame.getBounds());
	    	    myPanel.setBackground(Color.DARK_GRAY);
	    	    myPanel.setVisible(true);

	    	    //Adding panel to the frame
	    	    mainFrame.add(myPanel);

	    	    //Column names
	    	    String[] columns = new String[] {"Word", "Count"};

	    	    //Setting a default table
	    	    DefaultTableModel defaultModel = new DefaultTableModel(columns, 0);

	    	    //Creating table with default table properties
	    	    JTable myTable = new JTable(defaultModel);

	    	    //Filling table with TreeMap contents
	    	    for (Map.Entry<String, Integer> entry : map.entrySet()) {
	    	        defaultModel.addRow(new Object[] {entry.getKey(), entry.getValue()});
	    	    }

	    	    //Adding table to frame & making visible
	    	    myPanel.add(new JScrollPane(myTable));
	    	    mainFrame.setVisible(true);
	    	
	    	}
	
	
	    }
}
