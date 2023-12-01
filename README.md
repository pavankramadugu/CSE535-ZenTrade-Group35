## ZenTrade - Stress-Smart Investment Advisory

ZenTrade is a context-aware mobile application for high net-worth individuals, offering personalized investment and portfolio rebalancing advice based on individual risk tolerance, stress levels, and trading frequencies, aiming to create a sophisticated and adaptive investment experience.

### Instructions to run Fuzzy Component of the app

The Fuzzy Component takes the health conditions of the user from the sensor data module and the risk tolerance input to determine the portfolio factor. 

To effectively run the indicidual component, import the app into the Android Studio and perform the following steps:
- Find the jFuzzyLogic.jar file provided along with component code
- Copy the path of the jar file and add the following line to *dependecies{ }* in the app gradle file(build.gradle.kts(Module:app))

    implementation(files("C:/Users/uppal/Downloads/jFuzzyLogic.jar"))

- Now run the app to see the fuzzy output in the TextView of the MainActivity.








