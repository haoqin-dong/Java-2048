@echo off
chcp 65001 >nul <nul
setlocal enabledelayedexpansion
title 2048 一键启动器
cd /d "%~dp0"

echo ============================================================
echo                    2048 一键启动器
echo ============================================================
echo.

set "JDK_HOME="
if exist "%ProgramFiles%\Java\jdk-25.0.2\bin\javac.exe" set "JDK_HOME=%ProgramFiles%\Java\jdk-25.0.2"
if not defined JDK_HOME (
    for /d %%D in ("%ProgramFiles%\Java\jdk-25*") do (
        if exist "%%~fD\bin\javac.exe" set "JDK_HOME=%%~fD"
    )
)
if not defined JDK_HOME (
    for /d %%D in ("%ProgramFiles%\Java\jdk*") do (
        if exist "%%~fD\bin\javac.exe" (
            echo %%~fD | findstr /i "25" >nul && set "JDK_HOME=%%~fD"
        )
    )
)
if not defined JDK_HOME (
    for /d %%D in ("D:\Java\jdk-25*" "D:\jdk-25*" "D:\Program Files\Java\jdk-25*") do (
        if exist "%%~fD\bin\javac.exe" set "JDK_HOME=%%~fD"
    )
)
if not defined JDK_HOME (
    echo [错误] 没有找到 jdk-25，请确认电脑上已经安装 JDK 25。
    echo 已搜索: %ProgramFiles%\Java 以及 D 盘常见目录
    pause
    exit /b 1
)

set "JAVA_HOME=%JDK_HOME%"
set "PATH=%JDK_HOME%\bin;%PATH%"
echo [信息] 本次会话已自动配置 JAVA_HOME 与 PATH
echo [信息] 使用 JDK 目录: %JDK_HOME%
"%JDK_HOME%\bin\java.exe" -version <nul
echo.

:ASK_MEM
set "MEM_STR="
set /p MEM_STR=请输入给 JVM 分配的最大内存(MB，直接回车默认 512，建议 256-2048): 
if not defined MEM_STR set "MEM_STR=512"
echo %MEM_STR%| findstr /r "^[0-9][0-9]*$" >nul
if errorlevel 1 (
    echo [提示] 只能输入纯数字，例如 512，请重新输入。
    echo.
    goto ASK_MEM
)
if %MEM_STR% LSS 128 (
    echo [提示] 数值过小，已自动调整为 128MB。
    set "MEM_STR=128"
)
if %MEM_STR% GTR 8192 (
    echo [警告] 输入超过 8192MB，请确认物理内存充足。
    choice /c yn /m "仍然使用 %MEM_STR% MB 吗(Y/N)"
    if errorlevel 2 goto ASK_MEM
)
set /a MEM_MIN=MEM_STR/2
if %MEM_MIN% LSS 64 set "MEM_MIN=64"
echo [信息] 初始堆内存 %MEM_MIN%MB，最大堆内存 %MEM_STR%MB
echo.

if not exist "classes" mkdir "classes"
echo [信息] 正在使用 jdk-25 编译源码...
"%JDK_HOME%\bin\javac.exe" -encoding UTF-8 -d classes src\*.java <nul
if errorlevel 1 (
    echo.
    echo [错误] 编译失败，请将上方报错信息截图反馈。
    pause
    exit /b 1
)
echo [信息] 编译成功，正在启动 2048...
echo.
"%JDK_HOME%\bin\java.exe" -Xms%MEM_MIN%m -Xmx%MEM_STR%m -cp classes Main2048
set "EXIT_CODE=%ERRORLEVEL%"
echo.
if not "%EXIT_CODE%"=="0" (
    echo [提示] 程序异常退出，退出码 %EXIT_CODE%
    pause
)
endlocal
