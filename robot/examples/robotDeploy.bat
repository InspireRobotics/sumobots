@echo Deploying jar at %1%
@cd ../..
@gradlew --quiet --console=plain rms:tars:run -ProbotPath="%1%"