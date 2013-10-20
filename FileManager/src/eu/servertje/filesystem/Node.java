package eu.servertje.filesystem;

import java.nio.file.Path;
import java.util.List;

public interface Node
{
    Node getParent();
    void setParent(Node parent);
    Node getChild(String name);
    List<Node> getChildren();
//    void storeChildren();
    Node getSibling();
//    String getPathString();
    Path getPath();
    boolean isRoot();
    boolean hasParent();
    boolean hasChildren();
    boolean hasSibling();
    boolean isFile();
    boolean isDirectory();
    //.....
    Node getChild();
}
