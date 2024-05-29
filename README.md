# AssetTrackPro


### Script to Run the Application on the Server

```bash
cd /usr/zebra/Inwento || exit

# Check if the application is already running
if pgrep -f "java -Xmx64m -Xms64m -Dorg.gradle.appname=gradlew -classpath /usr/zebra/Inwento/gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain bootRun" >/dev/null; then
    echo "Stopping the application..."
    # Terminate the running application
    pkill -f "java -Xmx64m -Xms64m -Dorg.gradle.appname=gradlew -classpath /usr/zebra/Inwento/gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain bootRun"
fi

# Pull the latest changes from Git
echo "Pulling changes from Git..."
git pull

# Run ./gradlew bootRun
echo "Running the application..."
./gradlew bootRun
