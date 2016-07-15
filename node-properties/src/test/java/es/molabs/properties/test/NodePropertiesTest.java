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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import es.molabs.properties.NodeProperties;
import es.molabs.properties.NodePropertiesKey;
import es.molabs.properties.NodePropertiesToken;
import es.molabs.properties.NodePropertiesTokenLoader;
import es.molabs.properties.token.BaseNodePropertiesToken;
import es.molabs.properties.token.ValueTokenLoader;

@RunWith(MockitoJUnitRunner.class)
public class NodePropertiesTest 
{
	@Test
	public void testCloneable() throws Throwable
	{
		NodeProperties properties = new NodeProperties(1);
		InputStream inputStream = getClass().getResourceAsStream("/es/molabs/properties/test/properties/clone.properties");		
		
		try
		{
			properties.load(inputStream);
		}
		finally
		{
			inputStream.close();
		}		
		
		NodePropertiesTokenLoader loader = new ValueTokenLoader("*");
		
		NodePropertiesToken token = new BaseNodePropertiesToken();
		token.setTokenLoader(loader);
		token.setRuntime(true);
		
		NodePropertiesKey key = new NodePropertiesKey(token);
		String value = null;
		String expectedValue = null;
		
		value = properties.getProperty(key.toKey("test.property1.value"));
		expectedValue = "*-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		NodeProperties clone = (NodeProperties) properties.clone();
		
		// Checks that they are not the same object
		Assert.assertNotEquals(properties, clone);
		
		// Checks that they have the same class
		Assert.assertEquals(properties.getClass(), clone.getClass());
		
		// Checks that they have the same values
		value = clone.getProperty(key.toKey("test.property1.value"));
		expectedValue = "*-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
	}
	
	@Test
	public void testClear() throws Throwable
	{
		NodeProperties properties = new NodeProperties(1);
		InputStream inputStream = getClass().getResourceAsStream("/es/molabs/properties/test/properties/clone.properties");		
		
		try
		{
			properties.load(inputStream);
		}
		finally
		{
			inputStream.close();
		}
		
		int expectedValue = 2;
		int value = properties.stringPropertyNames().size();
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		// Clears the properties
		properties.clear();
		
		expectedValue = 0;
		value = properties.stringPropertyNames().size();
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
	}
	
	@Test
	public void testAdd() throws Throwable
	{
		// Creates the values to add
		Map<String, String> add = new HashMap<String, String>();
		add.put("property.a", "VALUE-A");
		add.put("property.b", "VALUE-B");
		
		// Creates the properties an adds the values
		NodeProperties properties = new NodeProperties();
		properties.setProperty("property.c", "VALUE-C");
		properties.load(add);
		
		// Checks that it still has the previous value
		String expectedValue = "VALUE-C";
		String value = properties.getProperty("property.c");
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		// Checks that it has the new values
		expectedValue = "VALUE-A";
		value = properties.getProperty("property.a");
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
				
		// Checks that it has 3 values
		int expectedValueInt = 3;
		int valueInt = properties.stringPropertyNames().size();
		Assert.assertEquals("Value must be [" + expectedValueInt + "].", expectedValueInt, valueInt);
	}
}
