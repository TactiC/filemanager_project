package eu.servertje.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import eu.servertje.filesystem.FsTree;
import eu.servertje.filesystem.FsView;

public class MainWindow extends JFrame implements ListSelectionListener
{
    private JScrollPane sp_parent                       = null;
    private JScrollPane sp_current                      = null;
    private JScrollPane sp_child                        = null;
    
    private DefaultListModel<String> parentListModel    = null;
    private DefaultListModel<String> currentListModel   = null;
    private DefaultListModel<String> childListModel     = null;

    private JList<String> parentList    = new JList<>();
    private JList<String> currentList   = new JList<>();
    private JList<String> childList     = new JList<>();
    
    private FsTree tree = new FsTree();
    
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
        currentList.setSelectedIndex(0);
//        currentList.transferFocus();
        sp_current = createList(currentListModel, currentList);
        sp_current.getInputMap().put(KeyStroke.getKeyStroke("L"), "left");
        sp_current.getActionMap().put("left", new leftAction());

//        sp_current.requestFocusInWindow();
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
    
    
    private JScrollPane createList(DefaultListModel<String> model, JList<String> list)
    {
        list = new JList<String>(model);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        
        JScrollPane pane = new JScrollPane(list);
        pane.setOpaque(true);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return pane;
    }
    
    public void fill()
    {
        FsView view = tree.getView();
        fillParentList(view);
        fillCurrentList(view);
//        fillChildList(view);
    }
    
    
   private void fillParentList(FsView view)
   {
        for (String string : view.getParentList())
        {
           this.parentListModel.addElement(string);
        }
   }

   private void fillCurrentList(FsView view)
   {
        for (String string : view.getCurrentList())
        {
           this.currentListModel.addElement(string);
        }
        this.validate();
        this.currentList.ensureIndexIsVisible(0);
        this.currentList.setSelectedIndex(1);
        this.validate();
   }

   private void fillChildList(FsView view)
   {
        for (String string : view.getChildList())
        {
           this.childListModel.addElement(string);
        }
   }
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getValueIsAdjusting() == false)
        {
            Object obj = e.getSource();
            if (obj instanceof JList)
            {
                // If it is a JList is is of type JList<String>
                // Trust me, I know these things...
                @SuppressWarnings("unchecked")
                JList<String> list = (JList<String>) obj;
                if (list != null)
                {
                    int index = list.getSelectedIndex();
                    DefaultListModel<String> model = (DefaultListModel<String>) list.getModel();
                    System.out.println(model.elementAt(index));
                    String location = model.elementAt(index).toString();
                    tree.listChilderen(location);
                    if (model == this.currentListModel)
                        model = this.childListModel;
                    FsView.updateChilderenList(tree.listChilderen(location), model);
                }
            }
        }
    }
    
    private class leftAction extends AbstractAction
    {
        public leftAction()
        {
            
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("left action");
        }
    }
}
