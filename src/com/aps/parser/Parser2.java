package com.aps.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.aps.ast.IAST;

public class Parser2 {

	public Parser2(){};

	public IAST parse(Node n) throws Exception{
		switch (n.getNodeType()) {

		case Node.DOCUMENT_NODE:{
			Document d=(Document) n;
			return this.parse(d);
		}

		case Node.ELEMENT_NODE:{
			Element e=(Element) n;
			NodeList nl= e.getChildNodes();
			String name = e.getTagName();

			switch(name){
			
			case "program":{

			}
			case "classes":{
				
			}
			case "locals":{
				
			}
			case "instructionlist":{
				
			}
			
			default:{
				String msg = "Unknown element name: " + name;
				throw new Exception(msg);
			}
			}
		}
		
		default:{
			String msg = "Unknown node type: " + n.getNodeName();
			throw new Exception(msg);
		}
		
		}
	}
}

