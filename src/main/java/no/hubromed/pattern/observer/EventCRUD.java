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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Create, read, update and delete (CRUD) are 4 events that can be of interest to an {@code Observer}.
 * It is up to the developer to decide how to interpret those events. In order to specify which events you
 * are interested in, use the predefined sets:
 *
 * @see #CRUD
 * @see #CUD
 * @see #C
 * @see #R
 * @see #U
 * @see #D
 */
public enum EventCRUD {

    /**
     * Intial value when you attach (start to listen).
     */
    INITIAL,
    /**
     * Event issued something is made, or is new.
     */
    CREATE,
    /**
     * Event issued something is read. For instance each time an API call is made, you may want to display this in
     * a GUI. The event is normally only issued/programmed on demand.
     */
    READ,
    /**
     * By far the most common event, issued each time a setter is called.
     */
    UPDATE,
    /**
     * When something is destroyed/removed this event should be issued.
     */
    DELETE;


    /**
     * A set only containing {@link #INITIAL}.
     */
    public static final Set<EventCRUD> I;
    /**
     * A set only containing {@link #CREATE}.
     */
    public static final Set<EventCRUD> C;
    /**
     * A set only containing {@link #READ}.
     */
    public static final Set<EventCRUD> R;
    /**
     * A set only containing {@link #UPDATE}.
     */
    public static final Set<EventCRUD> U;
    /**
     * A set only containing {@link #DELETE}.
     */
    public static final Set<EventCRUD> D;
    /**
     * A set only containing the most common scenario, {@link #INITIAL},{@link #CREATE}, {@link #UPDATE}, and {@link #DELETE}.
     */
    public static final Set<EventCRUD> CUD;
    /**
     * When all event are interesting, listens for {@link #INITIAL}, {@link #CREATE}, {@link #READ}, {@link #UPDATE}, and {@link #DELETE}.
     */
    public static final Set<EventCRUD> CRUD;

    static {
        I = create(INITIAL);
        C = create(CREATE);
        R = create(READ);
        U = create(UPDATE);
        D = create(DELETE);

        CUD = create(CREATE, UPDATE, DELETE, INITIAL);
        CRUD = create(CREATE, READ, UPDATE, DELETE, INITIAL);
    }


    private static Set<EventCRUD> create(EventCRUD... elements) {
        Set<EventCRUD> set = new HashSet<EventCRUD>(elements.length);
        set.addAll(Arrays.asList(elements));
        return Collections.unmodifiableSet(set);
    }


}

