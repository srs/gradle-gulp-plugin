package com.moowork.gradle.gulp

import com.moowork.gradle.node.task.NpmTask

class GulpInstallTask
    extends NpmTask
{
    public GulpInstallTask()
    {
        super()

        this.group = 'Gulp'
        this.description = "Runs 'npm install gulp' to install gulp"

        setArgs( ['install', 'gulp'] )

        getOutputs().dir( 'node_modules/gulp' )
    }
}
