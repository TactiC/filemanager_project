package eu.servertje.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * NOTE: this class only works (for now) with unix based filesystems.
 */
public class FsNode implements Node
{
    private Path path = null;
    private Node parent = null;
    private List<Node> children = null;
    
    /**
     * Constructor for FsNode class
     * @param path 
     * @param storeChilderen is used to indicate wheter or not childeren have to be fetched for this node.
     * 
     */
    public FsNode(String path, boolean storeChilderen)
    {
        this.path = Paths.get(path);
        if (storeChilderen)
            storeChilderen();
    }
    
    public FsNode(Path path, boolean storeChilderen)
    {
        this.path = path;
        if (storeChilderen)
            storeChilderen();
    }
    
    public String getPathString()
    {
        return this.path.toString();
    }
    
    @Override
    public Node getParent()
    {
        return this.parent;
    }
    
    @Override
    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    @Override
    public Node getChild(String name)
    {
        Node child = null;
        for (Node n : this.children)
        {
            if (n.toString().equals(name))
            {
                child = n;
                break;
            }
        }
        return child;
    }

    /**
     * Returns the first child node in the list of children
     * and null if there are no children.
     * TODO: What to do if there are no children?
     */
    @Override
    public Node getChild()
    {
        Node child = null;
        if ( (this.children != null) && (this.children.size() > 0) )
            child = this.children.get(0);
        return child;
    }
    

    private void storeChilderen()
    {
        this.children = new ArrayList<Node>();
            
        if (this.isFile())
        {
            // Not yet implemented....
        }
        else
        {
            try (DirectoryStream<Path> ds = Files.newDirectoryStream(
                    FileSystems.getDefault().getPath(this.path.toString())))
            {
                for (Path p  : ds)
                {
                    FsNode node = new FsNode(p, false);
                    node.setParent(this);
                    this.children.add(node);
                }
            }
            catch (AccessDeniedException ade)
            {
                // Too bad, continue...
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Node getSibling()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isRoot()
    {
        return this.path.getNameCount() == 0 ? true : false;
    }

    @Override
    public boolean hasParent()
    {
        return this.parent != null;
    }

    @Override
    public boolean hasChildren()
    {
        return this.children != null;
    }

    @Override
    public boolean hasSibling()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isFile()
    {
        File file = this.path.toFile();
        return file.isFile();
    }

    @Override
    public boolean isDirectory()
    {
        File file = this.path.toFile();
        return file.isDirectory();
    }
    
    @Override
    public String toString()
    {
        return this.path.getFileName().toString();
    }

    @Override
    public List<Node> getChildren()
    {
        if (this.children == null)
            storeChilderen();
        return this.children;
    }

    @Override
    public Path getPath()
    {
        return this.path;
    }

}
