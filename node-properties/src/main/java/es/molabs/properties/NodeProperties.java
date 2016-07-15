/**
 * Copyright (C) 2016 Luis Moral Guerrero <luis.moral@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.molabs.properties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import es.molabs.properties.node.Node;
import es.molabs.properties.node.TreeNode;
import es.molabs.properties.node.ValueNode;

public class NodeProperties implements Serializable, Cloneable
{
	private static final long serialVersionUID = 4085676599636757761L;
	
	private final static String SEPARATOR_CHARACTER = ".";	
	
	private int nodes;
	private Properties properties = null;
	
	private TreeNode root = null;
	private transient Map<String, String> cache = null;	
	
	public NodeProperties()
	{		
		this(0);
	}	
	
	public NodeProperties(int nodes)
	{
		properties = new Properties();
		
		this.nodes = nodes;
		
		root = new TreeNode("ROOT");
		cache = new HashMap<String, String>();		
	}
	
	public void load(NodeProperties properties)
	{
		load(properties.asProperties());
	}
	
	public void load(Map<String, String> properties)
	{
		this.properties.putAll(properties);
		
		if (nodes > 0) processProperties();
	}
	
	public void load(Properties properties)
	{
		this.properties.putAll(properties);
		
		if (nodes > 0) processProperties();
	}
	
	public void load(URL url) throws IOException
	{
		InputStream inputStream = url.openStream();
		
		try
		{
			load(inputStream);
		}
		finally
		{
			inputStream.close();
		}
	}
	
	public void load(InputStream inStream) throws IOException
	{
		this.load(inStream, StandardCharsets.ISO_8859_1.name());
	}
	
	public void load(InputStream inStream, String encoding) throws IOException
	{
		properties.load(new BufferedReader(new InputStreamReader(inStream, encoding)));
		
		if (nodes > 0) processProperties();
	}
	
	public void load(Reader reader) throws IOException
	{
		properties.load(reader);
		
		if (nodes > 0) processProperties();
	}
	
	public void loadFromXML(InputStream in) throws IOException
	{
		properties.loadFromXML(in);
		
		if (nodes > 0) processProperties();
	}
	
	public Enumeration<?> propertyNames()
	{
		return properties.propertyNames();
	}
	
	public Set<String> stringPropertyNames()
	{
		return properties.stringPropertyNames();
	}
	
	public Object setProperty(String key, String value)
	{
		Object result = properties.setProperty(key, value);
		
		if (nodes > 0) processProperties();
		
		return result;
	}
	
	public void store(OutputStream out, String comments) throws IOException
	{
		properties.store(out, comments);
	}
	
	public void store(Writer writer, String comments) throws IOException
	{
		properties.store(writer, comments);
	}
	
	public void storeToXML(OutputStream os, String comment) throws IOException
	{
		properties.storeToXML(os, comment);		
	}
	
	public void storeToXML(OutputStream os, String comment, String encoding) throws IOException
	{
		properties.storeToXML(os, comment, encoding);
	}
	
	protected void processProperties()
	{
		// Clears the cache
		clearCache();
		
		// Process the loaded properties		
		Set<String> keySet = properties.stringPropertyNames();		
		String[] splitedKey = null;
		String key = null;
		
		TreeNode treeNode = root;
		Node currentNode = null;		
		
		// For each key
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext())
		{
			key = iterator.next();
			
			treeNode = root;
			currentNode = null;					
			
			// Splits the key by nodes
			splitedKey = StringUtils.split(key, SEPARATOR_CHARACTER, this.nodes+1);			
			
			// For each node of the splited key
			for (int i=0; i<splitedKey.length; i++)
			{
				currentNode = treeNode.getNode(splitedKey[i]);
						
				// If it does not exists
				if (currentNode == null)
				{
					// If its the last node then is a key/value pair
					if (i+1 == splitedKey.length)
					{
						currentNode = new ValueNode(splitedKey[i], properties.getProperty(key));
					}
					// Else is a tree node
					else
					{
						currentNode = new TreeNode(splitedKey[i]);
					}
					
					// Adds it to the level
					treeNode.addNode(currentNode);				
				}
				
				if (currentNode instanceof TreeNode)
				{
					treeNode = (TreeNode) currentNode;
				}
			}	
		}
	}
	
	public String getProperty(String key)
	{
		// If there is less than one node then its a standard properties file
		if (nodes < 1) return properties.getProperty(key);
		
		// Gets the value from the cache
		String value = cache.get(key);
		
		// If there is no value for this key
		if (value == null && !cache.containsKey(key))
		{
			// Tries to to find it
			value = findKeyValue(key);
			
			// Puts the value in the cache
			cache.put(key, value);
		} 		
		
		return value;
	}
	
	public void clearCache()
	{
		cache.clear();
	}
	
	public void clear()
	{
		clearCache();
		
		root.clear();
		properties.clear();
	}
	
	public Properties asProperties()
	{
		return properties;
	}
	
	public Object clone()
	{
		NodeProperties clone = new NodeProperties(nodes);
		clone.load(properties);
		
		return clone;
	}
	
	private String findKeyValue(String key)
	{
		String[] splitedKey = StringUtils.split(key, SEPARATOR_CHARACTER, nodes+1);
		
		return root.getValue(splitedKey);
	}
}