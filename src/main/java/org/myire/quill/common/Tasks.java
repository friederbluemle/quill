/*
 * Copyright 2018 Peter Franzen. All rights reserved.
 *
 * Licensed under the Apache License v2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.myire.quill.common;

import java.io.File;
import java.util.concurrent.Callable;

import org.gradle.api.Task;


/**
 * Task related utility methods.
 */
public final class Tasks
{
    /**
     * Private constructor to disallow instantiations of utility method class.
     */
    private Tasks()
    {
        // Empty default ctor, defined to override access scope.
    }


    /**
     * Specify a file as input to a task, and mark it as an optional input if supported by the
     * runtime Gradle version.
     *
     * @param pTask The task.
     * @param pFile The input file.
     */
    @Deprecated
    static public void setOptionalInputFile(Task pTask, Object pFile)
    {
        pTask.getInputs().file(pFile).optional(true);
    }


    /**
     * Specify an input property value for a task. This method is defined primarily to allow setting
     * input property values with lambda expressions.
     *
     * @param pTask         The task.
     * @param pPropertyName The name of the property.
     * @param pValue        A {@code Callable} that returns the property's value.
     *
     * @throws NullPointerException if {@code pTask} is null.
     */
    static public <T> void inputProperty(Task pTask, String pPropertyName, Callable<T> pValue)
    {
        pTask.getInputs().property(pPropertyName, pValue);
    }


    /**
     * Specify an input file for a task. This method is defined primarily to allow setting input
     * file values with lambda expressions.
     *
     * @param pTask The task.
     * @param pFile A {@code Callable} that returns the file.
     *
     * @throws NullPointerException if {@code pTask} is null.
     */
    static public void inputFile(Task pTask, Callable<File> pFile)
    {
        pTask.getInputs().file(pFile);
    }


    /**
     * Specify an optional input file for a task. This method is defined primarily to allow setting
     * optional input file values with lambda expressions.
     *
     * @param pTask The task.
     * @param pFile A {@code Callable} that returns the optional file.
     *
     * @throws NullPointerException if {@code pTask} is null.
     */
    static public void optionalInputFile(Task pTask, Callable<File> pFile)
    {
        pTask.getInputs().file(pFile).optional(true);
    }


    /**
     * Specify an output file for a task. This method is defined primarily to allow setting output
     * file values with lambda expressions.
     *
     * @param pTask The task.
     * @param pFile A {@code Callable} that returns the file.
     *
     * @throws NullPointerException if {@code pTask} is null.
     */
    static public void outputFile(Task pTask, Callable<File> pFile)
    {
        pTask.getOutputs().file(pFile);
    }
}
