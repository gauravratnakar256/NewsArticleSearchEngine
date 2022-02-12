SET PYTHON_PATH = where python.exe
@echo off
%PYTHON_PATH% "main.py" "https://www.nytimes.com/" "50" "1000"