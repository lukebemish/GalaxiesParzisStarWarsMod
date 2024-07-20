package com.parzivail.internal.submoduledeps;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.JavaPlugin;

import javax.inject.Inject;
import java.util.Map;

public class SubmoduleDepsExtension
{
	private final DependencyHandler dependencyHandler;
	private final Project project;

	private final Configuration namedRuntimeElements;
	private final Configuration namedApiElements;

	private final Configuration modLocalRuntime;
	private final Configuration modCompileOnly;

	private final Configuration onlyApiElements;
	private final Configuration onlyRuntimeElements;

	private final Configuration localRuntime;
	private final Configuration compileOnly;

	@Inject
	public SubmoduleDepsExtension(Project project)
	{
		this.project = project;
		this.dependencyHandler = project.getDependencies();

		this.namedRuntimeElements = project.getConfigurations().getByName(SubmoduleDepsPlugin.NAMED_RUNTIME_ELEMENTS);
		this.namedApiElements = project.getConfigurations().getByName(SubmoduleDepsPlugin.NAMED_API_ELEMENTS);

		this.modLocalRuntime = project.getConfigurations().getByName(SubmoduleDepsPlugin.MOD_LOCAL_RUNTIME);
		this.modCompileOnly = project.getConfigurations().getByName(SubmoduleDepsPlugin.MOD_COMPILE_ONLY);

		this.onlyApiElements = project.getConfigurations().getByName(SubmoduleDepsPlugin.ONLY_API_ELEMENTS);
		this.onlyRuntimeElements = project.getConfigurations().getByName(SubmoduleDepsPlugin.ONLY_RUNTIME_ELEMENTS);

		this.localRuntime = project.getConfigurations().getByName("localRuntime");
		this.compileOnly = project.getConfigurations().getByName(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);
	}

	public ProjectDependency api(String subproject) {
		return api(subproject, it -> {});
	}

	public ProjectDependency api(String subproject, Action<ProjectDependency> action)
	{
		ProjectDependency projectDependency = configure(action, dependencyHandler.project(Map.of("path", subproject)));
		ProjectDependency namedApiElementsDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.NAMED_API_ELEMENTS)));
		ProjectDependency namedRuntimeElementsDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.NAMED_RUNTIME_ELEMENTS)));
		ProjectDependency apiElementsDependenciesDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.API_ELEMENTS_DEPENDENCIES)));
		ProjectDependency runtimeElementsDependenciesDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.RUNTIME_ELEMENTS_DEPENDENCIES)));

		this.onlyRuntimeElements.getDependencies().add(projectDependency);
		this.onlyApiElements.getDependencies().add(projectDependency);
		this.namedRuntimeElements.getDependencies().add(namedRuntimeElementsDependency);
		this.namedApiElements.getDependencies().add(namedApiElementsDependency);
		this.modCompileOnly.getDependencies().add(apiElementsDependenciesDependency);
		this.modLocalRuntime.getDependencies().add(runtimeElementsDependenciesDependency);
		this.compileOnly.getDependencies().add(namedApiElementsDependency);
		this.localRuntime.getDependencies().add(namedRuntimeElementsDependency);

		return projectDependency;
	}

	public ProjectDependency compileOnlyApi(String subproject) {
		return compileOnlyApi(subproject, it -> {});
	}

	public ProjectDependency compileOnlyApi(String subproject, Action<ProjectDependency> action)
	{
		ProjectDependency projectDependency = configure(action, dependencyHandler.project(Map.of("path", subproject)));
		ProjectDependency namedApiElementsDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.NAMED_API_ELEMENTS)));
		ProjectDependency apiElementsDependenciesDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.API_ELEMENTS_DEPENDENCIES)));

		this.onlyApiElements.getDependencies().add(projectDependency);
		this.namedApiElements.getDependencies().add(namedApiElementsDependency);
		this.modCompileOnly.getDependencies().add(apiElementsDependenciesDependency);
		this.compileOnly.getDependencies().add(namedApiElementsDependency);

		return projectDependency;
	}

	public ProjectDependency runtimeOnly(String subproject) {
		return runtimeOnly(subproject, it -> {});
	}

	public ProjectDependency runtimeOnly(String subproject, Action<ProjectDependency> action)
	{
		ProjectDependency projectDependency = configure(action, dependencyHandler.project(Map.of("path", subproject)));
		ProjectDependency namedRuntimeElementsDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.NAMED_RUNTIME_ELEMENTS)));
		ProjectDependency runtimeElementsDependenciesDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.RUNTIME_ELEMENTS_DEPENDENCIES)));

		this.onlyRuntimeElements.getDependencies().add(projectDependency);
		this.namedRuntimeElements.getDependencies().add(namedRuntimeElementsDependency);
		this.modLocalRuntime.getDependencies().add(runtimeElementsDependenciesDependency);
		this.localRuntime.getDependencies().add(namedRuntimeElementsDependency);

		return projectDependency;
	}

	public ProjectDependency implementation(String subproject) {
		return implementation(subproject, it -> {});
	}

	public ProjectDependency implementation(String subproject, Action<ProjectDependency> action)
	{
		ProjectDependency projectDependency = configure(action, dependencyHandler.project(Map.of("path", subproject)));
		ProjectDependency namedApiElementsDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.NAMED_API_ELEMENTS)));
		ProjectDependency namedRuntimeElementsDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.NAMED_RUNTIME_ELEMENTS)));
		ProjectDependency apiElementsDependenciesDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.API_ELEMENTS_DEPENDENCIES)));
		ProjectDependency runtimeElementsDependenciesDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.RUNTIME_ELEMENTS_DEPENDENCIES)));

		this.onlyRuntimeElements.getDependencies().add(projectDependency);
		this.namedRuntimeElements.getDependencies().add(namedRuntimeElementsDependency);
		this.modCompileOnly.getDependencies().add(apiElementsDependenciesDependency);
		this.modLocalRuntime.getDependencies().add(runtimeElementsDependenciesDependency);
		this.compileOnly.getDependencies().add(namedApiElementsDependency);
		this.localRuntime.getDependencies().add(namedRuntimeElementsDependency);

		return projectDependency;
	}

	public ProjectDependency compileOnly(String subproject) {
		return compileOnly(subproject, it -> {});
	}

	public ProjectDependency compileOnly(String subproject, Action<ProjectDependency> action)
	{
		ProjectDependency projectDependency = configure(action, dependencyHandler.project(Map.of("path", subproject)));
		ProjectDependency namedApiElementsDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.NAMED_API_ELEMENTS)));
		ProjectDependency apiElementsDependenciesDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.API_ELEMENTS_DEPENDENCIES)));

		this.modCompileOnly.getDependencies().add(apiElementsDependenciesDependency);
		this.compileOnly.getDependencies().add(namedApiElementsDependency);

		return projectDependency;
	}

	public ProjectDependency localRuntime(String subproject) {
		return localRuntime(subproject, it -> {});
	}

	public ProjectDependency localRuntime(String subproject, Action<ProjectDependency> action)
	{
		ProjectDependency projectDependency = configure(action, dependencyHandler.project(Map.of("path", subproject)));
		ProjectDependency namedRuntimeElementsDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.NAMED_RUNTIME_ELEMENTS)));
		ProjectDependency runtimeElementsDependenciesDependency = configure(action, dependencyHandler.project(Map.of("path", subproject, "configuration", SubmoduleDepsPlugin.RUNTIME_ELEMENTS_DEPENDENCIES)));

		this.modLocalRuntime.getDependencies().add(runtimeElementsDependenciesDependency);
		this.localRuntime.getDependencies().add(namedRuntimeElementsDependency);

		return projectDependency;
	}

	private static ProjectDependency configure(Action<ProjectDependency> action, Dependency path)
	{
		ProjectDependency projectDependency = (ProjectDependency) path;
		action.execute(projectDependency);
		return projectDependency;
	}
}
