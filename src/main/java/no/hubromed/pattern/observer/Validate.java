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
 * A simple validator class for the purpose of the Subject to validate correct configuration.
 */
final class Validate {

    private Validate() {
    }

    public static void notNull(Object value, String valueName) {
        if (value == null) {
            throw new IllegalArgumentException(valueName + " cannot be null");
        }
    }

    public static void isTrue(boolean b, String expression) {
        if (!b) {
            throw new IllegalArgumentException(expression);
        }
    }
}
