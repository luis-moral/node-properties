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
package es.molabs.properties.test;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import es.molabs.properties.NodePropertiesToken;
import es.molabs.properties.NodePropertiesTokenLoader;
import es.molabs.properties.token.BaseNodePropertiesToken;
import es.molabs.properties.token.SystemPropertyTokenLoader;
import es.molabs.properties.token.ValueTokenLoader;

/**
 * Test class for TokenLoader.
 */
@RunWith(MockitoJUnitRunner.class)
public class TokenLoaderTest 
{
	@Test
	public void testGetStaticTokenValue()
	{
		NodePropertiesTokenLoader staticTokenLoader = new StaticTokenLoader();
		NodePropertiesToken token = new BaseNodePropertiesToken();
		
		token.setTokenLoader(staticTokenLoader);
		token.setRuntime(false);
		
		Assert.assertEquals("Value must be [0].", "0", token.getTokenValue());
	}
	
	@Test
	public void testGetRuntimeTokenValue()
	{
		NodePropertiesTokenLoader runtimeTokenLoader = new RuntimeTokenLoader();		
		
		NodePropertiesToken token = new BaseNodePropertiesToken();
		token.setTokenLoader(runtimeTokenLoader);
		token.setRuntime(true);		
		
		Assert.assertEquals("Value must be [1].", "1", token.getTokenValue());
		Assert.assertEquals("Value must be [2].", "2", token.getTokenValue());
		Assert.assertEquals("Value must be [3].", "3", token.getTokenValue());
	}
	
	@Test
	public void testSystemPropertyTokenLoader()
	{
		Properties properties = System.getProperties();
		properties.setProperty("TEST_PROPERTY_A", "TEST_VALUE_A");
		
		// Checks with a property that exists
		SystemPropertyTokenLoader tokenLoader = new SystemPropertyTokenLoader();
		tokenLoader.setProperty("TEST_PROPERTY_A");		
		
		Assert.assertEquals("Value must be [TEST_VALUE_A].", "TEST_VALUE_A", tokenLoader.getTokenValue());		
		
		// Checks the default value
		tokenLoader = new SystemPropertyTokenLoader();
		tokenLoader.setDefaultValue("NO_VALUE");
		tokenLoader.setProperty("TEST_PROPERTY_V");
		
		Assert.assertEquals("Value must be [NO_VALUE].", "NO_VALUE", tokenLoader.getTokenValue());
	}
	
	@Test
	public void testValueTokenLoader()
	{
		ValueTokenLoader tokenLoader = new ValueTokenLoader();
		tokenLoader.setTokenValue("TEST_VALUE_A");
		
		Assert.assertEquals("Value must be [TEST_VALUE_A].", "TEST_VALUE_A", tokenLoader.getTokenValue());
	}
	
	private class StaticTokenLoader implements NodePropertiesTokenLoader
	{		
		public String getTokenValue() 
		{
			return "0";
		}		
	}
	
	private class RuntimeTokenLoader implements NodePropertiesTokenLoader
	{
		private String value1 = "1";
		private String value2 = "2";
		private String value3 = "3";
		
		private int counter = 0;
		
		public String getTokenValue() 
		{
			String value = null;
			
			counter++;
			
			int normalized = ((counter - 1) % 3) + 1;
			
			if (normalized == 1)
			{
				value = value1;
			}
			else if (normalized == 2)
			{
				value = value2;
			}
			else if (normalized == 3)
			{
				value = value3;
			}
			
			return value;
		}		
	}
}
