/*
 * Copyright 2005-2013 Skjalg Bjorndal
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package no.hubromed.pattern.observer;

/**
 * Common class for handling fields that are not observed. Typically issued by an {@code Observer} if it receives a
 * field it does not know how to handle.
 *
 * @author Skjalg Bjørndal
 * @since Jun 28, 2009, 12:17:22 PM
 */
public class NotObservedException extends IllegalStateException {

    public NotObservedException(Enum enumField) {
        super("Proper handling of the field '" + enumField + "´ is not observed in this code.");
    }
}
