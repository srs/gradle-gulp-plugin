package com.moowork.gradle.gulp

import org.gradle.api.Project

class GulpExtension
{
    final static String NAME = 'gulp'

    def File workDir

    GulpExtension( final Project project )
    {
        this.workDir = project.projectDir
    }
}
