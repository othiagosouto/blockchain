plugins {
    `java-platform`
}

dependencies {
    val espressoVersion = "3.3.0"
    val androidXTestVersion = "1.3.0"
    val androidxTestExtJunitVersion = "1.1.2"
    val robolectricVersion = "4.5.1"

    constraints {
        api("androidx.test:core:$androidXTestVersion")
        api("androidx.test.espresso:espresso-contrib:$espressoVersion")
        api("androidx.test.espresso:espresso-core:$espressoVersion")
        api("androidx.test.espresso:espresso-intents:$espressoVersion")
        api("androidx.test.ext:junit:$androidxTestExtJunitVersion")
        api("androidx.test:runner:$androidXTestVersion")
        api("org.robolectric:robolectric:$robolectricVersion")
        api("androidx.test:orchestrator:1.1.0")
    }
}
