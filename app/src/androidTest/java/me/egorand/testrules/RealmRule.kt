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

import android.support.test.InstrumentationRegistry
import io.realm.Realm
import io.realm.RealmConfiguration
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author Egor
 */
class RealmRule : TestRule {

    lateinit var realm: Realm

    override fun apply(base: Statement?, description: Description?) = RealmStatement(base)

    inner class RealmStatement(private val base: Statement?) : Statement() {

        override fun evaluate() {

            fun initRealm() {
                val context = InstrumentationRegistry.getTargetContext()
                val config = RealmConfiguration.Builder(context).build()
                Realm.setDefaultConfiguration(config)
                realm = Realm.getDefaultInstance()
            }

            try {
                initRealm()
                base?.evaluate()
            } finally {
                realm.executeTransaction { realm.deleteAll() }
            }
        }
    }
}
