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
package es.molabs.properties.token;

import es.molabs.properties.NodePropertiesToken;
import es.molabs.properties.NodePropertiesTokenLoader;

public class BaseNodePropertiesToken implements NodePropertiesToken
{
	private NodePropertiesTokenLoader tokenLoader = null;
	
	private boolean runtime = false;
	
	public BaseNodePropertiesToken()
	{		
	}
	
	public BaseNodePropertiesToken(NodePropertiesTokenLoader tokenLoader)
	{
		this.tokenLoader = tokenLoader;
	}
	
	public void setTokenLoader(NodePropertiesTokenLoader tokenLoader)
	{
		this.tokenLoader = tokenLoader;
	}
	
	public NodePropertiesTokenLoader getTokenLoader()
	{
		return tokenLoader;
	}
	
	public String getTokenValue()
	{
		return tokenLoader.getTokenValue();
	}
	
	public void setRuntime(boolean runtime)
	{
		this.runtime = runtime;
	}
	
	public boolean isRuntime()
	{
		return runtime;
	}
}
