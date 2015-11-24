// =================================================================================================
// Copyright 2011 Alan Ritter
// -------------------------------------------------------------------------------------------------
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this work except in compliance with the License.
// You may obtain a copy of the License in the LICENSE file, or at:
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// =================================================================================================

package twitter_nlp_wrapper;

//public enum NamedEntityType {
//	BENTITY("B-ENTITY"),
//	IENTITY("I-ENTITY"),
//	O("O");
//
//
//	// More human-readable version
//	public final String name;
//	private NamedEntityType(String s) {
//		this.name = s;
//	}
//}

public class NamedEntityType {
	public String name = "";

	NamedEntityType(String aName) {
		name = aName;
	}
}