@echo off
cls

set DRIVE_LETTER=%1:

set PATH=%DRIVE_LETTER%\Java\bin;%DRIVE_LETTER%\Java\ant-1.9.9\bin;c:\Windows

ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0="resources/solar_radiation_neural_net.txt"
::ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0="resources/bacterial_growth_neural_net.txt" 
::ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0="resources/custom_points_neural_net.txt" 

::ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0="resources/solar_radiation_neural_net.txt" >resources/solar_output.txt
::ant run -Ddrive-letter=%DRIVE_LETTER% -Darg0="resources/bacterial_growth_neural_net.txt" >resources/bacterial_output.txt
