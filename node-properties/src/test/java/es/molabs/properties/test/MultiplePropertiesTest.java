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
import es.molabs.properties.token.BaseNodePropertiesToken;
import es.molabs.properties.token.ValueTokenLoader;

/**
 * Test class for a multiple node properties.
 */
@RunWith(MockitoJUnitRunner.class)
public class MultiplePropertiesTest 
{
	@Test
	public void testGetProperty() throws IOException
	{
		NodeProperties properties = new NodeProperties(3);
		InputStream inputStream = getClass().getResourceAsStream("/es/molabs/properties/test/properties/multiple.properties");		
				
		try
		{
			properties.load(inputStream);
		}
		finally
		{
			inputStream.close();
		}
		
		ValueTokenLoader firstTokenLoader = new ValueTokenLoader("*");
		ValueTokenLoader secondTokenLoader = new ValueTokenLoader("*");
		ValueTokenLoader thirdTokenLoader = new ValueTokenLoader("*");
		
		NodePropertiesToken firstToken = new BaseNodePropertiesToken();
		firstToken.setTokenLoader(firstTokenLoader);
		firstToken.setRuntime(true);
		
		NodePropertiesToken secondToken = new BaseNodePropertiesToken();
		secondToken.setTokenLoader(secondTokenLoader);
		secondToken.setRuntime(true);
		
		NodePropertiesToken thirdToken = new BaseNodePropertiesToken();
		thirdToken.setTokenLoader(thirdTokenLoader);
		thirdToken.setRuntime(true);
		
		NodePropertiesKey key = new NodePropertiesKey(firstToken, secondToken, thirdToken);		
		String value = null;
		String expectedValue = null;
		
		firstTokenLoader.setTokenValue("*");
		secondTokenLoader.setTokenValue("*");
		thirdTokenLoader.setTokenValue("*");
		value = properties.getProperty(key.toKey("some.value"));
		expectedValue = "*-*-*-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		firstTokenLoader.setTokenValue("x");
		secondTokenLoader.setTokenValue("B");
		thirdTokenLoader.setTokenValue("x");
		value = properties.getProperty(key.toKey("some.value"));
		expectedValue = "*-B-*-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		firstTokenLoader.setTokenValue("A");
		secondTokenLoader.setTokenValue("x");
		thirdTokenLoader.setTokenValue("C");
		value = properties.getProperty(key.toKey("some.value"));
		expectedValue = "A-*-C-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		firstTokenLoader.setTokenValue("x");
		secondTokenLoader.setTokenValue("x");
		thirdTokenLoader.setTokenValue("C");
		value = properties.getProperty(key.toKey("some.value"));
		expectedValue = "*-*-C-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		firstTokenLoader.setTokenValue("A");
		secondTokenLoader.setTokenValue("x");
		thirdTokenLoader.setTokenValue("x");
		value = properties.getProperty(key.toKey("some.value"));
		expectedValue = "A-*-*-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		firstTokenLoader.setTokenValue("A");
		secondTokenLoader.setTokenValue("B");
		thirdTokenLoader.setTokenValue("x");
		value = properties.getProperty(key.toKey("some.value"));
		expectedValue = "A-B-*-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		firstTokenLoader.setTokenValue("x");
		secondTokenLoader.setTokenValue("B");
		thirdTokenLoader.setTokenValue("C");
		value = properties.getProperty(key.toKey("some.value"));
		expectedValue = "*-B-C-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		firstTokenLoader.setTokenValue("*");
		secondTokenLoader.setTokenValue("*");
		thirdTokenLoader.setTokenValue("*");
		value = properties.getProperty(key.toKey("some.value"));
		expectedValue = "*-*-*-value";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		firstTokenLoader.setTokenValue("x");
		secondTokenLoader.setTokenValue("x");
		thirdTokenLoader.setTokenValue("x");
		value = properties.getProperty(key.toKey("null.value"));
		expectedValue = null;
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
	}
	
	@Test
	public void testGetPropertyWildcards() throws IOException
	{
		NodeProperties properties = new NodeProperties(3);
		InputStream inputStream = getClass().getResourceAsStream("/es/molabs/properties/test/properties/multiple.properties");		
				
		try
		{
			properties.load(inputStream);
		}
		finally
		{
			inputStream.close();
		}
		
		ValueTokenLoader firstTokenLoader = new ValueTokenLoader("A");
		ValueTokenLoader secondTokenLoader = new ValueTokenLoader("B");
		ValueTokenLoader thirdTokenLoader = new ValueTokenLoader("C");
		
		NodePropertiesToken firstToken = new BaseNodePropertiesToken();
		firstToken.setTokenLoader(firstTokenLoader);
		firstToken.setRuntime(true);
		
		NodePropertiesToken secondToken = new BaseNodePropertiesToken();
		secondToken.setTokenLoader(secondTokenLoader);
		secondToken.setRuntime(true);
		
		NodePropertiesToken thirdToken = new BaseNodePropertiesToken();
		thirdToken.setTokenLoader(thirdTokenLoader);
		thirdToken.setRuntime(true);
		
		NodePropertiesKey key = new NodePropertiesKey(firstToken, secondToken, thirdToken);		
		String value = null;
		String expectedValue = null;
		
		value = properties.getProperty(key.toKey("key1"));
		expectedValue = "value1";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);

		value = properties.getProperty(key.toKey("key2"));
		expectedValue = "value2";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);		
		
		value = properties.getProperty(key.toKey("key3"));
		expectedValue = "value3";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		value = properties.getProperty(key.toKey("key4"));
		expectedValue = "value4";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		value = properties.getProperty(key.toKey("key5"));
		expectedValue = "value5";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		value = properties.getProperty(key.toKey("key6"));
		expectedValue = "value6";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		value = properties.getProperty(key.toKey("key7"));
		expectedValue = "value7";
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
	}
}
