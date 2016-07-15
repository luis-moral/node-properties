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

import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import es.molabs.properties.NodeProperties;
import es.molabs.properties.NodePropertiesKey;
import es.molabs.properties.NodePropertiesToken;
import es.molabs.properties.NodePropertiesTokenLoader;
import es.molabs.properties.token.BaseNodePropertiesToken;

/**
 * Test class for a single node properties.
 */
@RunWith(MockitoJUnitRunner.class)
public class SinglePropertiesTest 
{
	@Test
	public void testGetProperty() throws IOException
	{
		InputStream inputStream = getClass().getResourceAsStream("/es/molabs/properties/test/properties/single.properties");
		NodeProperties properties = new NodeProperties(1);				
		
		try
		{
			properties.load(inputStream);
		}
		finally
		{
			inputStream.close();
		}
		
		NodePropertiesTokenLoader loader = new TestTokenLoader();
		
		NodePropertiesToken token = new BaseNodePropertiesToken();
		token.setTokenLoader(loader);
		token.setRuntime(true);
		
		NodePropertiesKey key = new NodePropertiesKey(token);
		String value = null;
		String expectedValue = null;
		
		value = properties.getProperty(key.toKey("test.property1.value"));
		expectedValue = "*-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		value = properties.getProperty(key.toKey("test.property1.value"));
		expectedValue = "STATIC-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		value = properties.getProperty(key.toKey("null.property1.value"));
		expectedValue = null;
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
	}
	
	private class TestTokenLoader implements NodePropertiesTokenLoader
	{
		private String value1 = "*";
		private String value2 = "STATIC";
		
		private int counter = 0;
		
		public String getTokenValue() 
		{
			String value = null;
			
			counter++;
			
			int normalized = ((counter - 1) % 2) + 1;
			
			if (normalized == 1)
			{
				value = value1;
			}
			else if (normalized == 2)
			{
				value = value2;
			}
			
			return value;
		}
	}
}
