package org.apache.jena.example.helloworld;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;








class XmlJTree extends JTree {

  DefaultTreeModel dtModel = null;

  public XmlJTree(String filePath) {
    if (filePath != null)
      setPath(filePath);
  }

  public void setPath(String filePath) {
    Node root = null;
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.parse(filePath);
      root = (Node) doc.getDocumentElement();
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, "Can't parse file", "Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (root != null) {
      dtModel = new DefaultTreeModel(builtTreeNode(root));
      this.setModel(dtModel);
      
    }
  }

  private DefaultMutableTreeNode builtTreeNode(Node root) {
    DefaultMutableTreeNode dmtNode;

    dmtNode = new DefaultMutableTreeNode(root.getBaseURI()+root.getNodeName()+"||"+root.getTextContent()/*getTextContent()/*.getNodeName()*/+"\n");
    NodeList nodeList = ((org.w3c.dom.Node) root).getChildNodes();
    for (int count = 0; count < nodeList.getLength(); count++) {
      Node tempNode = (Node) nodeList.item(count);

      
     System.out.println(tempNode+"-"+tempNode.getNodeName());
      if (((org.w3c.dom.Node) tempNode).getNodeType() == Node.ELEMENT_NODE) {
        if (((org.w3c.dom.Node) tempNode).hasChildNodes()) {
          dmtNode.add(builtTreeNode(tempNode));
        }
      }
    }
    return dmtNode;
    
  }
  


}