package eu.servertje.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import eu.servertje.filesystem.FileManager;

public class MainWindow extends JFrame implements ListSelectionListener
{
    private JScrollPane sp_parent                       = null;
    private JScrollPane sp_current                      = null;
    private JScrollPane sp_child                        = null;
    
    private DefaultListModel<String> parentListModel    = null;
    private DefaultListModel<String> currentListModel   = null;
    private DefaultListModel<String> childListModel     = null;

    private FileManager manager = new FileManager();
    
    private JList<File> parentList = new JList<>();
    private JList<String> currentList = new JList<>();
    private JList<File> childList = new JList<>();

//    private File defaultHome = new File("/home/ronald");
//    private int index = 0;

    
    public MainWindow() {
        setMinimumSize(new Dimension(450, 300));
        setTitle("File browser");
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel contentPanel = new JPanel();
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new GridLayout(1, 0, 0, 0));
        
        // Initialize Parent pane
        this.parentListModel = new DefaultListModel<>();
        sp_parent = createList(parentListModel, parentList);
        contentPanel.add(sp_parent);
        
        // Initialize current pane
        this.currentListModel = new DefaultListModel<>();
        manager.updateCurrentFiles("/home/ronald", currentListModel);
        currentList.setSelectedIndex(0);
        sp_current = createList(currentListModel, currentList);
        contentPanel.add(sp_current);
        
        // Initialize child pane
        this.childListModel = new DefaultListModel<>();
        sp_child = createList(childListModel, childList);
        contentPanel.add(sp_child);
        
        JPanel footerPanel = new JPanel();
        getContentPane().add(footerPanel, BorderLayout.SOUTH);
        footerPanel.setLayout(new GridLayout(0, 1, 0, 0));
        
        JLabel lblInfo = new JLabel("...");
        lblInfo.setHorizontalAlignment(SwingConstants.LEFT);
        footerPanel.add(lblInfo);
        
        JPanel headerPanel = new JPanel();
        getContentPane().add(headerPanel, BorderLayout.NORTH);
        
        this.setVisible(true);
    }
    
//    private JScrollPane createDefaultScollPane()
//    {
//       return null;
//    }
    
    private JScrollPane createList(DefaultListModel model, JList list)
    {
        list = new JList<File>(model);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        
        JScrollPane pane = new JScrollPane(list);
        pane.setOpaque(true);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return pane;
    }
    
    public void addMoreData()
    {
        for (int i = 0; i < 10; i++)
        {
            File f = new File("abababa: " + i);
            currentListModel.removeElement(f);
        }
    }
    
   public void fillCurrentList()
   {
//       this.currentFiles = defaultHome.listFiles();
//       for (File f : this.currentFiles)
//       {
//           this.currentListModel.addElement(f.getName());
//       }
   }

   private void fillChildList(File file)
   {
//       this.childFiles = file.listFiles();
//       for (File f : this.childFiles)
//       {
//           this.currentListModel.addElement(f.getName());
//       }
   }
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getValueIsAdjusting() == false)
        {
            JList list = (JList) e.getSource();
            if (list != null)
            {
                int index = list.getSelectedIndex();
                DefaultListModel m = (DefaultListModel) list.getModel();
//                System.out.println(m.elementAt(index));
                String location = m.elementAt(index).toString();
//                this.manager.updateParentFiles(, this.parentListModel);
                this.manager.updateChildFiles(location, this.childListModel);
            }
        }
    }
}
