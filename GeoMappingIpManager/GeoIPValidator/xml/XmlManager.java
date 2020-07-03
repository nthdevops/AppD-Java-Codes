package xml;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import strings.IpStrings;

public class XmlManager {
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
    private Document doc;
    private NodeList elementList;
    
    public void setDbFactory() {
		this.dbFactory = DocumentBuilderFactory.newInstance();
	}
    
    public void setdBuilder() {
    	try {
    		this.dBuilder = dbFactory.newDocumentBuilder();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

    public void setDoc(String path) {
    	try {
			this.doc = dBuilder.parse(new File(path));
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    public void setElementList(String tag) {
    	this.elementList = doc.getDocumentElement().getElementsByTagName(tag);
    }
    
    public Node getMappingNode(int index) {
    	Node node = elementList.item(index);
    	return node;
    }
    
    public int getListSize() {
    	return elementList.getLength();
    }
    
    public String getNodeFromIp(int nodeIndex) {
		return getMappingNode(nodeIndex).getChildNodes().item(1).getAttributes().getNamedItem("from").getNodeValue().toString();
	}
	
	public String getNodeToIp(int nodeIndex) {
		return getMappingNode(nodeIndex).getChildNodes().item(1).getAttributes().getNamedItem("to").getNodeValue().toString();
	}
	
	public String getNodeCity(int nodeIndex) {
		return getMappingNode(nodeIndex).getChildNodes().item(3).getAttributes().getNamedItem("city").getNodeValue().toString();
	}
	
	public String getNodeFromIp(Node node) {
		return node.getChildNodes().item(1).getAttributes().getNamedItem("from").getNodeValue().toString();
	}
	
	public String getNodeToIp(Node node) {
		return node.getChildNodes().item(1).getAttributes().getNamedItem("to").getNodeValue().toString();
	}
	
	public String getNodeCity(Node node) {
		return node.getChildNodes().item(3).getAttributes().getNamedItem("city").getNodeValue().toString();
	}
    
	public ArrayList<String> getAllFromIPs() {
    	ArrayList<String> ips = new ArrayList<String>();
    	String from;
    	for(int i = 0; i < this.elementList.getLength(); i++) {
        	from = getNodeFromIp(i);
        	ips.add(from);
        }
    	return ips;
    }

	public ArrayList<String> getAllToIPs() {
    	ArrayList<String> ips = new ArrayList<String>();
    	String to;
    	for(int i = 0; i < this.elementList.getLength(); i++) {
        	to = getNodeToIp(i);
        	ips.add(to);
        }
    	return ips;
    }
	
	public boolean ipMapped(String ip) {
    	for(int i = 0; i < this.elementList.getLength(); i++) {
    		String from = getNodeFromIp(i);
    		String to = getNodeToIp(i);
    		if(IpStrings.ipIn(ip, from, to)) {
    			return true;
    		}
        }
    	return false;
    }

	public String getCityByIp(String ip) {
		if(ip == null)
			return null;
    	for(int i = 0; i < this.elementList.getLength(); i++) {
    		if(IpStrings.ipIn(ip, this.getNodeFromIp(i), this.getNodeToIp(i))) {
        		return getNodeCity(i);
        	}
        }
    	return null;
    }
	
	public ArrayList<String> getAllCities() {
    	ArrayList<String> ips = new ArrayList<String>();
    	String city;
    	for(int i = 0; i < this.elementList.getLength(); i++) {
        	city = getNodeCity(i);
        	ips.add(city);
        }
    	return ips;
    }
	
	public XmlManager(String path) {
		setDbFactory();
    	setdBuilder();
    	setDoc(path);
    	setElementList("mapping");
	}
}