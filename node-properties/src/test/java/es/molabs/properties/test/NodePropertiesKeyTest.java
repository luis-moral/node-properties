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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import es.molabs.properties.NodePropertiesKey;
import es.molabs.properties.NodePropertiesToken;
import es.molabs.properties.NodePropertiesTokenLoader;
import es.molabs.properties.token.BaseNodePropertiesToken;

@RunWith(MockitoJUnitRunner.class)
public class NodePropertiesKeyTest 
{
	@Test
	public void testToKeyNoLoaders()
	{
		NodePropertiesKey key = new NodePropertiesKey();
		
		Assert.assertEquals("Value must be [some.value].", "some.value", key.toKey("some.value"));
	}
	
	@Test
	public void testToKey()
	{
		NodePropertiesTokenLoader firstTokenLoader = new DummyRuntimeFirstTestTokenLoader();
		NodePropertiesTokenLoader secondTokenLoader = new DummyRuntimeSecondTokenLoader();
		NodePropertiesTokenLoader thirdTokenLoader = new DummyStaticThirdTokenLoader();
		
		NodePropertiesToken firstToken = new BaseNodePropertiesToken();
		firstToken.setTokenLoader(firstTokenLoader);
		firstToken.setRuntime(true);
		
		NodePropertiesToken secondToken = new BaseNodePropertiesToken();
		secondToken.setTokenLoader(secondTokenLoader);
		secondToken.setRuntime(true);
		
		NodePropertiesToken thirdToken = new BaseNodePropertiesToken();
		thirdToken.setTokenLoader(thirdTokenLoader);
		thirdToken.setRuntime(false);
		
		NodePropertiesKey key = new NodePropertiesKey(firstToken, secondToken, thirdToken);
		
		Assert.assertEquals("Value must be [*.*.STATIC.some.value].", "*.*.STATIC.some.value", key.toKey("some.value"));
		Assert.assertEquals("Value must be [NODEA.NODE1.STATIC.some.value].", "NODEA.NODE1.STATIC.some.value", key.toKey("some.value"));
		Assert.assertEquals("Value must be [NODEB.NODE2.STATIC.some.value].", "NODEB.NODE2.STATIC.some.value", key.toKey("some.value"));		
		Assert.assertEquals("Value must be [NODEC.NODE3.STATIC.some.value].", "NODEC.NODE3.STATIC.some.value", key.toKey("some.value"));
	}
	
	private class DummyRuntimeFirstTestTokenLoader implements NodePropertiesTokenLoader
	{
		private String value1 = "*";
		private String value2 = "NODEA";
		private String value3 = "NODEB";
		private String value4 = "NODEC";
		
		private int counter = 0;
		
		public String getTokenValue() 
		{
			String value = null;
			
			counter++;
			
			int normalized = ((counter - 1) % 4) + 1;
			
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
			else if (normalized == 4)
			{
				value = value4;
			}
			
			return value;
		}		
	}
	
	private class DummyRuntimeSecondTokenLoader implements NodePropertiesTokenLoader
	{
		private String value1 = "*";
		private String value2 = "NODE1";
		private String value3 = "NODE2";
		private String value4 = "NODE3";
		
		private int counter = 0;
		
		public String getTokenValue() 
		{
			String value = null;
			
			counter++;
			
			int normalized = ((counter - 1) % 4) + 1;
			
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
			else if (normalized == 4)
			{
				value = value4;
			}
			
			return value;
		}		
	}
	
	private class DummyStaticThirdTokenLoader implements NodePropertiesTokenLoader
	{
		public String getTokenValue() 
		{
			return "STATIC";
		}		
	}
}
