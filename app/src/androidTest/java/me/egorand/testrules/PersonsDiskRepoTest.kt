/*
 * Copyright 2016 Egor Andreevici
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

package me.egorand.testrules

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * @author Egor
 */
class PersonsDiskRepoTest {

    companion object {
        val TEST_PERSONS = listOf(
                Person("Alice", 26),
                Person("Bob", 55),
                Person("Christie", 18))
    }

    @Rule @JvmField val realmRule = RealmRule()

    lateinit var repo: PersonsDiskRepo

    @Before fun setUp() {
        realmRule.realm.executeTransaction {
            TEST_PERSONS.forEach { realmRule.realm.insert(it) }
        }
        repo = PersonsDiskRepo(realmRule.realm)
    }

    @Test fun shouldLoadAllPersons() {
        val result = repo.getAllPersons()

        assertThat(result.size).isEqualTo(TEST_PERSONS.size)
        assertThat(result).containsAll(TEST_PERSONS)
    }

    @Test fun shouldLoadPersonsAgedOver20() {
        val result = repo.getAllPersonsOlderThan(20)

        assertThat(result.size).isEqualTo(2)
        assertThat(result).contains(TEST_PERSONS[0])
        assertThat(result).contains(TEST_PERSONS[1])
        assertThat(result).doesNotContain(TEST_PERSONS[2])
    }
}