package com.moowork.gradle.gulp

import com.moowork.gradle.node.task.NodeTask
import org.gradle.api.GradleException

class GulpTask
    extends NodeTask
{
    private final static String GULP_SCRIPT = 'node_modules/gulp/bin/gulp.js';

    public GulpTask()
    {
        this.group = 'Gulp';
    }

    @Override
    void exec()
    {
        def localGulp = this.project.file( new File( this.project.node.nodeModulesDir, GULP_SCRIPT ) )
        if ( !localGulp.isFile() )
        {
            throw new GradleException(
                "gulp not installed in node_modules, please first run 'gradle ${GulpPlugin.GULP_INSTALL_NAME}'" )
        }

        setWorkingDir( this.project.gulp.workDir )
        setScript( localGulp )
        super.exec()
    }
}
