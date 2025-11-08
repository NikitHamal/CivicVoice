#!/usr/bin/env sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

#
# @author...
#

# We need to have this file in the same directory as the script
if [ -z "$DEFAULT_GRADLE_OPTS" ]; then
    DEFAULT_GRADLE_OPTS=""
fi
GRADLE_OPTS="$DEFAULT_GRADLE_OPTS $GRADLE_OPTS"

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""
JVM_OPTS="$DEFAULT_JVM_OPTS $JAVA_OPTS"

# Find the project root, which is the directory containing the wrapper
# properties file.
APP_HOME="$(cd `dirname "$0"` && pwd -P)"
PROPERTIES_FILE="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"

if [ ! -f "$PROPERTIES_FILE" ]; then
    # Fallback to searching parent directories
    CWD="`pwd -P`"
    while [ "$CWD" != "/" ]; do
        if [ -f "$CWD/gradlew" ]; then
            APP_HOME="$CWD"
            PROPERTIES_FILE="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"
            break
        fi
        CWD=`dirname "$CWD"`
    done
fi

if [ -f "$PROPERTIES_FILE" ]; then
    . "$APP_HOME/gradle/wrapper/gradle-wrapper.properties"
    APP_BASE_NAME=`basename "$APP_HOME"`
    APP_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
    exec "$JAVACMD" "$JVM_OPTS" -jar "$APP_JAR" "$@"
else
    echo "ERROR: Could not find gradle-wrapper.properties file in $APP_HOME or its parent directories." >&2
    exit 1
fi
