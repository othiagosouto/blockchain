package dev.thiagosouto.blockchain.features.charts.rules

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

internal class KoinTestRule(private val modulesList : List<Module> = emptyList()) : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    startKoin{
                        modules(modulesList)
                    }
                    base?.evaluate()
                } finally {
                    stopKoin()
                }
            }

        }
    }
}