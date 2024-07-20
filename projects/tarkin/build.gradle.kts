plugins {
	id("com.parzivail.internal.submoduledeps")
}

dependencies {
	submoduledeps.api(":projects:pswg")
	submoduledeps.api(":projects:addon-clonewars")
	api(project(":projects:tarkin-api"))

	// Cloth Config
	modImplementation("me.shedaniel.cloth:cloth-config-fabric:${project.findProperty("cloth_config_version")}") {
		exclude(group = "net.fabricmc.fabric-api")
	}
	include("me.shedaniel.cloth:cloth-config-fabric:${project.findProperty("cloth_config_version")}")

	// Trinkets
	modImplementation("dev.emi:trinkets:${project.findProperty("trinkets_version")}")
	include("dev.emi:trinkets:${project.findProperty("trinkets_version")}")
}