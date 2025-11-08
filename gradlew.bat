@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=
if "%JAVA_HOME%" == "" (
  set JAVA_EXE=java.exe
) else (
  set JAVA_EXE=%JAVA_HOME%\bin\java.exe
)
if exist "%JAVA_EXE%" (
  set _JAVACMD=%JAVA_EXE%
) else (
  set _JAVACMD=java.exe
)
if not "%_JAVACMD%" == "" (
  "%_JAVACMD%" -version >nul 2>&1
  if %errorlevel% neq 0 (
    echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
    echo.
    echo Please set the JAVA_HOME variable in your environment to match the
    echo location of your Java installation.
    goto :eof
  )
)

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Find the project root, which is the directory containing the wrapper
@rem properties file.
set CURRENT_DIR=%cd%
set WRAPPER_DIR=%CURRENT_DIR%
:findWrapper
if exist "%WRAPPER_DIR%\gradlew.bat" (
  set APP_HOME=%WRAPPER_DIR%
) else (
  for %%i in ("%WRAPPER_DIR%") do set WRAPPER_DIR=%%~dpi
  if not "%WRAPPER_DIR%" == "" goto findWrapper
)

if exist "%APP_HOME%\gradle\wrapper\gradle-wrapper.properties" (
  pushd "%APP_HOME%"
  set /p GRADLE_WRAPPER_JAR=<"%APP_HOME%\gradle\wrapper\gradle-wrapper.properties"
  popd
  for /f "tokens=1,2 delims==" %%a in ("%GRADLE_WRAPPER_JAR%") do (
    if "%%a" == "distributionUrl" set GRADLE_WRAPPER_JAR=%%b
  )
  set GRADLE_WRAPPER_JAR=%GRADLE_WRAPPER_JAR:file\:=%
  set GRADLE_WRAPPER_JAR=%APP_HOME%\%GRADLE_WRAPPER_JAR%
  if exist "%GRADLE_WRAPPER_JAR%" (
    set CLASSPATH=%GRADLE_WRAPPER_JAR%
  ) else (
    echo ERROR: Could not find gradle-wrapper.jar file.
    goto :eof
  )
) else (
  echo ERROR: Could not find gradle-wrapper.properties file.
  goto :eof
)

@rem Pause the script if --pause-on-exit is passed
set PAUSE_ON_EXIT=
if /i "%1" == "--pause-on-exit" set PAUSE_ON_EXIT=1
if /i "%2" == "--pause-on-exit" set PAUSE_ON_EXIT=1
if /i "%3" == "--pause-on-exit" set PAUSE_ON_EXIT=1

@rem Execute Gradle
"%_JAVACMD%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

@rem End of script
if "%PAUSE_ON_EXIT%" == "1" pause
if "%OS%"=="Windows_NT" endlocal
:eof
