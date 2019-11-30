package tmx_parser;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.*;

import org.w3c.dom.*;

//@author omkardev
//github = https://github.com/OmkarDev/TMX-Parser-Java

public class TMX {

	String tmx_path;
	String tsx_path = "";

	int tilewidth;
	int tileheight;
	int mapwidth;
	int mapheight;
	public int tiles[][][];
	public ArrayList<Rect> objs = new ArrayList<Rect>();
	String[] tokens;
	String image_Path;

	public TMX(String path) throws Exception {
		this.tmx_path = path;
		File file = new File(path);
		tsx_path += file.getParent() + "/";
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		Element map = doc.getDocumentElement();
		mapwidth = Integer.parseInt(map.getAttribute("width"));
		mapheight = Integer.parseInt(map.getAttribute("height"));
		tilewidth = Integer.parseInt(map.getAttribute("tilewidth"));
		tileheight = Integer.parseInt(map.getAttribute("tileheight"));
		NodeList layerList = doc.getElementsByTagName("layer");
		Node tileset = map.getElementsByTagName("tileset").item(0);
		Node source = tileset.getAttributes().item(1);
		tsx_path += source.getNodeValue();
		tiles = new int[layerList.getLength()][mapheight][mapwidth];
		for (int i = 0; i < layerList.getLength(); i++) {
			Node layers = layerList.item(i);
			Element layer = (Element) layers;
			String list = layer.getElementsByTagName("data").item(0).getTextContent();
			tokens = list.split(",");
			for (int j = 0; j < mapheight; j++) {
				for (int k = 0; k < mapwidth; k++) {
					tiles[i][j][k] = Integer.parseInt(tokens[k + j * mapwidth].trim());
				}
			}
		}

		NodeList objectList = doc.getElementsByTagName("objectgroup");
		for (int i = 0; i < objectList.getLength(); i++) {
			Node Node_objectgroup = objectList.item(i);
			Element objectgroup = (Element) Node_objectgroup;
			String name = objectgroup.getAttribute("name");
			NodeList objects = objectgroup.getElementsByTagName("object");
			for (int j = 0; j < objects.getLength(); j++) {
				Element obj = (Element) objects.item(j);
				int x = Integer.parseInt(obj.getAttribute("x"));
				int y = Integer.parseInt(obj.getAttribute("y"));
				int w = 0, h = 0;
				if (obj.getAttribute("width") != "") {
					w = Integer.parseInt(obj.getAttribute("width"));
				}
				if (obj.getAttribute("height") != "") {
					h = Integer.parseInt(obj.getAttribute("height"));
				}
				String objType = obj.getAttribute("type");
				String objName = obj.getAttribute("name");
				NodeList propertiesNodelist = obj.getElementsByTagName("properties");
				ArrayList<Property> properties = new ArrayList<Property>();
				if (propertiesNodelist.getLength() > 0) {
					for (int k = 0; k < propertiesNodelist.getLength(); k++) {
						Node propertyNode = propertiesNodelist.item(k);
						Element propertyElement = (Element) propertyNode;
						NodeList propertyNodeList = propertyElement.getElementsByTagName("property");
						for (int l = 0; l < propertyNodeList.getLength(); l++) {
							String propName = "", propType = "", propValue = "";
							Element property = (Element) propertyNodeList.item(l);
							propName = property.getAttribute("name");
							propType = property.getAttribute("type");
							propValue = property.getAttribute("value");
							properties.add(new Property(propName, propType, propValue));
						}
					}
					objs.add(new Rect(x, y, w, h, objName, objType, new String(name), properties));
				} else {
					objs.add(new Rect(x, y, w, h, objName, objType, new String(name)));
				}
			}
		}
		image_Path = TSX();
	}

	String TSX() throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		File file = new File(tsx_path);
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();
		Element root = doc.getDocumentElement();
		String imagePath = file.getParent() + "/";
		imagePath += root.getElementsByTagName("image").item(0).getAttributes().item(1).getNodeValue();
		return imagePath;
	}

	public String getImagePath() {
		return image_Path;
	}
}
