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

import org.junit.Test;

import java.util.HashSet;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * TODO javadoc
 *
 * @author Skjalg Bjørndal
 * @since Sep 25, 2010
 */
public class EventCRUDTest {


    @Test
    public void testCUD() {
        assertEquals(4, EventCRUD.CUD.size());
        assertTrue(EventCRUD.CUD.contains(EventCRUD.INITIAL));
        assertTrue(EventCRUD.CUD.contains(EventCRUD.CREATE));
        assertTrue(EventCRUD.CUD.contains(EventCRUD.UPDATE));
        assertTrue(EventCRUD.CUD.contains(EventCRUD.DELETE));
    }

    @Test
    public void testCRUD() {
        assertEquals(5, EventCRUD.CRUD.size());
        assertTrue(EventCRUD.CRUD.contains(EventCRUD.INITIAL));
        assertTrue(EventCRUD.CRUD.contains(EventCRUD.CREATE));
        assertTrue(EventCRUD.CRUD.contains(EventCRUD.READ));
        assertTrue(EventCRUD.CRUD.contains(EventCRUD.UPDATE));
        assertTrue(EventCRUD.CRUD.contains(EventCRUD.DELETE));
    }


    @Test
    public void testCreate() {
        assertEquals("C length", 1, EventCRUD.C.size());
        assertTrue("C should contain CREATE", EventCRUD.C.contains(EventCRUD.CREATE));
    }

    @Test
    public void testRead() {
        assertEquals("R length", 1, EventCRUD.R.size());
        assertTrue("R should contain READ", EventCRUD.R.contains(EventCRUD.READ));
    }

    @Test
    public void testUpdate() {
        assertEquals("U length", 1, EventCRUD.U.size());
        assertTrue("U should contain UPDATE", EventCRUD.U.contains(EventCRUD.UPDATE));
    }

    @Test
    public void testDelete() {
        assertEquals("D length", 1, EventCRUD.D.size());
        assertTrue("D should contain DELETE", EventCRUD.D.contains(EventCRUD.DELETE));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveContentFromCRUD() {
        EventCRUD.CRUD.remove(EventCRUD.READ);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveContentFromCUD() {
        EventCRUD.CUD.remove(EventCRUD.DELETE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveContentFromC() {
        EventCRUD.C.remove(EventCRUD.CREATE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveContentFromR() {
        EventCRUD.R.remove(EventCRUD.READ);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveContentFromU() {
        EventCRUD.U.remove(EventCRUD.UPDATE);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveContentFromD() {
        EventCRUD.D.remove(EventCRUD.DELETE);
    }



}
