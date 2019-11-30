package tmx_parser;

import java.util.ArrayList;

//@author omkardev
//github = https://github.com/OmkarDev/TMX-Parser-Java

public class Rect {

	public int x, y, w, h;
	public String objGroup, objName, objType;
	public ArrayList<Property> properties;

	public Rect(int x, int y, int w, int h, String objName, String objType, String objGroup,ArrayList<Property> properties) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.objName = objName;
		this.objGroup = objGroup;
		this.objType = objType;
		this.properties = properties;
	}
	
	public Rect(int x, int y, int w, int h, String objName, String objType, String objGroup) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.objName = objName;
		this.objGroup = objGroup;
		this.objType = objType;
	}

}
