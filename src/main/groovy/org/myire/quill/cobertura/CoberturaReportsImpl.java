/*
 * Copyright 2015 Peter Franzen. All rights reserved.
 *
 * Licensed under the Apache License v2.0: http://www.apache.org/licenses/LICENSE-2.0
 */
package org.myire.quill.cobertura;

import java.io.File;

import groovy.lang.Closure;

import org.gradle.api.Project;
import org.gradle.api.reporting.ConfigurableReport;
import org.gradle.api.reporting.DirectoryReport;
import org.gradle.api.reporting.Report;
import org.gradle.api.reporting.SingleFileReport;
import org.gradle.api.reporting.internal.TaskReportContainer;

import org.myire.quill.common.Projects;
import org.myire.quill.report.DefaultDirectoryReport;
import org.myire.quill.report.DefaultSingleFileReport;


/**
 * Default implementation of {@code CoberturaReports}. For some reason the Groovy compiler cannot
 * handle the covariant return types of some overridden methods in {@code NamedDomainObjectSet}. The
 * Java compiler has no problems handling it, so this implementation is in Java. Note that all
 * subclasses of {@code TaskReportContainer} in the Gradle distribution (e.g. {@code PmdReportsImpl}
 * and {@code DefaultBuildDashboardReports} are Java classes.
 */
public class CoberturaReportsImpl extends TaskReportContainer<Report> implements CoberturaReports
{
    static private final String XML_REPORT_NAME = "coberturaXml";
    static private final String HTML_REPORT_NAME = "coberturaHtml";
    static private final String DEFAULT_XML_REPORT_FILE_NAME = "coverage.xml";


    /**
     * Create a new {@code CoberturaReportsImpl}.
     *
     * @param pTask The task that owns this reports instance.
     */
    CoberturaReportsImpl(CoberturaReportsTask pTask)
    {
        super(ConfigurableReport.class, pTask);

        // By default the reports reside in a directory with the same name as the context.
        String aReportDirName = pTask.getContext().getName();

        // Add the XML report, which is a single file.
        SingleFileReport aXmlReport =
                add(DefaultSingleFileReport.class,
                    pTask.getProject(),
                    XML_REPORT_NAME,
                    "Cobertura XML report",
                    new DefaultXmlReportFile(pTask.getProject(), aReportDirName, DEFAULT_XML_REPORT_FILE_NAME));

        // Add the HTML report, which is a directory report.
        DirectoryReport aHtmlReport =
                add(DefaultDirectoryReport.class,
                    pTask.getProject(),
                    HTML_REPORT_NAME,
                    "Cobertura HTML report",
                    "index.html",
                    new DefaultReportsDir(pTask.getProject(), aReportDirName));

        // Both reports are enabled by default.
        aXmlReport.setEnabled(true);
        aHtmlReport.setEnabled(true);
    }


    /**
     * Get the XML report file. Default is a file called &quot;coverage.xml&quot; in a directory
     * with the same name as the context of the owning {@code CoberturaReportsTask}. This directory
     * resides in a directory named &quot;cobertura&quot; in the project's report directory.
     *
     * @return  The XML report.
     */
    @Override
    public SingleFileReport getXml()
    {
        return (SingleFileReport) getByName(XML_REPORT_NAME);
    }


    /**
     * Get the Cobertura HTML report directory. The default value is a directory with the same name
     * as the context of the owning {@code CoberturaReportsTask}. This directory resides in a
     * directory named &quot;cobertura&quot; in the project's report directory.
     *
     * @return  The HTML report.
     */
    @Override
    public DirectoryReport getHtml()
    {
        return (DirectoryReport) getByName(HTML_REPORT_NAME);
    }


    /**
     * Closure for lazily evaluating the default Cobertura report directory spec.
     */
    static private class DefaultReportsDir extends Closure<File>
    {
        private final Project fProject;
        private final String fDirectoryName;

        DefaultReportsDir(Project pProject, String pDirectoryName)
        {
            super(null);
            fProject = pProject;
            fDirectoryName = pDirectoryName;
        }

        public File doCall(Object pValue)
        {
            return defaultReportsDir();
        }

        protected File defaultReportsDir()
        {
            File aCoberturaRootReportDir = Projects.createReportDirectorySpec(fProject, "cobertura");
            return new File(aCoberturaRootReportDir, fDirectoryName);
        }
    }


    /**
     * Closure for lazily evaluating the default XML report file spec.
     */
    static private class DefaultXmlReportFile extends DefaultReportsDir
    {
        private final String fFileName;

        DefaultXmlReportFile(Project pProject, String pDirectoryName, String pFileName)
        {
            super(pProject, pDirectoryName);
            fFileName = pFileName;
        }

        public File doCall(Object pValue)
        {
            return new File(defaultReportsDir(), fFileName);
        }
    }
}
