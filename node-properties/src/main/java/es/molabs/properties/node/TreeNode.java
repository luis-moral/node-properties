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
package es.molabs.properties.node;

import java.util.Arrays;
import java.util.HashMap;

public class TreeNode extends Node
{
	private static final long serialVersionUID = -3942596996905768225L;
	
	private HashMap<String, Node> nodeMap = null;
	
	public TreeNode(String key)
	{
		super(key);
		
		nodeMap = new HashMap<String, Node>();
	}
	
	public void addNode(Node node)
	{
		nodeMap.put(node.getKey(), node);
	}
	
	public Node getNode(String key)
	{
		return nodeMap.get(key);
	}
	
	public void clear()
	{
		nodeMap.clear();
	}

	public String getValue(String[] splitedKey) 
	{
		String[] childNodeKey = Arrays.copyOfRange(splitedKey, 1, splitedKey.length);
		String result = null;		
		
		// Gets the node for this key
		Node node = nodeMap.get(splitedKey[0]);
				
		// If exists
		if (node != null)
		{
			// Gets the value
			result = node.getValue(childNodeKey);
		}
		
		// If the result was not found
		if (result == null)
		{
			// Tries with the wild card character
			node = nodeMap.get(Node.WILDCARD_CHARACTER);
			
			// If exists
			if (node != null)
			{
				// Gets the value
				result = node.getValue(childNodeKey);
			}
		}
		
		return result;
	}
}