@echo off
setlocal

set JAVA_HOME=C:/Tools/sun/jdk1.5
set PROD_HOME=D:/Web/prod
set RESTLET_HOME=D:\Forge\build\swc\restlet\trunk\build\dist\restlet-1.0.0
set WEB_HOME=D:\Forge\build\swc\web\trunk\build\dist\web-1.0rc6
set RESTLET_IP=192.168.0.2
set RESTLET_PORT=80
set WEB_LOGIN=liosnoe
set WEB_PASSWORD=sultconting

rem Copyright (c) 1999, 2006 Tanuki Software Inc.
rem
rem Java Service Wrapper general startup script
rem

rem
rem Resolve the real path of the wrapper.exe
rem  For non NT systems, the _REALPATH and _WRAPPER_CONF values
rem  can be hard-coded below and the following test removed.
rem
if "%OS%"=="Windows_NT" goto nt
echo This script only works with NT-based versions of Windows.
goto :eof

:nt
rem
rem Find the application home.
rem
rem %~dp0 is location of current script under NT
set _REALPATH=%~dp0

rem Decide on the wrapper binary.
set _WRAPPER_BASE=wrapper
set _WRAPPER_EXE=%_REALPATH%%_WRAPPER_BASE%-windows-x86-32.exe
if exist %_WRAPPER_EXE% goto conf
set _WRAPPER_EXE=%_REALPATH%%_WRAPPER_BASE%-windows-x86-64.exe
if exist %_WRAPPER_EXE% goto conf
set _WRAPPER_EXE=%_REALPATH%%_WRAPPER_BASE%.exe
if exist %_WRAPPER_EXE% goto conf
echo Unable to locate a Wrapper executable using any of the following names:
echo %_REALPATH%%_WRAPPER_BASE%-windows-x86-32.exe
echo %_REALPATH%%_WRAPPER_BASE%-windows-x86-64.exe
echo %_REALPATH%%_WRAPPER_BASE%.exe
pause
goto :eof

rem
rem Find the wrapper.conf
rem
:conf
set _WRAPPER_CONF="%~f1"
if not %_WRAPPER_CONF%=="" goto startup
set _WRAPPER_CONF="%WEB_HOME%\conf\wrapper.conf"

rem
rem Start the Wrapper
rem
:startup

"%_WRAPPER_EXE%" -c %_WRAPPER_CONF% set.JAVA_HOME=%JAVA_HOME% set.RESTLET_HOME=%RESTLET_HOME% set.WEB_HOME=%WEB_HOME% set.RESTLET_PORT=%RESTLET_PORT% set.RESTLET_IP=%RESTLET_IP% set.PROD_HOME=%PROD_HOME% set.WEB_LOGIN=%WEB_LOGIN% set.WEB_PASSWORD=%WEB_PASSWORD%

if not errorlevel 1 goto :eof
pause

