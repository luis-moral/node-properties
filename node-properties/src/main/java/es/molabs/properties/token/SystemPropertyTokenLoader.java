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

public class SystemPropertyTokenLoader implements NodePropertiesTokenLoader 
{
	// System property name
	private String property = null;
	
	// Default value if the system property does not exists
	private String defaultValue = null;
	
	public SystemPropertyTokenLoader()
	{		
	}
	
	public SystemPropertyTokenLoader(String property)
	{
		this.property = property;
	}
	
	/**
	 * Sets the system property name.
	 * 
	 * @param property system property name.
	 */
	public void setProperty(String property)
	{
		this.property = property;
	}
	
	/**
	 * Sets the default value. If the property does not exists.
	 * 
	 * @param defaultValue for the property.
	 */
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	
	public String getTokenValue() 
	{
		return (System.getProperty(property) != null ? System.getProperty(property) : defaultValue);
	}
}
