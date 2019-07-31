/*
 * Copyright 2015, 2019 Peter Franzen. All rights reserved.
 *
 * Licensed under the Apache License v2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.myire.quill.dashboard;

import org.gradle.api.reporting.Report;
import org.gradle.api.reporting.ReportContainer;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.tasks.Nested;


/**
 * The reports produced by a {@code DashboardReportTask}.
 */
public interface DashboardReports extends ReportContainer<Report>
{
    /**
     * Get the HTML file report.
     *
     * @return  The HTML file report.
     */
    @Nested
    SingleFileReport getHtml();
}
