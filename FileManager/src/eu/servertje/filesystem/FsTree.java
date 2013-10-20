package eu.servertje.filesystem;

import java.nio.file.Path;
import java.util.List;

public class FsTree
{
    private Node root;
    private Node current;
    private Path currentPath;
    private String defaultCurrent = "home";
    
    public FsTree()
    {
        this.root = new FsNode("/", true);
        this.current = this.root.getChild(defaultCurrent);
        this.currentPath = this.root.getPath().resolve(this.current.getPath());
        System.out.printf("Current path is %s %n", this.currentPath);
    }
    
    public Node getNode()
    {
        return this.root;
    }
    
    /**
     * Move towards the root of the tree
     */
    public void moveUp()
    {
        if (this.current.hasParent())
        {
            
        }
    }
    
    /**
     * Move towards the leafs of the tree
     */
    public void moveDown()
    {
        // TODO: This is a realy ugly solution, please solve this soon!!
        if (this.current.hasChildren() == false)
        {
            this.current.getChildren();            
        }
        if (this.current.hasChildren())
        {
            Node node = this.current.getChild();
            this.currentPath = this.currentPath.resolve(node.getPath());
            System.out.printf("Current path is %s %n", this.currentPath);
            this.current = node;
        }
    }
   
    public void listChilderen()
    {
        System.out.println("Childeren list:");
        List<Node> list = this.current.getChildren();
        for (Node n : list)
        {
            System.out.println(n);
        }
    }
    
    public void listCurrent()
    {
        if (this.current.hasParent())
        {
            System.out.println("Current list:");
            Node node = this.current.getParent();
            List<Node> list = node.getChildren();
            for (Node n : list)
            {
                System.out.println(n);
            }
        }
    }
    
    public void listParent()
    {
        Node node;
        if (this.current.hasParent())
        {
            node = this.current.getParent();
            if (node.hasParent())
            {
                System.out.println("Parent list:");
                node = this.current.getParent();
                List<Node> list = node.getChildren();
                for (Node n : list)
                {
                    System.out.println(n);
                }
            }
        }
    }
    
    
    public void listSiblings()
    {
    }
    
    public void listParentSiblings()
    {
        
    }
    
    public void listChildSiblings()
    {
        
    }
}
