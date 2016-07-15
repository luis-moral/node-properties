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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.molabs.properties.node.Node;

public class NodePropertiesKey 
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static String RUNTIME_TOKEN_VALUE = UUID.randomUUID().toString().substring(0, 4);
	
	private List<NodePropertiesToken> tokenList = null;
	private List<NodePropertiesToken> runtimeTokenList = null;
	private String tokenKey = null;	

	public NodePropertiesKey(NodePropertiesToken...tokenList)
	{
		this(Arrays.asList(tokenList));
	}
	
	/**
	 * Creates a NodePropertiesKey from a list of NodePropertiesToken.
	 * 
	 * @param tokenList	of the nodes in the properties file.
	 */
	public NodePropertiesKey(List<NodePropertiesToken> tokenList)
	{
		if (tokenList == null) tokenList = new ArrayList<NodePropertiesToken>(0);
		
		this.tokenList = tokenList;
	}
	
	public String toKey(String key)
	{
		// If the tokenKey is not already initialized
		if (tokenKey == null)
		{
			// Initializes the token key
			tokenKey = initTokenKey();			
		}		
		
		return getRuntimeTokensValues().concat(key);
	}
	
	private String initTokenKey()
	{
		StringBuilder builder = new StringBuilder();
		
		// For each token
		for (int i=0; i<tokenList.size(); i++)
		{
			// If the token value can change in runtime
			if (tokenList.get(i).isRuntime())
			{
				builder.append(NodePropertiesKey.RUNTIME_TOKEN_VALUE);
				
				// If the runtime token list does not exist already, creates it
				if (runtimeTokenList == null) runtimeTokenList = new ArrayList<NodePropertiesToken>();
				
				// Adds the token to the list
				runtimeTokenList.add(tokenList.get(i));
				
			}
			// Else
			else
			{
				// If the token contains a ".", replaces it for a "_"
				builder.append(tokenList.get(i).getTokenValue().replace(".", "_"));
			}
			
			builder.append(".");
		}			
		
		return builder.toString();
	}
	
	/**
	 * Updates the values of the runtime tokens.
	 * 
	 * @return Key multinode processed with the runtime values.
	 */
	private String getRuntimeTokensValues()
	{
		// If there is no runtime tokens
		if (runtimeTokenList == null) return tokenKey;
		
		String copy = tokenKey;
		
		// For each runtime token
		for (int i=0; i<runtimeTokenList.size(); i++)
		{
			// Gets the current token value
			String tokenValue = runtimeTokenList.get(i).getTokenValue();
			
			// If the token has no value
			if (tokenValue == null)
			{
				logger.warn("No runtime value for token [{}], using wildcard value.", tokenKey);
				
				tokenValue = Node.WILDCARD_CHARACTER;
			}
			
			// Replaces the RUNTIME_TOKEN_VALUE for the current runtime value. If the token contains a '.' replaces it by a '_'
			copy = copy.replaceFirst(NodePropertiesKey.RUNTIME_TOKEN_VALUE, tokenValue.replace(".", "_"));
		}		
		
		return copy;
	}
	
	public void clear() 
	{
		if (runtimeTokenList != null) 
		{
			runtimeTokenList.clear();
			runtimeTokenList = null;
		}
		
		if (tokenList != null) 
		{
			tokenList.clear();
			tokenList = null;
		}
		
		tokenKey = null;		
	}
}
