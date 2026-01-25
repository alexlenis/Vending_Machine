@echo off
cd /d "%~dp0"
echo Compiling...
if not exist out mkdir out
javac -encoding UTF-8 -d out src\*.java
if errorlevel 1 (
  echo Compile failed.
  pause
  exit /b 1
)
echo Running GUI...
java -cp out VendingMachineUI
echo GUI exited.
pause
