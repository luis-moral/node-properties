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

public interface NodePropertiesToken 
{
	/**
	 * Sets the MultiNodePropertyTokenLoader for this MultiNodePropertyToken.
	 * 
	 * @param tokenLoader for this MultiNodePropertyToken.
	 */
	public void setTokenLoader(NodePropertiesTokenLoader tokenLoader);
	
	/**
	 * Gets the MultiNodePropertyTokenLoader for this MultiNodePropertyToken. 
	 * 
	 * @return Current MultiNodePropertyTokenLoader.
	 */
	public NodePropertiesTokenLoader getTokenLoader();
	
	/**
	 * Gets the token value.
	 * 
	 * @return Token value.
	 */
	public String getTokenValue();
	
	/**
	 * Sets the runtime parameter. If this is true the parameters value can change in runtime should it will be processed each call, if false it will only processed once.
	 * 
	 * @param runtime if this runtime or not.
	 */
	public void setRuntime(boolean runtime);
	
	/**
	 * Gets the runtime parameter. If this is true the parameters value can change in runtime should it will be processed each call, if false it will only processed once.
	 * 
	 * @return If this runtime or not.
	 */
	public boolean isRuntime();
}