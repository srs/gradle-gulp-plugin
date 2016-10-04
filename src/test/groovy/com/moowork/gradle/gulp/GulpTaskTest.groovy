package com.moowork.gradle.gulp

import org.gradle.api.GradleException
import spock.lang.IgnoreIf

class GulpTaskTest
    extends AbstractTaskTest
{
  @IgnoreIf({env['NODE_PATH']})
    def "gulp not installed"()
    {
        given:
        def task = this.project.tasks.create( 'simple', GulpTask )

        when:
        this.project.evaluate()
        task.exec()

        then:
        thrown( GradleException )
    }
}
