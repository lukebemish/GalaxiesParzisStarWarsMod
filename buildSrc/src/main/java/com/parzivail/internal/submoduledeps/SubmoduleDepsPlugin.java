package com.parzivail.internal.submoduledeps;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.attributes.Attribute;

import java.util.Objects;

public class SubmoduleDepsPlugin implements Plugin<Project>
{
	public static final String NAMED_RUNTIME_ELEMENTS = "namedRuntimeElements";
	public static final String NAMED_API_ELEMENTS = "namedApiElements";

	public static final String MOD_LOCAL_RUNTIME = "modLocalRuntime";
	public static final String MOD_COMPILE_ONLY = "modCompileOnly";

	public static final String RUNTIME_ELEMENTS_DEPENDENCIES = "runtimeElementsDependencies";
	public static final String API_ELEMENTS_DEPENDENCIES = "apiElementsDependencies";

	public static final String ONLY_API_ELEMENTS = "onlyApiElements";
	public static final String ONLY_RUNTIME_ELEMENTS = "onlyRuntimeElements";

	@Override
	public void apply(Project project)
	{
		project.getPluginManager().apply("java");
		project.getPluginManager().apply("fabric-loom");

		var dependencyHandler = project.getDependencies();

		var modApi = project.getConfigurations().getByName("modApi");
		var modCompileOnlyApi = project.getConfigurations().getByName("modCompileOnlyApi");
		var modImplementation = project.getConfigurations().getByName("modImplementation");
		var modRuntimeOnly = project.getConfigurations().getByName("modRuntimeOnly");
		var namedElements = project.getConfigurations().maybeCreate("namedElements");

		var runtimeElementsDependencies = project.getConfigurations().maybeCreate(RUNTIME_ELEMENTS_DEPENDENCIES);
		var namedRuntimeElements = project.getConfigurations().maybeCreate(NAMED_RUNTIME_ELEMENTS);
		runtimeElementsDependencies.setCanBeResolved(true);
		namedRuntimeElements.setCanBeResolved(true);
		var runtimeElements = project.getConfigurations().getByName("runtimeElements");
		runtimeElementsDependencies.getDependencies().addAllLater(project.provider(modApi::getDependencies));
		runtimeElementsDependencies.getDependencies().addAllLater(project.provider(modImplementation::getDependencies));
		runtimeElementsDependencies.getDependencies().addAllLater(project.provider(modRuntimeOnly::getDependencies));
		namedRuntimeElements.getOutgoing().getArtifacts().addAllLater(project.provider(namedElements::getAllArtifacts));
		var onlyRuntimeElements = project.getConfigurations().maybeCreate(ONLY_RUNTIME_ELEMENTS);
		runtimeElements.extendsFrom(onlyRuntimeElements);

		var apiElementsDependencies = project.getConfigurations().maybeCreate(API_ELEMENTS_DEPENDENCIES);
		var namedApiElements = project.getConfigurations().maybeCreate(NAMED_API_ELEMENTS);
		apiElementsDependencies.setCanBeResolved(true);
		namedApiElements.setCanBeResolved(true);
		var apiElements = project.getConfigurations().getByName("apiElements");
		apiElementsDependencies.getDependencies().addAllLater(project.provider(modApi::getDependencies));
		apiElementsDependencies.getDependencies().addAllLater(project.provider(modCompileOnlyApi::getDependencies));
		namedApiElements.getOutgoing().getArtifacts().addAllLater(project.provider(namedElements::getAllArtifacts));
		var onlyApiElements = project.getConfigurations().maybeCreate(ONLY_API_ELEMENTS);
		apiElements.extendsFrom(onlyApiElements);

		dependencyHandler.getExtensions().create("submoduledeps", SubmoduleDepsExtension.class, project);
	}

	private void copyAttributes(Configuration from, Configuration to)
	{
		from.getAttributes().keySet().forEach(key -> copyAttribute(from, to, key));
	}

	private static <T> void copyAttribute(Configuration from, Configuration to, Attribute<T> key)
	{
		to.getAttributes().attribute(key, Objects.requireNonNull(from.getAttributes().getAttribute(key)));
	}
}
