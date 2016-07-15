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

/**
 * Test class for a zero nodes properties.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZeroPropertiesTest 
{
	@Test
	public void testGetProperty() throws IOException
	{
		NodeProperties properties = new NodeProperties();
		InputStream inputStream = getClass().getResourceAsStream("/es/molabs/properties/test/properties/zero.properties");		
				
		try
		{
			properties.load(inputStream);
		}
		finally
		{
			inputStream.close();
		}
	
		String value = null;
		String expectedValue = null;
		
		value = properties.getProperty("test.property1.value");
		expectedValue = "value1";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		value = properties.getProperty("test.property2.value");
		expectedValue = "value2";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		value = properties.getProperty("null.property.value");
		expectedValue = null;
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
	}
}
