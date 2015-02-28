Gradle plugin for Gulp
=======================

[![Build Status](http://goo.gl/3EpX6b)](http://goo.gl/EUkRd4)
[![Download](http://goo.gl/UuoQ84)](http://goo.gl/6jlBXE)
[![License](http://goo.gl/MMfZhl)](http://goo.gl/D6iAcM)

This is a very simple Gradle plugin for running gulp tasks part of the build.
It merely wraps calls to "gulp xyz" as "gradle gulp_xyz" tasks. Gulp is installed locally using npm.

Installing the plugin
---------------------

Releases of this plugin are hosted at Bintray and is part of the jCenter repository. Development builds
are published for every commit to the master branch. These SNAPSHOTs are hosted on the OJO repository
and to use them you will need to add OJO to your buildscript configuration.

Setup the plugin like this:

    plugins {
        id "com.moowork.gulp" version "0.9"
    }

Or using the old (pre 2.1) way:

    buildscript {
        repositories {
            jcenter()

            // If you want to use a SNAPSHOT build, add the OJO repository:
            maven {
                name 'JFrog OSS snapshot repo'
                url  'https://oss.jfrog.org/oss-snapshot-local/'
            }
        }

        dependencies {
            classpath 'com.moowork.gradle:gradle-gulp-plugin:0.9'
        }
    }

Include the plugin in your build.gradle file like this:

    apply plugin: 'com.moowork.gulp'

The plugin will also apply gradle-node-plugin for Node and NPM related tasks.
(See http://github/srs/gradle-node-plugin for details).

Using the plugin
----------------

You can run gulp tasks using this syntax:

    $ gradle gulp_build    # this runs gulp build
    $ gradle gulp_compile  # this runs gulp compile

... and so on.

These tasks do not appear explicitly in `gradle tasks`, they only appear as task rule.
Your gulpfile.js defines what gulp_* tasks exist (see `gulp --tasks`, or `gradle gulp_--tasks`).

Also (more importantly), you can depend on those tasks, e.g.

    // runs "gulp build" as part of your gradle build
    build.dependsOn gulp_build

This is the main advantage of this plugin, to allow build
scripts (and gulp agnostics) to run gulp tasks via gradle.

It is also possible to run a gulp task only if one of its input files have changed:

    def srcDir = new File(projectDir, "src/main/web")
    def targetDir = new File(project.buildDir, "web")
    gulp_dist.inputs.dir srcDir
    gulp_dist.outputs.dir targetDir

Extended Usage
--------------

If you need to supply gulp with options, you have to create GulpTasks:

    task gulpBuildWithOpts(type: GulpTask) {
       args = ["build", "arg1", "arg2"]
    }


NPM helpers
-----------

The plugin also provides two fixed helper tasks to run once per project, which
however require npm (https://npmjs.org/) to be installed:

 - installGulp: This installs gulp to the project folder, using "npm install gulp"
 - npmInstall: This just runs "npm install" (possibly useful for scripting)

Since gulp will only be installed in your project folder, it will not
interact with the rest of your system, and can easily be removed later by
deleting the node_modules folders.

So as an example, you can make sure a local version of gulp exists using this:

    // makes sure on each build that gulp is installed
    gulp_build.dependsOn 'installGulp'

    // processes your package.json before running gulp build
    gulp_build.dependsOn 'npmInstall'

    // runs "gulp build" as part of your gradle build
    build.dependsOn gulp_build


Automatically downloading Node
------------------------------

To simplify the build, you can say that the plugin should download Node and NPM automatically. The dependent
gradle-node-plugin does the magic (http://github.com/srs/gradle-node-plugin). Set this configuration to enable:

    node {
        // Version of node to use.
        version = '0.10.22'

        // Enabled the automatic download. False is the default (for now).
        download = true
    }

Node will be installed in the .gradle folder for current user under nodejs subdirectory.

Getting latest npm on Ubuntu
----------------------------

See https://github.com/creationix/nvm on how to install npm via nvm.

Building the Plugin
-------------------

To build the plugin, just type the following command:

    ./gradlew clean build
