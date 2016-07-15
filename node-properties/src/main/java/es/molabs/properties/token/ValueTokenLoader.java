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

import es.molabs.properties.NodePropertiesTokenLoader;

public class ValueTokenLoader implements NodePropertiesTokenLoader
{
	public String value = null;

	public ValueTokenLoader()
	{		
	}
	
	public ValueTokenLoader(String value)
	{
		this.value = value;
	}
	
	public void setTokenValue(String value) 
	{
		this.value = value;
	}
	
	public String getTokenValue() 
	{
		return value;
	}
}