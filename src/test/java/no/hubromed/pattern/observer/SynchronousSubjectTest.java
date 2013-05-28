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
 * Unit test of the {@link no.hubromed.pattern.observer.AbstractSubject}.
 *
 * @author Skjalg Bjørndal
 * @since Sep 24, 2010
 */
public class SynchronousSubjectTest extends AbstractSubjectTest {

    @Override
    public Subject<AbstractSubjectTest.Field, Subject.SynchronousSubject> getInstance() {
        return new Subject.SynchronousSubject<AbstractSubjectTest.Field, Subject.SynchronousSubject>();
    }

    @Override
    public String getClassName() {
        return Subject.SynchronousSubject.class.getSimpleName();
    }
}
