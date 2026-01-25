@echo off
cd /d "%~dp0"
echo Running Docker...
docker compose up --build
echo Docker finished.
pause
