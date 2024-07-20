plugins {
	id("groovy-gradle-plugin")
}

gradlePlugin {
	plugins {
		register("submoduledeps") {
			id = "com.parzivail.internal.submoduledeps"
			implementationClass = "com.parzivail.internal.submoduledeps.SubmoduleDepsPlugin"
		}
	}
}