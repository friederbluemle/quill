/*
 * Copyright 2015, 2018 Peter Franzen. All rights reserved.
 *
 * Licensed under the Apache License v2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.myire.quill.report

import org.gradle.api.Project
import org.gradle.api.reporting.Report
import org.gradle.api.reporting.SingleFileReport


/**
 * A {@code SingleFileReport} with a lazily evaluated default destination.
 */
class DefaultSingleFileReport extends DefaultDestinationReport implements SingleFileReport
{
    /**
     * Create a new {@code DefaultSingleFileReport}.
     *
     * @param pProject              The project for which the report will be produced.
     * @param pName                 The report's symbolic name.
     * @param pDisplayName          The report's descriptive name.
     * @param pDefaultDestination   A closure that will return the report's default file destination
     *                              when called.
     */
    DefaultSingleFileReport(Project pProject,
                            String pName,
                            String pDisplayName,
                            Closure<File> pDefaultDestination)
    {
        super(pProject,
              pName,
              pDisplayName,
              Report.OutputType.FILE,
              pDefaultDestination);
    }
}
