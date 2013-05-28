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
 * UI code that follows a life cycle. Should be initiated by calling {@link LifeCycleUtil#initLifeCycle(LifeCycle)},
 * and ended by calling {@link #freeResources()}.
 *
 * @author Skjalg Bjørndal
 * @since Jan 7, 2010
 */
public interface LifeCycle {

    /**
     * Create GUI components, but contains no layout.
     *
     * @see #makeLayout()
     */
    void createComponents();

    /**
     * Create listeners/handlers
     *
     * @see #freeResources()
     */
    void createHandlers();

    /**
     * Registers/adds listeners/handlers.
     */
    void registerHandlers();

    /**
     * Un-register listeners/handlers and generally frees up resources.
     */
    void freeResources();

    /**
     * Make the layout of this component.
     */
    void makeLayout();

    /**
     * Sets initial values for component.
     */
    void initComponent();

    /**
     * Updates the language for the component.
     */
    void i18n();


}


