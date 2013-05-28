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

package no.hubromed.pattern.lifecycle;

/**
 * Util class to call life cycle methods in the correct order.
 */
public final class LifeCycleUtil {

    private LifeCycleUtil() {
    }

    /**
     * Calls all methods on the given lifeCycle component.
     *
     * @param lifeCycle Component to be initialized.
     */
    public static void initLifeCycle(LifeCycle lifeCycle) {
        lifeCycle.createComponents();
        lifeCycle.makeLayout();
        lifeCycle.createHandlers();
        lifeCycle.registerHandlers();
        lifeCycle.initComponent();
        lifeCycle.i18n();
    }

    /**
     * Update language for this component and all its sub components.
     *
     * @param lifeCycles Component to be initialized.
     */
    public static void i18n(LifeCycle... lifeCycles) {
        for (LifeCycle lifeCycle : lifeCycles) {
            lifeCycle.i18n();
        }
    }

}
