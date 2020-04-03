*NOTE* If you would like to run the code please exclude the "website" folder when attempting to run on Android Studios.

# SmartBrace

SmartBrace Application for Carpal Tunnel Syndrome - Capstone Project

- Android 8.0 (API: 21) - Oreo
- Emulator: Samsung S10 Plus

Front Page (Complete) - Abaad Q.:
- receives data 'logdata' from firebase every 3 seconds,
   checks for log indicating an incorrect posture
- displays user posture (form) in ProgressBar (e.g: Loading/Inaccurate/Accurate)
- navigation: 
   buttons to log tracker, sensor reports, discover page,
   options menu to application settings and smartbrace website,
   fab (floating action button) to smartbrace website
- all data received/transmitted in realtime
 
Log Tracker (Complete) - Abaad Q.:
- receives data from 'LogData' firebase branch,
   each entry stored as object and via an adapter listed
   in a listview (sorted in LIFO, Last-in-first-out)
- listview includes, log time, angle, and form (correct/incorrect)
- connected to firebase realtime database, synced with arduino
- all data received/transmitted in realtime

Sensor Reports (Complete) - Omar Z.:
- 3 graph choices added to layout: Gyro, EMG Sensor, and EMG Voltage
- receive data from multiple firebase branches, then graph data pulled
  with options of seeing past hour, 6 hours or 24 hours of data graphed.
- incorporate notifications into graphs where if form of brace is inaccurate
  display data points in red, otherwise blue for user to distinguish uses.
- all data received/transmitted in realtime

Discover Page (Complete) - Abaad Q.:
- contains six useful links to syndrome related resources
- incorporates android WebView implemented in card view within grid layout,
   webview allows for links to open within the application
- links are currently static, updatable with change to a single line

Settings (Complete) - Abaad Q.:
- receives current vibrator motor setting (e.g: ON/OFF) via firebase,
   any changes made get transmitted to firebase and into Arduino
- reset user data option, confirms with the user and,
   upon confirmation clears the following data: LogData/GyroData/EmgData on firebase
- all settings received/transmitted in realtime

SmartBrace Website (complete) - Cameron B.-developed, Abaad Q.-hosted:
- project website containing information on carpal tunnel syndrome and the application/device
- Hosted on my Amazon AWS Cloud, 
   can be accessed here: https://aqamar.s3.ca-central-1.amazonaws.com/index.html
 
FireBase Connections - Abaad Q., Omar Z.:
- several instances of firebase connections established and maintained throughout the application
- e.g: LogData, Gyro, EMG, VibrateMotor
 
 
